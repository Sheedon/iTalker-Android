package com.sumx4ever.italker.services;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.sumx4ever.italker.factory.Factory;
import com.sumx4ever.italker.factory.data.helper.AccountHelper;
import com.sumx4ever.italker.factory.persistence.Account;

public class IntentService extends GTIntentService {
    public IntentService() {
    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {

    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {
        // 当设备初始化的时候
        // 获取设备Id
        onClientInit(clientid);
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        byte[] payload = msg.getPayload();
        if (payload != null) {
            String message = new String(payload);
            Log.i(TAG, "GET_MSG_DATA:" + message);
            onMessageArrived(message);
        }
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean b) {

    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {

    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage
            gtNotificationMessage) {

    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage gtNotificationMessage) {

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
