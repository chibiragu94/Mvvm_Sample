package com.example.mvvm_sample.networking.base.Constants;


import com.example.mvvm_sample.modules.home.data.model.RestuarantCollectionResponse;
import com.example.mvvm_sample.modules.restuarants.data.model.server_response.RestuarantResponse;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RestaurantAPI {

    @GET("search?entity_id=4&entity_type=city&q=southindian&lat=12.971891&lon=77.641151&radius=100")
    Observable<Response<RestuarantResponse>> fetchRestuarants(@Header("user-key") String user_key, @Header("Accept") String accept,
                                                              @Query("count") int count,@Query("start") int start);

    @GET("collections?city_id=4&lat=-35.960678&lon=143.365036&count=10")
    Observable<Response<RestuarantCollectionResponse>> fetchRestuarantsCollections(@Header("user-key") String user_key, @Header("Accept") String accept);
}
