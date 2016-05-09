package com.houzhi.slidelistener.demo.fragment;

import android.os.Bundle;

/**
 * Created by houzhi on 16-5-9.
 */
public class BaseDemoSlideFinishFragment extends BaseSlideFinishFragment{
    protected static final String ARG_BG_COLOR = "backgroundcolor";


    protected int bgColor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bgColor = getArguments().getInt(ARG_BG_COLOR);
        }
    }
}
