package com.felix.wheelview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.suk.library.Wheel;
import com.suk.library.WheelView;

public class MainActivity extends Activity {
    WheelView wheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wheel = findViewById(R.id.wheel);

        Wheel w1 = new Wheel(WheelView.DEFAULT_YEARS, "年", WheelView.DEFAULT_YEARS[WheelView.DEFAULT_YEARS.length / 2],
                R.color.ca7, R.color.ca2, R.color.ca7, R.dimen.da1, R.dimen.da2, R.dimen.da2);
        Wheel w2 = new Wheel(WheelView.DEFAULT_MONTHS, "月", WheelView.DEFAULT_MONTHS[WheelView.DEFAULT_MONTHS.length / 2],
                R.color.ca7, R.color.ca2, R.color.ca7, R.dimen.da1, R.dimen.da2, R.dimen.da2);
        Wheel w3 = new Wheel(WheelView.DEFAULT_MONTHS, "日", WheelView.DEFAULT_MONTHS[WheelView.DEFAULT_MONTHS.length / 2],
                R.color.ca7, R.color.ca2, R.color.ca7, R.dimen.da1, R.dimen.da2, R.dimen.da2);
        Wheel w4 = new Wheel(WheelView.DEFAULT_MONTHS, "日", WheelView.DEFAULT_MONTHS[WheelView.DEFAULT_MONTHS.length / 2],
                R.color.ca7, R.color.ca2, R.color.ca7, R.dimen.da1, R.dimen.da2, R.dimen.da2);
        Wheel[] ws = {w1, w2, w3,w4};
        wheel.setWheels(ws);
        wheel.setOnClickListener(new WheelView.OnWheelClickListener() {

            @Override
            public void onclick(String[] values) {
                StringBuilder sb = new StringBuilder();
                for (String value : values) {
                    sb.append(value).append(",");
                }
                Toast.makeText(MainActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
