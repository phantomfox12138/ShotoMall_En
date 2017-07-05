package com.taihold.shuangdeng.logic.wechatpay;

import android.content.Context;
import android.util.Log;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by niufan on 17/5/5.
 */

public class WeChatPay
{
    private static final String TAG = "PayActivity";
    
    public static final String PARTNER_ID = "1464999402";
    
    public static final String APP_ID = "wx5306df2997dc642a";
    
    public static final String PARTNER_KEY = "KcHtafgeCdxqz5OVoPjlON4Cr4eUf8Zw";
    
    private IWXAPI api;
    
    private String mPrepayId;
    
    private String mNoceStr;
    
    public WeChatPay(Context context, String prepayId, String noceStr)
    {
        this.mPrepayId = prepayId;
        this.mNoceStr = noceStr;
        
        api = WXAPIFactory.createWXAPI(context, APP_ID);
        api.registerApp(APP_ID);
    }
    
    public void wechatPay()
    {
        Runnable payRunnable = new Runnable()
        {
            @Override
            public void run()
            {
                //                        Map<String, String> xml = decodeXml(content);
                sendPayReq();
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    
    private void sendPayReq()
    {
        PayReq req = new PayReq();
        
        String appId = this.APP_ID;
        String partnerId = PARTNER_ID;
        //        String prepayId = "wx2017042717522095603ccc990184284326";//result.get("prepay_id");
        String packageValue = "Sign=WXPay";
        //        String nonceStr = genNonceStr();
        //        String nonceStr = "XBW5PxsV7D2BIrrD";//result.get("nonce_str");
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        
        List<Map<String, String>> signParams = new LinkedList<Map<String, String>>();
        
        Map<String, String> appid = new HashMap<String, String>();
        appid.put("appid", appId);
        signParams.add(appid);
        
        Map<String, String> nstr = new HashMap<String, String>();
        appid.put("noncestr", mNoceStr);
        signParams.add(nstr);
        
        Map<String, String> pvalue = new HashMap<String, String>();
        pvalue.put("package", packageValue);
        signParams.add(pvalue);
        
        Map<String, String> pid = new HashMap<String, String>();
        pid.put("partnerid", partnerId);
        signParams.add(pid);
        
        Map<String, String> preId = new HashMap<String, String>();
        preId.put("prepayid", mPrepayId);
        signParams.add(preId);
        
        Map<String, String> tstamp = new HashMap<String, String>();
        tstamp.put("timestamp", timeStamp);
        signParams.add(tstamp);
        
        //        String sign = MD5.getMessageDigest(signParams.toString().getBytes()).toUpperCase();
        
        String sign = genPackageSign(signParams).toUpperCase();
        
        Log.d(TAG, "appId:" + appId);
        Log.d(TAG, "partnerId:" + partnerId);
        Log.d(TAG, "prepayId:" + mPrepayId);
        Log.d(TAG, "packageValue:" + packageValue);
        Log.d(TAG, "nonceStr:" + mNoceStr);
        Log.d(TAG, "timeStamp:" + timeStamp);
        Log.d(TAG, "sign:" + sign);
        
        req.appId = this.APP_ID;//"wx5306df2997dc642a";
        req.partnerId = this.PARTNER_ID;
        req.prepayId = mPrepayId;
        req.packageValue = packageValue;
        req.nonceStr = mNoceStr;
        req.timeStamp = timeStamp;
        req.sign = sign;
        boolean flag = req.checkArgs();
        
        api.sendReq(req);
        Log.d(TAG, "wechat pay flag = " + flag);
        
    }
    
    private String genPackageSign(List<Map<String, String>> params)
    {
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < params.size(); i++)
        {
            for (Map.Entry<String, String> entry : params.get(i).entrySet())
            {
                sb.append(entry.getKey());
                sb.append('=');
                sb.append(entry.getValue());
                sb.append('&');
            }
        }
        
        sb.append("key=");
        sb.append(PARTNER_KEY);
        
        String packageSign = MD5.getMessageDigest(sb.toString().getBytes())
                .toUpperCase();
        Log.e("orion", packageSign);
        return packageSign;
    }
    
}
