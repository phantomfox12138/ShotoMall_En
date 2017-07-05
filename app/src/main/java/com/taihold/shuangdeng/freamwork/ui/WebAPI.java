package com.taihold.shuangdeng.freamwork.ui;

import android.content.Context;
import android.webkit.WebView;

import com.balysv.materialmenu.MaterialMenu;

/**
 * Created by niufan on 17/3/26.
 */

public interface WebAPI
{
    public void openNewActivity(Context context, String url);
    
    public void setNaviIcon(MaterialMenu materialMenu, boolean flag);
    
    public void saveToken(String token);
    
    public String getToken();
    
}
