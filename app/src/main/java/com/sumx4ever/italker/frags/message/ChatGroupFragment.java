package com.sumx4ever.italker.frags.message;


import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sumx4ever.italker.R;
import com.sumx4ever.italker.activities.GroupMemberActivity;
import com.sumx4ever.italker.activities.PersonalActivity;
import com.sumx4ever.italker.factory.model.db.Group;
import com.sumx4ever.italker.factory.model.db.view.MemberUserModel;
import com.sumx4ever.italker.factory.presenter.message.ChatContract;
import com.sumx4ever.italker.factory.presenter.message.ChatGroupPresenter;

import java.io.File;
import java.util.List;

import butterknife.BindView;

/**
 * 群聊天界面实现
 */
public class ChatGroupFragment extends ChatFragment<Group>
        implements ChatContract.GroupView {

    @BindView(R.id.im_header)
    ImageView mHeader;

    @BindView(R.id.lay_members)
    LinearLayout mLayMembers;

    @BindView(R.id.txt_member_more)
    TextView mMemberMore;

    public ChatGroupFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getHeaderLayoutId() {
        return R.layout.lay_chat_header_group;
    }

    @Override
    protected ChatContract.Presenter initPresenter() {
        return new ChatGroupPresenter(this, mReceiverId);
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        Glide.with(this)
                .load(R.drawable.default_banner_group)
                .apply(new RequestOptions()
                        .centerCrop())
                .into(new ViewTarget<CollapsingToolbarLayout, Drawable>(mCollapsingLayout) {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable>
                            transition) {
                        this.view.setContentScrim(resource.getCurrent());
                    }
                });
    }


    // 进行高度的综合运算，透明我们的头像和Icon
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        super.onOffsetChanged(appBarLayout, verticalOffset);
        View view = mLayMembers;

        if (view == null)
            return;

        if (verticalOffset == 0) {
            // 完全展开
            onViewChange(view, 1, View.VISIBLE);
        } else {
            // abs 运算
            verticalOffset = Math.abs(verticalOffset);
            final int totalScrollRange = appBarLayout.getTotalScrollRange();
            if (verticalOffset >= totalScrollRange) {
                // 关闭状态
                onViewChange(view, 1, View.INVISIBLE);
            } else {
                // 中间状态
                float progress = 1 - verticalOffset / (float) totalScrollRange;
                onViewChange(view, progress, View.VISIBLE);
            }
        }
    }


    /**
     * appBarLayout 高度改变时，头像跟着改变
     */
    private void onViewChange(View view, float progress, int visibility) {

        view.setVisibility(visibility);
        view.setScaleX(progress);
        view.setScaleY(progress);
        view.setAlpha(progress);
    }

    @Override
    public void onInit(Group group) {
        mCollapsingLayout.setTitle(group.getName());
        Glide.with(this)
                .load(group.getPicture())
                .apply(new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.default_banner_group))
                .into(mHeader);
    }

    @Override
    public void onInitGroupMembers(List<MemberUserModel> members, long moreCount) {
        if (members == null || members.size() == 0)
            return;

        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (final MemberUserModel member : members) {
            // 添加成员头像
            ImageView p = (ImageView) inflater.inflate(R.layout.lay_chat_group_portrait,
                    mLayMembers, false);
            mLayMembers.addView(p, 0);

            Glide.with(this)
                    .load(member.portrait)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.default_portrait)
                            .centerCrop()
                            .dontAnimate())
                    .into(p);

            // 个人界面查看
            p.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PersonalActivity.show(getContext(), member.userId);
                }
            });

        }

        if (moreCount > 0) {
            mMemberMore.setText(String.format("+%s", moreCount));
            mMemberMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 显示成员列表
                    GroupMemberActivity.show(getContext(), mReceiverId);
                }
            });
        } else {
            mMemberMore.setVisibility(View.GONE);
        }
    }

    @Override
    public void showAdminOption(boolean isAdmin) {
        if (isAdmin) {
            mToolbar.inflateMenu(R.menu.chat_group);
            mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.action_add) {
                        // mReceiverId 就是群的Id
                        GroupMemberActivity.showAdmin(getContext(), mReceiverId);
                        return true;
                    }
                    return false;
                }
            });
        }
    }
}
