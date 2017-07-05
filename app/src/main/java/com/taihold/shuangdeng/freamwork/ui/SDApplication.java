package com.taihold.shuangdeng.freamwork.ui;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.yanzhenjie.nohttp.NoHttp;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by niufan on 17/3/10.
 */

public class SDApplication extends Application
{
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        
        //NO HTTP初始化
        NoHttp.initialize(this);
        
        //极光推送初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        
        //        NoHttp.initialize(this, new NoHttp.Config()
        //                .setConnectTimeout(30 * 1000) // 全局连接超时时间，单位毫秒。
        //                .setReadTimeout(30 * 1000) // 全局服务器响应超时时间，单位毫秒。
        //        );
        
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder() //  
        .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration//  
        .Builder(getApplicationContext())//  
        .defaultDisplayImageOptions(defaultOptions)
                //  
                .discCacheSize(50 * 1024 * 1024)
                //  
                .discCacheFileCount(100)
                // 缓存一百张图片  
                .writeDebugLogs()
                //  
                .build();//  
        ImageLoader.getInstance().init(config);
    }
    
    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        
        System.gc();
    }
}
