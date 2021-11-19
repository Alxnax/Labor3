package com.example.restaurantfinder.webservice;
import com.google.gson.annotations.SerializedName;

public class User {
    public int id;

    @SerializedName("first_name")
    public String first_name;

    @SerializedName("last_name")
    public String last_name;

    @SerializedName("email")
    public String email;

    @SerializedName("avatar")
    public String avatar;

}
