package com.enggmartservices.enggmart.activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.enggmartservices.enggmart.R;
import com.enggmartservices.enggmart.utility.Utils;

public class AdminPannel extends AppCompatActivity {
    Button admin_login;
    EditText input_email;
    EditText input_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pannel);
        Utils.darkenStatusBar(this, R.color.colorPrimary);
        input_email = (EditText) findViewById(R.id.Name);
        input_password = (EditText) findViewById(R.id.Password);

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
        setTitle("Only for Admin");
        admin_login = (Button) findViewById(R.id.button);
        admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(input_email.getText().toString(), input_password.getText().toString());
            }
        });


    }


    private void validate(String userName, String userPassword) {
        if ((userName.equals("EMem@12345")) && (userPassword.equals("EMem@*#*#2018"))) {

            customDialog();

        } else {
            Toast.makeText(this, "Oops! Don't try this", Toast.LENGTH_SHORT).show();

        }
    }

    private void customDialog() {
        final Dialog openDialog = new Dialog(AdminPannel.this);
        openDialog.setContentView(R.layout.layout_admin_select);
        openDialog.show();

        RelativeLayout books = openDialog.findViewById(R.id.books_admin_select);
        RelativeLayout novels = openDialog.findViewById(R.id.novel_admin_select);
        RelativeLayout currentAffairs = openDialog.findViewById(R.id.currentaffairs_admin_select);
        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPannel.this, AdminConsole.class);
                startActivity(intent);
                finish();
                openDialog.dismiss();
            }
        });
        novels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPannel.this, UploadNovelAffaires.class);
                intent.putExtra("upload", "novel");
                startActivity(intent);
                finish();
                openDialog.dismiss();
            }
        });
        currentAffairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPannel.this, UploadNovelAffaires.class);
                intent.putExtra("upload", "currentaffairs");
                startActivity(intent);
                finish();
                openDialog.dismiss();
            }
        });
    }
}