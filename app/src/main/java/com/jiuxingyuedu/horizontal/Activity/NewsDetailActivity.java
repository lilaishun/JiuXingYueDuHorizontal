package com.jiuxingyuedu.horizontal.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.jiuxingyuedu.horizontal.Adapter.Detail_NewsAdapter;
import com.jiuxingyuedu.horizontal.App.MyApplication;
import com.jiuxingyuedu.horizontal.Base.BaseActivity;
import com.jiuxingyuedu.horizontal.Base.ProgressCancelListener;
import com.jiuxingyuedu.horizontal.Base.ProgressDialogHandler;
import com.jiuxingyuedu.horizontal.Bean.DateNews;
import com.jiuxingyuedu.horizontal.Bean.MessageEvent;
import com.jiuxingyuedu.horizontal.Bean.NewsDateilBean;
import com.jiuxingyuedu.horizontal.Bean.UserEvent;
import com.jiuxingyuedu.horizontal.R;
import com.jiuxingyuedu.horizontal.Util.BitMap;
import com.jiuxingyuedu.horizontal.Util.Configs;
import com.jiuxingyuedu.horizontal.Util.DataUtil;
import com.jiuxingyuedu.horizontal.Util.DownloadUtil;
import com.jiuxingyuedu.horizontal.Util.TimeUtils;
import com.jiuxingyuedu.horizontal.Util.Utils;
import com.jiuxingyuedu.horizontal.view.CustomDatePicker;
import com.jiuxingyuedu.horizontal.view.MyWebView;
import com.jiuxingyuedu.horizontal.view.MyWheelView;
import com.lidong.pdf.listener.OnDrawListener;
import com.lidong.pdf.listener.OnLoadCompleteListener;
import com.lidong.pdf.listener.OnPageChangeListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tencent.smtt.sdk.TbsReaderView;

import org.greenrobot.eventbus.EventBus;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;



