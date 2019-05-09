package com.jiuxingyuedu.horizontal.Base;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.jiuxingyuedu.horizontal.Receiver.NetBroadcastReceiver;
import com.jiuxingyuedu.horizontal.Util.ActivityUtil;
import com.jiuxingyuedu.horizontal.Util.DataUtil;

import java.util.Timer;
import java.util.TimerTask;

public abstract  class BaseActivity extends FragmentActivity implements NetBroadcastReceiver.NetChangeListener {
    public static NetBroadcastReceiver.NetChangeListener netEvent;// 网络状态改变监听事件
    private Timer timer;
    private TimerTask task;
    private int index;
    @Override
    protected void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        // 添加到Activity工具类
        ActivityUtil.getInstance().addActivity(this);
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
             //   System.out.println("执行了========");
                initTask();

            }
        };
        if(null!=timer){
            timer.schedule(task,0,60000*60*2);
        }
        // 初始化netEvent
        netEvent = this;

        // 执行初始化方法
        init();

    }
    // 抽象 - 初始化方法，可以对数据进行初始化
    protected abstract void init();

    protected abstract void initTask();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 点击手机上的返回键，返回上一层
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            ActivityUtil.getInstance().removeActivity(this);
        }
        return super.onKeyDown(keyCode, event);
    }
    /**
     * 权限检查方法，false代表没有该权限，ture代表有该权限
     */
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    /**
     * 权限请求方法
     */
    public void requestPermission(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    /**
     * 处理请求权限结果事件
     *
     * @param requestCode  请求码
     * @param permissions  权限组
     * @param grantResults 结果集
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doRequestPermissionsResult(requestCode, grantResults);
    }

    /**
     * 处理请求权限结果事件
     *
     * @param requestCode  请求码
     * @param grantResults 结果集
     */
    public void doRequestPermissionsResult(int requestCode, @NonNull int[] grantResults) {
    }

    /**
     * 网络状态改变时间监听
     *
     * @param netWorkState true有网络，false无网络
     */
    @Override
    public void onNetChange(boolean netWorkState) {
    }

    @Override
    protected void onDestroy() {
        if(null!=timer){
            System.out.println("===========================销毁了=");
            timer.cancel();
            timer.purge();
            index=0;
        }
        // Activity销毁时，提示系统回收
        // System.gc();
        netEvent = null;
        // 移除Activity
        ActivityUtil.getInstance().removeActivity(this);
        super.onDestroy();
    }

}
