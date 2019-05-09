package com.jiuxingyuedu.horizontal.Activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jiuxingyuedu.horizontal.Adapter.HorizontalListViewAdapter;
import com.jiuxingyuedu.horizontal.Adapter.MyFragmentPagerAdapter;
import com.jiuxingyuedu.horizontal.App.MyApplication;
import com.jiuxingyuedu.horizontal.Base.BaseActivity;
import com.jiuxingyuedu.horizontal.Bean.DateNews;
import com.jiuxingyuedu.horizontal.Fragment.AllFragment;
import com.jiuxingyuedu.horizontal.Fragment.DiQuFragment;
import com.jiuxingyuedu.horizontal.Fragment.HotFragment;
import com.jiuxingyuedu.horizontal.Fragment.LeiBieFragment;
import com.jiuxingyuedu.horizontal.Fragment.ZiXuFragment;
import com.jiuxingyuedu.horizontal.R;
import com.jiuxingyuedu.horizontal.Util.ActivityUtil;
import com.jiuxingyuedu.horizontal.Util.Configs;
import com.jiuxingyuedu.horizontal.Util.DataUtil;
import com.jiuxingyuedu.horizontal.Util.RadioButtonRestoreUtils;
import com.jiuxingyuedu.horizontal.view.IndicatorSeekBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jiuxingyuedu.horizontal.App.MyApplication.ISSHOWSEACHER;
import static com.jiuxingyuedu.horizontal.App.MyApplication.NewsNeanLists;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    public static final int CODE_RECORD_AUDIO = 0;
    public static final int CODE_GET_ACCOUNTS = 1;
    public static final int CODE_READ_PHONE_STATE = 2;
    public static final int CODE_CALL_PHONE = 3;
    public static final int CODE_CAMERA = 4;
    public static final int CODE_ACCESS_FINE_LOCATION = 5;
    public static final int CODE_ACCESS_COARSE_LOCATION = 6;
    public static final int CODE_READ_EXTERNAL_STORAGE = 7;
    public static final int CODE_WRITE_EXTERNAL_STORAGE = 8;
    public static final int CODE_ACCESS_NETWORK_STATE = 9;
    public static final int CODE_MULTI_PERMISSION = 100;
private  int index=0;
    public boolean ISShowSeach=false;
    public static final String PERMISSION_RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    public static final String PERMISSION_GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    public static final String PERMISSION_READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static final String PERMISSION_CALL_PHONE = Manifest.permission.CALL_PHONE;
    public static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    public static final String PERMISSION_ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String PERMISSION_ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String PERMISSION_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String ACCESS_NETWORK_STATE = Manifest.permission.ACCESS_NETWORK_STATE;
    private static final String[] requestPermissions = {
            PERMISSION_RECORD_AUDIO,
            PERMISSION_GET_ACCOUNTS,
            PERMISSION_READ_PHONE_STATE,
            PERMISSION_CALL_PHONE,
            PERMISSION_CAMERA,
            PERMISSION_ACCESS_FINE_LOCATION,
            PERMISSION_ACCESS_COARSE_LOCATION,
            PERMISSION_READ_EXTERNAL_STORAGE,
            PERMISSION_WRITE_EXTERNAL_STORAGE,
            ACCESS_NETWORK_STATE
    };

//    @BindView(R.id.vp)
//    ViewPager vp;
    @BindView(R.id.content)
