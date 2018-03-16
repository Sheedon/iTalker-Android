package com.sumx4ever.common.tools;

import android.util.DisplayMetrics;

import com.sumx4ever.common.app.BaseActivity;

/**
 * @author xudongsun on 2018/1/18.
 */

public class UiTool {

    private static int STATUS_BAR_HEIGHT = -1;

    /**
     * 得到我们的状态栏高度
     * @param baseActivity
     * @return 状态栏的高度
     */
    public static int getStatusBarHeight(BaseActivity baseActivity){
        //TODO 我们的状态栏高度

        return STATUS_BAR_HEIGHT;
    }


    /**
     * 获取页面宽度
     * @param baseActivity
     * @return 页面宽度像素
     */
    public static int getScreenWidth(BaseActivity baseActivity){
        DisplayMetrics displayMetrics = baseActivity.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * 获取页面高度
     * @param baseActivity
     * @return 页面高度像素
     */
    public static int getScreenHeight(BaseActivity baseActivity){
        DisplayMetrics displayMetrics = baseActivity.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }


}
