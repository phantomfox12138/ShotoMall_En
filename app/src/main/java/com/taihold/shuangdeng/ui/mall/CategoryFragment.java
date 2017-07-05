package com.taihold.shuangdeng.ui.mall;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.taihold.shuangdeng.R;
import com.taihold.shuangdeng.common.FusionAction;
import com.taihold.shuangdeng.util.Utility;

import net.frakbot.jumpingbeans.JumpingBeans;

public class CategoryFragment extends Fragment
{
    
    private View mRootView;
    
    private WebView mWebView;
    
    private TextView mWaitingView;
    
    private ImageView mLoadFailed;
    
    private JumpingBeans mJumpBeanText;
    
    private JumpingBeans.Builder builder;
    
    private View mTitle;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        
        if (null == mRootView)
        {
            mRootView = inflater.inflate(R.layout.fragment_category,
                    container,
                    false);
            initView();
        }
        
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null)
        {
            parent.removeView(mRootView);
        }
        return mRootView;
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
        
        if (null == builder)
        {
            builder = JumpingBeans.with(mWaitingView).appendJumpingDots();
        }
        
        mJumpBeanText = builder.build();
        
    }
    
    @Override
    public void onPause()
    {
        super.onPause();
        
        mJumpBeanText.stopJumping();
    }
    
    private void initView()
    {
        mWebView = (WebView) mRootView.findViewById(R.id.mall_web_view);
        mWaitingView = (TextView) mRootView.findViewById(R.id.waiting_view);
        mLoadFailed = (ImageView) mRootView.findViewById(R.id.load_failed);
        mTitle = mRootView.findViewById(R.id.title);
        
//        mTitle.setVisibility(View.GONE);
        
        mWebView.loadUrl("http://portal.shotomall.com/app/productList.html");
        //        mWebView.loadUrl("http://192.168.1.103/productList.html");
        
        WebSettings wSet = mWebView.getSettings();
        wSet.setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new CategoryFragment.WebMallUtil(
                getActivity()), "WebMallUtil");
        
        mWebView.setWebViewClient(new WebViewClient()
        {
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
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
            
            Utility.clearWebCache(getActivity());
            
            getActivity().startActivity(intent);
        }
        
        @JavascriptInterface
        public String getArray()
        {
            return "productType=0";
        }
        
        //        @JavascriptInterface
        //        public void showToast(String msg)
        //        {
        //            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
        //        }
        
    }
    
}
