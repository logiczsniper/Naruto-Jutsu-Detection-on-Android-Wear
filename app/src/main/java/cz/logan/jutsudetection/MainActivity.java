package cz.logan.jutsudetection;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.wearable.activity.WearableActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends WearableActivity {

    private ArrayList<JutsuGesture> activeGestures;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private SensorEventListener mSensorEventListener;
    private ViewPager viewPager;
    private LinearLayout basePage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        basePage = (LinearLayout) LayoutInflater.from(this).inflate(R
                .layout.page_base, null);

        /*
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        TODO: pass the correct data into the init methods of the JutsuGestures inside update below
        TODO: create superclass for specific jutsus. Upon init pass the following:
        - the complex data structure to represent the required sensor data
        - a forgiveness integer to be used as a range of sensor values that will still work
        - the path to the corresponding background image
        - the path to the corresponding audio clip
        TODO: create constants of complex data types that can represent the required data to
        qualify a certain sequence of sensor data to mean that specific jutsu. This is to be used
        when creating instances of SpecificJutsu.
        TODO: the entire calibration sequence. This should involve the user pressing a button and
        thrusting the watch forward and back with reasonable speed. The highest acceleration
        recorded is saved to measure the speed at which an attack should be recognised. More
        importantly and with greater difficulty, save the user's facing direction. The user must
        stay facing that starting direction or else recalibrate the app. This is so that I can
        get some bearings as to what direction everything is in.
        TODO: once the first hand signs are recognised, create a void method here to update the
        background image and play the audio clip based on the recognised jutsu.
        TODO: have the start of each gesture be recognised by a clap and hold of the hands!
        this will make the code much cleaner as instead of listening for gestures at all times it
        only listens for that one gesture first.

        After clap is recognised (play a sound), create one JutsuGesture and feed it the new sensor
        data for, by default, the next 5 seconds. At the end of the 5 seconds, JutsuGesture
        determines which jutsu was signed and runs a method in this class (mentioned above) to
        handle all the images and sound.

        TODO: main design

        activeGestures = new ArrayList<>(100);

        mSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {

                    JutsuGesture mJutsuGesture;

                    if (activeGestures.isEmpty() || event.values != activeGestures.get(0).allData.get
                            (0)) {
                        mJutsuGesture = new JutsuGesture(event.values);
                    }


                    // mTextView.setText(event.sensor.getName());
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        mSensorManager.registerListener(mSensorEventListener, mSensor, 10000);
        */

        class MainPageAdapter extends PagerAdapter {

            @Override
            public int getCount() {
                return 7;
            }

            @NonNull
            @Override
            public View instantiateItem(@NonNull ViewGroup collection, int position) {

                View page = basePage;
                System.out.println("_______________________" + position);
                switch (position) {
                    case 0:
                        page.setBackgroundResource(R.drawable.headband_fix);
                        break;
                    case 1:
                        page.setBackgroundResource(R.drawable.punch);
                        break;
                    case 2:
                        page.setBackgroundResource(R.drawable.shadow_clone);
                        break;
                    case 3:
                        page.setBackgroundResource(R.drawable.rasengan);
                        break;
                    case 4:
                        page.setBackgroundResource(R.drawable.summon);
                        break;
                    case 5:
                        page.setBackgroundResource(R.drawable.sage_mode);
                        break;
                    default:
                        page.setBackgroundResource(R.drawable.sexy_jutsu);
                        break;
                }

                collection.addView(page, 0);

                return page;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(View collection, int position, Object view) {
                ((ViewPager) collection).removeView((View) view);
            }

        }

        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 7;
            }

            @NonNull
            @Override
            public View instantiateItem(@NonNull ViewGroup collection, int position) {

                View page = basePage;
                System.out.println("_______________________" + position);
                switch (position) {
                    case 0:
                        page.setBackgroundResource(R.drawable.headband_fix);
                        break;
                    case 1:
                        page.setBackgroundResource(R.drawable.punch);
                        break;
                    case 2:
                        page.setBackgroundResource(R.drawable.shadow_clone);
                        break;
                    case 3:
                        page.setBackgroundResource(R.drawable.rasengan);
                        break;
                    case 4:
                        page.setBackgroundResource(R.drawable.summon);
                        break;
                    case 5:
                        page.setBackgroundResource(R.drawable.sage_mode);
                        break;
                    default:
                        page.setBackgroundResource(R.drawable.sexy_jutsu);
                        break;
                }

                collection.addView(page, 0);

                return page;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return false;
            }
        });
        viewPager.addOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

                try {
                    Toast.makeText(MainActivity.this, (viewPager.getAdapter().getPageTitle(position))
                            .toString() + (position + 1), Toast.LENGTH_SHORT)
                            .show();
                } catch (NullPointerException ne) {
                    Toast.makeText(MainActivity.this, "page " + (position + 1), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Enables Always-on
        setAmbientEnabled();
    }
}
