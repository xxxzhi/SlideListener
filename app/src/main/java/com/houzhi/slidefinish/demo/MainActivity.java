package com.houzhi.slidefinish.demo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.houzhi.slidefinish.R;
import com.houzhi.slidefinish.demo.fragment.BaseSlideFinishFragment;
import com.houzhi.slidefinish.demo.fragment.DemoSlideBottomFinishScrollViewFragment;
import com.houzhi.slidefinish.demo.fragment.DemoSlideHFinishFragment;
import com.houzhi.slidefinish.demo.fragment.DemoSlideLeftFinishButtonFragment;
import com.houzhi.slidefinish.demo.fragment.DemoSlideLeftFinishViewPagerFragment;
import com.houzhi.slidefinish.demo.fragment.DemoSlideRightFinishHScrollViewFragment;
import com.houzhi.slidefinish.demo.fragment.DemoSlideTopFinishFragment;
import com.houzhi.slidefinish.widget.SlideLayout;

public class MainActivity extends FragmentActivity implements BaseSlideFinishFragment.SlideFragmentFinishListener {
    FragmentManager fragmentManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = getLayoutInflater().inflate(R.layout.activity_main, null);

        SlideLayout slideLayout = new SlideLayout(this);
        slideLayout.addView(view);
        slideLayout.setDirection(SlideLayout.SlideDirection.RIGHT);
        setContentView(slideLayout);

        slideLayout.setOnSlideListener(new SlideLayout.OnSlideListener() {

            @Override
            public void onSlideFinish() {
                finish();
            }
        });


        initFragment();

    }


    private void initFragment() {

        fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();


        transaction.add(R.id.content, DemoSlideHFinishFragment.newInstance(Color.RED), "HORIZONTAL");


        transaction.add(R.id.content, DemoSlideTopFinishFragment.newInstance(Color.YELLOW), "TOP");
        transaction.add(R.id.content, DemoSlideBottomFinishScrollViewFragment.newInstance(Color.MAGENTA), "BOTTOM");
        transaction.add(R.id.content, DemoSlideLeftFinishButtonFragment.newInstance(Color.GREEN), "LEFT");
        transaction.add(R.id.content, DemoSlideLeftFinishViewPagerFragment.newInstance(Color.parseColor("#A6A6A6")), "DemoSlideLeftFinishViewPagerFragment");
        transaction.add(R.id.content, DemoSlideRightFinishHScrollViewFragment.newInstance(Color.BLUE), "RIGHT");


        transaction.commit();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("Activity", "onTouchEvent--" + event.getAction());
        return super.onTouchEvent(event);
    }

    @Override
    public void onSlideFragmentFinish(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.remove(fragment);

        transaction.commit();
    }

}
