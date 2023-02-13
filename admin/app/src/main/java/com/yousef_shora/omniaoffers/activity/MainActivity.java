package com.yousef_shora.omniaoffers.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.yousef_shora.omniaoffers.R;
import com.yousef_shora.omniaoffers.databinding.ActivityMainBinding;
import com.yousef_shora.omniaoffers.fragment.CouponsFragment;
import com.yousef_shora.omniaoffers.fragment.OffersFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View parent_view = binding.getRoot();
        setContentView(parent_view);

        //initial fragment
        replace_Fragment(new OffersFragment());

        binding.bottomNavigationBar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.offers:
                    replace_Fragment(new OffersFragment());
                    break;
                case R.id.coupons:
                    replace_Fragment(new CouponsFragment());
                    break;
            }
            return true;
        });

    }

    private void replace_Fragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }


}