/**
 *
 */
package com.taihold.shuangdeng.ui.home;

import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_USER_LOGOUT_SUCCESSED;

import java.lang.reflect.Method;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.taihold.shuangdeng.R;
import com.taihold.shuangdeng.common.FusionAction;
import com.taihold.shuangdeng.freamwork.ui.BasicActivity;
import com.taihold.shuangdeng.logic.home.HomePageLogic;
import com.taihold.shuangdeng.logic.home.IHomePageLogic;
import com.taihold.shuangdeng.logic.login.ILoginLogic;
import com.taihold.shuangdeng.logic.login.LoginLogic;
import com.taihold.shuangdeng.logic.model.CorpHonoerDbModel;
import com.taihold.shuangdeng.ui.mall.CartFragment;
import com.taihold.shuangdeng.ui.mall.CategoryFragment;
import com.taihold.shuangdeng.ui.mall.WebMallFragment;
import com.taihold.shuangdeng.ui.me.MeFragment;
import com.taihold.shuangdeng.util.StringUtil;
import com.taihold.shuangdeng.util.Utility;

/**
 * 首页列表
 *
 * @author 牛凡
 */
public class HomePageActivity extends BasicActivity
{
    /**
     * DEBUG_TAG
     */
    private static final String TAG = "HomePageActivity";
    
    private View mRootView;
    
    private static FragmentTabHost mHomeTable;
    
    private LayoutInflater mLayoutInflater;
    
    //    private Class mFragmentArray[] = { HomeFragment.class, CorpFragment.class,
    //            NewsFragment.class, WebMallFragment.class, MeFragment.class };
    
    private Class mFragmentArray[] = { WebMallFragment.class,
            CategoryFragment.class, CartFragment.class, MeFragment.class };
    
    private int mImageArray[] = { R.mipmap.icon_home_grey,
            R.mipmap.icon_category, R.mipmap.icon_shop_grey,
            R.mipmap.icon_my_grey };
    
    //    private int mTextArray[] = { R.string.home_tab_home,
    //            R.string.home_tab_corp, R.string.home_tab_news,
    //            R.string.home_tab_mall, R.string.home_tab_me };
    
    private int mTextArray[] = { R.string.home_tab_home,
            R.string.home_tab_corp, R.string.home_tab_mall,
            R.string.home_tab_me };
    
    private long exitTime = 0;
    
    public static HomePageLogic mHomeLogic;
    
    public static LoginLogic mLoginLogic;
    
    private List<CorpHonoerDbModel> corpHonoerList;
    
    private SharedPreferences mSp;
    
    //    private ProgressDialog mPbDialog;
    
    private View decorView;
    
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        //        Utility.setTranslucent(this);
        
        setContentView(R.layout.homepage_layout);
        
        //获取顶层视图
        decorView = getWindow().getDecorView();
        
