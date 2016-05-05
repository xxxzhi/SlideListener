package com.houzhi.slidefinish.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.houzhi.slidefinish.R;
import com.houzhi.slidefinish.fragment.BaseSlideFinishFragment;
import com.houzhi.slidefinish.widget.SlideFrameLayout;

public class DemoSlideTopFinishFragment1 extends BaseSlideFinishFragment {

	private static final String ARG_BG_COLOR = "backgroundcolor";

	private int bgColor;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_blank, container, false);
		TextView tv = (TextView)view.findViewById(R.id.tv_hint);
		tv.setText(R.string.slide_top);

		SlideFrameLayout slideFrameLayout = new SlideFrameLayout(getActivity());
		slideFrameLayout.addView(view);
		slideFrameLayout.setDirection(SlideFrameLayout.SlideDirection.TOP);

		slideFrameLayout.setOnSlideListener(new SlideFrameLayout.OnSlideListener() {

			@Override
			public void onSlideFinish() {
				mOnFragmentWantFinishListener
						.onSlideFragmentFinish(DemoSlideTopFinishFragment1.this);
			}
		});
		slideFrameLayout.setBackgroundColor(bgColor);
		return slideFrameLayout;

	}

	public static DemoSlideTopFinishFragment1 newInstance(int bgColor) {
		DemoSlideTopFinishFragment1 fragment = new DemoSlideTopFinishFragment1();
		Bundle args = new Bundle();
		args.putInt(ARG_BG_COLOR, bgColor);
		fragment.setArguments(args);
		return fragment;
	}

	public DemoSlideTopFinishFragment1() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			bgColor = getArguments().getInt(ARG_BG_COLOR);
		}
	}

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return null;
	}
}
