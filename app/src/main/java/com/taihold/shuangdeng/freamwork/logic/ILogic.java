package com.taihold.shuangdeng.freamwork.logic;

import android.content.Context;
import android.os.Handler;

/**
 * Logic接口
 * 
 * @author 牛凡
 * 
 */
public interface ILogic
{
    
    /**
     * Logic的初始化方法，在被注册到LogicBuilder后立即执行的初始化方法
     * 
     * @param context
     */
    public void init(Context context);
    
    /**
     * 给Logic加入Handler
     * 
     * @param handler
     *            UI传入的Handler
     */
    public void addHandler(Handler handler);
    
    /**
     * 移除Handler
     * 
     * @param handler
     *            UI传入的Handler
     */
    public void removeHandler(Handler handler);
    
}
