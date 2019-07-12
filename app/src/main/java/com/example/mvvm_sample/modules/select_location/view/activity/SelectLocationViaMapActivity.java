package com.example.mvvm_sample.modules.select_location.view.activity;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.mvvm_sample.R;
import com.example.mvvm_sample.databinding.ActivitySelectLocationViaMapBinding;
import com.example.mvvm_sample.modules.select_location.listener.AddressListener;
import com.example.mvvm_sample.modules.select_location.view.fragment.BottomSheetDialog;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SelectLocationViaMapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraIdleListener, AddressListener {

    private static final String TAG = "Map";

    private ActivitySelectLocationViaMapBinding binding;
    private GoogleMap mMap;
    // for getting the current location
    private FusedLocationProviderClient fusedLocationClient;
    private boolean isLocationFetched;
    private String updatedLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialise binding by setting the contentview
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_location_via_map);
        binding.setHandler(new Handler());
        // initialise to get the current location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // set Google map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // Hide actionBar
        getSupportActionBar().hide();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.setOnCameraMoveListener(this);
        mMap.setOnCameraIdleListener(this);

        getCurrentLocation();
    }


    @Override
    public void onCameraMove() {
        isLocationFetched = false;
        setNextButtonColor();
        updatedLocation = "";
        binding.tvLocUpdates.setText("Loading");
    }

    @Override
    public void onCameraIdle() {
        //get latlng at the center by calling
        LatLng midLatLng = mMap.getCameraPosition().target;
        isLocationFetched = true;
        setNextButtonColor();
        updatedLocation = getLocationDetailUsingGeoCoder(midLatLng.latitude, midLatLng.longitude);
        binding.tvLocUpdates.setText(updatedLocation);
    }

    // Get Current location using the fusedLocationProvider
    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        if (mMap != null) {
                            // Logic to handle location object
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            LatLng latLng = new LatLng(latitude, longitude);

                            isLocationFetched = true;
                            setNextButtonColor();

                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                        }
                    }
                });
    }


    /**
     * @param latitude
     * @param longitude
     * @return FullAddress
     * <p>
     * Get Complete address by passing the lat and lng using GeoCoder
     */
    public String getLocationDetailUsingGeoCoder(double latitude, double longitude) {
        String fullAddress = "";
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses != null && addresses.size() > 0) {
                fullAddress = addresses.get(0).getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fullAddress;
    }

    private void setNextButtonColor() {
        if (isLocationFetched) {
            binding.btnNext.setBackgroundTintList(getResources().getColorStateList(R.color.green));
        } else {
            binding.btnNext.setBackgroundTintList(getResources().getColorStateList(R.color.text_grey));
        }
    }

    @Override
    public void onAddressSelected() {
        finish();
    }


    /*
     * *************** Handler class to handle all view click through dataBindings **************************
     * */
    public class Handler {
        public void onBackPressed(View view) {
            finish();
        }

        public void onCurrentLocationClicked(View view) {
            getCurrentLocation();
        }

        public void onButtonNext(View view) {
            if (!updatedLocation.isEmpty() && isLocationFetched) {
                BottomSheetDialog bottomSheetFragment = new BottomSheetDialog();
                Bundle bundle = new Bundle();
                bundle.putString("selected_location", updatedLocation);
                bottomSheetFragment.setArguments(bundle);
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        }
    }
}
