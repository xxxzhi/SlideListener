package com.houzhi.slidefinish.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.houzhi.slidefinish.R;
import com.houzhi.slidefinish.fragment.BaseSlideFinishFragment;
import com.houzhi.slidefinish.widget.SlideLayout;

public class DemoSlideBottomFinishFragment extends BaseSlideFinishFragment {

	private static final String ARG_BG_COLOR = "backgroundcolor";

	private int bgColor;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_blank, container, false);
		TextView tv = (TextView)view.findViewById(R.id.tv_hint);
		tv.setText(R.string.slide_bottom);
		SlideLayout slideLayout = new SlideLayout(getActivity());
		slideLayout.addView(view);
		slideLayout.setDirection(SlideLayout.SlideDirection.BOTTOM);

		slideLayout.setOnSlideListener(new SlideLayout.OnSlideListener() {

			@Override
			public void onSlideFinish() {
				mOnFragmentWantFinishListener
						.onSlideFragmentFinish(DemoSlideBottomFinishFragment.this);
			}
		});



		slideLayout.setBackgroundColor(bgColor);

		return slideLayout;


//		view.setOnTouchListener(new View.OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				Log.i("", "onTouch"+""+event.getAction());
//				return false;
//			}
//		});
//		return view;
	}

	//滑动方向
	private SlideLayout.SlideDirection direction = SlideLayout.SlideDirection.BOTTOM;






	public static DemoSlideBottomFinishFragment newInstance(int bgColor) {
		DemoSlideBottomFinishFragment fragment = new DemoSlideBottomFinishFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_BG_COLOR, bgColor);
		fragment.setArguments(args);
		return fragment;
	}

	public DemoSlideBottomFinishFragment() {
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
