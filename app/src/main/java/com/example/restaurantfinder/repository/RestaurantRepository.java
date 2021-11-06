package com.example.restaurantfinder.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.restaurantfinder.dao.RestaurantsDao;
import com.example.restaurantfinder.db.RestaurantsDataBase;
import com.example.restaurantfinder.model.Restaurant;

import java.util.List;

public class RestaurantRepository {

    private RestaurantsDao restaurantsDao;
    private LiveData<List<Restaurant>> allRestaurants;

    public RestaurantRepository(Application application) {
        RestaurantsDataBase dataBase = RestaurantsDataBase.getInstance(application);
        restaurantsDao = dataBase.restaurantsDao();
        allRestaurants = restaurantsDao.getAllRestaurants();
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
        return allRestaurants;
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
