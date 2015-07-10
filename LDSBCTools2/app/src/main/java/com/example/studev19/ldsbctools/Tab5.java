package com.example.studev19.ldsbctools;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by studev19 on 5/18/2015.
 */
public class Tab5 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_5, container, false);
        WebView myWebView = (WebView) v.findViewById(R.id.webView);
        myWebView.loadUrl("https://docs.google.com/a/ldsbc.edu/forms/d/1PGgxSl2w9vsp4cq5jxii5Y6AOgCfCJ75o527xOrXD4U/viewform");
        return v;
    }

}
