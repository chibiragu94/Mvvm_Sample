package com.example.mvvm_sample.modules.home.view.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm_sample.R;
import com.example.mvvm_sample.databinding.ItemRestuarantCollectionBinding;
import com.example.mvvm_sample.modules.home.data.model.Collection;
import com.example.mvvm_sample.modules.home.data.model.Collection_;
import com.example.mvvm_sample.modules.home.listener.CollectionListener;
import com.example.mvvm_sample.modules.restuarants.view.activity.RestuarantActivity;
import com.example.mvvm_sample.utils.IntentKeys;

import java.util.List;

public class RestuarantCollectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements CollectionListener {

    private List<Collection> collectionList;

    public RestuarantCollectionAdapter(List<Collection> collectionList) {
        this.collectionList = collectionList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        ItemRestuarantCollectionBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_restuarant_collection, viewGroup, false);
        return new RestuarantCollectionAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Collection_ collection = collectionList.get(position).getCollection();
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.bindings.setCollection(collection);
        viewHolder.bindings.setItemClickListener(this);
    }

    @Override
    public int getItemCount() {
        return collectionList.size();
    }

    @Override
    public void onCollectionClicked(View view,String collectionName) {
        Intent intent1 = new Intent(view.getContext(), RestuarantActivity.class);
        intent1.putExtra(IntentKeys.COLLECTION_NAME,collectionName);
        view.getContext().startActivity(intent1);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ItemRestuarantCollectionBinding bindings;
        ViewHolder(ItemRestuarantCollectionBinding binding) {
            super(binding.getRoot());
            bindings = binding;
        }
    }

}
