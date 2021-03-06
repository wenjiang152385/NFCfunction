package com.oraro.nfcfunction;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.oraro.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.oraro.nfcfunction.common.App;
import com.oraro.nfcfunction.common.ExceptionForToast;
import com.oraro.nfcfunction.ui.fragment.FragmentA;
import com.oraro.nfcfunction.ui.fragment.FragmentB;
import com.oraro.nfcfunction.ui.fragment.FragmentC;
import com.oraro.nfcfunction.ui.fragment.FragmentD;
import com.oraro.nfcfunction.ui.fragment.FragmentE;
import com.oraro.nfcfunction.ui.fragment.SlidingMenuFragment;
import com.oraro.nfcfunction.utils.Constants;
import com.oraro.nfcfunction.utils.CustomFragmentManager;
import com.oraro.nfcfunction.utils.SimpleEvent;
import com.oraro.nfcfunction.utils.UIUtils;
import com.senter.support.openapi.StUhf;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    private SlidingMenu menu;
    private CustomFragmentManager mCustomFragmentManager;
    private boolean mIsClickBackBtn = false;
    private static final int MSG_QUIT_APP = 1;
    private SimpleEvent mSimpleEvent;
    private boolean isMainPosition = true;
    private boolean mIsNeedChangeMenu = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_QUIT_APP:
                    mIsClickBackBtn = false;
                    break;
            }
        }
    };
//    public static String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    setContentView(R.layout.activity_main);
        uhfModelChoiced(null);
        //App.appCfgSaveModelClear();
        App.getUhf(App.appCfgSavedModel());
        try {
            App.uhfInit();
        } catch (ExceptionForToast e) {
            e.printStackTrace();
            App.uhfClear();
            App.appCfgSaveModelClear();
        }
        menu=new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
    //设置触摸屏幕的模式
    menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
    menu.setShadowWidthRes(R.dimen.shadow_width);
    menu.setShadowDrawable(R.drawable.shadow);

    // 设置滑动菜单视图的宽度
    UIUtils mUiUtils = new UIUtils();
    menu.setBehindOffset(mUiUtils.getDisplayMetrics(this).widthPixels*4/5);
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
        transaction.replace(R.id.id_left_menu_frame,mSlidingMenuFragment,"Slidingmenu");
        transaction.commit();
//        menu.setSecondaryMenu(R.layout.right_menu_frame);
//        RightMenuFragment rightMenuFragment = new RightMenuFragment();
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.id_right_menu_frame, rightMenuFragment).commit();
    CustomFragmentManager.setCustomFragmentManagerNull();
    mCustomFragmentManager= CustomFragmentManager.getInstance(this);
    mCustomFragmentManager.setShowViewId(R.id.frame_1);
    FragmentA fragmentA = new FragmentA();
    FragmentB fragmentB = new FragmentB();
    mCustomFragmentManager.addFragment(fragmentB);
    FragmentC fragmentC = new FragmentC();
    mCustomFragmentManager.addFragment(fragmentC);
    FragmentD fragmentD = new FragmentD();
    mCustomFragmentManager.addFragment(fragmentD);
    FragmentE fragmentE = new FragmentE();
    mCustomFragmentManager.addFragment(fragmentE);
    mCustomFragmentManager.setMainFragment(fragmentA);
    mCustomFragmentManager.startFragment(fragmentA);
}
    protected void uhfModelChoiced(StUhf.InterrogatorModel interrogatorModel){
        if (interrogatorModel==null) {
            if (App.getUhfWithDetectionAutomaticallyIfNeed()!=null) {
//                if (views.cbRememberChoice.isChecked()) {
//                    App.appCfgSaveModel(App.uhfInterfaceAsModel());
//                }else {
//                }
                App.appCfgSaveModelClear();
            }else {
//                ah.showToastShort("no uhf module detected");
            }
        }else {
            if (App.getUhf(interrogatorModel)!=null) {
//                if (views.cbRememberChoice.isChecked()) {
//                    App.appCfgSaveModel(interrogatorModel);
//                }else {
//                }
                App.appCfgSaveModelClear();
            }else {
//                ah.showToastShort("no uhf module detected");
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCustomFragmentManager.destory();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SimpleEvent event) {
        mSimpleEvent=event;
        Log.e("jw","onEventMainThread收到了消息：" + event.getMsg());
        switch (event.getMsg()){
            case Constants.BACK_MAIN_CLOSE:
                isMainPosition = true;
                mIsNeedChangeMenu = true;
                mCustomFragmentManager.finishFragment();
                break;
        }
    }
    public void resetMenu(int size) {
        if (size == 1) {
            //mSlidingMenuFragment.setClickPosition(-1);
        }
    }
    public  void  hidingmenu() {
        menu.toggle();
    }
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        uid = techMap.get("uid").replace(":", " ");
//        Log.e("jw", "uid==" + uid + "techMap==" + techMap);
//
//
//    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (menu.isMenuShowing()) {
                        menu.toggle();
                        return  true;
                    }
//                    else if (!mIsClickBackBtn){
////                        if (!mIsClickBackBtn) {
//                            Toast.makeText(this,"再按一次退出应用", Toast.LENGTH_SHORT).show();
//                            mHandler.sendEmptyMessageDelayed(MSG_QUIT_APP, 3000);
//                            mIsClickBackBtn = true;
//                            return true;
////                        }
//                    }
                    if (mCustomFragmentManager.getSize() > 1) {
                        int i = mCustomFragmentManager.finishFragment();
                        resetMenu(i);
                        return true;
                    }
        }
        return super.onKeyDown(keyCode, event);
    }

}

