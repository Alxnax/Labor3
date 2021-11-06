package com.example.restaurantfinder.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;


//Here entity refers to tables in the db, and here we have a table called restaurant.
//we are using foreign key because the same author might have written more than one restaurants,
@Entity(tableName = "restaurant_table")
public class Restaurant {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "restaurants")
    @NonNull
    public String restaurants;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(@NonNull String restaurants) {
        this.restaurants = restaurants;
    }
}
