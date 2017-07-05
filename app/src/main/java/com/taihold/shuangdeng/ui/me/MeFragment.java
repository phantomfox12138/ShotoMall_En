package com.taihold.shuangdeng.ui.me;

import net.frakbot.jumpingbeans.JumpingBeans;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.taihold.shuangdeng.R;
import com.taihold.shuangdeng.common.FusionAction;
import com.taihold.shuangdeng.ui.home.HomePageActivity;
import com.taihold.shuangdeng.util.StringUtil;
import com.taihold.shuangdeng.util.Utility;

public class MeFragment extends Fragment
{
    
    private static final String TAG = "MeFragment";
    
    private View mRootView;
    
    private WebView mWebView;
    
    private TextView mWaitingView;
    
    private JumpingBeans mJumpBeanText;
    
    private ImageView mLoadFailed;
    
    private SharedPreferences mSp;
    
    private JumpingBeans.Builder builder;
    
    private ImageView mTitleIcon;
    
    private TextView mTitle;
    
    private EditText mSearchEdit;
    
    private ImageView mSearchBtn;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        if (null == mRootView)
        {
            mRootView = inflater.inflate(R.layout.fragment_web_mall,
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
        
        if (!StringUtil.isNullOrEmpty(mSp.getString("loginname", null))
                && !StringUtil.isNullOrEmpty(mSp.getString("password", null)))
        {
            reload();
        }
        
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
        
        mTitle = (TextView) mRootView.findViewById(R.id.title);
        mTitleIcon = (ImageView) mRootView.findViewById(R.id.icon);
        mSearchEdit = (EditText) mRootView.findViewById(R.id.search_edit);
        mSearchBtn = (ImageView) mRootView.findViewById(R.id.search_btn);
        View searchContent = mRootView.findViewById(R.id.search_content);
//        View titleIcon = mRootView.findViewById(R.id.title_icon_container);
        
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText(R.string.home_tab_me);
        searchContent.setVisibility(View.GONE);
//        titleIcon.setVisibility(View.GONE);
        
        mTitleIcon.setVisibility(View.GONE);
        mSearchEdit.setVisibility(View.GONE);
        mSearchBtn.setVisibility(View.GONE);
        
        mWebView.loadUrl("http://portal.shotomall.com/app/personaInfo.html");
        WebSettings wSet = mWebView.getSettings();
        wSet.setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new WebMallUtil(getActivity()),
                "WebMallUtil");
        mSp = getActivity().getSharedPreferences("ChinaShoto",
                Context.MODE_PRIVATE);
        
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
            
            context.startActivity(intent);
        }
        
        @JavascriptInterface
        public void toLogin()
        {
            Intent toLogin = new Intent(FusionAction.LOGIN_ACTION);
            
            startActivity(toLogin);
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
        public void logout()
        {
            String sid = mSp.getString("sid", null);
            HomePageActivity.mLoginLogic.logout(sid);
        }
        
    }
    
    public void closeJsDialog()
    {
        mWebView.loadUrl("javascript:layerclose()");
    }
    
    public void reload()
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
    
}
