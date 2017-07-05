package com.taihold.shuangdeng.ui.corp;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taihold.shuangdeng.R;
import com.taihold.shuangdeng.logic.home.HomePageLogic;
import com.taihold.shuangdeng.logic.model.CorpHonoerDbModel;
import com.taihold.shuangdeng.logic.model.DevPathDbModel;
import com.taihold.shuangdeng.ui.home.HomePageActivity;

public class CorpFragment extends Fragment
{
    private View mRootView;
    
    private TabLayout mTablayout;
    
    private ViewPager mCorpContentPager;
    
    private List<Fragment> mFragmentList;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        //注，此处不可重复加载 edit by 牛凡
        if (mRootView == null)
        {
            mRootView = inflater.inflate(R.layout.fragment_corp, null);
            
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
        mTablayout = (TabLayout) mRootView.findViewById(R.id.corp_tablayout);
        mCorpContentPager = (ViewPager) mRootView.findViewById(R.id.corp_content_pager);
        
        //注,TabLayout.setupWithViewPager()会清空所有table重新通过PagerAdapter.getPageTitle()方法重新创建
        //故，此处设置文字没卵用
        
        for (int i = 0; i < 8; i++)
            mTablayout.addTab(mTablayout.newTab());
        
        CorpPagerAdapter adapter = new CorpPagerAdapter(
                getActivity().getSupportFragmentManager());
        
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new IntroduceFragment());
        mFragmentList.add(new SocialRespFragment());
        mFragmentList.add(new CultureFragment());
        mFragmentList.add(new GlobalNetFragment());
        mFragmentList.add(new DevelopPathFragment());
        mFragmentList.add(new CorpHonorFragment());
        mFragmentList.add(new CorpQualityFragment());
        
        adapter.setFragmentList(mFragmentList);
        
        mCorpContentPager.setAdapter(adapter);
        
        mTablayout.setupWithViewPager(mCorpContentPager);
        
    }
    
    class CorpPagerAdapter extends FragmentPagerAdapter
    {
        private List<Fragment> mFragmentList;
        
        @Override
        public CharSequence getPageTitle(int position)
        {
//            switch (position)
//            {
//                case 0:
//
//                    return getString(R.string.corp_tab_introduction);
//
//                case 1:
//
//                    return getString(R.string.corp_tab_social_responsibility);
//
//                case 2:
//
//                    return getString(R.string.corp_tab_corp_culture);
//
//                case 3:
//
//                    return getString(R.string.corp_tab_global_net);
//
//                case 4:
//
//                    return getString(R.string.corp_tab_develop_path);
//
//                case 5:
//
//                    return getString(R.string.corp_tab_corp_honoer);
//
//                case 6:
//
//                    return getString(R.string.corp_tab_quality);
//
//                case 7:
//
//                    return getString(R.string.corp_tab_about_us);
//
//                default:
//
                    return "其他";
//
//            }
        }
        
        public void setFragmentList(List<Fragment> fragmentList)
        {
            this.mFragmentList = fragmentList;
        }
        
        public CorpPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }
        
        @Override
        public Fragment getItem(int position)
        {
            return mFragmentList.get(position);
        }
        
        @Override
        public int getCount()
        {
            return mFragmentList == null || mFragmentList.size() == 0 ? 0
                    : mFragmentList.size();
        }
    }
    
    public void setCorpIntroduce(String introduce)
    {
        ((IntroduceFragment) mFragmentList.get(0)).setCorpIntroduce(introduce);
    }
    
    public void setDevPathList(List<DevPathDbModel> list)
    {
        ((DevelopPathFragment) mFragmentList.get(4)).setDevPathList(list);
    }
    
    public List<DevPathDbModel> getDevPathList()
    {
        return ((DevelopPathFragment) mFragmentList.get(4)).getDevPathList();
    }
    
    public void notifyDataSetChanged()
    {
        ((DevelopPathFragment) (mFragmentList.get(4))).notifyDataSetChanged();
    }
    
    public void setHonoerList(List<CorpHonoerDbModel> list)
    {
        ((CorpHonorFragment) (mFragmentList.get(5))).setHomoerList(list);
    }
    
}
