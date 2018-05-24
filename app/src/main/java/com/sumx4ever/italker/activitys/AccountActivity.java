package com.sumx4ever.italker.activitys;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sumx4ever.common.app.BaseActivity;
import com.sumx4ever.common.app.BaseFragment;
import com.sumx4ever.italker.R;
import com.sumx4ever.italker.frags.account.UpdateInfoFragment;
import com.yalantis.ucrop.UCrop;

public class AccountActivity extends BaseActivity {
    private BaseFragment mCurFragment;

    /**
     * 账户Activity显示的入口
     * @param context Context
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        mCurFragment = new UpdateInfoFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, mCurFragment)
                .commit();

    }

    // Activity中收到剪切图片成功的回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCurFragment.onActivityResult(requestCode,resultCode,data);
    }

}
