/**
 * 
 */
package com.taihold.shuangdeng.logic.home;

import android.widget.ImageView;

import com.taihold.shuangdeng.logic.model.CorpHonoerDbModel;
import com.taihold.shuangdeng.logic.model.DevPathDbModel;

import java.util.List;

/**
 * 欢迎页逻辑接口
 * 
 * @author 牛凡
 */
public interface IHomePageLogic
{
    
    /**
     * 获取公司简介
     */
    public void getIntroduce();
    
    /**
     * 获取发展历程
     */
    public void getDevelopmentPath();
    
    /**
     * 公司荣誉
     */
    public void getCorpHonorList();
    
    /**
     * 新闻轮播图
     */
    public void getSliderList(String from);
    
    /**
     * 新闻列表
     */
    public void getNewsList(int typeId, String from);
    
    /**
     * 获取新闻详情
     */
    public void getNewsDetail(String url);
    
    /**
     * 微信支付
     */
    public void wechatPay(String orderId);
    
    /**
     * 支付宝支付
     */
    public void alipayPayout(String orderId);
    
}
