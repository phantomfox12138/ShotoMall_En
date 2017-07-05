package com.taihold.shuangdeng.ui.mall;

import static android.R.attr.permission;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_GET_ALIPAY_ORDER_ID_SUCCESS;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_GET_WECHAT_ORDER_ID_SUCCESS;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_NEWS_DETAIL;

import java.math.BigDecimal;

import org.json.JSONException;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.taihold.shuangdeng.R;
import com.taihold.shuangdeng.common.FusionAction;
import com.taihold.shuangdeng.freamwork.ui.BasicWebActivity;
import com.taihold.shuangdeng.logic.alipay.Alipay;
import com.taihold.shuangdeng.logic.home.HomePageLogic;
import com.taihold.shuangdeng.logic.home.IHomePageLogic;
import com.taihold.shuangdeng.logic.model.PayModel;
import com.taihold.shuangdeng.logic.unionpay.UnionPay;
import com.taihold.shuangdeng.logic.wechatpay.WeChatPay;
import com.taihold.shuangdeng.util.PreferenceUtils;
import com.taihold.shuangdeng.util.StringUtil;
import com.taihold.shuangdeng.util.Utility;

import net.frakbot.jumpingbeans.JumpingBeans;

import io.card.payment.CardIOActivity;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

public class WebActivity extends BasicWebActivity
{
    private static final String TAG = "WebActivity";
    
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_PRODUCTION;
    
    private static final String CONFIG_CLIENT_ID = "AYTvlcBKXCYJ827iE6Xa693GUasv4FMRTyxqzr3hg0fd_IlkahRrSIBoksqPz5fERojRhBISjEi3Xclq";
    
    private static final int REQUEST_CODE_PAYMENT = 123;
    
    private static final int REQUEST_CODE_WEB_REQUEST = 456;
    
    private WebView mWebView;
    
    private Toolbar mToolBar;
    
    private MaterialMenuDrawable mMaterialMenu;
    
    private SharedPreferences mSp;
    
    private HomePageLogic mHomeLogic;
    
    private SwipeBackLayout mSwipeBackLayout;
    
    private String mKeyTrackingMode;
    
    private UnionPay mUnionPay;
    
    private ImageView mLoadFailed;
    
    private TextView mWaitingView;
    
    private JumpingBeans mJumpBeanText;
    
    private JumpingBeans.Builder builder;
    
