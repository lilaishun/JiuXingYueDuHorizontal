package com.jiuxingyuedu.horizontal.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuxingyuedu.horizontal.Bean.NewsNean;
import com.jiuxingyuedu.horizontal.R;

import java.util.ArrayList;
import java.util.List;

public class HorizontalListViewAdapter extends BaseAdapter {

    private final Context context;
    private final int screenWidth;
    private  List<NewsNean> NewsNeanList = new ArrayList<>();;

    public HorizontalListViewAdapter(Context applicationContext, int screenWidth, List<NewsNean> newsNeanList) {
        this.context = applicationContext;
        this.screenWidth = screenWidth;
        this.NewsNeanList = newsNeanList;
    }

    @Override
    public int getCount() {
        return NewsNeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return NewsNeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_horizontallistview, null);

            holder = new ViewHolder();
            holder.rl=convertView.findViewById(R.id.rl);
            holder.tv=convertView.findViewById(R.id.tv);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        //计算每条的宽度
       // System.out.println("宽度======"+NewsNeanList.get(position).getName());
        holder.tv.setText(NewsNeanList.get(position).getName()+"");

        return convertView;
    }

    public  void SetData(List<NewsNean> newsNeanList){
        this.NewsNeanList = newsNeanList;
        notifyDataSetChanged();
    }
}

class ViewHolder {

    TextView tv;

    RelativeLayout rl;



}
