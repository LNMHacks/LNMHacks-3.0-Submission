package com.couponsecure.couponsecure.Views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.couponsecure.couponsecure.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.TagViewHolder> {

    private ArrayList<CouponsModel> coupons;
    private Context context;


    public CouponAdapter(ArrayList<CouponsModel> coupons, Context context) {
        this.coupons = coupons;
        this.context = context;
    }

    @Override
    public CouponAdapter.TagViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coupon_layout, parent, false);
        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TagViewHolder holder, final int position) {


        holder.id.setText(coupons.get(position).couponId);
        holder.number.setText(coupons.get(position).counponNo);
        holder.by.setText(coupons.get(position).counponBy);
        holder.offer.setText(coupons.get(position).offer);

        holder.claimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Your Transaction id "+holder.id.getText().toString()+"is under claim process..Please wait for QR Code",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return coupons.size();
    }

    public static class TagViewHolder extends RecyclerView.ViewHolder {

        private final TextView id;
        private final TextView number;
        private final TextView by;
        private final LinearLayout location, timing;
        private final Button claimButton;
        private final TextView offer;

        public TagViewHolder(View v) {
            super(v);
             id = v.findViewById(R.id.couponId);
             number = v.findViewById(R.id.couponNo);
             by = v.findViewById(R.id.couponBy);
             location = v.findViewById(R.id.ll_location_cons);
             timing = v.findViewById(R.id.ll_expiry);
             claimButton = v.findViewById(R.id.claim_button);
             offer = v.findViewById(R.id.offer);


        }
    }
}