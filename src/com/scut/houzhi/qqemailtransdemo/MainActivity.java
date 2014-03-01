package com.scut.houzhi.qqemailtransdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.scut.houzhi.slidefragment.demo.Slide1FinishFragment;
import com.scut.houzhi.slidefragment.SlideFinishFragment;

public class MainActivity extends FragmentActivity implements SlideFinishFragment.OnFragmentWantFinishListener{
	FragmentManager fragmentManager = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		fragmentManager = getSupportFragmentManager();
		
		
		FragmentTransaction transaction =fragmentManager.beginTransaction();
		

		
		transaction.add(R.id.content,Slide1FinishFragment.newInstance(Color.RED), "first");
		transaction.add(R.id.content,Slide1FinishFragment.newInstance(Color.BLUE), "first");
		transaction.add(R.id.content,Slide1FinishFragment.newInstance(Color.GREEN), "green");
		
		transaction.commit();
	}
	
	
	@Override
	public void onFragmentWantFinnish(Fragment fragment) {
		FragmentTransaction transaction =fragmentManager.beginTransaction();
		
		transaction.remove(fragment);
		
		transaction.commit();
	}

}
