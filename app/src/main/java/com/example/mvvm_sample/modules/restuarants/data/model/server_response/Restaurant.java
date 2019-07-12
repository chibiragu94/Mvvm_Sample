package com.example.mvvm_sample.modules.restuarants.data.model.server_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Restaurant {
    @SerializedName("restaurant")
    @Expose
    private Restaurant_ restaurant;
}
