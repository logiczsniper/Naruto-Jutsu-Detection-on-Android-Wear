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

/**
 * The main layout of the app. A grid view pager with 1 row and 8 columns. In each column, a
 * picture of a gesture apart from the first being the title screen.
 */
@Deprecated
public class ImageAdapter extends GridPagerAdapter {

    private final Context mContext;
    private ArrayList<Float> eventValues;
    private int pagerPosition;
    private Sensor mSensor;
    private SensorManager mSensorManager;
    private SensorEventListener mSensorEventListener;
    private GridViewPager pager;
    private JutsuGesture currentJutsuGesture;
    private MediaPlayer backgroundMusic;

    private Integer[][] backgroundImages = {{R.drawable.intro, R.drawable.headband_fix, R.drawable
            .punch, R.drawable
            .shadow_clone, R.drawable.rasengan, R.drawable.summon, R.drawable.sage_mode, R
            .drawable.sexy_jutsu}};

    /**
     * Starts background music for the first time, calls setUpSensors and saves other values.
     *
     * @param context       MainActivity.
     * @param pager         the instance of GridViewPager.
     * @param sensorManager the SensorManager created in onCreate.
     * @see #setUpSensors(SensorManager)
     */
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

    /**
     * Set the appropriate background image for each column, set the onClickListener. Functions
     * of this listener are described in line with block comments.
     *
     * @param viewGroup the viewGroup of the class.
     * @param row       the current row.
     * @param col       the current column.
     * @return ImageView the instance that was instantiated.
     */
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

    /**
     * The accelerometer provides data in the structure float[]. To work around this annoyance,
     * this method was created that takes a float[] and returns a far more accessible object.
     *
     * @param values the raw linear accelerometer output.
     * @return ArrayList<Float> the equivalent data in the new structure.
     */
    private ArrayList<Float> convertToPrimitive(float[] values) {
        ArrayList<Float> output = new ArrayList<>();

        for (float f : values) {
            Float newF = f;
            output.add(newF);
        }

        return output;
    }

    /**
     * In one method, most of the sensor operations are completed.
     *
     * @param sensorManager the sensor manager instance created in MainActivity.
     * @see #convertToPrimitive(float[])
     */
    private void setUpSensors(final SensorManager sensorManager) {
        this.mSensorManager = sensorManager;
        mSensor = Objects.requireNonNull(mSensorManager).getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        mSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                eventValues = convertToPrimitive(event.values);

                switch (currentJutsuGesture.status) {
                    case "inactive":
                        // stop listening for sensor data since it is done.
                        sensorManager.unregisterListener(mSensorEventListener, mSensor);
                        backgroundMusic.setVolume(0.4F, 0.4F);
                        break;
                    case "completed":
                        // stop listening for sensor data since it is done.
                        sensorManager.unregisterListener(mSensorEventListener, mSensor);
                        backgroundMusic.setVolume(0.4F, 0.4F);
                        currentJutsuGesture.playAudioClip();
                        break;
                    default:
                        // the jutsu is not over, send it the latest data.
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