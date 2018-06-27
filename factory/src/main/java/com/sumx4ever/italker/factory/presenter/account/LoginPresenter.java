package com.sumx4ever.italker.factory.presenter.account;

import android.text.TextUtils;

import com.sumx.factory.R;
import com.sumx4ever.factory.data.DataSource;
import com.sumx4ever.factory.presenter.BasePresenter;
import com.sumx4ever.italker.factory.data.helper.AccountHelper;
import com.sumx4ever.italker.factory.model.api.account.LoginModel;
import com.sumx4ever.italker.factory.model.api.account.RegisterModel;
import com.sumx4ever.italker.factory.model.db.User;
import com.sumx4ever.italker.factory.persistence.Account;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

/**
 * @author Sumx
 * @createDate 2018/6/11
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter ,DataSource.Callback<User>{
    /**
     * 设置一个View，子类可以复写
     *
     * @param view
     */
    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    public void login(String phone, String password) {
        // 调用开始方法，在start中默认启动了Loading
        start();

        // 得到View接口
        LoginContract.View view = getView();

        // 校验
        if(TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)){
            view.showError(R.string.data_account_login_invalid_parameter);
        }else{
            // 尝试传递PushId
            LoginModel model = new LoginModel(phone, password, Account.getPushId());
            AccountHelper.login(model,this);
        }
    }

    @Override
    public void onDataLoaded(User user) {
        final LoginContract.View view = getView();
        if (view == null)
            return;
        // 强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.loginSuc();
            }
        });
    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        // 网络请求告知注册失败
        final LoginContract.View view = getView();
        if (view == null)
            return;
        // 此时是从网络回送回来的，并不保证处于主现场状态
        // 强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                // 调用主界面注册失败显示错误
                view.showError(strRes);
            }
        });
    }
}
