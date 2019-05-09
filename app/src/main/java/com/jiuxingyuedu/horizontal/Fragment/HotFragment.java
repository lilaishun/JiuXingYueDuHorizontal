package com.jiuxingyuedu.horizontal.Fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.jiuxingyuedu.horizontal.Activity.MainActivity;
import com.jiuxingyuedu.horizontal.Adapter.AllFragmentAdapter;
import com.jiuxingyuedu.horizontal.App.MyApplication;
import com.jiuxingyuedu.horizontal.Bean.DateNews;
import com.jiuxingyuedu.horizontal.Bean.MessageEvent;
import com.jiuxingyuedu.horizontal.Bean.NewsNean;
import com.jiuxingyuedu.horizontal.R;
import com.jiuxingyuedu.horizontal.Util.Configs;
import com.jiuxingyuedu.horizontal.Util.PinyinComparator;
import com.jiuxingyuedu.horizontal.view.CustomGridView;
import com.jiuxingyuedu.horizontal.view.IndicatorSeekBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jiuxingyuedu.horizontal.App.MyApplication.ISSHOWSEACHER;

public class HotFragment extends LazyFragment {
    final List<NewsNean> NewsNeanListss = new ArrayList<>();
   // private int tabIndex;
    public static final String INTENT_INT_INDEX = "index";
    List<NewsNean> NewsNeanList = new ArrayList<>();
    private     List<NewsNean> NewsNeanListSeach = new ArrayList<>();
    @BindView(R.id.all_Gv)
    com.jiuxingyuedu.horizontal.view.CustomGridView all_Gv;
    @BindView(R.id.newslist_last)
    ImageView newslist_last;
    @BindView(R.id.newslist_next)
    ImageView newslist_next;
    private boolean ISSizeForE;//列表个数是否是8
    int currentIndex=0;
    private AllFragmentAdapter mAllFragmentAdapter;
private ArrayList<NewsNean> NewsList=new ArrayList();
    private IndicatorSeekBar indicatorSeekBar;
    private PinyinComparator pinyinComparator;

    int current=1; //页码
    int pageSize=8; //每页显示的数量
    private int pageCount;
    private int totalCount;
    private Map<String, DateNews> dateNews;
    private Timer timer;

    public static HotFragment newInstance(int tabIndex, boolean isLazyLoad) {

        Bundle args = new Bundle();
        args.putInt(INTENT_INT_INDEX, tabIndex);
        args.putBoolean(LazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad);
        HotFragment fragment = new HotFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_tabmain_item);
ButterKnife.bind(this,getLayContentView());
        EventBus.getDefault().register(this);
        dateNews = mActivity.DateNews;
        mAllFragmentAdapter = new AllFragmentAdapter(getApplicationContext(),dateNews);
        all_Gv.setAdapter(mAllFragmentAdapter);
        getData();
        newslist_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsList.clear();
                NewsListLast();
            }
        });
        newslist_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsList.clear();
                NewsListNext();
            }
        });


        indicatorSeekBar = mActivity.indicatorSeekBar;

mActivity.et_seacher.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(!mActivity.ISShowSeach){
            mActivity.ISShowSeach=true;
            mActivity.seacher_key.setVisibility(View.VISIBLE);
            mActivity.et_seacher.setText("");
            setStateFr(NewsNeanList,0);
            mActivity.et_seacher.setShowSoftInputOnFocus(false);
            setSheacher("", NewsNeanList);
            ISSHOWSEACHER=true;
        }else{
            ISSHOWSEACHER=false;
            mActivity. ISShowSeach=false;
            mActivity.et_seacher.setText("");
            mActivity.seacher_key.setVisibility(View.GONE);
            mActivity.et_seacher.setShowSoftInputOnFocus(false);

        }
    }
});


