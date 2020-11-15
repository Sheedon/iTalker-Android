package com.sumx4ever.italker.factory.presenter.search;

import com.sumx4ever.factory.presenter.BaseContract;
import com.sumx4ever.italker.factory.model.card.GroupCard;
import com.sumx4ever.italker.factory.model.card.UserCard;

import java.util.List;

/**
 * @author Sumx https://github.com/Sumx4ever
 * @createDate 2018/8/6
 */
public interface SearchContract {
    interface Presenter extends BaseContract.Presenter {
        // 搜索内容
        void search(String content);
    }

    // 搜索人的界面
    interface UserView extends BaseContract.View<Presenter> {
        void onSearchDone(List<UserCard> userCards);
    }

    // 搜索群的界面
    interface GroupView extends BaseContract.View<Presenter> {
        void onSearchDone(List<GroupCard> groupCards);
    }
}
