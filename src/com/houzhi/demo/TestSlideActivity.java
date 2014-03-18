package com.houzhi.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.houzhi.slidefinish.widget.SlideFrameLayout;
import com.scut.houzhi.qqemailtransdemo.R;

public class TestSlideActivity extends BaseActivity {
	protected FrameLayout.LayoutParams saveParams = null;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		View view = getLayoutInflater().inflate(R.layout.activity_test, null);
		SlideFrameLayout slideFrameLayout = new SlideFrameLayout(this);
		slideFrameLayout.addView(view);
		setContentView(slideFrameLayout);
		

		slideFrameLayout.setSlideListener(new SlideFrameLayout.SlideListener() {
			
			@Override
			public void onSlideFinish() {
				finish();
			}
		});
	}

}
