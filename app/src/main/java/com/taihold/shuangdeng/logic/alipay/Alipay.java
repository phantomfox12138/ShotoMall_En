package com.taihold.shuangdeng.logic.alipay;

import java.util.Map;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.taihold.shuangdeng.R;

/**
 * Created by niufan on 17/5/5.
 */

public class Alipay
{
    private static final String TAG = "Alipay";
    
    private static final int SDK_PAY_FLAG = 1;
    
    private Activity activity;
    
    private Handler mHandler = new Handler()
    {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case SDK_PAY_FLAG:
                {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult(
                            (Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    Log.d(TAG, "resultInfo = " + resultInfo);
                    
                    String resultStatus = payResult.getResultStatus();
                    String message = payResult.getMemo();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000"))
                    {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(activity,
                                R.string.payout_success,
                                Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        Toast.makeText(activity, message, Toast.LENGTH_SHORT)
//                                .show();
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };
    
    public Alipay(Activity activity)
    {
        this.activity = activity;
    }
    
    public void payV2(final String orderInfo)
    {
        Runnable payRunnable = new Runnable()
        {
            @Override
            public void run()
            {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());
                
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    
}
