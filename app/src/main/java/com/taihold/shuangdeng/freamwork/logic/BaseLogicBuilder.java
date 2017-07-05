/**
 * 
 */
package com.taihold.shuangdeng.freamwork.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

/**
 * 抽象的logic建造类<BR>
 * 抽象的logic建造类，用戶緩存logic和集中增加和移除handler
 * 
 * @author 牛凡
 */
public abstract class BaseLogicBuilder implements ILogicBuilder
{
    /**
     * DEBUG_TAG
     */
    private static final String TAG = "BaseLogicBuilder";
    
    /**
     * Logic对象缓存
     */
    private Map<String, ILogic> mCacheLogicMap = new HashMap<String, ILogic>();
    
    /**
     * DEFAULT_CONSTRUCTOR
     * 
     * @param context
     *            Context
     */
    public BaseLogicBuilder(Context context)
    {
        initLogic(context);
        
        initAllLogics(context);
    }
    
    /**
     * 初始化Logic
     * 
     * @param context
     *            Context
     */
    public abstract void initLogic(Context context);
    
    /**
     * 初始化所有Logic
     * 
     * @param context
     *            Context
     */
    private void initAllLogics(Context context)
    {
        Set<Entry<String, ILogic>> logics = mCacheLogicMap.entrySet();
        
        for (Entry<String, ILogic> logicEntry : logics)
        {
            logicEntry.getValue().init(context);
        }
    }
    
    /**
     * 根据接口类注册对应的logic对象到管理缓存中<BR>
     * 一般在子类的init方法中被执行，否则logic对象的init方法就不被调用
     * 
     * @param interfaceClass
     *            logic的接口类
     * @param logic
     *            ILogic的实现类
     */
    protected void registerLogic(Class<?> interfaceClass, ILogic logic)
    {
        String implName = interfaceClass.getName();
        if (!mCacheLogicMap.containsKey(implName))
        {
            if (isInterface(logic.getClass(), interfaceClass.getName())
                    && isInterface(logic.getClass(), ILogic.class.getName()))
            {
                mCacheLogicMap.put(implName, logic);
            }
            else
            {
                Log.e(TAG,
                        "interface " + implName + " register failed!",
                        new Throwable());
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void addHandlerToAllLogic(Handler handler)
    {
        Set<Entry<String, ILogic>> logics = mCacheLogicMap.entrySet();
        for (Entry<String, ILogic> logicEntry : logics)
        {
            ILogic logic = logicEntry.getValue();
            logic.addHandler(handler);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void removeHandlerFromAllLogic(Handler handler)
    {
        Set<Entry<String, ILogic>> logics = mCacheLogicMap.entrySet();
        for (Entry<String, ILogic> logicEntry : logics)
        {
            ILogic logic = logicEntry.getValue();
            logic.removeHandler(handler);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ILogic getLogicByInterface(Class<?> interfaceClass)
    {
        
        return mCacheLogicMap.get(interfaceClass.getName());
    }
    
    /**
     * 判断子类是否实现了接口
     * 
     * @param interfaceClass
     *            子类对象
     * @param className
     *            借口名称
     * @return 是否实现了接口
     */
    private boolean isInterface(Class<?> interfaceClass, String className)
    {
        Class<?>[] face = interfaceClass.getInterfaces();
        for (int i = 0, j = face.length; i < j; i++)
        {
            if (face[i].getName().equals(className))
            {
                return true;
            }
            else
            {
                Class<?>[] face1 = face[i].getInterfaces();
                for (int x = 0; x < face1.length; x++)
                {
                    if (face1[x].getName().equals(className))
                    {
                        return true;
                    }
                    else if (isInterface(face1[x], className))
                    {
                        return true;
                    }
                }
            }
        }
        if (null != interfaceClass.getSuperclass())
        {
            return isInterface(interfaceClass.getSuperclass(), className);
        }
        return false;
    }
}
