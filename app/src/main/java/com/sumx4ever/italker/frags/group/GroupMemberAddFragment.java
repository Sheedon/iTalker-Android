package com.sumx4ever.italker.frags.group;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sumx4ever.common.widget.PortraitView;
import com.sumx4ever.common.widget.recycler.RecyclerAdapter;
import com.sumx4ever.italker.R;
import com.sumx4ever.italker.activities.GroupCreateActivity;
import com.sumx4ever.italker.factory.presenter.group.GroupCreateContract;
import com.sumx4ever.italker.factory.presenter.group.GroupMemberAddContract;
import com.sumx4ever.italker.factory.presenter.group.GroupMemberAddPresenter;
import com.sumx4ever.italker.frags.media.GalleryFragment;

import net.qiujuer.genius.ui.compat.UiCompat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * 群成员添加的Fragment
 */
public class GroupMemberAddFragment extends BottomSheetDialogFragment
        implements GroupMemberAddContract.View {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private Adapter mAdapter;
    private GroupMemberAddContract.Presenter mPresenter;
    private Callback mCallback;

    public GroupMemberAddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // context 就是咱们的Activity
        mCallback = (Callback) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new GalleryFragment.TransStatusBottomSheetDialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 初始化
        initPresenter();
        // 获取我们的GalleryView
        View root = inflater.inflate(R.layout.fragment_group_member_add, container, false);
        ButterKnife.bind(this, root);
        initRecycler();
        initToolbar();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter != null)
            mPresenter.destroy();
    }

    private void initPresenter() {
        new GroupMemberAddPresenter(this);
    }

    private void initRecycler() {
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter = new Adapter());
    }

    private void initToolbar() {
        mToolbar.inflateMenu(R.menu.group_create);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_create) {
                    if (mPresenter != null)
                        mPresenter.submit();
                    return true;
                }
                return false;
            }
        });

        MenuItem item = mToolbar.getMenu().findItem(R.id.action_create);
        Drawable drawable = item.getIcon();
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, UiCompat.getColor(getResources(),R.color.textPrimary));
        item.setIcon(drawable);
    }

    @Override
    public void onAddedSucceed() {
        if(mCallback != null){
            mCallback.hideLoading();
            mCallback.refreshMembers();
        }
        dismiss();
    }

    @Override
    public String getGroupId() {
        return mCallback.getGroupId();
    }

    @Override
    public void showError(int str) {
        if (mCallback != null)
            mCallback.showError(str);
    }

    @Override
    public void showLoading() {
        if (mCallback != null)
            mCallback.showLoading();
    }

    @Override
    public void setPresenter(GroupMemberAddContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public RecyclerAdapter<GroupCreateContract.ViewModel> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChanged() {
        if(mCallback != null)
            mCallback.hideLoading();
    }

    private class Adapter extends RecyclerAdapter<GroupCreateContract.ViewModel> {

        @Override
        protected int getItemViewType(int position, GroupCreateContract.ViewModel viewModel) {
            return R.layout.cell_group_create_contact;
        }

        @Override
        protected ViewHolder<GroupCreateContract.ViewModel> onCreateViewHolder(View root, int viewType) {
            return new GroupMemberAddFragment.ViewHolder(root);
        }
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<GroupCreateContract.ViewModel> {
        @BindView(R.id.im_portrait)
        PortraitView mPortrait;
        @BindView(R.id.txt_name)
        TextView mName;
        @BindView(R.id.cb_select)
        CheckBox mSelect;


        ViewHolder(View itemView) {
            super(itemView);
        }

        @OnCheckedChanged(R.id.cb_select)
        void onCheckedChanged(boolean checked) {
            // 进行状态更改
            mPresenter.changeSelect(mData, checked);
        }

        @Override
        protected void onBind(GroupCreateContract.ViewModel viewModel) {
            mPortrait.setup(Glide.with(GroupMemberAddFragment.this), viewModel.author);
            mName.setText(viewModel.author.getName());
            mSelect.setChecked(viewModel.isSelected);
        }
    }

    // Fragment 与 Activity 之间的交互接口
    public interface Callback {
        String getGroupId();

        void hideLoading();

        // 公共的：显示一个字符串错误
        void showError(@StringRes int str);

        // 公共的：显示进度条
        void showLoading();

        // 刷新成员
        void refreshMembers();
    }

}
