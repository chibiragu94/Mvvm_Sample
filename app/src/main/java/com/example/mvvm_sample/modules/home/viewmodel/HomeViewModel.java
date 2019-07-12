package com.example.mvvm_sample.modules.home.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvm_sample.modules.home.data.model.Collection;
import com.example.mvvm_sample.modules.home.data.network_service_calls.GetRestuarantCollectionsService;
import com.example.mvvm_sample.modules.home.listener.OnCollectionResponseListener;

import java.util.List;

/**
 * @author chibi_ragu
 * HomeViewModel handles the business logic's and acl like intermediate between the view and it's model
 */
public class HomeViewModel extends ViewModel implements OnCollectionResponseListener {

    private MutableLiveData<List<Collection>> listMutableLiveData;

    public HomeViewModel() {
        this.listMutableLiveData = new MutableLiveData<>();
    }


    @Override
    public void onCollectionResponseTriggered(List<Collection> collectionList) {
        listMutableLiveData.postValue(collectionList);
    }

    public MutableLiveData<List<Collection>> getListMutableLiveData() {
        return listMutableLiveData;
    }

    public void getRestuarantCollections() {
        GetRestuarantCollectionsService service = new GetRestuarantCollectionsService(this);
        service.excecute();
    }


}
