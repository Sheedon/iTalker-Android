package com.sumx4ever.italker.factory.presenter.user;

import android.text.TextUtils;

import com.sumx.factory.R;
import com.sumx4ever.factory.data.DataSource;
import com.sumx4ever.factory.presenter.BasePresenter;
import com.sumx4ever.italker.factory.Factory;
import com.sumx4ever.italker.factory.data.helper.UserHelper;
import com.sumx4ever.italker.factory.model.api.user.UserUpdateModel;
import com.sumx4ever.italker.factory.model.card.UserCard;
import com.sumx4ever.italker.factory.model.db.User;
import com.sumx4ever.italker.factory.net.UploadHelper;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

/**
 * @author Sumx https://github.com/Sumx4ever
 * @createDate 2018/6/26
 */
public class UpdateInfoPresenter extends BasePresenter<UpdateInfoContract.View>
        implements UpdateInfoContract.Presenter, DataSource.Callback<UserCard> {

    /**
     * 设置一个View，子类可以复写
     *
     * @param view
     */
    public UpdateInfoPresenter(UpdateInfoContract.View view) {
        super(view);
    }

    @Override
    public void update(final String photoFilePath, final String desc, final boolean isMan) {
        start();
        final UpdateInfoContract.View view = getView();
        if (TextUtils.isEmpty(photoFilePath) || TextUtils.isEmpty(desc)) {
            view.showError(R.string.data_account_update_invalid_parameter);
            return;
        }
        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {
                String url = "https://img3.mukewang.com/55b772c10001bb8c04190419-100-100.jpg";//UploadHelper.uploadPortrait(photoFilePath);
                if (TextUtils.isEmpty(url)) {
                    // 上传失败
                    view.showError(R.string.data_upload_error);
                } else {
                    UserUpdateModel model = new UserUpdateModel("", url, desc,
                            isMan ? User.SEX_MAN : User.SEX_WOMAN);
                    UserHelper.update(model, UpdateInfoPresenter.this);
                }
            }
        });
    }

    @Override
    public void onDataLoaded(UserCard userCard) {
        final UpdateInfoContract.View view = getView();
        if (view == null)
            return;
        // 强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.updateSuc();
            }
        });
    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        // 网络请求告知注册失败
        final UpdateInfoContract.View view = getView();
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
