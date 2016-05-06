package com.houzhi.slidefinish.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by houzhi on 16-5-5.
 */
public class BlankLayout extends FrameLayout {
    private static final String LOGTAG = "BlankLayout";

    public BlankLayout(Context context) {
        super(context);
    }

    public BlankLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        requestDisallowInterceptTouchEvent(false);
        boolean res =  super.onInterceptTouchEvent(ev);
        Log.i(LOGTAG, "onInterceptTouchEvent--" + "    " + res);
        return res;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        requestDisallowInterceptTouchEvent(false);
        boolean res =  super.onTouchEvent(event);
        Log.i(LOGTAG, "onTouchEvent--" + "    " + res);
        return res;
    }
}
