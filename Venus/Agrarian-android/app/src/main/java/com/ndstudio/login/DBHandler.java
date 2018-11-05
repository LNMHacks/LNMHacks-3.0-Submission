package com.ndstudio.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/**
 * Created by Nishchhal on 10-Jun-16.
 */
public class DBHandler {

    public static final String DB_UNAME = "username";
    public static final String DB_UPASS = "password";
    private Connection con;
    private Statement st;
    public Context context;

    DBHandler(Context context)
    {
    }


    public boolean checkEntry(String username,String password)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://35.154.170.171:3306/aries?user=root&password=");

            st = con.createStatement();

            ResultSet rs = st.executeQuery("select password from authentication where username='"+username+"'");
            rs.next();
            if (password.equals(rs.getString(1)))
            {
                rs.close();
                st.close();
                con.close();
                return true;
            }

        }
        catch(Exception e)
        {
        }
        return false;
    }



}
