package com.jiuxingyuedu.horizontal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiuxingyuedu.horizontal.Activity.NewsDetailActivity;
import com.jiuxingyuedu.horizontal.Bean.DateNews;
import com.jiuxingyuedu.horizontal.Bean.NewsNean;
import com.jiuxingyuedu.horizontal.R;
import com.jiuxingyuedu.horizontal.Util.Configs;
import com.jiuxingyuedu.horizontal.Util.DataUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class AllFragmentAdapter extends BaseAdapter {
    private List<NewsNean> list=new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;
    String time="";
    String SubTime="";
    String logo1="";
    private Map<String, DateNews> dateNews2=new HashMap<>();
    public AllFragmentAdapter(Context context, Map<String, DateNews> dateNews){
        mContext=context;
        this.dateNews2=dateNews;
        mInflater = LayoutInflater.from(context);
    }
    public  void SetDate(List<NewsNean> list1){
        this.list.clear();
        this.list.addAll(list1);
        System.out.println("list==----==="+list.size());
        for (int i = 0; i <list.size() ; i++) {
            System.out.println("=Name--======"+list.get(i).getName());
        }

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 8;
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
            convertView = mInflater.inflate(R.layout.fragment_gv_item, parent, false); //加载布局
            holder = new ViewHolder();

            holder.new_name = (TextView) convertView.findViewById(R.id.new_name);
            holder.new_time = (TextView) convertView.findViewById(R.id.new_time);
            holder.read_Bt = (ImageView) convertView.findViewById(R.id.read_Bt);
            holder.ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
            holder.news_item_iv = (ImageView) convertView.findViewById(R.id.news_item_iv);
holder.ll_news=convertView.findViewById(R.id.ll_news);
            convertView.setTag(holder);
        } else {   //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (ViewHolder) convertView.getTag();
        }
        if(position<list.size()){
            holder.ll_news.setVisibility(View.VISIBLE);

        final NewsNean newsNean = list.get(position);
        if(newsNean!=null){
            String name = newsNean.getName();
            if(null!=name){
                String subName="";
                if(name.length()>5){
                    String substring = name.substring(0, 5);
                    subName=substring+"...";
                }else{
                    subName=name;
                }
                holder.new_name.setText(subName);
            }

             logo1 = list.get(position).getLogo();

            if(null!=logo1){
                //   //http://online.9stars.cn/PaperImages/2019/04/23/N00001/ids.jpg
                //                //http://d.9stars.cn/PaperCover/N00018_2015-04-27.jpg
                if(logo1.contains("PaperImages")&&logo1.contains("N0")){
                    String paperImages = logo1.substring(logo1.indexOf("PaperImages") + 1);
                    String substring = paperImages.substring(paperImages.indexOf("/") + 1, paperImages.lastIndexOf("/"));
                    SubTime = substring.substring(0, substring.lastIndexOf("/"));
                    System.out.println("paperImages====="+SubTime);
                }else if(logo1.contains("PaperCover")){
                    String paperImages = logo1.substring(logo1.indexOf("_") + 1,logo1.lastIndexOf("."));
                    SubTime = paperImages.replaceAll("-", "/");
                    System.out.println("paperImages==---==="+SubTime);
                }
            }
            DateNews dateNews = this.dateNews2.get(newsNean.getId());
            if(null!=dateNews){

                final String online = dateNews.getOnline();

                final String logo = Configs.BaseUrl+dateNews.getUrl();
                System.out.println("==========logo===="+logo);
            Glide.with(mContext).load(logo).error(R.mipmap.face).into(holder.news_item_iv);
            time=dateNews.getDate();
                time = time.replaceAll("-", "/");
                holder.new_time.setText(time);


        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logo1 = list.get(position).getLogo();

                if(null!=logo1){
                    //   //http://online.9stars.cn/PaperImages/2019/04/23/N00001/ids.jpg
                    //                //http://d.9stars.cn/PaperCover/N00018_2015-04-27.jpg
                    if(logo1.contains("PaperImages")&&logo1.contains("N0")){
                        String paperImages = logo1.substring(logo1.indexOf("PaperImages") + 1);
                        String substring = paperImages.substring(paperImages.indexOf("/") + 1, paperImages.lastIndexOf("/"));
                        SubTime = substring.substring(0, substring.lastIndexOf("/"));
                        System.out.println("paperImages====="+SubTime);
                    }else if(logo1.contains("PaperCover")){
                        String paperImages = logo1.substring(logo1.indexOf("_") + 1,logo1.lastIndexOf("."));
                        SubTime = paperImages.replaceAll("-", "/");
                        System.out.println("paperImages==---==="+SubTime);
                    }
                }
                Intent intent = new Intent();
                intent.setClass(mContext, NewsDetailActivity.class);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                DateNews dateNews = dateNews2.get(newsNean.getId());
                time=dateNews.getDate();
                time = time.replaceAll("-", "/");
                intent.putExtra("time", time);
                if(position<list.size()) {
                    intent.putExtra("name", list.get(position).getName());
                }

                intent.putExtra("logo", logo);
                intent.putExtra("online", online);
                intent.putExtra("id",list.get(position).getId());
intent.putExtra("SubTime",SubTime);
                System.out.println("logo2===="+list.get(position).getLogo());
                String urlfilter = list.get(position).getUrlfilter();
                intent.putExtra("urlfilter",urlfilter);
                mContext.startActivity(intent);
            }
        });
        }else{

                if(null==logo1){
                    logo1="";
                }
                Glide.with(mContext).load(logo1).error(R.mipmap.face).into(holder.news_item_iv);

              //  time= DataUtil.DateTime();
                holder.new_time.setText(SubTime);

                System.out.println("time========-----"+time);
                holder.ll_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logo1 = list.get(position).getLogo();

                        if(null!=logo1){
                            //   //http://online.9stars.cn/PaperImages/2019/04/23/N00001/ids.jpg
                            //                //http://d.9stars.cn/PaperCover/N00018_2015-04-27.jpg
                            if(logo1.contains("PaperImages")&&logo1.contains("N0")){
                                String paperImages = logo1.substring(logo1.indexOf("PaperImages") + 1);
                                String substring = paperImages.substring(paperImages.indexOf("/") + 1, paperImages.lastIndexOf("/"));
                                SubTime = substring.substring(0, substring.lastIndexOf("/"));
                                System.out.println("paperImages====="+SubTime);
                            }else if(logo1.contains("PaperCover")){
                                String paperImages = logo1.substring(logo1.indexOf("_") + 1,logo1.lastIndexOf("."));
                                SubTime = paperImages.replaceAll("-", "/");
                                System.out.println("paperImages==---==="+SubTime);
                            }
                        }
                        if(null==SubTime||"".equals(SubTime)){
                            SubTime= DataUtil.DateTime();

                        }
                        Intent intent = new Intent();
                        intent.setClass(mContext, NewsDetailActivity.class);
                        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("time", SubTime);
                        if(position<list.size()) {
                            intent.putExtra("name", list.get(position).getName());

                        }
                        System.out.println("logo2===="+list.get(position).getLogo());
                        intent.putExtra("logo", logo1);
                        intent.putExtra("online", "");
                        intent.putExtra("id",list.get(position).getId());
                        intent.putExtra("SubTime",SubTime);
                        String urlfilter = list.get(position).getUrlfilter();
                        intent.putExtra("urlfilter",urlfilter);
                        mContext.startActivity(intent);
                    }
                });
            }
        }
    }else{
            holder.ll_news.setVisibility(View.INVISIBLE);
        }
        return convertView ;
    }
    class ViewHolder{
ImageView news_item_iv;
TextView new_name,new_time;
ImageView read_Bt;
LinearLayout ll_news,ll_item;
    }
}