//        mActivity.et_seacher.setOnTouchListener(new View.OnTouchListener() {
//            int touch_flag=0;
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                touch_flag++;
//                if(touch_flag==2) {
//                    if(!mActivity.ISShowSeach){
//                        mActivity.ISShowSeach=true;
//                        mActivity.seacher_key.setVisibility(View.VISIBLE);
//                        mActivity.et_seacher.setText("");
//                        setStateFr(NewsNeanList,0);
//                        mActivity.et_seacher.setShowSoftInputOnFocus(false);
//                        setSheacher("", NewsNeanList);
//                        ISSHOWSEACHER=true;
//                    }else{
//                        mActivity. ISShowSeach=false;
//                        mActivity.et_seacher.setText("");
//                        mActivity.seacher_key.setVisibility(View.GONE);
//                        mActivity.et_seacher.setShowSoftInputOnFocus(false);
//                        ISSHOWSEACHER=false;
//                    }
//                    touch_flag=0;
//                }
//                return false;
//            }
//        });
        mActivity.et_seacher1.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if(MyApplication.ISONCLICKSEA) {
                    if (!mActivity.ISShowSeach) {
                        ISSHOWSEACHER = true;
                        mActivity.ISShowSeach = true;
                        mActivity.seacher_key.setVisibility(View.VISIBLE);
                        mActivity.et_seacher.setText("");
                        setStateFr(NewsNeanList, 0);
                        mActivity.et_seacher.setShowSoftInputOnFocus(false);
                        setSheacher("", NewsNeanList);

                    } else {
                        ISSHOWSEACHER = false;
                        mActivity.ISShowSeach = false;
                        mActivity.seacher_key.setVisibility(View.GONE);
                        mActivity.et_seacher.setText("");
                        mActivity.et_seacher.setShowSoftInputOnFocus(false);

                    }
                }
            }
        });
        mActivity.search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!"".equals(mActivity.et_seacher.getText().toString().trim())) {
                    mActivity.ISShowSeach = false;

                    mActivity.seacher_key.setVisibility(View.GONE);
                    setSeach(NewsNeanList, mActivity.et_seacher.getText().toString().trim());
                    ISSHOWSEACHER=false;  mActivity.et_seacher.setText("");
                }else{
                    Toast.makeText(getApplicationContext(),"请输入要搜索的字母",0).show();
                    setCreateTime();
                }
            }
        });
        mActivity.iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // setSeach(NewsNeanList,mActivity.et_seacher.getText().toString().trim());
                mActivity.et_seacher.setText("");

                setAdapterList(NewsNeanList);
            }
        });

        mActivity.et_seacher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println("您输入的数据为：==========="+s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("您输入的数据为：=onTextChanged=========="+s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            //    if(!"".equals(s.toString())) {
                if(ISSHOWSEACHER){
                    if(!Des){
                        setCreateTime();
                    }
                    setSheacher(s.toString(), NewsNeanList);
                }

               // }
            }
        });



        pinyinComparator = new PinyinComparator();



    }

    private void setSheacher(String s, final List<NewsNean> newsNeanLists) {
        NewsNeanListss.clear();
        //    Selection.setSelection((Spannable)s, s.toString().length());
        char[] chars = s.toString().toCharArray();
        System.out.println("您输入的数据为：=afterTextChanged=========="+s.toString()+chars.length);


        for (int i = 0; i <newsNeanLists.size() ; i++) {
            char[] chars1 = newsNeanLists.get(i).getPinyin().toCharArray();
            String seacherString="";
            for (int j = 0; j <chars.length ; j++) {
                if(s.toString().length()<=chars1.length){
                    seacherString+=chars1[j];
                }
            }

            if(seacherString.equals(s.toString())){
                NewsNeanListss.add(newsNeanLists.get(i));
            }
        }
        setStateFr(NewsNeanListss,chars.length);

        mActivity. horizontalListViewAdapter.SetData(NewsNeanListss);
        mActivity.hlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                mActivity. ISShowSeach=false;
                ISSHOWSEACHER=false;
                mActivity.seacher_key.setVisibility(View.GONE);
                mActivity.et_seacher.setText("");
                setSeach(newsNeanLists,mActivity.et_seacher.getText().toString().trim());
                String name = NewsNeanListss.get(position).getName();
                ArrayList<NewsNean> objects = new ArrayList<>();
                NewsList.clear();
                for (int i = 0; i <newsNeanLists.size() ; i++) {
                    if( newsNeanLists.get(i).getName().equals(name)) {



                        currentIndex = 0;
                        objects .add(newsNeanLists.get(i));

                    }
                }
                System.out.println("name=============+hot===="+objects.size());
                setAdapterList(objects);

            }
        });
    }

    private MainActivity mActivity;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (MainActivity) activity;
    }
    @Override
    protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();
        List<NewsNean> NewsNeanListss = new ArrayList<>();
        mActivity. horizontalListViewAdapter.SetData(NewsNeanListss);
        mActivity.et_seacher.setText("");
        indicatorSeekBar.setVisibility(View.VISIBLE);

        if(MyApplication.ISFORBack){

        }else {

            List<NewsNean> NewsNeanListzz= new ArrayList<>();
            mActivity.horizontalListViewAdapter.SetData(NewsNeanListzz);
            setAdapterList(NewsNeanList);

        }

