package com.taihold.shuangdeng.logic.login;

import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_IAMGE_CONFIRM_CODE_FAILED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_IAMGE_CONFIRM_CODE_SUCCESS;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_LOGIN_FORGET_PWD_RESET_FAILED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_LOGIN_FORGET_PWD_RESET_SUCCESSED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_LOGIN_USER_FAILED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_LOGIN_USER_SUCCESSED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_REGIST_COMPANY_FAILED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_REGIST_COMPANY_SUCCESSED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_REGIST_IMAGE_UPLOAD_FAILED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_REGIST_IMAGE_UPLOAD_SUCCESSED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_REGIST_USER_FAILED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_REGIST_USER_SUCCESSED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_SMS_CODE_HAS_SENDED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_SMS_CODE_HAS_SENDED_ERROR;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_USER_HAS_REGISTED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_USER_LOGOUT_SUCCESSED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_VALIIDATE_CODE_CONFIRM_FAILED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_VALIIDATE_CODE_CONFIRM_SUCCESS;
import static com.taihold.shuangdeng.common.HttpHelper.HEAD_URL;
import static com.taihold.shuangdeng.common.HttpHelper.REQUEST_COMPANY_REGIST;
import static com.taihold.shuangdeng.common.HttpHelper.REQUEST_FORGET_PASSWORD;
import static com.taihold.shuangdeng.common.HttpHelper.REQUEST_SMS_CONFIRM_CODE;
import static com.taihold.shuangdeng.common.HttpHelper.REQUEST_SMS_PASSWORD;
import static com.taihold.shuangdeng.common.HttpHelper.REQUEST_SMS_VALIDATE;
import static com.taihold.shuangdeng.common.HttpHelper.REQUEST_UPLOAD_IMAGE;
import static com.taihold.shuangdeng.common.HttpHelper.REQUEST_USER_LOGIN;
import static com.taihold.shuangdeng.common.HttpHelper.REQUEST_USER_LOGOUT;
import static com.taihold.shuangdeng.common.HttpHelper.REQUEST_USER_REGIST;
import static com.taihold.shuangdeng.common.HttpHelper.RQUEST_IMAGE_CONFIRM_CODE;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Looper;
import android.util.Log;

import com.taihold.shuangdeng.common.HttpHelper;
import com.taihold.shuangdeng.freamwork.logic.BaseLogic;
import com.taihold.shuangdeng.util.JsonUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by niufan on 17/3/26.
 */

public class LoginLogic extends BaseLogic implements ILoginLogic
{
    /**
     * DEBUG_TAG
     */
    private static final String TAG = "LoginLogic";
    
    private Context mContext;
    
    private RequestQueue mQueue;
    
    public LoginLogic(Context mContext)
    {
        this.mContext = mContext;
        
        mQueue = NoHttp.newRequestQueue();
    }
    
