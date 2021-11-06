package com.example.restaurantfinder.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.restaurantfinder.model.Restaurant;

import java.util.List;

/*Once the table is created, the next step is to create DAO of those
 * DAO means data access object, the queries for accessing db will be written here.
 * we can also wrap into LiveData, so our activity or fragment gets notified as soon as a row in the queried database table changes*/
@Dao
public interface RestaurantsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Restaurant... restaurants);

    @Update
    void update(Restaurant restaurant);

    @Delete
    void delete(Restaurant restaurant);

    @Query("DELETE FROM restaurant_table")
    void deleteAllRestaurants();

    @Query("SELECT * FROM restaurant_table")
    LiveData<List<Restaurant>> getAllRestaurants();
}
