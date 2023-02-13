package com.yousef_shora.omniaoffers.pojo;

import androidx.lifecycle.MutableLiveData;

import com.yousef_shora.omniaoffers.modal.Constants;
import com.yousef_shora.omniaoffers.modal.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CouponsRepo {
    CouponsFireStoreManager fireStoreManager = new CouponsFireStoreManager();
    public MutableLiveData<ArrayList<Coupon>> mutableLiveData = new MutableLiveData<>();

    public CouponsRepo() {
        mutableLiveData.setValue(new ArrayList<>());
    }

    public void insert_coupons(ArrayList<Coupon> offers_array_to_insert, CouponsFireStoreManager.InsertCouponsCallback callback) {
        Map<String, ArrayList<Coupon>> offerMap = new HashMap<>();
        offerMap.put(Constants.COUPONS_ARRAY, offers_array_to_insert);
        fireStoreManager.insert_offer_query(callback, offerMap);
    }

    public void insert_coupon(Coupon offer, CouponsFireStoreManager.InsertCouponsCallback callback) {

        Objects.requireNonNull(mutableLiveData.getValue()).add(offer);

        HashMap<String, ArrayList<Coupon>> hashMap = new HashMap<>();

        hashMap.put(Constants.COUPONS_ARRAY, mutableLiveData.getValue());

        fireStoreManager.insert_offer_query(callback, hashMap);
    }

    public void push_notification(Coupon offer, CouponsFireStoreManager.PostNotificationCallback callback) {
        fireStoreManager.post_notification(callback, offer);
    }

    public void upload_image(byte[] byte_array, String image_id, CouponsFireStoreManager.UploadImageCallback callback) {
        fireStoreManager.upload_image_query(callback, byte_array, image_id);
    }

    public void get_image(CouponsFireStoreManager.GetImageUrlCallback callback, String offer_id) {
        fireStoreManager.get_image_query(callback, Utils.getimagereferece(fireStoreManager.storageReference, offer_id));
    }

    public void get_offers(CouponsFireStoreManager.GetCouponsCallback callback) {
        fireStoreManager.getCoupons_query(callback);
    }

    public void observe_data() {
        fireStoreManager.observe_offers((offers_array) -> {
            if (offers_array != null) {
                //empty the array
                mutableLiveData.setValue(new ArrayList<>());

                mutableLiveData.setValue((ArrayList<Coupon>) offers_array);
            }
        });
    }
}