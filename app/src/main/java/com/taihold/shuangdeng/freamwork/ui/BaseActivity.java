/**
 * 
 */
package com.taihold.shuangdeng.freamwork.ui;

import java.util.HashSet;
import java.util.Set;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.taihold.shuangdeng.freamwork.logic.BaseLogicBuilder;
import com.taihold.shuangdeng.freamwork.logic.ILogic;

/**
 * Activity抽象基类
 * 
 * @author 牛凡
 */
public abstract class BaseActivity extends AppCompatActivity
{
    
    /**
     * DEBUG_TAG
     */
    private static final String TAG = "BaseActivity";
    
    /**
     * Logic构造器
     */
    private static BaseLogicBuilder mLogicBuilder = null;
    
    /**
     * Activity所持有的Handler
     */
    private Handler mHanlder = null;
    
    /**
     * 是否独立持有Handler
     */
    private boolean isPrivateHandler = false;
    
    /**
     * Logic对象缓存集合
     */
    private Set<ILogic> mLogicSet = new HashSet<ILogic>();
    
    /**
     * 超类Activity初始化方法
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        Log.d(TAG, "isInit() = " + isInit());
        if (!isInit())
        {
            Log.e(TAG,
                    "Launched the first should be the LauncheActivity's subclass:"
                            + this.getClass().getName(),
                    new Throwable());
            return;
        }
        
        if (!isPrivateHandler())
        {
            BaseActivity.mLogicBuilder.addHandlerToAllLogic(getHandler());
        }
        
        try
        {
            initLogic();
        }
        catch (Exception e)
        {
            Toast.makeText(this.getApplicationContext(), "Init logics failed :"
                    + e.getMessage(), Toast.LENGTH_LONG);
            Log.e(TAG, "Init logics failed :" + e.getMessage(), e);
        }
        
    }
    
    /**
     * 设置LogicBuilder 子类不可重写
     * 
     * @param builder
     *            BaseLogicBuilder
     */
    protected static final void setLogicBuilder(BaseLogicBuilder builder)
    {
        BaseActivity.mLogicBuilder = builder;
    }
    
    /**
     * 初始化Activity所注册的Logic对象 <BR>
     * 在该方法里通过getLogicByInterfaceClass获取logic对象
     */
    protected abstract void initLogic();
    
    /**
     * 获得BaseActivity的Handler
     */
    private Handler getHandler()
    {
        if (null == mHanlder)
        {
            mHanlder = new Handler()
            {
                @Override
                public void handleMessage(Message msg)
                {
                    BaseActivity.this.handleStateMessage(msg);
                }
            };
        }
        return mHanlder;
    }
    
    protected boolean isPrivateHandler()
    {
        return isPrivateHandler;
    }
    
    /**
     * logic通过handler回调的方法<BR>
     * 通过子类重载可以实现各个logic的sendMessage到handler里的回调方法
     * 
     * @param msg
     *            Message对象
     */
    protected void handleStateMessage(Message msg)
    {
        
    }
    
    /**
     * 判断是否对LogicBuilder初始化完成
     * 
     * @return true完成初始化/false 为完成初始化
     */
    protected boolean isInit()
    {
        return mLogicBuilder != null;
    }
    
    /**
     * 通过接口获取Logic对象
     * 
     * @param interfaceClass
     *            接口对象
     * @return 对应的借口对象
     */
    @SuppressWarnings("null")
    protected final ILogic getLogicByInterfaceClass(Class<?> interfaceClass)
    {
        ILogic logic = mLogicBuilder.getLogicByInterface(interfaceClass);
        Log.d(TAG, "logic = " + (null == logic));
        
        if (isPrivateHandler() && null != logic && !mLogicSet.contains(logic))
        {
            mLogicSet.add(logic);
        }
        
        if (null == logic)
        {
            Log.e(TAG, "Logic not found: " + logic.getClass().getName());
            return null;
        }
        
        return logic;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void onDestroy()
    {
        Handler handler = getHandler();
        
        if (null != handler)
        {
            if (isPrivateHandler() && null != mLogicSet)
            {
                for (ILogic logic : mLogicSet)
                {
                    logic.removeHandler(handler);
                }
            }
            else if (null != mLogicBuilder)
            {
                mLogicBuilder.removeHandlerFromAllLogic(handler);
            }
        }
        super.onDestroy();
    }
}
