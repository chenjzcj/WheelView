package com.suk.library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * 数字选择器
 *
 * @author Felix.Zhong
 */
public class CheckNumView extends RelativeLayout {
    private String[] texts;
    /**
     * 焦点初始位置
     */
    private int focusTextPosition;
    private int position = -100;
    private int currentY = -1000;
    private String unit;
    private int focusTextColor, optionTextColor, unitTextColor;
    private float focusTextSize, optionTextSize, unitTextSize;
    private TextView[] textViews;
    private WheelScrollView scrollView;
    private LinearLayout.LayoutParams layoutParamsWW = null;
    private LinearLayout.LayoutParams layoutParamsWS = null;

    public CheckNumView(Context context) {
        super(context);
    }

    public CheckNumView(Context context, Wheel wheel) {
        super(context);
        this.texts = wheel.getTexts();
        this.focusTextPosition = wheel.getFocusTextPosition();
        this.unit = wheel.getUnit();
        this.focusTextColor = context.getResources().getColor(wheel.getFocusTextColor());
        this.focusTextSize = context.getResources().getDimension(wheel.getFocusTextsize());
        this.optionTextColor = context.getResources().getColor(wheel.getOptionTextColor());
        this.optionTextSize = context.getResources().getDimension(wheel.getOptionTextsize());
        this.unitTextColor = context.getResources().getColor(wheel.getUnitTextColor());
        this.unitTextSize = context.getResources().getDimension(wheel.getUnitTextsize());
        layoutParamsWW = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParamsWS = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        initView(context);
    }

    /**
     * 初始化View
     *
     * @param context 上下文
     */
    @SuppressLint("ClickableViewAccessibility")
    private void initView(Context context) {
        RelativeLayout relayout = new RelativeLayout(context);
        textViews = new TextView[texts.length + 2];
        TextView unitView = new TextView(context);
        unitView.setText(unit);
        unitView.setGravity(Gravity.CENTER);
        unitView.setTextColor(unitTextColor);
        unitView.setTextSize(unitTextSize);
        unitView.getPaint().setTextSize(unitTextSize);
        unitView.setId(new Random().nextInt(100));

        LayoutParams lp2 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        lp2.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        relayout.addView(unitView, lp2);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        TextView top = new TextView(context);
        TextView bottom = new TextView(context);
        bottom.getPaint().setTextSize(optionTextSize);
        top.getPaint().setTextSize(optionTextSize);
        top.setGravity(Gravity.CENTER);
        bottom.setGravity(Gravity.CENTER);
        layoutParamsWS.height = WheelView.rowHeight;
        // 添加顶部空白
        layout.addView(top, layoutParamsWS);
        textViews[0] = top;
        for (int i = 0; i < texts.length; i++) {
            textViews[i + 1] = new TextView(context);
            textViews[i + 1].setText(texts[i]);
            textViews[i + 1].setTextColor(optionTextColor);
            textViews[i + 1].getPaint().setTextSize(optionTextSize);
            textViews[i + 1].setGravity(Gravity.CENTER);
            layout.addView(textViews[i + 1], layoutParamsWS);
        }
        // 添加底部空白
        layout.addView(bottom, layoutParamsWS);
        textViews[texts.length + 1] = bottom;

        layoutParamsWW.height = WheelView.rowHeight * 3;
        layoutParamsWW.gravity = CENTER_HORIZONTAL;
        scrollView = new WheelScrollView(context);
        scrollView.setId(new Random().nextInt(100));
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        scrollView.addView(layout, layoutParamsWW);
        LayoutParams lp = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.height = WheelView.rowHeight * 3;
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        lp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        lp.addRule(RelativeLayout.LEFT_OF, unitView.getId());
        lp.rightMargin = 15;
        relayout.addView(scrollView, lp);
        relayout.setGravity(Gravity.CENTER);
        addView(relayout);

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, focusTextPosition * WheelView.rowHeight);
            }
        });
        setFocusText(focusTextPosition + 1);
        scrollView.setOnScrollStopListener(new WheelScrollView.OnScrollStopListener() {

            @Override
            public void onStop(int y) {
                if (y != currentY) {
                    // 判断滚动误差，不到行高的一半就抹掉，超过行高的一半而不到一个行高就填满
                    if (y % WheelView.rowHeight >= (WheelView.rowHeight / 2)) {
                        y = y + WheelView.rowHeight - y % WheelView.rowHeight;
                        scrollView.scrollTo(0, y);
                    } else {
                        y = y - y % WheelView.rowHeight;
                        scrollView.scrollTo(0, y);
                    }
                    setFocusText(y / WheelView.rowHeight + 1);
                }
                currentY = y;
            }
        });
        for (TextView textView : textViews) {
            textView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View arg0, MotionEvent arg1) {
                    return true;
                }
            });
        }

    }

    /**
     * 设置焦点文字风格
     *
     * @param position 角标
     */
    private void setFocusText(int position) {
        if (this.position >= 0) {
            textViews[this.position].setTextColor(optionTextColor);
            textViews[this.position].getPaint().setTextSize(optionTextSize);
        }
        textViews[position].setTextColor(focusTextColor);
        textViews[position].getPaint().setTextSize(focusTextSize);
        this.position = position;
    }

    public String getNumber() {
        return texts[position - 1];
    }
}
