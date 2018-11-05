package com.enggmartservices.enggmart.adapers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enggmartservices.enggmart.activities.PDFViewActivity;
import com.example.user.enggmart.R;
import com.enggmartservices.enggmart.models.ModelCurrentAffairs;

import java.util.List;

public class CurrentAffairsAdapter extends RecyclerView.Adapter<CurrentAffairsAdapter.MyViewHolder> {
    private List<ModelCurrentAffairs> listItemsCurrentAffaires;
    private Context context;


    public CurrentAffairsAdapter(Context context, List<ModelCurrentAffairs> listItemsCurrentAffairs) {
        this.context = context;
        this.listItemsCurrentAffaires = listItemsCurrentAffairs;
    }

    @Override
    public CurrentAffairsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_current_affair, parent, false);
        return new CurrentAffairsAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CurrentAffairsAdapter.MyViewHolder holder, final int position) {
        final ModelCurrentAffairs modelCurrentAffairs = listItemsCurrentAffaires.get(position);
        holder.itemDate.setText(modelCurrentAffairs.getItemDate() + "");
        final String pdfFile = modelCurrentAffairs.getPdfFile();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open another activity on item click
                Intent intent = new Intent(holder.itemView.getContext(), PDFViewActivity.class);
                intent.putExtra("pdf", pdfFile);
                holder.itemView.getContext().startActivity(intent);
                // start Intent
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItemsCurrentAffaires.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        private TextView itemDate;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemDate = (TextView) itemView.findViewById(R.id.current_affairs_item_date);
        }


    }
}






