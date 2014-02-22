package com.scut.houzhi.qqemailtransdemo;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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

	private OnFragmentInteractionListener mListener;

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
	public static BlankFragment newInstance(int bgColor, String param2) {
		BlankFragment fragment = new BlankFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_PARAM1, bgColor);
		args.putString(ARG_PARAM2, param2);
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
			mParam2 = getArguments().getString(ARG_PARAM2);
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
				v.setLayoutParams(params);
				
				return true;
			}
		});
		
		return v;
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(Uri uri);
	}

}
