package com.sumx4ever.italker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.igexin.sdk.PushConsts;
import com.sumx4ever.italker.factory.Factory;
import com.sumx4ever.italker.factory.data.helper.AccountHelper;
import com.sumx4ever.italker.factory.persistence.Account;

/**
 * 个推消息接收器
 *
 * @author Sumx
 * @createDate 2018/6/11
 */
public class MessageReceiver extends BroadcastReceiver {
    private static final String TAG = MessageReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null)
            return;

        Bundle bundle = intent.getExtras();

        // 判断当前信息的意图
        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_CLIENTID:
                Log.i(TAG, "GET_CLIENTID:" + bundle.toString());
                // 当设备初始化的时候
                // 获取设备Id
                onClientInit(bundle.getString("clientid"));
                break;
            case PushConsts.GET_MSG_DATA:
                // 常规信息送达
                byte[] payload = bundle.getByteArray("payload");
                if (payload != null) {
                    String message = new String(payload);
                    Log.i(TAG, "GET_MSG_DATA:" + message);
                    onMessageArrived(message);
                }
                break;
            default:
                Log.i(TAG, "OTHER:" + bundle.toString());
                break;
        }
    }

    /**
     * 当设备初始化的时候
     *
     * @param cid 设备id
     */
    private void onClientInit(String cid) {
        // 设置设备Id
        Account.setPushId(cid);
        if (Account.isLogin()) {
            // 账户登陆状态，进行一次push
            AccountHelper.bindPush(null);
        }
    }

    /**
     * 消息送达时
     *
     * @param message 新消息
     */
    private void onMessageArrived(String message) {
        // 交给Factory处理
        Factory.dispatchPush(message);
    }
}
