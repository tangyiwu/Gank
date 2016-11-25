package com.example.tyw.gank.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

/**
 * Created by tangyiwu on 2016/11/25.
 */

public class ClickFrameLayout extends FrameLayout {
    private int mTouchSlop;

    private int mXDown;
    private int mYDown;

    public ClickFrameLayout(Context context) {
        this(context, null);
    }

    public ClickFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClickFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXDown = (int) ev.getX();
                mYDown = (int) ev.getY();
                break;
        }
        int xDelta = (int) (ev.getX() - mXDown);
        int yDelta = (int) (ev.getY() - mYDown);
        if (Math.abs(xDelta) < Math.abs(yDelta) || Math.abs(xDelta) < mTouchSlop) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
