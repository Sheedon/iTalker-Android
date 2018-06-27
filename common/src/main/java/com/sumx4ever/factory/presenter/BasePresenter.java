package com.sumx4ever.factory.presenter;

/**
 * @author Sumx stuby of qiujuer's MVP
 * @createDate 2018/5/31
 */
public class BasePresenter<T extends BaseContract.View> implements BaseContract.Presenter{

    protected T mView;

    /**
     * 设置一个View，子类可以复写
     */
    public BasePresenter(T view){
        setView(view);
    }

    /**
     * 给子类使用的获取View的操作
     * 不允许复写
     *
     * @return View
     */
    protected void setView(T view) {
        this.mView = view;
        this.mView.setPresenter(this);
    }

    protected final T getView() {
        return mView;
    }

    @Override
    public void start() {
        // 开始的时候进行Loading调用
        T view = mView;
        if(view != null){
            view.showLoading();
        }
    }

    @Override
    public void destroy() {
        T view = mView;
        mView = null;
        if(view != null){
            // 把Presenter设置为NULL
            view.setPresenter(null);
        }
    }
}
