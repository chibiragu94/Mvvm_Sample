package com.example.mvvm_sample.modules.home.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class RestuarantCollectionResponse {

    @SerializedName("collections")
    @Expose
    private List<Collection> collections = null;
    @SerializedName("has_more")
    @Expose
    private Integer hasMore;
    @SerializedName("share_url")
    @Expose
    private String shareUrl;
    @SerializedName("display_text")
    @Expose
    private String displayText;
    @SerializedName("has_total")
    @Expose
    private Integer hasTotal;
}
