package cz.logan.jutsudetection;

import java.util.ArrayList;
import java.util.Arrays;

class DataAnalyser {

    ArrayList<Float> getDataGap(ArrayList<Float> valuesOne, ArrayList<Float> valuesTwo) {

        Float dx = Math.abs(valuesOne.get(0) - valuesTwo.get(0));
        Float dy = Math.abs(valuesOne.get(1) - valuesTwo.get(1));
        Float dz = Math.abs(valuesOne.get(2) - valuesTwo.get(2));

        return new ArrayList<>(Arrays.asList(dx, dy, dz));
    }

}
