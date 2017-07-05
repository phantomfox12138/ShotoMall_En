package com.taihold.shuangdeng.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.taihold.shuangdeng.R;
import com.taihold.shuangdeng.common.FusionAction;
import com.taihold.shuangdeng.freamwork.ui.BasicActivity;
import com.taihold.shuangdeng.logic.login.ILoginLogic;
import com.taihold.shuangdeng.logic.login.LoginLogic;
import com.taihold.shuangdeng.ui.mall.WebActivity;
import com.taihold.shuangdeng.util.StringUtil;

import java.util.Set;

import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_LOGIN_USER_FAILED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_LOGIN_USER_SUCCESSED;

public class WelcomeActivity extends BasicActivity
{
    /**
     * DEBUG_TAG
     */
    private static final String TAG = "WelcomeActivity";
    
    /**
     * 欢迎页跳转按钮
     */
    private LinearLayout mJumpBtn;
    
    private ImageView mWelcomeBgImg;
    
    private LoginLogic mLoginLogic;
    
    private SharedPreferences mSp;
    
    private boolean mPushAliasFlag;
    
    private String mLoginName;
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.welcome_layout);
        
        mSp = getSharedPreferences("ChinaShoto", MODE_PRIVATE);
        
        initView();
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.READ_PHONE_STATE },
                    0);
        }
    }
    
    @Override
    protected void initLogic()
    {
        super.initLogic();
        mLoginLogic = (LoginLogic) getLogicByInterfaceClass(ILoginLogic.class);
    }
    
    private final TagAliasCallback mAliasCallback = new TagAliasCallback()
    {
        @Override
        public void gotResult(int code, String alias, Set<String> tags)
        {
            switch (code)
            {
                case 0:
                    mPushAliasFlag = true;
                    break;
            }
        }
    };
    
    @Override
    protected void handleStateMessage(Message msg)
    {
        super.handleStateMessage(msg);
        
        switch (msg.what)
        {
            case REQUEST_LOGIN_USER_SUCCESSED:
                
                String sid = (String) msg.obj;
                
                mSp.edit().putString("sid", sid).commit();
                
                if (null != getIntent()
                        && !StringUtil.isNullOrEmpty(getIntent().getStringExtra(FusionAction.WEB_ATTR.FROM_WHERE)))
                {
                    if (getIntent().getStringExtra(FusionAction.WEB_ATTR.FROM_WHERE)
                            .equals("push"))
                    {
                        toWeb();
                        return;
                    }
                }
                
                break;
            
            case REQUEST_LOGIN_USER_FAILED:
                
                break;
        }
        
        startActivity(new Intent(FusionAction.HOME_ACTION));
        finish();
    }
    
    private void toWeb()
    {
        
        Intent i = new Intent(FusionAction.WEB_ACTION);
        
        i.putExtra(FusionAction.WEB_ATTR.BUTTON_FLAG, "X");
        i.putExtra(FusionAction.WEB_ATTR.TITLE,
                getString(R.string.profile_order_detail));
        String url = getIntent().getStringExtra(FusionAction.WEB_ATTR.URL);
        i.putExtra(FusionAction.WEB_ATTR.URL, url);
        
        startActivity(i);
        
    }
    
    /**
     * 初始化视图
     */
    private void initView()
    {
        mJumpBtn = (LinearLayout) findViewById(R.id.jump_btn);
        mWelcomeBgImg = (ImageView) findViewById(R.id.welcome_bg);
        
        mJumpBtn.setOnClickListener(new OnClickListener()
        {
            
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(FusionAction.HOME_ACTION));
                
                finish();
            }
        });
        
        mLoginName = mSp.getString("loginname", "");
        final String password = mSp.getString("password", "");
        
        if (!mPushAliasFlag)
        {
            JPushInterface.setAliasAndTags(getApplicationContext(),
                    mLoginName,
                    null,
                    mAliasCallback);
        }
        
        AlphaAnimation alpha = new AlphaAnimation(0f, 1f);
        alpha.setDuration(2800);
        alpha.setFillAfter(true);
        alpha.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {
                
            }
            
            @Override
            public void onAnimationEnd(Animation animation)
            {
                if (!StringUtil.isNullOrEmpty(mLoginName)
                        && !StringUtil.isNullOrEmpty(password))
                {
                    mLoginLogic.userLogin(mLoginName, password);
                }
                else
                {
                    if (null != getIntent()
                            && !StringUtil.isNullOrEmpty(getIntent().getStringExtra(FusionAction.WEB_ATTR.FROM_WHERE)))
                    {
                        if (getIntent().getStringExtra(FusionAction.WEB_ATTR.FROM_WHERE)
                                .equals("push"))
                        {
                            toWeb();
                            return;
                        }
                    }
                    startActivity(new Intent(FusionAction.HOME_ACTION));
                    finish();
                }
            }
            
            @Override
            public void onAnimationRepeat(Animation animation)
            {
                
            }
        });
        
        mWelcomeBgImg.setAnimation(alpha);
        //        
        //        new Thread()
        //        {
        //            
        //            @Override
        //            public void run()
        //            {
        //                try
        //                {
        //                    sleep(3000);
        //                }
        //                catch (InterruptedException e)
        //                {
        //                    e.printStackTrace();
        //                }
        //                
        //                startActivity(new Intent(FusionAction.HOME_ACTION));
        //                
        //                finish();
        //            }
        //        }.start();
    }
}
