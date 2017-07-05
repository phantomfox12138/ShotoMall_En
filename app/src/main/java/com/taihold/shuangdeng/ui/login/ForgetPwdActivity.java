package com.taihold.shuangdeng.ui.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.taihold.shuangdeng.R;
import com.taihold.shuangdeng.freamwork.ui.BasicActivity;
import com.taihold.shuangdeng.logic.login.ILoginLogic;
import com.taihold.shuangdeng.util.StringUtil;
import com.taihold.shuangdeng.util.Utility;

import static com.taihold.shuangdeng.common.FusionAction.FORGET_PWD_DETAIL;
import static com.taihold.shuangdeng.common.FusionAction.REGIST_EXTRA.SMS_VALIDATE_CODE;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_SMS_CODE_HAS_SENDED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_SMS_CODE_HAS_SENDED_ERROR;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_USER_HAS_REGISTED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_VALIIDATE_CODE_CONFIRM_FAILED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_VALIIDATE_CODE_CONFIRM_SUCCESS;

public class ForgetPwdActivity extends BasicActivity
{
    private EditText mUserEdit;
    
    private TextInputLayout mUserTil;
    
    private Button mSmsBtn;
    
    private EditText mSmsEdit;
    
    private TextInputLayout mSmsTil;
    
    private EditText mPwdEdit;
    
    private TextInputLayout mPwdTil;
    
    private Button mNextStepBtn;
    
    private String mSid;
    
    private String mUserName;
    
    private AlertDialog mDialog;
    
    private ILoginLogic mLoginLogic;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        Utility.setTranslucent(this);
        setContentView(R.layout.activity_forget_psd);
        
        initView();
        
    }
    
    private void initView()
    {
        mUserEdit = (EditText) findViewById(R.id.phone_num_edit);
        mUserTil = (TextInputLayout) findViewById(R.id.phone_num_til);
        mSmsEdit = (EditText) findViewById(R.id.sms_confirm_code_edit);
        mSmsTil = (TextInputLayout) findViewById(R.id.sms_confirm_til);
        mPwdEdit = (EditText) findViewById(R.id.forget_password_edit);
        mPwdTil = (TextInputLayout) findViewById(R.id.forget_password_til);
        
        mSmsBtn = (Button) findViewById(R.id.send_sms_code);
        
        mNextStepBtn = (Button) findViewById(R.id.forget_pwd_next);
        
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        
        setSupportActionBar(toolbar);
        setTitle(R.string.login_forget_pwd_title);
        
        MaterialMenuDrawable materialMenu = new MaterialMenuDrawable(this,
                Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
        materialMenu.setTransformationDuration(500);
        materialMenu.setIconState(MaterialMenuDrawable.IconState.ARROW);
        
        mDialog = new AlertDialog.Builder(this).setTitle(R.string.warning)
                .setMessage(R.string.login_sure_to_abondent_get_pwd_back)
                .setIcon(R.mipmap.ic_launcher_round)
                .setPositiveButton(R.string.confirm,
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog,
                                    int which)
                            {
                                finish();
                            }
                        })
                .setNeutralButton(R.string.cancel,
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog,
                                    int which)
                            {
                                dialog.dismiss();
                            }
                        })
                .create();
        
        toolbar.setNavigationIcon(materialMenu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!mDialog.isShowing())
                    mDialog.show();
            }
        });
        
        mSmsBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mUserTil.setErrorEnabled(true);
                
                if (StringUtil.isNullOrEmpty(mUserEdit.getText().toString()))
                {
                    mUserTil.setError(getString(R.string.login_username_is_null));
                    return;
                    
                }
                else if (mUserEdit.getText().toString().length() != 11)
                {
                    mUserTil.setError(getString(R.string.login_phone_num_is_unavailable));
                    return;
                }
                
                mUserTil.setErrorEnabled(false);
                
                String username = mUserEdit.getText().toString();
                mLoginLogic.sendSMSConfrimCode4ForgetPwd(username);
                
                new TimeCount(60000, 1000).start();
                
                showToast(R.string.regist_sms_code_has_sended,
                        Toast.LENGTH_LONG);
            }
        });
        
        mNextStepBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //                mLoginLogic.resetPwd4Forget(mSid, mPwdEdit.getText().toString());
                
                mUserName = mUserEdit.getText().toString();
                mLoginLogic.validateSmsCode(mUserName, mSmsEdit.getText()
                        .toString());
            }
        });
    }
    
    @Override
    protected void initLogic()
    {
        super.initLogic();
        
        mLoginLogic = (ILoginLogic) getLogicByInterfaceClass(ILoginLogic.class);
    }
    
    @Override
    protected void handleStateMessage(Message msg)
    {
        super.handleStateMessage(msg);
        switch (msg.what)
        {
            case REQUEST_VALIIDATE_CODE_CONFIRM_SUCCESS:
                
                mSid = (String) msg.obj;
                
                Intent intent = new Intent(FORGET_PWD_DETAIL);
                intent.putExtra(SMS_VALIDATE_CODE, mSid);
                
                startActivity(intent);
                finish();
                
                break;
            
            case REQUEST_VALIIDATE_CODE_CONFIRM_FAILED:
                
                mSmsTil.setError(getString(R.string.regist_image_confirm_code_error));
                break;
            
            case REQUEST_USER_HAS_REGISTED:
                
                mUserTil.setError(getString(R.string.regist_user_has_registed));
                break;
            case REQUEST_SMS_CODE_HAS_SENDED_ERROR:
                
                showToast(R.string.regist_sms_send_failed, Toast.LENGTH_LONG);
                break;
            case REQUEST_SMS_CODE_HAS_SENDED:
                
                showToast(R.string.regist_sms_code_has_sended,
                        Toast.LENGTH_LONG);
                break;
        }
    }
    
    class TimeCount extends CountDownTimer
    {
        public TimeCount(long millisInFuture, long countDownInterval)
        {
            //参数依次为总时长,和计时的时间间隔
            super(millisInFuture, countDownInterval);
        }
        
        @Override
        public void onFinish()
        {
            //计时完毕时触发  
            mSmsBtn.setText(R.string.regist_send_sms_code);
            mSmsBtn.setEnabled(true);
        }
        
        @Override
        public void onTick(long millisUntilFinished)
        {
            //计时过程显示  
            mSmsBtn.setEnabled(false);
            mSmsBtn.setText(millisUntilFinished / 1000
                    + getResources().getString(R.string.regist_sms_count_down));
        }
    }
    
    @Override
    public void onBackPressed()
    {
        if (!mDialog.isShowing())
            mDialog.show();
    }
}
