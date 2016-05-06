package com.houzhi.slidefinish.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

public class SlideFrameLayout extends FrameLayout {
    /**
     * move thershold ，if the distance of moving more than thershold ,this framelayout will be slide.
     */
    private static final int MOVE_THRESHOLD = 5;
    private static final int MOVE_DIRECTION_THRESHOLD = 6;

    public SlideFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public SlideFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlideFrameLayout(Context context) {
        super(context);
        init();
    }

    public static enum SlideDirection {
        LEFT, RIGHT, HORIZONAL, TOP, BOTTOM, VERTICAL, NO
    }

    ;

    /**
     * listen to the slide
     *
     * @author houzhi
     */
    public static interface OnSlideListener {
        /**
         *
         */
        void onSlideFinish();
    }

    private OnSlideListener mOnSlideListener;

    protected FrameLayout.LayoutParams saveParams = null;

    /**
     * mark the animation is starting
     */
    private boolean isAnimationRunning = false;

    public OnSlideListener getOnSlideListener() {
        return mOnSlideListener;
    }

    public void setOnSlideListener(OnSlideListener mSlideListener) {
        this.mOnSlideListener = mSlideListener;
    }

    private void init() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.TOP | Gravity.LEFT;
        setLayoutParams(params);

    }

    private boolean slideHorizontalFinish(View v, MotionEvent event) {
        Log.i(LOGTAG, "slideHorizontalFinish--" + "    ");
        float moveDis = 0;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isAnimationRunning) return false;
                beginX = event.getRawX();
                beginY = event.getRawY();
                isFirstMoveEvent = true;
                isMove = false;
                Log.i(LOGTAG, "touch ACTION_DOWN beginX---" + beginX);

                saveParams = new FrameLayout.LayoutParams(
                        (ViewGroup.MarginLayoutParams) v.getLayoutParams());
//                return false;
//                break;
                return true;
            case MotionEvent.ACTION_MOVE:
                if (!checkHorizontalMove(event)) {
                    // doesn't move
                    break;
                }
                moveDis = event.getRawX() - beginX;

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

                params.leftMargin = (int) moveDis;
                params.rightMargin = (int) -moveDis;
                params.gravity = Gravity.LEFT | Gravity.TOP;
                Log.i(LOGTAG, "touch ACTION_MOVE---" + moveDis + "--" + event.getRawX());
                v.setLayoutParams(params);
                return true;
            case MotionEvent.ACTION_UP:
                if (!isMove)
                    break;

                moveDis = event.getRawX() - beginX;
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
                Log.i(LOGTAG, "touch dis---" + disappear + "=---=" + (moveDis) + "---"
                        + v.getWidth());
                startEndHorizontalAnimation(v, duration, from, to, disappear);
                return true;
        }
        return false;

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
        if (event.getAction() != MotionEvent.ACTION_MOVE) return false;
        if (isFirstMoveEvent) {
            float moveDis = event.getRawX() - beginX;
            float moveDisV = Math.abs(event.getRawY() - beginY);
            isFirstMoveEvent = false;
            isMove = true;
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
        if (isFirstMoveEvent) {
            // only the first action_move to check whether this action move or not.
            isFirstMoveEvent = false;
            isMove = true;
            float moveDis = event.getRawY() - beginY;
            Log.i(LOGTAG, "touch ACTION_DOWN moveDis---" + moveDis);
            float moveDisH = Math.abs(event.getRawX() - beginX);
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

    private boolean slideVerticalFinish(View v, MotionEvent event) {
        Log.i(LOGTAG, "slideVerticalFinish--" + "    ");
        float moveDis = 0;
//        judgeVertical(v, event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isAnimationRunning) return false;
                beginY = event.getRawY();
                beginX = event.getRawX();
                isFirstMoveEvent = true;
                isMove = false;
                Log.i(LOGTAG, "touch ACTION_DOWN beginX---" + beginY);

                saveParams = new FrameLayout.LayoutParams(
                        (ViewGroup.MarginLayoutParams) v.getLayoutParams());
                Log.i(LOGTAG, saveParams.leftMargin + "," + saveParams.rightMargin + "," + saveParams.topMargin + "," + saveParams.bottomMargin);
                return true;
            case MotionEvent.ACTION_MOVE:

                if (!checkVerticalMove(event)) {
                    // not move
                    Log.i(LOGTAG, "touch ACTION_MOVE--- not move --");
                    break;
                }
                moveDis = event.getRawY() - beginY;
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) v
                        .getLayoutParams();

                params.topMargin = saveParams.topMargin + (int) moveDis;
                params.bottomMargin = saveParams.bottomMargin + (int) -moveDis;
                params.gravity = Gravity.LEFT | Gravity.TOP;
                Log.i(LOGTAG, "touch ACTION_MOVE---" + moveDis + "--" + event.getRawY());
                v.setLayoutParams(params);
                return true;
            case MotionEvent.ACTION_UP:
                if (!isMove)
                    break;

                moveDis = event.getRawY() - beginY;
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
                Log.i(LOGTAG, "touch dis---" + disappear + "=---=" + (moveDis) + "---"
                        + v.getWidth());
                startEndVerticalAnimation(v, duration, from, to, disappear);
                return true;
        }

        return false;

    }

    // move horizontal animation
    private void startEndHorizontalAnimation(final View view, long duration,
                                             float from, float to, final boolean disappear) {
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
                        mOnSlideListener.onSlideFinish();
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
        isAnimationRunning = true;
        view.startAnimation(animationSet);

    }

    /**
     * recover from slide finish
     */
    public void recoverFromSlide() {
        clearAnimation();
        Log.i(LOGTAG, saveParams.leftMargin + "," + saveParams.rightMargin + "," + saveParams.topMargin + "," + saveParams.bottomMargin);
        setLayoutParams(saveParams);
    }

    // move vertical animation
    private void startEndVerticalAnimation(final View view, long duration,
                                           float from, float to, final boolean disappear) {
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
                        mOnSlideListener.onSlideFinish();
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
        isAnimationRunning = true;
        view.startAnimation(animationSet);

    }


    float beginX = 0, beginY = 0;

    boolean isMove = false;
    boolean isFirstMoveEvent = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
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


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        boolean judgeRes = false;
//        switch (direction) {
//            case LEFT:
//            case RIGHT:
//            case HORIZONAL:
//                judgeRes = judgeHorizonal(this, ev);
//                break;
//            case TOP:
//            case BOTTOM:
//            case VERTICAL:
//                judgeRes = judgeVertical(this, ev);
//                break;
//            case NO:
//                judgeRes = false;
//                break;
//        }
//        Log.i(LOGTAG, "onInterceptTouchEvent--" + direction + "    " + judgeRes);
        return isMove;
    }

}
