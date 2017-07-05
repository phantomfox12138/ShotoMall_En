/**
 * 
 */
package com.taihold.shuangdeng.freamwork.logic;

import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * Activity的抽象基类<BR>
 * 
 * @author 牛凡
 * 
 */
public class BaseLogic implements ILogic
{
    /**
     * DEBUG_TAG
     */
    private static final String TAG = "BaseLogic";

    /**
     * Logic对UI的Handler的缓存
     */
    protected List<Handler> mHandlerList = new Vector<Handler>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(Context context)
    {
        // TODO: 可用于例如初始化DB监听等
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addHandler(Handler handler)
    {
        mHandlerList.add(handler);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeHandler(Handler handler)
    {
        mHandlerList.remove(handler);
    }

    /**
     * 向UI层发送空消息
     * 
     * @param what
     *            MSG_ID
     */
    public void sendEmptyMessage(int what)
    {
        synchronized (mHandlerList)
        {
            for (Handler handler : mHandlerList)
            {
                handler.sendEmptyMessage(what);
            }
        }
    }

    /**
     * 向UI层发送附带对象消息
     * 
     * @param what
     *            MSG_ID
     * @param obj
     *            附带对象
     */
    public void sendMessage(int what, Object obj)
    {
        if (null == obj)
        {
            sendEmptyMessage(what);
        } else
        {
            synchronized (mHandlerList)
            {

                for (Handler handler : mHandlerList)
                {
                    Message msg = new Message();
                    msg.what = what;
                    msg.obj = obj;
                    handler.sendMessage(msg);
                }

            }
        }
    }

    /**
     * 向UI层发送延迟空消息
     * 
     * @param what
     *            MSG_ID
     * @param delayMillis
     *            延迟时间（毫秒单位）
     */
    public void sendEmptyMessageDelay(int what, long delayMillis)
    {
        synchronized (mHandlerList)
        {
            for (Handler handler : mHandlerList)
            {
                handler.sendEmptyMessageDelayed(what, delayMillis);
            }
        }
    }

    /**
     * 向UI层发送附带对象的延迟消息
     * 
     * @param what
     *            MSG_ID
     * @param obj
     *            附带对象
     * @param delayMillis
     *            延迟时间（毫秒单位）
     */
    public void sendMessageDelay(int what, Object obj, long delayMillis)
    {
        if (null == obj)
        {
            sendEmptyMessageDelay(what, delayMillis);
        } else
        {
            synchronized (mHandlerList)
            {
                for (Handler handler : mHandlerList)
                {
                    Message msg = new Message();
                    msg.what = what;
                    msg.obj = obj;
                    handler.sendMessageDelayed(msg, delayMillis);
                }
            }
        }

    }
}
