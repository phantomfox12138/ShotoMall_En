package com.taihold.shuangdeng.logic.model;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.RequestQueue;

/**
 * Created by niufan on 17/3/21.
 */

public class DevPathDbModel
{
    private String time;
    
    private String content;
    
    private List<String> imagePaths;
    
    private String[] imageUrls;
    
    public void setImageUrls(String[] imageUrls)
    {
        this.imageUrls = imageUrls;
    }
    
    public String[] getImageUrls()
    {
        return imageUrls;
    }
    
    private List<Bitmap> bitmapList = new ArrayList<Bitmap>();
    
    public String getTime()
    {
        return time;
    }
    
    public void setTime(String time)
    {
        this.time = time;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public List<String> getImagePaths()
    {
        return imagePaths;
    }
    
    public void setImagePaths(List<String> imagePaths)
    {
        this.imagePaths = imagePaths;
    }
    
    public List<Bitmap> getBitmapList()
    {
        return bitmapList;
    }
    
}
