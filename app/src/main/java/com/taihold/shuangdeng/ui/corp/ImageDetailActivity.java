package com.taihold.shuangdeng.ui.corp;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.squareup.picasso.Picasso;
import com.taihold.shuangdeng.R;

public class ImageDetailActivity extends AppCompatActivity
{
    // View name of the header image. Used for activity scene transitions
    public static final String VIEW_NAME_HEADER_IMAGE = "detail:header:image";
    
    private ImageView mImageView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        
        mImageView = (ImageView) findViewById(R.id.image_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        
        setSupportActionBar(toolbar);
        
        MaterialMenuDrawable mMaterialMenu = new MaterialMenuDrawable(this,
                Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
        
        mMaterialMenu.setTransformationDuration(500);
        mMaterialMenu.animateIconState(MaterialMenuDrawable.IconState.ARROW);
        toolbar.setNavigationIcon(mMaterialMenu);
        
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        
        //        mImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(),
        //                getIntent().getIntExtra("item_image", 0)));
        
        ViewCompat.setTransitionName(mImageView, VIEW_NAME_HEADER_IMAGE);
        
        mImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        
        loadFullSizeImage();
    }
    
    private void loadFullSizeImage()
    {
        Picasso.with(mImageView.getContext())
                .load(getIntent().getIntExtra("item_image", 0))
                .into(mImageView);
    }
}
