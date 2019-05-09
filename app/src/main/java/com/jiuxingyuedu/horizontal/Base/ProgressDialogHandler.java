package com.jiuxingyuedu.horizontal.Base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;

import com.jiuxingyuedu.horizontal.Util.DensityUtil;
import com.jiuxingyuedu.horizontal.R;

public class ProgressDialogHandler extends Handler {

    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private Dialog pd;

    private Context context;
    private boolean cancelable;
    private ProgressCancelListener mProgressCancelListener;
    private boolean isShow;

    public ProgressDialogHandler(Context context, ProgressCancelListener mProgressCancelListener,
                                 boolean cancelable, boolean isShow) {
        super();
        this.context = context;
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
        this.isShow=isShow;
    }

    private void initProgressDialog(){
        System.out.println("initProgressDialog");
        if (pd == null) {
       //     System.out.println("pd == null==initProgressDialog");
            pd = new Dialog(context, R.style.prograssBarStyle);

            View view= View.inflate(context,R.layout.dialog_loading,null);

            pd.setContentView(view);

            pd.setCancelable(cancelable);

            if (cancelable) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        mProgressCancelListener.onCancelProgress();
                    }
                });
            }

            if ((!pd.isShowing())&&isShow) {
                pd.show();
                WindowManager.LayoutParams params = pd.getWindow().getAttributes();
                params.width = (int) DensityUtil.dip2px(120);
                params.height=(int) DensityUtil.dip2px(90);
                params.x = 260; // 新位置X坐标
             //   params.y = 100; // 新位置Y坐标
                pd.getWindow().setAttributes(params);
            }
        }
    }

    private void dismissProgressDialog(){
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }

}
