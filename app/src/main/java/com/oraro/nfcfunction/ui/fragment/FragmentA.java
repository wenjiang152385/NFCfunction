package com.oraro.nfcfunction.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import com.oraro.nfcfunction.MainActivity;
import com.oraro.nfcfunction.R;
import com.oraro.nfcfunction.ui.view.ProgressWebView;
import com.oraro.nfcfunction.utils.Constants;

/**
 * Created by Administrator on 2017/6/2 0002.
 */
public class FragmentA extends Fragment {

    private ProgressWebView webView;
    private MainActivity mainActivity;
    // 计时功能
    private static boolean isExit = false;
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_a,container,false);
        initView(view);
        return view;


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity= (MainActivity) activity;

    }

    private void initView(View view) {
        webView = (ProgressWebView) view.findViewById(R.id.webview_a);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.loadUrl(Constants.SERVICE_IP+"horizon-web");
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);
    }

}
