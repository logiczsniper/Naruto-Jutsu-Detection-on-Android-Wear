package cz.logan.jutsudetection;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.wearable.view.GridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

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
    private Integer[][] backgroundImages = {{R.drawable.intro, R.drawable.headband_fix, R.drawable
            .punch, R.drawable
            .shadow_clone, R.drawable.rasengan, R.drawable.summon, R.drawable.sage_mode, R
            .drawable.sexy_jutsu}};
    private GridViewPager pager;
    private JutsuGesture currentJutsuGesture;
    private MediaPlayer backgroundMusic;

    ImageAdapter(final Context context, GridViewPager pager, SensorManager sensorManager) {
        mContext = context;
        this.pager = pager;
        setUpSensors(sensorManager);
        backgroundMusic = MediaPlayer.create(mContext, R.raw.background_music);
        backgroundMusic.setVolume(0.4F, 0.4F);
        backgroundMusic.setLooping(true);
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount(int i) {
        return 8;
    }

    @Override
    public int getCurrentColumnForRow(int row, int currentColumn) {
        return currentColumn;
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int row, int col) {

        backgroundMusic.start();

        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(backgroundImages[row][col]);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // update pagerPosition before creating the gesture
                pagerPosition = pager.getCurrentItem().x;

                if (pagerPosition != 0) {

                    backgroundMusic.setVolume(0.1F, 0.1F);

                    // create the Jutsu Gesture
                    currentJutsuGesture = new JutsuGesture(pagerPosition, mContext);

                    // start listening for sensor data
                    mSensorManager.registerListener(mSensorEventListener, mSensor, 10000);
                } else {
                    Toast.makeText(mContext, "Swipe right to get started!",
                            Toast.LENGTH_LONG).show();
                }
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
                        sensorManager.unregisterListener(mSensorEventListener, mSensor);
                        backgroundMusic.setVolume(0.4F, 0.4F);
                        break;
                    case "completed":
                        // stop listening for sensor data since it is done
                        sensorManager.unregisterListener(mSensorEventListener, mSensor);
                        backgroundMusic.setVolume(0.4F, 0.4F);
                        currentJutsuGesture.playAudioClip();
                        break;
                    default:
                        currentJutsuGesture.updateData(eventValues);
                        break;
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }
}