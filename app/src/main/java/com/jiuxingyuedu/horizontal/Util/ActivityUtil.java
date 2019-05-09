package com.jiuxingyuedu.horizontal.Util;

import android.app.Activity;
import android.os.Process;
import android.view.KeyEvent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ActivityUtil {
    private List<Activity> activityList = new ArrayList<>();
    private static ActivityUtil instance;

    // 单例模式中获取唯一的ExitApplication实例
    public static synchronized ActivityUtil getInstance() {
        if (null == instance) {
            instance = new ActivityUtil();
        }
        return instance;
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        if (activityList == null)
            activityList = new ArrayList<>();
        activityList.add(activity);
    }

    // 移除Activity
    public void removeActivity(Activity activity) {
        if (activityList != null)
            activityList.remove(activity);
    }

    // 遍历所有Activity并finish
    public void exitSystem() {
        for (Activity activity : activityList) {
            if (activity != null)
                activity.finish();
        }
        // 退出进程
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

//    // 设置返回按钮的监听事件
//    private long exitTime = 0;
//
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        // 监听返回键，点击两次退出程序
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if ((System.currentTimeMillis() - exitTime) > 5000) {
//                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_LONG).show();
//                exitTime = System.currentTimeMillis();
//            } else {
//                ActivityUtil.getInstance().exitSystem();
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

}
