package com.taihold.shuangdeng.logic.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.taihold.shuangdeng.R;
import com.taihold.shuangdeng.common.FusionAction;
import com.taihold.shuangdeng.common.HttpHelper;
import com.taihold.shuangdeng.ui.WelcomeActivity;

import cn.jpush.android.api.JPushInterface;

public class PushReceiver extends BroadcastReceiver
{
    
    /**
     * DEBUG_TAG
     */
    private static final String TAG = "PushReceiver";
    
    @Override
    public void onReceive(Context context, Intent intent)
    {
        //接收Registration Id
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction()))
        {
            Log.d(TAG, "接收Registration Id");
        }
        //接收到推送下来的自定义消息
        else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction()))
        {
            Log.d(TAG, "接收到推送下来的自定义消息");
        }
        //接收到推送下来的通知
        else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction()))
        {
            Log.d(TAG, "接收到推送下来的通知");
            
        }
        //用户点击打开了通知
        else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction()))
        {
            Log.d(TAG, "用户点击打开了通知");
            
            String extraStr = intent.getStringExtra(JPushInterface.EXTRA_EXTRA);
            
            Log.d(TAG, "用户点击打开了通知,附加内容：" + extraStr);
            
            //            Intent i = new Intent(FusionAction.WEB_ACTION);
            Intent i = new Intent(context, WelcomeActivity.class);
            i.putExtra(FusionAction.WEB_ATTR.URL, HttpHelper.TO_ORDER_DETAIL
                    + HttpHelper.OrderDetailAttr.ORDER_ID + "=" + extraStr);
            i.putExtra(FusionAction.WEB_ATTR.FROM_WHERE, "push");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            
            context.startActivity(i);
        }
        //用户收到到RICH PUSH CALLBACK
        else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction()))
        {
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
            Log.d(TAG, "用户收到到RICH PUSH CALLBACK");
        }
        //connected state change
        else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction()))
        {
            Log.d(TAG, "connected state change");
        }
    }
}
