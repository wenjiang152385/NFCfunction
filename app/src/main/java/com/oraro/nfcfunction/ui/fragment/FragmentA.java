package com.oraro.nfcfunction.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.oraro.nfcfunction.MainActivity;
import com.oraro.nfcfunction.R;
import com.oraro.nfcfunction.ui.view.MorePopWindows;

/**
 * Created by Administrator on 2017/6/2 0002.
 */
public class FragmentA extends Fragment {
    private WebView webView;
    private EditText webUrltText;
    private Button gotoButton;
    private ChromeClient webChromeClient;
    private WebSettings webSettings;

    // 网页地址和按键布局
    private LinearLayout webUrlLayout;
    private RelativeLayout btnsLayout;

    // 常用按键
    private Button preButton;
    private Button nextButton;
    private Button homeButton;
    private Button tabsButton;
    private Button moreButton;
    private Button moreBtn_normal_refreshButton;
    private Button moreBtn_normal_fullButton;
    private Button fullButton;
    private ProgressBar progressBar;

    // 获取更多按钮弹出窗口
    private MorePopWindows morePopWindows;

    // 网址
    private String url = "";

    // 计时功能
    private static boolean isExit = false;
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    private MyWebViewClient myWebViewClient;
    // 手势监听
    private GestureListener mGestureListener;
    private GestureDetector mGestureDetector;

    // 监听器
    private BtnClickedListener btnClickedListener;
    private WebUrlChangedListener webUrlChangedListener;
    private WebViewTouchListener webViewTouchListener;

    // 菜单
    private static int FIRST = Menu.FIRST;
    private static int SECOND = Menu.FIRST + 1;
    private MainActivity mainActivity;
    private Context mcontext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.activity_home,container,false);
        initView(view);


        // 将webview作为默认的网页显示
        myWebViewClient = new MyWebViewClient();
        webView.setWebViewClient(myWebViewClient);

        // 加载进度条,百分之百自动隐藏
        webChromeClient = new ChromeClient();
        webView.setWebChromeClient(webChromeClient);

        // 获得配置,并设置
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // 设置基本的配置
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);

        // 为进入按键设置事件
        btnClickedListener = new BtnClickedListener();
        gotoButton.setOnClickListener(btnClickedListener);

        // 获得webview的触摸
        webViewTouchListener = new WebViewTouchListener();
        webView.setOnTouchListener(webViewTouchListener);

        // 将触摸交给GestureListener处理
        mGestureListener = new GestureListener();
        mGestureDetector = new GestureDetector(getActivity(), mGestureListener);

        // 为地址栏添加绑定事件,检查地址是否合法,以及自动补全
        webUrlChangedListener = new WebUrlChangedListener();
        webUrltText.addTextChangedListener(webUrlChangedListener);

        preButton.setEnabled(false);
        nextButton.setEnabled(false);

        // 为常用按键绑定Listener
        preButton.setOnClickListener(btnClickedListener);
        nextButton.setOnClickListener(btnClickedListener);
        homeButton.setOnClickListener(btnClickedListener);
        tabsButton.setOnClickListener(btnClickedListener);
        moreButton.setOnClickListener(btnClickedListener);

        fullButton.setOnClickListener(btnClickedListener);

        progressBar.setVisibility(View.GONE);

        this.morePopWindows = new MorePopWindows(mcontext, mainActivity.getWindowManager()
                .getDefaultDisplay().getWidth() - 30, mainActivity.getWindowManager()
                .getDefaultDisplay().getHeight() / 3);
        return view;


    }
    /*
	 * 按钮事件,用来判断输入是否合法,不合法警告
	 */
    private class BtnClickedListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 得到"转入"按键
            if (v.getId() == R.id.GotoBtn) {
                String url = webUrltText.getText().toString();
                // 判断是否合法
                if (URLUtil.isNetworkUrl(url) && URLUtil.isValidUrl(url)) {
                    webView.loadUrl(url);
                } else {
                    // 弹出对话框提示用户
                    new AlertDialog.Builder(getActivity())
                            .setTitle("警告")
                            .setMessage("不是有效的网址!" + "\n" + "请返回重新输入~")
                            .setPositiveButton("返回",
                                    new AlertDialog.OnClickListener() {
                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {
                                            dialog.dismiss();
                                        }
                                    }).create().show();
                }
            }
            // 得到返回按键
            else if (v.getId() == R.id.Pre_Btn) {
                if (webView.canGoBack()) {
                    webView.goBack();
                }
            } else if (v.getId() == R.id.Next_Btn) {
                if (webView.canGoForward()) {
                    webView.goForward();
                }
            } else if (v.getId() == R.id.Home_Btn) {
                webView.loadUrl("http://www.baidu.com");
            } else if (v.getId() == R.id.Tabs_Btn) {
                Toast.makeText(mcontext, "仍在开发中...",
                        Toast.LENGTH_SHORT).show();
            } else if (v.getId() == R.id.More_Btn) {
                LayoutInflater toolsInflater = LayoutInflater
                        .from(mcontext);
                View toolsView = toolsInflater.inflate(R.layout.activity_tabs,
                        null);
                morePopWindows.showAtLocation(toolsView, Gravity.BOTTOM
                                | Gravity.CENTER_HORIZONTAL, 0,
                        moreButton.getHeight() + 20);
                moreBtn_normal_refreshButton = (Button) morePopWindows
                        .getView(R.id.more_normal_refresh);
                moreBtn_normal_fullButton = (Button) morePopWindows
                        .getView(R.id.more_normal_full);
                moreBtn_normal_refreshButton.setOnClickListener(this);
                moreBtn_normal_fullButton.setOnClickListener(this);
            } else if (v.getId() == R.id.more_normal_refresh) {
                if (!(url.equals("") && url.equals("http://"))) {
                    webView.loadUrl(url);
                }
            } else if (v.getId() == R.id.more_normal_full) {
                webUrlLayout.setVisibility(View.GONE);
                btnsLayout.setVisibility(View.GONE);
                fullButton.setVisibility(View.VISIBLE);
            } else if (v.getId() == R.id.Full_Btn) {
                webUrlLayout.setVisibility(View.VISIBLE);
                btnsLayout.setVisibility(View.VISIBLE);
                fullButton.setVisibility(View.GONE);
            }

        }
    }
    /*
	 * TextWatcher 实时检测url的合法性
	 */
    private class WebUrlChangedListener implements TextWatcher {

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

            url = s.toString();
            if (!(url.startsWith("http://")) || (url.startsWith("https://"))) {
                url = "http://" + url;
            }
            if (URLUtil.isNetworkUrl(url) && URLUtil.isValidUrl(url)) {

                // changeStatueOfWebUrlGotoBtn(true);
            } else {
                // changeStatueOfWebUrlGotoBtn(false);
            }
        }
    }
    /*
	 * 通过自己的webView来显示所有网页,需要override WebViewClient
	 */
    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        // 加载完成后隐藏地址栏
        @Override
        public void onPageFinished(WebView view, String url) {

            super.onPageFinished(view, url);
            webUrltText.setText(url);
            // webUrlLayout.setVisibility(View.GONE);
            StatueOfPreAndNextBtns();
        }

    }
    /*
 * 设置是否可以点击向前向后按钮
 */
    private void StatueOfPreAndNextBtns() {
        // 可返回,返回按钮可点击,否则不可以
        if (webView.canGoBack()) {
            preButton.setEnabled(true);
        } else {
            preButton.setEnabled(false);
        }
        // 可进入,进入按钮可点击,否则不可以
        if (webView.canGoForward()) {
            nextButton.setEnabled(true);
        } else {
            nextButton.setEnabled(false);
        }
    }
    /*
 * 手势的实现 上滑到顶部显示地址栏,向下滑到底部,隐藏地址栏
 */
    private class GestureListener implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            if (webView.getScrollY() == 0) {
                // 滑倒顶部
                webUrlLayout.setVisibility(View.VISIBLE);
            }
            if (webView.getScrollY() > 0) {
                // 滑倒底部
                webUrlLayout.setVisibility(View.GONE);
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }
    }
    /*
	 * 自定义oTouchListener 将手势交给GestureListener解决
	 */

    private class WebViewTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (v.getId() == R.id.webview) {
                return mGestureDetector.onTouchEvent(event);
            }
            return false;
        }
    }
    /*
 * WebChromeClient自定义继承类 override onProgressChanged
 */
    private class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                progressBar.setVisibility(view.GONE);
            } else {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
            }
        }
    }
