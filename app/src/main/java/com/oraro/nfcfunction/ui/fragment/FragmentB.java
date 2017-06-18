package com.oraro.nfcfunction.ui.fragment;

import android.annotation.SuppressLint;
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
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.oraro.nfcfunction.MainActivity;
import com.oraro.nfcfunction.R;
import com.oraro.nfcfunction.ui.view.ProgressWebView;
import com.oraro.nfcfunction.utils.Constants;
import com.oraro.nfcfunction.utils.CustomFragmentManager;

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
    private CustomFragmentManager mCustomFragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_b,container,false);
        mCustomFragmentManager = CustomFragmentManager.getInstance(getActivity());
        initView(view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
       mainActivity= (MainActivity) activity;
    }

    @SuppressLint("JavascriptInterface")
    private void initView(View view) {

        webView = (ProgressWebView) view.findViewById(R.id.webview_b);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JavascriptInterface(),"androidToast");
        webView.loadUrl(Constants.SERVICE_IP+"horizon-web/horizon/workflow/support/open.wf?flowId=wtdsp");
//        webView.loadUrl(Constants.SERVICE_IP+"horizon-web/horizon/workflow/support/open.wf?flowId=wtdsp&formId=HZ2889e95b21ffc6015b220cbf3c000b");
//        webView.loadUrl(Constants.SERVICE_IP+"horizon-web/horizon/template/form/default.wf?flowId=wtdsp&formId=HZ2889e95b21ffc6015b220cbf3c000b");
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);
        webView.setWebViewClient(new WebViewClient());
//             @Override
//             public void onPageFinished(WebView view, String url) {
////                 super.onPageFinished(view, url);
//                     Log.e("zxl","zxl--->finish--->"+url+"--->"+("http://192.168.9.4:8080/horizon-web/horizon/basics/getBasics.wf".equals(url))+"--->"+((Constants.SERVICE_IP+"horizon-web//horizon/workflow/support/open.wf?flowId=wtdsp").equals(url)));
//                 if("http://192.168.9.4:8080/horizon-web/horizon/basics/getBasics.wf".equals(url)){
//                     Log.e("jw","sssdiao用0");
//
//                     webView.loadUrl(Constants.SERVICE_IP+"horizon-web//horizon/workflow/support/open.wf?flowId=wtdsp");
//                 }else if((Constants.SERVICE_IP+"horizon-web//horizon/workflow/support/open.wf?flowId=wtdsp").equals(url)){
//                     Log.e("jw","sssdiao用");
////                    webView.loadUrl("javascript: openNewEntrust()");
//                     webView.loadUrl("javascript: entrust_readrfid('123')");
//
//                 }
//
//             }
//         });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("jw","lllllll");
        webView.loadUrl("javascript: entrust_readrfid('" +  mainActivity.uid + "')");
    }

    private class JavascriptInterface {
        @android.webkit.JavascriptInterface
        public void showToast() {
            if (mCustomFragmentManager.getSize() > 1) {
                mCustomFragmentManager.finishFragment();

            }
        }
        public  void showtalk(){
            Toast.makeText(mainActivity,"提交成功",Toast.LENGTH_SHORT).show();
        }
    }
}
