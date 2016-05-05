package com.houzhi.slidefinish.fragment.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.houzhi.slidefinish.demo.BlankFragment;
import com.houzhi.slidefinish.fragment.SlideBottomFinishFragment;
import com.houzhi.slidefinish.fragment.SlideHorizonalFinishFragment;
import com.houzhi.slidefinish.fragment.SlideRightFinishFragment;
import com.scut.houzhi.qqemailtransdemo.R;

public class DemoSlideBottomFinishFragment extends SlideBottomFinishFragment {
	
	private static final String ARG_PARAM1 = "param1";
	
	private int bgColor;
	@Override
	protected View onCreateContentView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_blank, container, false);
		v.setBackgroundColor(bgColor);
		
		v.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				((Button)arg0).setText("I can be click!");
			}
		});
		
		return v;
	}

	public static DemoSlideBottomFinishFragment newInstance(int bgColor) {
		DemoSlideBottomFinishFragment fragment = new DemoSlideBottomFinishFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_PARAM1, bgColor);
		fragment.setArguments(args);
		return fragment;
	}

	public DemoSlideBottomFinishFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			bgColor = getArguments().getInt(ARG_PARAM1);
		}
	}
	
}
