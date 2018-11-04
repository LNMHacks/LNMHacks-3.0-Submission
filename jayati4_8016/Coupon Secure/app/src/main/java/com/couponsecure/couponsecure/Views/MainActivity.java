package com.couponsecure.couponsecure.Views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.couponsecure.couponsecure.R;
import com.hypertrack.core_android_sdk.HyperTrackCore;
import com.hypertrack.core_android_sdk.permissions.LocationPermissionCallback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends Activity {
    int check =0;
    private Button button;
    private EditText phoneNumber;
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         phoneNumber= findViewById(R.id.phn);
         password= findViewById(R.id.password);
        button= (Button) findViewById(R.id.login_btn);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                 if(phoneNumber.getText().toString().equals("7840844065") && password.getText().toString().equals("123456"))
                {
                    Toast.makeText(MainActivity.this,"Successfull",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,CouponDashboardActivity.class));
                }
                else{
                    Toast.makeText(MainActivity.this,"Unsuccessfull",Toast.LENGTH_SHORT).show();

                }

            }
        });

//        HyperTrackCore.requestLocationPermissions(this, new LocationPermissionCallback() {
//            @Override
//            public void onLocationPermissionGranted() {
//                // Handle location permission granted
//            }
//​
//            @Override
//            public void onLocationPermissionDenied() {
//                // Handle location permission denied
//            }
//        });

    }


    @Override
    public void onBackPressed() {

        if(check<1) {
            Toast.makeText(this, "Press to Exit", Toast.LENGTH_SHORT).show();
        check++;
        }
        else
        this.finish();
    }
}
