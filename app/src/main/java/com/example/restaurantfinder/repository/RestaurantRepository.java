package com.example.restaurantfinder.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.restaurantfinder.dao.RestaurantsDao;
import com.example.restaurantfinder.db.RestaurantsDataBase;
import com.example.restaurantfinder.model.Restaurant;
import com.example.restaurantfinder.webservice.User;
import com.example.restaurantfinder.webservice.Webservice;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;


public class RestaurantRepository {

    private RestaurantsDao restaurantsDao;
    private LiveData<List<Restaurant>> allRestaurants;
    private Webservice webservice_objekt;

    public RestaurantRepository(Application application) {
        RestaurantsDataBase dataBase = RestaurantsDataBase.getInstance(application);
        restaurantsDao = dataBase.restaurantsDao();
        allRestaurants = restaurantsDao.getAllRestaurants();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/api/")
                .build();
        webservice_objekt = retrofit.create(Webservice.class);
    }

    public void insert(Restaurant restaurant) {
        new InsertRestaurantAsyncTask(restaurantsDao).execute(restaurant);
    }

    public void update(Restaurant restaurant) {
        new UpdateRestaurantAsyncTask(restaurantsDao).execute(restaurant);
    }

    public void delete(Restaurant restaurant) {
        new DeleteRestaurantAsyncTask(restaurantsDao).execute(restaurant);
    }

    public void deleteAllRestaurants() {
        new DeleteAllRestaurantAsyncTask(restaurantsDao).execute();
    }

    public LiveData<List<Restaurant>> getAllRestaurants() {
        refreshAllRestaurants();
        return allRestaurants;
    }


    public void refreshAllRestaurants() {
        System.out.println("HIER------------------------------------");
        // if DB leer aus Webservice laden
        if (true){
            try {
                // https://reqres.in/api/users?page=2
                Response<List<User>> response = webservice_objekt.getUser().execute();
                List users = response.body();
                /*for (int i = 0; i < users.size(); i++){
                    Restaurant restaurant = new Restaurant();
                    String retrievedRestaurant = users[i].fist_name; // first_name
                    String retrievedRestaurantOrt = ""; // last_name
                    restaurant.setRestaurants(retrievedRestaurant);
                    restaurant.setRestaurantOrt(retrievedRestaurantOrt);

                    insert(restaurant);
                }*/
                System.out.println("------------------------------------");
                System.out.println(response.body());
                System.out.println(users);
            } catch (Exception e) {
                e.printStackTrace();
            };
        };

    }

    private static class InsertRestaurantAsyncTask extends AsyncTask<Restaurant, Void, Void> {
        private RestaurantsDao restaurantsDao;

        private InsertRestaurantAsyncTask(RestaurantsDao restaurantsDao) {
            this.restaurantsDao = restaurantsDao;
        }

        @Override
        protected Void doInBackground(Restaurant... restaurants) {
            restaurantsDao.insert(restaurants[0]);
            return null;
        }
    }

    private static class UpdateRestaurantAsyncTask extends AsyncTask<Restaurant, Void, Void> {
        private RestaurantsDao restaurantsDao;

        private UpdateRestaurantAsyncTask(RestaurantsDao restaurantsDao) {
            this.restaurantsDao = restaurantsDao;
        }

        @Override
        protected Void doInBackground(Restaurant... restaurants) {
            restaurantsDao.update(restaurants[0]);
            return null;
        }
    }

    private static class DeleteRestaurantAsyncTask extends AsyncTask<Restaurant, Void, Void> {
        private RestaurantsDao restaurantsDao;

        private DeleteRestaurantAsyncTask(RestaurantsDao restaurantsDao) {
            this.restaurantsDao = restaurantsDao;
        }

        @Override
        protected Void doInBackground(Restaurant... restaurants) {
            restaurantsDao.delete(restaurants[0]);
            return null;
        }
    }

    private static class DeleteAllRestaurantAsyncTask extends AsyncTask<Void, Void, Void> {
        private RestaurantsDao restaurantsDao;

        private DeleteAllRestaurantAsyncTask(RestaurantsDao restaurantsDao) {
            this.restaurantsDao = restaurantsDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            restaurantsDao.deleteAllRestaurants();
            return null;
        }
    }
}
