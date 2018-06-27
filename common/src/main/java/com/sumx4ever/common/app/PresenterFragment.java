package com.sumx4ever.common.app;

import android.os.Bundle;

import com.sumx4ever.factory.presenter.BaseContract;

/**
 * @author Sumx
 * @createDate 2018/5/31
 */
public abstract class PresenterFragment<Presenter extends BaseContract.Presenter> extends BaseFragment
        implements BaseContract.View<Presenter> {

    protected Presenter mPresenter;

    @Override
    protected void initArgs(Bundle bundle) {
        super.initArgs(bundle);

        // 在界面onAttach之后就触发初始化Presenter
        initPresenter();
    }

    /**
     * 初始化Presenter
     * @return Presenter
     */
    protected abstract Presenter initPresenter();

    @Override
    public void showError(int str) {
        // 显示错误
        Application.showToast(str);
    }

    @Override
    public void showLoading() {
        // TODO 显示一个Loading
    }

    @Override
    public void setPresenter(Presenter presenter) {
        // View中赋值Presenter
        mPresenter = presenter;
    }
}
