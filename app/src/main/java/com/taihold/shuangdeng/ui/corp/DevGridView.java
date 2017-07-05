package com.taihold.shuangdeng.ui.corp;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by niufan on 17/5/7.
 */

public class DevGridView extends GridView
{
    public DevGridView(Context context)
    {
        super(context);
    }
    
    public DevGridView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    
    public DevGridView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }
    
    public DevGridView(Context context, AttributeSet attrs, int defStyleAttr,
            int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
