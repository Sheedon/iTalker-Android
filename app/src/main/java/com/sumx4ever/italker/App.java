package com.sumx4ever.italker;

import com.igexin.sdk.PushManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.sumx4ever.common.app.Application;
import com.sumx4ever.italker.factory.Factory;
import com.sumx4ever.italker.services.IntentService;
import com.sumx4ever.italker.services.TalkerPushService;

/**
 * Created by xudongsun on 2018/1/18.
 */

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        // 调用Factory进行初始化
        Factory.setup();
        PushManager.getInstance().initialize(this,TalkerPushService.class);
        PushManager.getInstance().registerPushIntentService(this, IntentService.class);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
}
