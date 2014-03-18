package com.houzhi.demo;

import com.scut.houzhi.qqemailtransdemo.R;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link BlankFragment.OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link BlankFragment#newInstance} factory method
 * to create an instance of this fragment.
 * 
 */
public class BlankFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private int bgColor;
	private String mParam2;


	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 * 
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment BlankFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static BlankFragment newInstance(int bgColor) {
		BlankFragment fragment = new BlankFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_PARAM1, bgColor);
		fragment.setArguments(args);
		return fragment;
	}

	public BlankFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			bgColor = getArguments().getInt(ARG_PARAM1);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_blank, container, false);
		v.setBackgroundColor(bgColor);
		
		v.setOnTouchListener(new  View.OnTouchListener() {
			float beginX = 0 ;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.i("", "touch"+event.getAction());
				float moveDis = 0 ;
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					beginX = event.getX();
					break;
				case MotionEvent.ACTION_MOVE:
					moveDis = event.getX() - beginX;
					break;
				case MotionEvent.ACTION_UP:
					moveDis = event.getX() - beginX;
					break;
				}
				
				FrameLayout.LayoutParams params =(FrameLayout.LayoutParams) v.getLayoutParams();
			
				params.leftMargin += moveDis;
				params.rightMargin -= moveDis;
				params.gravity = Gravity.LEFT | Gravity.TOP;
				v.setLayoutParams(params);
				
				return true;
			}
		});
		
		return v;
	}



	@Override
	public void onDetach() {
		super.onDetach();
	}


}
