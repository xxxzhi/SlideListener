package com.houzhi.slidelistener.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

public class DirectionSlideListenerLayout extends FrameLayout {
    /**
     * move thershold ，if the distance of moving more than thershold ,this framelayout will be slide.
     */
    private static final int MOVE_DIRECTION_THRESHOLD = 6;

    public DirectionSlideListenerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public DirectionSlideListenerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DirectionSlideListenerLayout(Context context) {
        super(context);
        init();
    }

    public enum SlideDirection {
        LEFT, RIGHT, HORIZONAL, TOP, BOTTOM, VERTICAL, NO
    }


    /**
     * listen to the slide
     *
     * @author houzhi
     */
    public static interface OnDirectionSlideListener {
        /**
         *
         */
        void onDirectionSlide();
    }

    private OnDirectionSlideListener mOnSlideListener;

    protected FrameLayout.LayoutParams saveParams = null;

    private int edgeLengthForSlide;
    
    public int getEdgeLengthForSlide() {
        return edgeLengthForSlide;
    }

    public void setEdgeLengthForSlide(int edgeLengthForSlide) {
        this.edgeLengthForSlide = edgeLengthForSlide;
    }

    private boolean onlyBorderValid = true;


    public boolean isOnlyBorderValid() {
        return onlyBorderValid;
    }

    public void setOnlyBorderValid(boolean onlyBorderValid) {
        this.onlyBorderValid = onlyBorderValid;
    }

    private boolean enableListenerSlide = true;

    public boolean isEnableListenerSlide() {
        return enableListenerSlide;
    }

    public void setEnableListenerSlide(boolean enableListenerSlide) {
        this.enableListenerSlide = enableListenerSlide;
    }

    /**
     * mark the animation is starting
     */
    private boolean isAnimationRunning = false;

    public OnDirectionSlideListener getOnSlideListener() {
        return mOnSlideListener;
    }

    public void setOnSlideListener(OnDirectionSlideListener mSlideListener) {
        this.mOnSlideListener = mSlideListener;
    }

