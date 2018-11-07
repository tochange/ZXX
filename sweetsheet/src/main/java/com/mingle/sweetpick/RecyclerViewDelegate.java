package com.mingle.sweetpick;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.mingle.widget.SweetView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

/**
 * @author zzz40500
 * @version 1.0
 * @date 2015/8/5.
 * @github: https://github.com/zzz40500
 */
public class RecyclerViewDelegate implements View.OnClickListener {
    private SweetView mSweetView;
    protected Status mStatus = Status.DISMISS;
    protected ViewGroup mParentVG;
    protected View mRootView;
    private ImageView mBg;


    public RecyclerViewDelegate(ViewGroup parentVG) {
        mParentVG = parentVG;
        mBg = new ImageView(parentVG.getContext());
        mSweetView = new SweetView(parentVG.getContext());
        mRootView = mSweetView;
        mBg.setOnClickListener(this);
    }

    protected enum Status {
        SHOW, SHOWING,
        DISMISS, DISMISSING
    }

    public void toggle() {
        switch (mStatus) {
            case SHOW:
            case SHOWING:
                dismissP();
                break;

            case DISMISS:
            case DISMISSING:
                show();
                break;
        }
    }

    private void addAndShowBg() {
        ViewHelper.setTranslationY(mRootView, 0);
        if (mBg.getParent() != null) {
            mParentVG.removeView(mBg);
        }

        mParentVG.addView(mBg, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ViewHelper.setAlpha(mBg, 0);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBg, "alpha", 0, 1);
        objectAnimator.setDuration(400);
        objectAnimator.start();
    }

    private void dismissAndRemoveBg() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBg, "alpha", 1, 0);
        objectAnimator.setDuration(400);
        objectAnimator.start();
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mParentVG.removeView(mBg);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    private void dismissP() {
        if (mStatus == Status.DISMISS) {
            return;
        }
        mBg.setClickable(false);
        dismissAndRemoveBg();
        dismissRootView();
    }

    private void dismissRootView() {
        ObjectAnimator translationOut = ObjectAnimator.ofFloat(mRootView,
                "translationY", 0, mRootView.getHeight());
        translationOut.setDuration(600);
        translationOut.setInterpolator(new DecelerateInterpolator());
        translationOut.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationStart(Animator animation) {
                mStatus = Status.DISMISSING;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mStatus = Status.DISMISS;
                mParentVG.removeView(mRootView);
            }
        });
        translationOut.start();
    }

    @Override
    public void onClick(View v) {
        dismissP();
    }


    private void show() {
        if (mStatus != Status.DISMISS) {
            return;
        }
        mBg.setClickable(true);
        addAndShowBg();

        if (mRootView.getParent() != null) {
            mParentVG.removeView(mRootView);
        }

        mParentVG.addView(mRootView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
        mSweetView.show();
    }

}
