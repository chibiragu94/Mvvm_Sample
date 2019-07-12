package com.example.mvvm_sample.modules.home.listener;

import com.example.mvvm_sample.modules.home.data.model.Collection;

import java.util.List;

public interface OnCollectionResponseListener {

    void onCollectionResponseTriggered(List<Collection> collectionList);

}
