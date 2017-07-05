package com.taihold.shuangdeng.ui.login;

import static com.taihold.shuangdeng.common.FusionAction.IMAGE_VERIFY;
import static com.taihold.shuangdeng.common.FusionAction.IMAGE_VERIFY_EXTRA.IMAGE_VERIFY_CODE;
import static com.taihold.shuangdeng.common.FusionAction.REGIST_EXTRA.SMS_VALIDATE_CODE;
import static com.taihold.shuangdeng.common.FusionAction.REGIST_EXTRA.USER_MOBILE;
import static com.taihold.shuangdeng.common.FusionAction.REGIST_EXTRA.USER_TYPE;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_SMS_CODE_HAS_SENDED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_SMS_CODE_HAS_SENDED_ERROR;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_USER_HAS_REGISTED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_VALIIDATE_CODE_CONFIRM_FAILED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_VALIIDATE_CODE_CONFIRM_SUCCESS;
import static com.taihold.shuangdeng.component.db.URIField.USERNAME;
import static com.taihold.shuangdeng.component.db.URIField.VERIFYCODE;

import java.util.Map;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class RegistActivity extends BasicActivity
{
    private static final String TAG = "RegistActivity";
    
    private Toolbar mToolBar;
    
    private MaterialMenuDrawable mMaterialMenu;
    
    private EditText mUserEdit;
    
    private TextInputLayout mUserTil;
    
    private Button mSmsBtn;
    
    private EditText mSmsEdit;
    
    private TextInputLayout mSmsTil;
    
    private Button mRegistNextBtn;
    
    private ILoginLogic mLoginLogic;
    
    private String mSid;
    
    public static Map<String, Long> map;
    
    private String mUserName;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        
        //        initDialogActivity();
        
        initView(savedInstanceState);
    }
    
    @Override
    protected void initLogic()
    {
        super.initLogic();
        mLoginLogic = (ILoginLogic) getLogicByInterfaceClass(ILoginLogic.class);
    }
    
    private void initView(Bundle savedInstanceState)
    {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mRegistNextBtn = (Button) findViewById(R.id.regist_next);
        mUserEdit = (EditText) findViewById(R.id.phone_num_edit);
        mSmsBtn = (Button) findViewById(R.id.send_sms_code);
        mSmsEdit = (EditText) findViewById(R.id.sms_confirm_code_edit);
        
        mUserTil = (TextInputLayout) findViewById(R.id.phone_num_til);
        mSmsTil = (TextInputLayout) findViewById(R.id.sms_confirm_til);
        
        registerForContextMenu(mRegistNextBtn);
        
        setSupportActionBar(mToolBar);
        
        mSmsTil.setErrorEnabled(true);
        
        mMaterialMenu = new MaterialMenuDrawable(this, Color.WHITE,
                MaterialMenuDrawable.Stroke.THIN);
        mMaterialMenu.setTransformationDuration(500);
        mMaterialMenu.setIconState(MaterialMenuDrawable.IconState.ARROW);
        
        mToolBar.setNavigationIcon(mMaterialMenu);
        setTitle(R.string.regist_confirm_phone_num);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        
        mSmsBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mUserTil.setErrorEnabled(true);
                //                mImgConfirmTil.setErrorEnabled(true);
                
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
                //                mImgConfirmTil.setErrorEnabled(false);
                
                String username = mUserEdit.getText().toString();
                
                Intent intent = new Intent(IMAGE_VERIFY);
                intent.putExtra(USERNAME, username);
                
                startActivityForResult(intent, IMAGE_VERIFY_CODE);
                
            }
        });
        
        mRegistNextBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                
                mUserTil.setErrorEnabled(true);
                //                mImgConfirmTil.setErrorEnabled(true);
                
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
                //                mImgConfirmTil.setErrorEnabled(false);
                
                mUserName = mUserEdit.getText().toString();
                mLoginLogic.validateSmsCode(mUserName, mSmsEdit.getText()
                        .toString());
                
                //                mRegistNextBtn.showContextMenu();
                //                mSmsTil.setError(getString(R.string.regist_image_confirm_code_error));
            }
        });
        
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        
        if (v.getId() == R.id.regist_next)
        {
            MenuInflater flater = getMenuInflater();
            flater.inflate(R.menu.main_menu, menu);
            menu.setHeaderTitle(getString(R.string.regist_select_type))
                    .setHeaderIcon(R.mipmap.ic_launcher);
        }
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.regist_user:
                
                Intent registPersonal = new Intent(
                        FusionAction.REGIST_DETAIL_ACTION);
                registPersonal.putExtra(USER_TYPE, "personal");
                registPersonal.putExtra(USER_MOBILE, mUserEdit.getText()
                        .toString());
                registPersonal.putExtra(SMS_VALIDATE_CODE, mSid);
                
                Log.d(TAG, "sid = " + mSid);
                
                startActivity(registPersonal);
                
                break;
            
            case R.id.regist_company:
                
                Intent registCompany = new Intent(
                        FusionAction.REGIST_DETAIL_ACTION);
                registCompany.putExtra(USER_TYPE, "company");
                
                registCompany.putExtra(USER_MOBILE, mUserName);
                registCompany.putExtra(SMS_VALIDATE_CODE, mSid);
                
                startActivity(registCompany);
                
                break;
            
            default:
                super.onContextItemSelected(item);
        }
        
        finish();
        return super.onContextItemSelected(item);
    }
    
    @Override
    protected void handleStateMessage(Message msg)
    {
        super.handleStateMessage(msg);
        
        switch (msg.what)
        {
            case REQUEST_VALIIDATE_CODE_CONFIRM_SUCCESS:
                
                mSid = (String) msg.obj;
                
                mRegistNextBtn.showContextMenu();
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
                
                new TimeCount(60000, 1000).start();
                
                showToast(R.string.regist_sms_code_has_sended,
                        Toast.LENGTH_LONG);
                break;
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case IMAGE_VERIFY_CODE:
                    
                    String username = mUserEdit.getText().toString();
                    String verifyCode = data.getStringExtra(VERIFYCODE);
                    mLoginLogic.getSMSConfirmCode(username, verifyCode);
                    
                    break;
            }
        }
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        
        unregisterForContextMenu(mRegistNextBtn);
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
}
