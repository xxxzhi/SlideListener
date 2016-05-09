package com.houzhi.slidefinish.demo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.houzhi.slidefinish.widget.SlideLayout;
import com.houzhi.slidefinish.widget.SlideLayout.SlideDirection;
import com.houzhi.slidefinish.R;

public class TestSlideActivity extends FragmentActivity {
	protected FrameLayout.LayoutParams saveParams = null;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = getLayoutInflater().inflate(R.layout.activity_test, null);
		SlideLayout slideLayout = new SlideLayout(this);
		slideLayout.addView(view);
		slideLayout.setDirection(SlideDirection.RIGHT);
		setContentView(slideLayout);

		slideLayout.setOnSlideListener(new SlideLayout.OnSlideListener() {

			@Override
			public void onSlideFinish() {
				finish();
			}
		});
//
//		setContentView(R.layout.fragment_blank1);
	}

}
