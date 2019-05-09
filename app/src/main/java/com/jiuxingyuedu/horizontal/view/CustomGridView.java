package com.jiuxingyuedu.horizontal.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

public class CustomGridView  extends GridView {

    public CustomGridView(Context context) {
        super(context);
    }

    public CustomGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //  AT_MOST参数表示控件可以自由调整大小，最大不超过Integer.MAX_VALUE/4

        int height=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, height);
    }

    int dX, dY;
    long dTime;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dX = (int) ev.getRawX();
                dY = (int) ev.getRawY();
                dTime = System.currentTimeMillis();

                break;
            case MotionEvent.ACTION_UP:
                int mx = (int)(ev.getRawX() - dX);
                int my = (int) (ev.getRawY() - dY);
                System.out.println("mx===="+mx);
                int time = (int) (System.currentTimeMillis() - dTime);
                System.out.println("time===="+time);
                if (Math.abs(mx) <70 && Math.abs(my)  <= 70 && time < 300) {

                    return false;
                }else{
                    if(mx<0){
                        if(onItemListener!=null){
                            onItemListener.onItemRight();

                        }
                        System.out.println("往左画===");
                    }else if(mx>200){
                        if(onItemListener!=null){
                            onItemListener.onItemLeft();
                        }
                        System.out.println("往右画===");
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private OnRightListener onItemListener;

    public void setOnRightItemListener(OnRightListener listener) {
        onItemListener = listener;
    }
    public interface OnRightListener {
        void onItemRight();
        void onItemLeft();
    }

}
