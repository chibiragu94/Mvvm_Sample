package com.example.mvvm_sample.modules.restuarants.data.model.server_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class MenuStatus {

    @SerializedName("has_menu_status")
    @Expose
    private HasMenuStatus hasMenuStatus;
    @SerializedName("res_id")
    @Expose
    private Integer resId;
}
