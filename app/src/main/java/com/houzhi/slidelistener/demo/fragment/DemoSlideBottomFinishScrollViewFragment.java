package com.houzhi.slidelistener.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.houzhi.slidelistener.R;
import com.houzhi.slidelistener.widget.DirectionSlideListenerLayout;

public class DemoSlideBottomFinishScrollViewFragment extends BaseDemoSlideFinishFragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scrollview, container, false);
        TextView tv = (TextView) view.findViewById(R.id.tv_hint);
        tv.setText(R.string.slide_bottom);
        DirectionSlideListenerLayout slideLayout = new DirectionSlideListenerLayout(getActivity());
        slideLayout.addView(view);
        slideLayout.setDirection(DirectionSlideListenerLayout.SlideDirection.BOTTOM);

        slideLayout.setOnSlideListener(new DirectionSlideListenerLayout.OnDirectionSlideListener() {

            @Override
            public void onDirectionSlide() {
                if (mOnFragmentWantFinishListener != null)
                    mOnFragmentWantFinishListener
                            .onSlideFragmentFinish(DemoSlideBottomFinishScrollViewFragment.this);
            }
        });


        slideLayout.setBackgroundColor(bgColor);

        return slideLayout;

    }


    public static DemoSlideBottomFinishScrollViewFragment newInstance(int bgColor) {
        DemoSlideBottomFinishScrollViewFragment fragment = new DemoSlideBottomFinishScrollViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_BG_COLOR, bgColor);
        fragment.setArguments(args);
        return fragment;
    }

    public DemoSlideBottomFinishScrollViewFragment() {
    }


}
