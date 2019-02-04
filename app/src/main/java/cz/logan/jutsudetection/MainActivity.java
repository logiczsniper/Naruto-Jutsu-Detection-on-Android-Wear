package cz.logan.jutsudetection;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;

/**
 * Naruto Jutsu hand sign detection app.
 *
 * @author Logan Czernel
 * @since 03-01-2019
 */

public class MainActivity extends Activity {

    @Deprecated
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // set layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up GridViewPager & DotsPageIndicator
        GridViewPager pager = findViewById(R.id.pager);

        pager.setAdapter(new ImageAdapter(this, pager, (SensorManager) getSystemService(Context.SENSOR_SERVICE)));
        final DotsPageIndicator dotsPageIndicator = findViewById(R.id.page_indicator);
        dotsPageIndicator.setPager(pager);
    }
}