package com.couponsecure.couponsecure.Views;


import android.location.Location;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.couponsecure.couponsecure.R;
import com.hypertrack.core_android_sdk.HyperTrackCore;
import com.hypertrack.core_android_sdk.callbacks.ErrorResponse;
import com.hypertrack.core_android_sdk.callbacks.SuccessResponse;

/**
 * A simple {@link Fragment} subclass.
 */
public class CouponsNearMe extends Fragment {


    private static final String PUBLISHABLE_KEY = "pk_4436b8bccd82216e45d9d66b256e16445b774e82";

    public CouponsNearMe() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coupons_near_me, container, false);


//        HyperTrack.requestPermissions(this);
//        HyperTrack.requestLocationServices(this);
//        HyperTrack.getCurrentLocation(new HyperTrackCallback() {
//            @Override
//            public void onSuccess(@NonNull SuccessResponse successResponse) {
//                location = (Location) successResponse.getResponseObject();
//                locationTextView.setText("Latitude is: " + location.getLatitude() + "\nLongitude is:" + location.getLongitude());
//            }
//
//            @Override
//            public void onError(@NonNull ErrorResponse errorResponse) {
//                Toast.makeText(LandingActivity.this, "ERROR" + errorResponse.toString(), Toast.LENGTH_LONG).show();
//            }
//        });
//
//    }


//    private void initUIViews() {
//        // Initialize AssignAction Button
//        Button logoutButton = (Button) findViewById(R.id.logout_btn);
//        Button redeemButton = (Button) findViewById(R.id.redeembtn);
//        locationTextView = (TextView) findViewById(R.id.locationTextView);
//
//        if (logoutButton != null)
//            logoutButton.setOnClickListener(logoutButtonClickListener);
//        if (redeemButton != null)
//            redeemButton.setOnClickListener(redeemButtonClickListener);
//    }
//
//    private View.OnClickListener redeemButtonClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent i = new Intent(LandingActivity.this, FinalActivity.class);
//            i.putExtra("lat", location.getLatitude());
//            i.putExtra("long", location.getLongitude());
//            startActivity(i);
//        }
//    };
//    // Click Listener for AssignAction Button
//    private View.OnClickListener logoutButtonClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Toast.makeText(LandingActivity.this, "Successfully logged out",
//                    Toast.LENGTH_SHORT).show();
//
//            // Stop HyperTrack SDK
//            HyperTrack.stopTracking();
//
//            // Proceed to LoginActivity for a fresh User Login
//            Intent loginIntent = new Intent(LandingActivity.this,
//                    MainActivity.class);
//            loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(loginIntent);
//            finish();
//        }
//    };
    }

}
