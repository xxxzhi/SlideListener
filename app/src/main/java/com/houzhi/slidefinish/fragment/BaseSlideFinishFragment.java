/**
 * 
 */
package com.houzhi.slidefinish.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.houzhi.slidefinish.R;

/**
 * @author houzhi
 *	可监听滑动删除 fragment
 *	使用该fragment 的Activity 必须实现SlideFragmentFinishListener 接口
 *
 *	
 */
public abstract class BaseSlideFinishFragment extends Fragment {
	
	/**
	 * 
	 * @author houzhi
	 * 
	 */
	public static interface SlideFragmentFinishListener{
		public void onSlideFragmentFinish(Fragment fragment);
	}
	
	//监听 fragment 接口
	protected SlideFragmentFinishListener mOnFragmentWantFinishListener;
	
	protected Activity activity ;
	
	/**
	 * 屏幕信息
	 */
	protected DisplayMetrics outMetrics = new DisplayMetrics();
	
	@Override
	public void onAttach(Activity activity) {
		if(activity instanceof SlideFragmentFinishListener){
			mOnFragmentWantFinishListener = (SlideFragmentFinishListener)activity;
		}else{
			throw new RuntimeException("the Activity which use SlideFinishFragment" +
					" must implements OnFragmentWantFinishLIstener!");
		}
		this.activity = activity ;
		
		activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		
		super.onAttach(activity);
	}
	
	
	
	
	protected FrameLayout.LayoutParams saveParams = null;
	/**
	 * the onCreateView will callback this method 
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 * @return
	 */
	abstract protected View onCreateContentView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState);

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		final Button bt = (Button)view.findViewById(R.id.bt_test);
		bt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bt.setText("invoke onclick");
			}
		});
	}
}
