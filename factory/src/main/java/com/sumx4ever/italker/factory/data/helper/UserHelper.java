package com.sumx4ever.italker.factory.data.helper;

import com.sumx.factory.R;
import com.sumx4ever.factory.data.DataSource;
import com.sumx4ever.italker.factory.Factory;
import com.sumx4ever.italker.factory.model.api.RspModel;
import com.sumx4ever.italker.factory.model.api.user.UserUpdateModel;
import com.sumx4ever.italker.factory.model.card.UserCard;
import com.sumx4ever.italker.factory.model.db.User;
import com.sumx4ever.italker.factory.net.Network;
import com.sumx4ever.italker.factory.net.RemoteService;

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
            public void onResponse(Call<RspModel<UserCard>> call, Response<RspModel<UserCard>> response) {
                RspModel<UserCard> rspModel = response.body();
                if (rspModel.success()) {
                    UserCard userCard = rspModel.getResult();
                    // 数据库的存储操作，需要把UserCard转化为User
                    // 保存用户信息
                    User user = userCard.build();
                    user.save();
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
}
