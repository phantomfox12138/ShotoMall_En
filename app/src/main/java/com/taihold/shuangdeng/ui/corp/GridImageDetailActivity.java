package com.taihold.shuangdeng.ui.corp;

import android.os.Bundle;
import android.util.Log;

import com.taihold.shuangdeng.R;
import com.taihold.shuangdeng.common.FusionAction;
import com.taihold.shuangdeng.common.HttpHelper;
import com.taihold.shuangdeng.freamwork.ui.BasicActivity;
import com.taihold.shuangdeng.sliderlibrary.Animations.DescriptionAnimation;
import com.taihold.shuangdeng.sliderlibrary.SliderLayout;
import com.taihold.shuangdeng.sliderlibrary.SliderTypes.BaseSliderView;
import com.taihold.shuangdeng.sliderlibrary.SliderTypes.TextSliderView;

/**
 * Created by niufan on 17/5/8.
 */

public class GridImageDetailActivity extends BasicActivity
{
    private SliderLayout mImagePager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.image_detail_layout);
        
        initView();
    }
    
    private void initView()
    {
        mImagePager = (SliderLayout) findViewById(R.id.image_detail_pager);
        
        mImagePager.setPresetTransformer(SliderLayout.Transformer.Default);
        mImagePager.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mImagePager.setCustomAnimation(new DescriptionAnimation());
        mImagePager.stopAutoCycle();
        
        String[] imageUrls = getIntent().getStringArrayExtra(FusionAction.GRID_IMAGE_DETAIL_EXTRA.IMAGE_URLS);
        String title = getIntent().getStringExtra(FusionAction.GRID_IMAGE_DETAIL_EXTRA.TITLE);
        
        for (int i = 0; i < imageUrls.length; i++)
        {
            TextSliderView textSliderView = new TextSliderView(this);
            
            textSliderView.description(title)
                    .image(HttpHelper.IMAGE_HEAD_URL + imageUrls[i])
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            mImagePager.addSlider(textSliderView);
        }
        
    }
}