public class NewsDetailActivity extends BaseActivity implements OnPageChangeListener
        , OnLoadCompleteListener, OnDrawListener, TbsReaderView.ReaderCallback, ProgressCancelListener {
    public Map<String, DateNews> DateNews2 = new HashMap<>();
    public Map<String, DateNews> DateNews3 = new HashMap<>();
    private boolean Erro;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    String obj1 = (String) msg.obj;
                    getData3(obj1);
                    iv_last.setVisibility(View.INVISIBLE);
detail_newsAdapter.SetDate(NewsDateilList);
if(NewsDateilList.size()>0){
    iv_next.setVisibility(View.VISIBLE);
}else{
    iv_next.setVisibility(View.INVISIBLE);
}
                    detail_newsAdapter.SelectIndex(0);
                   // x5web.loadUrl(NewsDateilList.get(0).getPdf());
                    //x5web.loadUrl("www.baidu.com");
                    CurrentIndex=0;
                    mWebView.setVisibility(View.INVISIBLE);

                    displayFromFile1(NewsDateilList.get(CurrentIndex).getUrl(),CurrentIndex);

                    break;
                case 2:
                    Erro=false;
                    System.out.println("==================2=--=="+online);
                        if (!ISFrist&&"".equals(online)&&(null!=subTime||!"".equals(subTime))) {
                            if(null==online||"".equals(online)) {

                                System.out.println("subTime==="+subTime);
                            getData2(subTime);
                            time_detail.setText(subTime);
                            }else{

                                iv_next.setVisibility(View.INVISIBLE);
                                iv_last.setVisibility(View.INVISIBLE);
                                ArrayList<NewsDateilBean> NewsDateilList=new ArrayList<>();
                                detail_newsAdapter.SetDate(NewsDateilList);
//        mWebView.loadUrl("http://yq.leiju.com.cn/register");
                                rl_iv.setVisibility(View.GONE);
                                if(null==online||"".equals(online)){
                                    no_data.setVisibility(View.VISIBLE);
                                }else {

                                    no_data.setVisibility(View.GONE);

                                    mWebView.setVisibility(View.VISIBLE);
                                    mWebView.setVisibility(View.VISIBLE);
                                    mWebView.getSettings().setSupportZoom(true);//缩放
                                    mWebView.getSettings().setBuiltInZoomControls(true);
                                    mWebView.getSettings().setDisplayZoomControls(false);//不显示控制器
                                    mWebView.getSettings().setUseWideViewPort(true);


//        System.out.println("online=="+"http://interface.9stars.cn/"+fileUrl);
//        mWebView.loadUrl("http://interface.9stars.cn/"+fileUrl);
//                        Elements body = doc1.select("body");
//                        body.html("<div><img width=\"100%\" height=\"auto\" src=\"" + online + "\" onclick='javascript:injectObject.close();' />"
//                                + "</div>");
//                        mWebView.loadDataWithBaseURL(
//                                "file:///android_asset/", doc1.html(),
//                                "text/html", "UTF-8", "");
                                    showProgressDialog();
                                    mWebView.loadUrl(online);
                                }
                            }

                    }else{


                    iv_next.setVisibility(View.INVISIBLE);
                    iv_last.setVisibility(View.INVISIBLE);
                    ArrayList<NewsDateilBean> NewsDateilList=new ArrayList<>();
                    detail_newsAdapter.SetDate(NewsDateilList);
//        mWebView.loadUrl("http://yq.leiju.com.cn/register");
                    rl_iv.setVisibility(View.GONE);

                            System.out.println("==================2==="+online);
                    if(null==online||"".equals(online)){
                        no_data.setVisibility(View.VISIBLE);
                    }else {
                        no_data.setVisibility(View.GONE);

                        mWebView.setVisibility(View.VISIBLE);
                        mWebView.setVisibility(View.VISIBLE);
                        mWebView.getSettings().setSupportZoom(true);//缩放
                        mWebView.getSettings().setBuiltInZoomControls(true);
                        mWebView.getSettings().setDisplayZoomControls(false);//不显示控制器
                        mWebView.getSettings().setUseWideViewPort(true);


//        System.out.println("online=="+"http://interface.9stars.cn/"+fileUrl);
//        mWebView.loadUrl("http://interface.9stars.cn/"+fileUrl);
//                        Elements body = doc1.select("body");
//                        body.html("<div><img width=\"100%\" height=\"auto\" src=\"" + online + "\" onclick='javascript:injectObject.close();' />"
//                                + "</div>");
//                        mWebView.loadDataWithBaseURL(
//                                "file:///android_asset/", doc1.html(),
//                                "text/html", "UTF-8", "");
                        showProgressDialog();
                        mWebView.loadUrl(online);
                    }
                    }


//                    if(null==online||"".equals(online)){
//                        no_data.setVisibility(View.VISIBLE);
//                    }else{
//                        no_data.setVisibility(View.GONE);
//                    }

                    break;
                case 5:
                    Bitmap obj = (Bitmap) msg.obj;
                    System.out.println("===================bitmap");
                    rl_iv.setVisibility(View.VISIBLE);
                    rl_iv.setImageBitmap(obj);

                    iv_next.setFocusable(true);
                    iv_last.setFocusable(true);
                    dismissProgressDialog();
                    break;
                case 6:
                    DateNews dateNews = DateNews2.get(id2);
                    System.out.println("ID2======"+id2);
                    if(null!=dateNews){
                        final String logo = Configs.BaseUrl+dateNews.getUrl();
                        System.out.println("==========logo===="+logo);
                        Glide.with(NewsDetailActivity.this).load(logo).error(R.mipmap.face).into(logo_iv);
                        String online2 = dateNews.getOnline();
                        if(null==online2){
                            online="";
                        }else{
                            online=online2;
                        }


if(ISFrist){
    getData(DataTime);
}else{
    ISFrist=true;
    getData(subTime);
}

                    }else{
                        System.out.println("没有匹配到相应的报纸ID");
                        Glide.with(NewsDetailActivity.this).load("").error(R.mipmap.face).into(logo_iv);
                        online="";

                        if(ISFrist){

                            getData(DataTime);
                        }else{
                            ISFrist=true;
                            getData(subTime);
                        }
                    }


                    break;
                case 7:
                    Glide.with(NewsDetailActivity.this).load("").error(R.mipmap.face).into(logo_iv);
                    online="";
                    System.out.println("=================7=="+ISFrist);
                    if(ISFrist) {
                        getData(DataTime);
                    }else{
                        ISFrist=true;
                        getData(subTime);
                    }
                    break;
                case 9:
                    DateNews dateNews3 = DateNews3.get(id2);
                    System.out.println("ID2======"+id2);
                    if(null!=dateNews3){
                        final String logo = Configs.BaseUrl+dateNews3.getUrl();
                        System.out.println("==========logo===="+logo);
                        Glide.with(NewsDetailActivity.this).load(logo).error(R.mipmap.face).into(logo_iv);
                        String online2 = dateNews3.getOnline();
                        if(null==online2){
                            online="";
                        }else{
                            online=online2;
                        }
                    }
                    break;
            }
        }
    };
    private ArrayList<NewsDateilBean> NewsDateilList=new ArrayList<>();
    @BindView(R.id.name_detail)
    TextView name_detail;
    @BindView(R.id.time_detail)
    TextView time_detail;
    @BindView(R.id.wangqi_detail)
    ImageView wangqi_detail;
    @BindView(R.id.iv_last)
    ImageView iv_last;
    @BindView(R.id.iv_next)
    ImageView iv_next;
    @BindView(R.id.iv_close)
    ImageView iv_close;
    @BindView(R.id.queren)
    Button queren;
    @BindView(R.id.gotoDay)
    Button gotoDay;
    @BindView(R.id.ok_btn)
    Button ok_btn;
    @BindView(R.id.gv_detail)
    GridView gv_detail;
//    @BindView(R.id.x5web)
//    com.lidong.pdf.PDFView x5web;
    @BindView(R.id.ll_win)
    LinearLayout ll_win;
    @BindView(R.id.rl_root)
    MyWebView   mWebView;
    @BindView(R.id.nian)
    com.jiuxingyuedu.horizontal.view.MyWheelView nian;
    @BindView(R.id.yue)
    com.jiuxingyuedu.horizontal.view.MyWheelView yue;
    @BindView(R.id.ri)
    com.jiuxingyuedu.horizontal.view.MyWheelView ri;
    @BindView(R.id.logo_iv)
            ImageView logo_iv;
    @BindView(R.id.iv)
            ImageView iv;
    @BindView(R.id.iv_fangda)
            ImageView iv_fangda;
    @BindView(R.id.rl_iv)
    com.rrtoyewx.touchimageviewlibrary.TouchImageView rl_iv;
