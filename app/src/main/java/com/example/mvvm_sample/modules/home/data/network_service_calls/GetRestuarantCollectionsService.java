package com.example.mvvm_sample.modules.home.data.network_service_calls;

import android.util.Log;

import com.example.mvvm_sample.modules.home.data.model.Collection;
import com.example.mvvm_sample.modules.home.data.model.RestuarantCollectionResponse;
import com.example.mvvm_sample.modules.home.listener.OnCollectionResponseListener;
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

public class GetRestuarantCollectionsService {

    private static final String TAG = GetRestuarantCollectionsService.class.getName();
    private OnCollectionResponseListener listener;

    public GetRestuarantCollectionsService(OnCollectionResponseListener listener) {
        this.listener = listener;
    }

    public void excecute() {
        RetrofitBuilderHelper retrofitBuilderHelper = RetrofitBuilderHelper.getInstance();
        retrofitBuilderHelper.setBaseUrl(BaseUrl.MVVM_SAMPLE_BASE_URL);

        RestaurantAPI restaurantAPI = retrofitBuilderHelper.createServiceWithoutBasicAuth((RestaurantAPI.class));
        Observable<Response<RestuarantCollectionResponse>> registerObervable = restaurantAPI.fetchRestuarantsCollections("b6bc0b5e556eaccd91268fab1f04fe46", "application/json");
        registerObervable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<RestuarantCollectionResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<RestuarantCollectionResponse> response) {
                        Log.v(TAG, "Response: " + response.body().toString());
                        if (response.code() == HttpURLConnection.HTTP_OK) {
                              listener.onCollectionResponseTriggered(response.body().getCollections());
                        } else {
                            listener.onCollectionResponseTriggered(Collections.<Collection>emptyList());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onCollectionResponseTriggered(Collections.<Collection>emptyList());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
