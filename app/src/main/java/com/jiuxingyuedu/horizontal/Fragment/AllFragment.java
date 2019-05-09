package com.jiuxingyuedu.horizontal.Fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
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
import com.jiuxingyuedu.horizontal.Bean.NewsNean;
import com.jiuxingyuedu.horizontal.Bean.UserEvent;
import com.jiuxingyuedu.horizontal.R;
import com.jiuxingyuedu.horizontal.Util.Configs;
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
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jiuxingyuedu.horizontal.App.MyApplication.ISSHOWSEACHER;
import static com.jiuxingyuedu.horizontal.App.MyApplication.NewsNeanLists;

public class AllFragment extends LazyFragment {

    //private int tabIndex;
    public static final String INTENT_INT_INDEX = "index";
    List<NewsNean> NewsNeanList = new ArrayList<>();
    private     List<NewsNean> NewsNeanListSeach = new ArrayList<>();
    private     List<NewsNean> NewsNeanListCurrentSelect = new ArrayList<>();
    List<NewsNean> NewsNeanListss = new ArrayList<>();
    @BindView(R.id.all_Gv)
    com.jiuxingyuedu.horizontal.view.CustomGridView all_Gv;
    @BindView(R.id.newslist_last)
    ImageView newslist_last;
    @BindView(R.id.newslist_next)
    ImageView newslist_next;
    private boolean ISSizeForE;//列表个数是否是8
    private boolean ISPageLast;
    int currentIndex=0;
    private int CurrentPageNum=0;
    private AllFragmentAdapter mAllFragmentAdapter;
private ArrayList<NewsNean> NewsList=new ArrayList();
    private IndicatorSeekBar indicatorSeekBar;
    int current=1; //页码
    int pageSize=8; //每页显示的数量
    private int pageCount;
    private int totalCount;
    private Map<String, DateNews> dateNews;
    private MainActivity mActivity;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (MainActivity) activity;
    }
    public static AllFragment newInstance(int tabIndex, boolean isLazyLoad) {

        Bundle args = new Bundle();
        args.putInt(INTENT_INT_INDEX, tabIndex);
        args.putBoolean(LazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad);
        AllFragment fragment = new AllFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_tabmain_item);
ButterKnife.bind(this,getLayContentView());
      //  EventBus.getDefault().register(this);
        System.out.println("===================================================");
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
                    mActivity.et_seacher.setText("");
                    mActivity.ISShowSeach=true;
                    mActivity.seacher_key.setVisibility(View.VISIBLE);
                    mActivity.ll_earch1.setVisibility(View.INVISIBLE);
                    mActivity.et_seacher.setShowSoftInputOnFocus(false);
                    setStateFr(NewsNeanLists,0);
                    ISSHOWSEACHER=true;
                    setSeacher("",NewsNeanLists);
                }else{
                    ISSHOWSEACHER=false;
                    mActivity.et_seacher.setText("");
                    mActivity. ISShowSeach=false;
                    mActivity.et_seacher.setShowSoftInputOnFocus(false);
                    mActivity.seacher_key.setVisibility(View.GONE);
                    mActivity.ll_earch1.setVisibility(View.VISIBLE);

                }
            }
        });

