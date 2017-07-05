package com.taihold.shuangdeng.ui.login;

import static com.taihold.shuangdeng.common.FusionAction.FORGET_PASSWORD_ACTION;
import static com.taihold.shuangdeng.common.FusionAction.REGIST_ACTION;
import static com.taihold.shuangdeng.common.FusionAction.REGIST_EXTRA.USER_MOBILE;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_LOGIN_USER_FAILED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_LOGIN_USER_SUCCESSED;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.taihold.shuangdeng.R;
import com.taihold.shuangdeng.freamwork.ui.BasicActivity;
import com.taihold.shuangdeng.logic.login.ILoginLogic;
import com.taihold.shuangdeng.logic.login.LoginLogic;
import com.taihold.shuangdeng.util.StringUtil;

public class LoginActivity extends BasicActivity implements
        View.OnClickListener
{
    
    private TextView mForgetPsd;
    
    private View mFastReg;
    
    private Button mLoginBtn;
    
    private EditText mUsernameEdit;
    
    private EditText mPasswordEdit;
    
    private TextInputLayout mUserTil;
    
    private TextInputLayout mPasswordTil;
    
    private CheckBox mPwdSwitcher;
    
    private LoginLogic mLoginlogic;
    
    private SharedPreferences mSp;
    
    private ProgressDialog mPDialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_login);
        
        //        initDialogActivity();
        
        initView();
        
    }
    
    @Override
    protected void initLogic()
    {
        super.initLogic();
        
        mLoginlogic = (LoginLogic) getLogicByInterfaceClass(ILoginLogic.class);
    }
    
    private void initView()
    {
        mForgetPsd = (TextView) findViewById(R.id.forget_password);
        mFastReg = findViewById(R.id.fast_regist);
        mLoginBtn = (Button) findViewById(R.id.login_btn);
        mPwdSwitcher = (CheckBox) findViewById(R.id.password_switcher);
        
        mSp = getSharedPreferences("ChinaShoto", MODE_PRIVATE);
        
        mPwdSwitcher.setChecked(false);
        
        mUsernameEdit = (EditText) findViewById(R.id.username_edit);
        mPasswordEdit = (EditText) findViewById(R.id.password_edit);
        
        mUserTil = (TextInputLayout) findViewById(R.id.username_til);
        mPasswordTil = (TextInputLayout) findViewById(R.id.password_til);
        
        mPDialog = new ProgressDialog(this);
        mPDialog.setTitle(R.string.app_name);
        mPDialog.setMessage(getString(R.string.please_wait));
        mPDialog.setCancelable(false);
        
        mUserTil.setErrorEnabled(true);
        mPasswordTil.setErrorEnabled(true);
        
        mFastReg.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);
        mForgetPsd.setOnClickListener(this);
        
        mPwdSwitcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked)
            {
                if (isChecked)
                {
                    mPasswordEdit.setTransformationMethod(new SingleLineTransformationMethod());
                }
                else
                {
                    mPasswordEdit.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });
        
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.forget_password:
                
                Intent intent = new Intent(FORGET_PASSWORD_ACTION);
                intent.putExtra(USER_MOBILE, mUsernameEdit.getText().toString());
                startActivity(intent);
                
                //                overridePendingTransition(R.anim.push_left_in,
                //                        R.anim.push_left_out);
                
                break;
            
            case R.id.fast_regist:
                
                startActivity(new Intent(REGIST_ACTION));
                
                break;
            
            case R.id.login_btn:
                
                if (StringUtil.isNullOrEmpty(mUsernameEdit.getText().toString()))
                {
                    mUserTil.setError(getString(R.string.login_username_is_null));
                    return;
                }
                else if (mUsernameEdit.getText().toString().length() != 11)
                {
                    mUserTil.setError(getString(R.string.login_phone_num_is_unavailable));
                    
                    return;
                }
                else
                {
                    mUserTil.setErrorEnabled(false);
                    
                }
                
                if (StringUtil.isNullOrEmpty(mPasswordEdit.getText().toString()))
                {
                    mPasswordTil.setError(getString(R.string.login_password_is_null));
                    return;
                }
                else
                {
                    mPasswordTil.setErrorEnabled(false);
                }
                
                mLoginlogic.userLogin(mUsernameEdit.getText().toString(),
                        mPasswordEdit.getText().toString());
                mPDialog.show();
                break;
            
            default:
                break;
        }
    }
    
    @Override
    protected void handleStateMessage(Message msg)
    {
        super.handleStateMessage(msg);
        
        switch (msg.what)
        {
            case REQUEST_LOGIN_USER_SUCCESSED:
                
                String sid = (String) msg.obj;
                
                showToast(R.string.login_successed, Toast.LENGTH_LONG);
                mSp.edit()
                        .putString("sid", sid)
                        .putString("loginname",
                                mUsernameEdit.getText().toString())
                        .putString("password",
                                mPasswordEdit.getText().toString())
                        .commit();
                
                mPDialog.dismiss();
                finish();
                break;
            
            case REQUEST_LOGIN_USER_FAILED:
                mPDialog.dismiss();
                showToast(R.string.login_failed, Toast.LENGTH_LONG);
                break;
        }
    }
    
}
