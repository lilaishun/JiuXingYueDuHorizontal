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
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
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
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jiuxingyuedu.horizontal.App.MyApplication.ISSHOWSEACHER;

public class DiQuFragment extends LazyFragment implements View.OnClickListener {
    private List<NewsNean> NewsNeanListss = new ArrayList<>();
   // private int tabIndex;
    public static final String INTENT_INT_INDEX = "index";
    List<NewsNean> NewsNeanList = new ArrayList<>();
    List<NewsNean> NewsNeanListDi = new ArrayList<>();
    List<NewsNean> NewsNeanListCurrent = new ArrayList<>();
    List<NewsNean> NewsNeanListCurrentSelect = new ArrayList<>();
    private     List<NewsNean> NewsNeanListSeach = new ArrayList<>();
    @BindView(R.id.all_Gv)
    com.jiuxingyuedu.horizontal.view.CustomGridView all_Gv;
    @BindView(R.id.newslist_last)
    ImageView newslist_last;
    @BindView(R.id.newslist_next)
    ImageView newslist_next;



    /*地区*/

    @BindView(R.id.beijing)
    TextView beijing;
    @BindView(R.id.tianjin)
    TextView tianjin;
    @BindView(R.id.hebei)
    TextView hebei;
    @BindView(R.id.shanxi)
    TextView shanxi;
    @BindView(R.id.neimenggu)
    TextView neimenggu;
    @BindView(R.id.liaoning)
    TextView liaoning;
    @BindView(R.id.jilin)
    TextView jilin;
    @BindView(R.id.heilongjiang)
    TextView heilongjiang;
    @BindView(R.id.shanghai)
    TextView shanghai;
    @BindView(R.id.jiangsu)
    TextView jiangsu;
    @BindView(R.id.zhejiang)
    TextView zhejiang;
    @BindView(R.id.anhui)
    TextView anhui;
    @BindView(R.id.fujian)
    TextView fujian;
    @BindView(R.id.jiangxi)
    TextView jiangxi;
    @BindView(R.id.shand)
    TextView shandong;
    @BindView(R.id.henan)
    TextView henan;
    @BindView(R.id.hubei)
    TextView hubei;
    @BindView(R.id.hunan)
    TextView hunan;
    @BindView(R.id.guangdong)
    TextView guangdong;
    @BindView(R.id.guangxi)
    TextView guangxi;
    @BindView(R.id.hainan)
    TextView hainan;
    @BindView(R.id.chongqing)
    TextView chongqing;
    @BindView(R.id.sichuan)
    TextView sichuan;
    @BindView(R.id.guizhou)
    TextView guizhou;
    @BindView(R.id.yunnan)
    TextView yunnan;
    @BindView(R.id.xizang)
    TextView xizang;
    @BindView(R.id.shanxi2)
    TextView shanxi2;
    @BindView(R.id.gansu)
    TextView gansu;
    @BindView(R.id.qinghai)
    TextView qinghai;
    @BindView(R.id.ningxia)
    TextView ningxia;
    @BindView(R.id.xinjiang)
    TextView xinjiang;
    @BindView(R.id.ll_diqu)
    LinearLayout ll_diqu;


    int current=1; //页码
    int pageSize=8; //每页显示的数量
    private int pageCount;
    private int totalCount;
















private boolean Des;
    private boolean ISSizeForE;//列表个数是否是8
    int currentIndex=0;
    private AllFragmentAdapter mAllFragmentAdapter;
private ArrayList<NewsNean> NewsList=new ArrayList();
    private IndicatorSeekBar indicatorSeekBar;
    private PinyinComparator pinyinComparator;
    private Map<String, DateNews> dateNews;
    private  List<NewsNean> NewsNeanListdiqi = new ArrayList<>();
    private Timer timer;
    private Handler mHadler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Des=true;
                    DesTimer();
                    System.out.println("========================执行了");
                    ISSHOWSEACHER=false;
                    mActivity. ISShowSeach=false;
                    mActivity.seacher_key.setVisibility(View.GONE);
                    mActivity.et_seacher.setText("");
                    mActivity.ll_earch1.setVisibility(View.VISIBLE);
                    mActivity.et_seacher.setShowSoftInputOnFocus(false);
                    mActivity.setFrist();