//        mActivity.et_seacher.setOnTouchListener(new View.OnTouchListener() {
//            //按住和松开的标识
//            int touch_flag=0;
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                touch_flag++;
//                if(touch_flag==2){
//                    //自己业务
//
//                    if(!mActivity.ISShowSeach){
//                        mActivity.et_seacher.setText("");
//                        mActivity.ISShowSeach=true;
//                        mActivity.seacher_key.setVisibility(View.VISIBLE);
//                        mActivity.ll_earch1.setVisibility(View.INVISIBLE);
//                        mActivity.et_seacher.setShowSoftInputOnFocus(false);
//                        setStateFr(NewsNeanLists,0);
//                        ISSHOWSEACHER=true;
//                        setSeacher("",NewsNeanLists);
//                    }else{
//                        mActivity.et_seacher.setText("");
//                        mActivity. ISShowSeach=false;
//                        mActivity.et_seacher.setShowSoftInputOnFocus(false);
//                        mActivity.seacher_key.setVisibility(View.GONE);
//                        mActivity.ll_earch1.setVisibility(View.VISIBLE);
//                        ISSHOWSEACHER=false;
//                    }
//                    touch_flag=0;
//                }
//
//
//                return false;
//            }
//        });



        mActivity.et_seacher1.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if(MyApplication.ISONCLICKSEA) {

                    if (!mActivity.ISShowSeach) {
                        mActivity.ISShowSeach = true;
                        mActivity.seacher_key.setVisibility(View.VISIBLE);
                        mActivity.ll_earch1.setVisibility(View.INVISIBLE);
                        mActivity.et_seacher.setText("");
                        mActivity.et_seacher.setShowSoftInputOnFocus(false);
                        setStateFr(NewsNeanLists, 0);
                        setSeacher("",NewsNeanLists);
                        ISSHOWSEACHER = true;

                    } else {
                        ISSHOWSEACHER = false;
                        mActivity.ISShowSeach = false;
                        mActivity.et_seacher.setShowSoftInputOnFocus(false);
                        mActivity.seacher_key.setVisibility(View.GONE);
                        mActivity.ll_earch1.setVisibility(View.VISIBLE);
                        mActivity.et_seacher.setText("");

                    }
                }
            }
        });
        mActivity.search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!"".equals(mActivity.et_seacher.getText().toString().trim())) {
                    ISSHOWSEACHER=false;
                    mActivity.ISShowSeach = false;
                    mActivity.seacher_key.setVisibility(View.GONE);
                    mActivity.ll_earch1.setVisibility(View.VISIBLE);
                    setSeach(NewsNeanLists, mActivity.et_seacher.getText().toString().trim());
                    mActivity.et_seacher.setText("");

                }else{
                    Toast.makeText(getApplicationContext(),"请输入要搜索的字母",0).show();
                }
            }
        });
        mActivity.iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // setSeach(NewsNeanLists,mActivity.et_seacher.getText().toString().trim());
                mActivity.et_seacher.setText("");

                setAdapterList(NewsNeanLists);

            }
        });

        mActivity.et_seacher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


//                if (!"".equals(s.toString())) {
                if(ISSHOWSEACHER){
                    Selection.setSelection((Spannable) s, s.toString().length());
                    setSeacher(s.toString(),NewsNeanLists);
                }

//                }
            }
        });





    }

