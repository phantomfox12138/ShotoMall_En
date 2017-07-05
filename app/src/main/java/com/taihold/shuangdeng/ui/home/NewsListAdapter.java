package com.taihold.shuangdeng.ui.home;

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.taihold.shuangdeng.R;
import com.taihold.shuangdeng.logic.model.NewsDbModel;
import com.taihold.shuangdeng.util.StringUtil;

/**
 * Created by niufan on 17/3/15.
 */

public class NewsListAdapter extends RecyclerView.Adapter<NewsListHolder>
{
    private static final String TAG = "NewsListAdapter";
    
    public static final int NORMAL_TYPE = 0;
    
    public static final int FOOT_TYPE = 11;
    
    private int max_count = 10;
    
    private Boolean isFootView = false;
    
    private NewsListHolder holder;
    
    private NewsListHolder footer;
    
    private Context mContext;
    
    private List<NewsDbModel> mNewsListData;
    
    private OnItemClickLitener mListener;
    
    private DisplayImageOptions options;
    
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    
    public NewsListAdapter(Context context)
    {
        this.mContext = context;
        options = new DisplayImageOptions.Builder()//.showImageOnLoading(R.drawable.ic_stub)
        //.showImageForEmptyUri(R.drawable.ic_empty)
        //.showImageOnFail(R.drawable.ic_error)
        .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                //.displayer(new RoundedBitmapDisplayer(20))
                .build();
        
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
                context);
        
        builder.threadPoolSize(4);
        ImageLoader.getInstance().init(builder.build());
    }
    
    public void setHomeListData(List<NewsDbModel> homeListData)
    {
        this.mNewsListData = homeListData;
    }
    
    public interface OnItemClickLitener
    {
        void onItemClick(NewsListHolder view, int position);
        
        void onItemLongClick(NewsListHolder view, int position);
    }
    
    public void setOnItemClickListener(OnItemClickLitener listener)
    {
        this.mListener = listener;
    }
    
    @Override
    public NewsListHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        holder = new NewsListHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.news_list_item, null), NORMAL_TYPE);
        
        footer = new NewsListHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_footer_layout, parent, false), FOOT_TYPE);
        
        if (viewType == FOOT_TYPE)
            return footer;
        
        return holder;
    }
    
    @Override
    public int getItemViewType(int position)
    {
        if (position == max_count - 1)
        {
            return FOOT_TYPE;
        }
        return NORMAL_TYPE;
    }
    
    @Override
    public void onBindViewHolder(final NewsListHolder holder, int position)
    {
        //如果footview存在，并且当前位置ViewType是FOOT_TYPE
        if (isFootView && (getItemViewType(position) == FOOT_TYPE))
        {
            holder.footer_title.setText(holder.footViewText);
            // 刷新太快 所以使用Hanlder延迟两秒
            Handler handler = new Handler();
            handler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    max_count += 5;
                    notifyDataSetChanged();
                }
            }, 2000);
            
        }
        else
        {
            holder.itemTitle.setText(mNewsListData.get(position).getTitle());
            holder.itemContent.setText(mNewsListData.get(position).getContent());
//            holder.itemReadCount.setText(mNewsListData.get(position)
//                    .getReadCount() + mContext.getString(R.string.read_count));
            holder.id = mNewsListData.get(position).getNewsDetailId();
            
            try
            {
                Date date = StringUtil.parse(mNewsListData.get(position)
                        .getCreateTime());
                String createTime = StringUtil.formatDate(date);
                
                Log.d(TAG, "date = " + date.toString());
                Log.d(TAG, "createTime = " + createTime);
                
                holder.itemCreateTime.setText(createTime);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            
            ImageLoader.getInstance().displayImage(mNewsListData.get(position)
                    .getIconUrl(),
                    holder.itemIcon,
                    options,
                    animateFirstListener);
            
            // 如果设置了回调，则设置点击事件
            if (mListener != null)
            {
                holder.itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        int pos = holder.getLayoutPosition();
                        mListener.onItemClick(holder, pos);
                    }
                });
                
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
                {
                    @Override
                    public boolean onLongClick(View v)
                    {
                        int pos = holder.getLayoutPosition();
                        mListener.onItemLongClick(holder, pos);
                        return false;
                    }
                });
            }
        }
        
    }
    
    @Override
    public int getItemCount()
    {
        if (mNewsListData == null)
        {
            return 0;
        }
        if (mNewsListData.size() < max_count)
        {
            return mNewsListData.size();
        }
        return max_count;
    }
    
    private class AnimateFirstDisplayListener extends
            SimpleImageLoadingListener
    {
        
        final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
        
        @Override
        public void onLoadingComplete(String imageUri, View view,
                Bitmap loadedImage)
        {
            if (loadedImage != null)
            {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay)
                {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
                
                //                notifyDataSetChanged();
            }
        }
    }
    
    //创建一个方法来设置footView中的文字
    public void setFootFlag()
    {
        isFootView = true;
        //        footer.footViewText = footViewText;
    }
    
}
