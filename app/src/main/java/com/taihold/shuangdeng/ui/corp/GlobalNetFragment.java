package com.taihold.shuangdeng.ui.corp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.taihold.shuangdeng.R;
import com.taihold.shuangdeng.common.FusionAction;

public class GlobalNetFragment extends Fragment
{
    private static final String TAG = "GlobalNetFragment";
    
    private View mRootView;
    
    private ListView mGlobalList;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        if (null == mRootView)
        {
            mRootView = inflater.inflate(R.layout.fragment_global_net,
                    container,
                    false);
        }
        
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null)
        {
            parent.removeView(mRootView);
        }
        
        initView();
        
        return mRootView;
    }
    
    private void initView()
    {
        mGlobalList = (ListView) mRootView.findViewById(R.id.global_list);
        
//        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
//                R.array.global_network_company_location,
//                R.layout.global_net_list_item);
        
//        mGlobalList.setAdapter(adapter);
        
        mGlobalList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id)
            {
                Intent toCorpDetail = new Intent(
                        FusionAction.CORP_DETAIL_ACTION);
                
                String itemName = ((TextView) (view.findViewById(android.R.id.text1))).getText()
                        .toString();
                
                toCorpDetail.putExtra(FusionAction.CORP_DETAIL_EXTRA.POSITION,
                        position);
                toCorpDetail.putExtra(FusionAction.CORP_DETAIL_EXTRA.CORP_NAME,
                        itemName);
                
                startActivity(toCorpDetail);
            }
        });
        
    }
}
