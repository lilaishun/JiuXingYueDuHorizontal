package com.jiuxingyuedu.horizontal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiuxingyuedu.horizontal.R;

import java.util.ArrayList;
import java.util.List;

public class YueAdapter extends BaseAdapter {
    private List<String> list=new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;
    int nian=1;
    String time="";
    public YueAdapter(Context context){
        mContext=context;
        mInflater = LayoutInflater.from(context);
        for (int i = 0; i <12 ; i++) {
            nian+=i;
            list.add(nian+"");
        }
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
                 if (convertView == null) {
                         convertView = mInflater.inflate(R.layout.time_item, parent, false); //加载布局
                       holder = new ViewHolder();

                         holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);


                       convertView.setTag(holder);
                     } else {   //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
                         holder = (ViewHolder) convertView.getTag();
                     }


        holder.tv_time.setText(list.get(position));

        //

        return convertView ;
    }
    class ViewHolder{

TextView tv_time;

    }
}
