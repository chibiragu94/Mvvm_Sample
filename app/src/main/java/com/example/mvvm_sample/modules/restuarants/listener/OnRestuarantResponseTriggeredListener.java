package com.example.mvvm_sample.modules.restuarants.listener;

import com.example.mvvm_sample.modules.restuarants.data.model.RestuarantDb;
import com.example.mvvm_sample.modules.restuarants.data.model.server_response.Restaurant;
import java.util.List;

public interface OnRestuarantResponseTriggeredListener {

    void showRestuarantResponse(List<Restaurant> restuarantResponse,int start,int count);
    void showRestuarantListFromDb(List<RestuarantDb> dbList);
}
