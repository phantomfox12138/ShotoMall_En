package com.taihold.shuangdeng.common;

public class HttpHelper
{
    
    /**
     * 服务请求头部
     */
    //    public static final String HEAD_URL = "http://192.168.1.103:8080/shoto";
    //    public static final String IMAGE_HEAD_URL = "http://192.168.1.103:8080/";
    
    //    public static final String HEAD_URL = "http://192.168.1.117:8080/shoto";
    //    public static final String IMAGE_HEAD_URL = "http://192.168.1.117:8080";
    public static final String HEAD_URL = "http://portal.shotomall.com/shoto";
    
    public static final String IMAGE_HEAD_URL = "http://portal.shotomall.com";
    
    /**
     * 获取短信验证码(TEMP)
     */
    //    public static final String REQUEST_SMS_CONFIRM_CODE = "/sms/registerTest";
    
    /**
     * 获取短信验证码
     */
    public static final String REQUEST_SMS_CONFIRM_CODE = "/sms/register";
    
    public interface SmsConfirmCodeParam
    {
        String MOBILE = "mobile";
        
        String IMAGECODE = "imageCode";
    }
    
    /**
     * 验证短信验证码
     */
    public static final String REQUEST_SMS_VALIDATE = "/sms/validate";
    
    /**
     * 发送短信验证码（重置密码）
     */
    public static final String REQUEST_SMS_PASSWORD = "/sms/password";
    
    public interface ValidateParam
    {
        String MOBILE = "mobile";
        
        String SMSCODE = "code";
    }
    
    /**
     * 重置密码（忘记密码）
     */
    public static final String REQUEST_FORGET_PASSWORD = "/login/password";
    
    public interface ForgetPwdParam
    {
        String SID = "__sid";
        
        String PASSWORD = "password";
    }
    
    /**
     * 获取图片验证码
     */
    public static final String RQUEST_IMAGE_CONFIRM_CODE = "/validateCode/image";
    
    public interface ImageConfirmCodeParam
    {
        String LOGINNAME = "loginName";
        
        String WIDTH = "width";
        
        String HEIGHT = "height";
        
        String FONT = "font";
    }
    
    /**
     * 用户注册接口
     */
    public static final String REQUEST_USER_REGIST = "/register/saveUser";
    
    /**
     * 企业注册接口
     */
    public static final String REQUEST_COMPANY_REGIST = "/register/saveCorporateUser";
    
    public interface RegistUserParam
    {
        String SID = "__sid";
        
        String MOBILE = "mobile";
        
        String MAIL = "mail";
        
        String COMPANY_MAIL = "shotoCorporateUser.unitEmail";
        
        String PASSWORD = "password";
        
        String NAME = "name";
        
        String UNIT_NAME = "shotoCorporateUser.unitName";
        
        String LICENSE_PATH = "shotoCorporateUser.license";
    }
    
    /**
     * 上传营业执照照片
     */
    public static final String REQUEST_UPLOAD_IMAGE = "/register/uploadImage";
    
    public interface UploadImageParam
    {
        String LOGINNAME = "loginName";
        
        //MultipartFile流的图片
        String IMAGE = "image";
        
        String SID = "__sid";
    }
    
    /**
     * 登录
     */
    public static final String REQUEST_USER_LOGIN = "/login/login";
    
    public interface LoginUserParam
    {
        String LOGINNAME = "loginName";
        
        String PASSWORD = "password";
    }
    
    /**
     * 注销
     */
    public static final String REQUEST_USER_LOGOUT = "/shotoUser/logout";
    
    /**
     * 公司简介
     */
    public static final String REQUEST_COMPANY_INTRODUCE = "/page/findDetailById?id=105";
    
    /**
     * 发展历程
     */
    public static final String REQUEST_DEVELOPMENT_PATH = "/page/findList?id=16";
    
    /**
     * 公司荣誉
     */
    public static final String REQUEST_CORP_HONOER = "/page/findPages?id=18";
    
    /**
     * 新闻轮播图
     */
    public static final String REQUEST_CORP_NEWS_SLIDER_IMAGE_LIST = "/page/findList?id=119";
    
    /**
     * 新闻列表
     * <p>
     * 公司新闻 28<BR>
     * 媒体报道 29<BR>
     * 展会预告 30<BR>
     */
    public static final String REQUEST_CORP_NEWS_LIST = "/page/findList?id=";
    
    /**
     * 新闻详情
     */
    public static final String REQUEST_NEWS_DETAIL = "/page/findDetailById?id=";
    
    /**
     * 微信预支付
     */
    public static final String REQUEST_WECHAT_PAYOUT = "/shotoOrder/orderPayWechat";
    
    /**
     * 支付宝预支付
     */
    public static final String REQUEST_ALIPAY_PAYOUT = "/shotoOrder/orderPayAli";
    
    public interface Pay_Attr
    {
        String ORDER_ID = "orderId";
        
    }
    
    /**
     * 跳转商品列表
     * <p>
     * 0 通信保障 1 电力储能 2 交通动力 3 循环回收
     */
    public static final String TO_PRODUCT_LIST = "http://192.168.1.106/productList.html";
    
    public interface ProductGoodsListAttr
    {
        public String PRODUCTTYPE = "productType";
    }
    
    public static final String TO_ORDER_DETAIL = "http://192.168.1.106/orderdetail.html?";
    
    public interface OrderDetailAttr
    {
        public String ORDER_ID = "orderId";
    }
    
    /**
     * 公司荣誉详情
     */
    public static final String REQUEST_CORP_HONOER_DETAIL = "/page/findDetailById?id=";
    
}
