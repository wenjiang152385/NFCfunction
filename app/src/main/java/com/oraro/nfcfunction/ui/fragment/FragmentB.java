package com.oraro.nfcfunction.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.oraro.nfcfunction.MainActivity;
import com.oraro.nfcfunction.R;
import com.oraro.nfcfunction.common.App;
import com.oraro.nfcfunction.common.DataTransfer;
import com.oraro.nfcfunction.ui.view.ProgressWebView;
import com.oraro.nfcfunction.utils.Constants;
import com.oraro.nfcfunction.utils.CustomFragmentManager;
import com.senter.support.openapi.StUhf;

/**
 * Created by Administrator on 2017/6/2 0002.
 */
public class FragmentB extends Fragment {

    private ProgressWebView webView;
    private MainActivity mainActivity;
    private CustomFragmentManager mCustomFragmentManager;
    Handler handler=new Handler();
    ContinuousInventoryListener	workerLisener=new ContinuousInventoryListener()
    {

        @Override
        public void onTagInventory(StUhf.UII uii, StUhf.InterrogatorModelDs.UmdFrequencyPoint frequencyPoint, Integer antennaId, StUhf.InterrogatorModelDs.UmdRssi rssi)
        {
            Log.e("jw","eeeeee");
            addNewUiiMassageToListview(uii);
        }

        @Override
        public void onFinished()
        {
            Log.e("jw","ffff");

        }
    };
    private String ui;

    private void addNewUiiMassageToListview(StUhf.UII uii) {
        ui = DataTransfer.xGetString(uii.getEpc().getBytes());
        Log.e("jw","uii1="+ ui);
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.e("jw","uii2="+ui);
                webView.loadUrl("javascript: entrust_readrfid('" +  ui + "')");
                worker.stopInventory();
            }
        });



    }

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

    @Override
    public void onDestroy() {
        super.onDestroy();
      worker.stopInventory();
    }

    @SuppressLint("JavascriptInterface")
    private void initView(View view) {

        webView = (ProgressWebView) view.findViewById(R.id.webview_b);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JavascriptInterface(),"androidToast");
//        webView.loadUrl(Constants.SERVICE_IP+"horizon-web/horizon/workflow/support/open.wf?flowId=wtdsp&formId=HZ2889e95b21ffc6015b220cbf3c000b");
//        webView.loadUrl(Constants.SERVICE_IP+"horizon-web/horizon/template/form/default.wf?flowId=wtdsp&formId=HZ2889e95b21ffc6015b220cbf3c000b");
//        webView.setFocusable(true);
//        webView.setFocusableInTouchMode(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {


                Log.e("jw","pfffpfffurl=="+url);
//                if (worker.isInventroing())
//                {
//                    worker.stopInventory();
//                } else
//                {
//                }
//                    worker.startInventory();

//                if("http://192.168.9.4:8080/horizon-web/horizon/workflow/support/open.wf?flowId=wtdsp".equals(url)){
//                    Log.e("jw","192.168.9.4");
//                    view.loadUrl("javascript: entrust_readrfid('1234')");
//                }
            }
        });

        webView.loadUrl(Constants.SERVICE_IP+"horizon-web/horizon/workflow/support/open.wf?flowId=wtdsp");

    }

    ContinuousInventoryWorker	worker = new ContinuousInventoryWorker(workerLisener);
    private interface ContinuousInventoryListener
    {
        /**
         * will be called on finished completely
         */
        public void onFinished();

        public void onTagInventory(StUhf.UII uii, StUhf.InterrogatorModelDs.UmdFrequencyPoint frequencyPoint, Integer antennaId, StUhf.InterrogatorModelDs.UmdRssi rssi);
    }
    private static class ContinuousInventoryWorker {
        /**
         * go on inventoring after one inventory cycle finished.
         */

        public static boolean goOnInventoring = true;
        private ContinuousInventoryListener mListener = null;

        private boolean isInventoring = false;

        /**
         * @param listener must no be null
         */
        public ContinuousInventoryWorker(ContinuousInventoryListener listener) {
            if (listener == null) {
                throw new NullPointerException();
            }
            mListener = listener;
        }

        public void startInventory() {
            goOnInventoring = true;
            isInventoring = true;

            App.uhfInterfaceAsModelD2().iso18k6cRealTimeInventory(1, new StUhf.InterrogatorModelDs.UmdOnIso18k6cRealTimeInventory() {

                @Override
                public void onFinishedWithError(StUhf.InterrogatorModelDs.UmdErrorCode error) {
                    Log.e("jw", "111");
                    onFinishedOnce();
                }

                @Override
                public void onFinishedSuccessfully(Integer antennaId, int readRate, int totalRead) {
                    Log.e("jw", "222");
                    onFinishedOnce();
                }

                private void onFinishedOnce() {
                    Log.e("jw", "goOnInventoring==" + goOnInventoring);
                    if (goOnInventoring) {
                        startInventory();
                    } else {
                        Log.e("jw", "isInventoring==" + isInventoring);
                        isInventoring = false;
                        mListener.onFinished();
                    }
                }

                @Override
                public void onTagInventory(StUhf.UII uii, StUhf.InterrogatorModelDs.UmdFrequencyPoint frequencyPoint, Integer antennaId, StUhf.InterrogatorModelDs.UmdRssi rssi) {
                   Log.e("jw","tititi");
                    mListener.onTagInventory(uii, frequencyPoint, antennaId, rssi);
                }
            });
        }
        public void stopInventory()
        {
            goOnInventoring = false;
        }

        public boolean isInventroing()
        {
            return isInventoring;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("jw","lllllll");
//        webView.loadUrl("javascript: entrust_readrfid('" +  mainActivity.uid + "')");
    }

    private class JavascriptInterface {
        @android.webkit.JavascriptInterface
        public void showToast() {
            if (mCustomFragmentManager.getSize() > 1) {
                mCustomFragmentManager.finishFragment();

            }
        }
        @android.webkit.JavascriptInterface
        public void loadfinish(){
            Log.e("jw","loadfinish");
            worker.startInventory();
        }
        public  void showtalk(){
            Toast.makeText(mainActivity,"提交成功",Toast.LENGTH_SHORT).show();
        }
    }
}
