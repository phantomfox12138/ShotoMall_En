package com.taihold.shuangdeng.ui.news;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taihold.shuangdeng.R;
import com.taihold.shuangdeng.logic.model.NewsDbModel;
import com.taihold.shuangdeng.logic.model.SliderDbModel;
import com.taihold.shuangdeng.sliderlibrary.SliderTypes.BaseSliderView;
import com.taihold.shuangdeng.sliderlibrary.SliderTypes.TextSliderView;
import com.taihold.shuangdeng.ui.home.HomePageActivity;

public class NewsFragment extends Fragment
{
    private View mRootView;
    
    private TabLayout mNewsTab;
    
    private ViewPager mNewsPager;
    
    private List<SubNewsFragment> fragmentList = new ArrayList<SubNewsFragment>();
    
    private NewsPageAdapter adapter;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        if (null == mRootView)
        {
            mRootView = inflater.inflate(R.layout.fragment_news,
                    container,
                    false);
            initView();
        }
        
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null)
        {
            parent.removeView(mRootView);
        }
        
        return mRootView;
    }
    
    private void initView()
    {
        mNewsTab = (TabLayout) mRootView.findViewById(R.id.news_tablayout);
        mNewsPager = (ViewPager) mRootView.findViewById(R.id.news_pager);
        
        for (int i = 0; i < 3; i++)
            mNewsTab.addTab(mNewsTab.newTab());
        
        for (int i = 0; i < 3; i++)
        {
            SubNewsFragment subNews = new SubNewsFragment();
            subNews.setPosition(i);
            
            fragmentList.add(subNews);
        }
        
        adapter = new NewsPageAdapter(getActivity().getSupportFragmentManager());
        
        adapter.setFragmentList(fragmentList);
        
        mNewsPager.setAdapter(adapter);
        
        mNewsTab.setupWithViewPager(mNewsPager);
        
        mNewsPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels)
            {
                
            }
            
            @Override
            public void onPageSelected(int position)
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
            
            @Override
            public void onPageScrollStateChanged(int state)
            {
                
            }
        });
    }
    
    class NewsPageAdapter extends FragmentPagerAdapter
    {
        private List<SubNewsFragment> fragmentList;
        
        public NewsPageAdapter(FragmentManager fm)
        {
            super(fm);
        }
        
        public void setFragmentList(List<SubNewsFragment> fragmentList)
        {
            this.fragmentList = fragmentList;
        }
        
        @Override
        public CharSequence getPageTitle(int position)
        {
//            switch (position)
//            {
//                case 0:
//                    return getString(R.string.news_tab_company);
//
//                case 1:
//                    return getString(R.string.news_tab_media_report);
//
//                case 2:
//                    return getString(R.string.news_tab_exhibition_trailer);
//
//                default:
                    return "其他";
//            }
        }
        
        @Override
        public Fragment getItem(int position)
        {
            return fragmentList.get(position);
        }
        
        @Override
        public int getCount()
        {
            return null == fragmentList || fragmentList.size() == 0 ? 0
                    : fragmentList.size();
        }
    }
    
    public void setImageMap(List<SliderDbModel> list)
    {
        fragmentList.get(0).setmImageMaps(list);
    }
    
    public void setCorpNewsList(String type, List<NewsDbModel> newsList)
    {
        if (type.equals("news"))
        {
            fragmentList.get(0).setNewsData(newsList);
            fragmentList.get(0).notifyDataSetChanged();
        }
        if (type.equals("media"))
        {
            fragmentList.get(1).setNewsData(newsList);
            fragmentList.get(1).notifyDataSetChanged();
        }
        if (type.equals("exhibition"))
        {
            fragmentList.get(2).setNewsData(newsList);
            fragmentList.get(2).notifyDataSetChanged();
        }
        adapter.notifyDataSetChanged();
    }
    
    public void setRefreshing(String type, boolean flag)
    {
        if (type.equals("news"))
        {
            fragmentList.get(0).setRefreshing(flag);
        }
        if (type.equals("media"))
        {
            fragmentList.get(1).setRefreshing(flag);
        }
        if (type.equals("exhibition"))
        {
            fragmentList.get(2).setRefreshing(flag);
        }
        
    }
    
    public boolean isRefreshing(String type)
    {
        if (type.equals("news"))
        {
            return fragmentList.get(0).isRefreshing();
        }
        if (type.equals("media"))
        {
            return fragmentList.get(1).isRefreshing();
        }
        if (type.equals("exhibition"))
        {
            return fragmentList.get(2).isRefreshing();
        }
        
        return false;
    }
    
}
