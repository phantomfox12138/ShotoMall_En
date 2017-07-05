/**
 * 
 */
package com.taihold.shuangdeng.freamwork.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.taihold.shuangdeng.freamwork.logic.BaseLogicBuilder;

/**
 * 首次启动时必须加载，其他子类不需要继承
 * 
 * @author 牛凡
 */
public abstract class LauncheActivity extends BaseActivity
{
    
    /**
     * DEBUG_TAG
     */
    private static final String TAG = "LauncheActivity";
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if (!isInit())
        {
            BaseLogicBuilder builder = createLogicBuilder(this.getApplicationContext());
            super.setLogicBuilder(builder);
            Log.d(TAG, "LogicBuilder loaded successfully!");
            initSystem();
        }
        super.onCreate(savedInstanceState);
    }
    
    /**
     * Logic建造管理类需要创建的接口<BR>
     * 需要子类继承后，指定Logic建造管理类具体实例
     * 
     * @param context
     *            系统的context对象
     * @return Logic建造管理类具体实例
     */
    protected abstract BaseLogicBuilder createLogicBuilder(Context context);
    
    /**
     * 初始化系统具体工作<BR>
     * 诸如：用户身份，留给子类具体实现
     */
    protected abstract void initSystem();
    
}
