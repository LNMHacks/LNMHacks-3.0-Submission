package com.enggmartservices.enggmart.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.enggmartservices.enggmart.R;
import com.enggmartservices.enggmart.models.StoreModel;
import com.enggmartservices.enggmart.adapers.CustomAdapterStore;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ThreeFragment extends Fragment {

    private RecyclerView mAllItemsListView;
    private List<StoreModel> listItemsStore;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // get the reference of RecyclerView
        View rootView = inflater.inflate(R.layout.fragment_three, container, false);
        mAllItemsListView = rootView.findViewById(R.id.recycler_view_store);
        listItemsStore = new ArrayList<>();
        // set a GridLayoutManager with 3 number of columns , horizontal gravity and false value for reverseLayout to show the items from start to end
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        mAllItemsListView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

        listItemsStore.clear();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("storeDetails");

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.hasChildren()) {
                    Log.e("hello", dataSnapshot.toString());
                    String itemID = dataSnapshot.getKey();
                    Map map = (Map) dataSnapshot.getValue();
                    String itemName = map.get("itemName").toString();
                    String itemPrice = map.get("itemPrice").toString();
                    String itemDescription = map.get("itemDescription").toString();
                    String itemImage = map.get("itemImage").toString();
                    StoreModel storeModel = new StoreModel();
                    storeModel.setItemID(itemID);
                    storeModel.setItemName(itemName);
                    storeModel.setItemPrice(itemPrice);
                    storeModel.setItemDescription(itemDescription);
                    storeModel.setItemImage(itemImage);
                    listItemsStore.add(storeModel);
                    //  call the constructor of CustomAdapterStore to send the reference and data to Adapter
                    CustomAdapterStore customAdapter = new CustomAdapterStore(getActivity(), listItemsStore);
                    mAllItemsListView.setAdapter(customAdapter); // set the Adapter to RecyclerView

                } else {
                    Toast.makeText(getActivity(), "no Items", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

        return rootView;
    }

}

