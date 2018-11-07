package com.mingle.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.mingle.sweetsheet.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;

import static com.mingle.widget.SweetView.Status.NONE;

public class SweetView extends View {
    private Paint mPaint;
    private int mArcHeight;
    private int mMaxArcHeight;
    private Status mStatus = NONE;
    private Path mPath = new Path();

    public enum Status {
        NONE,
        STATUS_SMOOTH_UP,
        STATUS_UP,
        STATUS_DOWN,
    }

    public SweetView(Context context) {
        super(context);
        init();
    }

    public SweetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SweetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(getResources().getColor(android.R.color.white));
        mMaxArcHeight = getResources().getDimensionPixelSize(R.dimen.arc_max_height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBG(canvas);
    }

    private void drawBG(Canvas canvas) {
        mPath.reset();
        int currentPointY = 0;
        switch (mStatus) {
            case NONE:
                currentPointY = mMaxArcHeight;
                break;
            case STATUS_SMOOTH_UP:
            case STATUS_UP:
                currentPointY = getHeight() - (int) ((getHeight() - mMaxArcHeight) * Math.min(1, (mArcHeight - mMaxArcHeight / 4) * 2.0 / mMaxArcHeight * 1.3));
                break;
            case STATUS_DOWN:
                currentPointY = mMaxArcHeight;
                break;
        }

        mPath.moveTo(0, currentPointY);
        mPath.quadTo(getWidth() / 2, currentPointY - mArcHeight, getWidth(), currentPointY);
        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    public void show() {
        mStatus = Status.STATUS_SMOOTH_UP;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, mMaxArcHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mArcHeight = value;
                if (value == mMaxArcHeight) {
                    duang();
                }
                invalidate();
            }
        });
        valueAnimator.setDuration(8000);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.start();
    }

    public void duang() {
        mStatus = Status.STATUS_DOWN;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mMaxArcHeight, 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mArcHeight = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mStatus = NONE;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.setDuration(5000);
        valueAnimator.setInterpolator(new OvershootInterpolator(4f));
        valueAnimator.start();
    }
}
