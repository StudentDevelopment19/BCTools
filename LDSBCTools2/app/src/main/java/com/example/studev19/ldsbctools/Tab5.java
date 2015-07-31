package com.example.studev19.ldsbctools;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


/**
 * Created by studev19 on 5/18/2015.
 */
public class Tab5 extends Fragment {

    private static boolean connectionStatus;
    private static SwipeRefreshLayout webSwipe;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_5, container, false);
        webSwipe = (SwipeRefreshLayout) v.findViewById(R.id.swipeWeb);
        webSwipe.setColorSchemeResources(R.color.primaryColor, R.color.accentColor);
        final WebView WEB_VIEW = (WebView) v.findViewById(R.id.webView);
        WEB_VIEW.getSettings().setJavaScriptEnabled(true);

        if (connectionStatus == false){
            String html = "<html><body><p>You must be connected to the internet to display this tab correctly.</p></body></html>";
            String mime = "text/html";
            String encoding = "utf-8";

            WEB_VIEW.loadDataWithBaseURL(null, html, mime, encoding, null);
        }
        else {
            WEB_VIEW.loadUrl("https://docs.google.com/a/ldsbc.edu/forms/d/1PGgxSl2w9vsp4cq5jxii5Y6AOgCfCJ75o527xOrXD4U/viewform");
        }

        webSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webSwipe.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        webSwipe.setRefreshing(false);
                        WEB_VIEW.loadUrl("https://docs.google.com/a/ldsbc.edu/forms/d/1PGgxSl2w9vsp4cq5jxii5Y6AOgCfCJ75o527xOrXD4U/viewform");
                    }
                }, 4000);
            }
        });

        return v;
    }

    public static void setConnectionStatus(boolean status){
        connectionStatus = status;
    }

}
