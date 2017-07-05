package com.taihold.shuangdeng.logic.model;

import android.graphics.Bitmap;

/**
 * Created by niufan on 17/3/22.
 */

public class CorpHonoerDbModel
{
    private int id;
    
    private String iconPath;
    
    private String title;
    
    private String subTitle;
    
    private Bitmap icon;
    
    public String getIconPath()
    {
        return iconPath;
    }
    
    public void setIconPath(String iconPath)
    {
        this.iconPath = iconPath;
    }
    
    public void setIcon(Bitmap icon)
    {
        this.icon = icon;
    }
    
    public Bitmap getIcon()
    {
        return icon;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getSubTitle()
    {
        return subTitle;
    }
    
    public void setSubTitle(String subTitle)
    {
        this.subTitle = subTitle;
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
}
