package com.taihold.shuangdeng.ui.imageloader;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.taihold.shuangdeng.R;
import com.taihold.shuangdeng.ui.imageloader.bean.ImageFloder;
import com.taihold.shuangdeng.ui.imageloader.utils.BasePopupWindowForListView;
import com.taihold.shuangdeng.ui.imageloader.utils.CommonAdapter;
import com.taihold.shuangdeng.ui.imageloader.utils.ViewHolder;

import java.util.List;

public class ListImageDirPopupWindow extends
        BasePopupWindowForListView<ImageFloder>
{
    private ListView mListDir;
    
    public ListImageDirPopupWindow(int width, int height,
            List<ImageFloder> datas, View convertView)
    {
        super(convertView, width, height, true, datas);
    }
    
    @Override
    public void initViews()
    {
        mListDir = (ListView) findViewById(R.id.id_list_dir);
        mListDir.setAdapter(new CommonAdapter<ImageFloder>(context, mDatas,
                R.layout.imageloade_list_dir_item)
        {
            @Override
            public void convert(ViewHolder helper, ImageFloder item)
            {
                helper.setText(R.id.id_dir_item_name, item.getName());
                helper.setImageByUrl(R.id.id_dir_item_image,
                        item.getFirstImagePath());
//                helper.setText(R.id.id_dir_item_count, item.getCount()
//                        + context.getString(R.string.page));
            }
        });
    }
    
    public interface OnImageDirSelected
    {
        void selected(ImageFloder floder);
    }
    
    private OnImageDirSelected mImageDirSelected;
    
    public void setOnImageDirSelected(OnImageDirSelected mImageDirSelected)
    {
        this.mImageDirSelected = mImageDirSelected;
    }
    
    @Override
    public void initEvents()
    {
        mListDir.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id)
            {
                
                if (mImageDirSelected != null)
                {
                    mImageDirSelected.selected(mDatas.get(position));
                }
            }
        });
    }
    
    @Override
    public void init()
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    protected void beforeInitWeNeedSomeParams(Object... params)
    {
        // TODO Auto-generated method stub
    }
    
}
