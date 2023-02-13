package com.yousef_shora.omniaoffers.viewmodel;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yousef_shora.omniaoffers.modal.Constants;
import com.yousef_shora.omniaoffers.modal.Utils;
import com.yousef_shora.omniaoffers.pojo.Offer;
import com.yousef_shora.omniaoffers.pojo.OffersRepo;

import java.io.IOException;
import java.util.ArrayList;

public class InsertViewModel extends ViewModel {
    public MutableLiveData<Boolean> insertedOfferMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Uri> imageUriMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<String> descriptionMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> titleMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> categoryMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> postNotificationMutableLiveData = new MutableLiveData<>();

    private final OffersRepo repo = new OffersRepo();
    private Offer offer_to_add = new Offer();

    public void setImageUri(Uri imageUri) {
        this.imageUriMutableLiveData.setValue(imageUri);
    }

    public void setOffer_to_add(Offer offer_to_add) {
        this.offer_to_add = offer_to_add;
    }

    public void insert_data(ContentResolver contentResolver) {
        try {
            if (imageUriMutableLiveData.getValue() != null) {

                Bitmap current_bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUriMutableLiveData.getValue());

                byte[] image = Utils.convert_bitmap_to_byte_Array(current_bitmap, current_bitmap.getWidth(), current_bitmap.getHeight());

                repo.upload_image(image, offer_to_add.getId(), uploaded -> {
                    if (uploaded) {
                        repo.get_image(image_url -> {
                            if (image_url != null) {
                                offer_to_add.setPicture_url(image_url);
                                insert_offer();
                            } else {
                                insertedOfferMutableLiveData.setValue(false);
                            }
                        }, offer_to_add.getId());
                    } else {
                        insertedOfferMutableLiveData.setValue(false);
                    }
                });
            } else {
                offer_to_add.setPicture_url(Constants.LIMITED_OFFER_IMAGE);
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

    private void post_notification(Offer offer) {
        repo.push_notification(offer, posted -> {
            if (posted) {
                insertedOfferMutableLiveData.postValue(true);
            } else {
                insertedOfferMutableLiveData.postValue(false);
            }
        });
    }


    private void insert_offer() {
        repo.get_offers(offers_array -> {
            if (offers_array != null) {
                offers_array.add(offer_to_add);
            } else {
                offers_array = new ArrayList<>();
                offers_array.add(offer_to_add);
            }
            repo.insert_offers((ArrayList<Offer>) offers_array, inserted -> {
                if (inserted) {
                    if (Boolean.TRUE.equals(postNotificationMutableLiveData.getValue())) {
                        post_notification(offer_to_add);
                    } else {
                        insertedOfferMutableLiveData.setValue(true);
                    }
                } else {
                    insertedOfferMutableLiveData.setValue(false);
                }
            });
        });
    }
}
