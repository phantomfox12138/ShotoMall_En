package com.taihold.shuangdeng.ui.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.taihold.shuangdeng.R;
import com.taihold.shuangdeng.common.FusionAction;
import com.taihold.shuangdeng.freamwork.ui.BasicActivity;
import com.taihold.shuangdeng.logic.login.ILoginLogic;
import com.taihold.shuangdeng.util.StringUtil;

import static com.taihold.shuangdeng.common.FusionAction.REGIST_EXTRA.SMS_VALIDATE_CODE;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_LOGIN_FORGET_PWD_RESET_FAILED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_LOGIN_FORGET_PWD_RESET_SUCCESSED;

public class ForgetPwdDetailActivity extends BasicActivity
{
    
    private EditText mPwdEdit;
    
    private TextInputLayout mPwdTil;
    
    private EditText mPwdConfirmEdit;
    
    private TextInputLayout mPwdConfirmTil;
    
    private Button mConfirmBtn;
    
    private AlertDialog mDialog;
    
    private ILoginLogic mLoginLogic;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd_detail);
        
        initView();
        
    }
    
    @Override
    protected void initLogic()
    {
        super.initLogic();
        mLoginLogic = (ILoginLogic) getLogicByInterfaceClass(ILoginLogic.class);
    }
    
    private void initView()
    {
        final String sid = getIntent().getStringExtra(SMS_VALIDATE_CODE);
        
        mPwdEdit = (EditText) findViewById(R.id.forget_password_edit);
        mPwdTil = (TextInputLayout) findViewById(R.id.forget_password_til);
        mPwdConfirmEdit = (EditText) findViewById(R.id.confirm_password_edit);
        mPwdConfirmTil = (TextInputLayout) findViewById(R.id.confirm_password_til);
        
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
        
        mPwdTil.setErrorEnabled(true);
        mPwdConfirmTil.setErrorEnabled(true);
        
        mConfirmBtn = (Button) findViewById(R.id.forget_pwd_confirm);
        mConfirmBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (StringUtil.isNullOrEmpty(mPwdEdit.getText().toString()))
                {
                    mPwdTil.setError(getString(R.string.login_password_is_null));
                    
                    return;
                }
                
                if (StringUtil.isNullOrEmpty(mPwdConfirmEdit.getText()
                        .toString())
                        || mPwdConfirmEdit.getText().equals(mPwdEdit.getText()
                                .toString()))
                {
                    mPwdConfirmTil.setError(getString(R.string.regist_pwd_is_different));
                    return;
                }
                
                mLoginLogic.resetPwd4Forget(sid, mPwdConfirmEdit.getText()
                        .toString());
            }
        });
    }
    
    @Override
    protected void handleStateMessage(Message msg)
    {
        super.handleStateMessage(msg);
        
        switch (msg.what)
        {
            case REQUEST_LOGIN_FORGET_PWD_RESET_SUCCESSED:
                
                showToast(R.string.login_forget_pwd_reset_successed,
                        Toast.LENGTH_LONG);
                
                finish();
                
                break;
            case REQUEST_LOGIN_FORGET_PWD_RESET_FAILED:
                
                showToast(R.string.login_forget_pwd_reset_failed,
                        Toast.LENGTH_LONG);
                break;
        }
        
    }
    
    @Override
    public void onBackPressed()
    {
        if (!mDialog.isShowing())
            mDialog.show();
        
    }
}
