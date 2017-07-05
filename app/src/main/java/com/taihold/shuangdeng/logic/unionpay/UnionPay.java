package com.taihold.shuangdeng.logic.unionpay;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import com.taihold.shuangdeng.common.HttpHelper;
import com.unionpay.UPPayAssistEx;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by niufan on 17/5/16.
 */

public class UnionPay implements Handler.Callback, Runnable
{
    private static final String TAG = "UnionPay";
    
    /*****************************************************************
     * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
     *****************************************************************/
    private final String mMode = "00";
    
    private String TN_URL_01 = "http://www.shuangdeng.com.cn/shoto/shotoOrder/unionPay?payMethod=0219";
    
    private Context mContext;
    
    private Handler mHandler = null;
    
    private ProgressDialog mLoadingDialog = null;
    
    private RequestQueue mQueue;
    
    private String orderId;
    
    public UnionPay(Context context)
    {
        this.mContext = context;
        mHandler = new Handler(this);
        mQueue = NoHttp.newRequestQueue();
    }
    
    public void startPay(String orderId)
    {
        this.orderId = orderId;
        
        TN_URL_01 = TN_URL_01 + "&" + HttpHelper.Pay_Attr.ORDER_ID + "="
                + orderId;
        
        mLoadingDialog = ProgressDialog.show(mContext, // context
                "", // title
                "正在努力的获取tn中,请稍候...", // message
                true); // 进度是否是不确定的，这只和创建进度条有关
        
        /*************************************************
         * 步骤1：从网络开始,获取交易流水号即TN
         ************************************************/
        new Thread(this).start();
    }
    
    @Override
    public boolean handleMessage(Message msg)
    {
        //        Log.e(LOG_TAG, " " + "" + msg.obj);
        if (mLoadingDialog.isShowing())
        {
            mLoadingDialog.dismiss();
        }
        
        String tn = "";
        if (msg.obj == null || ((String) msg.obj).length() == 0)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("错误提示");
            builder.setMessage("网络连接失败,请重试!");
            builder.setNegativeButton("确定",
                    new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        }
        else
        {
            tn = (String) msg.obj;
            /*************************************************
             * 步骤2：通过银联工具类启动支付插件
             ************************************************/
            //            doStartUnionPayPlugin(this, tn, mMode);
            UPPayAssistEx.startPay(mContext, null, null, tn, mMode);
        }
        
        return false;
    }
    
    @Override
    public void run()
    {
        String tn = null;
        InputStream is;
        try
        {
            
            String url = TN_URL_01;
            
            URL myURL = new URL(url);
            URLConnection ucon = myURL.openConnection();
            ucon.setConnectTimeout(120000);
            is = ucon.getInputStream();
            int i = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((i = is.read()) != -1)
            {
                baos.write(i);
            }
            
            tn = baos.toString();
            
            Log.d(TAG, "union pay tn code = " + tn);
            is.close();
            baos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        Message msg = mHandler.obtainMessage();
        msg.obj = tn;
        mHandler.sendMessage(msg);
    }
    
    public void processPayResult(Intent data)
    {
        /*************************************************
         * 步骤3：处理银联手机支付控件返回的支付结果
         ************************************************/
        if (data == null)
        {
            return;
        }
        
        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success"))
        {
            
            // 如果想对结果数据验签，可使用下面这段代码，但建议不验签，直接去商户后台查询交易结果
            // result_data结构见c）result_data参数说明
            if (data.hasExtra("result_data"))
            {
                String result = data.getExtras().getString("result_data");
                try
                {
                    JSONObject resultJson = new JSONObject(result);
                    String sign = resultJson.getString("sign");
                    String dataOrg = resultJson.getString("data");
                    // 此处的verify建议送去商户后台做验签
                    // 如要放在手机端验，则代码必须支持更新证书 
                    boolean ret = true;
                    verify(dataOrg, sign, mMode);
                    if (ret)
                    {
                        // 验签成功，显示支付结果
                        msg = "支付成功！";
                    }
                    else
                    {
                        // 验签失败
                        msg = "支付失败！";
                    }
                }
                catch (JSONException e)
                {
                }
            }
            // 结果result_data为成功时，去商户后台查询一下再展示成功
            msg = "支付成功！";
        }
        else if (str.equalsIgnoreCase("fail"))
        {
            msg = "支付失败！";
        }
        else if (str.equalsIgnoreCase("cancel"))
        {
            msg = "用户取消了支付";
        }
        
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("支付结果通知");
        builder.setMessage(msg);
        builder.setInverseBackgroundForced(true);
        // builder.setCustomTitle();
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    
    public boolean verify(String msg, String sign64, String mode)
    {
        String verifyUrl = "http://www.shuangdeng.com.cn/shoto/unionPay/verifyAppData";
        
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(verifyUrl,
                RequestMethod.POST);
        
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("sign", sign64);
            jsonObject.put("data", msg);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        
        request.add("param", jsonObject.toString());
        
        mQueue.add(1, request, new OnResponseListener<JSONObject>()
        {
            @Override
            public void onStart(int what)
            {
                
            }
            
            @Override
            public void onSucceed(int what, Response<JSONObject> response)
            {
                Log.d(TAG, "union pay verify = " + response.get().toString());
            }
            
            @Override
            public void onFailed(int what, Response<JSONObject> response)
            {
                
            }
            
            @Override
            public void onFinish(int what)
            {
                
            }
        });
        
        return true;
        
    }
}