@BindView(R.id.no_data)
    RelativeLayout no_data;
    int FangdaCurrentNum=1;
//    org.jsoup.nodes.Document doc1 = null;
int nianNUm=2000;
int yueNum=1;
int riNum=1;
int CurrentYear;
int CurrentMonth;
private boolean ISShowTime;
    private int CurrentIndex=0;//当前展示的位置
    private Detail_NewsAdapter detail_newsAdapter;
    private CustomDatePicker mDatePicker;
    private long endTimestamp;
    private List<String> nianlist=new ArrayList<>();
    private List<String> yuelist=new ArrayList<>();
    private List<String> rilist=new ArrayList<>();
    private int year;
    private int month;
    private String name;
    private TbsReaderView mTbsReaderView;
    private boolean ISFangda;
    private String online;
    private int yearCurrent;
    private int monthCurrent;
    private int dayCurrent;
    private String id2;
    private boolean ISFrist;
private String DataTime;
    private String subTime;
    private String urlfilter;
    private Timer timer;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news_detail);


        ButterKnife.bind(this);

        Calendar calendar1 = Calendar.getInstance();
//获取系统的日期
//年
        yearCurrent = calendar1.get(Calendar.YEAR);
//月

        monthCurrent = calendar1.get(Calendar.MONTH)+1;

