package com.scut.houzhi.slidefragment.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scut.houzhi.qqemailtransdemo.R;
import com.scut.houzhi.slidefragment.SlideLeftFinishFragment;

public class DemoSlideLeftFinishFragment extends SlideLeftFinishFragment {
	
	private static final String ARG_PARAM1 = "param1";
	
	private int bgColor;
	@Override
	protected View onCreateContentView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_blank, container, false);
		v.setBackgroundColor(bgColor);
		
		
		return v;
	}

	public static DemoSlideLeftFinishFragment newInstance(int bgColor) {
		DemoSlideLeftFinishFragment fragment = new DemoSlideLeftFinishFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_PARAM1, bgColor);
		fragment.setArguments(args);
		return fragment;
	}

	public DemoSlideLeftFinishFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			bgColor = getArguments().getInt(ARG_PARAM1);
		}
	}
	
}
