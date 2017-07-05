package com.taihold.shuangdeng.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by niufan on 17/3/17.
 */

public class HomePagerAdapter extends FragmentPagerAdapter
{
    private List<Fragment> mFragmentList;
    
    public void setFragmentList(List<Fragment> fragmentList)
    {
        this.mFragmentList = fragmentList;
    }
    
    public HomePagerAdapter(FragmentManager fm)
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
