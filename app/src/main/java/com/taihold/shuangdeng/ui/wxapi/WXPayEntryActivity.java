package com.taihold.shuangdeng.ui.wxapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.taihold.shuangdeng.R;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.mm.opensdk.utils.Log;

public class WXPayEntryActivity extends AppCompatActivity implements
        IWXAPIEventHandler
{
    
    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
    
    private IWXAPI api;
    
    private static final String APP_ID = "wx5306df2997dc642a";
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay_entry);
        
        api = WXAPIFactory.createWXAPI(this, APP_ID);
        api.handleIntent(getIntent(), this);
    }
    
    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }
    
    @Override
    public void onReq(BaseReq req)
    {
    }
    
    /**
     * 得到支付结果回调
     */
    @Override
    public void onResp(BaseResp resp)
    {
        Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);// 支付结果码
    }
}
