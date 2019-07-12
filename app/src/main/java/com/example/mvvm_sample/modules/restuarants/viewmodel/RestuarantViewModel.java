package com.example.mvvm_sample.modules.restuarants.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvm_sample.modules.restuarants.data.model.RestuarantDb;
import com.example.mvvm_sample.modules.restuarants.data.model.server_response.Restaurant;
import com.example.mvvm_sample.modules.restuarants.data.network_service_calls.GetRestuarantServices;
import com.example.mvvm_sample.modules.restuarants.data.room_db_repository.RestuarantRepository;
import com.example.mvvm_sample.modules.restuarants.listener.OnRestuarantResponseTriggeredListener;

import java.util.List;


public class RestuarantViewModel extends ViewModel implements OnRestuarantResponseTriggeredListener {

    private static final String TAG = "RestuarantViewModel";
    // Restuarant list mulatable livedata
    private MutableLiveData<List<RestuarantDb>> restuarantListMutableLiveData;
    // isLoading boolean mulatable livedata
    private MutableLiveData<Boolean> progressMutableLiveData;
    // isLoading for moadMore boolean mulatable livedata
    private MutableLiveData<Boolean> loadMoreprogressMutableLiveData;
    // initial start values
    private int start;
    // to show further restuarant count
    private int count;
    private int previouscoCount;

    public RestuarantViewModel() {
        restuarantListMutableLiveData = new MutableLiveData<>();
        progressMutableLiveData = new MutableLiveData<>();
        loadMoreprogressMutableLiveData = new MutableLiveData<>();
        start = 0;
        count = 9;
        previouscoCount = start;
    }


    /**
     * @param count          specifies number of restuarants to be displayed
     * @param start          specifies the start count
     * @param restaurantList Show list of restuarant from it's response
     */
    @Override
    public void showRestuarantResponse(List<Restaurant> restaurantList, int start, int count) {
        //change the loading value to false after getting the response
        progressMutableLiveData.postValue(false);
        loadMoreprogressMutableLiveData.postValue(false);

        // Increment the start position
        this.start = start + 10;
        this.count = 10;
        this.previouscoCount = this.start;

        for (int restuarantCount = 0; restuarantCount < restaurantList.size(); restuarantCount++) {
            RestuarantDb restuarantDb = new RestuarantDb();

            restuarantDb.setRestuarantId(Integer.parseInt(restaurantList.get(restuarantCount).getRestaurant().getId()));
            restuarantDb.setName(restaurantList.get(restuarantCount).getRestaurant().getName());
            restuarantDb.setAddress(restaurantList.get(restuarantCount).getRestaurant().getLocation().getAddress());
            restuarantDb.setLocality(restaurantList.get(restuarantCount).getRestaurant().getLocation().getLocality());
            restuarantDb.setCostForTwo(restaurantList.get(restuarantCount).getRestaurant().getAverageCostForTwo());
            restuarantDb.setLatitude(Double.parseDouble(restaurantList.get(restuarantCount).getRestaurant().getLocation().getLatitude()));
            restuarantDb.setLongitude(Double.parseDouble(restaurantList.get(restuarantCount).getRestaurant().getLocation().getLongitude()));
            restuarantDb.setPickUpStatus(restaurantList.get(restuarantCount).getRestaurant().getR().getHasMenuStatus().getDelivery());
            restuarantDb.setTakeAwayStatus(restaurantList.get(restuarantCount).getRestaurant().getR().getHasMenuStatus().getTakeaway());
            restuarantDb.setTimings(restaurantList.get(restuarantCount).getRestaurant().getTimings());
            restuarantDb.setThumbImage(restaurantList.get(restuarantCount).getRestaurant().getThumb());
            restuarantDb.setAverageRating(restaurantList.get(restuarantCount).getRestaurant().getUserRating().getAggregateRating());

            // Insert Restuarant to DB
            new RestuarantRepository(this).insertRestuarants(restuarantDb);
        }

        // then fetch all the data stored on the DB
        new RestuarantRepository(this).getAllRestuarants();
    }

    @Override
    public void showRestuarantListFromDb(List<RestuarantDb> dbList) {
        restuarantListMutableLiveData.postValue(dbList);
    }

    // Return list of restuarants to view
    public MutableLiveData<List<RestuarantDb>> getMovies() {
        return restuarantListMutableLiveData;
    }

    // return progress status to view
    public MutableLiveData<Boolean> getProgressStatus() {
        return progressMutableLiveData;
    }

    // return LoadMore progress status to view
    public MutableLiveData<Boolean> getLoadMoreProgressStatus() {
        return loadMoreprogressMutableLiveData;
    }

    // Get all Restuarants from services
    public void getRestuarantFromServices(boolean isFirstTime) {

        Log.v(TAG, " startCount: " + start + " Count: " + count);

        if (previouscoCount == start) {
            if (isFirstTime) {
                progressMutableLiveData.postValue(true);
            } else {
                loadMoreprogressMutableLiveData.postValue(true);
            }
            previouscoCount++;
            GetRestuarantServices services = new GetRestuarantServices(this);
            services.excecute(start, count);
        }
    }


}