//    /*
// * 判断网页是否能返回 ,不能返回的话连续两次退出键 退出程序
// */
//    @Override
//    public void onBackPressed() {
//        // 判断是否可退
//        if (webView.canGoBack()) {
//            webView.goBack();
//            // 也可以在其中更改其他按钮状态
//        } else {
//            if (!isExit) {
//                isExit = true;
//                Toast.makeText(mcontext, "再按一次退出程序",
//                        Toast.LENGTH_LONG).show();
//                // 2s判定
//                handler.sendEmptyMessageDelayed(0, 2000);
//            } else {
//                finish();
//                System.exit(0);
//            }
//        }
//    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity= (MainActivity) activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
      mcontext=context;
    }

    private void initView(View view) {
        webView = (WebView) view.findViewById(R.id.webview);
        webUrltText = (EditText)view. findViewById(R.id.web_Url_addr);
        gotoButton = (Button) view.findViewById(R.id.GotoBtn);
        webUrlLayout = (LinearLayout)view. findViewById(R.id.web_Url_Layout);
        btnsLayout = (RelativeLayout)view. findViewById(R.id.Btns_Layout);
        preButton = (Button) view.findViewById(R.id.Pre_Btn);
        nextButton = (Button) view.findViewById(R.id.Next_Btn);
        homeButton = (Button) view.findViewById(R.id.Home_Btn);
        tabsButton = (Button) view.findViewById(R.id.Tabs_Btn);
        moreButton = (Button) view.findViewById(R.id.More_Btn);
        fullButton = (Button)view. findViewById(R.id.Full_Btn);
        progressBar = (ProgressBar)view. findViewById(R.id.webProgressBar);
    }
}
