package com.houzhi.slidelistener.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.houzhi.slidelistener.R;
import com.houzhi.slidelistener.demo.fragment.BaseSlideFinishFragment;
import com.houzhi.slidelistener.demo.fragment.DemoSlideBottomFinishScrollViewFragment;
import com.houzhi.slidelistener.demo.fragment.DemoSlideHFinishFragment;
import com.houzhi.slidelistener.demo.fragment.DemoSlideTopFinishButtonFragment;
import com.houzhi.slidelistener.demo.fragment.DemoSlideLeftFinishViewPagerFragment;
import com.houzhi.slidelistener.demo.fragment.DemoSlideRightFinishHScrollViewFragment;
import com.houzhi.slidelistener.demo.fragment.DemoSlideTopFinishFragment;
import com.houzhi.slidelistener.widget.DirectionSlideListenerLayout;

public class MainActivity extends FragmentActivity implements BaseSlideFinishFragment.SlideFragmentFinishListener {
    FragmentManager fragmentManager = null;
    DirectionSlideListenerLayout slideLayout = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = getLayoutInflater().inflate(R.layout.activity_main, null);

        slideLayout = new DirectionSlideListenerLayout(this);
        slideLayout.addView(view);
        slideLayout.setDirection(DirectionSlideListenerLayout.SlideDirection.RIGHT);
        setContentView(slideLayout);

        slideLayout.setOnSlideListener(new DirectionSlideListenerLayout.OnDirectionSlideListener() {

            @Override
            public void onDirectionSlide() {
                Toast.makeText(MainActivity.this,"Activity Finish",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        slideLayout.setOnlyBorderValid(true);
        slideLayout.setEnableListenerSlide(false);
        initFragment();

    }


    private void initFragment() {

        fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();


        transaction.add(R.id.content, DemoSlideHFinishFragment.newInstance(Color.RED), "HORIZONTAL");
        transaction.add(R.id.content, DemoSlideTopFinishButtonFragment.newInstance(Color.GREEN), "LEFT");
//        transaction.add(R.id.content, DemoSlideTopFinishFragment.newInstance(Color.YELLOW), "TOP");
        transaction.add(R.id.content, DemoSlideBottomFinishScrollViewFragment.newInstance(Color.MAGENTA), "BOTTOM");

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

        if(fragmentManager.getBackStackEntryCount() == 0){
            slideLayout.setEnableListenerSlide(true);
        }
    }

}
