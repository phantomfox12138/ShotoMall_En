package com.taihold.shuangdeng.ui.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.taihold.shuangdeng.R;

import static com.taihold.shuangdeng.ui.home.NewsListAdapter.FOOT_TYPE;
import static com.taihold.shuangdeng.ui.home.NewsListAdapter.NORMAL_TYPE;

/**
 * Created by niufan on 17/3/23.
 */

public class NewsListHolder extends RecyclerView.ViewHolder
{
    public ImageView itemIcon;
    
    public TextView itemTitle;
    
    public TextView itemContent;
    
    public TextView itemReadCount;
    
    public TextView itemCreateTime;
    
    public TextView footer_title;
    
    public ProgressBar footer_pb;
    
    public String footViewText = "";
    
    public String title;
    
    public int id;
    
    public NewsListHolder(View itemView, int viewType)
    {
        super(itemView);
        
        if (viewType == NORMAL_TYPE)
        {
            itemIcon = (ImageView) itemView.findViewById(R.id.item_icon);
            itemTitle = (TextView) itemView.findViewById(R.id.item_title);
            itemContent = (TextView) itemView.findViewById(R.id.item_content);
            itemReadCount = (TextView) itemView.findViewById(R.id.item_read_count);
            itemCreateTime = (TextView) itemView.findViewById(R.id.item_create_time);
            
        }
        else if (viewType == FOOT_TYPE)
        {
            footer_title = (TextView) itemView.findViewById(R.id.footer_title);
            footer_pb = (ProgressBar) itemView.findViewById(R.id.footer_pb);
        }
    }
}
