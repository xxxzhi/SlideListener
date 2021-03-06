package com.houzhi.slidelistener.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.houzhi.slidelistener.R;
import com.houzhi.slidelistener.widget.DirectionSlideListenerLayout;

public class DemoSlideTopFinishButtonFragment extends BaseDemoSlideFinishFragment {


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_only_button, container, false);
		TextView tv = (TextView)view.findViewById(R.id.tv_hint);
		tv.setText(R.string.slide_top);

		DirectionSlideListenerLayout slideLayout = new DirectionSlideListenerLayout(getActivity());
		slideLayout.addView(view);
		slideLayout.setDirection(DirectionSlideListenerLayout.SlideDirection.TOP);

		slideLayout.setOnSlideListener(new DirectionSlideListenerLayout.OnDirectionSlideListener() {

			@Override
			public void onDirectionSlide() {
				mOnFragmentWantFinishListener
						.onSlideFragmentFinish(DemoSlideTopFinishButtonFragment.this);
			}
		});
		slideLayout.setBackgroundColor(bgColor);
		return slideLayout;

	}

	public static DemoSlideTopFinishButtonFragment newInstance(int bgColor) {
		DemoSlideTopFinishButtonFragment fragment = new DemoSlideTopFinishButtonFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_BG_COLOR, bgColor);
		fragment.setArguments(args);
		return fragment;
	}

	public DemoSlideTopFinishButtonFragment() {
	}


}