FrameLayout content;
    @BindView(R.id.RG_Main)
    RadioGroup RG_Main;
    @BindView(R.id.dateTime)
    TextView dateTime;

    @BindView(R.id.indicator_seek_bar)
    public IndicatorSeekBar indicatorSeekBar;
    @BindView(R.id.et_seacher)
    public EditText et_seacher;
    @BindView(R.id.et_seacher1)
    public View et_seacher1;
    @BindView(R.id.ll_earch1)
    public LinearLayout ll_earch1;
    @BindView(R.id.ziA)
    Button ziA;
    @BindView(R.id.ziB)
    Button ziB;
    @BindView(R.id.ziC)
    Button ziC;
    @BindView(R.id.ziD)
    Button ziD;
    @BindView(R.id.ziE)
    Button ziE;
    @BindView(R.id.ziF)
    Button ziF;
    @BindView(R.id.ziG)
    Button ziG;
    @BindView(R.id.ziH)
    Button ziH;
    @BindView(R.id.ziI)
    Button ziI;
    @BindView(R.id.ziJ)
    Button ziJ;
    @BindView(R.id.ziK)
    Button ziK;
    @BindView(R.id.ziL)
    Button ziL;
    @BindView(R.id.ziM)
    Button ziM;
    @BindView(R.id.ziN)
    Button ziN;
    @BindView(R.id.ziO)
    Button ziO;
    @BindView(R.id.ziP)
    Button ziP;
    @BindView(R.id.ziQ)
    Button ziQ;
    @BindView(R.id.ziR)
    Button ziR;
    @BindView(R.id.ziS)
    Button ziS;
    @BindView(R.id.ziT)
    Button ziT;
    @BindView(R.id.ziU)
    Button ziU;
    @BindView(R.id.ziV)
    Button ziV;
    @BindView(R.id.ziW)
    Button ziW;
    @BindView(R.id.ziX)
    Button ziX;
    @BindView(R.id.ziY)
    Button ziY;
    @BindView(R.id.ziZ)
    Button ziZ;
    @BindView(R.id.ziDelate)
    Button ziDelate;
    @BindView(R.id.search_btn)
    public ImageView search_btn;

    @BindView(R.id.seacher_key)
    public RelativeLayout seacher_key;

    @BindView(R.id.iv_clear)
    public ImageView iv_clear;
    @BindView(R.id.btn1)
    public RadioButton btn1;
    @BindView(R.id.btn2)
    public RadioButton btn2;
    @BindView(R.id.btn3)
    public RadioButton btn3;
    @BindView(R.id.btn4)
    public RadioButton btn4;
    @BindView(R.id.btn5)
    public RadioButton btn5;
    @BindView(R.id.hlv)
    public com.jiuxingyuedu.horizontal.view.HorizontalListView hlv;
    public HorizontalListViewAdapter horizontalListViewAdapter;
    boolean ISONCLICKSEA;//是否可以点击搜索框
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
if(index==0){
    index+=1;
    initView();
}

                    break;
            }
        }
    };
    public Map<String, DateNews> DateNews = new HashMap<>();
    private FragmentManager supportFragmentManager;

    //private MyFragmentPagerAdapter myFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        btn1.setChecked(true);
        seacher_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dateTime.setText(dateTime.getText() + DataUtil.DataTime());
        getData(DataUtil.DateTime());


    }

    private void getData(final String time) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkGo.<String>get(Configs.BaseUrl + "jpg/" + time + "/Papers.xml")//http://interface.9stars.cn/jpg/2019/03/31/Papers.xml
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


    private void setRadioSize() {
        btn1.setTextSize(18);
        btn2.setTextSize(18);
        btn3.setTextSize(18);
        btn4.setTextSize(18);
        btn5.setTextSize(18);
    }

    private void parseXML(String data) {

        DateNews.clear();
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
                DateNews.put(id, dateNews);
            }

            Message obtain = Message.obtain();
            obtain.what = 0;
            mHandler.sendMessage(obtain);

        } catch (
                JDOMException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        }


    }


    private void initView() {
        MyApplication.ISONCLICKSEA=true;

        et_seacher.setInputType(InputType.TYPE_NULL);



        int screenWidth = getWindowManager().getDefaultDisplay().getWidth(); // 屏幕宽（像素，如：480px
        horizontalListViewAdapter = new HorizontalListViewAdapter(getApplicationContext(), screenWidth, NewsNeanLists);
        hlv.setAdapter(horizontalListViewAdapter);


//禁止进度条滑动
        indicatorSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });


        RG_Main.setOnCheckedChangeListener(this);
        ArrayList fragmentList = new ArrayList<>();
        fragmentList.add(AllFragment.newInstance(0, false));
        fragmentList.add(ZiXuFragment.newInstance(1, true));
        fragmentList.add(LeiBieFragment.newInstance(2, true));
        fragmentList.add(DiQuFragment.newInstance(3, true));
        fragmentList.add(HotFragment.newInstance(4, true));
        supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        //myFragmentPagerAdapter = new MyFragmentPagerAdapter(supportFragmentManager, fragmentList);
