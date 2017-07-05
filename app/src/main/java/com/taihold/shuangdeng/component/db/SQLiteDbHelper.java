/**
 * 
 */
package com.taihold.shuangdeng.component.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库帮助类
 * 
 * @author 牛凡
 */
public class SQLiteDbHelper extends SQLiteOpenHelper
{
    public SQLiteDbHelper(Context context)
    {
        super(context, URIField.DBNAME, null, URIField.VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + URIField.TNAME + "(" + URIField.TID
                + " integer primary key autoincrement not null,"
                + URIField.EMAIL + " text not null," + URIField.USERNAME
                + " text not null," + URIField.DATE + " interger not null,"
                + URIField.SEX + " text not null);");
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS person");
        onCreate(db);
    }
    
    public void add(String email, String username, String date, String sex)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(URIField.EMAIL, email);
        values.put(URIField.USERNAME, username);
        values.put(URIField.DATE, date);
        values.put(URIField.SEX, sex);
        db.insert(URIField.TNAME, "", values);
    }
}
