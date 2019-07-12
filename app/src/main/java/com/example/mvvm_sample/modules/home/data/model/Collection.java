package com.example.mvvm_sample.modules.home.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Collection {

    @SerializedName("collection")
    @Expose
    private Collection_ collection;

}
