package com.taihold.shuangdeng.ui.corp;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.taihold.shuangdeng.R;
import com.taihold.shuangdeng.common.FusionAction;
import com.taihold.shuangdeng.common.HttpHelper;
import com.taihold.shuangdeng.logic.model.DevPathDbModel;
import com.yanzhenjie.nohttp.rest.RequestQueue;

public class DevelopPathFragment extends Fragment
{
    
    /**
     * DEBUG_TAG
     */
    private static final String TAG = "DevelopPathFragment";
    
    private View mRootView;
    
    private ListView mDevPathList;
    
    private List<DevPathDbModel> devPathDataList = new ArrayList<DevPathDbModel>();
    
    private RequestQueue mQueue;
    
    //    private ImageAdapter imageAdapter;// = new ImageAdapter(getActivity());
    
    private DevelopPathListAdapter DevPathAdatper;//= new DevelopPathListAdapter(
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        if (null == mRootView)
        {
            mRootView = inflater.inflate(R.layout.fragment_develop_path,
                    container,
                    false);
            
        }
        
        //        ImageLoaderConfig config = new ImageLoaderConfig().setLoadingPlaceholder(R.drawable.loading)
        //                .setNotFoundPlaceholder(R.drawable.not_found)
        //                .setCache(new DoubleCache(this))
        //                .setThreadCount(4)
        //                .setLoadPolicy(new ReversePolicy());
        //        // 初始化
        //        ImageLoader.getInstance().init(config);
        
        ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(
                getActivity()).threadPoolSize(4).build());
        
        DevPathAdatper = new DevelopPathListAdapter(getActivity());
        initView();
        
        return mRootView;
    }
    
    private void initView()
    {
        mDevPathList = (ListView) mRootView.findViewById(R.id.develop_list);
        
        //        for (int i = 0; i < 3; i++)
        //        {
        //            DevPathDbModel model = new DevPathDbModel();
        //
        //            devPathDataList.add(model);
        //        }
        
        //        DevPathAdatper = new DevelopPathListAdapter(getActivity());
        DevPathAdatper.setDataList(devPathDataList);
        
        mDevPathList.setAdapter(DevPathAdatper);
        
        //        adapter = new SimpleAdapter(getActivity(), gridData,
        //                R.layout.grid_item_layout, new String[]{"image"},
        //                new int[]{R.id.image});
        
        //        imageAdapter = new ImageAdapter(getActivity());
        
    }
    
    public void setDevPathList(List<DevPathDbModel> list)
    {
        devPathDataList.clear();
        devPathDataList.addAll(list);
        
    }
    
    public List<DevPathDbModel> getDevPathList()
    {
        return devPathDataList;
    }
    
    class DevelopPathListAdapter extends BaseAdapter
    {
        private List<DevPathDbModel> dataList;
        
        private Context context;
        
        public DevelopPathListAdapter(Context context)
        {
            this.context = context;
        }
        
        public void setDataList(List<DevPathDbModel> dataList)
        {
            this.dataList = dataList;
        }
        
        @Override
        public int getCount()
        {
            return dataList == null || dataList.size() == 0 ? 0
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
                        .inflate(R.layout.develop_list_item, null);
                
                holder = new Holder();
                
                holder.title = (TextView) convertView.findViewById(R.id.timeline_title);
                holder.content = (TextView) convertView.findViewById(R.id.timeline_content);
                holder.imageGrid = (DevGridView) convertView.findViewById(R.id.image_grid);
                
                holder.timelineUp = (TextView) convertView.findViewById(R.id.timeline_up);
                holder.timelineDown = (TextView) convertView.findViewById(R.id.timeline_down);
                
                convertView.setTag(holder);
                
            }
            else
            {
                holder = (Holder) convertView.getTag();
            }
            
            if (position == 0)
            {
                holder.timelineUp.setVisibility(View.GONE);
            }
            //            else if (position == dataList.size() - 1)
            //            {
            //                holder.timelineDown.setVisibility(View.GONE);
            //            }
            
            final DevPathDbModel model = dataList.get(position);
            
            holder.title.setText(model.getTime());
            holder.content.setText(model.getContent());
            Log.d(TAG, "content = " + model.getContent());
            
            if (model.getImageUrls() == null
                    && model.getImageUrls().length == 0)
            {
                holder.imageGrid.setVisibility(View.GONE);
            }
            else
            {
                ImageAdapter imageAdapter = new ImageAdapter(getActivity());
                imageAdapter.setModel(model);
                imageAdapter.setTitle(model.getContent());
                holder.imageGrid.setAdapter(imageAdapter);
            }
            
            return convertView;
        }
    }
    
    class Holder
    {
        TextView title;
        
        TextView content;
        
        TextView timelineUp;
        
        TextView timelineDown;
        
        GridView imageGrid;
    }
    
    class ImageAdapter extends BaseAdapter
    {
        
        private Context context;
        
        private DevPathDbModel model;
        
        private String title;
        
        public ImageAdapter(Context context)
        {
            this.context = context;
        }
        
        public void setModel(DevPathDbModel model)
        {
            this.model = model;
        }
        
        public void setTitle(String title)
        {
            this.title = title;
        }
        
        @Override
        public int getCount()
        {
            return model.getImageUrls() != null ? model.getImageUrls().length
                    : 0;
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
        public View getView(final int position, View convertView,
                ViewGroup parent)
        {
            
            final ImageHolder holder;
            
            if (convertView == null)
            {
                convertView = LayoutInflater.from(context)
                        .inflate(R.layout.grid_item_layout, null);
                holder = new ImageHolder();
                
                holder.imageview = (ImageView) convertView.findViewById(R.id.image);
                
                convertView.setTag(holder);
                
            }
            else
            {
                holder = (ImageHolder) convertView.getTag();
            }
            
            holder.imageview.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent toImgeaDetail = new Intent(
                            FusionAction.GRID_IMAGE_DETAIL_ACTION);
                    
                    toImgeaDetail.putExtra(FusionAction.GRID_IMAGE_DETAIL_EXTRA.IMAGE_URLS,
                            model.getImageUrls());
                    toImgeaDetail.putExtra(FusionAction.GRID_IMAGE_DETAIL_EXTRA.TITLE,
                            title);
                    
                    startActivity(toImgeaDetail);
                }
            });
            
            Log.d(TAG,
                    "develop path = " + HttpHelper.IMAGE_HEAD_URL
                            + model.getImageUrls()[position]
                            + "  imagePath.length = "
                            + model.getImageUrls().length);
            
            if (parent.getChildCount() == position)
            {
                
                if (holder.imageview.getDrawable() == null)
                {
                    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                            .cacheOnDisk(true)
                            .bitmapConfig(Bitmap.Config.RGB_565)
                            .build();
                    ImageLoader.getInstance()
                            .displayImage(HttpHelper.IMAGE_HEAD_URL
                                    + model.getImageUrls()[position],
                                    holder.imageview,
                                    options);
                }
            }
            else
            {
            }
            
            return convertView;
        }
        
        class ImageHolder
        {
            ImageView imageview;
        }
    }
    
    public void notifyDataSetChanged()
    {
        if (DevPathAdatper != null)// && imageAdapter != null)
        {
            DevPathAdatper.notifyDataSetChanged();
            //            imageAdapter.notifyDataSetChanged();
        }
    }
}
