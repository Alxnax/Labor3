package com.example.restaurantfinder.db;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;

import com.example.restaurantfinder.dao.RestaurantsDao;
import com.example.restaurantfinder.model.Restaurant;

/*The RoomDatabase is an abstract class that ties all the pieces together and connects the entities to their corresponding DAO. Just as in an SQLiteOpenHelper, we have to define a version number and a migration strategy. */
@Database(entities = {Restaurant.class}, version = 1)
public abstract class RestaurantsDataBase extends RoomDatabase {

    private static RestaurantsDataBase instance;

    public abstract RestaurantsDao restaurantsDao();

    /*fallbackToDestructiveMigration we can let Room recreate our database if we increase the version number.
We create our database in form of a static singleton with the databaseBuilder, where we have to pass our database class and a file name.*/
    public static synchronized RestaurantsDataBase getInstance(Context context) {
        String DB_NAME = "restaurants_db";
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    RestaurantsDataBase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        //onCreate will be called only once, it will not recreate each time unless change in
        //version number and added fallbackToDestructiveMigration()
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    public void reCreateDB() {
        if (instance != null) {
            new PopulateDbAsyncTask(instance).execute();
        }
    }

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private RestaurantsDao restaurantsDao;

        private PopulateDbAsyncTask(RestaurantsDataBase dataBase) {
            restaurantsDao = dataBase.restaurantsDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            restaurantsDao.deleteAllRestaurants();

            Restaurant restaurantOne = new Restaurant();
            restaurantOne.setRestaurants("Restaurant 1");

            Restaurant restaurantTwo = new Restaurant();
            restaurantTwo.setRestaurants("Restaurant 2");

            Restaurant restaurantThree = new Restaurant();
            restaurantThree.setRestaurants("Restaurant 3");

            Restaurant restaurantFour = new Restaurant();
            restaurantFour.setRestaurants("Restaurant 4");

            restaurantsDao.insert(restaurantOne, restaurantTwo, restaurantThree, restaurantFour);

            return null;
        }
    }
}
