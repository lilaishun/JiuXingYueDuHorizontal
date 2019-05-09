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

public class RiAdapter extends BaseAdapter {
    private List<String> list=new ArrayList<>();
    private List<String> nain=new ArrayList<>();
    private List<String> yue=new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;
    int ri=1;
    String time="";
    public RiAdapter(Context context){
        mContext=context;
        mInflater = LayoutInflater.from(context);
        for (int i = 0; i <31 ; i++) {
            ri+=i;
            list.add(ri+"");
        }
    }

    public void setList(List<String> list1,List<String> list2,int nianIndex,int yueIndex) {
        this.nain = list1;
        this.yue = list1;
        if(nianIndex%4==0){

            if(yueIndex==2){
                list.clear();
                for (int i = 0; i <29 ; i++) {
                    ri+=i;
                    list.add(ri+"");
                }
            }
        }else{
            if(yueIndex==2){
                list.clear();
                for (int i = 0; i <28 ; i++) {
                    ri+=i;
                    list.add(ri+"");
                }
            }
        }
        if(yueIndex==1||yueIndex==3||yueIndex==5||yueIndex==7||yueIndex==8||yueIndex==10||yueIndex==12){
            list.clear();
            for (int i = 0; i <31 ; i++) {
                ri+=i;
                list.add(ri+"");
            }
        }else if(yueIndex==2||yueIndex==4||yueIndex==6||yueIndex==9||yueIndex==10||yueIndex==11){
            list.clear();
            for (int i = 0; i <30 ; i++) {
                ri+=i;
                list.add(ri+"");
            }
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
