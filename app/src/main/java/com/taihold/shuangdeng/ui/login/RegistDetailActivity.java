package com.taihold.shuangdeng.ui.login;

import static com.taihold.shuangdeng.common.FusionAction.REGIST_EXTRA.IMAGE_LOADER_COUNT_EXTRA;
import static com.taihold.shuangdeng.common.FusionAction.REGIST_EXTRA.IMAGE_RESULT_LIST;
import static com.taihold.shuangdeng.common.FusionAction.REGIST_EXTRA.REQUEST_ADD_IMAGE_CODE;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_IMAGE_NOT_FOUND;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_REGIST_COMPANY_FAILED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_REGIST_COMPANY_SUCCESSED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_REGIST_IMAGE_UPLOAD_FAILED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_REGIST_IMAGE_UPLOAD_SUCCESSED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_REGIST_USER_FAILED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_REGIST_USER_SUCCESSED;

import java.util.List;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.taihold.shuangdeng.R;
import com.taihold.shuangdeng.common.FusionAction;
import com.taihold.shuangdeng.freamwork.ui.BasicActivity;
import com.taihold.shuangdeng.logic.login.ILoginLogic;
import com.taihold.shuangdeng.util.StringUtil;
import com.taihold.shuangdeng.util.Utility;

public class RegistDetailActivity extends BasicActivity
{
    
    private static final String TAG = "RegistDetailActivity";
    
    private Toolbar mToolBar;
    
    private MaterialMenuDrawable mMaterialMenu;
    
    private View mPersonalLayout;
    
    private View mCompanyLayout;
    
    private EditText mPwdEdit;
    
    private TextInputLayout mPwdTil;
    
    private EditText mConfirmPwdEdit;
    
    private TextInputLayout mConfirmPwdTil;
    
    private EditText mEmailEdit;
    
    private TextInputLayout mEmailTil;
    
    private EditText mNickNameEdit;
    
    private Button mRegistConfirm;
    
    private ILoginLogic mLoginLogic;
    
    private EditText mCompanyEdit;
    
    private TextInputLayout mCompanyTil;
    
    private AlertDialog mDialog;
    
    private ImageView mCorpLicenseImg;
    
    private String userType;
    
    private String userMobile;
    
    private String validateSid;
    
    private ProgressDialog mProgressDialog;
    
