/**
 * 
 */
package com.taihold.shuangdeng.logic.home;

import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_CORP_HONOER_LIST_SUCCESSED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_GET_ALIPAY_ORDER_ID_SUCCESS;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_GET_COMPANY_INTRODUCE_SUCCESSED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_GET_DEVELOPMENT_PATH_SUCCESSED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_GET_WECHAT_ORDER_ID_SUCCESS;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_NEWS_DETAIL;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_NEWS_LIST_FROM_HOME_SUCCESSED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_NEWS_LIST_FROM_NEWS_CORP_SUCCESSED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_NEWS_LIST_FROM_NEWS_EXHIBITION_SUCCESSED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_NEWS_LIST_FROM_NEWS_MEDIA_SUCCESSED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_NEWS_SLIDER_IMAGE_MAPS_FROM_HOME_SUCCESSED;
import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_NEWS_SLIDER_IMAGE_MAPS_FROM_NEWS_SUCCESSED;
import static com.taihold.shuangdeng.common.HttpHelper.HEAD_URL;
import static com.taihold.shuangdeng.common.HttpHelper.REQUEST_ALIPAY_PAYOUT;
import static com.taihold.shuangdeng.common.HttpHelper.REQUEST_CORP_NEWS_LIST;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.taihold.shuangdeng.common.FusionAction;
import com.taihold.shuangdeng.common.HttpHelper;
import com.taihold.shuangdeng.freamwork.logic.BaseLogic;
import com.taihold.shuangdeng.logic.model.CorpHonoerDbModel;
import com.taihold.shuangdeng.logic.model.DevPathDbModel;
import com.taihold.shuangdeng.logic.model.NewsDbModel;
import com.taihold.shuangdeng.logic.model.PayModel;
import com.taihold.shuangdeng.logic.model.SliderDbModel;
import com.taihold.shuangdeng.util.JsonUtil;
import com.taihold.shuangdeng.util.PullParser;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * 欢迎页面Logic
 * 
 * @author 牛凡
 */
public class HomePageLogic extends BaseLogic implements IHomePageLogic
{
    /**
     * DEBUG_TAG
     */
    private static final String TAG = "HomePageLogic";
    
    /**
     * Context
     */
    private Context mContext;
    
    /**
     * 请求队列
     */
    private RequestQueue mQueue;
    
    private SharedPreferences mSp;
    
    /**
     * 
     */
    private ProgressDialog mPbDialog;
    
    public HomePageLogic(Context context)
    {
        this.mContext = context;
        
        mQueue = NoHttp.newRequestQueue();
        mSp = mContext.getSharedPreferences("ChinaShoto", mContext.MODE_PRIVATE);
        
    }
    
