package com.oraro.nfcfunction.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.Toast;

import com.oraro.nfcfunction.MainActivity;
import com.oraro.nfcfunction.R;
import com.oraro.nfcfunction.ui.view.ProgressWebView;
import com.oraro.nfcfunction.utils.Constants;

/**
 * Created by Administrator on 2017/6/2 0002.
 */
public class FragmentB extends Fragment {

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
       View view= inflater.inflate(R.layout.fragment_b,container,false);
        initView(view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
       mainActivity= (MainActivity) activity;
    }

    private void initView(View view) {

        webView = (ProgressWebView) view.findViewById(R.id.webview_b);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.loadUrl(Constants.SERVICE_IP+"horizon-web/horizon/template/form/default.wf?formId=HZ2889e95b21ffc6015b220cbf3c000b");
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);
        String uri=webView.getUrl();
        Log.e("jw","uilb=="+uri);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("jw","lllllll");
        webView.loadUrl("javascript: entrust_readrfid('" +  mainActivity.uid + "')");
    }
    public void goback(){
        // 判断是否可退
        if (webView.canGoBack()) {
            webView.goBack();
            // 也可以在其中更改其他按钮状态
        } else {
            if (!isExit) {
                isExit = true;
                Toast.makeText(mainActivity, "再按一次退出程序",
                        Toast.LENGTH_LONG).show();
                // 2s判定
                handler.sendEmptyMessageDelayed(0, 2000);
            } else {
               mainActivity.finish();
                System.exit(0);
            }
        }
    }
}
