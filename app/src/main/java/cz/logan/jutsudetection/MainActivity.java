package cz.logan.jutsudetection;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends WearableActivity {

    private TextView mTextView;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private SensorEventListener mSensorEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        /*
        TODO: create array of active gestures and the logic to add/remove new or old JutsuGestures
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
        */
        final Integer[] mData = {};

        final JutsuGesture mJutsuGesture = new JutsuGesture(mData);

        mSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                System.out.println("Values: " + Arrays.toString(event.values) + "\n\n");

                mJutsuGesture.updateData(mData);
                // mTextView.setText(event.sensor.getName());
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        mSensorManager.registerListener(mSensorEventListener, mSensor, 10);

        // Enables Always-on
        setAmbientEnabled();
    }
}
