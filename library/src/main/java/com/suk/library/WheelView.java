package com.suk.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


/**
 * 时间选择器控件
 *
 * @author Felix.Zhong
 */
public class WheelView extends RelativeLayout {
    /**
     * 默认年,月,日
     */
    public static String[] DEFAULT_YEARS = {"2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025"};
    public static String[] DEFAULT_MONTHS = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    public static String[] DEFAULT_DAYS = {
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
            "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24",
            "25", "26", "27", "28", "29", "30", "31"};
    /**
     * 中间位置图片背景
     */
    private int focusBackground, buttonBackground;
    private int buttonTextColor;
    private float buttonTextSize;
    public static int rowHeight;
    private CheckNumView[] numberViews;
    private Context context;
    private OnWheelClickListener mOnClickListener;
    private boolean buttonVisible;
    private LinearLayout.LayoutParams layoutParamsFW;
    private LinearLayout.LayoutParams layoutParamsWW;

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        layoutParamsFW = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParamsWW = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WheelView);
        focusBackground = a.getResourceId(R.styleable.WheelView_focusBackground, 0);
        buttonBackground = a.getResourceId(R.styleable.WheelView_buttonBackground, 0);
        buttonTextColor = a.getColor(R.styleable.WheelView_buttonTextColor, Color.WHITE);
        //因为getDimension方法会自动将sp转成dp，所以再将其转成sp
        buttonTextSize = a.getDimension(R.styleable.WheelView_buttonTextSize, 10);
        buttonTextSize = px2sp(buttonTextSize);
        rowHeight = (int) a.getDimension(R.styleable.WheelView_rowHeight, 20);
        buttonVisible = a.getBoolean(R.styleable.WheelView_buttonVisible, true);
        a.recycle();
    }

    /**
     * 获得结果
     *
     * @return String[]
     */
    public String[] getResult() {
        String[] nums = new String[numberViews.length];
        for (int i = 0; i < numberViews.length; i++) {
            nums[i] = numberViews[i].getNumber();
        }
        return nums;
    }

    /**
     * 配置控件参数
     * 1.每个Wheel代表一个选择项
     * 2.Wheel中配置了相关选项参数
     *
     * @param wheels Wheel[]
     */
    public void setWheels(Wheel[] wheels) {
        numberViews = new CheckNumView[wheels.length];
        for (int i = 0; i < wheels.length; i++) {
            numberViews[i] = new CheckNumView(context, wheels[i]);
        }
        Button btn = new Button(context);
        btn.setTextColor(buttonTextColor);
        btn.setTextSize(buttonTextSize);
        btn.setText(R.string.ok);
        btn.setBackgroundResource(buttonBackground);
        btn.setGravity(Gravity.CENTER);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mOnClickListener.onclick(getResult());
            }
        });
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layoutParamsFW.width = 0;
        layoutParamsFW.weight = 4;

        for (CheckNumView numberView : numberViews) {
            layout.addView(numberView, layoutParamsFW);
        }
        if (buttonVisible) {
            layoutParamsWW.gravity = Gravity.CENTER;
            layoutParamsWW.width = 0;
            layoutParamsWW.height = rowHeight / 5 * 4;
            layoutParamsWW.weight = 3;
            layoutParamsWW.rightMargin = rowHeight / 5;
            layout.addView(btn, layoutParamsWW);
        }

        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.height = WheelView.rowHeight;
        lp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        ImageView focusView = new ImageView(context);
        focusView.setBackgroundResource(focusBackground);
        addView(focusView, lp);

        LayoutParams lp2 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.height = WheelView.rowHeight * 3;
        lp2.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        addView(layout, lp2);
    }

    /**
     * 设置确定按钮点击事件
     *
     * @param mOnClickListener OnWheelClickListener
     */
    public void setOnClickListener(OnWheelClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public interface OnWheelClickListener {
        /**
         * 确定按钮点击事件
         *
         * @param values 选择器对应值数组
         */
        void onclick(String[] values);
    }

    public int px2sp(final float pxValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}
