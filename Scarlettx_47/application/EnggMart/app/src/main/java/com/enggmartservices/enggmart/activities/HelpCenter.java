package com.enggmartservices.enggmart.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.enggmartservices.enggmart.R;

public class HelpCenter extends AppCompatActivity {
    private ImageView hlpCancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);
        hlpCancle = (ImageView) findViewById(R.id.canclehelp);
        hlpCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