//    //定义处理接收的方法
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void userEventBus(UserEvent userEvent){
//        MyApplication.ISFORBack=false;
//    }



    @Override
    protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();

        indicatorSeekBar.setVisibility(View.VISIBLE);
        mActivity.et_seacher.setText("");
if(MyApplication.ISFORBack){

}else{
    List<NewsNean> NewsNeanListzz= new ArrayList<>();
    mActivity.horizontalListViewAdapter.SetData(NewsNeanListzz);


    setAdapterList(NewsNeanList);
}





    }

    private void setSeach(List<NewsNean> NewsNeanLists, String s){
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


        mActivity.setState();
      //  MainActivity activity = (MainActivity) getActivity();
        if(index<5) {


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
    }


    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkGo.<String>get(Configs.AllNewsUrl)
                        .tag(this)

                        .cacheKey("diyi")//要缓冲的那个类型必须实现Serializable接口才行，并且如果其他地方出现一样的cacheKey，就会顶替掉原先的cacheKey对应的值。
                        .cacheMode(CacheMode.NO_CACHE)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                // Log.d_select_state("EasyHttpActivity", "response:"+response.body() );

                                parseXML(response.body());
                            }

                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);

                            }
                        });

            }
        }
        ).start();
    }

    @Override
    public void onDestroyViewLazy() {
        super.onDestroyViewLazy();

    }



    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            //     tvLoading.setVisibility(View.GONE);
            int id = 0;
            switch (msg.what) {
                case 1:

                    currentIndex=0;
                    NewsNeanLists=NewsNeanList;

                    setStateFr(NewsNeanLists,0);
                    setAdapterList(NewsNeanLists);
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
            List<NewsNean> updateList = NewsNeanListCurrentSelect.subList(start, end);
            indicatorSeekBar.setMax(pageCount);
            indicatorSeekBar.setProgress(current);
            mAllFragmentAdapter.SetDate(updateList);
        }else{
            current=1;
            if (current == 1) {
                newslist_last.setVisibility(View.INVISIBLE);
            }
            if (current == pageCount) {
                newslist_next.setVisibility(View.INVISIBLE);
            }
        }


    }

    private void NewsListNext(){
        current+=1;

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
    updateList = NewsNeanListCurrentSelect.subList(start, end);
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

    }

    public void parseXML(String xmlPath) {
        SAXBuilder builder = new SAXBuilder();//选用jdom中的SAXBuilder解析器解析xml16
        try {//

            InputStream in_nocode   =   new ByteArrayInputStream(xmlPath.getBytes());
            Document doc = builder.build(in_nocode);//从传入xml文件中提取出doc
            Element equipments = doc.getRootElement();//从doc中得到根节点，赋值给equipments

            List<Element> newspaper1 = equipments.getChildren("newspaper");//在equipments中得到名字为equipment的子节点List

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
    @Override
    protected void onFragmentStopLazy() {
        super.onFragmentStopLazy();
        indicatorSeekBar.setVisibility(View.INVISIBLE);
    }
    private void setAdapterList(final List<NewsNean> NewsNeanLists){
        NewsNeanListCurrentSelect.clear();
        currentIndex=0;
        indicatorSeekBar.setVisibility(View.VISIBLE);
        NewsNeanListCurrentSelect.addAll(NewsNeanLists);
        setStateFr(NewsNeanLists,0);
        current=1;
        totalCount = NewsNeanLists.size();
        pageCount = (totalCount / pageSize) + ((totalCount % pageSize > 0) ? 1 : 0);

        int start=(current-1) * pageSize;
        int end = current== pageCount ? totalCount : current * pageSize;

        if(end>totalCount){
            end=totalCount;
        }
        if(start<0){
            start=0;
        }
        if(end<0){
            end=0;
        }
        if (current == 1) {
            newslist_last.setVisibility(View.INVISIBLE);
        }
        if (pageCount > 1) {
            newslist_next.setVisibility(View.VISIBLE);
        } else if (current == pageCount) {
            newslist_next.setVisibility(View.INVISIBLE);
        }
        List<NewsNean> updateList=NewsNeanLists.subList(start,end);
        if(pageCount>1){
            newslist_next.setVisibility(View.VISIBLE);
        }
        mAllFragmentAdapter.SetDate(updateList);
        indicatorSeekBar.setMax(pageCount);
        indicatorSeekBar.setProgress(current);
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

                if(end>totalCount){
                    end=totalCount;
                }
                if(start<0){
                    start=0;
                }
                if(end<0){
                    end=0;
                }

                List<NewsNean> updateList=NewsNeanListCurrentSelect.subList(start,end);
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




    }

    private void  setSeacher(String s, final List<NewsNean> NewsNeanLists){




            char[] chars = s.toString().toCharArray();
            NewsNeanListss.clear();

            NewsNeanListss = new ArrayList<>();
            if(!"".equals(s)) {
                for (int i = 0; i < NewsNeanLists.size(); i++) {
                    char[] chars1 = NewsNeanLists.get(i).getPinyin().toCharArray();
                    String seacherString = "";
                    for (int j = 0; j < chars.length; j++) {
                        if (s.toString().length() <= chars1.length) {
                            seacherString += chars1[j];
                        }
                    }

                    if (seacherString.equals(s.toString())) {
                        NewsNeanListss.add(NewsNeanLists.get(i));
                    }
                }
            }else{
                NewsNeanListss.addAll(NewsNeanLists) ;
            }
            setStateFr(NewsNeanListss, chars.length);
            System.out.println("name======size===" + NewsNeanListss.size());
            mActivity.horizontalListViewAdapter.SetData(NewsNeanListss);

            mActivity.hlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    mActivity.ISShowSeach = false;
                    ISSHOWSEACHER = false;
                    mActivity.seacher_key.setVisibility(View.GONE);
                    mActivity.ll_earch1.setVisibility(View.VISIBLE);
                    mActivity.et_seacher.setText("");
                    //  setSeach(NewsNeanListss,mActivity.et_seacher.getText().toString().trim());
                    String name = NewsNeanListss.get(position).getName();
                    System.out.println("name======" + name + "======position====" + position + "==NewsNeanListss====" + NewsNeanListss.size());
                    ArrayList<NewsNean> objects = new ArrayList<>();
                    NewsList.clear();
                    for (int i = 0; i < NewsNeanLists.size(); i++) {
                        if (NewsNeanLists.get(i).getName().equals(name)) {

                            System.out.println("name=====" + NewsNeanLists.get(i).getName() + "-=====" + name);

                            currentIndex = 0;
                            objects.add(NewsNeanLists.get(i));

                        }
                    }
                    System.out.println("name=============+all===="+objects.size());
                    setAdapterList(objects);


                }
            });

    }
}