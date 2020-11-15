package com.sumx4ever.italker.factory.data.helper;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.sumx.factory.R;
import com.sumx4ever.factory.data.DataSource;
import com.sumx4ever.italker.factory.Factory;
import com.sumx4ever.italker.factory.model.api.RspModel;
import com.sumx4ever.italker.factory.model.api.user.UserUpdateModel;
import com.sumx4ever.italker.factory.model.card.UserCard;
import com.sumx4ever.italker.factory.model.db.User;
import com.sumx4ever.italker.factory.model.db.User_Table;
import com.sumx4ever.italker.factory.model.db.view.UserSampleModel;
import com.sumx4ever.italker.factory.net.Network;
import com.sumx4ever.italker.factory.net.RemoteService;
import com.sumx4ever.italker.factory.persistence.Account;
import com.sumx4ever.utils.CollectionUtil;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Sumx https://github.com/Sumx4ever
 * @createDate 2018/6/27
 */
public class UserHelper {
    // 更新用户信息
    public static void update(UserUpdateModel model, final DataSource.Callback<UserCard> callback) {
        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService service = Network.remote();
        Call<RspModel<UserCard>> call = service.userUpdate(model);
        call.enqueue(new Callback<RspModel<UserCard>>() {
            @Override
            public void onResponse(Call<RspModel<UserCard>> call, Response<RspModel<UserCard>>
                    response) {
                RspModel<UserCard> rspModel = response.body();
                if (rspModel.success()) {
                    UserCard userCard = rspModel.getResult();
                    // 唤起进行保存的操作
                    Factory.getUserCenter().dispatch(userCard);
                    // 返回成功
                    callback.onDataLoaded(userCard);
                } else {
                    // 错误情况下进行错误分配
                    Factory.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<UserCard>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }

    // 搜索的方法
    public static Call search(String name, final DataSource.Callback<List<UserCard>> callback) {
        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService service = Network.remote();
        Call<RspModel<List<UserCard>>> call = service.userSearch(name);
        call.enqueue(new Callback<RspModel<List<UserCard>>>() {
            @Override
            public void onResponse(Call<RspModel<List<UserCard>>> call,
                                   Response<RspModel<List<UserCard>>> response) {
                RspModel<List<UserCard>> rspModel = response.body();
                if (rspModel.success()) {
                    // 返回数据
                    callback.onDataLoaded(rspModel.getResult());
                } else {
                    // 错误情况下进行错误分配
                    Factory.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<List<UserCard>>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });

        // 把当前的调度这返回
        return call;
    }

    // 关注人的方法
    public static void follow(String userId, final DataSource.Callback<UserCard> callback) {
        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService service = Network.remote();
        Call<RspModel<UserCard>> call = service.userFollow(userId);
        call.enqueue(new Callback<RspModel<UserCard>>() {
            @Override
            public void onResponse(Call<RspModel<UserCard>> call, Response<RspModel<UserCard>>
                    response) {
                RspModel<UserCard> rspModel = response.body();
                if (rspModel.success()) {
                    UserCard userCard = rspModel.getResult();
                    // 唤起进行保存的操作
                    Factory.getUserCenter().dispatch(userCard);
                    // 返回数据
                    callback.onDataLoaded(userCard);
                } else {
                    // 错误情况下进行错误分配
                    Factory.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<UserCard>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }

    // 刷新联系人的操作
    public static void refreshContacts() {
        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService service = Network.remote();
        service.userContacts().enqueue(new Callback<RspModel<List<UserCard>>>() {
            @Override
            public void onResponse(Call<RspModel<List<UserCard>>> call,
                                   Response<RspModel<List<UserCard>>> response) {
                RspModel<List<UserCard>> rspModel = response.body();
                if (rspModel != null && rspModel.success()) {
                    // 拿到集合
                    List<UserCard> cards = rspModel.getResult();
                    if (cards == null || cards.size() == 0)
                        return;
//                    Factory.getUserCenter().dispatch(CollectionUtil.toArray(cards, UserCard.class));
                    Factory.getUserCenter().dispatch(cards.toArray(new UserCard[0]));
                } else {
                    // 错误情况下进行错误分配
                    Factory.decodeRspCode(rspModel, null);
                }
            }

            @Override
            public void onFailure(Call<RspModel<List<UserCard>>> call, Throwable t) {
                // nothing
            }
        });
    }

    // 从本地查询一个用户的信息
    public static User findFromLocal(String userId) {
        return SQLite.select().from(User.class).where(User_Table.id.eq(userId)).querySingle();
    }

    // 从网络查询某用户的信息
    public static User findFromNet(String userId) {
        RemoteService service = Network.remote();
        try {
            Response<RspModel<UserCard>> response = service.userFind(userId).execute();
            UserCard userCard = response.body().getResult();
            if (userCard != null) {
                User user = userCard.build();
                // 唤起进行保存的操作
                Factory.getUserCenter().dispatch(userCard);
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 搜索一个用户，优先本地缓存，
     * 没有用然后再从网络拉取
     */
    public static User search(String userId) {
        User user = findFromLocal(userId);
        if (user == null) {
            return findFromNet(userId);
        }
        return user;
    }

    /**
     * 搜索一个用户，优先网络查询
     * 没有用然后再从本地缓存拉取
     */
    public static User searchFirstNet(String userId) {
        User user = findFromNet(userId);
        if (user == null) {
            return findFromLocal(userId);
        }
        return user;
    }


    // 获取一个联系人列表，
    // 但是是一个简单的数据的
    public static List<UserSampleModel> getSampleContact() {
        //"select id = ??";
        //"select User_id = ??";
        return SQLite.select(User_Table.id.withTable().as("id"),
                User_Table.name.withTable().as("name"),
                User_Table.portrait.withTable().as("portrait"))
                .from(User.class)
                .where(User_Table.isFollow.eq(true))
                .and(User_Table.id.notEq(Account.getUserId()))
                .orderBy(User_Table.name, true)
                .queryCustomList(UserSampleModel.class);
    }
}
