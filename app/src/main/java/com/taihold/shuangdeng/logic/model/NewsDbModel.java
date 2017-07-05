/**
 * 
 */
package com.taihold.shuangdeng.logic.model;

import java.io.Serializable;

/**
 * 首页列表数据库Model
 * 
 * @author 牛凡
 */
public class NewsDbModel implements Serializable
{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -983843271073766298L;
    
    private int newsDetailId;
    
    private String iconUrl;
    
    private String title;
    
    private String content;
    
    private String readCount;
    
    private String createTime;
    
    public int getNewsDetailId()
    {
        return newsDetailId;
    }
    
    public void setNewsDetailId(int newsDetailId)
    {
        this.newsDetailId = newsDetailId;
    }
    
    public String getIconUrl()
    {
        return iconUrl;
    }
    
    public void setIconUrl(String iconUrl)
    {
        this.iconUrl = iconUrl;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public String getReadCount()
    {
        return readCount;
    }
    
    public void setReadCount(String readCount)
    {
        this.readCount = readCount;
    }
    
    public String getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
}
