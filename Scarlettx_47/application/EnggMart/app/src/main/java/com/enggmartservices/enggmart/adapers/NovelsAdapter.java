package com.enggmartservices.enggmart.adapers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.enggmartservices.enggmart.activities.PDFViewActivity;
import com.example.user.enggmart.R;
import com.enggmartservices.enggmart.models.ModelNovels;

import java.util.List;


public class NovelsAdapter extends RecyclerView.Adapter<NovelsAdapter.MyViewHolder> {
    private List<ModelNovels> listItemsNovels;
    private Context context;


    public NovelsAdapter(Context context, List<ModelNovels> listItemsNovels) {
        this.context = context;
        this.listItemsNovels = listItemsNovels;
    }

    @Override
    public NovelsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_novel, parent, false);
        return new NovelsAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final NovelsAdapter.MyViewHolder holder, final int position) {
        final ModelNovels modelNovels = listItemsNovels.get(position);
        Glide.with(context).load(modelNovels.getItemImage() + "").into(holder.itemImage);
        final String pdfFile = modelNovels.getPdfFile();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open another activity on item click
                Intent intent = new Intent(holder.itemView.getContext(), PDFViewActivity.class);
                intent.putExtra("pdf", pdfFile);
                holder.itemView.getContext().startActivity(intent); // start Intent
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItemsNovels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        private ImageView itemImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.image_item_list_novel);
        }


    }
}






