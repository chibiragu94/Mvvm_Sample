package com.example.mvvm_sample.modules.restuarants.data.model.server_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Photo_ {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("thumb_url")
    @Expose
    private String thumbUrl;
    @SerializedName("res_id")
    @Expose
    private Integer resId;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("timestamp")
    @Expose
    private Integer timestamp;
    @SerializedName("friendly_time")
    @Expose
    private String friendlyTime;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
}
