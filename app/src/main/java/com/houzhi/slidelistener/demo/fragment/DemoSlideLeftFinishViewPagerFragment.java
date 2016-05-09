package com.houzhi.slidelistener.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.houzhi.slidelistener.R;
import com.houzhi.slidelistener.widget.DirectionSlideListenerLayout;

public class DemoSlideLeftFinishViewPagerFragment extends BaseDemoSlideFinishFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_view_pager, container, false);
        TextView tv = (TextView) view.findViewById(R.id.tv_hint);
        tv.setText(R.string.slide_left);

        ViewPager vPager = (ViewPager) view.findViewById(R.id.vp);
        vPager.setAdapter(new TestPagerAdapter(getChildFragmentManager()));


        DirectionSlideListenerLayout slideLayout = new DirectionSlideListenerLayout(getActivity());
        slideLayout.addView(view);
        slideLayout.setDirection(DirectionSlideListenerLayout.SlideDirection.LEFT);


        slideLayout.setOnSlideListener(new DirectionSlideListenerLayout.OnDirectionSlideListener() {

            @Override
            public void onDirectionSlide() {
                mOnFragmentWantFinishListener
                        .onSlideFragmentFinish(DemoSlideLeftFinishViewPagerFragment.this);
            }
        });

        slideLayout.setBackgroundColor(bgColor);
        return slideLayout;

    }


    static class TestPagerAdapter extends FragmentPagerAdapter {

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

    public static DemoSlideLeftFinishViewPagerFragment newInstance(int bgColor) {
        DemoSlideLeftFinishViewPagerFragment fragment = new DemoSlideLeftFinishViewPagerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_BG_COLOR, bgColor);
        fragment.setArguments(args);
        return fragment;
    }

    public DemoSlideLeftFinishViewPagerFragment() {
    }

}
