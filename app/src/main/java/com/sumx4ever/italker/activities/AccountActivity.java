package com.sumx4ever.italker.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sumx4ever.common.app.Activity;
import com.sumx4ever.common.app.Fragment;
import com.sumx4ever.italker.R;
import com.sumx4ever.italker.frags.account.AccountTrigger;
import com.sumx4ever.italker.frags.account.LoginFragment;
import com.sumx4ever.italker.frags.account.RegisterFragment;

import net.qiujuer.genius.ui.compat.UiCompat;

import butterknife.BindView;

public class AccountActivity extends Activity implements AccountTrigger {
    private Fragment currentFragment;
    private Fragment loginFragment;
    private Fragment registerFragment;

    @BindView(R.id.im_bg)
    ImageView mBg;

    /**
     * 账户Activity显示的入口
     *
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

        // 初始化Fragment
        currentFragment = loginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.lay_container, currentFragment)
                .commit();

        Glide.with(this)
                .load(R.drawable.bg_src_tianjin)
                .apply(new RequestOptions().centerCrop())
                .into(new ViewTarget<ImageView ,Drawable>(mBg) {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        // 使用适配类进行包装
                        Drawable drawable = DrawableCompat.wrap(resource);
                        drawable.setColorFilter(UiCompat.getColor(getResources(),R.color.colorAccent),
                                PorterDuff.Mode.SCREEN); // 设置着色的效果和颜色，蒙板模式
                        // 设置给ImageView
                        this.view.setImageDrawable(drawable);
                    }
                });

    }


    @Override
    public void triggerView() {
        Fragment fragment;
        if(currentFragment == loginFragment){
            if(registerFragment == null){
                // 第一次进入一定为null
                // 做初始化操作
                registerFragment = new RegisterFragment();
            }
            fragment = registerFragment;
        }else{
            // 默认情况下loginFragment已经赋值
            fragment = loginFragment;
        }
        // 重新赋值当前需要显示的Fragment
        currentFragment = fragment;
        // 切换
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.lay_container,currentFragment)
                .commit();
    }
}
