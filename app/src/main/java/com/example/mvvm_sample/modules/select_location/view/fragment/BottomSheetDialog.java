package com.example.mvvm_sample.modules.select_location.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;

import com.example.mvvm_sample.R;
import com.example.mvvm_sample.common.LocationType;
import com.example.mvvm_sample.databinding.FragmentBottomSheetsLocationBinding;
import com.example.mvvm_sample.modules.select_location.listener.AddressListener;
import com.example.mvvm_sample.modules.select_location.listener.BottomSheetViewListener;
import com.example.mvvm_sample.modules.select_location.viewmodel.BottomSheetViewModel;
import com.example.mvvm_sample.utils.IntentKeys;
import com.example.mvvm_sample.utils.SharedPrefsUtils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetDialog extends BottomSheetDialogFragment implements BottomSheetViewListener {

    public BottomSheetDialog() {
    }

    private FragmentBottomSheetsLocationBinding binding;
    private BottomSheetViewModel viewModel;
    private String selectedLocation;
    private AddressListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (AddressListener)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        selectedLocation = getArguments().getString("selected_location");
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_sheets_location, container, false);
        viewModel = ViewModelProviders.of((FragmentActivity) getContext()).get(BottomSheetViewModel.class);

        binding.setViewmodel(viewModel);
        binding.setHandler(this);
        binding.setLifecycleOwner(this);

        viewModel.updateLocationType(String.valueOf(LocationType.HOME));
        viewModel.updateLocationName("");
        viewModel.updateSelectedLocation(selectedLocation);

        onbserveValuesToViews();
        return binding.getRoot();
    }

    @Override
    public void onNextClicked(View view) {
        // viewModel.saveAddressValues();
        SharedPrefsUtils.setStringPreference(getContext(), IntentKeys.INTENT_LOCATION, selectedLocation);
        listener.onAddressSelected();
    }

    @Override
    public void onHomeClicked(View view) {
        viewModel.updateLocationType(String.valueOf(LocationType.HOME));
    }

    @Override
    public void onOfficeClicked(View view) {
        viewModel.updateLocationType(String.valueOf(LocationType.OFFICE));
    }

    @Override
    public void onOthersClicked(View view) {
        viewModel.updateLocationType(String.valueOf(LocationType.OTHERS));
    }


    private void onbserveValuesToViews() {
        viewModel.getLocationType().observe((LifecycleOwner) getContext(), value ->
        {
            if (value.equals(String.valueOf(LocationType.HOME))) {
                binding.tvHome.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.green));
                binding.tvOffice.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.text_grey));
                binding.tvOthers.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.text_grey));
            } else if (value.equals(String.valueOf(LocationType.OFFICE))) {

                binding.tvHome.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.text_grey));
                binding.tvOffice.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.green));
                binding.tvOthers.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.text_grey));
            } else {
                binding.tvHome.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.text_grey));
                binding.tvOffice.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.text_grey));
                binding.tvOthers.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.green));
            }
        });
    }
}
