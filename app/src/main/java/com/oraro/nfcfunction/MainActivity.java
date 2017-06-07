package com.oraro.nfcfunction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.oraro.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.oraro.nfcfunction.ui.activity.NFCBaseActivity;
import com.oraro.nfcfunction.ui.fragment.FragmentA;
import com.oraro.nfcfunction.ui.fragment.FragmentB;
import com.oraro.nfcfunction.ui.fragment.FragmentC;
import com.oraro.nfcfunction.ui.fragment.FragmentD;
import com.oraro.nfcfunction.ui.fragment.FragmentE;
import com.oraro.nfcfunction.ui.fragment.SlidingMenuFragment;
import com.oraro.nfcfunction.utils.CustomFragmentManager;
import com.oraro.nfcfunction.utils.UIUtils;

public class MainActivity extends NFCBaseActivity {

    private SlidingMenu menu;
    public  static boolean isLogin;
    private UIUtils mUiUtils;
    private CustomFragmentManager mCustomFragmentManager;
    private FragmentA fragmentA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
         setContentView(R.layout.activity_main);
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        //设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        /**
         * SLIDING_WINDOW：菜单栏里不包括ActionBar或标题
         * SLIDING_CONTENT：菜单栏里包括ActionBar或标题
         */
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

        // 设置滑动菜单视图的宽度
        mUiUtils = new UIUtils();
        menu.setMenu(R.layout.left_menu_frame);
            menu.setBehindOffset(mUiUtils.getDisplayMetrics(this).widthPixels * 4 / 5);
            SlidingMenuFragment mSlidingMenuFragment = new SlidingMenuFragment();
            // 将侧滑栏的mSlidingMenuFragment类填充到侧滑栏的容器的布局文件中
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.id_left_menu_frame, mSlidingMenuFragment, "Slidingmenu");
            transaction.commit();
          CustomFragmentManager.setCustomFragmentManagerNull();
         mCustomFragmentManager = CustomFragmentManager.getInstance(this);
         mCustomFragmentManager.setShowViewId(R.id.frame_1);
//        }
            fragmentA = new FragmentA();
            FragmentB fragmentB=new FragmentB();
            mCustomFragmentManager.addFragment(fragmentB);
            FragmentC fragmentC =new FragmentC();
            mCustomFragmentManager.addFragment(fragmentC);
            FragmentD fragmentD=new FragmentD();
            mCustomFragmentManager.addFragment(fragmentD);
            FragmentE fragmentE=new FragmentE();
            mCustomFragmentManager.addFragment(fragmentE);
            mCustomFragmentManager.setMainFragment(fragmentA);
            mCustomFragmentManager.startFragment(fragmentA);
    }

    public  void  hidingmenu() {
        menu.toggle();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
      String  uid = techMap.get("uid").replace(":", " ");
        Log.e("jw","uid=="+uid);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCustomFragmentManager.destory();
    }

    /*
      * 判断网页是否能返回 ,不能返回的话连续两次退出键 退出程序
      */
//    @Override
//    public void onBackPressed() {
//        // 判断是否可退
//        if (webView.canGoBack()) {
//            webView.goBack();
//            // 也可以在其中更改其他按钮状态
//        } else {
//            if (!isExit) {
//                isExit = true;
//                Toast.makeText(getApplicationContext(), "再按一次退出程序",
//                        Toast.LENGTH_LONG).show();
//                // 2s判定
//                handler.sendEmptyMessageDelayed(0, 2000);
//            } else {
//                finish();
//                System.exit(0);
//            }
//        }
//    }

//    // 菜单
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add(0, FIRST, 1, "关于");
//        menu.add(0, SECOND, 2, "退出");
//        return true;
//    }

}
