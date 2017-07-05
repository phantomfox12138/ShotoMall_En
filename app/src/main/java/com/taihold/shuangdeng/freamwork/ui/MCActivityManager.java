/**
 * 
 */
package com.taihold.shuangdeng.freamwork.ui;

import java.util.Stack;

import android.app.Activity;
import android.util.Log;

/**
 * @author itany
 * 
 */
public class MCActivityManager
{
    
    /**
     * DEBUG_TAG
     */
    private static final String TAG = "MCActivityManager";
    
    /**
     * Activity栈管理
     */
    private Stack<Activity> mList = new Stack<Activity>();
    
    /**
     * 单例对象
     */
    private static MCActivityManager instance;
    
    /**
     * DEFAULT_CONSTRUCTOR
     */
    private MCActivityManager()
    {
    }
    
    public synchronized static MCActivityManager getInstance()
    {
        if (null == instance)
        {
            instance = new MCActivityManager();
        }
        return instance;
    }
    
    /**
     * 添加Activity到Activity堆栈管理中
     * 
     * @param activity
     *            Activity
     */
    public void addActivity(Activity activity)
    {
        Log.d(TAG, "activity = " + activity.getLocalClassName());
        mList.add(activity);
    }
    
    public void removeActivity(Activity activity)
    {
        mList.remove(activity);
    }
    
    /**
     * 退出应用时，销毁所有的Activity
     */
    public void exit()
    {
        try
        {
            for (Activity activity : mList)
            {
                Log.d(TAG,
                        mList.size() + "  " + "activity = "
                                + activity.getLocalClassName());
                if (activity != null)
                {
                    activity.finish();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            System.exit(0);
        }
    }
    
    /**
     * 清除activity
     */
    public void clearActivities()
    {
        try
        {
            for (Activity activity : mList)
            {
                Log.d(TAG,
                        mList.size() + "  " + "activity = "
                                + activity.getLocalClassName());
                if (activity != null)
                {
                    activity.finish();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
