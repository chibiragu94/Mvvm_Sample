package com.example.mvvm_sample.modules.restuarants.data.network_service_calls;

import android.util.Log;

import com.example.mvvm_sample.modules.restuarants.data.model.server_response.Restaurant;
import com.example.mvvm_sample.modules.restuarants.data.model.server_response.RestuarantResponse;
import com.example.mvvm_sample.modules.restuarants.listener.OnRestuarantResponseTriggeredListener;
import com.example.mvvm_sample.networking.base.Constants.BaseUrl;
import com.example.mvvm_sample.networking.base.Constants.RestaurantAPI;
import com.example.mvvm_sample.networking.base.RetrofitBuilderHelper;

import java.net.HttpURLConnection;
import java.util.Collections;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class GetRestuarantServices {

    private static final String TAG = GetRestuarantServices.class.getName();
    private OnRestuarantResponseTriggeredListener onResponseTriggered;

    public GetRestuarantServices(OnRestuarantResponseTriggeredListener onResponseTriggered) {
        this.onResponseTriggered = onResponseTriggered;
    }

    public void excecute(int start,int count) {
        RetrofitBuilderHelper retrofitBuilderHelper = RetrofitBuilderHelper.getInstance();
        retrofitBuilderHelper.setBaseUrl(BaseUrl.MVVM_SAMPLE_BASE_URL);

        RestaurantAPI restaurantAPI = retrofitBuilderHelper.createServiceWithoutBasicAuth((RestaurantAPI.class));
        Observable<Response<RestuarantResponse>> registerObervable = restaurantAPI.fetchRestuarants("b6bc0b5e556eaccd91268fab1f04fe46", "application/json",count,start);
        registerObervable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<RestuarantResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<RestuarantResponse> response) {
                        Log.v(TAG, "Response: " + response.body().toString());
                        if (response.code() == HttpURLConnection.HTTP_OK) {
                            onResponseTriggered.showRestuarantResponse(response.body().getRestaurants(),response.body().getResultsStart(),response.body().getResultsShown());
                        } else {
                            onResponseTriggered.showRestuarantResponse(Collections.<Restaurant>emptyList(),0,9);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        onResponseTriggered.showRestuarantResponse(Collections.<Restaurant>emptyList(),0,9);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
