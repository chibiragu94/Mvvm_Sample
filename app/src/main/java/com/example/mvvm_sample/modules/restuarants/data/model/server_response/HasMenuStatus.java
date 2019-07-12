package com.example.mvvm_sample.modules.restuarants.data.model.server_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class HasMenuStatus {

    @SerializedName("delivery")
    @Expose
    private Integer delivery;
    @SerializedName("takeaway")
    @Expose
    private Integer takeaway;
}
