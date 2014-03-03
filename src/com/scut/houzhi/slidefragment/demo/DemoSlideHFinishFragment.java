package com.scut.houzhi.slidefragment.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scut.houzhi.qqemailtransdemo.BlankFragment;
import com.scut.houzhi.qqemailtransdemo.R;
import com.scut.houzhi.slidefragment.SlideHorizonalFinishFragment;
import com.scut.houzhi.slidefragment.SlideRightFinishFragment;

public class DemoSlideHFinishFragment extends SlideHorizonalFinishFragment {
	
	private static final String ARG_PARAM1 = "param1";
	
	private int bgColor;
	@Override
	protected View onCreateContentView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_blank, container, false);
		v.setBackgroundColor(bgColor);
		
		
		return v;
	}

	public static DemoSlideHFinishFragment newInstance(int bgColor) {
		DemoSlideHFinishFragment fragment = new DemoSlideHFinishFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_PARAM1, bgColor);
		fragment.setArguments(args);
		return fragment;
	}

	public DemoSlideHFinishFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			bgColor = getArguments().getInt(ARG_PARAM1);
		}
	}
	
}
