package com.taihold.shuangdeng.ui.login;

import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_IAMGE_CONFIRM_CODE_FAILED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_IAMGE_CONFIRM_CODE_SUCCESS;
import static com.taihold.shuangdeng.component.db.URIField.USERNAME;
import static com.taihold.shuangdeng.component.db.URIField.VERIFYCODE;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.taihold.shuangdeng.R;
import com.taihold.shuangdeng.freamwork.ui.BasicActivity;
import com.taihold.shuangdeng.logic.login.ILoginLogic;
import com.taihold.shuangdeng.util.StringUtil;

public class ImageVerifyActivity extends BasicActivity
{
    
    private EditText mImgConfirmEdit;
    
    private ImageView mConfirmImg;
    
    private TextInputLayout mImgConfirmTil;
    
    private Button mImgVerifyConfirmBtn;
    
    private ILoginLogic mLoginlogic;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_verify);
        
        initView();
    }
    
    @Override
    protected void initLogic()
    {
        super.initLogic();
        
        mLoginlogic = (ILoginLogic) getLogicByInterfaceClass(ILoginLogic.class);
    }
    
    private void initView()
    {
        
        final String username = getIntent().getStringExtra(USERNAME);
        
        mImgConfirmEdit = (EditText) findViewById(R.id.img_confirm_edit);
        mConfirmImg = (ImageView) findViewById(R.id.img_confirm_iv);
        mImgVerifyConfirmBtn = (Button) findViewById(R.id.image_verify_confirm_btn);
        
        mImgConfirmTil = (TextInputLayout) findViewById(R.id.img_confirm_til);
        
        mLoginlogic.getImageConfirmCode(username);
        
        mConfirmImg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                
                mLoginlogic.getImageConfirmCode(username);
            }
        });
        
        mImgVerifyConfirmBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                
                if (StringUtil.isNullOrEmpty(mImgConfirmEdit.getText()
                        .toString()))
                {
                    mImgConfirmTil.setError(getString(R.string.regist_image_confirm_code_error));
                    return;
                }
                
                Intent intent = getIntent().putExtra(USERNAME, username)
                        .putExtra(VERIFYCODE,
                                mImgConfirmEdit.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                
            }
        });
    }
    
    @Override
    protected void handleStateMessage(Message msg)
    {
        super.handleStateMessage(msg);
        
        switch (msg.what)
        {
        
            case REQUEST_IAMGE_CONFIRM_CODE_SUCCESS:
                
                mConfirmImg.setImageBitmap((Bitmap) msg.obj);
                
                break;
            case REQUEST_IAMGE_CONFIRM_CODE_FAILED:
                
                showToast(R.string.regist_get_img_verify_code_failed,
                        Toast.LENGTH_LONG);
                break;
        
        }
    }
}
