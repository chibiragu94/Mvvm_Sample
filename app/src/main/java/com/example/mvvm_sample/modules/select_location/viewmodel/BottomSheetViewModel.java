package com.example.mvvm_sample.modules.select_location.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BottomSheetViewModel extends ViewModel {

    private static final String TAG = "BottomSheetViewModel";

    private MutableLiveData<String> locationName;
    private MutableLiveData<String> locationType;
    private MutableLiveData<String> locationSelected;

    public BottomSheetViewModel() {
        this.locationName = new MutableLiveData<>();
        this.locationType = new MutableLiveData<>();
        this.locationSelected = new MutableLiveData<>();
    }

    public MutableLiveData<String> getLocationName() {
        return locationName;
    }

    public MutableLiveData<String> getLocationType() {
        return locationType;
    }

    public MutableLiveData<String> getLocationSelected() {
        return locationSelected;
    }

    public void updateSelectedLocation(String value) {
        locationSelected.postValue(value);
    }

    public void saveAddressValues() {
        Log.v(TAG, " Name: " + locationName.getValue() + " selected: " + locationType.getValue() + " type: " + locationSelected.getValue());
    }

    public void updateLocationType(String value) {
        locationType.postValue(value);
    }

    public void updateLocationName(String value) {
        locationName.postValue(value);
    }
}
