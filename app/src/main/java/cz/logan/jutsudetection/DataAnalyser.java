package cz.logan.jutsudetection;

import java.util.ArrayList;

class DataAnalyser {

    boolean isMajorDataChange(ArrayList<Float> previousEventValues, ArrayList<Float>
            currentSensorValues, float buffer) {

        for (int x = 0; x <= 2; x++) {

            if (Math.abs(previousEventValues.get(x) - currentSensorValues.get(x)) > buffer) {
                return true;
            }
        }

        return false;
    }

    // TODO: this should allow you to compare two
    // ArrayList<Float> objects. Must be accessible by both this class and JutsuGesture. Will be
    // used to assert how similar the input data is to the Jutsu data.
    // returns average gap between each of the values (x, y, z) or total gap.
    /*
    ArrayList<Float> getDataGap() {

    }
    */
}
