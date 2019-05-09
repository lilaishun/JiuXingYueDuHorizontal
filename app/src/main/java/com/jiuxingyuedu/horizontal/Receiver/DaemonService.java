package com.jiuxingyuedu.horizontal.Receiver;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by 马吉尧 on 2017/12/18.
 * 邮箱:1505508999@qq.com
 */

public class DaemonService extends Service {


    private String packageName = "";

    private MyThread thread = null;

    private int sleepTime = 1000;

    @Override
    public void onCreate() {
        super.onCreate();
        if(thread == null)
        {
            thread = new MyThread();
            thread.start();
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent != null)
        {
            Bundle bundle = intent.getExtras();
            if(bundle != null)
            {
                packageName = bundle.getString("packageName");
                sleepTime = bundle.getInt("sleepTime");
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if(thread != null)
        {
            thread.stopThread();
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class MyThread extends Thread
    {
        public Boolean running = true;
        public void run()
        {
            while (running)
            {
                check();
                try
                {
                    Thread.sleep(sleepTime);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
        public void stopThread()
        {
            running = false;
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            interrupted();
        }
    }

    private void check()
    {
        Boolean isAppRunning = false;
        ActivityManager am = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(50);
        for (ActivityManager.RunningTaskInfo info : list)
        {
            if (info.topActivity.getPackageName().equals(packageName) && info.baseActivity.getPackageName().equals(packageName))
            {
                isAppRunning = true;
                break;
            }
        }
        if(!isAppRunning)
        {
            Intent intent = this.getPackageManager().getLaunchIntentForPackage(packageName);
            if (intent == null)
                return;
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
