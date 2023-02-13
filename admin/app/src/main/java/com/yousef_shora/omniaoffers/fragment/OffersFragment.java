package com.yousef_shora.omniaoffers.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.yousef_shora.omniaoffers.activity.InsertActivity;
import com.yousef_shora.omniaoffers.databinding.FragmentOffersBinding;
import com.yousef_shora.omniaoffers.modal.OffersRecyclerViewAdapter;
import com.yousef_shora.omniaoffers.modal.SwipeToDeleteCallback;
import com.yousef_shora.omniaoffers.modal.Utils;
import com.yousef_shora.omniaoffers.pojo.Offer;
import com.yousef_shora.omniaoffers.viewmodel.OffersViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class OffersFragment extends Fragment {
    private FragmentOffersBinding binding;
    OffersViewModel mainViewModel;
    OffersRecyclerViewAdapter myAdapter;
    ArrayList<Offer> recycler_data = new ArrayList<>();

    public OffersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOffersBinding.inflate(inflater, container, false);
        myAdapter = new OffersRecyclerViewAdapter(recycler_data);
        binding.rvoffers.setAdapter(myAdapter);
        binding.rvoffers.setLayoutManager(new LinearLayoutManager(requireContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(),

                DividerItemDecoration.VERTICAL);

        binding.rvoffers.addItemDecoration(dividerItemDecoration);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainViewModel = new ViewModelProvider(requireActivity()).get(OffersViewModel.class);

        mainViewModel.offersMutableLiveData.observe(requireActivity(), offers -> {
            recycler_data.clear();
            recycler_data.addAll(offers);
            myAdapter.notifyDataSetChanged();
        });


        mainViewModel.deletedDataMutableLiveData.observe(requireActivity(), position -> {
            if (position != -1) {
                Utils.hide_progressDialog(requireContext());
                Snackbar snackbar = Snackbar
                        .make(binding.parentRelativelayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            } else {
                Snackbar snackbar = Snackbar
                        .make(binding.parentRelativelayout, "Couldn't delete offer.", Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }

        });
        SwipeToDeleteCallback swipecallback = new SwipeToDeleteCallback(requireContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                new AlertDialog.Builder(mContext)
                        .setTitle("Delete Item")
                        .setMessage("Are you sure you want to delete this Item?")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            Utils.create_progressDialog(requireContext());
                            myAdapter.removeItem(position, mainViewModel);
                        })
                        .setNegativeButton(android.R.string.cancel, (dialogInterface, i) -> myAdapter.notifyItemChanged(position))
                        .setOnCancelListener(dialogInterface -> myAdapter.notifyItemChanged(position))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        };
        ItemTouchHelper helper = new ItemTouchHelper(swipecallback);
        helper.attachToRecyclerView(binding.rvoffers);
        binding.addFab.setOnClickListener(view2 ->

        {
            Intent intent = new Intent(requireContext(), InsertActivity.class);
            startActivity(intent);
        });
    }
}