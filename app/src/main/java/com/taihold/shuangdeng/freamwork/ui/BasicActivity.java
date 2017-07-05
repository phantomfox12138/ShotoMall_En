/**
 * 
 */
package com.taihold.shuangdeng.freamwork.ui;

import java.util.Properties;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.taihold.shuangdeng.freamwork.logic.BaseLogicBuilder;
import com.taihold.shuangdeng.logic.LogicBuilder;

/**
 * * UI 层基类Activity<BR>
 * 包含UI 层的公用功能
 * 
 * @author 牛凡
 */
public abstract class BasicActivity extends LauncheActivity
{
    /**
     * DEBUG_TAG
     */
    private static final String TAG = "BasicActivity";
    
    /**
     * 当前Activity
     */
    private static BasicActivity mCurrentActivity;
    
    /**
     * Activity堆栈
     */
    protected MCActivityManager mActivityManger = MCActivityManager.getInstance();
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        mActivityManger.addActivity(this);
        
    }
    
    protected void initDialogActivity()
    {
        //窗口对齐屏幕宽度
        Window win = this.getWindow();
        win.getDecorView().setPadding(30, 0, 30, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;//设置对话框置顶显示
        win.setAttributes(lp);
    }
    
    private Properties getProperties()
    {
        Properties props = new Properties();
        try
        {
            int id = this.getResources().getIdentifier("theme_config",
                    "raw",
                    getPackageName());
            props.load(getResources().openRawResource(id));
        }
        catch (Exception e)
        {
            Log.i(TAG, "Could not find the properties file.");
        }
        return props;
    }
    
    @Override
    protected void initSystem()
    {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void initLogic()
    {
        // 实现父类的抽象方法.子类就可以选择性实现此方法了
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected BaseLogicBuilder createLogicBuilder(Context context)
    {
        BaseLogicBuilder builder = LogicBuilder.getInstance(context);
        return builder;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void onResume()
    {
        mCurrentActivity = this;
        super.onResume();
        
    }
    
    /**
     * 弹出Toast
     * 
     * @param str
     *            字符串内容
     * @param duration
     *            显示时常
     */
    public void showToast(String str, int duration)
    {
        Toast.makeText(this, str, duration).show();
    }
    
    /**
     * 弹出Toast
     * 
     * @param resId
     *            String资源Id
     * @param duration
     *            显示时常
     */
    public void showToast(int resId, int duration)
    {
        Toast.makeText(this, resId, duration).show();
    }
    
    /**
     * Activity销毁时移除堆栈
     */
    @Override
    public void finish()
    {
        Log.d(TAG, "finish");
        mActivityManger.removeActivity(this);
        super.finish();
    }
    
    /**
     * TitleBar右侧按钮点击事件
     */
    public void onRightBtnClick()
    {
        
    }
    
    /**
     * 获取屏幕宽度
     * 
     * @return
     */
    public String getscreen()
    {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int h = dm.heightPixels;
        int w = dm.widthPixels;
        return w + "x" + h;
    }
    
    /**
     * 获取版本号
     * 
     * @param context
     * @return
     */
    public String getAppVersionName(Context context)
    {
        String versionName = "";
        try
        {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0)
            {
                return "";
            }
        }
        catch (Exception e)
        {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }
    
    /**
     * 获取联网类型
     * 
     * @param context
     * @return
     */
    public String getNet(Context context)
    {
        if (context != null)
        {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo == null || !mNetworkInfo.isAvailable())
            {
                // return FusionConfig.INTERNET_CONNECTED_NO_INTERNET_CONNECTED;
            }
            else if (mNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
            {
                return "2g";
            }
            else if (mNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI)
            {
                return "wifi";
            }
        }
        return "null";
        
    }
    
}
