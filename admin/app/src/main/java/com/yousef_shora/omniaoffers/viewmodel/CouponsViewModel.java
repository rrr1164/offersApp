package com.yousef_shora.omniaoffers.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.yousef_shora.omniaoffers.pojo.Coupon;
import com.yousef_shora.omniaoffers.pojo.CouponsRepo;
import com.yousef_shora.omniaoffers.pojo.Offer;
import com.yousef_shora.omniaoffers.pojo.OffersRepo;

import java.util.ArrayList;

public class CouponsViewModel extends ViewModel {
    public MutableLiveData<ArrayList<Coupon>> couponsMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> deletedDataMutableLiveData = new MutableLiveData<>();
    private final CouponsRepo repo;

    private final Observer<ArrayList<Coupon>> observer = offers -> {
        couponsMutableLiveData.setValue(offers);
    };

    public void remove_coupon(int index) {
        assert index >= 0 && couponsMutableLiveData.getValue() != null && index < couponsMutableLiveData.getValue().size();
        couponsMutableLiveData.getValue().remove(index);
        repo.insert_coupons(couponsMutableLiveData.getValue(), deleted -> {
            if (deleted) {
                deletedDataMutableLiveData.setValue(index);
            } else {
                deletedDataMutableLiveData.setValue(-1);
            }
        });
    }



    public CouponsViewModel() {
        repo = new CouponsRepo();
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