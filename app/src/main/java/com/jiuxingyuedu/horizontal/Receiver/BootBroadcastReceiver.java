package com.jiuxingyuedu.horizontal.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jiuxingyuedu.horizontal.Activity.MainActivity;

/**
 * Created by 马吉尧 on 2018/3/29.
 * 邮箱:1505508999@qq.com
 */

public class BootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
      if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            //开机启动班牌
            Intent i = new Intent(context, MainActivity.class);
            //开机启动NIDS
//            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
