/**
 * 
 */
package com.taihold.shuangdeng.util;

import android.text.Html;
import android.util.Log;

import com.taihold.shuangdeng.common.HttpHelper;
import com.taihold.shuangdeng.logic.model.CorpHonoerDbModel;
import com.taihold.shuangdeng.logic.model.DevPathDbModel;
import com.taihold.shuangdeng.logic.model.NewsDbModel;
import com.taihold.shuangdeng.logic.model.SliderDbModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Json解析工具类
 * 
 * @author 牛凡
 */
public class JsonUtil
{
    private static final String TAG = "JsonUtil";
    
    /**
     * Json串基础解析，解析返回消息
     * 
     * @param jsonString
     *            json字符串
     * @return msg 返回消息
     * @throws JSONException
     */
    public static String parseJsonMessage(String jsonString)
            throws JSONException
    {
        StringBuffer msg = new StringBuffer();
        JSONObject jsonObj = new JSONObject(jsonString);
        if (jsonObj.has("message"))
        {
            msg.append(jsonObj.get("message"));
        }
        
        return msg.toString();
    }
    
    /**
     * Json串基础解析，解析回执标志
     * 
     * @param jsonString
     * @return msg 回执标志
     * @throws JSONException
     */
    public static boolean parseJsonSuccessFlag(String jsonString)
            throws JSONException
    {
        //{"status":"200","message":"验证成功","data":"c6182e69364f4e499dd99103371605f0"}
        boolean flag = false;
        JSONObject jsonObj = new JSONObject(jsonString);
        if (jsonObj.has("status"))
        {
            if (jsonObj.getInt("status") == 200)
            {
                
                flag = true;
                
                return flag;
            }
            
        }
        return flag;
        
    }
    
    public static String parseJsonLoginSid(String jsonString)
            throws JSONException
    {
        JSONObject jsonObject = new JSONObject(jsonString);
        if (jsonObject.has("data"))
        {
            JSONObject jsonObj = jsonObject.getJSONObject("data");
            if (jsonObj.has("sid"))
            {
                return jsonObj.getString("sid");
            }
        }
        
        return null;
    }
    
    public static String parseJsonName(String jsonStirng) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(jsonStirng);
        
