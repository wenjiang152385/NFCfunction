package com.oraro.nfcfunction.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
public class FragmentC extends Fragment {

    private ProgressWebView webView;
    private MainActivity mainActivity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view= inflater.inflate(R.layout.fragment_c,container,false);
            initView(view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity= (MainActivity) activity;

    }

    private void initView(View view) {
        webView = (ProgressWebView) view.findViewById(R.id.webview_c);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.loadUrl(Constants.SERVICE_IP+"horizon-web/horizon/template/form/default.wf?formId=HZ2889a25ae97484015aea3febd90038");
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);
        String uri= webView.getUrl();

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("jw","ccccc");
        webView.loadUrl("javascript: html_readrfid('" +  mainActivity.uid + "')");
    }
}
