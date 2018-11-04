package com.enggmartservices.enggmart.activities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.enggmartservices.enggmart.R;
import com.enggmartservices.enggmart.adapers.CurrentAffairsAdapter;
import com.enggmartservices.enggmart.models.ModelCurrentAffairs;
import com.enggmartservices.enggmart.utility.Utils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CurrentAffairsActivity extends AppCompatActivity {
    private RecyclerView simpleList;
    private List<ModelCurrentAffairs> listItemsCurrentAffaires;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_affairs_layout);
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
        setTitle("Current Affairs");


        Utils.darkenStatusBar(this, R.color.colorPrimary);


        simpleList = findViewById(R.id.current_affairs_list_view);
        listItemsCurrentAffaires = new ArrayList<>();
        simpleList.setLayoutManager(new LinearLayoutManager(this));
        listItemsCurrentAffaires.clear();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("currentaffairs");

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.hasChildren()) {
                    Log.e("hello", dataSnapshot.getValue() + "");
                    Map map = (Map) dataSnapshot.getValue();
                    Log.e("hello", map.get("date").toString() + "");
                    Log.e("hello", map.get("pdf").toString() + "");

                    String itemDate = map.get("date").toString();
                    String itemPdf = map.get("pdf").toString();
                    ModelCurrentAffairs modelCurrentAffairs = new ModelCurrentAffairs();
                    modelCurrentAffairs.setItemDate(itemDate);
                    modelCurrentAffairs.setPdfFile(itemPdf);

                    listItemsCurrentAffaires.add(modelCurrentAffairs);
                    //  call the constructor of CustomAdapterStore to send the reference and data to Adapter
                    CurrentAffairsAdapter currentAffairsAdapter = new CurrentAffairsAdapter(CurrentAffairsActivity.this, listItemsCurrentAffaires);
                    simpleList.setAdapter(currentAffairsAdapter); // set the Adapter to RecyclerView

                } else {
                    Toast.makeText(CurrentAffairsActivity.this, "no Items", Toast.LENGTH_SHORT).show();
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
        finish();
    }
}
