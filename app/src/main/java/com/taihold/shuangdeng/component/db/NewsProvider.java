/**
 * 
 */
package com.taihold.shuangdeng.component.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * ContentProvider数据提供器
 * 
 * @author 牛凡
 */
public class NewsProvider extends ContentProvider
{
    
    SQLiteDbHelper dBlite;
    
    SQLiteDatabase db;
    
    private static final UriMatcher sMatcher;
    static
    {
        sMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sMatcher.addURI(URIField.AUTOHORITY, URIField.TNAME, URIField.ITEM);
        sMatcher.addURI(URIField.AUTOHORITY,
                URIField.TNAME + "/#",
                URIField.ITEM_ID);
        
    }
    
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        // TODO Auto-generated method stub
        db = dBlite.getWritableDatabase();
        int count = 0;
        switch (sMatcher.match(uri))
        {
            case URIField.ITEM:
                count = db.delete(URIField.TNAME, selection, selectionArgs);
                break;
            case URIField.ITEM_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete(URIField.TID, URIField.TID
                        + "="
                        + id
                        + (!TextUtils.isEmpty(URIField.TID = "?") ? "AND("
                                + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
    
    @Override
    public String getType(Uri uri)
    {
        // TODO Auto-generated method stub
        switch (sMatcher.match(uri))
        {
            case URIField.ITEM:
                return URIField.CONTENT_TYPE;
            case URIField.ITEM_ID:
                return URIField.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }
    }
    
    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        // TODO Auto-generated method stub
        
        db = dBlite.getWritableDatabase();
        long rowId;
        if (sMatcher.match(uri) != URIField.ITEM)
        {
            throw new IllegalArgumentException("Unknown URI" + uri);
        }
        rowId = db.insert(URIField.TNAME, URIField.TID, values);
        if (rowId > 0)
        {
            Uri noteUri = ContentUris.withAppendedId(URIField.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }
        throw new IllegalArgumentException("Unknown URI" + uri);
    }
    
    @Override
    public boolean onCreate()
    {
        // TODO Auto-generated method stub
        this.dBlite = new SQLiteDbHelper(this.getContext());
        //                db = dBlite.getWritableDatabase();
        //                return (db == null)?false:true;
        return true;
    }
    
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder)
    {
        // TODO Auto-generated method stub
        db = dBlite.getWritableDatabase();
        Cursor c;
        Log.d("-------", String.valueOf(sMatcher.match(uri)));
        switch (sMatcher.match(uri))
        {
            case URIField.ITEM:
                c = db.query(URIField.TNAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null);
                
                break;
            case URIField.ITEM_ID:
                String id = uri.getPathSegments().get(1);
                c = db.query(URIField.TNAME,
                        projection,
                        URIField.TID
                                + "="
                                + id
                                + (!TextUtils.isEmpty(selection) ? "AND("
                                        + selection + ')' : ""),
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                Log.d("!!!!!!", "Unknown URI" + uri);
                throw new IllegalArgumentException("Unknown URI" + uri);
        }
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }
    
    @Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs)
    {
        // TODO Auto-generated method stub
        return 0;
    }
}
