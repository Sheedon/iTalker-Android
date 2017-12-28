package com.sumx4ever.common.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;


import java.util.List;

import butterknife.ButterKnife;

/**
 * @author xudongsun
 */

public abstract class Activity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //在界面未初始化之前调用的初始化窗口
        initWindows();

        if (initArgs(getIntent().getExtras())) {
            //设置layoutID绑定到页面上
            int layId = getContentLayoutId();
            setContentView(layId);

            initWidget();
            initData();
        } else {
            finish();
        }

    }

    /**
     * 初始化窗口
     */
    protected void initWindows() {

    }

    /**
     * 初始化相关参数
     * 在需要传值的页面 只有参数完整 才去获取资源id，布局，数据
     *
     * @param bundle 传入参数
     * @return 默认true 子类复写
     */
    protected boolean initArgs(Bundle bundle) {
        return true;
    }


    /**
     * 得到当前页面的资源Id
     *
     * @return 资源文件Id
     */
    protected abstract int getContentLayoutId();


    /**
     * 初始化控件
     */
    protected void initWidget() {
        ButterKnife.bind(this);
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }


    @Override
    public boolean onSupportNavigateUp() {
        // 当点击界面导航返回时，Finish当前界面
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        //得到当前Activity下的所有Fragment
        List<android.support.v4.app.Fragment> fragments = getSupportFragmentManager().getFragments();
        //判断fragments是否为空
        if (fragments != null && fragments.size() > 0) {
            for (Fragment fragment : fragments) {
                //判断是否为我们能过处理的Fragment类型
                if (fragment instanceof com.sumx4ever.common.app.Fragment) {
                    //判断是否拦截了返回按钮
                    if (((com.sumx4ever.common.app.Fragment) fragment).onBackPressed()) {
                        //如果拦截 直接Return
                        return;
                    }
                }
            }
        }

        super.onBackPressed();
        finish();
    }
}
