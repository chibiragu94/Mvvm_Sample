package com.example.mvvm_sample.modules.restuarants.data.model.server_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Location {

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("locality")
    @Expose
    private String locality;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("city_id")
    @Expose
    private Integer cityId;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;
    @SerializedName("country_id")
    @Expose
    private Integer countryId;
    @SerializedName("locality_verbose")
    @Expose
    private String localityVerbose;
}
