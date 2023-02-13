package com.yousef_shora.omniaoffers.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.yousef_shora.omniaoffers.R;
import com.yousef_shora.omniaoffers.databinding.ActivityInsertCouponBinding;
import com.yousef_shora.omniaoffers.modal.Constants;
import com.yousef_shora.omniaoffers.modal.Utils;
import com.yousef_shora.omniaoffers.pojo.Coupon;
import com.yousef_shora.omniaoffers.viewmodel.CouponsInsertViewModel;

import org.jetbrains.annotations.Nullable;

public class InsertCouponActivity extends AppCompatActivity {

    CouponsInsertViewModel insertViewModel;
    ProgressDialog progressDialog;
    ActivityInsertCouponBinding binding;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertCouponBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Set title
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
            getSupportActionBar().setTitle(getString(R.string.app_name));
        }

        Coupon coupon_to_add = new Coupon();
        insertViewModel = new ViewModelProvider(this).get(CouponsInsertViewModel.class);

        String[] categories = Utils.get_categories();
        AutoCompleteTextView categories_option = findViewById(R.id.categories_options);
        ArrayAdapter<String> categories_adapter = new ArrayAdapter<>(this, R.layout.list_categories, categories);
        categories_option.setAdapter(categories_adapter);

        bindTwoWayEditText(R.id.titleEditText, insertViewModel.titleMutableLiveData);
        bindTwoWayEditText(R.id.descriptionEditText, insertViewModel.descriptionMutableLiveData);
        bindTwoWayCompleteText(R.id.categories_options, insertViewModel.categoryMutableLiveData, R.id.categories_layout);
        bindTwoWayCheckBox(R.id.post_notification_checkbox, insertViewModel.postNotificationMutableLiveData);

        insertViewModel.titleMutableLiveData.observe(this, coupon_to_add::setTitle);

        insertViewModel.descriptionMutableLiveData.observe(this, coupon_to_add::setDescription);

        insertViewModel.categoryMutableLiveData.observe(this, coupon_to_add::setCategory);

        insertViewModel.insertedCouponMutableLiveData.observe(this, inserted -> {
            hide_progressDialog();
            if (inserted) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Snackbar.make(binding.parentInsert, "Error inserting Data", Snackbar.LENGTH_SHORT).show();
            }
        });

        insertViewModel.imageUriMutableLiveData.observe(this, uri -> binding.pickedImage.setImageURI(uri));

        binding.imagePicker.setOnClickListener(second_view -> selectImage());

        binding.addCoupon.setOnClickListener(second_view -> {
            if (insertViewModel.validate_form()) {
                submit_data(coupon_to_add);
            } else {
                Snackbar.make(binding.parentInsert, "There are Empty Fields", Snackbar.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_IMAGE_OPEN && resultCode == RESULT_OK) {
            // Do work with full size photo saved at fullPhotoUri
            insertViewModel.setImageUri(data.getData());
        }
    }

    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
        startActivityForResult(intent, Constants.REQUEST_IMAGE_OPEN);
    }


    private void submit_data(Coupon coupon_to_add) {
        create_progressDialog();
        insertViewModel.setCoupon_to_add(coupon_to_add);
        insertViewModel.insert_data(getContentResolver());
    }

    public void create_progressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Inserting Data...");
        progressDialog.show();
    }

    public void hide_progressDialog() {
        if (progressDialog == null) {
            create_progressDialog();
        }
        progressDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    private void bindTwoWayEditText(int viewId, final MutableLiveData<String> changeableData) {
        final TextInputEditText editText = this.findViewById(viewId);
        editText.addTextChangedListener((new TextWatcher() {
            public void afterTextChanged(@Nullable Editable s) {

            }

            public void beforeTextChanged(@Nullable CharSequence text, int start, int count, int after) {

            }

            public void onTextChanged(@Nullable CharSequence text, int start, int before, int count) {
                if (text == null || text.toString().isEmpty() && viewId != R.id.descriptionEditText) {
                    editText.setError("Empty Field");
                }
                changeableData.setValue(String.valueOf(text));
            }
        }));
    }

    private void bindTwoWayCompleteText(int viewId, final MutableLiveData<String> changeableData, int errorviewId) {
        final AutoCompleteTextView completeTextView = this.findViewById(viewId);
        final TextInputLayout inputLayout = this.findViewById(errorviewId);
        completeTextView.setOnItemClickListener((adapterView, view, position, l) -> {
            String selection = (String) adapterView.getItemAtPosition(position);
            if (selection == null || selection.isEmpty()) {
                inputLayout.setError("Empty Field");
            }
            changeableData.setValue(String.valueOf(selection));
        });
    }

    private void bindTwoWayCheckBox(int viewId, final MutableLiveData<Boolean> changeableData) {
        final MaterialCheckBox checkBox = this.findViewById(viewId);
        checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            changeableData.setValue(b);
        });
    }
}


