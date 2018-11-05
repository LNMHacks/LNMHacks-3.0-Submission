package com.enggmartservices.enggmart.activities;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Handler;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.enggmartservices.enggmart.R;
import com.enggmartservices.enggmart.utility.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2500;
    private LinearLayout animat;
    private FirebaseAuth userAuth;
    private DatabaseReference version;
    private DatabaseReference versionLink;
    //final String appPackageName = PackageManager.g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Utils.darkenStatusBar(this, R.color.textColorPrimary);
        Animation a = AnimationUtils.loadAnimation(this, R.anim.fade_in_logo);
        a.reset();
        animat = (LinearLayout) findViewById(R.id.anim);
        animat.clearAnimation();
        animat.startAnimation(a);
        userAuth = FirebaseAuth.getInstance();
        version = FirebaseDatabase.getInstance().getReference().child("version").child("number");

    }

    @Override
    protected void onStart() {
        super.onStart();

        version.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals("1.0.0")) {

                    final FirebaseUser currentUser = userAuth.getCurrentUser();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (currentUser == null) {
                                Intent i = new Intent(SplashScreen.this, SignInSignUp.class);
                                startActivity(i);
                                overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
                                finish();
                            } else {

                                startActivity(new Intent(SplashScreen.this, HomeActivity.class));
                                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                                finish();
                            }
                        }
                    }, SPLASH_TIME_OUT);
                } else {
                    updateApp();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void updateApp() {
        versionLink = FirebaseDatabase.getInstance().getReference().child("version").child("link");


        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("New Version of EnggMart is Available")
                .setPositiveButton("update Application", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        versionLink.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue() != null) {
                                    // String link = dataSnapshot.getValue().toString();
                                    // Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        Toast.makeText(SplashScreen.this, "updating", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SplashScreen.this, "Please Update", Toast.LENGTH_SHORT).show();
                        Process.killProcess(Process.myPid());
                    }
                })
                .setIcon(R.mipmap.ic_launcher)
                .show();


    }
}