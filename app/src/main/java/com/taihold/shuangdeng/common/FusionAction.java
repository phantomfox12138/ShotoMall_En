/**
 * 
 */
package com.taihold.shuangdeng.common;

/**
 * 所有UI跳转界面的action定义
 * 
 * @author 牛凡
 */
public class FusionAction
{
    
    /**
     * 首页 action name
     */
    public static final String HOME_ACTION = "com.taihold.shuangdeng.HOME_PAGE";
    
    /**
     * 登录 action
     */
    public static final String LOGIN_ACTION = "com.taihold.shuangdeng.LOGIN";
    
    public interface LoginExtra
    {
        public String FROM = "from";
    }
    
    /**
     * 注册 action
     */
    public static final String REGIST_ACTION = "com.taihold.shuangdeng.REGIST";
    
    /**
     * 注册详情 action
     */
    public static final String REGIST_DETAIL_ACTION = "com.taihold.shuangdeng.REGIST_DETAIL";
    
    /**
     * 忘记密码 （验证部分）action
     */
    public static final String FORGET_PASSWORD_ACTION = "com.taihold.shuangdeng.FORGET_PASSWORD";
    
    /**
     * WebActivity action
     */
    public static final String WEB_ACTION = "com.taihold.shuangdeng.WEB_ACTION";
    
    /**
     * ImageLoader
     */
    public static final String IMAGELOADER_ACTION = "com.taihold.shuangdeng.IMAGE_LOADER";
    
    /**
     * 图片验证码
     */
    public static final String IMAGE_VERIFY = "com.taihold.shuangdeng.IMAGE_VERIFY";
    
    /**
     * 忘记密码（输入部分） action
     */
    public static final String FORGET_PWD_DETAIL = "com.taihold.shuangdeng.FORGETPWD_DETAIL";
    
    /**
     * 微信支付页面（测试）
     */
    public static final String WECHAT_PAYMENT_ACTIVITY = "com.taihold.shuangdeng.WECHAT_PAYMENT";
    
    public interface IMAGE_VERIFY_EXTRA
    {
        public String USERNAME = "user_name";
        
        public int IMAGE_VERIFY_CODE = 0xff11;
    }
    
    public interface WEB_ATTR
    {
        public String URL = "web_url";
        
        public String BUTTON_FLAG = "button_flag";
        
        public String TITLE = "title";
        
        public String VALUE = "value";
        
        public String REQEUST = "request";
        
        public String DELETE_KEY = "delete_key";
        
        public String FROM_WHERE = "from_where";
        
    }
    
    public interface REGIST_EXTRA
    {
        public String USER_TYPE = "user_type";
        
        public String USER_MOBILE = "user_mobile";
        
        public String SMS_VALIDATE_CODE = "sms_validate_code";
        
        public int REQUEST_ADD_IMAGE_CODE = 0x22ff;
        
        public int REQUEST_CODE_ASK_PERMISSIONS = 0x33ff;
        
        /**
         * 获取图片总数
         */
        public String IMAGE_LOADER_COUNT_EXTRA = "maxnum";
        
        public String IMAGE_RESULT_LIST = "datalist";
        
    }
    
    /**
     * 图片详情
     */
    public static final String GRID_IMAGE_DETAIL_ACTION = "com.taihold.shuangdeng.GRID_IMAGE_DETAIL";
    
    public interface GRID_IMAGE_DETAIL_EXTRA
    {
        String IMAGE_URLS = "image_urls";
        
        String TITLE = "title";
    }
    
    /**
     * 分公司详情
     */
    public static final String CORP_DETAIL_ACTION = "com.taihold.shuangdeng.CORP_DETAIL";
    
    public interface CORP_DETAIL_EXTRA
    {
        String CORP_NAME = "corp_name";
        
        String POSITION = "position";
    }
}