//        vp.setAdapter(myFragmentPagerAdapter);
//        vp.setOnPageChangeListener(this);
//        vp.setOffscreenPageLimit(4);
        //控制显示隐藏
        fragmentTransaction.replace(R.id.content, AllFragment.newInstance(0,true));
        //提交
        fragmentTransaction.commitAllowingStateLoss();
        setState();

        ziA.setOnClickListener(this);
        ziB.setOnClickListener(this);
        ziC.setOnClickListener(this);
        ziD.setOnClickListener(this);
        ziE.setOnClickListener(this);
        ziF.setOnClickListener(this);
        ziG.setOnClickListener(this);
        ziH.setOnClickListener(this);
        ziI.setOnClickListener(this);
        ziJ.setOnClickListener(this);
        ziK.setOnClickListener(this);
        ziL.setOnClickListener(this);
        ziM.setOnClickListener(this);
        ziN.setOnClickListener(this);
        ziO.setOnClickListener(this);
        ziP.setOnClickListener(this);
        ziQ.setOnClickListener(this);
        ziR.setOnClickListener(this);
        ziS.setOnClickListener(this);
        ziT.setOnClickListener(this);
        ziU.setOnClickListener(this);
        ziV.setOnClickListener(this);
        ziW.setOnClickListener(this);
        ziX.setOnClickListener(this);
        ziY.setOnClickListener(this);
        ziZ.setOnClickListener(this);
        ziDelate.setOnClickListener(this);
    }

    @Override
    protected void init() {
        // 判断权限

        for (int i = 0; i < requestPermissions.length; i++) {
            if (!hasPermission(requestPermissions[i])) {
                requestPermission(i, requestPermissions[i]);
            }
        }

    }

    @Override
    protected void initTask() {//调用定时器
        getData(DataUtil.DateTime());
    }

    // 处理请求权限结果
    @Override
    public void doRequestPermissionsResult(int requestCode, @NonNull int[] grantResults) {
        switch (requestCode) {
//            case ConstantUtil.PERMISSIONS_REQUEST_READ_PHONE_STATE:// 读取手机信息权限
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // 权限请求成功
//                    Toast.makeText(this, "权限请求成功", Toast.LENGTH_SHORT).show();
//                } else {
//                    // 权限请求失败
//                    Toast.makeText(this, "权限请求失败", Toast.LENGTH_SHORT).show();
//                }
//                break;
        }
    }

    // 处理网络状态结果
    @Override
    public void onNetChange(boolean netWorkState) {
        super.onNetChange(netWorkState);
        //     textView.setText();
        Toast.makeText(getApplicationContext(), netWorkState ? "有网络" : "无网络", 0).show();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    // 设置返回按钮的监听事件
    private long exitTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 监听返回键，点击两次退出程序
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 5000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else {
                ActivityUtil.getInstance().exitSystem();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //清理所有导航栏的状态
        if(!ISSHOWSEACHER){
            FragmentTransaction transaction = supportFragmentManager.beginTransaction();
            MyApplication.ISFORBack=false;
        clearState();
        switch (checkedId) {
            case R.id.btn1:
                //给ViewPager设置当前布局
                setRadioSize();
            //    vp.setCurrentItem(0);
                transaction.replace(R.id.content,AllFragment.newInstance(0,true));
                btn1.setTextSize(20);
                MyApplication.ISONCLICKSEA=true;
                break;
            case R.id.btn2:
                setRadioSize();
            //    vp.setCurrentItem(1);
                transaction.replace(R.id.content,ZiXuFragment.newInstance(1,true));
                btn2.setTextSize(20);
                MyApplication.ISONCLICKSEA=true;
                break;
            case R.id.btn3:
                setRadioSize();
              //  vp.setCurrentItem(2);
                LeiBieFragment leiBieFragment = LeiBieFragment.newInstance(2, true);
                transaction.replace(R.id.content,leiBieFragment);
                btn3.setTextSize(20);

             //   leiBieFragment.ClearData();
                MyApplication.ISONCLICKSEA=false;
                break;
            case R.id.btn4:
                setRadioSize();
             //   vp.setCurrentItem(3);
                DiQuFragment diQuFragment = DiQuFragment.newInstance(3, true);
                transaction.replace(R.id.content,diQuFragment);
                btn4.setTextSize(20);

             //   diQuFragment.ClearData();
                MyApplication.ISONCLICKSEA=false;
                break;
            case R.id.btn5:
                setRadioSize();
             //   vp.setCurrentItem(4);
                transaction.replace(R.id.content,HotFragment.newInstance(4,true));
                btn5.setTextSize(20);
                MyApplication.ISONCLICKSEA=true;
                break;
        }
            transaction.commitAllowingStateLoss();
        RadioButtonRestoreUtils.restoredRadioButton(checkedId,group,this,MainActivity.this);
        }
    }

    //初始化底部导航栏
    private void clearState() {
        btn3.setChecked(false);
        btn3.setChecked(false);
    }

    public void setState(String z) {



        if (z.equals("A")) {
            ziA.setEnabled(true);
        } else if (z.equals("B")) {
            ziB.setEnabled(true);
        } else if (z.equals("C")) {
            ziC.setEnabled(true);
        } else if (z.equals("D")) {
            ziD.setEnabled(true);
        } else if (z.equals("E")) {
            ziE.setEnabled(true);
        } else if (z.equals("F")) {
            ziF.setEnabled(true);
        } else if (z.equals("G")) {
            ziG.setEnabled(true);
        } else if (z.equals("H")) {
            ziH.setEnabled(true);
        } else if (z.equals("I")) {
            ziI.setEnabled(true);
        } else if (z.equals("J")) {
            ziJ.setEnabled(true);
        } else if (z.equals("K")) {
            ziK.setEnabled(true);
        } else if (z.equals("L")) {
            ziL.setEnabled(true);
        } else if (z.equals("M")) {
            ziM.setEnabled(true);
        } else if (z.equals("N")) {
            ziN.setEnabled(true);
        } else if (z.equals("O")) {
            ziO.setEnabled(true);
        } else if (z.equals("P")) {
            ziP.setEnabled(true);
        } else if (z.equals("Q")) {
            ziQ.setEnabled(true);
        } else if (z.equals("R")) {
            ziR.setEnabled(true);
        } else if (z.equals("S")) {
            ziS.setEnabled(true);
        } else if (z.equals("T")) {
            ziT.setEnabled(true);
        } else if (z.equals("U")) {
            ziU.setEnabled(true);
        } else if (z.equals("V")) {
            ziV.setEnabled(true);
        } else if (z.equals("W")) {
            ziW.setEnabled(true);
        } else if (z.equals("X")) {
            ziX.setEnabled(true);
        } else if (z.equals("Y")) {
            ziY.setEnabled(true);
            ziY.setClickable(true);
        } else if (z.equals("Z")) {
            ziZ.setEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    public void setState() {

        ziA.setEnabled(false);

        ziB.setEnabled(false);

        ziC.setEnabled(false);

        ziD.setEnabled(false);

        ziE.setEnabled(false);

        ziF.setEnabled(false);

        ziG.setEnabled(false);

        ziH.setEnabled(false);

        ziI.setEnabled(false);

        ziJ.setEnabled(false);

        ziK.setEnabled(false);

        ziL.setEnabled(false);

        ziM.setEnabled(false);

        ziN.setEnabled(false);

        ziO.setEnabled(false);

        ziP.setEnabled(false);

        ziQ.setEnabled(false);

        ziR.setEnabled(false);

        ziS.setEnabled(false);

        ziT.setEnabled(false);

        ziU.setEnabled(false);

        ziV.setEnabled(false);

        ziW.setEnabled(false);

        ziX.setEnabled(false);


        ziY.setEnabled(false);

        ziZ.setEnabled(false);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    String ziMu = "";

    @Override
    public void onClick(View v) {
        ziMu = et_seacher.getText().toString();

        switch (v.getId()) {

            case R.id.ziA:
                et_seacher.setText(ziMu += "A");
                break;
            case R.id.ziB:
                et_seacher.setText(ziMu += "B");
                break;
            case R.id.ziC:
                et_seacher.setText(ziMu += "C");
                break;
            case R.id.ziD:
                et_seacher.setText(ziMu += "D");
                break;
            case R.id.ziE:
                et_seacher.setText(ziMu += "E");
                break;
            case R.id.ziF:
                et_seacher.setText(ziMu += "F");
                break;
            case R.id.ziG:
                et_seacher.setText(ziMu += "G");
                break;
            case R.id.ziH:
                et_seacher.setText(ziMu += "H");
                break;
            case R.id.ziI:
                et_seacher.setText(ziMu += "I");
                break;
            case R.id.ziJ:
                et_seacher.setText(ziMu += "J");
                break;
            case R.id.ziK:
                et_seacher.setText(ziMu += "K");
                break;
            case R.id.ziL:
                et_seacher.setText(ziMu += "L");
                break;
            case R.id.ziM:
                et_seacher.setText(ziMu += "M");
                break;
            case R.id.ziN:
                et_seacher.setText(ziMu += "N");
                break;
            case R.id.ziO:
                et_seacher.setText(ziMu += "O");
                break;
            case R.id.ziP:
                et_seacher.setText(ziMu += "P");
                break;
            case R.id.ziQ:
                et_seacher.setText(ziMu += "Q");
                break;
            case R.id.ziR:
                et_seacher.setText(ziMu += "R");
                break;
            case R.id.ziS:
                et_seacher.setText(ziMu += "S");
                break;
            case R.id.ziT:
                et_seacher.setText(ziMu += "T");
                break;
            case R.id.ziU:
                et_seacher.setText(ziMu += "U");
                break;
            case R.id.ziV:
                et_seacher.setText(ziMu += "V");
                break;
            case R.id.ziW:
                et_seacher.setText(ziMu += "W");
                break;
            case R.id.ziX:
                et_seacher.setText(ziMu += "X");
                break;

            case R.id.ziY:
                et_seacher.setText(ziMu += "Y");
                break;
            case R.id.ziZ:
                et_seacher.setText(ziMu += "Z");
                break;
            case R.id.ziDelate:
                String text = et_seacher.getText().toString().trim();
                if (" ".equals(text) || "".equals(text)) {
                    et_seacher.setText("");
                } else {
                    char[] chars = text.toCharArray();
                    et_seacher.setText("");
                    String s = "";
                    for (int i = 0; i < chars.length - 1; i++) {
                        s += chars[i] + "";


                    }
                    et_seacher.setText(s);
                }
                break;

        }
    }

    public void  setFrist(){
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        MyApplication.ISFORBack=false;
        clearState();
        setRadioSize();
        //    vp.setCurrentItem(0);
        transaction.replace(R.id.content,AllFragment.newInstance(0,true));
        btn1.setTextSize(20);
        MyApplication.ISONCLICKSEA=true;
        transaction.commitAllowingStateLoss();
        RadioButtonRestoreUtils.restoredRadioButton(R.id.btn1,RG_Main,this,MainActivity.this);
    }

    @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                 // RESULT_OK，判断另外一个activity已经结束数据输入功能，Standard activity result:
               // operation succeeded. 默认值是-1
              if(data!=null){
                  setFrist();
              }
        System.out.println("=======================result");
            }
}
