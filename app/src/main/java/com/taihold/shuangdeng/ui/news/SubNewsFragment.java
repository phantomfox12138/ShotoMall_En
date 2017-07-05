package com.taihold.shuangdeng.ui.news;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.taihold.shuangdeng.ui.home.HomeFragment;
import com.taihold.shuangdeng.ui.home.HomePageActivity;
import com.taihold.shuangdeng.ui.home.NewsListAdapter;
import com.taihold.shuangdeng.ui.home.NewsListHolder;
import com.taihold.shuangdeng.util.StringUtil;

public class SubNewsFragment extends Fragment implements
        BaseSliderView.OnSliderClickListener
{
    
    private RecyclerView mNewsList;
    
    private NewsListAdapter mNewsListAdapter;
    
    private List<NewsDbModel> mNewsData = new ArrayList<NewsDbModel>();
    
    private SliderLayout mNewsSlider;
    
    private Toolbar mHomeToolbar;
    
    private AppBarLayout mAppbarLayout;
    
    private SwipeRefreshLayout mRefreshLayout;
    
    private int position;
    
    private View mTopMenu;
    
    private List<SliderDbModel> mSliderDataList = new ArrayList<SliderDbModel>();
    
    private enum CollapsingToolbarLayoutState
    {
        EXPANDED, COLLAPSED, INTERNEDIATE
    };
    
    public int getPosition()
    {
        return position;
    }
    
    public void setPosition(int position)
    {
        this.position = position;
    }
    
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
    
    private SubNewsFragment.CollapsingToolbarLayoutState mState;
    
    private View mRootView;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        
        //        Utility.setTranslucent(this);
        //        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        if (mRootView == null)
        {
            mRootView = inflater.inflate(R.layout.fragment_subnews_layout, null);
            initView();
        }
        
        //        initData();
        
        return mRootView;
    }
    
    private void initView()
    {
        mNewsList = (RecyclerView) mRootView.findViewById(R.id.home_list);
        mNewsSlider = (SliderLayout) mRootView.findViewById(R.id.home_slider);
        mHomeToolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        mAppbarLayout = (AppBarLayout) mRootView.findViewById(R.id.appbar_layout);
        mRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.refresh_layout);
        mTopMenu = mRootView.findViewById(R.id.top_menu);
        
        mTopMenu.setVisibility(View.GONE);
        
        mNewsSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mNewsSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mNewsSlider.setCustomAnimation(new DescriptionAnimation());
        mNewsSlider.setDuration(2000);
        //        mNewsSlider.addOnPageChangeListener(this);
        
        if (position == 0)
        {
            mNewsSlider.setVisibility(View.VISIBLE);
            mAppbarLayout.setLayoutParams(new CoordinatorLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 500));
        }
        else
        {
            mNewsSlider.setVisibility(View.GONE);
            mAppbarLayout.setLayoutParams(new CoordinatorLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 0));
        }
        
        mNewsListAdapter = new NewsListAdapter(getActivity());
        
        mNewsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        //        mNewsList.addItemDecoration(new DividerItemDecoration(getActivity(),
        //                LinearLayoutManager.HORIZONTAL));
        
        mNewsListAdapter.setHomeListData(mNewsData);
        mNewsListAdapter.setFootFlag();
        
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mRefreshLayout.setRefreshing(true);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                switch (position)
                {
                    case 0:
                        HomePageActivity.mHomeLogic.getNewsList(28, "news");
                        break;
                    
                    case 1:
                        HomePageActivity.mHomeLogic.getNewsList(29, "media");
                        break;
                    
                    case 2:
                        HomePageActivity.mHomeLogic.getNewsList(30,
                                "exhibition");
                        break;
                }
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
        
        mNewsList.setAdapter(mNewsListAdapter);
        
        mNewsListAdapter.setOnItemClickListener(new NewsListAdapter.OnItemClickLitener()
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
                        mNewsData.get(position).getTitle());
                startActivity(toWeb);
            }
            
            @Override
            public void onItemLongClick(NewsListHolder holder, int position)
            {
                
            }
        });
    }
    
    public void setmImageMaps(List<SliderDbModel> list)
    {
        mSliderDataList.clear();
        mSliderDataList.addAll(list);
        
        for (int i = 0; i < list.size(); i++)
        {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            
            textSliderView.description(list.get(i).getTitle())
                    .image(list.get(i).getImageUrl())
                    .setOnSliderClickListener(this)
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            
            mNewsSlider.addSlider(textSliderView);
        }
        
    }
    
    public void setNewsData(List<NewsDbModel> list)
    {
        mNewsData.clear();
        mNewsData.addAll(list);
    }
    
    public void notifyDataSetChanged()
    {
        mNewsListAdapter.notifyDataSetChanged();
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