//日

        dayCurrent = calendar1.get(Calendar.DAY_OF_MONTH);




        Intent intent = getIntent();
        String time = intent.getStringExtra("time");
        name = intent.getStringExtra("name");
        String logo = intent.getStringExtra("logo");
        online = intent.getStringExtra("online");
        id2 = intent.getStringExtra("id");
        subTime = intent.getStringExtra("SubTime");
        urlfilter = intent.getStringExtra("urlfilter");
        if(null==online||"".equals(online)){
          //  no_data.setVisibility(View.VISIBLE);
        }else{
            no_data.setVisibility(View.GONE);
        }
        no_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCreateTime();

            }
        });
        if(online==null){
            online="";
        }

        time_detail.setText(time);

        String subName="";
        if(name.length()>5){
            String substring = name.substring(0, 5);
            subName=substring+"...";
        }else{
            subName=name;
        }
        name_detail.setText(subName);
        System.out.println("logo===="+logo);
        Glide.with(this).load(logo).error(R.mipmap.face).into(logo_iv);
        ll_win.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCreateTime();

            }
        });
        queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ISShowTime=!ISShowTime;
                ll_win.setVisibility(View.GONE);
                CurrentIndex=0;
                //TODO 加载网络请求
                int year = nian.getCurrentItem()+ 1900 + 70;
                int month = yue.getCurrentItem();
                int day = ri.getCurrentItem();
                int daySize = ri.getItemCount();
                if (day > daySize) {
                    day = day - daySize;
                }
                String m="";
                if((month+1)<10){
                    m="0"+(month+1);
                }else {
                    m= (month+1)+"";
                }
                String Day="";
                if(day+1<10){
                    Day="0"+(day+1);
                }else{
                    Day=(day+1)+"";
                }

                if(year>yearCurrent){
                    Toast.makeText(getApplicationContext(),"请选择小于当前的时间",0).show();
                    return;
                }

                if(year==yearCurrent&&(month+1)>monthCurrent){
                    Toast.makeText(getApplicationContext(),"请选择小于当前的时间",0).show();
                    return;
                }
                if(year==yearCurrent&&(month+1)==monthCurrent&&(day+1)>dayCurrent){
                    Toast.makeText(getApplicationContext(),"请选择小于当前的时间",0).show();
                    return;
                }

                time_detail.setText(year+"/"+m+"/"+Day);
                DataTime=year+"/"+m+"/"+Day;
                ISFrist=true;
                getData2(DataTime);
                setCreateTime();
            }
        });



        WebSettings webSettings = mWebView.getSettings();

        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);

        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(true);
        mWebView.setHorizontalScrollBarEnabled(false);//水平不显示
        mWebView.setVerticalScrollBarEnabled(false); //垂直不显示
        mWebView.getSettings().setJavaScriptEnabled(true);

        webSettings.setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        // 设置可以支持缩放
        mWebView.getSettings().setSupportZoom(true);

        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);// 不使用缓存
        // 设置出现缩放工具
        mWebView.getSettings().setBuiltInZoomControls(false);
        // 为图片添加放大缩小功能
        mWebView.getSettings().setUseWideViewPort(true);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);


      //  mWebView.addJavascriptInterface(new JsObject(), "injectObject");//第4步骤




                            mWebView.setWebChromeClient(new WebChromeClient() {

                        @Override
                        public void onProgressChanged(WebView view, int newProgress) {
                          //  System.out.println("newProgress========"+newProgress);
                            super.onProgressChanged(view, newProgress);

//                            if(newProgress==100){
//                                System.out.println("newProgress======--=="+newProgress);
//                            }
                        }

                    });
                    /* 同上,重写WebViewClient可以监听网页的跳转和资源加载等等... */
                    mWebView.setWebViewClient(new WebViewClient() {


                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                           // if (url.startsWith("scheme:") || url.startsWith("scheme:")) {
//                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                                startActivity(intent);
                         //   }
                            System.out.println( "url==============="+url+"====="+urlfilter);
                            Pattern pattern = Pattern.compile(""+urlfilter);
                            if(pattern.matcher(url).matches()){
                                mWebView.loadUrl(url);
                            }else {

                            }
                            setCreateTime();
                            return true;
                        }

                        @Override
                        public void onPageFinished(WebView view, String url) {
                            System.out.println("=============onPageFinished");
                            setCreateTime();
                            if(!Erro) {
                                no_data.setVisibility(View.GONE);
                            }
                            dismissProgressDialog();
                         //   imgReset();
                            super.onPageFinished(view, url);

                        }

                        @Override
                        public void onPageStarted(WebView view, String url, Bitmap favicon) {

                            super.onPageStarted(view, url, favicon);
                        }

                        @Override
                        public void onReceivedError(WebView view, int errorCode,  String description, String failingUrl) {
                            super.onReceivedError(view, errorCode, description, failingUrl);
                            no_data.setVisibility(View.VISIBLE);
                            Erro=true;
                            System.out.println("=============onReceivedError");
                            mWebView.setVisibility(View.INVISIBLE);
                            dismissProgressDialog();
                        }


                    });


        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FangdaCurrentNum=1;
                mWebView.setInitialScale(100);//
                System.out.println("点击了=========");
                iv_fangda.setVisibility(View.INVISIBLE);
                iv.setVisibility(View.INVISIBLE);
                setCreateTime();
            }
        });
        iv_fangda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FangdaCurrentNum>3){
                    FangdaCurrentNum=3;
                }
                mWebView.setInitialScale(FangdaCurrentNum*100);//为25%，最小缩放等级
                FangdaCurrentNum+=1;
                iv.setVisibility(View.VISIBLE);
                setCreateTime();
            }
        });
        gotoDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentIndex=0;

                ISShowTime=!ISShowTime;
                ll_win.setVisibility(View.GONE);


                nian.setData(TimeUtils.getItemList(TimeUtils.TYPE_YEAR));
                nian.setBackground(getDrawable(R.mipmap.past_calender_datebg));
                nian.setOnSelectListener(new MyWheelView.SelectListener() {
                    @Override
                    public void onSelect(int index, String text) {
                        year = index + TimeUtils.MIN_YEAR;

                    }
                });
                yue.setData(TimeUtils.getItemList(TimeUtils.TYPE_MONTH));
                yue.setOnSelectListener(new MyWheelView.SelectListener() {
                    @Override
                    public void onSelect(int index, String text) {
                        month = index + 1;
                        ri.setData(TimeUtils.createdDay(year, month));
                    }
                });
                ri.setData(TimeUtils.getItemList(TimeUtils.TYPE_DAY));

                Date date = new Date(System.currentTimeMillis());
                Log.d("aa", date.getYear() + ">>" + date.getMonth() + ">>" + date.getDate() + ">>" + date.getHours() + ">>" + date.getMinutes());
                nian.setCurrentItem(date.getYear() - 70);
                yue.setCurrentItem(date.getMonth());
                ri.setCurrentItem(date.getDate() - 1);

                int year = nian.getCurrentItem();
                int month = yue.getCurrentItem();
                int day = ri.getCurrentItem();
                int daySize = ri.getItemCount();
                if (day > daySize) {
                    day = day - daySize;
                }


                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year + 1900 + 70);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DATE, day + 1);


                System.out.println("======"+year+"/"+(month+1)+"/"+(day+1));
                String m="";
                if((month+1)<10){
                    m="0"+(month+1);
                }else {
                    m= (month+1)+"";
                }
                String Day="";
                if(day+1<10){
                    Day="0"+(day+1);
                }else{
                    Day=(day+1)+"";
                }
                time_detail.setText((year+ 1900 + 70)+"/"+m+"/"+Day);
                DataTime=(year+ 1900 + 70)+"/"+m+"/"+Day;
                ISFrist=true;
                getData2(DataTime);
                setCreateTime();
            }
        });




        nian.setData(TimeUtils.getItemList(TimeUtils.TYPE_YEAR));
        nian.setBackground(getDrawable(R.mipmap.past_calender_datebg));
        nian.setOnSelectListener(new MyWheelView.SelectListener() {
            @Override
            public void onSelect(int index, String text) {
                year = index + TimeUtils.MIN_YEAR;
                setCreateTime();
            }
        });
        yue.setData(TimeUtils.getItemList(TimeUtils.TYPE_MONTH));
        yue.setOnSelectListener(new MyWheelView.SelectListener() {
            @Override
            public void onSelect(int index, String text) {
                month = index + 1;
                ri.setData(TimeUtils.createdDay(year, month));
                setCreateTime();
            }
        });
        ri.setData(TimeUtils.getItemList(TimeUtils.TYPE_DAY));

        Date date = new Date(System.currentTimeMillis());
        Log.d("aa", date.getYear() + ">>" + date.getMonth() + ">>" + date.getDate() + ">>" + date.getHours() + ">>" + date.getMinutes());
        nian.setCurrentItem(date.getYear() - 70);
        yue.setCurrentItem(date.getMonth());
        ri.setCurrentItem(date.getDate() - 1);

        int year = nian.getCurrentItem();
        int month = yue.getCurrentItem();
        int day = ri.getCurrentItem();
        int daySize = ri.getItemCount();
        if (day > daySize) {
            day = day - daySize;
        }


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year + 1900 + 70);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, day + 1);
        System.out.println("year====="+year+"==month="+month+"====day="+day);
        detail_newsAdapter = new Detail_NewsAdapter(getApplicationContext());
        gv_detail.setAdapter(detail_newsAdapter);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserEvent userEvent = new UserEvent();
                userEvent.setIndex(0);
              //  EventBus.getDefault().post(userEvent);
                MyApplication.ISFORBack=true;
                NewsDetailActivity.this.finish();
                DesTimer();
            }
        });
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserEvent userEvent = new UserEvent();
                userEvent.setIndex(0);
              //  EventBus.getDefault().post(userEvent);
                MyApplication.ISFORBack=true;
                NewsDetailActivity.this.finish();
                DesTimer();
            }
        });

        wangqi_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ISShowTime){
                    ISShowTime=!ISShowTime;
                    ll_win.setVisibility(View.VISIBLE);
                }else{
                    ISShowTime=!ISShowTime;
                    ll_win.setVisibility(View.GONE);
                }
                setCreateTime();

            }
        });


        gv_detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (Utils.isFastClick()) {
                    // 进行点击事件后的逻辑操作
                    detail_newsAdapter.SelectIndex(position);
                    CurrentIndex = position;
                    if (CurrentIndex >= 0) {
                        displayFromFile1(NewsDateilList.get(CurrentIndex).getUrl(), CurrentIndex);

                        detail_newsAdapter.SelectIndex(CurrentIndex);
                    }
                    setCreateTime();
                }
            }
        });
        iv_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isFastClick()) {
                    // 进行点击事件后的逻辑操作

                CurrentIndex=CurrentIndex-1;
                if(CurrentIndex>=0) {
                    displayFromFile1(NewsDateilList.get(CurrentIndex).getUrl(),CurrentIndex);

                    detail_newsAdapter.SelectIndex(CurrentIndex);
                }
                setCreateTime();
            }
            }
        });
        iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isFastClick()) {
                    // 进行点击事件后的逻辑操作
                    CurrentIndex += 1;
                    if (CurrentIndex < NewsDateilList.size()) {
                        System.out.println("pdf=====" + NewsDateilList.get(CurrentIndex).getPdf());
                        displayFromFile1(NewsDateilList.get(CurrentIndex).getUrl(), CurrentIndex);

                        detail_newsAdapter.SelectIndex(CurrentIndex);
                    }
                    setCreateTime();
                }
            }
        });
        getData(time);


    }

    @Override
    public void onCancelProgress() {

    }

    class JsObject {
        JsObject() {
        }

        public String toString() {
            return "injectedObject";
        }

        @JavascriptInterface
        public void close() {
            finish();//finish掉当前的activity
        }
    }
    /**
     * 翻页回调
     * @param page
     * @param pageCount
     */
    @Override
    public void onPageChanged(int page, int pageCount) {
        Toast.makeText( this , "page= " + page +
                " pageCount= " + pageCount , Toast.LENGTH_SHORT).show();
    }

    /**
     * 加载完成回调
     * @param nbPages  总共的页数
     */
    @Override
    public void loadComplete(int nbPages) {
        Toast.makeText( this ,  "加载完成" + nbPages  , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
        // Toast.makeText( MainActivity.this ,  "pageWidth= " + pageWidth + "
        // pageHeight= " + pageHeight + " displayedPage="  + displayedPage , Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void init() {

    }

    @Override
    protected void initTask() {

    }

    /**
     * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
     **/
    private void imgReset() {
        mWebView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                "}" +
                "})()");
    }

    /**
     * 获取打开网络的pdf文件
     * @param fileUrl
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void displayFromFile1(final String fileUrl, int  index ) {
        /**
         *
         * @param context 上下文
         * @param fileUrl 下载完整url
         * @param destFileDir  SD路径
         * @param destFileName  文件名
         * @param mFileRelativeUrl 下载相对地址
         * （我们从服务器端获取到的数据都是相对的地址）例如： "filepath": "/movie/20180511/1526028508.txt"
         */
        iv_next.setFocusable(false);
        iv_last.setFocusable(false);
        showProgressDialog();
        detail_newsAdapter.SelectIndex(index);
        if(index==0){
            iv_last.setVisibility(View.INVISIBLE);
        }
        if(index>0){
            iv_last.setVisibility(View.VISIBLE);
        }
        if(index==NewsDateilList.size()-1){
            iv_next.setVisibility(View.INVISIBLE);
        }else{
            iv_next.setVisibility(View.VISIBLE);
        }
        no_data.setVisibility(View.GONE);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {

//        Bitmap bitmap = BitMap.getInstance().returnBitMap("http://interface.9stars.cn/" + fileUrl);
//
//        Message obtain = Message.obtain();
//                            obtain.what=5;
//                           obtain.obj=bitmap;
//                            mHandler.sendMessage(obtain);
//
//            }
//        }).start();
//
//        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        mWebView.loadDataWithBaseURL(null, getNewContent("http://interface.9stars.cn/"+fileUrl), "text/html", "UTF-8", null);

                        new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            System.out.println("图片========"+"http://interface.9stars.cn/"+fileUrl);
                            Bitmap myBitmap = Glide.with(NewsDetailActivity.this)
                                    .load("http://interface.9stars.cn/"+fileUrl)
                                    .asBitmap() //必须
                                    .centerCrop()

                                   // .into(2102, 3160)
                                    .into(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
                                    .get();
                            Message obtain = Message.obtain();
                            obtain.what=5;
                            obtain.obj=myBitmap;
                            mHandler.sendMessage(obtain);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
//


//                        try {
//                            System.out.println("========="+Configs.BaseUrl+fileUrl);
//                        URL url = new URL(Configs.BaseUrl+fileUrl);
//                        HttpURLConnection conn = null;
//
//                            conn = (HttpURLConnection)url.openConnection();
//
//                        conn.setConnectTimeout(5000);
//                        conn.setRequestMethod("GET");
//                        if(conn.getResponseCode() == 200){
//                            InputStream inputStream = conn.getInputStream();
//                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                         //   return bitmap;
//                            Message obtain = Message.obtain();
//                            obtain.what=5;
//                            obtain.obj=bitmap;
//                            mHandler.sendMessage(obtain);
//
//
//                        }
//                            System.out.println("conn.getResponseCode()===="+conn.getResponseCode());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                    }
                }).start();








