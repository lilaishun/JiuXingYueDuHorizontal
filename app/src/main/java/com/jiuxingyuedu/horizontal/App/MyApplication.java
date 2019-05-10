package com.jiuxingyuedu.horizontal.App;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jiuxingyuedu.horizontal.Bean.NewsNean;
import com.jiuxingyuedu.horizontal.Receiver.DaemonService;
import com.lzy.okgo.OkGo;
import com.tencent.smtt.sdk.QbSdk;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends android.app.Application {
    public static List<NewsNean> NewsNeanLists = new ArrayList<>();
    public static boolean ISSHOWSEACHER;
    public static boolean ISFORBack;//是否是上一页返回
    public static boolean ISONCLICKSEA;//是否可以点击搜索框
    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.getInstance().init(this);
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        //错误日志收集
//	CrashCatchHandler.getInstance().init(this);
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);

        Intent   intentService = new Intent(this, DaemonService.class);
        Bundle bundle = new Bundle();
        bundle.putString("packageName", getPackageName());
        bundle.putInt("sleepTime", 1000);
        intentService.putExtras(bundle);
        startService(intentService);
    }
}
