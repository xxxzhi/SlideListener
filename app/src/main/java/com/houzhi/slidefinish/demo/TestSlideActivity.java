package com.houzhi.slidefinish.demo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.houzhi.slidefinish.widget.SlideFrameLayout;
import com.houzhi.slidefinish.widget.SlideFrameLayout.SlideDirection;
import com.houzhi.slidefinish.R;

public class TestSlideActivity extends FragmentActivity {
	protected FrameLayout.LayoutParams saveParams = null;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = getLayoutInflater().inflate(R.layout.activity_test, null);
		SlideFrameLayout slideFrameLayout = new SlideFrameLayout(this);
		slideFrameLayout.addView(view);
		slideFrameLayout.setDirection(SlideDirection.RIGHT);
		setContentView(slideFrameLayout);

		slideFrameLayout.setOnSlideListener(new SlideFrameLayout.OnSlideListener() {

			@Override
			public void onSlideFinish() {
				finish();
			}
		});
//
//		setContentView(R.layout.fragment_blank1);
	}

}
