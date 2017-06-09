package com.oraro.nfcfunction.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import com.oraro.nfcfunction.R;
import com.oraro.nfcfunction.ui.view.ProgressWebView;
import com.oraro.nfcfunction.utils.Constants;

/**
 * Created by Administrator on 2017/6/2 0002.
 */
public class FragmentA extends Fragment {

    private ProgressWebView webView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_a,container,false);
        initView(view);
        return view;


    }

    private void initView(View view) {
        webView = (ProgressWebView) view.findViewById(R.id.webview_a);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.loadUrl(Constants.SERVICE_IP+"horizon-web");
        String uri=webView.getUrl();

        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//
//                return true;
//            }
//        });
    }
}