        initView();
        
    }
    
    private void initView()
    {
        mRootView = findViewById(R.id.root_layout);
        mHomeTable = (FragmentTabHost) findViewById(R.id.home_table);
        
        mSp = getSharedPreferences("ChinaShoto", MODE_PRIVATE);
        
        mLayoutInflater = LayoutInflater.from(this);
        
        mHomeTable.setup(this,
                getSupportFragmentManager(),
                android.R.id.tabcontent);
        
        int count = mFragmentArray.length;
        for (int i = 0; i < count; i++)
        {
            // 新建一个tabSpec并给每个tabSpec按钮设置文字和视图;  
            TabHost.TabSpec tabSpec = mHomeTable.newTabSpec(getString(mTextArray[i]))
                    .setIndicator(getTabItemView(i));//为指示器设置视图
            // 将tabSpec关联到fragment;  
            mHomeTable.addTab(tabSpec, mFragmentArray[i], null);
            // 设置tabSpec默认的背景颜色;  
            mHomeTable.getTabWidget().getChildAt(i);
        }
        
        mHomeLogic.getNewsList(28, "home");
        mHomeLogic.getSliderList("home");
        
        //        mPbDialog = ProgressDialog.show(this,
        //                getString(R.string.app_name),
        //                getString(R.string.please_wait));
        
        ((ImageView) mHomeTable.getCurrentTabView()
                .findViewById(R.id.home_tab_icon)).setImageResource(R.mipmap.icon_home);
        ((TextView) mHomeTable.getCurrentTabView()
                .findViewById(R.id.home_tab_title)).setTextColor(getResources().getColor(R.color.colorPrimary));
        
        mHomeTable.getTabWidget()
                .getChildTabViewAt(3)
                .setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if (StringUtil.isNullOrEmpty(mSp.getString("loginname",
                                null))
                                && StringUtil.isNullOrEmpty(mSp.getString("password",
                                        null)))
                        {
                            startActivity(new Intent(FusionAction.LOGIN_ACTION));
                        }
                        else
                        {
                            mHomeTable.setCurrentTabByTag(getString(mTextArray[3]));
                        }
                    }
                });
        
        mHomeTable.getTabWidget()
                .getChildTabViewAt(2)
                .setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if (StringUtil.isNullOrEmpty(mSp.getString("loginname",
                                null))
                                && StringUtil.isNullOrEmpty(mSp.getString("password",
                                        null)))
                        {
                            startActivity(new Intent(FusionAction.LOGIN_ACTION));
                        }
                        else
                        {
                            mHomeTable.setCurrentTabByTag(getString(mTextArray[2]));
                        }
                    }
                });
        
        mHomeTable.setOnTabChangedListener(new TabHost.OnTabChangeListener()
        {
            @Override
            public void onTabChanged(String tabId)
            {
                if (tabId.equals(getString(mTextArray[0])))
                {
                    initTabState();
                    ((ImageView) mHomeTable.getCurrentTabView()
                            .findViewById(R.id.home_tab_icon)).setImageResource(R.mipmap.icon_home);
                    ((TextView) mHomeTable.getCurrentTabView()
                            .findViewById(R.id.home_tab_title)).setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                
                if (tabId.equals(getString(mTextArray[1])))
                {
                    initTabState();
                    ((ImageView) mHomeTable.getCurrentTabView()
                            .findViewById(R.id.home_tab_icon)).setImageResource(R.mipmap.icon_category_press);
                    ((TextView) mHomeTable.getCurrentTabView()
                            .findViewById(R.id.home_tab_title)).setTextColor(getResources().getColor(R.color.colorPrimary));
                    
                    mHomeLogic.getIntroduce();
                    mHomeLogic.getDevelopmentPath();
                    mHomeLogic.getCorpHonorList();
                }
                //                if (tabId.equals(getString(mTextArray[2])))
                //                {
                //                    initTabState();
                //                    ((ImageView) mHomeTable.getCurrentTabView()
                //                            .findViewById(R.id.home_tab_icon)).setImageResource(R.mipmap.icon_news);
                //                    ((TextView) mHomeTable.getCurrentTabView()
                //                            .findViewById(R.id.home_tab_title)).setTextColor(getResources().getColor(R.color.colorPrimary));
                //                    mHomeLogic.getNewsList(28, "news");
                //                    mHomeLogic.getSliderList("news");
                //                }
                
                if (tabId.equals(getString(mTextArray[2])))
                {
                    initTabState();
                    ((ImageView) mHomeTable.getCurrentTabView()
                            .findViewById(R.id.home_tab_icon)).setImageResource(R.mipmap.icon_shop);
                    ((TextView) mHomeTable.getCurrentTabView()
                            .findViewById(R.id.home_tab_title)).setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                
                if (tabId.equals(getString(mTextArray[3])))
                {
                    initTabState();
                    ((ImageView) mHomeTable.getCurrentTabView()
                            .findViewById(R.id.home_tab_icon)).setImageResource(R.mipmap.icon_my);
                    ((TextView) mHomeTable.getCurrentTabView()
                            .findViewById(R.id.home_tab_title)).setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                
            }
        });
        
    }
    
    private void initTabState()
    {
        ((ImageView) mHomeTable.getTabWidget()
                .getChildAt(0)
                .findViewById(R.id.home_tab_icon)).setImageResource(R.mipmap.icon_home_grey);
        ((TextView) mHomeTable.getTabWidget()
                .getChildAt(0)
                .findViewById(R.id.home_tab_title)).setTextColor(getResources().getColor(android.R.color.darker_gray));
        
        ((ImageView) mHomeTable.getTabWidget()
                .getChildAt(1)
                .findViewById(R.id.home_tab_icon)).setImageResource(R.mipmap.icon_category);
        ((TextView) mHomeTable.getTabWidget()
                .getChildAt(1)
                .findViewById(R.id.home_tab_title)).setTextColor(getResources().getColor(android.R.color.darker_gray));
        
        //        ((ImageView) mHomeTable.getTabWidget()
        //                .getChildAt(2)
        //                .findViewById(R.id.home_tab_icon)).setImageResource(R.mipmap.icon_news_grey);
        //        ((TextView) mHomeTable.getTabWidget()
        //                .getChildAt(2)
        //                .findViewById(R.id.home_tab_title)).setTextColor(getResources().getColor(android.R.color.darker_gray));
        
        ((ImageView) mHomeTable.getTabWidget()
                .getChildAt(2)
                .findViewById(R.id.home_tab_icon)).setImageResource(R.mipmap.icon_shop_grey);
        ((TextView) mHomeTable.getTabWidget()
                .getChildAt(2)
                .findViewById(R.id.home_tab_title)).setTextColor(getResources().getColor(android.R.color.darker_gray));
        
        ((ImageView) mHomeTable.getTabWidget()
                .getChildAt(3)
                .findViewById(R.id.home_tab_icon)).setImageResource(R.mipmap.icon_my_grey);
        ((TextView) mHomeTable.getTabWidget()
                .getChildAt(3)
                .findViewById(R.id.home_tab_title)).setTextColor(getResources().getColor(android.R.color.darker_gray));
        
    }
    
    /**
     * 给每个Tab按钮设置图标和文字
     */
    private View getTabItemView(int index)
    {
        View view = mLayoutInflater.inflate(R.layout.home_tab, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.home_tab_icon);
        TextView textView = (TextView) view.findViewById(R.id.home_tab_title);
        imageView.setImageResource(mImageArray[index]);
        textView.setText(mTextArray[index]);
        return view;
    }
    
    @Override
    protected void initLogic()
    {
        super.initLogic();
        mHomeLogic = (HomePageLogic) getLogicByInterfaceClass(IHomePageLogic.class);
        
        mLoginLogic = (LoginLogic) getLogicByInterfaceClass(ILoginLogic.class);
    }
    
    @Override
    protected void handleStateMessage(Message msg)
    {
        super.handleStateMessage(msg);
        switch (msg.what)
        {
        //            case REQUEST_GET_COMPANY_INTRODUCE_SUCCESSED:
        //
        //                String introduce = (String) msg.obj;
        //
        //                CorpFragment corp = (CorpFragment) getSupportFragmentManager().findFragmentByTag(getString(mTextArray[1]));
        //                corp.setCorpIntroduce(introduce);
        //
        //                break;
        //            case REQUEST_GET_COMPANY_INTRODUCE_FAILED:
        //
        //                break;
        //
        //            case REQUEST_GET_DEVELOPMENT_PATH_SUCCESSED:
        //
        //                List<DevPathDbModel> list = (List<DevPathDbModel>) msg.obj;
        //
        //                ((CorpFragment) getSupportFragmentManager().findFragmentByTag(getString(mTextArray[1]))).setDevPathList(list);
        //
        //                //                mHomeLogic.downloadImageList(list);
        //
        //                break;
        //
        //            case REQUEST_GET_DEVELOPMENT_PATH_FAIILED:
        //                break;
        //
        //            case REQUEST_DOWNLOAD_IMG_SUCCESSED:
        //
        //                List<DevPathDbModel> devPathList = (List<DevPathDbModel>) msg.obj;
        //
        //                ((CorpFragment) getSupportFragmentManager().findFragmentByTag(getString(mTextArray[1]))).setDevPathList(devPathList);
        //                ((CorpFragment) getSupportFragmentManager().findFragmentByTag(getString(mTextArray[1]))).notifyDataSetChanged();
        //
        //                break;
        //
        //            case REQUEST_CORP_HONOER_ICON_SUCCESSED:
        //
        //                break;
        //
        //            case REQUEST_CORP_HONOER_LIST_SUCCESSED:
        //
        //                corpHonoerList = (List<CorpHonoerDbModel>) msg.obj;
        //
        //                ((CorpFragment) getSupportFragmentManager().findFragmentByTag(getString(mTextArray[1]))).setHonoerList(corpHonoerList);
        //
        //                break;
        //
        //            case REQUEST_NEWS_SLIDER_IMAGE_MAPS_FROM_HOME_SUCCESSED:
        //                List<SliderDbModel> imageSliderList = (List<SliderDbModel>) msg.obj;
        //
        //                ((HomeFragment) getSupportFragmentManager().findFragmentByTag(getString(mTextArray[0]))).setmImageMaps(imageSliderList);
        //
        //                break;
        //
        //            case REQUEST_NEWS_SLIDER_IMAGE_MAPS_FROM_NEWS_SUCCESSED:
        //                List<SliderDbModel> newsImageSliderList = (List<SliderDbModel>) msg.obj;
        //                ((NewsFragment) getSupportFragmentManager().findFragmentByTag(getString(mTextArray[2]))).setImageMap(newsImageSliderList);
        //
        //                break;
        //
        //            case REQUEST_NEWS_LIST_FROM_HOME_SUCCESSED:
        //                List<NewsDbModel> newsList = (List<NewsDbModel>) msg.obj;
        //
        //                ((HomeFragment) getSupportFragmentManager().findFragmentByTag(getString(mTextArray[0]))).setNewsList(newsList);
        //                ((HomeFragment) getSupportFragmentManager().findFragmentByTag(getString(mTextArray[0]))).notifyDataSetChanged();
        //
        //                //                if (mPbDialog.isShowing())
        //                //                {
        //                //                    mPbDialog.dismiss();
        //                //                }
        //                if (((HomeFragment) getSupportFragmentManager().findFragmentByTag(getString(mTextArray[0]))).isRefreshing())
        //                {
        //                    ((HomeFragment) getSupportFragmentManager().findFragmentByTag(getString(mTextArray[0]))).setRefreshing(false);
        //                }
        //
        //                break;
        //
        //            case REQUEST_NEWS_LIST_FROM_NEWS_CORP_SUCCESSED:
        //                List<NewsDbModel> corpNewsList = (List<NewsDbModel>) msg.obj;
        //
        //                ((NewsFragment) getSupportFragmentManager().findFragmentByTag(getString(mTextArray[2]))).setCorpNewsList("news",
        //                        corpNewsList);
        //                //                if (mPbDialog.isShowing())
        //                //                {
        //                //                    mPbDialog.dismiss();
        //                //                }
        //
        //                if (((NewsFragment) getSupportFragmentManager().findFragmentByTag(getString(mTextArray[2]))).isRefreshing("news"))
        //                {
        //                    ((NewsFragment) getSupportFragmentManager().findFragmentByTag(getString(mTextArray[2]))).setRefreshing("news",
        //                            false);
        //                }
        //                break;
        //
        //            case REQUEST_NEWS_LIST_FROM_NEWS_MEDIA_SUCCESSED:
        //                List<NewsDbModel> mediaNewsList = (List<NewsDbModel>) msg.obj;
        //
        //                ((NewsFragment) getSupportFragmentManager().findFragmentByTag(getString(mTextArray[2]))).setCorpNewsList("media",
        //                        mediaNewsList);
        //                //                if (mPbDialog.isShowing())
        //                //                {
        //                //                    mPbDialog.dismiss();
        //                //                }
        //
        //                if (((NewsFragment) getSupportFragmentManager().findFragmentByTag(getString(mTextArray[2]))).isRefreshing("media"))
        //                {
        //                    ((NewsFragment) getSupportFragmentManager().findFragmentByTag(getString(mTextArray[2]))).setRefreshing("media",
        //                            false);
        //                }
        //                break;
        //
        //            case REQUEST_NEWS_LIST_FROM_NEWS_EXHIBITION_SUCCESSED:
        //
        //                List<NewsDbModel> exhibitionNewsList = (List<NewsDbModel>) msg.obj;
        //
        //                ((NewsFragment) getSupportFragmentManager().findFragmentByTag(getString(mTextArray[2]))).setCorpNewsList("exhibition",
        //                        exhibitionNewsList);
        //                //                if (mPbDialog.isShowing())
        //                //                {
        //                //                    mPbDialog.dismiss();
        //                //                }
        //
        //                if (((NewsFragment) getSupportFragmentManager().findFragmentByTag(getString(mTextArray[2]))).isRefreshing("exhibition"))
        //                {
        //                    ((NewsFragment) getSupportFragmentManager().findFragmentByTag(getString(mTextArray[2]))).setRefreshing("exhibition",
        //                            false);
        //                }
        //                break;
        
            case REQUEST_USER_LOGOUT_SUCCESSED:
                
                Log.d(TAG, "logout successed!");
                
                ((MeFragment) getSupportFragmentManager().findFragmentByTag(getString(mTextArray[3]))).closeJsDialog();
                
                mHomeTable.setCurrentTabByTag(getString(mTextArray[0]));
                mSp.edit()
                        .putString("loginname", null)
                        .putString("password", null)
                        .commit();
                
                break;
        }
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            if ((System.currentTimeMillis() - exitTime) > 2000)
            {
                Toast.makeText(getApplicationContext(),
                        "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                
                //                Snackbar.make(mRootView, "再按一次退出程序", Snackbar.LENGTH_SHORT)
                //                        .setAction("", new View.OnClickListener()
                //                        {
                //                            @Override
                //                            public void onClick(View v)
                //                            {
                //                            }
                //                        })
                //                        .show();
                
                exitTime = System.currentTimeMillis();
            }
            else
            {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
    private void init()
    {
        int flag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        //判断当前版本在4.0以上并且存在虚拟按键，否则不做操作
        if (Build.VERSION.SDK_INT < 19 || !checkDeviceHasNavigationBar())
        {
            //一定要判断是否存在按键，否则在没有按键的手机调用会影响别的功能。如之前没有考虑到，导致图传全屏变成小屏显示。
            return;
        }
        else
        {
            // 获取属性
            decorView.setSystemUiVisibility(flag);
        }
    }
    
    /**
     * 判断是否存在虚拟按键
     *
     * @return
     */
    public boolean checkDeviceHasNavigationBar()
    {
        boolean hasNavigationBar = false;
        Resources rs = getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0)
        {
            hasNavigationBar = rs.getBoolean(id);
        }
        try
        {
            Class<?> systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass,
                    "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride))
            {
                hasNavigationBar = false;
            }
            else if ("0".equals(navBarOverride))
            {
                hasNavigationBar = true;
            }
        }
        catch (Exception e)
        {
            
        }
        return hasNavigationBar;
    }
    
}
