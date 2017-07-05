package com.taihold.shuangdeng.ui.corp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.taihold.shuangdeng.R;
import com.taihold.shuangdeng.common.FusionAction;
import com.taihold.shuangdeng.freamwork.ui.BasicActivity;

/**
 * Created by niufan on 17/5/15.
 */

public class CorpDetailActivity extends BasicActivity
{
    private Toolbar mToolbar;
    
    private TextView mContractName;
    
    private TextView mContractTele;
    
    private TextView mContractFax;
    
    private TextView mContractMobile;
    
    private TextView mContractEmail;
    
    private TextView mContractAddress;
    
    private String mCorpName;
    
    private int mPosition;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.corp_detail_layout);
        
        initData();
        
        initView();
    }
    
    private void initView()
    {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mContractName = (TextView) findViewById(R.id.contract_name);
//        mContractTele = (TextView) findViewById(R.id.contract_tel);
//        mContractFax = (TextView) findViewById(R.id.contract_fax);
//        mContractMobile = (TextView) findViewById(R.id.contract_mobile);
//        mContractEmail = (TextView) findViewById(R.id.contract_email);
//        mContractAddress = (TextView) findViewById(R.id.contract_add);
        
        MaterialMenuDrawable materialMenu = new MaterialMenuDrawable(this,
                Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
        
        materialMenu.setTransformationDuration(500);
        materialMenu.animateIconState(MaterialMenuDrawable.IconState.ARROW);
        
        mToolbar.setNavigationIcon(materialMenu);
        mToolbar.setTitle(mCorpName);
        
        mToolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        
        if (mPosition > 31)
        {
//            String[] worldCorpDetails = getResources().getStringArray(R.array.world_network_company_detail);
//            String corpDetail = worldCorpDetails[mPosition - 32];
//            String[] detail = corpDetail.split("\\|");
            
//            if (mPosition == 5 || mPosition == 9)
//            {
//                mContractAddress.setVisibility(View.GONE);
//                for (int i = 0; i < detail.length; i++)
//                {
//                    switch (i)
//                    {
//                        case 0:
//                            mContractName.setText(detail[0]);
//                            break;
//                        case 1:
//                            mContractTele.setText(detail[1]);
//                            break;
//                        case 2:
//                            mContractFax.setText(detail[2]);
//                            break;
//                        case 3:
//                            mContractMobile.setText(detail[3]);
//
//                            break;
//                        case 4:
//                            mContractEmail.setText(detail[4]);
//                            break;
//
//                    }
//                }
//            }
            
            mContractFax.setVisibility(View.GONE);
            mContractMobile.setVisibility(View.GONE);
            
//            for (int i = 0; i < detail.length; i++)
//            {
//                switch (i)
//                {
//                    case 0:
//                        mContractName.setText(detail[0]);
//                        break;
//                    case 1:
//                        mContractTele.setText(detail[1]);
//                        break;
//                    case 2:
//                        mContractEmail.setText(detail[2]);
//                        break;
//                    case 3:
//                        mContractAddress.setText(detail[3]);
//                        break;
//
//                }
//            }
            
        }
        else
        {
//            String[] chinaCorpDetails = getResources().getStringArray(R.array.global_network_company_detail);
//
//            mContractName.setVisibility(View.GONE);
//
//            String corpDetail = chinaCorpDetails[mPosition];
//            String[] detail = corpDetail.split("\\|");
//
//            if (detail.length < 5)
//            {
//                if (detail.length == 3)
//                {
//                    mContractName.setVisibility(View.VISIBLE);
//                    for (int i = 0; i < detail.length; i++)
//                    {
//                        if (mPosition == 31)
//                        {
//                            switch (i)
//                            {
//                                case 0:
//                                    mContractName.setText(detail[0]);
//                                    break;
//                                case 1:
//                                    mContractTele.setText(detail[1]);
//                                    break;
//                                case 2:
//                                    mContractEmail.setText(detail[2]);
//                                    break;
//                                case 3:
//                                    mContractAddress.setText(detail[3]);
//                                    break;
//
//                            }
//                        }
//                    }
//                    return;
//                }
                
//                for (int i = 0; i < detail.length; i++)
//                {
//                    if (mPosition == 6 || mPosition == 19 || mPosition == 26
//                            || mPosition == 30)
//                    {
//                        switch (i)
//                        {
//                            case 0:
//                                mContractMobile.setText(detail[0]);
//                                break;
//                            case 1:
//                                mContractEmail.setText(detail[1]);
//                                break;
//                            case 2:
//                                mContractAddress.setText(detail[2]);
//                                break;
//                        }
//                    }
//
//                }
//            }
//
//            for (int i = 0; i < detail.length; i++)
//            {
//                switch (i)
//                {
//                    case 0:
//                        mContractTele.setText(detail[0]);
//                        break;
//                    case 1:
//                        mContractFax.setText(detail[1]);
//                        break;
//                    case 2:
//                        mContractMobile.setText(detail[2]);
//                        break;
//                    case 3:
//                        mContractEmail.setText(detail[3]);
//                        break;
//                    case 4:
//                        mContractAddress.setText(detail[4]);
//                        break;
//
//                }
//            }
        }
        
    }
    
    private void initData()
    {
        mCorpName = getIntent().getStringExtra(FusionAction.CORP_DETAIL_EXTRA.CORP_NAME);
        mPosition = getIntent().getIntExtra(FusionAction.CORP_DETAIL_EXTRA.POSITION,
                -1);
    }
}
