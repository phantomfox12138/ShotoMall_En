package com.taihold.shuangdeng.ui.corp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taihold.shuangdeng.R;

public class CultureFragment extends Fragment
{
    
    private View mRootView;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        if (mRootView == null)
        {
            
            mRootView = inflater.inflate(R.layout.fragment_culture,
                    container,
                    false);
        }
        
        return mRootView;
    }
    
}