Des=false;
        setCreateTime();

    }
    private void setSeach(List<NewsNean> NewsNeanLists,String s){
        NewsNeanListSeach.clear();
        char[] chars = s.toCharArray();
        for (int i = 0; i < NewsNeanLists.size(); i++) {
            char[] chars1 = NewsNeanLists.get(i).getPinyin().toCharArray();
            String seacherString = "";
            for (int j = 0; j < chars.length; j++) {
                if (s.toString().length() <= chars1.length) {
                    seacherString += chars1[j];
                }
            }

            if (seacherString.equals(s.toString())) {
                NewsNeanListSeach.add(NewsNeanLists.get(i));
            }
        }

        setAdapterList(NewsNeanListSeach);
    }

    private void setStateFr(List<NewsNean> NewsNeanLists,int index){
        System.out.println("点击了======键盘");

            mActivity.setState();
        if(index<5) {
            System.out.println("NewsNeanList======" + NewsNeanLists.size());
            for (int i = 0; i < NewsNeanLists.size(); i++) {
                String pinyin = NewsNeanLists.get(i).getPinyin();
                char[] stringArr = pinyin.toCharArray();
                if (index < stringArr.length) {
                    mActivity.setState(stringArr[index] + "");
                }


            }
        }else{
            mActivity.setState();
        }
        setCreateTime();
    }


    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("========" + Configs.AllNewsUrl);
                OkGo.<String>get(Configs.AllNewsUrl)
                        .tag(this)

                        .cacheKey("diyi")//要缓冲的那个类型必须实现Serializable接口才行，并且如果其他地方出现一样的cacheKey，就会顶替掉原先的cacheKey对应的值。
                        .cacheMode(CacheMode.NO_CACHE)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                // Log.d_select_state("EasyHttpActivity", "response:"+response.body() );
                                System.out.println("response====" + response.body().length());
                                parseXML(response.body());
                            }

                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);
                                System.out.println("请求失败===");
                            }
                        });
                System.out.println("请i去了数据====");
            }
        }
        ).start();
    }

    @Override
    public void onDestroyViewLazy() {
        super.onDestroyViewLazy();

        EventBus.getDefault().unregister(this);
    }


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            //     tvLoading.setVisibility(View.GONE);
            int id = 0;
            switch (msg.what) {
                case 1:

                    Collections.sort(NewsNeanList, new Comparator<NewsNean>() {
                        @Override
                        public int compare(NewsNean user1, NewsNean user2) {
                            int idNum1=0;
                            int idNum2=0;
                            String id1 = user1.getHeat();
                            if(!"".equals(id1)&&null!=id1){
                                idNum1=Integer.parseInt(id1);
                            }
                            String id2 = user2.getHeat();
                            if("".equals(id2)&&null!=id2){
                                idNum2=Integer.parseInt(id2);
                            }


                            //可以按User对象的其他属性排序，只要属性支持compareTo方法
                            System.out.println("idNum1======"+(idNum1-idNum2));
                            return idNum1-idNum2;
                        }
                    });

                    setStateFr(NewsNeanList,0);

                    setAdapterList(NewsNeanList);
                    break;
                case 2:


                    break;
                case 3:

                    break;
                case 4:

                    break;
            }

        }
    };

    private void NewsListLast(){




        current-=1;
        if(current>=1) {
            if (current == 1) {
                newslist_last.setVisibility(View.INVISIBLE);
            }
            if (current > 1) {
                newslist_last.setVisibility(View.VISIBLE);
                newslist_next.setVisibility(View.VISIBLE);
            } else if (current == pageCount) {
                newslist_next.setVisibility(View.INVISIBLE);
            }
            int start = (current - 1) * pageSize;
            int end = current == pageCount ? totalCount : current * pageSize;
            if(end>totalCount){
                end=totalCount;
            }
            if(start<0){
                start=0;
            }
            if(end<0){
                end=0;
            }
            List<NewsNean> updateList = NewsNeanList.subList(start, end);
            indicatorSeekBar.setMax(pageCount);
            indicatorSeekBar.setProgress(current);
            mAllFragmentAdapter.SetDate(updateList);
        }else{
            current=1;
        }

        setCreateTime();
    }
    @Override
    protected void onFragmentStopLazy() {
        super.onFragmentStopLazy();


        indicatorSeekBar.setVisibility(View.INVISIBLE);

Des=true;
DesTimer();

    }
    private void NewsListNext(){

        current+=1;
        System.out.println(pageCount+"current=Next==="+current);
        if(current>1){
            newslist_last.setVisibility(View.VISIBLE);
        }else if(current==pageCount){
            newslist_next.setVisibility(View.INVISIBLE);
        }
        if(current<=pageCount) {

            int start = (current - 1) * pageSize;
            int end = current == pageCount ? totalCount : current * pageSize;

            List<NewsNean> updateList = new ArrayList<>();
            if(end>totalCount){
                end=totalCount;
            }
            if(start<0){
                start=0;
            }
            if(end<0){
                end=0;
            }
            System.out.println(totalCount + "start====" + start + "=end=---==" + end);
            updateList = NewsNeanList.subList(start, end);
            indicatorSeekBar.setMax(pageCount);
            indicatorSeekBar.setProgress(current);

            mAllFragmentAdapter.SetDate(updateList);
        }else{
            current=pageCount;
            newslist_next.setVisibility(View.INVISIBLE);
            if(current==1) {
                newslist_last.setVisibility(View.INVISIBLE);
            }
        }


        setCreateTime();
    }

    public void parseXML(String xmlPath) {
        SAXBuilder builder = new SAXBuilder();//选用jdom中的SAXBuilder解析器解析xml16
        try {//
            System.out.println("xmlPath==="+xmlPath);
            InputStream in_nocode   =   new ByteArrayInputStream(xmlPath.getBytes());
            Document doc = builder.build(in_nocode);//从传入xml文件中提取出doc
            Element equipments = doc.getRootElement();//从doc中得到根节点，赋值给equipments

            List<Element> newspaper1 = equipments.getChildren("newspaper");//在equipments中得到名字为equipment的子节点List
            System.out.println("解析到了===equipmentList");

            NewsNeanList.clear();

            for (int i = 0; i < newspaper1.size(); i++) {

                NewsNean newsNean = new NewsNean();
/**
 * <newspaper id="N00001" name="人民日报" enabled="True">
 * 		<pinyin>RMRB</pinyin>
 * 		<datatype>pdf</datatype>
 * 		<logo>http://online.9stars.cn/PaperImages/2019/03/14/N00001/ids.jpg</logo>
 * 		<category>日报</category>
 * 		<category>综合报</category>
 * 		<district>全国</district>
 * 		<heat>10</heat>
 * 		<urlfilters>
 * 			<urlfilter>^http://paper.people.com.cn/rmrb/html/\d_select_state{4}-\d_select_state{2}/\d_select_state{2}/.*</urlfilter>
 * 		</urlfilters>
 * 	</newspaper>
 */

                Element newspaper = newspaper1.get(i);
                String id = newspaper.getAttributeValue("id");
                String name = newspaper.getAttributeValue("name");
                String enabled = newspaper.getAttributeValue("enabled");

                String pinyin = newspaper.getChild("pinyin").getText();
                String datatype = newspaper.getChild("datatype").getText();
                String logo = newspaper.getChild("logo").getText();
                String category = newspaper.getChild("category").getText();
                String category2 = newspaper.getChild("category").getText();
                String district = newspaper.getChild("district").getText();
                String heat = newspaper.getChild("heat").getText();
                String urlfilter ="";

                List<Element> urlfilters = newspaper.getChildren("urlfilters");
                for (int j = 0; j <urlfilters.size() ; j++) {
                    Element element = urlfilters.get(j);
                    Element urlfilter1 = element.getChild("urlfilter");
                    if(urlfilter1!=null) {
                        urlfilter = urlfilter1.getText();
                        System.out.println("====" + urlfilter);
                    }
                }

                newsNean.setId(id);
                newsNean.setName(name);
                newsNean.setEnabled(enabled);
                newsNean.setPinyin(pinyin);
                newsNean.setDatatype(datatype);
                newsNean.setLogo(logo);
                newsNean.setCategory(category);
                newsNean.setCategory2(category2);
                newsNean.setDistrict(district);
                newsNean.setHeat(heat);
                newsNean.setUrlfilter(urlfilter);
                NewsNeanList.add(newsNean);
            }
            NewsList.clear();
            Message obtain = Message.obtain();
            obtain.what=1;
            mHandler.sendMessage(obtain);

        } catch (
                JDOMException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

private void setAdapterList(final List<NewsNean> NewsNeanLists){
    currentIndex=0;


    setStateFr(NewsNeanLists,0);
    current=1;
    totalCount = NewsNeanLists.size();
    pageCount = (totalCount / pageSize) + ((totalCount % pageSize > 0) ? 1 : 0);
    indicatorSeekBar.setVisibility(View.VISIBLE);
    int start=(current-1) * pageSize;
    int end = current== pageCount ? totalCount : current * pageSize;
    if (current == 1) {
        newslist_last.setVisibility(View.INVISIBLE);
    }
    if (pageCount > 1) {
        newslist_next.setVisibility(View.VISIBLE);
    } else if (current == pageCount) {
        newslist_next.setVisibility(View.INVISIBLE);
    }
    if(end>totalCount){
        end=totalCount;
    }
    if(start<0){
        start=0;
    }
    if(end<0){
        end=0;
    }
    List<NewsNean> updateList=NewsNeanLists.subList(start,end);
    if(pageCount>1){
        newslist_next.setVisibility(View.VISIBLE);
    }
    mAllFragmentAdapter.SetDate(updateList);
    indicatorSeekBar.setMax(pageCount);
    indicatorSeekBar.setProgress(current);


    indicatorSeekBar.setOnSeekBarChangeListener(new IndicatorSeekBar.OnIndicatorSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, float indicatorOffset) {
            String indicatorText = progress + "%";
            currentIndex=progress;

            current=progress;
            if(current==0){
                current=1;
                progress=1;
            }
            int start=(progress-1) * pageSize;
            int end = progress== pageCount ? totalCount : progress * pageSize;
            System.out.println("end==="+end+"===start="+start+"========"+totalCount);
            if(end>totalCount){
                end=totalCount;
            }
            if(start<0){
                start=0;
            }
            if(end<0){
                end=0;
            }

            List<NewsNean> updateList=NewsNeanLists.subList(start,end);
            if(pageCount>1){
                newslist_next.setVisibility(View.VISIBLE);
            }
            mAllFragmentAdapter.SetDate(updateList);
            indicatorSeekBar.setProgress(current);
            if (current == 1) {
                newslist_last.setVisibility(View.INVISIBLE);
            }

            if (current > 1) {
                newslist_last.setVisibility(View.VISIBLE);
                if(current<pageCount){
                    newslist_next.setVisibility(View.VISIBLE);
                }else if (current == pageCount) {
                    newslist_next.setVisibility(View.INVISIBLE);
                }

            }
            setCreateTime();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // tvIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // tvIndicator.setVisibility(View.INVISIBLE);

        }
    });





    all_Gv.setOnRightItemListener(new CustomGridView.OnRightListener() {
        @Override
        public void onItemRight() {
            NewsList.clear();
            NewsListNext();
        }

        @Override
        public void onItemLeft() {
            NewsList.clear();
            NewsListLast();
        }
    });
    setCreateTime();
}
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ForFrist(MessageEvent messageEvent) {
//        Des=true;
//        DesTimer();
//        System.out.println("========================执行了=====");
//
//        mActivity.setFrist();
        mHadler.sendEmptyMessage(0);
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

                    mActivity.setFrist();

                    break;
            }
        }
    };
}