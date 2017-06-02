package com.oraro.nfcfunction.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by  on 2016/8/30 0030.
 */
public class UIUtils {
    /**
     * 获取当前屏幕数据
     * @return
     */
    public DisplayMetrics getDisplayMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }


    public void setViewMargin(View view, int left, int top, int right, int bottom) {
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).setMargins(left, top, right, bottom);
    }


    public void setViewParams(View view,int width, int height) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.width = width;
        lp.height = height;
        view.setLayoutParams(lp);
    }
//    public static String[] getStringArray(int id){
//        return MBroadcastApplication.getMyContext().getResources().getStringArray(id);
//    }
}
