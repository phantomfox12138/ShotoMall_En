/**
 * 
 */
package com.taihold.shuangdeng.logic;

import android.content.Context;

import com.taihold.shuangdeng.freamwork.logic.BaseLogicBuilder;
import com.taihold.shuangdeng.logic.home.HomePageLogic;
import com.taihold.shuangdeng.logic.home.IHomePageLogic;
import com.taihold.shuangdeng.logic.login.ILoginLogic;
import com.taihold.shuangdeng.logic.login.LoginLogic;

/**
 * Logic构造器
 * 
 * @author 牛凡
 */
public class LogicBuilder extends BaseLogicBuilder
{
    /**
     * DEBUG_TAG
     */
    private static final String TAG = "LogicBuilder";
    
    /**
     * 单例对象
     */
    private static LogicBuilder sInstance;
    
    /**
     * 单例构造方法
     * 
     * @param context
     *            context
     * @return 单例对象
     */
    public static LogicBuilder getInstance(Context context)
    {
        if (null == sInstance)
        {
            sInstance = new LogicBuilder(context);
        }
        return sInstance;
    }
    
    /**
     * DEFAULT_CONSTRUCTOR
     * 
     * @param context
     *            Context
     */
    private LogicBuilder(Context context)
    {
        super(context);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void initLogic(Context context)
    {
        registAllLogics(context);
    }
    
    /**
     * 注册所有Logic对象
     * 
     * @param context
     *            Context
     */
    private void registAllLogics(Context context)
    {
        // 首页Logic
        HomePageLogic slash = new HomePageLogic(context);
        //登录注册
        LoginLogic login = new LoginLogic(context);


        registerLogic(IHomePageLogic.class, slash);
        registerLogic(ILoginLogic.class, login);
        
    }
    
}
