package cz.logan.jutsudetection;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Allows for the comparison of fundamental data structures of the app.
 */
class DataAnalyser {

    /**
     * Get the difference between each element in the two lists via absolute value.
     *
     * @param valuesOne the first ArrayList to be compared.
     * @param valuesTwo the second ArrayList to be compared.
     * @return ArrayList<Float> the differences between the two lists in the form
     * {difference in x values, difference in y values, difference in z values};
     */
    ArrayList<Float> getDataGap(ArrayList<Float> valuesOne, ArrayList<Float> valuesTwo) {

        Float dx = Math.abs(valuesOne.get(0) - valuesTwo.get(0));
        Float dy = Math.abs(valuesOne.get(1) - valuesTwo.get(1));
        Float dz = Math.abs(valuesOne.get(2) - valuesTwo.get(2));

        return new ArrayList<>(Arrays.asList(dx, dy, dz));
    }

}
