package com.taihold.shuangdeng.logic.model;

import java.io.Serializable;

/**
 * Created by niufan on 17/5/5.
 */

public class PayModel implements Serializable
{
    private String prepayId;
    
    private String nonceStr;
    
    public String getPrepayId()
    {
        return prepayId;
    }
    
    public void setPrepayId(String prepayId)
    {
        this.prepayId = prepayId;
    }
    
    public String getNonceStr()
    {
        return nonceStr;
    }
    
    public void setNonceStr(String nonceStr)
    {
        this.nonceStr = nonceStr;
    }
}
