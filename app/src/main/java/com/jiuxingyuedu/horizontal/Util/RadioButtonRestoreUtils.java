package com.jiuxingyuedu.horizontal.Util;

import android.content.Context;
import android.support.annotation.IdRes;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.jiuxingyuedu.horizontal.R;
/**
 * Created by 我的电脑 on 2019/4/3.
 */

public class RadioButtonRestoreUtils {
    public static void restoredRadioButton(@IdRes int checkedId, RadioGroup radioGroup, RadioGroup.OnCheckedChangeListener listener, Context mContext) {
        radioGroup.setOnCheckedChangeListener(null);//checked监听事件失效

        RadioButton radioButton = (RadioButton) radioGroup.findViewById(checkedId);
        //清除radioButton的选中状态，只保留背景色
        radioButton.setClickable(true);
        radioButton.setChecked(false);
        int childCount = radioGroup.getChildCount();
        RadioButton childAt = null;
        //将所有radioButton背景色置为未选中状态,目的是清除之前的设置
        for (int i = 0; i < childCount; i++) {
            childAt = (RadioButton) radioGroup.getChildAt(i);
        //    childAt.setBackgroundResource(R.drawable.ih_airconditioner_tv_normal);
            childAt.setTextColor(mContext.getResources().getColor(R.color.black1));
            childAt.setTextSize(18);
        }
        //将之前选中的radioButton背景色设为选中状态
  //      radioButton.setBackgroundResource(R.drawable.ih_airconditioner_tv_focused);
        radioButton.setTextColor(mContext.getResources().getColor(R.color.black));
        radioButton.setTextSize(20);
        radioGroup.setOnCheckedChangeListener(listener);//重新添加
    }


}


