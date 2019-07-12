package com.example.mvvm_sample.modules.home.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Collection_ {

    @SerializedName("collection_id")
    @Expose
    private Integer collectionId;
    @SerializedName("res_count")
    @Expose
    private Integer resCount;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("share_url")
    @Expose
    private String shareUrl;
}
