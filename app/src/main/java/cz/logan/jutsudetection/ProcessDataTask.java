package cz.logan.jutsudetection;

import android.os.AsyncTask;

import java.util.ArrayList;

class ProcessDataTask extends AsyncTask<Void, Void, Void> {

    private ArrayList<Float> eventValues;
    private JutsuGesture currentJutsuGesture;

    ProcessDataTask(ArrayList<Float> eventValues, JutsuGesture currentJutsuGesture) {
        this.eventValues = eventValues;
        this.currentJutsuGesture = currentJutsuGesture;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        currentJutsuGesture.updateData(eventValues);
        // TODO: allow users to scroll slightly but push them back onto the gesture they are currently on

        return null;
    }


}