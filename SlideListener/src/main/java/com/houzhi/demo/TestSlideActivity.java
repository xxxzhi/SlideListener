package com.houzhi.slidefinish.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.houzhi.slidefinish.widget.SlideFrameLayout;
import com.houzhi.slidefinish.widget.SlideFrameLayout.SlideDirection;
import com.scut.houzhi.qqemailtransdemo.R;

public class TestSlideActivity extends BaseActivity {
	protected FrameLayout.LayoutParams saveParams = null;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		View view = getLayoutInflater().inflate(R.layout.activity_test, null);
		SlideFrameLayout slideFrameLayout = new SlideFrameLayout(this);
		slideFrameLayout.addView(view);
		slideFrameLayout.setDirection(SlideDirection.BOTTOM);
		setContentView(slideFrameLayout);
		
		
		slideFrameLayout.setOnSlideListener(new SlideFrameLayout.OnSlideListener() {
			
			@Override
			public void onSlideFinish() {
				finish();
			}
		});
		
		
		findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				((TextView)arg0).setText("has click");
			}
		});
	}

}
