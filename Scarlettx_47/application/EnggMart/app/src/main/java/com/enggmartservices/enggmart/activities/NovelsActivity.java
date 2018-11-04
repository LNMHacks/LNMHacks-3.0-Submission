package com.enggmartservices.enggmart.activities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.enggmartservices.enggmart.R;
import com.enggmartservices.enggmart.adapers.NovelsAdapter;
import com.enggmartservices.enggmart.models.ModelNovels;
import com.enggmartservices.enggmart.utility.Utils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class NovelsActivity extends AppCompatActivity {
    private RecyclerView simpleList;
    private List<ModelNovels> listItemsNovels;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novels);

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
        setTitle("Novels");
        Utils.darkenStatusBar(this, R.color.colorPrimary);
        simpleList = findViewById(R.id.novels_list_view);
        listItemsNovels = new ArrayList<>();
        // set a GridLayoutManager with 3 number of columns , horizontal gravity and false value for reverseLayout to show the items from start to end
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);
        simpleList.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        listItemsNovels.clear();


        mDatabase = FirebaseDatabase.getInstance().getReference().child("novel");

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.hasChildren()) {
                    Log.e("hello", dataSnapshot.toString());
                    Map map = (Map) dataSnapshot.getValue();
                    String itemImage = map.get("img").toString();
                    String itemPdf = map.get("pdf").toString();
                    ModelNovels modelNovels = new ModelNovels();
                    modelNovels.setItemImage(itemImage);
                    modelNovels.setPdfFile(itemPdf);
                    listItemsNovels.add(modelNovels);
                    //  call the constructor of CustomAdapterStore to send the reference and data to Adapter
                    NovelsAdapter novelsAdapter = new NovelsAdapter(NovelsActivity.this, listItemsNovels);
                    simpleList.setAdapter(novelsAdapter); // set the Adapter to RecyclerView
                } else {
                    Toast.makeText(NovelsActivity.this, "no Items", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
