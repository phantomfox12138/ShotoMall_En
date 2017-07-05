/**
 * 
 */
package com.taihold.shuangdeng.logic.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.taihold.shuangdeng.component.db.URIField;

/**
 * 首页数据库适配器
 * 
 * @author 牛凡
 */
public class HomeListDbAdapter
{
    /**
     * DEBUG_TAG
     */
    private static final String TAG = "HomeListDbAdapter";
    
    /**
     * EmailBoxDbAdapter 实例
     */
    private static HomeListDbAdapter sInstance;
    
    /**
     * ContentResolver 对象
     */
    private ContentResolver mContentResolver;
    
    /**
     * 私有构造方法
     * 
     * @param context
     *            Context
     */
    private HomeListDbAdapter(Context context)
    {
        mContentResolver = context.getContentResolver();
    }
    
    /**
     * 获得HomeListDbAdapter唯一实例<BR>
     * 
     * @param context
     *            Context
     * @return HomeListDbAdapter 对象
     */
    public static synchronized HomeListDbAdapter getInsatance(Context context)
    {
        if (null == sInstance)
        {
            sInstance = new HomeListDbAdapter(context);
        }
        return sInstance;
    }
    
    public void test()
    {
        //通过contentResolver进行查找
        Cursor cursor = mContentResolver.query(URIField.CONTENT_URI,
                new String[] { URIField.EMAIL, URIField.USERNAME,
                        URIField.DATE, URIField.SEX },
                null,
                null,
                null);
        while (cursor.moveToNext())
        {
            Log.d(TAG,
                    "sql = "
                            + cursor.getString(cursor.getColumnIndex(URIField.EMAIL))
                            + " "
                            + cursor.getString(cursor.getColumnIndex(URIField.USERNAME))
                            + " "
                            + cursor.getString(cursor.getColumnIndex(URIField.DATE))
                            + " "
                            + cursor.getString(cursor.getColumnIndex(URIField.SEX)));
        }
        
        cursor.close();
    }
}
