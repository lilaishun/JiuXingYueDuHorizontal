package com.jiuxingyuedu.horizontal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiuxingyuedu.horizontal.Bean.NewsDateilBean;
import com.jiuxingyuedu.horizontal.R;
import com.jiuxingyuedu.horizontal.Util.Configs;

import java.util.ArrayList;
import java.util.List;

public class Detail_NewsAdapter extends BaseAdapter {
    private List<NewsDateilBean> list=new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;
    String time="";
    private int Index=0;
    public Detail_NewsAdapter(Context context){
        mContext=context;
        mInflater = LayoutInflater.from(context);
    }
    public  void SetDate(List<NewsDateilBean> list1){
        this.list=list1;
        System.out.println("list====="+list.size());
        notifyDataSetChanged();
    }
    public  void SelectIndex(int index){
        Index=index;
        notifyDataSetChanged();
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
                         convertView = mInflater.inflate(R.layout.detail_item, parent, false); //加载布局
                       holder = new ViewHolder();


                         holder.iv_detail_item = (ImageView) convertView.findViewById(R.id.iv_detail_item);
                     holder.ll_detail_item = (LinearLayout) convertView.findViewById(R.id.ll_detail_item);
                     holder.news_nums = (TextView) convertView.findViewById(R.id.news_nums);
                       convertView.setTag(holder);
                     } else {  //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
                         holder = (ViewHolder) convertView.getTag();
                     }
        final NewsDateilBean newsNean = list.get(position);
if(Index==position){
   // holder.iv_detail_item.setScaleType(ImageView.ScaleType.CENTER_CROP);
    holder.ll_detail_item.setLayoutParams(new LinearLayout.LayoutParams(130, 195));
    holder.ll_detail_item.setBackgroundResource(R.drawable.shape_bian_rad);

}else{
    holder.ll_detail_item.setBackgroundResource(R.drawable.shape_bian_while);
    holder.ll_detail_item.setLayoutParams(new LinearLayout.LayoutParams(120, 190));
   // holder.iv_detail_item.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
}

        holder.news_nums.setText("A"+(position+1));

        //
        Glide.with(mContext).load(Configs.BaseUrl+newsNean.getPreview()).into(holder.iv_detail_item);

        return convertView ;
    }
    class ViewHolder{
ImageView iv_detail_item;
LinearLayout ll_detail_item;
TextView news_nums;
    }
}