//        mWebView.setInitialScale(100);//
//        DownloadUtil.download(fileUrl, getCacheDir() + "/temp.pdf",
//                new DownloadUtil.OnDownloadListener() {
//                    @Override
//                    public void onDownloadSuccess(final String path) {
//                        Log.d("MainActivity", "onDownloadSuccess: " + path);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                preView(path);
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onDownloading(int progress) {
//                        Log.d("MainActivity", "onDownloading: " + progress);
//                    }
//
//                    @Override
//                    public void onDownloadFailed(String msg) {
//                        Log.d("MainActivity", "onDownloadFailed: " + msg);
//                    }
//                });
    }
    //http://interface.9stars.cn//jpg/2019/2/25/人民日报/Paper.xml
    //http://interface.9stars.cn//jpg/2019/03/24/新华每日电讯/Paper.xml
    private String getNewContent(String htmltext) {
        org.jsoup.nodes.Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (org.jsoup.nodes.Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }
        return doc.toString();
    }
    private void getData(final String time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("======"+Configs.BaseUrl+"/jpg/"+time+"/"+name+"/Paper.xml");
                OkGo.<String>get(Configs.BaseUrl+"/jpg/"+time+"/"+name+"/Paper.xml")
                        .tag(this)

                        .cacheKey("diyi")//要缓冲的那个类型必须实现Serializable接口才行，并且如果其他地方出现一样的cacheKey，就会顶替掉原先的cacheKey对应的值。
                        .cacheMode(CacheMode.NO_CACHE)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                // Log.d_select_state("EasyHttpActivity", "response:"+response.body() );
                                System.out.println("response====" + response.body().length());
                                parseXML(response.body(),time);
                            }

                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);
                                System.out.println("请求失败===");
                                Message obtain = Message.obtain();
                                obtain.what=2;
                                mHandler.sendMessage(obtain);
                            }
                        });
                System.out.println("请i去了数据====");
            }
        }
        ).start();
    }

    private void parseXML(String body,String Time) {
        SAXBuilder builder = new SAXBuilder();//选用jdom中的SAXBuilder解析器解析xml16
        try {//
            System.out.println("xmlPath==="+body);
            InputStream in_nocode   =   new ByteArrayInputStream(body.getBytes());
            Document doc = builder.build(in_nocode);//从传入xml文件中提取出doc
            Element equipments = doc.getRootElement();//从doc中得到根节点，赋值给equipments

            List<Element> newspaper1 = equipments.getChildren("Url");//在equipments中得到名字为equipment的子节点List
            System.out.println("解析到了===equipmentList");

            NewsDateilList.clear();

            for (int i = 0; i < newspaper1.size(); i++) {


/**<root version="4.0" relatedUrl="false" width="2119" height="3026">
 <online>
 http://paper.people.com.cn/rmrb/html/2019-03/13/nbs.D110000renmrb_01.htm
 </online>
 <Url preview="jpg/2019/03/13/人民日报/0_t.jpg" pdf="http://paper.people.com.cn/rmrb/page/2019-03/13/01/rmrb2019031301.pdf" previewH="jpg/2019/03/13/人民日报/0_h.jpg">jpg/2019/03/13/人民日报/0.jpg</Url>
 <Url preview="jpg/2019/03/13/人民日报/1_t.jpg" pdf="http://paper.people.com.cn/rmrb/page/2019-03/13/02/rmrb2019031302.pdf" previewH="jpg/2019/03/13/人民日报/1_h.jpg">jpg/2019/03/13/人民日报/1.jpg</Url>
 <Url preview="jpg/2019/03/13/人民日报/2_t.jpg" pdf="http://paper.people.com.cn/rmrb/page/2019-03/13/03/rmrb2019031303.pdf" previewH="jpg/2019/03/13/人民日报/2_h.jpg">jpg/2019/03/13/人民日报/2.jpg</Url>
 <Url preview="jpg/2019/03/13/人民日报/3_t.jpg" pdf="http://paper.people.com.cn/rmrb/page/2019-03/13/04/rmrb2019031304.pdf" previewH="jpg/2019/03/13/人民日报/3_h.jpg">jpg/2019/03/13/人民日报/3.jpg</Url>

 */
                Element newspaper = newspaper1.get(i);
                String url = newspaper.getText();
                String preview = newspaper.getAttributeValue("preview");
                String pdf = newspaper.getAttributeValue("pdf");
                String previewH = newspaper.getAttributeValue("previewH");
                String text = newspaper.getText();
                NewsDateilBean newsDateilBean = new NewsDateilBean();
                newsDateilBean.setPdf(pdf);
                newsDateilBean.setUrl(url);
                newsDateilBean.setPreview(preview);
                newsDateilBean.setPreviewH(previewH);
                System.out.println("preview=="+preview+"==pdf="+pdf+"===previewH="+previewH);
                NewsDateilList.add(newsDateilBean);
            }

            Message obtain = Message.obtain();
            obtain.what=1;
            obtain.obj=Time;
            mHandler.sendMessage(obtain);

        } catch (
                JDOMException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {
        System.out.println("=============onCallBackAction==");
        Bundle bundle = new Bundle();
        bundle.putString("filePath", "/storage/emulated/0/rmrb2019032401.pdf.pdf");
        bundle.putString("tempPath", Environment.getExternalStorageDirectory().getPath());
        File file = new File("/storage/emulated/0/rmrb2019032401.pdf.pdf");
        boolean result = mTbsReaderView.preOpen(parseFormat("/storage/emulated/0/rmrb2019032401.pdf.pdf"), false);
      //  LogTool.ii(TAG,"result: "+result+" Path: "+file.getPath());
        if (result) {
            mTbsReaderView.openFile(bundle);
        }


    }
    private String parseFormat(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
    /**
     * 预览pdf
     *
     * @param pdfUrl url或者本地文件路径
     */
    private void preView(String pdfUrl) {
        //1.只使用pdf.js渲染功能，自定义预览UI界面
        mWebView.loadUrl("file:///android_asset/index.html?" + pdfUrl);
        //2.使用mozilla官方demo加载在线pdf
//        mWebView.loadUrl("http://mozilla.github.io/pdf.js/web/viewer.html?file=" + pdfUrl);
        //3.pdf.js放到本地
//        mWebView.loadUrl("file:///android_asset/pdfjs/web/viewer.html?file=" + pdfUrl);
        //4.使用谷歌文档服务
//        mWebView.loadUrl("http://docs.google.com/gviewembedded=true&url=" + pdfUrl);
    }



    private void getData2(final String time) {
      //  ISFrist=true;
        new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println("getData2===="+Configs.BaseUrl + "jpg/" + time + "/Papers.xml");
                OkGo.<String>get(Configs.BaseUrl + "jpg/" + time + "/Papers.xml")//http://interface.9stars.cn/jpg/2019/03/31/Papers.xml
                        .tag(this)

                        .cacheKey("diyi")//要缓冲的那个类型必须实现Serializable接口才行，并且如果其他地方出现一样的cacheKey，就会顶替掉原先的cacheKey对应的值。
                        .cacheMode(CacheMode.NO_CACHE)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                // Log.d_select_state("EasyHttpActivity", "response:"+response.body() );


                                parseXML2(response.body());
                            }

                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);
                                Message obtain = Message.obtain();
                                obtain.what = 7;
                                mHandler.sendMessage(obtain);

                            }
                        });


            }
        }
        ).start();
    }private void getData3(final String time) {
      //  ISFrist=true;
        new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println("getData2===="+Configs.BaseUrl + "jpg/" + time + "/Papers.xml");
                OkGo.<String>get(Configs.BaseUrl + "jpg/" + time + "/Papers.xml")//http://interface.9stars.cn/jpg/2019/03/31/Papers.xml
                        .tag(this)

                        .cacheKey("diyi")//要缓冲的那个类型必须实现Serializable接口才行，并且如果其他地方出现一样的cacheKey，就会顶替掉原先的cacheKey对应的值。
                        .cacheMode(CacheMode.NO_CACHE)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                // Log.d_select_state("EasyHttpActivity", "response:"+response.body() );


                                parseXML3(response.body());
                            }

                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);