                    break;
            }
        }
    };
    public static DiQuFragment newInstance(int tabIndex, boolean isLazyLoad) {

        Bundle args = new Bundle();
        args.putInt(INTENT_INT_INDEX, tabIndex);
        args.putBoolean(LazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad);
        DiQuFragment fragment =new DiQuFragment();
        fragment.setArguments(args);
        return  fragment;
    }


    @Override
    public void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_diqu_item);
ButterKnife.bind(this,getLayContentView());
        EventBus.getDefault().register(this);
        //tabIndex = getArguments().getInt(INTENT_INT_INDEX);
        dateNews = mActivity.DateNews;
        mAllFragmentAdapter = new AllFragmentAdapter(getApplicationContext(),dateNews);
        all_Gv.setAdapter(mAllFragmentAdapter);

        /*地区点击事件*/

    beijing.setOnClickListener(this);

        tianjin.setOnClickListener(this);

     hebei.setOnClickListener(this);

        shanxi.setOnClickListener(this);

       neimenggu.setOnClickListener(this);

        liaoning.setOnClickListener(this);

        jilin.setOnClickListener(this);

     heilongjiang.setOnClickListener(this);

      shanghai.setOnClickListener(this);

      jiangsu.setOnClickListener(this);
        zhejiang.setOnClickListener(this);
       anhui.setOnClickListener(this);
      fujian.setOnClickListener(this);
       jiangxi.setOnClickListener(this);
       shandong.setOnClickListener(this);
        henan.setOnClickListener(this);
       hubei.setOnClickListener(this);
       hunan.setOnClickListener(this);
       guangdong.setOnClickListener(this);
        guangxi.setOnClickListener(this);
        hainan.setOnClickListener(this);
       chongqing.setOnClickListener(this);
       sichuan.setOnClickListener(this);
        guizhou.setOnClickListener(this);
      yunnan.setOnClickListener(this);
       xizang.setOnClickListener(this);
       shanxi2.setOnClickListener(this);
       gansu.setOnClickListener(this);
        qinghai.setOnClickListener(this);
       ningxia.setOnClickListener(this);
       xinjiang.setOnClickListener(this);








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

        mActivity.et_seacher.setOnTouchListener(new View.OnTouchListener() {
            //按住和松开的标识
            int touch_flag=0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                touch_flag++;
                if(touch_flag==2){
                    touch_flag=0;
                }


                return false;
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
//                        mActivity.ll_earch1.setVisibility(View.INVISIBLE);
//                        setStateFr(NewsNeanListCurrent,0);
//                        mActivity.et_seacher.setShowSoftInputOnFocus(false);
//                        setSheacher("", NewsNeanListCurrent);
//                        ISSHOWSEACHER=true;
//                    }else{
//                        mActivity. ISShowSeach=false;
//                        mActivity.seacher_key.setVisibility(View.GONE);
//                        mActivity.et_seacher.setText("");
//                        mActivity.ll_earch1.setVisibility(View.VISIBLE);
//                        mActivity.et_seacher.setShowSoftInputOnFocus(false);
//                        ISSHOWSEACHER=false;
//                    }
//                    touch_flag=0;
//                }
//                return false;
//            }
//        });
        mActivity.et_seacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mActivity.ISShowSeach){
                    ISSHOWSEACHER=true;
                        mActivity.ISShowSeach=true;
                        mActivity.seacher_key.setVisibility(View.VISIBLE);
                        mActivity.et_seacher.setText("");
                        mActivity.ll_earch1.setVisibility(View.INVISIBLE);
                        setStateFr(NewsNeanListCurrent,0);
                        mActivity.et_seacher.setShowSoftInputOnFocus(false);
                        setSheacher("", NewsNeanListCurrent);

                    }else{
                    ISSHOWSEACHER=false;
                        mActivity. ISShowSeach=false;
                        mActivity.seacher_key.setVisibility(View.GONE);
                        mActivity.et_seacher.setText("");
                        mActivity.ll_earch1.setVisibility(View.VISIBLE);
                        mActivity.et_seacher.setShowSoftInputOnFocus(false);

                    }
            }
        });
        mActivity.et_seacher1.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
             //   System.out.println("ISONCLICKSEA====="+ISONCLICKSEA);
                if (MyApplication.ISONCLICKSEA) {
                    if (!mActivity.ISShowSeach) {
                        mActivity.ISShowSeach = true;
                        mActivity.seacher_key.setVisibility(View.VISIBLE);
                        mActivity.et_seacher.setText("");
                        mActivity.ll_earch1.setVisibility(View.INVISIBLE);
                        setStateFr(NewsNeanListCurrent, 0);
                        mActivity.et_seacher.setShowSoftInputOnFocus(false);
                        setSheacher("", NewsNeanListCurrent);
                        ISSHOWSEACHER = true;
                    } else {
                        mActivity.ISShowSeach = false;
                        mActivity.seacher_key.setVisibility(View.GONE);
                        mActivity.et_seacher.setText("");
                        mActivity.ll_earch1.setVisibility(View.VISIBLE);
                        mActivity.et_seacher.setShowSoftInputOnFocus(false);
                        ISSHOWSEACHER = false;
                    }
                }else{

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
                    setSeach(NewsNeanListCurrent, mActivity.et_seacher.getText().toString().trim());
                    mActivity.et_seacher.setText("");

                }else{
                    Toast.makeText(getApplicationContext(),"请输入要搜索的字母",0).show();
                }
            }
        });
        mActivity.iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // setSeach(NewsNeanList,mActivity.et_seacher.getText().toString().trim());
                mActivity.et_seacher.setText("");


                setAdapterList(NewsNeanListCurrent);








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
             //   Selection.setSelection((Spannable)s, s.toString().length());
              //  if(!"".equals(s.toString())) {
                if(ISSHOWSEACHER) {
                    setSheacher(s.toString(), NewsNeanListCurrent);
                    if(!Des){
                        setCreateTime();
                    }
                }
             //   }
            }
        });



        pinyinComparator = new PinyinComparator();



    }

    private void setSheacher(String s, final List<NewsNean> newsNeanList) {
        char[] chars = s.toString().toCharArray();
        System.out.println("您输入的数据为：=afterTextChanged=========="+s.toString()+chars.length);
        NewsNeanListss.clear();


        for (int i = 0; i <newsNeanList.size() ; i++) {
            char[] chars1 = newsNeanList.get(i).getPinyin().toCharArray();
            String seacherString="";
            for (int j = 0; j <chars.length ; j++) {
                if(s.toString().length()<=chars1.length){
                    seacherString+=chars1[j];
                }
            }

            if(seacherString.equals(s.toString())){
                NewsNeanListss.add(newsNeanList.get(i));
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
                mActivity.ll_earch1.setVisibility(View.VISIBLE);
                setSeach(NewsNeanListss,mActivity.et_seacher.getText().toString().trim());
                String name = NewsNeanListss.get(position).getName();
                ArrayList<NewsNean> objects = new ArrayList<>();
                NewsList.clear();
                for (int i = 0; i <newsNeanList.size() ; i++) {
                    if( newsNeanList.get(i).getName().equals(name)) {



                        currentIndex = 0;
                        objects .add(newsNeanList.get(i));

                    }
                }
                System.out.println("name=============+diqu===="+objects.size());
                setAdapterList(objects);


            }
        });
    }

    @Override
    protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();
        List<NewsNean> NewsNeanListss = new ArrayList<>();
        mActivity. horizontalListViewAdapter.SetData(NewsNeanListss);
        mActivity.et_seacher.setText("");
        getData();

        if(!MyApplication.ISONCLICKSEA){
            List<NewsNean> NewsNeanListzz= new ArrayList<>();
            mActivity.horizontalListViewAdapter.SetData(NewsNeanListzz);
            ClearData();
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

//

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            //     tvLoading.setVisibility(View.GONE);
            int id = 0;
            switch (msg.what) {
                case 1:

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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ForFrist(MessageEvent messageEvent) {
//        Des=true;
//        DesTimer();
//        System.out.println("========================执行了=====");
//
//        mActivity.setFrist();
        mHadler.sendEmptyMessage(0);
    }
    private void NewsListLast(){
      //  NewsList.clear();

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
            List<NewsNean> updateList = NewsNeanListCurrentSelect.subList(start, end);
            indicatorSeekBar.setMax(pageCount);
            indicatorSeekBar.setProgress(current);
            mAllFragmentAdapter.SetDate(updateList);
        }else{
            current=1;
        }

        setCreateTime();

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

            System.out.println(totalCount + "start====" + start + "=end=---==" + end);
            if(end>totalCount){
                end=totalCount;
            }
            if(start<0){
                start=0;
            }
            if(end<0){
                end=0;
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


    @Override
    public void onClick(View v) {
        MyApplication.ISONCLICKSEA=true;
        switch (v.getId()){
            case R.id.beijing:
                setDataDiqu("北京");
                break;
            case R.id.tianjin:
                setDataDiqu("天津");
                break;
            case R.id.hebei:
                setDataDiqu("河北");
                break;
            case R.id.shanxi:
                setDataDiqu("山西");
                break;
            case R.id.neimenggu:
                setDataDiqu("内蒙古");
                break;
            case R.id.liaoning:
                setDataDiqu("辽宁");
                break;
            case R.id.jilin:
                setDataDiqu("吉林");
                break;
            case R.id.heilongjiang:
                setDataDiqu("黑龙江");
                break;
            case R.id.shanghai:
                setDataDiqu("上海");
                break;
            case R.id.jiangsu:
                setDataDiqu("江苏");
                break;
            case R.id.zhejiang:
                setDataDiqu("浙江");
                break;
            case R.id.anhui:
                setDataDiqu("安徽");
                break;
            case R.id.fujian:
                setDataDiqu("福建");
                break;
            case R.id.jiangxi:
                setDataDiqu("江西");
                break;
            case R.id.shand:
                setDataDiqu("山东");
                break;
            case R.id.henan:
                setDataDiqu("河南");
                break;
            case R.id.hubei:
                setDataDiqu("湖北");
                break;
            case R.id.hunan:
                setDataDiqu("湖南");
                break;
            case R.id.guangdong:
                setDataDiqu("广东");
                break;
            case R.id.guangxi:
                setDataDiqu("广西");
                break;
            case R.id.hainan:
                setDataDiqu("海南");
                break;
            case R.id.chongqing:
                setDataDiqu("重庆");
                break;
            case R.id.sichuan:
                setDataDiqu("四川");
                break;
            case R.id.guizhou:
                setDataDiqu("贵州");
                break;
            case R.id.yunnan:
                setDataDiqu("云南");
                break;
            case R.id.xizang:
                setDataDiqu("西藏");
                break;
            case R.id.shanxi2:
                setDataDiqu("陕西");
                break;
            case R.id.gansu:
                setDataDiqu("甘肃");
                break;
            case R.id.qinghai:
                setDataDiqu("青海");
                break;
            case R.id.ningxia:
                setDataDiqu("宁夏");
                break;
            case R.id.xinjiang:
                setDataDiqu("新疆");
                break;

        }
    }

    @Override
    protected void onFragmentStopLazy() {
        super.onFragmentStopLazy();

Des=true;
DesTimer();


    }

    private MainActivity mActivity;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (MainActivity) activity;
    }
    public void ClearData(){
        currentIndex=0;
        newslist_last.setVisibility(View.INVISIBLE);
        newslist_next.setVisibility(View.INVISIBLE);
      //  ISONCLICKSEA=false;
        if(null!=ll_diqu){
            ll_diqu.setVisibility(View.VISIBLE);
        }
        if(null!=NewsNeanListCurrent){
            NewsNeanListCurrent.clear();
        }
        if(null!=mAllFragmentAdapter){
            setAdapterList(NewsNeanListCurrent);
            indicatorSeekBar.setVisibility(View.INVISIBLE);
        }

    }

    private void setDataDiqu(String name){
        //System.out.println("========"+name);
        ll_diqu.setVisibility(View.GONE);
        NewsNeanListCurrent.clear();
        NewsNeanListdiqi.clear();
        for (int i = 0; i <NewsNeanList.size() ; i++) {
            NewsNean newsNean = NewsNeanList.get(i);
            if(name.equals(newsNean.getDistrict())){
              //  System.out.println("========"+name);
                NewsNeanListdiqi.add(newsNean);
            }

        }


        setStateFr(NewsNeanListdiqi,0);
        NewsNeanListCurrent.addAll(NewsNeanListdiqi);



        setAdapterList(NewsNeanListCurrent);
    }

    private void setAdapterList( List<NewsNean> NewsNeanLists){
        currentIndex=0;
        indicatorSeekBar.setVisibility(View.VISIBLE);
        NewsNeanListCurrentSelect=NewsNeanLists;
        setStateFr(NewsNeanLists,0);
        current=1;
        totalCount = NewsNeanLists.size();

        pageCount = (totalCount / pageSize) + ((totalCount % pageSize > 0) ? 1 : 0);

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


        setCreateTime();
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


}