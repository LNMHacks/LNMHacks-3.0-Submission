package com.enggmartservices.enggmart.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.enggmartservices.enggmart.R;
import com.enggmartservices.enggmart.utility.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

public class PaymentPage extends AppCompatActivity {


    private String itemID;
    private String itemType;
    private String price;
    private String itemNameString;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseUser;
    private TextView itemName, itemPrice, itemCondition, rentPolicy;
    private ImageView img;
    private EditText name, address, landmark, contactno;
    private Button placeOrder;
    private UUID uuid;
    private DatabaseReference mDatabaseorder;
    private FirebaseAuth userAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_page);
        Utils.darkenStatusBar(this, R.color.colorPrimary);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                itemID = extras.getString("idItem");
                itemType = extras.getString("itemtype").toString();
            }
        } else {
            itemID = (String) savedInstanceState.getSerializable("id");
            itemType = (String) savedInstanceState.getSerializable("itemtype");
        }
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
        itemCondition = findViewById(R.id.condition);
        itemName = findViewById(R.id.item_name_pay);
        rentPolicy = findViewById(R.id.rent_policy);
        itemPrice = findViewById(R.id.price_to_be_paid);
        name = findViewById(R.id.name_order);
        address = findViewById(R.id.address_order);
        contactno = findViewById(R.id.contact_order);
        landmark = findViewById(R.id.landmark_order);
        placeOrder = findViewById(R.id.p_placeorder);
        img = findViewById(R.id.image_item_payment);
        String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("storeDetails").child(itemID);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Glide.with(getApplicationContext()).load(dataSnapshot.child("itemImage").getValue().toString()).into(img);
                itemName.setText(dataSnapshot.child("itemName").getValue().toString());
                itemNameString = dataSnapshot.child("itemName").getValue().toString();
                String itemPriceString = dataSnapshot.child("itemPrice").getValue().toString();
                if (itemType.equals("NEW")) {
                    price = "\u20B9 " + Math.round(Float.parseFloat(itemPriceString) * 0.8) + "";
                    itemPrice.setText(price);
                    itemCondition.setText("NEW");
                    rentPolicy.setVisibility(View.GONE);
                } else if (itemType.equals("OLD")) {
                    itemCondition.setText("OLD");
                    price = "\u20B9 " + Math.round(Float.parseFloat(itemPriceString) * 0.7) + "";
                    itemPrice.setText(price);
                    rentPolicy.setVisibility(View.GONE);
                } else if (itemType.equals("RENT")) {
                    itemCondition.setText("On RENT");
                    price = "\u20B9 " + Math.round(Float.parseFloat(itemPriceString) * 0.77) + "";
                    itemPrice.setText(price);
                    rentPolicy.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nameorder = name.getText().toString().trim();
                final String addressorder = address.getText().toString().trim();
                final String landmarkorder = landmark.getText().toString().trim();
                final String contectorder = contactno.getText().toString().trim();
                if (validateOrderDetails(nameorder, addressorder, landmarkorder, contectorder)) {
                    placeOrder.setEnabled(false);
                    uuid = UUID.randomUUID();
                    mDatabaseorder = FirebaseDatabase.getInstance().getReference().child("orders").child(uuid.toString());
                    Map<String, String> map = new HashMap<>();
                    map.put("addressOfOrder", addressorder);
                    map.put("nameUser", nameorder);
                    map.put("landmarkOfOrder", landmarkorder);
                    map.put("ContactNoOfOrder", contectorder);
                    map.put("amount", price);
                    map.put("userId", userAuth.getCurrentUser().getUid());
                    map.put("itemId", itemID);
                    map.put("itemName", itemNameString);
                    map.put("itemCondition", itemType);
                    map.put("orderStatus", "Order Confirmed");
                    final String timeStamp = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss",
                            Locale.getDefault()).format(new Date());
                    map.put("time", timeStamp);
                    mDatabaseorder.setValue(map);
                    databaseUpdate(uuid.toString());
                }
            }
        });
    }

    private void databaseUpdate(String s) {
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("users").child(userAuth.getCurrentUser().getUid()).child("orders").child(s);
        mDatabaseUser.setValue("orderconfirmed");
        Toast.makeText(PaymentPage.this,
                "Your Order is Confirmed & you can see further details in My Orders",
                Toast.LENGTH_LONG).show();
        finish();
    }

    private boolean validateOrderDetails(String nameorder, String addressorder, String landmarkorder, String contectorder) {
        if (TextUtils.isEmpty(nameorder)) {
            name.setError("Please Enter Name");
        } else if (TextUtils.isEmpty(addressorder)) {
            address.setError("Please Enter full Address");
        } else if (TextUtils.isEmpty(landmarkorder)) {
            landmark.setError("Please Enter LandMark");
        } else if (!checkPhone(contectorder)) {
            contactno.setError("enter valid phone no.");
        } else
            return true;
        return false;
    }

    private boolean checkPhone(String contectOrder) {
        if (Pattern.matches("[0-9]+", contectOrder) && contectOrder.length() == 10)
            return true;
        return false;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}