//                                Message obtain = Message.obtain();
//                                obtain.what = 7;
//                                mHandler.sendMessage(obtain);

                            }
                        });


            }
        }
        ).start();
    }
    private void parseXML2(String data) {


        SAXBuilder builder = new SAXBuilder();//选用jdom中的SAXBuilder解析器解析xml16
        try {//


            InputStream in_nocode = new ByteArrayInputStream(data.getBytes());
            Document doc = builder.build(in_nocode);//从传入xml文件中提取出doc
            Element equipments = doc.getRootElement();//从doc中得到根节点，赋值给equipments

            List<Element> newspaper1 = equipments.getChildren("Paper");//在equipments中得到名字为equipment的子节点List




            for (int i = 0; i < newspaper1.size(); i++) {

/**
 *   <Paper>
 *     <id>N00001</id>
 *     <Name>人民日报</Name>
 *     <Url>jpg/2019/03/13/人民日报/0_s.jpg</Url>
 *     <CoverUrl date="2019-03-12">Cover\N00023_2015-04-27.jpg</CoverUrl>
 *     <online>http://paper.people.com.cn/rmrb/html/2019-03/13/nbs.D110000renmrb_01.htm</online>
 *   </Paper>
 */com.jiuxingyuedu.horizontal.Bean.DateNews dateNews = new DateNews();
                Element newspaper = newspaper1.get(i);
                String id = newspaper.getChild("id").getText();
                String Name = newspaper.getChild("Name").getText();
                Element url = newspaper.getChild("Url");
                String Url = "";
                if (null != url) {
                    Url = url.getText();
                }

                String online = newspaper.getChild("online").getText();
                Element coverUrl = newspaper.getChild("CoverUrl");
                if (null == coverUrl) {
                    dateNews.setDate(DataUtil.DateTime());
                } else {
                    String date = coverUrl.getAttributeValue("date");
                    dateNews.setDate(date);
                }
                dateNews.setId(id);
                dateNews.setName(Name);
                dateNews.setUrl(Url);
                dateNews.setOnline(online);
                DateNews2.put(id, dateNews);
            }

            Message obtain = Message.obtain();
            obtain.what = 6;
            mHandler.sendMessage(obtain);

        } catch (
                JDOMException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        }


    }    private void parseXML3(String data) {


        SAXBuilder builder = new SAXBuilder();//选用jdom中的SAXBuilder解析器解析xml16
        try {//


            InputStream in_nocode = new ByteArrayInputStream(data.getBytes());
            Document doc = builder.build(in_nocode);//从传入xml文件中提取出doc
            Element equipments = doc.getRootElement();//从doc中得到根节点，赋值给equipments

            List<Element> newspaper1 = equipments.getChildren("Paper");//在equipments中得到名字为equipment的子节点List




            for (int i = 0; i < newspaper1.size(); i++) {

/**
 *   <Paper>
 *     <id>N00001</id>
 *     <Name>人民日报</Name>
 *     <Url>jpg/2019/03/13/人民日报/0_s.jpg</Url>
 *     <CoverUrl date="2019-03-12">Cover\N00023_2015-04-27.jpg</CoverUrl>
 *     <online>http://paper.people.com.cn/rmrb/html/2019-03/13/nbs.D110000renmrb_01.htm</online>
 *   </Paper>
 */com.jiuxingyuedu.horizontal.Bean.DateNews dateNews = new DateNews();
                Element newspaper = newspaper1.get(i);
                String id = newspaper.getChild("id").getText();
                String Name = newspaper.getChild("Name").getText();
                Element url = newspaper.getChild("Url");
                String Url = "";
                if (null != url) {
                    Url = url.getText();
                }

                String online = newspaper.getChild("online").getText();
                Element coverUrl = newspaper.getChild("CoverUrl");
                if (null == coverUrl) {
                    dateNews.setDate(DataUtil.DateTime());
                } else {
                    String date = coverUrl.getAttributeValue("date");
                    dateNews.setDate(date);
                }
                dateNews.setId(id);
                dateNews.setName(Name);
                dateNews.setUrl(Url);
                dateNews.setOnline(online);
                DateNews3.put(id, dateNews);
            }

            Message obtain = Message.obtain();
            obtain.what = 9;
            mHandler.sendMessage(obtain);

        } catch (
                JDOMException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        }


    }

    private ProgressDialogHandler mProgressDialogHandler;
    private void showProgressDialog() {
        mProgressDialogHandler = new ProgressDialogHandler(NewsDetailActivity.this, this, true, true);
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }else{
            System.out.println("======mProgressDialogHandler = null");
        }
    }



    //开启定时器
    private void createTimer (){
        //避免定时器多次执行，每次先判断销毁
        if(null!=timer){
            timer.cancel();
            timer.purge();
        }
//创建定时器
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHadler.sendEmptyMessage(0);
            }
        },10000*6*5,10000);
    }
    //销毁定时器

    private void DesTimer(){
        if(null!=timer){
            timer.cancel();
            timer.purge();
            timer=null;
        }
    }
    //调用定时器
    private void setCreateTime(){
        System.out.println("=====================调用定时器");
        DesTimer();
        createTimer();

    }
    private boolean Des;
    private Handler mHadler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Des=true;
                    DesTimer();
                    System.out.println("========================执行了");

                  //  mActivity.setFrist();
                    Des=true;
                    MessageEvent messageEvent = new MessageEvent("");
                    EventBus.getDefault().post(messageEvent);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
    DesTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCreateTime();
    }
}
