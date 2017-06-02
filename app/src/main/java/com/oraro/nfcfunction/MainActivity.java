package com.oraro.nfcfunction;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.oraro.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.oraro.nfcfunction.ui.activity.BaseActivity;
import com.oraro.nfcfunction.ui.fragment.FragmentA;
import com.oraro.nfcfunction.ui.fragment.FragmentB;
import com.oraro.nfcfunction.ui.fragment.FragmentC;
import com.oraro.nfcfunction.ui.fragment.FragmentD;
import com.oraro.nfcfunction.ui.fragment.FragmentE;
import com.oraro.nfcfunction.ui.fragment.SlidingMenuFragment;
import com.oraro.nfcfunction.utils.CustomFragmentManager;
import com.oraro.nfcfunction.utils.UIUtils;

public class MainActivity extends BaseActivity {

    private SlidingMenu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        //设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);

        // 设置滑动菜单视图的宽度
        UIUtils mUiUtils = new UIUtils();
        menu.setBehindOffset(mUiUtils.getDisplayMetrics(this).heightPixels * 1 / 5);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        /**
         * SLIDING_WINDOW：菜单栏里不包括ActionBar或标题
         * SLIDING_CONTENT：菜单栏里包括ActionBar或标题
         */
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        menu.setMenu(R.layout.left_menu_frame);
       SlidingMenuFragment mSlidingMenuFragment = new SlidingMenuFragment();
        // 将侧滑栏的mSlidingMenuFragment类填充到侧滑栏的容器的布局文件中
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.id_left_menu_frame, mSlidingMenuFragment, "Slidingmenu");
        transaction.commit();
        CustomFragmentManager.setCustomFragmentManagerNull();
        CustomFragmentManager  mCustomFragmentManager = CustomFragmentManager.getInstance(this);
        mCustomFragmentManager.setShowViewId(R.id.frame_1);
        FragmentA fragmentA=new FragmentA();
        FragmentB  fragmentB=new FragmentB();
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

}
