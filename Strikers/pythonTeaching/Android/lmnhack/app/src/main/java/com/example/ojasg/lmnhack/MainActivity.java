package com.example.ojasg.lmnhack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
        final Socket socket;
        try{
            socket = IO.socket("http://3.112.1.36:80");

            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    Log.e("ojas","yeah");
                    // socket.emit("message", "hi");
                    // socket.disconnect();
                }

            }).on("python", new Emitter.Listener() {
                //message is the keyword for communication exchanges
                @Override
                public void call(Object... args) {
                    Log.e("python","true");
                    Intent intent = new Intent(MainActivity.this,VideoActivity.class);
                    startActivity(intent);
                    //socket.emit("message", "hi");
                }

            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {}

            });
            socket.connect();

        }
        catch(Exception e){

        }

    }
}
