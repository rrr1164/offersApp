package com.yousef_shora.omniaoffers.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.yousef_shora.omniaoffers.pojo.Offer;
import com.yousef_shora.omniaoffers.pojo.OffersRepo;

import java.util.ArrayList;

public class OffersViewModel extends ViewModel {
    public MutableLiveData<ArrayList<Offer>> offersMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> deletedDataMutableLiveData = new MutableLiveData<>();
    private final OffersRepo repo;

    private final Observer<ArrayList<Offer>> observer = offers -> {
        offersMutableLiveData.setValue(offers);
    };

    public void remove_offer(int index) {
        assert index >= 0 && offersMutableLiveData.getValue() != null && index < offersMutableLiveData.getValue().size();
        offersMutableLiveData.getValue().remove(index);
        repo.insert_offers(offersMutableLiveData.getValue(), deleted -> {
            if (deleted) {
                deletedDataMutableLiveData.setValue(index);
            } else {
                deletedDataMutableLiveData.setValue(-1);
            }
        });
    }



    public OffersViewModel() {
        repo = new OffersRepo();
        repo.observe_data();
        repo.mutableLiveData.observeForever(observer);
    }

    @Override
    protected void onCleared() {
        // assuming it's the same LiveData
        repo.mutableLiveData.removeObserver(observer);
        super.onCleared();

    }
}