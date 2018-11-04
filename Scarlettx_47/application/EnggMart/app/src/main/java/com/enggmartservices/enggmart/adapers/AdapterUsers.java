package com.enggmartservices.enggmart.adapers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enggmartservices.enggmart.models.ModelUserClass;
import com.enggmartservices.enggmart.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterUsers extends BaseAdapter {
    private Context context;
    private List<ModelUserClass> list;

    public AdapterUsers(Context context, List<ModelUserClass> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView name, phone, email;
        CircleImageView dpuser;
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.usersinflate, parent, false);
        name = (TextView) convertView.findViewById(R.id.nameu);
        email = (TextView) convertView.findViewById(R.id.emailu);
        //  phone = (TextView) convertView.findViewById(R.id.phoneu);
        dpuser = (CircleImageView) convertView.findViewById(R.id.usersdp);
        Log.e("name", list.get(position).getName());
        Log.e("email", list.get(position).getEmail());
        Log.e("phone", list.get(position).getPhone());
        Log.e("Image", list.get(position).getImage());
        name.setText(list.get(position).getName());
        email.setText(list.get(position).getEmail());
        // phone.setText(list.get(position).getPhone());
        String img = list.get(position).getImage();
        if (!img.equals("not Provided"))
            Glide.with(context).load(img).into(dpuser);
        return convertView;
    }

}
