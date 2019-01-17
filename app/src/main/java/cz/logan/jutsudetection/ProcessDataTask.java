package cz.logan.jutsudetection;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;

import java.util.ArrayList;

class ProcessDataTask extends AsyncTask<Void, Void, Void> {

    private ArrayList<Float> eventValues;
    private JutsuGesture currentJutsuGesture;
    private SensorManager sensorManager;
    private Sensor mSensor;
    private SensorEventListener mSensorEventListener;

    ProcessDataTask(ArrayList<Float> eventValues, int pagerPosition, SensorManager sensorManager, Sensor mSensor, SensorEventListener mSensorEventListener) {
        this.eventValues = eventValues;
        currentJutsuGesture = new JutsuGesture(pagerPosition);
        this.sensorManager = sensorManager;
        this.mSensor = mSensor;
        this.mSensorEventListener = mSensorEventListener;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        // remove inactive gestures, update active gestures, empty if one completes
        switch (currentJutsuGesture.status) {
            case "inactive":
                // stop listening for sensor data since it is done
                sensorManager.unregisterListener(mSensorEventListener, mSensor);
                break;
            case "completed":
                sensorManager.unregisterListener(mSensorEventListener, mSensor);
                // TODO: play sound HERE
                break;
            default:
                currentJutsuGesture.updateData(eventValues);
                break;
        }

        return null;
    }
}