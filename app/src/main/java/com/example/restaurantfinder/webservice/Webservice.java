package com.example.restaurantfinder.webservice;


import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface Webservice {
    @GET("/api/users/1")
    Call<UserData> getUser();
}