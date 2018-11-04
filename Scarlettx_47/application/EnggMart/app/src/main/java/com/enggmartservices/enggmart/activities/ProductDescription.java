package com.enggmartservices.enggmart.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enggmartservices.enggmart.R;
import com.enggmartservices.enggmart.utility.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProductDescription extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private Button button;
    private String itemID;
    private String itemType;
    private String priceitem;
    private TextView name, price, description, percentOff, pricePurchace;
    private ImageView img;
    private RadioGroup rGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_description);
        Utils.darkenStatusBar(this, R.color.colorPrimary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
            } else {
                itemID = extras.getString("id");
            }
        } else {
            itemID = (String) savedInstanceState.getSerializable("id");
        }

        name = findViewById(R.id.item_name_des);
        price = findViewById(R.id.item_price_des);
        description = findViewById(R.id.item_description_des);
        percentOff = findViewById(R.id.des_off_percent);
        pricePurchace = findViewById(R.id.des_price_purchace);
        button = (Button) findViewById(R.id.purchase);
        img = findViewById(R.id.img_item_des);
        strikeText(price);
        // This will get the radiogroup
        rGroup = (RadioGroup) findViewById(R.id.radiogroup);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("storeDetails").child(itemID);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Glide.with(getApplicationContext()).load(dataSnapshot.child("itemImage").getValue().toString()).into(img);
                name.setText(dataSnapshot.child("itemName").getValue().toString());
                priceitem = dataSnapshot.child("itemPrice").getValue().toString();
                pricePurchace.setText("\u20B9 " + Math.round(Float.parseFloat(priceitem) * 0.8) + "");
                itemType = "NEW";
                percentOff.setText("20% off");
                price.setText("\u20B9 " + priceitem);
                description.setText(dataSnapshot.child("itemDescription").getValue().toString());
                return;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
// This overrides the radiogroup onCheckListener
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.new_book:
                        pricePurchace.setText("\u20B9 " + Math.round(Float.parseFloat(priceitem) * 0.8) + "");
                        percentOff.setText("20% off");
                        itemType = "NEW";
                        break;
                    case R.id.old_book:
                        pricePurchace.setText("\u20B9 " + Math.round(Float.parseFloat(priceitem) * 0.7) + "");
                        percentOff.setText("30% off");
                        itemType = "OLD";
                        break;
                    case R.id.rent_book:

                        pricePurchace.setText("\u20B9 " + Math.round(Float.parseFloat(priceitem) * 0.77) + "");
                        percentOff.setText("47% ret");
                        itemType = "RENT";
                        break;
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProductDescription.this, PaymentPage.class);
                i.putExtra("idItem", itemID);
                i.putExtra("itemtype", itemType);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void strikeText(TextView tv) {
        tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }
}
