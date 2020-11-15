package com.sumx4ever.italker.factory.presenter.message;

import android.support.v7.util.DiffUtil;

import com.sumx4ever.common.widget.recycler.RecyclerAdapter;
import com.sumx4ever.factory.data.DataSource;
import com.sumx4ever.italker.factory.data.helper.UserHelper;
import com.sumx4ever.italker.factory.data.message.SessionDataSource;
import com.sumx4ever.italker.factory.data.message.SessionRepository;
import com.sumx4ever.italker.factory.data.user.ContactDataSource;
import com.sumx4ever.italker.factory.data.user.ContactRepository;
import com.sumx4ever.italker.factory.model.db.Session;
import com.sumx4ever.italker.factory.model.db.User;
import com.sumx4ever.italker.factory.presenter.BaseSourcePresenter;
import com.sumx4ever.italker.factory.presenter.contact.ContactContract;
import com.sumx4ever.italker.factory.utils.DiffUiDataCallback;

import java.util.List;

/**
 * 最近聊天列表的Presenter
 *
 * @author Sumx https://github.com/Sumx4ever
 * @createDate 2018/8/8
 */
public class SessionPresenter extends BaseSourcePresenter<Session, Session,
        SessionDataSource, SessionContract.View>
        implements SessionContract.Presenter {

    public SessionPresenter(SessionContract.View view) {
        super(new SessionRepository(), view);
    }


    @Override
    public void onDataLoaded(List<Session> sessions) {
        // 无论怎么操作，数据变更，最终都会通知到这里来
        SessionContract.View view = getView();
        if (view == null)
            return;

        List<Session> old = view.getRecyclerAdapter().getItems();
        // 差异计算
        DiffUiDataCallback<Session> callback = new DiffUiDataCallback<>(old, sessions);
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        // 进行界面刷新
        refreshData(result, sessions);
    }

}
