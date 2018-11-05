package com.enggmartservices.enggmart.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.enggmartservices.enggmart.R;
import com.enggmartservices.enggmart.utility.Utils;

public class NewsPaperActivity extends AppCompatActivity {
    private LinearLayout b11, b12, b13, b14, b15, b16, b17, b18, b19;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newspaper);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Utils.darkenStatusBar(this, R.color.colorPrimary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setTitle("Newspaper");


        b11 = findViewById(R.id.first);
        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.timesofindia.indiatimes.com/"));
                startActivity(i);
            }
        });

        b12 = findViewById(R.id.second);
        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.hindustantimes.com/"));
                startActivity(i);

            }
        });
        b13 = findViewById(R.id.third);
        b13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.thehindu.com/"));
                startActivity(i);

            }
        });
        b14 = findViewById(R.id.fourth);
        b14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bbc.com/"));
                startActivity(i);

            }
        });
        b15 = findViewById(R.id.fifth);
        b15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.livehindustan.com/"));
                startActivity(i);

            }
        });
        b16 = findViewById(R.id.sixth);
        b16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.jagran.com/"));
                startActivity(i);

            }
        });
        b17 = findViewById(R.id.seventh);
        b17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bhaskar.com/"));
                startActivity(i);

            }
        });
        b18 = findViewById(R.id.eight);
        b18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.patrika.com/"));
                startActivity(i);

            }
        });
        b19 = findViewById(R.id.ninth);
        b19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.indianexpress.com/"));
                startActivity(i);

            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
