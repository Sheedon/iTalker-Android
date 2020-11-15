package com.sumx4ever.italker.factory.presenter.contact;

import com.sumx4ever.factory.presenter.BasePresenter;
import com.sumx4ever.italker.factory.Factory;
import com.sumx4ever.italker.factory.data.helper.UserHelper;
import com.sumx4ever.italker.factory.model.db.User;
import com.sumx4ever.italker.factory.persistence.Account;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

/**
 * @author Sumx https://github.com/Sumx4ever
 * @createDate 2018/8/16
 */
public class PersonalPresenter extends BasePresenter<PersonalContract.View> implements PersonalContract.Presenter {

    private User user;

    public PersonalPresenter(PersonalContract.View view) {
        super(view);
    }

    @Override
    public void start() {
        super.start();

        // 个人界面用户数据优先从网络拉取
        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {
                PersonalContract.View view = getView();
                if (view != null) {
                    String userId = view.getUserId();
                    User user = UserHelper.searchFirstNet(userId);
                    onLoaded(user);
                }
            }
        });
    }

    /**
     * 进行界面的设置
     *
     * @param user 用户信息
     */
    private void onLoaded(final User user) {
        this.user = user;
        // 是否就是我自己
        final boolean isSelf = user.getId().equalsIgnoreCase(Account.getUserId());
        // 是否已经关注
        final boolean isFollow = isSelf || user.isFollow();
        // 已经关注同时不是自己才能聊天
        final boolean allowSayHello = isFollow && !isSelf;


        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                PersonalContract.View view = getView();
                if(view == null) return;
                view.onLoadDone(user);
                view.setFollowStatus(isFollow);
                view.allowSayHello(allowSayHello);
            }
        });
    }

    @Override
    public User getUserPersonal() {
        return user;
    }
}
