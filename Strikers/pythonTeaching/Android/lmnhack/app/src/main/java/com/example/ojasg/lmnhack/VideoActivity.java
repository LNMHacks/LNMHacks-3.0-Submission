package com.example.ojasg.lmnhack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("https://www.youtube.com/watch?v=kLZuut1fYzQ");
    }
}
