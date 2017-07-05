package com.taihold.shuangdeng.ui.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taihold.shuangdeng.R;
import com.taihold.shuangdeng.common.FusionAction;
import com.taihold.shuangdeng.common.HttpHelper;
import com.taihold.shuangdeng.logic.model.NewsDbModel;
import com.taihold.shuangdeng.logic.model.SliderDbModel;
import com.taihold.shuangdeng.sliderlibrary.SliderLayout;
import com.taihold.shuangdeng.sliderlibrary.Animations.DescriptionAnimation;
import com.taihold.shuangdeng.sliderlibrary.SliderTypes.BaseSliderView;
import com.taihold.shuangdeng.sliderlibrary.SliderTypes.TextSliderView;
import com.taihold.shuangdeng.sliderlibrary.Tricks.ViewPagerEx;

public class HomeFragment extends Fragment implements
        ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener,
        View.OnClickListener
{
    private static final String TAG = "HomeFragment";
    
    private RecyclerView mHomeList;
    
    private SwipeRefreshLayout mRefreshLayout;
    
    private NewsListAdapter mHomeAdapter;
    
    private List<NewsDbModel> mHomeData = new ArrayList<NewsDbModel>();;
    
    private SliderLayout mHomeSlider;
    
    private Toolbar mHomeToolbar;
    
    private AppBarLayout mAppbarLayout;
    
    private List<SliderDbModel> mSliderDataList = new ArrayList<SliderDbModel>();
    
    private View mCommBtn;
    
    private View mEleBtn;
    
    private View mTrifficBtn;
    
    private View mRecycleBtn;
    
    @Override
    public void onSliderClick(BaseSliderView slider, int position)
    {
        Intent toWeb = new Intent(FusionAction.WEB_ACTION);
        toWeb.putExtra(FusionAction.WEB_ATTR.REQEUST, HttpHelper.HEAD_URL
                + HttpHelper.REQUEST_NEWS_DETAIL
                + mSliderDataList.get(position).getId());
        toWeb.putExtra(FusionAction.WEB_ATTR.BUTTON_FLAG, "X");
        toWeb.putExtra(FusionAction.WEB_ATTR.TITLE,
                mSliderDataList.get(position).getTitle());
        startActivity(toWeb);
    }
    
    @Override
    public void onClick(View v)
    {
        Intent toWeb = new Intent(FusionAction.WEB_ACTION);
        toWeb.putExtra(FusionAction.WEB_ATTR.BUTTON_FLAG, "X");
        
        switch (v.getId())
        {
            case R.id.comm_btn:
                toWeb.putExtra(FusionAction.WEB_ATTR.URL,
                        HttpHelper.TO_PRODUCT_LIST);
                toWeb.putExtra(FusionAction.WEB_ATTR.TITLE,
                        getString(R.string.home_comm_scure));
                toWeb.putExtra(FusionAction.WEB_ATTR.VALUE,
                        HttpHelper.ProductGoodsListAttr.PRODUCTTYPE + "=" + 0);
                break;
            
            case R.id.ele_btn:
                
                toWeb.putExtra(FusionAction.WEB_ATTR.URL,
                        HttpHelper.TO_PRODUCT_LIST);
                toWeb.putExtra(FusionAction.WEB_ATTR.TITLE,
                        getString(R.string.home_elec_stroe));
                toWeb.putExtra(FusionAction.WEB_ATTR.VALUE,
                        HttpHelper.ProductGoodsListAttr.PRODUCTTYPE + "=" + 1);
                break;
            
            case R.id.traffic_btn:
                
                toWeb.putExtra(FusionAction.WEB_ATTR.URL,
                        HttpHelper.TO_PRODUCT_LIST);
                toWeb.putExtra(FusionAction.WEB_ATTR.TITLE,
                        getString(R.string.home_traffic_power));
                toWeb.putExtra(FusionAction.WEB_ATTR.VALUE,
                        HttpHelper.ProductGoodsListAttr.PRODUCTTYPE + "=" + 2);
                break;
            
            case R.id.recycle_btn:
                
                toWeb.putExtra(FusionAction.WEB_ATTR.URL,
                        HttpHelper.TO_PRODUCT_LIST);
                toWeb.putExtra(FusionAction.WEB_ATTR.TITLE,
                        getString(R.string.home_recycle_back));
                toWeb.putExtra(FusionAction.WEB_ATTR.VALUE,
                        HttpHelper.ProductGoodsListAttr.PRODUCTTYPE + "=" + 3);
                break;
        }
        
        startActivity(toWeb);
        
    }
    
    private enum CollapsingToolbarLayoutState
    {
        EXPANDED, COLLAPSED, INTERNEDIATE
    };
    
    private CollapsingToolbarLayoutState mState;
    
    private View mRootView;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        
        //        Utility.setTranslucent(this);
        //        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        if (mRootView == null)
        {
            mRootView = inflater.inflate(R.layout.fragment_home, null);
            initView();
        }
        
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null)
        {
            parent.removeView(mRootView);
        }


        initData();
        
        return mRootView;
    }
    
    private void initView()
    {
        mHomeList = (RecyclerView) mRootView.findViewById(R.id.home_list);
        mHomeSlider = (SliderLayout) mRootView.findViewById(R.id.home_slider);
        mHomeToolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        mAppbarLayout = (AppBarLayout) mRootView.findViewById(R.id.appbar_layout);
        mRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.refresh_layout);
        mCommBtn = mRootView.findViewById(R.id.comm_btn);
        mEleBtn = mRootView.findViewById(R.id.ele_btn);
        mTrifficBtn = mRootView.findViewById(R.id.traffic_btn);
        mRecycleBtn = mRootView.findViewById(R.id.recycle_btn);
        
        mCommBtn.setOnClickListener(this);
        mEleBtn.setOnClickListener(this);
        mTrifficBtn.setOnClickListener(this);
        mRecycleBtn.setOnClickListener(this);
        
        mHomeSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mHomeSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mHomeSlider.setCustomAnimation(new DescriptionAnimation());
        mHomeSlider.setDuration(2000);
        mHomeSlider.addOnPageChangeListener(this);
        
        mHomeAdapter = new NewsListAdapter(getActivity());
        
        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mHomeList.setLayoutManager(llm);
        
        mHomeAdapter.setHomeListData(mHomeData);
        mHomeAdapter.setFootFlag();
        
        mHomeAdapter.setOnItemClickListener(new NewsListAdapter.OnItemClickLitener()
        {
            @Override
            public void onItemClick(NewsListHolder holder, int position)
            {
                
                Intent toWeb = new Intent(FusionAction.WEB_ACTION);
                toWeb.putExtra(FusionAction.WEB_ATTR.REQEUST,
                        HttpHelper.HEAD_URL + HttpHelper.REQUEST_NEWS_DETAIL
                                + holder.id);
                toWeb.putExtra(FusionAction.WEB_ATTR.BUTTON_FLAG, "X");
                toWeb.putExtra(FusionAction.WEB_ATTR.TITLE,
                        mHomeData.get(position).getTitle());
                startActivity(toWeb);
            }
            
            @Override
            public void onItemLongClick(NewsListHolder holder, int position)
            {
                
            }
        });
        
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mRefreshLayout.setRefreshing(true);
        
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                HomePageActivity.mHomeLogic.getNewsList(28, "home");
            }
        });
        
        mAppbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener()
        {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout,
                    int verticalOffset)
            {
                if (verticalOffset == 0)
                {
                    //展开状态
                    if (mState != CollapsingToolbarLayoutState.EXPANDED)
                    {
                        mState = CollapsingToolbarLayoutState.EXPANDED;
                        
                        mRefreshLayout.setEnabled(true);
                    }
                }
                //收起状态
                else if (Math.abs(verticalOffset) >= mAppbarLayout.getTotalScrollRange())
                {
                    if (mState != CollapsingToolbarLayoutState.COLLAPSED)
                    {
                        mState = CollapsingToolbarLayoutState.COLLAPSED;
                        
                        mRefreshLayout.setEnabled(false);
                        //                        mCircleMenu.close(true);
                    }
                }
                //中间状态
                else
                {
                    if (mState != CollapsingToolbarLayoutState.INTERNEDIATE)
                    {
                        
                        if (mState == CollapsingToolbarLayoutState.COLLAPSED)
                        {
                            //                            mCircleMenu.close(true);
                        }
                        else
                        {
                            mState = CollapsingToolbarLayoutState.INTERNEDIATE;
                            //                            mCircleMenu.close(true);
                        }
                    }
                }
                
            }
        });
        
        mHomeList.setAdapter(mHomeAdapter);
    }
    
    /**
     * 初始化数据
     */
    private void initData()
    {
    }
    
    @Override
    public void onPageScrolled(int position, float positionOffset,
            int positionOffsetPixels)
    {
        
    }
    
    @Override
    public void onPageSelected(int position)
    {
        
    }
    
    @Override
    public void onPageScrollStateChanged(int state)
    {
        
    }
    
    public void setmImageMaps(List<SliderDbModel> list)
    {
        mSliderDataList.clear();
        mSliderDataList.addAll(list);
        
        for (int i = 0; i < list.size(); i++)
        {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            
            Log.d(TAG, "slider image url = " + list.get(i).getUrl());
            
            textSliderView.description(list.get(i).getTitle())
                    .image(list.get(i).getImageUrl())
                    .setOnSliderClickListener(this)
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setPosition(i);
            textSliderView.setNewsTitle(list.get(i).getTitle());
            
            mHomeSlider.addSlider(textSliderView);
        }
    }
    
    public void setNewsList(List<NewsDbModel> list)
    {
        mHomeData.clear();
        mHomeData.addAll(list);
    }
    
    public void notifyDataSetChanged()
    {
        mHomeAdapter.notifyDataSetChanged();
    }
    
    public void setRefreshing(boolean flag)
    {
        mRefreshLayout.setRefreshing(flag);
    }
    
    public boolean isRefreshing()
    {
        return mRefreshLayout.isRefreshing();
    }
}
