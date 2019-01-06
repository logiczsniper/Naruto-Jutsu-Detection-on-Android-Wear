package cz.logan.jutsudetection;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private Integer[][] jutsuImages = {{R.drawable.headband_fix, R.drawable.punch, R.drawable
            .shadow_clone, R.drawable.rasengan, R.drawable.summon, R.drawable.sage_mode, R
            .drawable.sexy_jutsu}};
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private SensorEventListener mSensorEventListener;
    private ArrayList<JutsuGesture> activeGestures = new ArrayList<>(200);
    private int pagerPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // set layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up GridViewPager
        final GridViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(new ImageAdapter(this));
        final DotsPageIndicator dotsPageIndicator = findViewById(R.id.page_indicator);
        dotsPageIndicator.setPager(pager);

        /*
        TODO: pass the correct data into the init methods of the JutsuGestures inside update below
        TODO: create constants of complex data types that can represent the required data to
        qualify a certain sequence of sensor data to mean that specific jutsu. This is to be used
        when creating instances of SpecificJutsu.
        TODO: the entire calibration sequence. This should involve the user pressing a button and
        thrusting the watch forward and back with reasonable speed. The highest acceleration
        recorded is saved to measure the speed at which an attack should be recognised. More
        importantly and with greater difficulty, save the user's facing direction. The user must
        stay facing that starting direction or else recalibrate the app. This is so that I can
        get some bearings as to what direction everything is in.
        TODO: when the screen changes iterate through active gestures and remove gestures that
        started sensing for the gesture of the previous screen.
        */

        // getting sensor data
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        mSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                pagerPosition = pager.getCurrentItem().x;

                // TODO: add conditional so that only larger changes in sensor data triggers this
                // TODO: be able to remove below comments without breaking the thing
                // JutsuGesture mJutsuGesture = new JutsuGesture(pagerPosition, event.values);
                // activeGestures.add(mJutsuGesture);

                // remove inactive gestures, update active gestures, empty if one completes
                ArrayList<JutsuGesture> inactiveGestures = new ArrayList<>();
                for (JutsuGesture currentJutsuGesture : activeGestures) {
                    switch (currentJutsuGesture.status) {
                        case "inactive":
                            inactiveGestures.add(currentJutsuGesture);
                            break;
                        case "completed":
                            inactiveGestures = activeGestures;
                            break;
                        default:
                            currentJutsuGesture.updateData(event.values);
                            break;
                    }
                }

                activeGestures.removeAll(inactiveGestures);
                //System.out.println(activeGestures.size());
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        mSensorManager.registerListener(mSensorEventListener, mSensor, 10);
    }

    // override GridPagerAdapter
    public class ImageAdapter extends GridPagerAdapter {
        final Context mContext;

        ImageAdapter(final Context context) {
            mContext = context;
        }

        @Override
        public int getRowCount() {
            return 1;
        }

        @Override
        public int getColumnCount(int i) {
            return 7;
        }

        @Override
        public int getCurrentColumnForRow(int row, int currentColumn) {
            return currentColumn;
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, int row, int col) {
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(jutsuImages[row][col]);
            viewGroup.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup viewGroup, int i, int i2, Object o) {
            viewGroup.removeView((View) o);
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view.equals(o);
        }
    }
}