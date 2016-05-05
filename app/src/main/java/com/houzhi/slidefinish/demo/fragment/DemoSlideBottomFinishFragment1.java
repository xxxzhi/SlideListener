package com.houzhi.slidefinish.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.houzhi.slidefinish.R;
import com.houzhi.slidefinish.fragment.BaseSlideFinishFragment;
import com.houzhi.slidefinish.widget.SlideFrameLayout;

public class DemoSlideBottomFinishFragment1 extends BaseSlideFinishFragment {

	private static final String ARG_BG_COLOR = "backgroundcolor";

	private int bgColor;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_blank1, container, false);
//		TextView tv = (TextView)view.findViewById(R.id.tv_hint);
//		tv.setText(R.string.slide_bottom);
//		SlideFrameLayout slideFrameLayout = new SlideFrameLayout(getActivity());
//		slideFrameLayout.addView(view);
//		slideFrameLayout.setDirection(SlideFrameLayout.SlideDirection.BOTTOM);
//
//		slideFrameLayout.setOnSlideListener(new SlideFrameLayout.OnSlideListener() {
//
//			@Override
//			public void onSlideFinish() {
//				mOnFragmentWantFinishListener
//						.onSlideFragmentFinish(DemoSlideBottomFinishFragment1.this);
//			}
//		});
//
//
//
//		slideFrameLayout.setBackgroundColor(bgColor);
//
//		return slideFrameLayout;


//		view.setOnTouchListener(new View.OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				Log.i("", "onTouch"+""+event.getAction());
//				return false;
//			}
//		});
		return view;
	}

	//滑动方向
	private SlideFrameLayout.SlideDirection direction = SlideFrameLayout.SlideDirection.BOTTOM;






	public static DemoSlideBottomFinishFragment1 newInstance(int bgColor) {
		DemoSlideBottomFinishFragment1 fragment = new DemoSlideBottomFinishFragment1();
		Bundle args = new Bundle();
		args.putInt(ARG_BG_COLOR, bgColor);
		fragment.setArguments(args);
		return fragment;
	}

	public DemoSlideBottomFinishFragment1() {
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
