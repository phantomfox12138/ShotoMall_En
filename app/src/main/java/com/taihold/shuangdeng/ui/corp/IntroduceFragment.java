package com.taihold.shuangdeng.ui.corp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taihold.shuangdeng.R;
import com.taihold.shuangdeng.common.FusionCode;
import com.taihold.shuangdeng.sliderlibrary.Animations.DescriptionAnimation;
import com.taihold.shuangdeng.sliderlibrary.SliderLayout;
import com.taihold.shuangdeng.sliderlibrary.SliderTypes.BaseSliderView;
import com.taihold.shuangdeng.sliderlibrary.SliderTypes.TextSliderView;
import com.taihold.shuangdeng.ui.HomePagerAdapter;
import com.taihold.shuangdeng.ui.home.HomePageActivity;

import java.util.HashMap;

import static com.taihold.shuangdeng.common.FusionMessageType.REQUEST_GET_COMPANY_INTRODUCE_SUCCESSED;

public class IntroduceFragment extends Fragment
{
    
    /**
     * DEBUG_TAG
     */
    private static final String TAG = "IntroduceFragment";
    
    private View mRootView;
    
    private SliderLayout mIntroduceSlider;
    
    private TextView mCorpIntroduceTextView;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        
        if (mRootView == null)
        {
            mRootView = inflater.inflate(R.layout.fragment_introduce, null);
            initView();
        }
        
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null)
        {
            parent.removeView(mRootView);
        }
        
        return mRootView;
    }
    
    private void initSlider()
    {
        // initialize a SliderLayout
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
//        file_maps.put(getString(R.string.corp_welcome_title_01),
//                R.mipmap.group_img01);
//        file_maps.put(getString(R.string.corp_welcome_title_02),
//                R.mipmap.group_img02);
//        file_maps.put(getString(R.string.corp_welcome_title_03),
//                R.mipmap.group_img03);
        //        file_maps.put("Game of Thrones", R.mipmap.game_of_thrones);
        
        for (String name : file_maps.keySet())
        {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            
            textSliderView.description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            
            mIntroduceSlider.addSlider(textSliderView);
        }
        
        mIntroduceSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mIntroduceSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mIntroduceSlider.setCustomAnimation(new DescriptionAnimation());
        mIntroduceSlider.setDuration(2000);
        //        mIntroduceSlider.addOnPageChangeListener(this);
    }
    
    private void initView()
    {
        
        mIntroduceSlider = (SliderLayout) mRootView.findViewById(R.id.introduce_slider);
        mCorpIntroduceTextView = (TextView) mRootView.findViewById(R.id.corp_introduce_content);
        
        initSlider();
        
    }
    
    public void setCorpIntroduce(String introduce)
    {
        
        mCorpIntroduceTextView.setText(Html.fromHtml(introduce));
        
    }
    
}