    @Override
    public void userLogin(String userName, String password)
    {
        
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(HEAD_URL
                + REQUEST_USER_LOGIN, RequestMethod.POST);
        
        request.add(HttpHelper.LoginUserParam.LOGINNAME, userName);
        request.add(HttpHelper.LoginUserParam.PASSWORD, password);
        
        mQueue.add(1, request, new OnResponseListener<JSONObject>()
        {
            @Override
            public void onStart(int what)
            {
                
            }
            
            @Override
            public void onSucceed(int what, Response<JSONObject> response)
            {
                
                Log.d(TAG, response.get().toString());
                
                try
                {
                    if (JsonUtil.parseJsonSuccessFlag(response.get().toString()))
                    {
                        
                        String sid = JsonUtil.parseJsonLoginSid(response.get()
                                .toString());
                        //                        String name = JsonUtil.parseJsonName(response.get().toString());
                        
                        sendMessage(REQUEST_LOGIN_USER_SUCCESSED, sid);
                        
                    }
                    else
                    {
                        
                        sendEmptyMessage(REQUEST_LOGIN_USER_FAILED);
                    }
                    
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                
            }
            
            @Override
            public void onFailed(int what, Response<JSONObject> response)
            {
                sendEmptyMessage(REQUEST_LOGIN_USER_FAILED);
            }
            
            @Override
            public void onFinish(int what)
            {
                
            }
        });
        
    }
    
    @Override
    public void companyRegist(String confirmCode, String mobile, String mail,
            String name, String companyName, String password, String licensePath)
    {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(HEAD_URL
                + REQUEST_COMPANY_REGIST, RequestMethod.POST);
        
        request.add(HttpHelper.RegistUserParam.SID, confirmCode);
        request.add(HttpHelper.RegistUserParam.MOBILE, mobile);
        request.add(HttpHelper.RegistUserParam.COMPANY_MAIL, mail);
        request.add(HttpHelper.RegistUserParam.NAME, name);
        request.add(HttpHelper.RegistUserParam.PASSWORD, password);
        request.add(HttpHelper.RegistUserParam.UNIT_NAME, companyName);
        request.add(HttpHelper.RegistUserParam.LICENSE_PATH, licensePath);
        
        mQueue.add(1, request, new OnResponseListener<JSONObject>()
        {
            @Override
            public void onStart(int what)
            {
                
            }
            
            @Override
            public void onSucceed(int what, Response<JSONObject> response)
            {
                JSONObject jsonObject = response.get();
                
                try
                {
                    if (JsonUtil.parseJsonSuccessFlag(jsonObject.toString()))
                    {
                        sendEmptyMessage(REQUEST_REGIST_COMPANY_SUCCESSED);
                    }
                    else
                    {
                        sendEmptyMessage(REQUEST_REGIST_COMPANY_FAILED);
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            
            @Override
            public void onFailed(int what, Response<JSONObject> response)
            {
                sendEmptyMessage(REQUEST_REGIST_COMPANY_FAILED);
            }
            
            @Override
            public void onFinish(int what)
            {
                
            }
        });
    }
    
    @Override
    public void uploadCompanyImage(final String sid, final String loginName,
            final String image)
    {
        
        //        IconUploader loader = new IconUploader(mContext,
        //                new IconUploader.onFileUploadListener()
        //                {
        //                    
        //                    @Override
        //                    public void onFileUploadSuccess()
        //                    {
        //                        sendEmptyMessage(REQUEST_REGIST_IMAGE_UPLOAD_SUCCESSED);
        //                    }
        //                    
        //                    @Override
        //                    public void onFileUploadFailed()
        //                    {
        //                        sendEmptyMessage(REQUEST_REGIST_IMAGE_UPLOAD_FAILED);
        //                    }
        //                });
        
        IconUploader.setListener(new IconUploader.onResponseListener()
        {
            @Override
            public void onUploadSuccess(String msg, String path)
            {
                sendMessage(REQUEST_REGIST_IMAGE_UPLOAD_SUCCESSED, path);
            }
            
            @Override
            public void onUploadFailed()
            {
                sendEmptyMessage(REQUEST_REGIST_IMAGE_UPLOAD_FAILED);
            }
        });
        
        new Thread()
        {
            
            public void run()
            {
                Looper.prepare();
                
                try
                {
                    
                    Map<String, String> param = new HashMap<String, String>();
                    param.put(HttpHelper.UploadImageParam.LOGINNAME, loginName);
                    param.put(HttpHelper.UploadImageParam.SID, sid);
                    
                    Map<String, File> fileMap = new HashMap<String, File>();
                    fileMap.put("image", new File(image));
                    
                    IconUploader.post(HEAD_URL + REQUEST_UPLOAD_IMAGE,
                            param,
                            fileMap);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                Looper.loop();
            };
            
        }.start();
    }
    
    @Override
    public void userRegist(String confirmCode, String mobile, String mail,
            String name, String password)
    {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(HEAD_URL
                + REQUEST_USER_REGIST, RequestMethod.POST);
        
        request.add(HttpHelper.RegistUserParam.SID, confirmCode);
        request.add(HttpHelper.RegistUserParam.MOBILE, mobile);
        request.add(HttpHelper.RegistUserParam.MAIL, mail);
        request.add(HttpHelper.RegistUserParam.NAME, name);
        request.add(HttpHelper.RegistUserParam.PASSWORD, password);
        
        mQueue.add(1, request, new OnResponseListener<JSONObject>()
        {
            @Override
            public void onStart(int what)
            {
                
            }
            
            @Override
            public void onSucceed(int what, Response<JSONObject> response)
            {
                JSONObject jObj = response.get();
                try
                {
                    if (JsonUtil.parseJsonSuccessFlag(response.get().toString()))
                    {
                        
                        sendEmptyMessage(REQUEST_REGIST_USER_SUCCESSED);
                    }
                    else
                    {
                        sendEmptyMessage(REQUEST_REGIST_USER_FAILED);
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            
            @Override
            public void onFailed(int what, Response<JSONObject> response)
            {
                sendEmptyMessage(REQUEST_REGIST_USER_FAILED);
            }
            
            @Override
            public void onFinish(int what)
            {
                
            }
        });
        
    }
    
    @Override
    public void validateSmsCode(String mobile, String smsCode)
    {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(HEAD_URL
                + REQUEST_SMS_VALIDATE, RequestMethod.POST);
        
        request.add(HttpHelper.ValidateParam.MOBILE, mobile);
        request.add(HttpHelper.ValidateParam.SMSCODE, smsCode);
        
        mQueue.add(1, request, new OnResponseListener<JSONObject>()
        {
            @Override
            public void onStart(int what)
            {
                
            }
            
            @Override
            public void onSucceed(int what, Response<JSONObject> response)
            {
                
                try
                {
                    if (JsonUtil.parseJsonSuccessFlag(response.get().toString()))
                    {
                        
                        sendMessage(REQUEST_VALIIDATE_CODE_CONFIRM_SUCCESS,
                                JsonUtil.parseJsonSid(response.get().toString()));
                        
                    }
                    else
                    {
                        sendEmptyMessage(REQUEST_VALIIDATE_CODE_CONFIRM_FAILED);
                    }
                    
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                
                Log.d(TAG, response.get().toString());
                
            }
            
            @Override
            public void onFailed(int what, Response<JSONObject> response)
            {
            }
            
            @Override
            public void onFinish(int what)
            {
                
            }
        });
        
    }
    
    @Override
    public void resetPwd4Forget(String sid, String password)
    {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(HEAD_URL
                + REQUEST_FORGET_PASSWORD, RequestMethod.POST);
        
        request.add(HttpHelper.ForgetPwdParam.SID, sid);
        request.add(HttpHelper.ForgetPwdParam.PASSWORD, password);
        
        mQueue.add(1, request, new OnResponseListener<JSONObject>()
        {
            @Override
            public void onStart(int what)
            {
                
            }
            
            @Override
            public void onSucceed(int what, Response<JSONObject> response)
            {
                String json = response.get().toString();
                
                try
                {
                    if (JsonUtil.parseJsonSuccessFlag(json))
                    {
                        sendEmptyMessage(REQUEST_LOGIN_FORGET_PWD_RESET_SUCCESSED);
                    }
                    else
                    {
                        sendEmptyMessage(REQUEST_LOGIN_FORGET_PWD_RESET_FAILED);
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            
            @Override
            public void onFailed(int what, Response<JSONObject> response)
            {
                sendEmptyMessage(REQUEST_LOGIN_FORGET_PWD_RESET_FAILED);
            }
            
            @Override
            public void onFinish(int what)
            {
                
            }
        });
    }
    
    @Override
    public void sendSMSConfrimCode4ForgetPwd(String mobile)
    {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(HEAD_URL
                + REQUEST_SMS_PASSWORD, RequestMethod.POST);
        
        request.add(HttpHelper.ValidateParam.MOBILE, mobile);
        
        mQueue.add(1, request, new OnResponseListener<JSONObject>()
        {
            @Override
            public void onStart(int what)
            {
                
            }
            
            @Override
            public void onSucceed(int what, Response<JSONObject> response)
            {
                JSONObject jObj = response.get();
                Log.d(TAG, jObj.toString());
                
                try
                {
                    if (JsonUtil.parseJsonSuccessFlag(jObj.toString()))
                    {
                        sendEmptyMessage(REQUEST_SMS_CODE_HAS_SENDED);
                    }
                    else
                    {
                        sendEmptyMessage(REQUEST_USER_HAS_REGISTED);
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            
            @Override
            public void onFailed(int what, Response<JSONObject> response)
            {
                sendEmptyMessage(REQUEST_SMS_CODE_HAS_SENDED_ERROR);
            }
            
            @Override
            public void onFinish(int what)
            {
                
            }
        });
    }
    
    @Override
    public void logout(String sid)
    {
        final Request<JSONObject> request = NoHttp.createJsonObjectRequest(HEAD_URL
                + REQUEST_USER_LOGOUT,
                RequestMethod.POST);
        request.add(HttpHelper.RegistUserParam.SID, sid);
        
        mQueue.add(1, request, new OnResponseListener<JSONObject>()
        {
            @Override
            public void onStart(int what)
            {
                
            }
            
            @Override
            public void onSucceed(int what, Response<JSONObject> response)
            {
                String jsonStr = response.get().toString();
                
                Log.d(TAG, "successed logout json = " + jsonStr);
                
                try
                {
                    if (JsonUtil.parseJsonSuccessFlag(jsonStr))
                    {
                        sendEmptyMessage(REQUEST_USER_LOGOUT_SUCCESSED);
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                
            }
            
            @Override
            public void onFailed(int what, Response<JSONObject> response)
            {
            }
            
            @Override
            public void onFinish(int what)
            {
                
            }
        });
        
    }
    
    @Override
    public void getSMSConfirmCode(String phoneNum, String verifyCode)
    {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(HEAD_URL
                + REQUEST_SMS_CONFIRM_CODE, RequestMethod.POST);
        
        request.add(HttpHelper.SmsConfirmCodeParam.MOBILE, phoneNum);
        request.add(HttpHelper.SmsConfirmCodeParam.IMAGECODE, verifyCode);
        
        mQueue.add(1, request, new OnResponseListener<JSONObject>()
        {
            @Override
            public void onStart(int what)
            {
                
            }
            
            @Override
            public void onSucceed(int what, Response<JSONObject> response)
            {
                JSONObject jObj = response.get();
                Log.d(TAG, jObj.toString());
                
                try
                {
                    if (JsonUtil.parseJsonSuccessFlag(jObj.toString()))
                    {
                        sendEmptyMessage(REQUEST_SMS_CODE_HAS_SENDED);
                    }
                    else
                    {
                        sendEmptyMessage(REQUEST_USER_HAS_REGISTED);
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            
            @Override
            public void onFailed(int what, Response<JSONObject> response)
            {
                sendEmptyMessage(REQUEST_SMS_CODE_HAS_SENDED_ERROR);
            }
            
            @Override
            public void onFinish(int what)
            {
                
            }
        });
        
    }
    
    @Override
    public void getImageConfirmCode(String phoneNum)
    {
        Request<Bitmap> requestBitmap = NoHttp.createImageRequest(HEAD_URL
                + RQUEST_IMAGE_CONFIRM_CODE);
        
        requestBitmap.add(HttpHelper.ImageConfirmCodeParam.LOGINNAME, phoneNum);
        requestBitmap.add(HttpHelper.ImageConfirmCodeParam.WIDTH, 700);
        requestBitmap.add(HttpHelper.ImageConfirmCodeParam.HEIGHT, 260);
        requestBitmap.add(HttpHelper.ImageConfirmCodeParam.FONT, 200);
        
        mQueue.add(1, requestBitmap, new OnResponseListener<Bitmap>()
        {
            @Override
            public void onStart(int what)
            {
                
            }
            
            @Override
            public void onSucceed(int what, Response<Bitmap> response)
            {
                Bitmap bitmap = response.get();
                
                if (null != bitmap)
                {
                    sendMessage(REQUEST_IAMGE_CONFIRM_CODE_SUCCESS, bitmap);
                }
                else
                {
                    sendEmptyMessage(REQUEST_IAMGE_CONFIRM_CODE_FAILED);
                }
            }
            
            @Override
            public void onFailed(int what, Response<Bitmap> response)
            {
                
            }
            
            @Override
            public void onFinish(int what)
            {
                
            }
        });
    }
}
