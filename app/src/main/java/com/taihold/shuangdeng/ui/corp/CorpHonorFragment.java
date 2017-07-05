package com.taihold.shuangdeng.ui.corp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.taihold.shuangdeng.R;
import com.taihold.shuangdeng.common.FusionAction;
import com.taihold.shuangdeng.common.HttpHelper;
import com.taihold.shuangdeng.logic.model.CorpHonoerDbModel;

public class CorpHonorFragment extends Fragment
{
    private View mRootView;
    
    private ListView mCorpHonoerList;
    
    private static CorpHonoerListAdapter adapter;
    
    private List<CorpHonoerDbModel> data = new ArrayList<CorpHonoerDbModel>();;
    
    private DisplayImageOptions options;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        options = new DisplayImageOptions.Builder()//.showImageOnLoading(R.drawable.ic_stub)
        //.showImageForEmptyUri(R.drawable.ic_empty)
        //.showImageOnFail(R.drawable.ic_error)
        .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                //.displayer(new RoundedBitmapDisplayer(20))
                .build();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        if (null == mRootView)
        {
            mRootView = inflater.inflate(R.layout.fragment_corp_honor,
                    container,
                    false);
        }
        
        initView();
        
        return mRootView;
    }
    
    private void initView()
    {
        mCorpHonoerList = (ListView) mRootView.findViewById(R.id.corp_honoer_list);
        
        //
        //        for (int i = 0; i < 10; i++)
        //        {
        //            CorpHonoerDbModel model = new CorpHonoerDbModel();
        //
        //            data.add(model);
        //        }
        
        adapter = new CorpHonoerListAdapter(getActivity());
        adapter.setDataList(data);
        
        mCorpHonoerList.setAdapter(adapter);
        
        mCorpHonoerList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id)
            {
                Holder holder = (Holder) view.getTag();
                
                Intent toWeb = new Intent(FusionAction.WEB_ACTION);
                toWeb.putExtra(FusionAction.WEB_ATTR.REQEUST,
                        HttpHelper.HEAD_URL
                                + HttpHelper.REQUEST_CORP_HONOER_DETAIL
                                + holder.id);
                toWeb.putExtra(FusionAction.WEB_ATTR.BUTTON_FLAG, "X");
                toWeb.putExtra(FusionAction.WEB_ATTR.TITLE, data.get(position)
                        .getTitle());
                startActivity(toWeb);
            }
        });
        
    }
    
    public void setHomoerList(List<CorpHonoerDbModel> list)
    {
        data.clear();
        data.addAll(list);
    }
    
    class CorpHonoerListAdapter extends BaseAdapter
    {
        private Context context;
        
        private List<CorpHonoerDbModel> dataList;
        
        private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
        
        public CorpHonoerListAdapter(Context context)
        {
            this.context = context;
        }
        
        public void setDataList(List<CorpHonoerDbModel> dataList)
        {
            this.dataList = dataList;
        }
        
        @Override
        public int getCount()
        {
            return null == dataList || dataList.size() == 0 ? 0
                    : dataList.size();
        }
        
        @Override
        public Object getItem(int position)
        {
            return null;
        }
        
        @Override
        public long getItemId(int position)
        {
            return 0;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            Holder holder;
            
            if (convertView == null)
            {
                
                convertView = LayoutInflater.from(context)
                        .inflate(R.layout.corp_honoer_list_item, null);
                
                holder = new Holder();
                
                holder.icon = (ImageView) convertView.findViewById(R.id.corp_honoer_icon);
                holder.title = (TextView) convertView.findViewById(R.id.corp_honoer_title);
                holder.subTitle = (TextView) convertView.findViewById(R.id.corp_honoer_subtitle);
                
                convertView.setTag(holder);
                
            }
            else
            {
                holder = (Holder) convertView.getTag();
            }
            
            holder.title.setText(dataList.get(position).getTitle());
            holder.honoerTitle = dataList.get(position).getTitle();
            holder.id = dataList.get(position).getId();
            holder.subTitle.setText(dataList.get(position).getSubTitle());
            
            ImageLoader.getInstance().displayImage(HttpHelper.IMAGE_HEAD_URL
                    + dataList.get(position).getIconPath(),
                    holder.icon,
                    options,
                    animateFirstListener);
            
            return convertView;
        }
    }
    
    class Holder
    {
        ImageView icon;
        
        TextView title;
        
        TextView subTitle;
        
        String honoerTitle;
        
        int id;
    }
    
    private static class AnimateFirstDisplayListener extends
            SimpleImageLoadingListener
    {
        
        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
        
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
                
                adapter.notifyDataSetChanged();
            }
        }
    }
    
}