    private static PayPalConfiguration config = new PayPalConfiguration().environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Example Merchant")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.paypal.com/webapps/mpp/ua/privacy-full"))
            .merchantUserAgreementUri(Uri.parse("https://www.paypal.com/webapps/mpp/ua/useragreement-full"))
            .languageOrLocale("en");
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        
        mSwipeBackLayout = getSwipeBackLayout();
        
        mKeyTrackingMode = getString(R.string.key_tracking_mode);
        
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        saveTrackingMode(SwipeBackLayout.EDGE_LEFT);
        
        //启动paypal服务
        Intent intent = new Intent(WebActivity.this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
        
        initView(getIntent().getStringExtra(FusionAction.WEB_ATTR.URL),
                getIntent().getStringExtra(FusionAction.WEB_ATTR.BUTTON_FLAG),
                getIntent().getStringExtra(FusionAction.WEB_ATTR.TITLE));
    }
    
    private void initView(String url, String buttonFlag, String title)
    {
        mWebView = (WebView) findViewById(R.id.web_view);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mWaitingView = (TextView) findViewById(R.id.waiting_view);
        mLoadFailed = (ImageView) findViewById(R.id.load_failed);
        
        String requestUrl = getIntent().getStringExtra(FusionAction.WEB_ATTR.REQEUST);
        
        if (!StringUtil.isNullOrEmpty(requestUrl))
        {
            mHomeLogic.getNewsDetail(requestUrl);
        }
        
        mSp = getSharedPreferences("ChinaShoto", MODE_PRIVATE);
        
        setSupportActionBar(mToolBar);
        
        mMaterialMenu = new MaterialMenuDrawable(this, Color.WHITE,
                MaterialMenuDrawable.Stroke.THIN);
        
        mMaterialMenu.setTransformationDuration(500);
        mMaterialMenu.animateIconState(buttonFlag.equals("X") ? MaterialMenuDrawable.IconState.X
                : MaterialMenuDrawable.IconState.ARROW);
        
        setTitle(title);
        mToolBar.setNavigationIcon(mMaterialMenu);
        
        mToolBar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mMaterialMenu.getIconState() == MaterialMenuDrawable.IconState.ARROW)
                {
                    if (mWebView.canGoBack())
                    {
                        mWebView.goBack();
                    }
                    else
                    {
                        finish();
                    }
                }
                else if (mMaterialMenu.getIconState() == MaterialMenuDrawable.IconState.X)
                {
                    finish();
                }
            }
        });
        
        mWebView.addJavascriptInterface(new WebMallUtil(this), "WebMallUtil");
        
        mWebView.loadUrl(url);
        mWebView.setVisibility(View.VISIBLE);
        
        WebSettings wSet = mWebView.getSettings();
        wSet.setJavaScriptEnabled(true);
        
        mWebView.setWebViewClient(new WebViewClient()
        {
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
            
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                super.onPageStarted(view, url, favicon);
                
                mLoadFailed.setVisibility(View.GONE);
                mWaitingView.setVisibility(View.VISIBLE);
            }
            
            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);
                
                mJumpBeanText.stopJumping();
                mWaitingView.setVisibility(View.GONE);
            }
            
            @Override
            public void onReceivedError(WebView view,
                    WebResourceRequest request, WebResourceError error)
            {
                super.onReceivedError(view, request, error);
                mLoadFailed.setVisibility(View.VISIBLE);
            }
        });
        
        mLoadFailed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mWebView.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mWebView.reload();
                    }
                });
            }
        });
        
    }
    
    private void saveTrackingMode(int flag)
    {
        PreferenceUtils.setPrefInt(getApplicationContext(),
                mKeyTrackingMode,
                flag);
    }
    
    @Override
    protected void initLogic()
    {
        super.initLogic();
        mHomeLogic = (HomePageLogic) getLogicByInterfaceClass(IHomePageLogic.class);
    }
    
    @Override
    protected void handleStateMessage(Message msg)
    {
        super.handleStateMessage(msg);
        
        switch (msg.what)
        {
            case REQUEST_NEWS_DETAIL:
                String newsDetail = (String) msg.obj;
                Log.d(TAG, "news detail = " + newsDetail);
                
                mWebView.loadData(newsDetail, "text/html; charset=UTF-8", null);
                break;
            case REQUEST_GET_WECHAT_ORDER_ID_SUCCESS:
                
                PayModel model = (PayModel) msg.obj;
                
                WeChatPay weChatPay = new WeChatPay(this, model.getPrepayId(),
                        model.getNonceStr());
                weChatPay.wechatPay();
                break;
            
            case REQUEST_GET_ALIPAY_ORDER_ID_SUCCESS:
                
                String orderInfo = (String) msg.obj;
                
                Alipay alipay = new Alipay(this);
                alipay.payV2(orderInfo);
                
                break;
        }
    }
    
    @Override
    protected void onPause()
    {
        super.onPause();
        mJumpBeanText.stopJumping();
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        
        mWebView.post(new Runnable()
        {
            @Override
            public void run()
            {
                mWebView.reload();
            }
        });
        if (null == builder)
        {
            builder = JumpingBeans.with(mWaitingView).appendJumpingDots();
        }
        
        mJumpBeanText = builder.build();
        
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack())
        {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
    class WebMallUtil
    {
        private Context context;
        
        public WebMallUtil(Context context)
        {
            this.context = context;
        }
        
        @JavascriptInterface
        public void reloadUrl()
        {
            mWebView.post(new Runnable()
            {
                @Override
                public void run()
                {
                    mWebView.reload();
                }
            });
            
        }
        
        @JavascriptInterface
        public void loadUrl(final String url)
        {
            mWebView.post(new Runnable()
            {
                @Override
                public void run()
                {
                    mWebView.loadUrl(url);
                }
            });
            
        }
        
        @JavascriptInterface
        public void openNewActivity(String url, String buttonFlag,
                String title, String value)
        {
            Intent intent = new Intent(FusionAction.WEB_ACTION);
            
            intent.putExtra(FusionAction.WEB_ATTR.URL, url);
            intent.putExtra(FusionAction.WEB_ATTR.BUTTON_FLAG, buttonFlag);
            intent.putExtra(FusionAction.WEB_ATTR.TITLE, title);
            intent.putExtra(FusionAction.WEB_ATTR.VALUE, value);
            
            Utility.clearWebCache(WebActivity.this);
            
            context.startActivity(intent);
        }
        
        @JavascriptInterface
        public String getArray()
        {
            String result = getIntent().getStringExtra(FusionAction.WEB_ATTR.VALUE);
            return result.toString();
        }
        
        @JavascriptInterface
        public void toLogin()
        {
            startActivity(new Intent(FusionAction.LOGIN_ACTION));
        }
        
        @JavascriptInterface
        public void saveToken(String token)
        {
            mSp.edit().putString("sid", token).commit();
        }
        
        @JavascriptInterface
        public String getToken()
        {
            String sid = mSp.getString("sid", null);
            return sid;
        }
        
        @JavascriptInterface
        public void setData(String key, String value)
        {
            mSp.edit().putString(key, value).commit();
        }
        
        @JavascriptInterface
        public String getData(String key, String value)
        {
            return mSp.getString(key, "");
        }
        
        @JavascriptInterface
        public void setTitle(String title)
        {
            WebActivity.this.setTitle(title);
        }
        
        @JavascriptInterface
        public void setTitleBtnArrow()
        {
            mMaterialMenu.animateIconState(MaterialMenuDrawable.IconState.ARROW);
        }
        
        @JavascriptInterface
        public void setTitleBtnX()
        {
            mMaterialMenu.animateIconState(MaterialMenuDrawable.IconState.X);
        }
        
        @JavascriptInterface
        public void finishClose()
        {
            finish();
        }
        
        @JavascriptInterface
        public void setWeChatOrderId(String orderId)
        {
            mHomeLogic.wechatPay(orderId);
        }
        
        @JavascriptInterface
        public void setAlipayOrderId(String orderId)
        {
            mHomeLogic.alipayPayout(orderId);
        }
        
        @JavascriptInterface
        public void setPaypalInfo(String price, String goodsName, String orderId)
        {
            
            PayPalPayment thingToBuy = getThingToBuy(price,
                    goodsName,
                    orderId,
                    PayPalPayment.PAYMENT_INTENT_SALE);
            
            Intent toPayment = new Intent(WebActivity.this,
                    PaymentActivity.class);
            
            toPayment.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            
            toPayment.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
            
            startActivityForResult(toPayment, REQUEST_CODE_PAYMENT);
        }
        
        @JavascriptInterface
        public void toUnionPay(String orderId)
        {
            mUnionPay = new UnionPay(WebActivity.this);
            mUnionPay.startPay(orderId);
        }
        
        //        @JavascriptInterface
        //        public void showToast(String msg)
        //        {
        //            Toast.makeText(WebActivity.this, msg, Toast.LENGTH_LONG).show();
        //        }
    }
    
    private PayPalPayment getThingToBuy(String price, String goodsName,
            String orderId, String paymentIntent)
    {
        PayPalPayment payment = new PayPalPayment(new BigDecimal(price), "USD",
                goodsName, paymentIntent);
        
        payment.custom(orderId);
        
        return payment;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PAYMENT)
        {
            if (resultCode == RESULT_OK)
            {
                //当paypal支付成功
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null)
                {
                    try
                    {
                        Log.i(TAG, confirm.toJSONObject().toString(4));
                        Log.i(TAG, confirm.getPayment()
                                .toJSONObject()
                                .toString(4));
                        /**
                         * TODO: send 'confirm' (and possibly
                         * confirm.getPayment() to your server for verification
                         * // TODO: 发送支付ID到你的服务器进行验证 or consent completion. See
                         * https://developer.paypal.com/webapps/developer/docs/
                         * integration/mobile/verify-mobile-payment/ for more
                         * details.
                         *
                         * For sample mobile backend interactions, see
                         * https://github
                         * .com/paypal/rest-api-sdk-python/tree/master
                         * /samples/mobile_backend
                         */
                        
                        showToast("PaymentConfirmation info received from PayPal",
                                Toast.LENGTH_LONG);
                        
                        mWebView.goBack();
                        /**
                         * /*PayPal返回 { "response": { "state": "approved", "id":
                         * "PAY-3S9908362R7615747KP4FQAA", "create_time":
                         * "2014-08-23T08:59:44Z", "intent": "sale" }, "client":
                         * { "platform": "Android", "paypal_sdk_version":
                         * "2.2.2", "product_name": "PayPal-Android-SDK",
                         * "environment": "sandbox" }, "response_type":
                         * "payment" }
                         */
                        
                    }
                    catch (JSONException e)
                    {
                        Log.e(TAG,
                                "an extremely unlikely failure occurred: ",
                                e);
                    }
                }
                
                if (resultCode == Activity.RESULT_CANCELED)
                {
                    Log.i(TAG, "The user canceled.");
                    showToast("User canceled.", Toast.LENGTH_LONG);
                }
            }
        }
        else
        //if (requestCode == REQUEST_CODE_WEB_REQUEST)
        {
            if (resultCode == RESULT_OK)
            {
                mUnionPay.processPayResult(data);
                mWebView.goBack();
            }
        }
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        
        Log.d(TAG, "Destory");
    }
}
