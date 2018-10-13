package com.suk.library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * @author Felix.Zhong
 */
public class WheelScrollView extends ScrollView implements Runnable {

    private int y = -100;
    private int curY = 0;
    private Thread t;
    private boolean flag = false;
    private OnScrollStopListener listener;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (flag) {
                listener.onStop(curY);
                flag = false;
            }
            y = -100;
            curY = 0;
        }

    };

    public WheelScrollView(Context context) {
        super(context);
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        this.y = y < 0 ? 0 : y;
    }

    public void setOnScrollStopListener(OnScrollStopListener listener) {
        this.listener = listener;
    }

    public interface OnScrollStopListener {
        void onStop(int y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            flag = true;
            if (t == null) {
                t = new Thread(this);
                t.start();
            } else if (!t.isAlive()) {
                t = new Thread(this);
                t.start();
            }
        }
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            flag = false;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 滑动事件(滑动速度减半)
     */
    @Override
    public void fling(int velocityY) {
        super.fling(velocityY / 3);
    }

    @Override
    public void run() {
        while (flag) {
            try {
                if (curY == y) {
                    handler.sendEmptyMessage(0);
                } else {
                    curY = y;
                }
                Thread.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
