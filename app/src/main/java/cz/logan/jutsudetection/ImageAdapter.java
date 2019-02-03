package cz.logan.jutsudetection;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.wearable.view.GridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Objects;

// override GridPagerAdapter
@Deprecated
public class ImageAdapter extends GridPagerAdapter {

    private final Context mContext;
    private ArrayList<Float> eventValues;
    private int pagerPosition;
    private Sensor mSensor;
    private SensorManager mSensorManager;
    private SensorEventListener mSensorEventListener;
    private Integer[][] jutsuImages = {{R.drawable.headband_fix, R.drawable.punch, R.drawable
            .shadow_clone, R.drawable.rasengan, R.drawable.summon, R.drawable.sage_mode, R
            .drawable.sexy_jutsu}};
    private GridViewPager pager;
    private JutsuGesture currentJutsuGesture;

    ImageAdapter(final Context context, GridViewPager pager, SensorManager sensorManager) {
        mContext = context;
        this.pager = pager;
        setUpSensors(sensorManager);
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
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // update pagerPosition before creating the gesture
                pagerPosition = pager.getCurrentItem().x;

                // create the Jutsu Gesture
                currentJutsuGesture = new JutsuGesture(pagerPosition, mContext);

                // start listening for sensor data
                mSensorManager.registerListener(mSensorEventListener, mSensor, 10000);
            }
        });
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

    private ArrayList<Float> convertToPrimitive(float[] values) {
        ArrayList<Float> output = new ArrayList<>();

        for (float f : values) {
            Float newF = f;
            output.add(newF);
        }

        return output;
    }

    private void setUpSensors(final SensorManager sensorManager) {
        this.mSensorManager = sensorManager;
        mSensor = Objects.requireNonNull(mSensorManager).getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        mSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                eventValues = convertToPrimitive(event.values);

                switch (currentJutsuGesture.status) {
                    case "inactive":
                        // stop listening for sensor data since it is done
                        System.out.println("YOU FAILED");
                        sensorManager.unregisterListener(mSensorEventListener, mSensor);
                        break;
                    case "completed":
                        // stop listening for sensor data since it is done
                        System.out.println("YOU DID IT LOGAN");
                        sensorManager.unregisterListener(mSensorEventListener, mSensor);
                        currentJutsuGesture.playAudioClip();
                        break;
                    default:
                        currentJutsuGesture.updateData(eventValues);
                        // new ProcessDataTask(eventValues, currentJutsuGesture).execute();
                        break;
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }
}