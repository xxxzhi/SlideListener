package com.houzhi.slidefinish.demo;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.houzhi.slidefinish.R;
import com.houzhi.slidefinish.demo.fragment.BlankFragment;
import com.houzhi.slidefinish.widget.SlideFrameLayout;

public class TestViewPagerActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_test_view_pager, null);

        ViewPager vPager = (ViewPager)view.findViewById(R.id.vp);
        vPager.setAdapter(new TestPagerAdapter(getSupportFragmentManager()));


        SlideFrameLayout slideFrameLayout = new SlideFrameLayout(this);
        slideFrameLayout.addView(view);
        slideFrameLayout.setDirection(SlideFrameLayout.SlideDirection.RIGHT);
        setContentView(slideFrameLayout);

        slideFrameLayout.setOnSlideListener(new SlideFrameLayout.OnSlideListener() {

            @Override
            public void onSlideFinish() {
                finish();
            }
        });
    }


    static class TestPagerAdapter extends FragmentPagerAdapter{

        public TestPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new BlankFragment();
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
