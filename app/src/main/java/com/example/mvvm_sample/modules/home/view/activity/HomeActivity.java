package com.example.mvvm_sample.modules.home.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mvvm_sample.R;
import com.example.mvvm_sample.databinding.ActivityHomeBinding;
import com.example.mvvm_sample.modules.home.data.model.Collection;
import com.example.mvvm_sample.modules.home.view.adapter.RestuarantCollectionAdapter;
import com.example.mvvm_sample.modules.home.viewmodel.HomeViewModel;
import com.example.mvvm_sample.modules.select_location.view.activity.SelectLocationViaMapActivity;
import com.example.mvvm_sample.utils.IntentKeys;
import com.example.mvvm_sample.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChibiRagu
 * HomeActivity show list of collections provided and User can select his current position
 */
public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private HomeViewModel viewModel;
    private RestuarantCollectionAdapter adapter;

    private String selectedLcoation;
    private List<Collection> restuarantCollectionList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        binding.setHandler(new Handler());

        getSupportActionBar().hide();
        initRvAdapter();
        getObserveViewmodelDataToViews();

        viewModel.getRestuarantCollections();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        selectedLcoation = SharedPrefsUtils.getStringPreference(this, IntentKeys.INTENT_LOCATION, "please select your Location");
        binding.tvSelectedLocation.setText(selectedLcoation);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            Intent intent1 = new Intent(this, SelectLocationViaMapActivity.class);
            startActivity(intent1);
        }
    }

    private void initRvAdapter() {
        adapter = new RestuarantCollectionAdapter(restuarantCollectionList);
        binding.rvCollections.setLayoutManager(new LinearLayoutManager(this));
        binding.rvCollections.setHasFixedSize(true);
        binding.rvCollections.setAdapter(adapter);
    }

    // Observes all the model changes and send it to views
    private void getObserveViewmodelDataToViews() {
        viewModel.getListMutableLiveData().observe(this, collectionList -> {
            restuarantCollectionList.clear();
            restuarantCollectionList.addAll(collectionList);
            adapter.notifyDataSetChanged();
        });
    }


    /*
     * Handler class to work in HomeActivity view's Clicks
     * */
    public class Handler {
        public void onSelectLocationClicked(View view) {
            // Check for the permission
            if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                Intent intent1 = new Intent(view.getContext(), SelectLocationViaMapActivity.class);
                startActivity(intent1);
            } else {
                // Request the permission
                ActivityCompat.requestPermissions(HomeActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
        }
    }
}
