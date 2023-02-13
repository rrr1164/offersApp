package com.yousef_shora.omniaoffers.viewmodel;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yousef_shora.omniaoffers.modal.Constants;
import com.yousef_shora.omniaoffers.modal.Utils;
import com.yousef_shora.omniaoffers.pojo.Coupon;
import com.yousef_shora.omniaoffers.pojo.CouponsRepo;

import java.io.IOException;
import java.util.ArrayList;

public class CouponsInsertViewModel extends ViewModel {
    public MutableLiveData<Boolean> insertedCouponMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Uri> imageUriMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<String> descriptionMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> titleMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> categoryMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> postNotificationMutableLiveData = new MutableLiveData<>();

    private final CouponsRepo repo = new CouponsRepo();
    private Coupon coupon_to_add = new Coupon();

    public void setImageUri(Uri imageUri) {
        this.imageUriMutableLiveData.setValue(imageUri);
    }

    public void setCoupon_to_add(Coupon offer_to_add) {
        this.coupon_to_add = offer_to_add;
    }

    public void insert_data(ContentResolver contentResolver) {
        try {
            if (imageUriMutableLiveData.getValue() != null) {

                Bitmap current_bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUriMutableLiveData.getValue());

                byte[] image = Utils.convert_bitmap_to_byte_Array(current_bitmap, current_bitmap.getWidth(), current_bitmap.getHeight());

                repo.upload_image(image, coupon_to_add.getId(), uploaded -> {
                    if (uploaded) {
                        repo.get_image(image_url -> {
                            if (image_url != null) {
                                coupon_to_add.setPicture_url(image_url);
                                insert_offer();
                            } else {
                                insertedCouponMutableLiveData.setValue(false);
                            }
                        }, coupon_to_add.getId());
                    } else {
                        insertedCouponMutableLiveData.setValue(false);
                    }
                });
            } else {
                coupon_to_add.setPicture_url(Constants.LIMITED_OFFER_IMAGE);
                insert_offer();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean validate_form() {
        return titleMutableLiveData != null && titleMutableLiveData.getValue() != null && !titleMutableLiveData.getValue().isEmpty() &&
                categoryMutableLiveData != null && categoryMutableLiveData.getValue() != null && !categoryMutableLiveData.getValue().isEmpty() &&
                Utils.is_category(categoryMutableLiveData.getValue());
    }

    private void post_notification(Coupon coupon) {
        repo.push_notification(coupon, posted -> {
            if (posted) {
                insertedCouponMutableLiveData.postValue(true);
            } else {
                insertedCouponMutableLiveData.postValue(false);
            }
        });
    }


    private void insert_offer() {
        repo.get_offers(coupons_array -> {
            if (coupons_array != null) {
                coupons_array.add(coupon_to_add);
            } else {
                coupons_array = new ArrayList<>();
                coupons_array.add(coupon_to_add);
            }
            repo.insert_coupons((ArrayList<Coupon>) coupons_array, inserted -> {
                if (inserted) {
                    if (Boolean.TRUE.equals(postNotificationMutableLiveData.getValue())) {
                        post_notification(coupon_to_add);
                    } else {
                        insertedCouponMutableLiveData.setValue(true);
                    }
                } else {
                    insertedCouponMutableLiveData.setValue(false);
                }
            });
        });
    }
}
