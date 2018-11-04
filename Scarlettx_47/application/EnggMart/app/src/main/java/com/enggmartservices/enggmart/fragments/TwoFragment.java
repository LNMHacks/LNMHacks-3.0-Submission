package com.enggmartservices.enggmart.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.user.enggmart.R;


public class TwoFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_two, container, false);
        WebView wb = (WebView) rootView.findViewById(R.id.webview);
        wb.setWebViewClient(new Browser());
        String url = "https://www.indiabix.com";
        wb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wb.loadUrl(url);


        return rootView;
    }

    private class Browser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}

