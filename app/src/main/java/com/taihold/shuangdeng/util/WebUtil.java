package com.taihold.shuangdeng.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.balysv.materialmenu.MaterialMenu;
import com.balysv.materialmenu.MaterialMenuDrawable;
import com.taihold.shuangdeng.common.FusionAction;

/**
 * Created by niufan on 17/3/26.
 */

public class WebUtil
{
    private SharedPreferences mSp;
    
    public WebUtil(Context context)
    {
        mSp = context.getSharedPreferences("Shoto", Context.MODE_PRIVATE);
    }
    
    @JavascriptInterface
    public static void openNewActivity(Context context, String url)
    {
        Intent intent = new Intent(FusionAction.WEB_ACTION);
        
        intent.putExtra(FusionAction.WEB_ATTR.URL, url);
        
        context.startActivity(intent);
    }
    
    @JavascriptInterface
    public static void reloadPage(WebView webView, String url)
    {
        webView.loadUrl(url);
    }
    
    /**
     *
     * @param flag
     *            flag true X false back
     */
    @JavascriptInterface
    public static void setNaviIcon(MaterialMenu materialMenu, boolean flag)
    {
        materialMenu.animateIconState(flag ? MaterialMenuDrawable.IconState.X
                : MaterialMenuDrawable.IconState.ARROW);
    }
    
}