    private void init() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.TOP | Gravity.LEFT;
        setLayoutParams(params);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        edgeLengthForSlide = (int) (10 * displayMetrics.density);
    }


    public static final String LOGTAG = "SlideFrameLayout";
    private SlideDirection direction = SlideDirection.RIGHT;

    public SlideDirection getDirection() {
        return direction;
    }

    public void setDirection(SlideDirection direction) {
        this.direction = direction;
    }





    private boolean checkHorizontalMove(MotionEvent event) {
        Log.i(LOGTAG, "checkHorizontalMove---" + event.getActionMasked());
        if (event.getActionMasked() != MotionEvent.ACTION_MOVE) return false;

        if (isFirstMoveEvent && !isMove) {
            isFirstMoveEvent = false;
            isMove = true;

            Log.i(LOGTAG, "checkHorizontalMove---beginX: " + beginX + ",getEdgeFlags:" + event.getEdgeFlags()+",width"+getWidth());
            // only the event slide from border can move at normal
            if (isOnlyBorderValid() && childHasRespond) {
                Log.i(LOGTAG, "checkHorizontalMove : enter check" );
                switch (direction) {
                    case RIGHT:
//                        if(event.getEdgeFlags() != MotionEvent.EDGE_LEFT){
                        if (beginX > 0 + getEdgeLengthForSlide()) {
//                            Log.i(LOGTAG, "checkHorizontalMove : RIGHT" );
                            isMove = false;
                            return false;
                        }
                        break;
                    case LEFT:
//                        if(event.getEdgeFlags() != MotionEvent.EDGE_RIGHT){
                        if (beginX < getWidth() - getEdgeLengthForSlide()) {
                            Log.i(LOGTAG, "checkHorizontalMove : not move" );
                            isMove = false;
                            return false;
                        }
                        break;
                    case HORIZONAL:
                        break;
                }
            }

//            Log.i(LOGTAG, "checkHorizontalMove1---beginX: " + beginX + ",saveParams.leftMargin" + saveParams.leftMargin);

            float moveDis = event.getRawX() - beginRawX;
            float moveDisV = Math.abs(event.getRawY() - beginRawY);

            if (moveDisV > MOVE_DIRECTION_THRESHOLD) {
                // if has move vertical , 已经往垂直方向移动了
                isMove = false;
            } else {
                switch (direction) {
                    case RIGHT:
                        if (moveDis <= 0)
                            isMove = false;
                        break;
                    case LEFT:
                        if (moveDis >= 0)
                            isMove = false;
                        break;
                    case HORIZONAL:
                        if (moveDis == 0)
                            isMove = false;
                        break;
                }
            }
        }
        return isMove;
    }


    private boolean checkVerticalMove(MotionEvent event) {
        if (event.getActionMasked() != MotionEvent.ACTION_MOVE) return false;

        if (isFirstMoveEvent && !isMove) {// only the first action_move to check whether this action move or not.
            isFirstMoveEvent = false;
            isMove = true;
            // only the event slide from border can move
            if (isOnlyBorderValid() && childHasRespond) {
                switch (direction) {
                    case BOTTOM:
//                        if(event.getEdgeFlags() != MotionEvent.EDGE_TOP){ // getEdgeFlags maybe always 0
                        if (beginY > 0 + getEdgeLengthForSlide()) {
                            isMove = false;
                            return false;
                        }
                        break;
                    case TOP:
//                        if(event.getEdgeFlags() != MotionEvent.EDGE_BOTTOM){
                        if (beginY < saveParams.height - getEdgeLengthForSlide()) {
                            isMove = false;
                            return false;
                        }
                        break;
                    case HORIZONAL:
                        break;
                }
            }

            isFirstMoveEvent = false;
            isMove = true;
            float moveDis = event.getRawY() - beginRawY;
//            Log.i(LOGTAG, "touch ACTION_DOWN moveDis---" + moveDis);
            float moveDisH = Math.abs(event.getRawX() - beginRawX);
            if (moveDisH > MOVE_DIRECTION_THRESHOLD) {
                //  move horizonal
                isMove = false;
            } else {
                //judge whether  distance of moving over or not
                switch (direction) {
                    case BOTTOM:
                        if (moveDis <= 0) {
                            isMove = false;
                        }
                        break;
                    case TOP:
                        if (moveDis >= 0) {
                            isMove = false;
                        }
                        break;
                    case VERTICAL:
                        if (moveDis == 0) {
                            isMove = false;
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        return isMove;
    }

    private boolean slideHorizontalFinish(View v, MotionEvent event) {
        Log.i(LOGTAG, "slideHorizontalFinish--" + "    ");
        float moveDis = 0;

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (isAnimationRunning) return false;
                return true;
            case MotionEvent.ACTION_MOVE:
                if (!childHasRespond && !checkHorizontalMove(event)) {
                    // doesn't move
                    break;
                }
                moveDis = event.getRawX() - beginRawX;

                switch (direction) {
                    case RIGHT:
                        if (moveDis <= 0)
                            moveDis = 0;
                        break;
                    case LEFT:
                        if (moveDis >= 0)
                            moveDis = 0;
                        break;
                    case HORIZONAL:
                        break;
                    default:
                        break;
                }


                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) v
                        .getLayoutParams();

                params.leftMargin = saveParams.leftMargin + (int) moveDis;
                params.rightMargin = saveParams.rightMargin + (int) -moveDis;
                params.gravity = Gravity.LEFT | Gravity.TOP;
                v.setLayoutParams(params);
                return true;
            case MotionEvent.ACTION_UP:
                if (!isMove)
                    break;

                moveDis = event.getRawX() - beginRawX;
                switch (direction) {
                    case RIGHT:
                        if (moveDis <= 0)
                            moveDis = 0;
                        break;
                    case LEFT:
                        if (moveDis >= 0)
                            moveDis = 0;
                        break;
                    case HORIZONAL:
                        break;
                    default:
                        break;
                }

                boolean disappear = false;
                float from = 0,
                        to = -moveDis;
                if (Math.abs(moveDis) > v.getWidth() / 2) {
                    // 移动超出 消失此Fragment
                    to = v.getWidth() * (Math.abs(moveDis) / moveDis);
                    disappear = true;
                }
                long duration = 500;
//                Log.i(LOGTAG, "touch dis---" + disappear + "=---=" + (moveDis) + "---"
//                        + v.getWidth());
                startEndHorizontalAnimation(v, duration, from, to, disappear);
                return true;
        }
        return false;

    }

    private boolean slideVerticalFinish(View v, MotionEvent event) {
//        Log.i(LOGTAG, "slideVerticalFinish--" + "    ");
        float moveDis = 0;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (isAnimationRunning) return false;
                return true;
            case MotionEvent.ACTION_MOVE:

                if (!childHasRespond && !checkVerticalMove(event)) {
                    // not move
                    Log.i(LOGTAG, "touch ACTION_MOVE--- not move --");
                    break;
                }
                moveDis = event.getRawY() - beginRawY;

                switch (direction) {
                    case BOTTOM:
                        if (moveDis <= 0)
                            moveDis = 0;
                        break;
                    case TOP:
                        if (moveDis >= 0)
                            moveDis = 0;
                        break;
                    case VERTICAL:
                        break;
                    default:
                        break;
                }

                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) v
                        .getLayoutParams();

                params.topMargin = saveParams.topMargin + (int) moveDis;
                params.bottomMargin = saveParams.bottomMargin + (int) -moveDis;
                params.gravity = Gravity.LEFT | Gravity.TOP;
//                Log.i(LOGTAG, "touch ACTION_MOVE---" + moveDis + "--" + event.getRawY());
                v.setLayoutParams(params);
                return true;
            case MotionEvent.ACTION_UP:
                if (!isMove)
                    break;

                moveDis = event.getRawY() - beginRawY;
                //judge whether  distance of moving over or not
                switch (direction) {
                    case BOTTOM:
                        if (moveDis <= 0)
                            moveDis = 0;
                        break;
                    case TOP:
                        if (moveDis >= 0)
                            moveDis = 0;
                        break;
                    case VERTICAL:
                        break;
                    default:
                        break;
                }

                boolean disappear = false;
                float from = 0,
                        to = -moveDis;
                if (Math.abs(moveDis) > v.getHeight() / 2) {
                    // 移动超出 消除此Fragment
                    to = v.getHeight() * (Math.abs(moveDis) / moveDis);
                    disappear = true;
                }
                long duration = 500;
//                Log.i(LOGTAG, "touch dis---" + disappear + "=---=" + (moveDis) + "---"
//                        + v.getWidth());
                startEndVerticalAnimation(v, duration, from, to, disappear);
                return true;
        }

        return false;

    }

    // move horizontal animation
    private void startEndHorizontalAnimation(final View view, long duration,
                                             float from, float to, final boolean disappear) {
        isAnimationRunning = true;
        AnimationSet animationSet = new AnimationSet(true);

        if (disappear) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
            animationSet.addAnimation(alphaAnimation);
        }
        TranslateAnimation translateAnimation = new TranslateAnimation(from,
                to, 0, 0);
        animationSet.addAnimation(translateAnimation);
        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        animationSet.setFillEnabled(true);
        animationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation arg0) {
                if (!isAnimationRunning) return;
                if (disappear) {
                    if (mOnSlideListener != null) {
                        mOnSlideListener.onDirectionSlide();
                    }
                } else {
                    recoverFromSlide();
                }
                isAnimationRunning = false;
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {

            }

            @Override
            public void onAnimationStart(Animation arg0) {

            }
        });

        view.startAnimation(animationSet);

    }

    /**
     * recover from slide finish
     */
    public void recoverFromSlide() {
        clearAnimation();
        setLayoutParams(saveParams);
    }

    // move vertical animation
    private void startEndVerticalAnimation(final View view, long duration,
                                           float from, float to, final boolean disappear) {
        isAnimationRunning = true;
        AnimationSet animationSet = new AnimationSet(true);

        if (disappear) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
            animationSet.addAnimation(alphaAnimation);
        }
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0,
                from, to);
        animationSet.addAnimation(translateAnimation);
        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        animationSet.setFillEnabled(true);
        animationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation arg0) {
                Log.i(LOGTAG, "onAnimationEnd:" + arg0.hasEnded());
                Log.i(LOGTAG, "onAnimationEnd");
                if (!isAnimationRunning) return;
                if (disappear) {
                    if (mOnSlideListener != null) {
                        mOnSlideListener.onDirectionSlide();
                    }
                } else {

                    recoverFromSlide();

                }
                isAnimationRunning = false;
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                Log.i(LOGTAG, "onAnimationRepeat");
            }

            @Override
            public void onAnimationStart(Animation arg0) {
                Log.i(LOGTAG, "onAnimationStart");
            }
        });

        view.startAnimation(animationSet);

    }


    float beginRawX = 0, beginRawY = 0;
    float beginX = 0, beginY = 0;
    boolean isMove = false;
    boolean isFirstMoveEvent = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!isEnableListenerSlide()) return false;
        boolean res = false;
        switch (direction) {
            case LEFT:
            case RIGHT:
            case HORIZONAL:
                res = slideHorizontalFinish(this, event);
                break;
            case TOP:
            case BOTTOM:
            case VERTICAL:
                res = slideVerticalFinish(this, event);
                break;
            default:
        }
        Log.i(LOGTAG, "onTouchEvent--" + "    " + res);
        return res;
    }

    boolean childHasRespond = false;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if(!isEnableListenerSlide()) return false;


        Log.i(LOGTAG, "touch onInterceptTouchEvent---" + event.getActionMasked() + ",getEdgeFlags:" + event.getEdgeFlags());
        //当为MotionEvent.ACTION_MOVE时,
        // 如果子类不消耗当前事件流，那么onInterceptTouchEvent将不会被调用,也就是说只有子类会消耗这次事件流，才会调用onInterceptTouchEvent

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                childHasRespond = false;
                if (isAnimationRunning) return false;
                beginRawX = event.getRawX();
                beginRawY = event.getRawY();
                beginX = event.getX();
                beginY = event.getY();
                isFirstMoveEvent = true;
                isMove = false;

                saveParams = new FrameLayout.LayoutParams(
                        (ViewGroup.MarginLayoutParams) this.getLayoutParams());
                return false;
            case MotionEvent.ACTION_MOVE:
                childHasRespond = true;
                switch (direction) {
                    case LEFT:
                    case RIGHT:
                    case HORIZONAL:
                        return checkHorizontalMove(event);
                    case TOP:
                    case BOTTOM:
                    case VERTICAL:
                        return checkVerticalMove(event);
                    case NO:
                        return false;
                }
            case MotionEvent.ACTION_UP:
                if (!isMove)
                    break;
                return true;
        }

        return false;

    }

}
