package com.taihold.shuangdeng.ui.corp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.taihold.shuangdeng.R;
import com.taihold.shuangdeng.logic.model.CorpQualityDbModel;

public class CorpQualityFragment extends Fragment
{
    private View mRootView;
    
    private RecyclerView mQualityGrid;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        if (null == mRootView)
        {
            mRootView = inflater.inflate(R.layout.fragment_corp_quality,
                    container,
                    false);
        }
        
        initView();
        
        return mRootView;
    }
    
    private void initView()
    {
        mQualityGrid = (RecyclerView) mRootView.findViewById(R.id.corp_quality_grid);
        
        mQualityGrid.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        
        List<CorpQualityDbModel> data = new ArrayList<CorpQualityDbModel>();
        
        initList(data);
        
        CorpQualityAdapter adapter = new CorpQualityAdapter(getActivity());
        adapter.setDataList(data);
        
        mQualityGrid.setAdapter(adapter);
    }
    
    class CorpQualityAdapter extends
            RecyclerView.Adapter<CorpQualityFragment.Holder>
    {
        private Context context;
        
        private List<CorpQualityDbModel> dataList;
        
        public CorpQualityAdapter(Context context)
        {
            this.context = context;
        }
        
        public void setDataList(List<CorpQualityDbModel> dataList)
        {
            this.dataList = dataList;
        }
        
        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            Holder holder = new Holder(LayoutInflater.from(context)
                    .inflate(R.layout.corp_quality_item, null));
            return holder;
        }
        
        @Override
        public void onBindViewHolder(final Holder holder, final int position)
        {
            holder.name.setText(dataList.get(position).getName());
            holder.icon.setImageResource(dataList.get(position).getIconId());
            
            holder.icon.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(context,
                            ImageDetailActivity.class);
                    intent.putExtra("item_image", dataList.get(position)
                            .getIconId());
                    
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                            holder.icon,
                            "robot");
                    context.startActivity(intent, options.toBundle());
                }
            });
        }
        
        @Override
        public int getItemCount()
        {
            return null == dataList || dataList.size() == 0 ? 0
                    : dataList.size();
        }
        
    }
    
    class Holder extends RecyclerView.ViewHolder
    {
        ImageView icon;
        
        TextView name;
        
        public Holder(View itemView)
        {
            super(itemView);
            
            icon = (ImageView) itemView.findViewById(R.id.corp_quality_icon);
            name = (TextView) itemView.findViewById(R.id.corp_quality_name);
        }
        
    }
    
    private void initList(List<CorpQualityDbModel> data)
    {
        CorpQualityDbModel model1 = new CorpQualityDbModel();
        model1.setIconId(R.mipmap.a_01);
        data.add(model1);
        
        CorpQualityDbModel model2 = new CorpQualityDbModel();
        model2.setIconId(R.mipmap.a_02);
        data.add(model2);
        
        CorpQualityDbModel model3 = new CorpQualityDbModel();
        model3.setIconId(R.mipmap.a_03);
        data.add(model3);
        
        CorpQualityDbModel model4 = new CorpQualityDbModel();
        model4.setIconId(R.mipmap.a_04);
        data.add(model4);
        
        CorpQualityDbModel model5 = new CorpQualityDbModel();
        model5.setIconId(R.mipmap.a_05);
        data.add(model5);
        
        CorpQualityDbModel model6 = new CorpQualityDbModel();
        model6.setIconId(R.mipmap.a_06);
        data.add(model6);
        
        CorpQualityDbModel model7 = new CorpQualityDbModel();
        model7.setIconId(R.mipmap.a_07);
        data.add(model7);
        
        CorpQualityDbModel model8 = new CorpQualityDbModel();
        model8.setIconId(R.mipmap.a_08);
        data.add(model8);
        
        CorpQualityDbModel model9 = new CorpQualityDbModel();
        model9.setIconId(R.mipmap.a_09);
        data.add(model9);
        
        CorpQualityDbModel model10 = new CorpQualityDbModel();
        model10.setIconId(R.mipmap.a_10);
        data.add(model10);
        
        CorpQualityDbModel model11 = new CorpQualityDbModel();
        model11.setIconId(R.mipmap.a_11);
        data.add(model11);
        
        CorpQualityDbModel model12 = new CorpQualityDbModel();
        model12.setIconId(R.mipmap.d_01);
        data.add(model12);
        
        CorpQualityDbModel model13 = new CorpQualityDbModel();
        model13.setIconId(R.mipmap.d_02);
        data.add(model13);
        
        CorpQualityDbModel model14 = new CorpQualityDbModel();
        model14.setIconId(R.mipmap.d_03);
        data.add(model14);
        
        CorpQualityDbModel model15 = new CorpQualityDbModel();
        model15.setIconId(R.mipmap.d_04);
        data.add(model15);
        
        CorpQualityDbModel model16 = new CorpQualityDbModel();
        model16.setIconId(R.mipmap.d_05);
        data.add(model16);
        
        CorpQualityDbModel model17 = new CorpQualityDbModel();
        model17.setIconId(R.mipmap.d_06);
        data.add(model17);
        
        CorpQualityDbModel model18 = new CorpQualityDbModel();
        model18.setIconId(R.mipmap.d_07);
        data.add(model18);
        
        CorpQualityDbModel model19 = new CorpQualityDbModel();
        model19.setIconId(R.mipmap.d_08);
        data.add(model19);
        
        CorpQualityDbModel model20 = new CorpQualityDbModel();
        model20.setIconId(R.mipmap.d_09);
        data.add(model20);
        
        CorpQualityDbModel model21 = new CorpQualityDbModel();
        model21.setIconId(R.mipmap.d_10);
        data.add(model12);
        
        CorpQualityDbModel model22 = new CorpQualityDbModel();
        model22.setIconId(R.mipmap.d_11);
        data.add(model22);
        
        CorpQualityDbModel model23 = new CorpQualityDbModel();
        model23.setIconId(R.mipmap.d_12);
        data.add(model23);
        
        CorpQualityDbModel model24 = new CorpQualityDbModel();
        model24.setIconId(R.mipmap.d_13);
        data.add(model24);
        
        CorpQualityDbModel model25 = new CorpQualityDbModel();
        model25.setIconId(R.mipmap.d_01_j);
        data.add(model25);
    }
    
}
