package com.yousef_shora.omniaoffers.modal;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.yousef_shora.omniaoffers.R;
import com.yousef_shora.omniaoffers.pojo.Offer;
import com.yousef_shora.omniaoffers.viewmodel.OffersViewModel;

import java.util.ArrayList;

public class OffersRecyclerViewAdapter extends RecyclerView.Adapter<OffersRecyclerViewAdapter.ViewHolder> {
    ArrayList<Offer> offers;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView offerTitle;
        TextView offerCategory;
        ImageView offerImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            offerTitle = itemView.findViewById(R.id.offer_title_textview);
            offerCategory = itemView.findViewById(R.id.offer_cateogry_textview);
            offerImage = itemView.findViewById(R.id.offer_image);
        }
    }

    public ArrayList<Offer> getData() {
        return offers;
    }

    public OffersRecyclerViewAdapter(ArrayList<Offer> offers) {
        this.offers = offers;
    }

    @NonNull
    @Override
    public OffersRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_offer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.offerTitle.setText(offers.get(position).getTitle());
        holder.offerCategory.setText(offers.get(position).getCategory());
        String picture_url = offers.get(position).getPicture_url();
        if (picture_url != null && !picture_url.isEmpty()) {
            Picasso.get().load(offers.get(position).getPicture_url()).into(holder.offerImage);
        }
    }

    public void removeItem(int position, OffersViewModel viewModel) {
        Log.v(Constants.FireStore_Log,"called remove");
        viewModel.remove_offer(position);
    }

    @Override
    public int getItemCount() {
        if (offers != null)
            return offers.size();
        else
            return 0;
    }
}
