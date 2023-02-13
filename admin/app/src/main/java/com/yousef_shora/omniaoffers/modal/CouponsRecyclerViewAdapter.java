package com.yousef_shora.omniaoffers.modal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.yousef_shora.omniaoffers.R;
import com.yousef_shora.omniaoffers.pojo.Coupon;
import com.yousef_shora.omniaoffers.viewmodel.CouponsViewModel;
import com.yousef_shora.omniaoffers.viewmodel.OffersViewModel;

import java.util.ArrayList;

public class CouponsRecyclerViewAdapter extends RecyclerView.Adapter<CouponsRecyclerViewAdapter.ViewHolder> {
    ArrayList<Coupon> coupons;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView couponTitle;
        TextView couponCategory;
        ImageView couponImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            couponTitle = itemView.findViewById(R.id.offer_title_textview);
            couponCategory = itemView.findViewById(R.id.offer_cateogry_textview);
            couponImage = itemView.findViewById(R.id.offer_image);
        }
    }

    public ArrayList<Coupon> getData() {
        return coupons;
    }

    public CouponsRecyclerViewAdapter(ArrayList<Coupon> offers) {
        this.coupons = offers;
    }

    @NonNull
    @Override
    public CouponsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_offer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponsRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.couponTitle.setText(coupons.get(position).getTitle());
        holder.couponCategory.setText(coupons.get(position).getCategory());
        String picture_url = coupons.get(position).getPicture_url();
        if (picture_url != null && !picture_url.isEmpty()) {
            Picasso.get().load(coupons.get(position).getPicture_url()).into(holder.couponImage);
        }
    }

    public void removeItem(int position, CouponsViewModel viewModel) {
        viewModel.remove_coupon(position);
    }

    @Override
    public int getItemCount() {
        if (coupons != null)
            return coupons.size();
        else
            return 0;
    }
}
