package com.ndstudio.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Switch;

import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Nishchhal on 18-Jun-16.
 */
public class Profile extends AppCompatActivity implements View.OnClickListener
{
    String username;
    private TextView state;
    private TextView crop;
    private TextView soil;
    private TextView landArea;
    private TextView stage;
    private TextView day;
    private TextView soilMoist;
    private TextView temp;
    private TextView humidity;
    private TextView weather;
    FloatingActionButton refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        state =  findViewById(R.id.state);
        crop =  findViewById(R.id.crop);
        soil =  findViewById(R.id.soil);
        landArea =  findViewById(R.id.landArea);
        stage =  findViewById(R.id.stage);
        day =  findViewById(R.id.day);
        soilMoist =  findViewById(R.id.soilMoisture);
        temp =  findViewById(R.id.temperature);
        humidity =  findViewById(R.id.humidity);
        weather =  findViewById(R.id.weather);
        refresh =  findViewById(R.id.refresh);
        refresh.setOnClickListener(this);
        refresh.performClick();

    }


    @Override


    public void onClick(View v)
    {
        String username1 = new String(username+"_tech");
        String username2 = new String(username+"_rt");
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://35.154.170.171:3306/aries?user=root&password=");

            Statement st1 = con.createStatement();
            Statement st2 = con.createStatement();

            ResultSet rs1 = st1.executeQuery("select * from "+username1+" order by id desc limit 1");
            ResultSet rs2 = st2.executeQuery("select * from "+username2+" order by id desc limit 1");
            rs1.next();
            rs2.next();
                state.setText("State       :   "+ rs1.getString(2));
                 crop.setText("Crop        :   "+ rs1.getString(3));
                stage.setText("Stage      :   "+ rs2.getString(5));
             landArea.setText("Land Area :   "+ rs1.getString(5));
                 soil.setText("Soil        :   "+ rs1.getString(6));
                  day.setText("Days       :   "+ rs2.getString(6));
            soilMoist.setText("Soil Moist :   "+ rs2.getString(2));
                 temp.setText("Temp.      :  "+ rs2.getString(3));
             humidity.setText("Humidity  :  "+ rs2.getString(4));
              weather.setText("Weather   :  "+ rs2.getString(7));
            Toast.makeText(Profile.this, "Refreshed", Toast.LENGTH_SHORT).show();
            rs1.close();
            st1.close();
            st2.close();
            rs2.close();
            con.close();
        }
        catch(Exception e)
        {
            Toast.makeText(Profile.this, "Error Occured", Toast.LENGTH_SHORT).show();
        }
    }


}
