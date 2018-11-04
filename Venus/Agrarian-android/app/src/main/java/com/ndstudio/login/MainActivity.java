package com.ndstudio.login;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button signup,login,cancel,forgot;
    private EditText edtUsername,edtPassword;
    //private TextView tv;
    DBHandler dbh;
    public static String username,password;
    String str="new";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        login = (Button)findViewById(R.id.login);
        edtUsername = (EditText)findViewById(R.id.name);
        edtPassword = (EditText)findViewById(R.id.pass);
        //tv = (TextView)findViewById(R.id.tv);

        dbh = new DBHandler(this);

        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(edtUsername.getText().toString().equals(""))
            return;

        username = edtUsername.getText().toString();
        password = edtPassword.getText().toString();

        switch(v.getId())
        {
            case R.id.login :
                    if (password.equals(""))
                    {
                        Toast.makeText(MainActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else
                    {
                        boolean isCheck = dbh.checkEntry(username,password);
                        Intent intent = new Intent(MainActivity.this,Profile.class);
                        if(isCheck==true)
                        {
                            intent.putExtra("username",username);
                            startActivity(intent);
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                    }
                    break;
        }

    }
}
