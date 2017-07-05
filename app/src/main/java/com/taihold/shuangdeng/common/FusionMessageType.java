/**
 * 
 */
package com.taihold.shuangdeng.common;

/**
 * 消息类型定义<BR>
 * 
 * @author 牛凡
 */
public class FusionMessageType
{
    /**
     * 网络异常what基数
     */
    private static final int BASE_HTTP_ONERROR = -0x100001;
    
    /**
     * slash页
     */
    public static final int SLASH_HTTP_ONERROR = BASE_HTTP_ONERROR + 1;
    
    public static final int REQUEST_IAMGE_CONFIRM_CODE_SUCCESS = BASE_HTTP_ONERROR + 2;
    
    public static final int REQUEST_IAMGE_CONFIRM_CODE_FAILED = BASE_HTTP_ONERROR + 3;
    
    public static final int REQUEST_VALIIDATE_CODE_CONFIRM_SUCCESS = BASE_HTTP_ONERROR + 4;
    
    public static final int REQUEST_VALIIDATE_CODE_CONFIRM_FAILED = BASE_HTTP_ONERROR + 5;
    
    public static final int REQUEST_REGIST_USER_SUCCESSED = BASE_HTTP_ONERROR + 6;
    
    public static final int REQUEST_REGIST_USER_FAILED = BASE_HTTP_ONERROR + 7;
    
    public static final int REQUEST_LOGIN_USER_SUCCESSED = BASE_HTTP_ONERROR + 8;
    
    public static final int REQUEST_LOGIN_USER_FAILED = BASE_HTTP_ONERROR + 9;
    
    public static final int REQUEST_USER_HAS_REGISTED = BASE_HTTP_ONERROR + 10;
    
    public static final int REQUEST_SMS_CODE_HAS_SENDED = BASE_HTTP_ONERROR + 11;
    
    public static final int REQUEST_SMS_CODE_HAS_SENDED_ERROR = BASE_HTTP_ONERROR + 12;
    
    public static final int REQUEST_IMAGE_NOT_FOUND = BASE_HTTP_ONERROR + 13;
    
    public static final int REQUEST_REGIST_COMPANY_SUCCESSED = BASE_HTTP_ONERROR + 14;
    
    public static final int REQUEST_REGIST_COMPANY_FAILED = BASE_HTTP_ONERROR + 15;
    
    public static final int REQUEST_REGIST_IMAGE_UPLOAD_SUCCESSED = BASE_HTTP_ONERROR + 16;
    
    public static final int REQUEST_REGIST_IMAGE_UPLOAD_FAILED = BASE_HTTP_ONERROR + 17;
    
    public static final int REQUEST_LOGIN_FORGET_PWD_RESET_SUCCESSED = BASE_HTTP_ONERROR + 18;
    
    public static final int REQUEST_LOGIN_FORGET_PWD_RESET_FAILED = BASE_HTTP_ONERROR + 19;
    
    public static final int REQUEST_GET_COMPANY_INTRODUCE_SUCCESSED = BASE_HTTP_ONERROR + 20;
    
    public static final int REQUEST_GET_COMPANY_INTRODUCE_FAILED = BASE_HTTP_ONERROR + 21;
    
    public static final int REQUEST_GET_DEVELOPMENT_PATH_SUCCESSED = BASE_HTTP_ONERROR + 22;
    
    public static final int REQUEST_GET_DEVELOPMENT_PATH_FAIILED = BASE_HTTP_ONERROR + 23;
    
    public static final int REQUEST_DOWNLOAD_DEV_PATH_IMAGE_SUCCESSED = BASE_HTTP_ONERROR + 24;
    
    public static final int REQUEST_DOWNLOAD_DEV_PATH_IMAGE_FAILED = BASE_HTTP_ONERROR + 25;
    
    public static final int REQUEST_DOWNLOAD_IMG_SUCCESSED = BASE_HTTP_ONERROR + 26;
    
    public static final int REQUEST_CORP_HONOER_ICON_SUCCESSED = BASE_HTTP_ONERROR + 27;
    
    public static final int REQUEST_CORP_HONOER_ICON_FAILED = BASE_HTTP_ONERROR + 28;
    
    public static final int REQUEST_CORP_HONOER_LIST_SUCCESSED = BASE_HTTP_ONERROR + 29;
    
    public static final int REQUEST_CORP_HONOER_LIST_FAILED = BASE_HTTP_ONERROR + 30;
    
    public static final int REQUEST_NEWS_SLIDER_IMAGE_MAPS_FROM_HOME_SUCCESSED = BASE_HTTP_ONERROR + 31;
    
    public static final int REQUEST_NEWS_SLIDER_IMAGE_MAPS_FROM_HOME_FAILED = BASE_HTTP_ONERROR + 32;
    
    public static final int REQUEST_NEWS_LIST_FROM_HOME_SUCCESSED = BASE_HTTP_ONERROR + 33;
    
    public static final int REQUEST_NEWS_LIST_FROM_HOME_FAILED = BASE_HTTP_ONERROR + 34;
    
    public static final int REQUEST_NEWS_LIST_FROM_NEWS_CORP_SUCCESSED = BASE_HTTP_ONERROR + 35;
    
    public static final int REQUEST_NEWS_LIST_FROM_NEWS_CORP__FAILED = BASE_HTTP_ONERROR + 36;
    
    public static final int REQUEST_NEWS_SLIDER_IMAGE_MAPS_FROM_NEWS_SUCCESSED = BASE_HTTP_ONERROR + 37;
    
    public static final int REQUEST_NEWS_SLIDER_IMAGE_MAPS_FROM_NEWS_FAILED = BASE_HTTP_ONERROR + 38;
    
    public static final int REQUEST_NEWS_LIST_FROM_NEWS_MEDIA_SUCCESSED = BASE_HTTP_ONERROR + 39;
    
    public static final int REQUEST_NEWS_LIST_FROM_NEWS_MEDIA__FAILED = BASE_HTTP_ONERROR + 40;
    
    public static final int REQUEST_NEWS_LIST_FROM_NEWS_EXHIBITION_SUCCESSED = BASE_HTTP_ONERROR + 41;
    
    public static final int REQUEST_NEWS_LIST_FROM_NEWS_EXHIBITION__FAILED = BASE_HTTP_ONERROR + 42;
    
    public static final int REQUEST_NEWS_DETAIL = BASE_HTTP_ONERROR + 43;
    
    public static final int REQUEST_GET_WECHAT_ORDER_ID_SUCCESS = BASE_HTTP_ONERROR + 44;
    
    public static final int REQUEST_GET_ALIPAY_ORDER_ID_SUCCESS = BASE_HTTP_ONERROR + 45;
    
    public static final int REQUEST_USER_LOGOUT_SUCCESSED = BASE_HTTP_ONERROR + 46;
    
    public static final int REQUEST_USER_LOGOUT_FAILED = BASE_HTTP_ONERROR + 47;
    
}
