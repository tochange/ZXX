package com.mingle.sweetpick;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mingle.sweetsheet.R;
import com.mingle.widget.FreeGrowUpParentRelativeLayout;
import com.mingle.widget.SweetView;

/**
 * @author zzz40500
 * @version 1.0
 * @date 2015/8/5.
 * @github: https://github.com/zzz40500
 */
public class RecyclerViewDelegate extends Delegate {

    private SweetView mSweetView;
    private FreeGrowUpParentRelativeLayout mFreeGrowUpParentRelativeLayout;
    private boolean mIsDragEnable;

    public RecyclerViewDelegate(boolean dragEnable) {
        mIsDragEnable = dragEnable;

    }

    @Override
    protected View createView() {

        View rootView = LayoutInflater.from(mParentVG.getContext())
                .inflate(R.layout.layout_rv_sweet, null, false);

        mSweetView = (SweetView) rootView.findViewById(R.id.sv);
        mFreeGrowUpParentRelativeLayout = (FreeGrowUpParentRelativeLayout) rootView.findViewById(R.id.freeGrowUpParentF);
        mSweetView.setAnimationListener(new AnimationImp());
        return rootView;
    }


    protected void show() {
        super.show();
        ViewGroup.LayoutParams lp =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        if (mRootView.getParent() != null) {
            mParentVG.removeView(mRootView);
        }

        mParentVG.addView(mRootView, lp);
        mSweetView.show();
    }

    @Override
    protected void dismiss() {
        super.dismiss();

    }


    class AnimationImp implements SweetView.AnimationListener {

        @Override
        public void onStart() {
            mFreeGrowUpParentRelativeLayout.reset();
            mStatus = SweetSheet.Status.SHOWING;
        }

        @Override
        public void onEnd() {
            if (mStatus == SweetSheet.Status.SHOWING) {
                mStatus = SweetSheet.Status.SHOW;
            }

        }

        @Override
        public void onContentShow() {
        }
    }
}