        if (jsonObject.has("name"))
        {
            return jsonObject.getString("name");
        }
        return null;
    }
    
    public static String parseJsonSid(String jsonString) throws JSONException
    {
        JSONObject jsonObj = new JSONObject(jsonString);
        if (jsonObj.has("data"))
        {
            return jsonObj.getString("data");
        }
        
        return null;
    }
    
    public static String parseJsonContent(String jsonString)
            throws JSONException
    {
        JSONObject jsonObject = new JSONObject(jsonString);
        
        if (jsonObject.has("data"))
        {
            JSONObject jObj = jsonObject.getJSONObject("data");
            
            if (jObj.has("content"))
            {
                String content = jObj.getString("content");
                
                return content;
            }
            
        }
        
        return null;
    }
    
    public static List<DevPathDbModel> parseJsonContentList(String jsonString)
            throws JSONException
    {
        JSONObject jsonObject = new JSONObject(jsonString);
        
        List<DevPathDbModel> list = new ArrayList<DevPathDbModel>();
        
        if (jsonObject.has("data"))
        {
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            
            for (int i = 0; i < jsonArray.length(); i++)
            {
                DevPathDbModel model = new DevPathDbModel();
                
                JSONObject jObj = jsonArray.getJSONObject(i);
                
                if (jObj.has("title"))
                {
                    String title = jObj.getString("title");
                    
                    model.setTime(title);
                }
                
                if (jObj.has("remarks"))
                {
                    String description = jObj.getString("remarks");
                    
                    String content = Html.fromHtml(description).toString();
                    
                    model.setContent(content);
                }
                
                if (jObj.has("image"))
                {
                    String imagePath = jObj.getString("image");
                    
                    String[] imgPaths = imagePath.split("\\|");
                    
                    //                    List<String> imgList = new ArrayList<String>();
                    //                    for (int j = 0; j < imgPaths.length; j++)
                    //                    {
                    //                        imgList.add(imgPaths[j]);
                    //                    }
                    //                    model.setImagePaths(imgList);
                    model.setImageUrls(imgPaths);
                    
                }
                
                list.add(model);
            }
            
            return list;
            
        }
        
        return null;
        
    }
    
    public static List<CorpHonoerDbModel> parseHonoerList(String jsonStr)
            throws JSONException
    {
        List<CorpHonoerDbModel> list = new ArrayList<CorpHonoerDbModel>();
        
        JSONObject jsonObject = new JSONObject(jsonStr);
        
        if (jsonObject.has("data"))
        {
            JSONObject jObj = jsonObject.getJSONObject("data");
            
            if (jObj.has("list"))
            {
                JSONArray jsonArray = jObj.getJSONArray("list");
                
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    CorpHonoerDbModel model = new CorpHonoerDbModel();
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    
                    if (jsonObj.has("id"))
                    {
                        model.setId(jsonObj.getInt("id"));
                    }
                    
                    if (jsonObj.has("title"))
                    {
                        model.setTitle(jsonObj.getString("title"));
                    }
                    
                    if (jsonObj.has("description"))
                    {
                        model.setSubTitle(jsonObj.getString("description"));
                    }
                    
                    if (jsonObj.has("image"))
                    {
                        model.setIconPath(jsonObj.getString("image"));
                    }
                    
                    list.add(model);
                }
                
                return list;
            }
        }
        
        return null;
    }
    
    public static List<SliderDbModel> parseSliderImages(String jsonStr)
            throws JSONException
    {
        List<SliderDbModel> list = new ArrayList<SliderDbModel>();
        
        JSONObject jsonObject = new JSONObject(jsonStr);
        
        if (jsonObject.has("data"))
        {
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                SliderDbModel model = new SliderDbModel();
                
                if (jsonObj.has("image"))
                {
                    model.setTitle(jsonObj.has("title") ? jsonObj.getString("title")
                            : "");
                    model.setImageUrl(HttpHelper.IMAGE_HEAD_URL
                            + jsonObj.getString("image"));
                    
                    Log.d(TAG,
                            "slider image url = " + HttpHelper.IMAGE_HEAD_URL
                                    + jsonObj.getString("image"));
                    
                }
                if (jsonObj.has("id"))
                {
                    model.setId(jsonObj.getInt("id"));
                }
                
                list.add(model);
            }
            
            return list;
        }
        return null;
    }
    
    public static List<NewsDbModel> parseNewsList(String jsonStr)
            throws JSONException
    {
        List<NewsDbModel> list = new ArrayList<NewsDbModel>();
        
        JSONObject jsonObject = new JSONObject(jsonStr);
        
        if (jsonObject.has("data"))
        {
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                
                NewsDbModel model = new NewsDbModel();
                
                if (jsonObj.has("id"))
                {
                    model.setNewsDetailId(jsonObj.getInt("id"));
                }
                if (jsonObj.has("title"))
                {
                    model.setTitle(jsonObj.getString("title"));
                }
                if (jsonObj.has("description"))
                {
                    model.setContent(jsonObj.getString("description"));
                }
                if (jsonObj.has("createDate"))
                {
                    model.setCreateTime(jsonObj.getString("createDate"));
                }
                if (jsonObj.has("hits"))
                {
                    model.setReadCount(jsonObj.getString("hits"));
                }
                if (jsonObj.has("image"))
                {
                    model.setIconUrl(HttpHelper.IMAGE_HEAD_URL
                            + jsonObj.getString("image"));
                    
                    Log.d(TAG,
                            "news list image url = "
                                    + HttpHelper.IMAGE_HEAD_URL
                                    + jsonObj.getString("image"));
                }
                
                list.add(model);
            }
            
            return list;
        }
        
        return null;
    }
    
    public static String parseNewsDetail(String jsonString)
            throws JSONException
    {
        JSONObject jsonObject = new JSONObject(jsonString);
        if (jsonObject.has("data"))
        {
            JSONObject jsonObj = jsonObject.getJSONObject("data");
            if (jsonObj.has("content"))
            {
                String content = jsonObj.getString("content");
                
                return content;
            }
        }
        
        return null;
    }
    
    public static String parsePayXml(String jsonStr) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(jsonStr);
        if (jsonObject.has("data"))
        {
            JSONObject jsonObj = jsonObject.getJSONObject("data");
            if (jsonObj.has("pay"))
            {
                String payXml = jsonObj.getString("pay");
                return payXml;
            }
        }
        
        return null;
    }
    
    public static String parseUploadMsg(String json) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(json);
        
        String message = null;
        if (jsonObject.has("message"))
        {
            message = jsonObject.getString("message");
        }
        
        return message;
    }
    
    public static String parseUploadPath(String json) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(json);
        
        String path = null;
        
        if (jsonObject.has("data"))
        {
            path = jsonObject.getString("data");
        }
        
        return path;
    }
}