    private String mLicensePath;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_detail);
        
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
        
        userType = getIntent().getStringExtra(FusionAction.REGIST_EXTRA.USER_TYPE);
        userMobile = getIntent().getStringExtra(FusionAction.REGIST_EXTRA.USER_MOBILE);
        validateSid = getIntent().getStringExtra(FusionAction.REGIST_EXTRA.SMS_VALIDATE_CODE);
        
        Log.d(TAG, "userMobile = " + userMobile);
        Log.d(TAG, "validateSid = " + validateSid);
        
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mPersonalLayout = findViewById(R.id.regist_user_layout);
        mCompanyLayout = findViewById(R.id.regist_compay_layout);
        
        if ("personal".equals(userType))
        {
            
            //mPersonalLayout(normal)
            mPwdEdit = (EditText) findViewById(R.id.password_edit);
            mPwdTil = (TextInputLayout) findViewById(R.id.password_til);
            mConfirmPwdEdit = (EditText) findViewById(R.id.confirm_password_edit);
            mConfirmPwdTil = (TextInputLayout) findViewById(R.id.confirm_password_til);
            
            //mPersonalLayout(email) mCompanyLayout(corp_email)
            mEmailEdit = (EditText) findViewById(R.id.email_edit);
            mEmailTil = (TextInputLayout) findViewById(R.id.email_til);
            mNickNameEdit = (EditText) findViewById(R.id.nick_name_edit);
            
            //mCompanyLayout(special)
            mCompanyEdit = (EditText) findViewById(R.id.company_name_edit);
            mCompanyTil = (TextInputLayout) findViewById(R.id.company_name_til);
            mCorpLicenseImg = (ImageView) findViewById(R.id.corp_image);
        }
        else
        {
            initCompanyView();
        }
        
        mCorpLicenseImg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent pickPhoto = new Intent(FusionAction.IMAGELOADER_ACTION);
                pickPhoto.putExtra(IMAGE_LOADER_COUNT_EXTRA, 1);
                
                startActivityForResult(pickPhoto, REQUEST_ADD_IMAGE_CODE);
            }
        });
        
        mDialog = new AlertDialog.Builder(this).setTitle(R.string.warning)
                .setMessage(R.string.regist_confirm_to_abandon)
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
        
        mPwdTil.setErrorEnabled(true);
        mConfirmPwdTil.setErrorEnabled(true);
        mEmailTil.setErrorEnabled(true);
        
        if ("personal".equals(userType))
        {
            mRegistConfirm = (Button) findViewById(R.id.regist_confirm);
        }
        else
        {
            mRegistConfirm = (Button) findViewById(R.id.regist_company_confirm);
        }
        
        mRegistConfirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (StringUtil.isNullOrEmpty(mPwdEdit.getText().toString()))
                {
                    mPwdTil.setError(getString(R.string.login_password_is_null));
                }
                
                if (StringUtil.isNullOrEmpty(mConfirmPwdEdit.getText()
                        .toString())
                        || mConfirmPwdEdit.getText().equals(mPwdEdit.getText()
                                .toString()))
                {
                    mConfirmPwdTil.setError(getString(R.string.regist_pwd_is_different));
                }
                
                if (!StringUtil.isEmail(mEmailEdit.getText().toString()))
                {
                    mEmailTil.setError(getString(R.string.regist_email_is_illegal));
                    
                    return;
                }
                
                if ("personal".equals(userType))
                {
                    mLoginLogic.userRegist(validateSid,
                            userMobile,
                            mEmailEdit.getText().toString(),
                            mNickNameEdit.getText().toString(),
                            mPwdEdit.getText().toString());
                }
                else
                {
                    
                    mLoginLogic.companyRegist(validateSid,
                            userMobile,
                            mEmailEdit.getText().toString(),
                            mNickNameEdit.getText().toString(),
                            mCompanyEdit.getText().toString(),
                            mPwdEdit.getText().toString(),
                            mLicensePath);
                    
                }
                
            }
        });
        
        setSupportActionBar(mToolBar);
        
        mMaterialMenu = new MaterialMenuDrawable(this, Color.WHITE,
                MaterialMenuDrawable.Stroke.THIN);
        mMaterialMenu.setTransformationDuration(500);
        mMaterialMenu.setIconState(MaterialMenuDrawable.IconState.ARROW);
        
        mToolBar.setNavigationIcon(mMaterialMenu);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!mDialog.isShowing())
                    mDialog.show();
            }
        });
        
        if ("personal".equals(userType))
        {
            mPersonalLayout.setVisibility(View.VISIBLE);
            mCompanyLayout.setVisibility(View.GONE);
            
            setTitle(R.string.regist_personal_user);
        }
        else
        {
            mPersonalLayout.setVisibility(View.GONE);
            mCompanyLayout.setVisibility(View.VISIBLE);
            setTitle(R.string.regist_company_user);
        }
        
    }
    
    private void initCompanyView()
    {
        mPwdEdit = (EditText) findViewById(R.id.company_password_edit);
        mPwdTil = (TextInputLayout) findViewById(R.id.company_password_til);
        mConfirmPwdEdit = (EditText) findViewById(R.id.company_confirm_password_edit);
        mConfirmPwdTil = (TextInputLayout) findViewById(R.id.company_confirm_password_til);
        
        mEmailEdit = (EditText) findViewById(R.id.company_email_edit);
        mEmailTil = (TextInputLayout) findViewById(R.id.company_email_til);
        mNickNameEdit = (EditText) findViewById(R.id.company_nick_name_edit);
        
        //mCompanyLayout(special)
        mCompanyEdit = (EditText) findViewById(R.id.company_name_edit);
        mCompanyTil = (TextInputLayout) findViewById(R.id.company_name_til);
        mCorpLicenseImg = (ImageView) findViewById(R.id.corp_image);
    }
    
    @Override
    protected void handleStateMessage(Message msg)
    {
        super.handleStateMessage(msg);
        switch (msg.what)
        {
            case REQUEST_REGIST_USER_SUCCESSED:
                
                showToast(R.string.regist_user_successed, Toast.LENGTH_LONG);
                
                finish();
                break;
            
            case REQUEST_REGIST_USER_FAILED:
                
                showToast(R.string.regist_user_failed, Toast.LENGTH_LONG);
                break;
            
            case REQUEST_IMAGE_NOT_FOUND:
                
                showToast(R.string.imageloader_image_not_found,
                        Toast.LENGTH_LONG);
                break;
            
            case REQUEST_REGIST_COMPANY_SUCCESSED:
                
                if (null != mProgressDialog && mProgressDialog.isShowing())
                {
                    mProgressDialog.dismiss();
                }
                
                showToast(R.string.regist_company_successed, Toast.LENGTH_LONG);
                finish();
                break;
            
            case REQUEST_REGIST_COMPANY_FAILED:
                
                if (null != mProgressDialog && mProgressDialog.isShowing())
                {
                    mProgressDialog.dismiss();
                }
                
                showToast(R.string.regist_company_failed, Toast.LENGTH_LONG);
                break;
            
            case REQUEST_REGIST_IMAGE_UPLOAD_SUCCESSED:
                
                if (null != mProgressDialog && mProgressDialog.isShowing())
                {
                    mProgressDialog.dismiss();
                }
                
                mLicensePath = (String) msg.obj;
                
                showToast(R.string.regist_company_image_upload_success,
                        Toast.LENGTH_LONG);
                break;
            
            case REQUEST_REGIST_IMAGE_UPLOAD_FAILED:
                
                if (null != mProgressDialog && mProgressDialog.isShowing())
                {
                    mProgressDialog.dismiss();
                }
                
                showToast(R.string.regist_company_image_upload_failed,
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
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case REQUEST_ADD_IMAGE_CODE:
                    
                    List<String> imageList = data.getStringArrayListExtra(IMAGE_RESULT_LIST);
                    
                    String imgPath = imageList.get(0);
                    mCorpLicenseImg.setImageBitmap(Utility.createImageThumbnail(imgPath));
                    
                    mProgressDialog = ProgressDialog.show(this,
                            getString(R.string.warning),
                            getString(R.string.please_wait));
                    
                    mLoginLogic.uploadCompanyImage(validateSid,
                            userMobile,
                            imgPath);
                    break;
            }
        }
    }
}
