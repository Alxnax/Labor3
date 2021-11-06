package com.example.restaurantfinder.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.example.restaurantfinder.model.Restaurant;
import com.example.restaurantfinder.repository.RestaurantRepository;

import java.util.List;

public class RestaurantsViewModel extends AndroidViewModel {

    private RestaurantRepository restaurantRepository;

    private LiveData<List<Restaurant>> allRestaurants;

    public RestaurantsViewModel(@NonNull Application application) {
        super(application);
        restaurantRepository = new RestaurantRepository(application);
        allRestaurants = restaurantRepository.getAllRestaurants();
    }

    public void insert(Restaurant restaurant) {
        restaurantRepository.insert(restaurant);
    }

    public void update(Restaurant restaurant) {
        restaurantRepository.update(restaurant);
    }

    public void delete(Restaurant restaurant) {
        restaurantRepository.delete(restaurant);
    }

    public void deleteAllRestaurants() {
        restaurantRepository.deleteAllRestaurants();
    }

    public LiveData<List<Restaurant>> getAllRestaurants() {
        return allRestaurants;
    }
}
