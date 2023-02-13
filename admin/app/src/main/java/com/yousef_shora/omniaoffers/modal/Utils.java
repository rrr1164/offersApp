package com.yousef_shora.omniaoffers.modal;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Utils {
    private enum Categories {
        All,
        Fashion,
        Services,
        Cab,
        Restaurant,
        Groceries,
        Flight,
        Beauty
    }

    static ProgressDialog progressDialog;

    public static void create_progressDialog(Context mcontext) {
        progressDialog = new ProgressDialog(mcontext);
        progressDialog.setTitle("Inserting Data...");
        progressDialog.show();
    }

    public static void hide_progressDialog(Context mcontext) {
        if (progressDialog == null) {
            create_progressDialog(mcontext);
        }
        progressDialog.dismiss();
    }

    public static StorageReference getimagereferece(StorageReference storageReference, String offer_id) {
        return storageReference.child(Constants.OFFERS_COLLECTION + "/" + offer_id);
    }

    public static byte[] compress_image(Uri image_uri, ContentResolver contentResolver) {
        try {
            Bitmap fullBitmap = MediaStore.Images.Media.getBitmap(contentResolver, image_uri);
            int scaleDivider = 2;
            // 2. Get the downsized image content as a byte[]
            int scaleWidth = fullBitmap.getWidth() / scaleDivider;
            int scaleHeight = fullBitmap.getHeight() / scaleDivider;
            return convert_bitmap_to_byte_Array(fullBitmap, scaleWidth, scaleHeight);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }

    public static int get_bitmap_size(Bitmap bitmap) {
        return convert_bitmap_to_byte_Array(bitmap, bitmap.getWidth(), bitmap.getHeight()).length;
    }

    public static byte[] convert_bitmap_to_byte_Array(Bitmap fullBitmap, int scaleWidth, int scaleHeight) {

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(fullBitmap, scaleWidth, scaleHeight, true);

        // 2. Instantiate the downsized image content as a byte[]
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        return baos.toByteArray();
    }

    public static String[] get_categories() {
        int categories_size = 0;
        for (Categories category : Categories.values()) {
            categories_size += 1;
        }
        String[] categories_array = new String[categories_size];
        int i = 0;
        for (Categories category : Categories.values()) {
            categories_array[i] = category.name();
            i++;
        }
        return categories_array;
    }

    public static boolean is_category(String check_against) {
        Categories[] arr = Categories.values();
        for (Categories category : arr) {
            if (category.name().equalsIgnoreCase(check_against)) {
                return true;
            }
        }
        return false;
    }
}
