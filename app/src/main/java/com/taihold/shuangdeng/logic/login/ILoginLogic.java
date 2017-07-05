package com.taihold.shuangdeng.logic.login;

/**
 * Created by niufan on 17/3/26.
 */

public interface ILoginLogic
{
    
    public void userLogin(String userName, String password);
    
    public void uploadCompanyImage(String sid, String loginName, String image);
    
    public void companyRegist(String confirmCode, String mobile, String mail,
            String name, String companyName, String password, String licensePath);
    
    public void userRegist(String confirmCode, String mobile, String mail,
            String name, String password);
    
    public void getSMSConfirmCode(String phoneNum, String verifyCode);
    
    public void getImageConfirmCode(String phoneNum);
    
    public void validateSmsCode(String mobile, String smsCode);
    
    public void resetPwd4Forget(String sid, String password);
    
    public void sendSMSConfrimCode4ForgetPwd(String mobile);
    
    public void logout(String sid);
}
