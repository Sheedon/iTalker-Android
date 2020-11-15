package com.sumx4ever.common.app;

import android.os.Bundle;

import com.sumx4ever.factory.presenter.BaseContract;

/**
 * @author Sumx
 * @createDate 2018/5/31
 */
public abstract class PresenterFragment<Presenter extends BaseContract.Presenter> extends Fragment
        implements BaseContract.View<Presenter> {

    protected Presenter mPresenter;

    @Override
    protected void initArgs(Bundle bundle) {
        super.initArgs(bundle);
    }

    @Override
    protected void initBefore() {
        super.initBefore();
        initPresenter();
    }

    /**
     * 初始化Presenter
     * @return Presenter
     */
    protected abstract Presenter initPresenter();

    @Override
    public void showError(int str) {
        // 显示错误, 优先使用占位布局
        if(mPlaceHolderView != null){
            mPlaceHolderView.triggerError(str);
        }else {
            Application.showToast(str);
        }
    }

    @Override
    public void showLoading() {
        // TODO 显示一个Loading
        if(mPlaceHolderView != null){
            mPlaceHolderView.triggerLoading();
        }
    }

    @Override
    public void setPresenter(Presenter presenter) {
        // View中赋值Presenter
        mPresenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter != null){
            mPresenter.destroy();
        }

    }
}