    @Override
    public void getIntroduce()
    {
        final Request<JSONObject> request = NoHttp.createJsonObjectRequest(HttpHelper.HEAD_URL
                + HttpHelper.REQUEST_COMPANY_INTRODUCE,
                RequestMethod.POST);
        
        mQueue.add(1, request, new OnResponseListener<JSONObject>()
        {
            @Override
            public void onStart(int what)
            {
                
            }
            
            @Override
            public void onSucceed(int what, Response<JSONObject> response)
            {
                JSONObject jsonObj = response.get();
                Log.d(TAG, jsonObj.toString());
                try
                {
                    if (JsonUtil.parseJsonSuccessFlag(jsonObj.toString()))
                    {
                        Log.d(TAG, "data = " + jsonObj.toString());
                        
                        String content = JsonUtil.parseJsonContent(jsonObj.toString());
                        Log.d(TAG, content);
                        
                        sendMessage(REQUEST_GET_COMPANY_INTRODUCE_SUCCESSED,
                                content);
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
    public void getDevelopmentPath()
    {
        final Request<JSONObject> request = NoHttp.createJsonObjectRequest(HttpHelper.HEAD_URL
                + HttpHelper.REQUEST_DEVELOPMENT_PATH,
                RequestMethod.POST);
        
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
                        List<DevPathDbModel> list = JsonUtil.parseJsonContentList(response.get()
                                .toString());
                        
                        sendMessage(REQUEST_GET_DEVELOPMENT_PATH_SUCCESSED,
                                list);
                        
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
    public void getCorpHonorList()
    {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(HttpHelper.HEAD_URL
                + HttpHelper.REQUEST_CORP_HONOER,
                RequestMethod.POST);
        
        request.add("pageSize", "8");
        
        mQueue.add(1, request, new OnResponseListener<JSONObject>()
        {
            @Override
            public void onStart(int what)
            {
                
            }
            
            @Override
            public void onSucceed(int what, Response<JSONObject> response)
            {
                String jsonString = response.get().toString();
                Log.d(TAG, "CorpHonoer = " + jsonString);
                try
                {
                    if (JsonUtil.parseJsonSuccessFlag(jsonString))
                    {
                        List<CorpHonoerDbModel> list = JsonUtil.parseHonoerList(jsonString);
                        
                        sendMessage(REQUEST_CORP_HONOER_LIST_SUCCESSED, list);
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
    public void getSliderList(final String from)
    {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(HttpHelper.HEAD_URL
                + HttpHelper.REQUEST_CORP_NEWS_SLIDER_IMAGE_LIST,
                RequestMethod.POST);
        
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
                
                Log.d(TAG, "news slider list = " + json);
                
                try
                {
                    if (JsonUtil.parseJsonSuccessFlag(json))
                    {
                        List<SliderDbModel> list = JsonUtil.parseSliderImages(json);
                        
                        if (from.equals("home"))
                        {
                            sendMessage(REQUEST_NEWS_SLIDER_IMAGE_MAPS_FROM_HOME_SUCCESSED,
                                    list);
                        }
                        if (from.equals("news"))
                        {
                            sendMessage(REQUEST_NEWS_SLIDER_IMAGE_MAPS_FROM_NEWS_SUCCESSED,
                                    list);
                        }
                        
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
    public void getNewsList(int typeId, final String from)
    {
        final Request<JSONObject> request = NoHttp.createJsonObjectRequest(HttpHelper.HEAD_URL
                + REQUEST_CORP_NEWS_LIST + typeId,
                RequestMethod.POST);
        
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
                
                Log.d(TAG, "news List = " + jsonStr);
                
                try
                {
                    if (JsonUtil.parseJsonSuccessFlag(jsonStr))
                    {
                        List<NewsDbModel> newsList = JsonUtil.parseNewsList(jsonStr);
                        
                        if (from.equals("home"))
                        {
                            sendMessage(REQUEST_NEWS_LIST_FROM_HOME_SUCCESSED,
                                    newsList);
                        }
                        if (from.equals("news"))
                        {
                            sendMessage(REQUEST_NEWS_LIST_FROM_NEWS_CORP_SUCCESSED,
                                    newsList);
                        }
                        if (from.equals("media"))
                        {
                            sendMessage(REQUEST_NEWS_LIST_FROM_NEWS_MEDIA_SUCCESSED,
                                    newsList);
                        }
                        
                        if (from.equals("exhibition"))
                        {
                            sendMessage(REQUEST_NEWS_LIST_FROM_NEWS_EXHIBITION_SUCCESSED,
                                    newsList);
                        }
                        
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
    public void getNewsDetail(String url)
    {
        final Request<JSONObject> request = NoHttp.createJsonObjectRequest(url,
                RequestMethod.POST);
        
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
                Log.d(TAG, "news detail = " + jsonStr);
                
                try
                {
                    if (JsonUtil.parseJsonSuccessFlag(jsonStr))
                    {
                        String newsContent = JsonUtil.parseNewsDetail(jsonStr);
                        
                        StringBuffer sb = new StringBuffer();
                        
                        sb.append("<html><head>")
                                .append("</head>")
                                .append("<body>")
                                .append(newsContent)
                                .append("</body>")
                                .append("</html>");
                        
                        sendMessage(REQUEST_NEWS_DETAIL, newsContent);
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
    public void wechatPay(String orderId)
    {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(HEAD_URL
                + HttpHelper.REQUEST_WECHAT_PAYOUT, RequestMethod.POST);
        
        String sid = mSp.getString("sid", "");
        request.add(HttpHelper.UploadImageParam.SID, sid);
        request.add(HttpHelper.Pay_Attr.ORDER_ID, orderId);
        request.add("tradeType", "APP");
        
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
                Log.d(TAG, "wechat pay = " + jsonStr);
                try
                {
                    if (JsonUtil.parseJsonSuccessFlag(jsonStr))
                    {
                        String prepayXml = JsonUtil.parsePayXml(jsonStr);
                        Log.d(TAG, "prepayXml = " + prepayXml);
                        PayModel model = PullParser.parseXml(prepayXml);
                        
                        sendMessage(REQUEST_GET_WECHAT_ORDER_ID_SUCCESS, model);
                    }
                    else
                    {
                        Intent toLogin = new Intent(FusionAction.LOGIN_ACTION);
                        toLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        
                        mContext.startActivity(toLogin);
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
    public void alipayPayout(String orderId)
    {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(HEAD_URL
                + REQUEST_ALIPAY_PAYOUT, RequestMethod.POST);
        
        String sid = mSp.getString("sid", "");
        request.add(HttpHelper.UploadImageParam.SID, sid);
        request.add(HttpHelper.Pay_Attr.ORDER_ID, orderId);
        request.add("isQRcode", "false");
        
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
                
                Log.d(TAG, "alipay payinfo = " + jsonStr);
                
                try
                {
                    if (JsonUtil.parseJsonSuccessFlag(jsonStr))
                    {
                        String prepayXml = JsonUtil.parsePayXml(jsonStr);
                        
                        sendMessage(REQUEST_GET_ALIPAY_ORDER_ID_SUCCESS,
                                prepayXml);
                        
                    }
                    else
                    {
                        Intent toLogin = new Intent(FusionAction.LOGIN_ACTION);
                        toLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        
                        mContext.startActivity(toLogin);
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
}
