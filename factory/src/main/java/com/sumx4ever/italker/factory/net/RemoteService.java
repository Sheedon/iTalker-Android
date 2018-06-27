package com.sumx4ever.italker.factory.net;

import com.sumx4ever.italker.factory.model.api.RspModel;
import com.sumx4ever.italker.factory.model.api.account.AccountRspModel;
import com.sumx4ever.italker.factory.model.api.account.LoginModel;
import com.sumx4ever.italker.factory.model.api.account.RegisterModel;
import com.sumx4ever.italker.factory.model.api.user.UserUpdateModel;
import com.sumx4ever.italker.factory.model.card.UserCard;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * 网络请求的所有的接口
 *
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public interface RemoteService {

    /**
     * 注册接口
     *
     * @param model 传入的是RegisterModel
     * @return 返回的是RspModel<AccountRspModel>
     */
    @POST("account/register")
    Call<RspModel<AccountRspModel>> accountRegister(@Body RegisterModel model);

    /**
     * 登陆接口
     *
     * @param model 传入的是LoginModel
     * @return 返回的是RspModel<AccountRspModel>
     */
    @POST("account/login")
    Call<RspModel<AccountRspModel>> accountLogin(@Body LoginModel model);

    /**
     * 绑定设备Id
     *
     * @param pushId 设备Id
     * @return 返回的是RspModel<AccountRspModel>
     */
    @POST("account/bind/{pushId}")
    Call<RspModel<AccountRspModel>> accountBind(@Path(encoded = true, value = "pushId") String pushId);

    /**
     * 用户更新接口
     *
     * @param model 用户信息
     * @return
     */
    @PUT("user")
    Call<RspModel<UserCard>> userUpdate(@Body UserUpdateModel model);
}
