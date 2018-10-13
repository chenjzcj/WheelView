日期滚动选择器，使用效果如下图所示：

![日期选择器](https://upload-images.jianshu.io/upload_images/8903781-f1f5b9f474e0f486.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

maven引用：

    <dependency>
      <groupId>com.felix</groupId>
      <artifactId>wheelview</artifactId>
      <version>1.0.0</version>
      <type>pom</type>
    </dependency>
    
gradle引用：

    implementation 'com.felix:wheelview:1.0.0'
    
使用方法：

    <com.suk.library.WheelView
            android:id="@+id/wheel"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_centerInParent="true"
            android:background="@drawable/wheel_view_bg"
            app:buttonBackground="@drawable/btn_selecter"
            app:buttonTextSize="12sp"
            app:focusBackground="@drawable/focus_bg"
            app:rowHeight="50dp" />
       
1. buttonBackground:按钮背景
2. buttonTextSize:按钮文字尺寸
3. focusBackground:文字选中时的背景颜色
4. rowHeight:滚动的高度
            
在Activity中进行初始化设置：

        wheel = findViewById(R.id.wheel);

        Wheel w1 = new Wheel(WheelView.DEFAULT_YEARS, "年", WheelView.DEFAULT_YEARS[WheelView.DEFAULT_YEARS.length / 2],
                R.color.ca7, R.color.ca2, R.color.ca7, R.dimen.focus_size, R.dimen.option_size, R.dimen.focus_size);
        Wheel w2 = new Wheel(WheelView.DEFAULT_MONTHS, "月", WheelView.DEFAULT_MONTHS[WheelView.DEFAULT_MONTHS.length / 2],
                R.color.ca7, R.color.ca2, R.color.ca7, R.dimen.focus_size, R.dimen.option_size, R.dimen.focus_size);
        Wheel w3 = new Wheel(WheelView.DEFAULT_DAYS, "日", WheelView.DEFAULT_DAYS[WheelView.DEFAULT_DAYS.length / 2],
                R.color.ca7, R.color.ca2, R.color.ca7, R.dimen.focus_size, R.dimen.option_size, R.dimen.focus_size);
        Wheel[] ws = {w1, w2, w3};
        wheel.setWheels(ws);
        wheel.setOnClickListener(new WheelView.OnWheelClickListener() {

            @Override
            public void onclick(String[] values) {
                StringBuilder sb = new StringBuilder();
                for (String value : values) {
                    sb.append(value).append("-");
                }
                Toast.makeText(MainActivity.this, sb.substring(0, sb.length() - 1), Toast.LENGTH_SHORT).show();
            }
        });