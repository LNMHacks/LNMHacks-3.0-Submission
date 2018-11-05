package com.enggmartservices.enggmart.adapers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enggmartservices.enggmart.activities.ProductDescription;
import com.enggmartservices.enggmart.R;
import com.enggmartservices.enggmart.models.StoreModel;

import java.util.List;

public class CustomAdapterStore extends RecyclerView.Adapter<CustomAdapterStore.MyViewHolder> {
    private List<StoreModel> listItemsStore;
    private Context context;


    public CustomAdapterStore(Context context, List<StoreModel> listItemsStore) {
        this.context = context;
        this.listItemsStore = listItemsStore;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final StoreModel storeModel = listItemsStore.get(position);
        holder.name.setText(storeModel.getItemName() + "");
        holder.price.setText("\u20B9 " + storeModel.getItemPrice() + "");
        Glide.with(context).load(storeModel.getItemImage()).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open another activity on item click
                Intent intent = new Intent(holder.itemView.getContext(), ProductDescription.class);
                intent.putExtra("id", storeModel.getItemID().toString());
                holder.itemView.getContext().startActivity(intent); // start Intent
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItemsStore.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // init the item view's
        private View mView;
        private TextView name, price;
        private ImageView image;
        private LinearLayout linearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.name_item_list);
            price = itemView.findViewById(R.id.price_item_list);
            image = (ImageView) itemView.findViewById(R.id.image_item_list);
            linearLayout = itemView.findViewById(R.id.item_layout_store);
        }

        @Override
        public void onClick(View v) {
            if (v == linearLayout) {

            }
        }
    }
}
