package com.taihold.shuangdeng.util;

import android.util.Xml;

import com.taihold.shuangdeng.logic.model.PayModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by niufan on 17/5/5.
 */

public class PullParser
{
    private static PayModel model;
    
    public static PayModel parseXml(String xml)
    {
        
        // 由android.util.Xml创建一个XmlPullParser实例
        XmlPullParser parser = Xml.newPullParser();
        
        // 产生第一个事件
        int eventType = 0;
        try
        {
            InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            // 设置输入流 并指明编码方式
            parser.setInput(is, "UTF-8");
            
            eventType = parser.getEventType();
            
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                
                switch (eventType)
                {
                // 判断当前事件是否为文档开始事件
                    case XmlPullParser.START_DOCUMENT:
                        //                    booksList = new ArrayList<Book>(); // 初始化books集合
                        break;
                    
                    // 判断当前事件是否为标签元素开始事件
                    case XmlPullParser.START_TAG:
                        
                        if (parser.getName().equals("xml"))
                        {
                            model = new PayModel();
                        }
                        else if (parser.getName().equals("prepay_id"))
                        {
                            eventType = parser.next();
                            model.setPrepayId(parser.getText());
                        }
                        else if (parser.getName().equals("nonce_str"))
                        {
                            eventType = parser.next();
                            model.setNonceStr(parser.getText());
                        }
                        
                        break;
                    
                    // 判断当前事件是否为标签元素结束事件
                    case XmlPullParser.END_TAG:
                        break;
                }
                // 进入下一个元素并触发相应事件
                eventType = parser.next();
            }
            
        }
        catch (XmlPullParserException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return model;
    }
}
