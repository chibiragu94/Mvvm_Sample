package com.example.mvvm_sample.modules.restuarants.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm_sample.R;
import com.example.mvvm_sample.databinding.ActivityRestuarantBinding;
import com.example.mvvm_sample.modules.restuarants.data.model.RestuarantDb;
import com.example.mvvm_sample.modules.restuarants.view.adapter.RestuarantAdapter;
import com.example.mvvm_sample.modules.restuarants.viewmodel.RestuarantViewModel;
import com.example.mvvm_sample.utils.IntentKeys;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chibiRagu
 * RestuarantActivity fetches the restuarants from nearby
 * Pagination is implemented by increasing its start count and count
 */
public class RestuarantActivity extends AppCompatActivity {

    private static final String TAG = "RestuarantActivity";

    private RestuarantAdapter restuarantAdapter;
    private RestuarantViewModel viewModel;
    private ActivityRestuarantBinding binding;

    private List<RestuarantDb> restaurantList = new ArrayList<>();
    private boolean isLoading;
    private boolean isLastLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_restuarant);
        viewModel = ViewModelProviders.of(this).get(RestuarantViewModel.class);
        binding.setHandler(new Handlers());

        initRvRestuarant();
        getValuesFromIntent();
        setRvScrollListeners();
        getSupportActionBar().hide();
        viewModel.getRestuarantFromServices(true);

        setViewModelObservers();

    }

    private void getValuesFromIntent() {
        String collectionName = getIntent().getExtras().getString(IntentKeys.COLLECTION_NAME);
        binding.tvCollectionName.setText(collectionName);
    }

    private void setRvScrollListeners() {
        binding.rvRestuarants.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager llm = ((LinearLayoutManager) recyclerView.getLayoutManager());
                int visibleItemCount = llm.getChildCount();
                int totalItemCount = llm.getItemCount();
                int firstVisibleItemPosition = llm.findFirstVisibleItemPosition();

                if (isLastLoading) {

                } else {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0) {
                        if (restaurantList.size() < 29) {
                            viewModel.getRestuarantFromServices(false);
                        } else {
                            isLastLoading = true;
                            restuarantAdapter.removeLoadingFooter();
                        }
                    }
                }
            }
        });
    }


    private void initRvRestuarant() {
        restuarantAdapter = new RestuarantAdapter(restaurantList);
        binding.rvRestuarants.setLayoutManager(new LinearLayoutManager(this));
        binding.rvRestuarants.setHasFixedSize(true);
        binding.rvRestuarants.setAdapter(restuarantAdapter);
    }


    // Observes all the model changes and send it to views
    private void setViewModelObservers() {
        viewModel.getMovies().observe(this, restaurants -> {
            restaurantList.clear();
            restaurantList.addAll(restaurants);
            Log.v(TAG, "restaurantList size: " + restaurantList.size());
            restuarantAdapter.notifyDataSetChanged();
        });

        viewModel.getProgressStatus().observe(this, isLoading -> {
            this.isLoading = isLoading;
            if (isLoading) binding.progressBar.setVisibility(View.VISIBLE);
            else binding.progressBar.setVisibility(View.GONE);
        });

        viewModel.getLoadMoreProgressStatus().observe(this, isLoading -> {
            isLastLoading = isLoading;
            if (isLastLoading) {
                restuarantAdapter.addLoadingFooter();
            } else {
                restuarantAdapter.removeLoadingFooter();
            }
        });
    }


    /*
     * Handler class to work in RestuarantActivity view's Clicks
     * */
    public class Handlers {
        public void onImageBackClicked(View view) {
            finish();
        }

    }


}
