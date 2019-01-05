package cz.logan.jutsudetection;

import java.sql.Timestamp;
import java.util.ArrayList;

class JutsuGesture {

    /*
    Different possible status values:
        "active" - the gesture attempts to identify a gesture every update
        "inactive" - the time ran out before the gesture was identified
        "completed" - the gesture was identified
     */
    String status = "active";

    /*
    Saves the time of the declaration.
     */
    Timestamp timestamp;

    /*
    Structure of this data package:

        {
        {ALL X VALUES}, {ALL Y VALUES}, {ALL Z VALUES}
        }

     */
    ArrayList<float[]> allData = new ArrayList<>();

    JutsuGesture(float[] data) {
        allData.add(data);
        timestamp = new Timestamp(System.currentTimeMillis());
}

    void updateData(float[] newData) {

        allData.add(newData);

        if (((new Timestamp(System.currentTimeMillis())).getTime() - this.timestamp.getTime()) /
                1000 >= 5) {
            // After 5 seconds, JutsuGesture ends
            this.status = "inactive";
        }
    }

    void addToAllData() {

    }

    void identificationAttempt() {
        // TODO: iterate through all data and try to match it against one of the Specific Jutsu
        // TODO: classes.
        for (float[] dataPackage : this.allData) {

            for (float planeValue : dataPackage) {



            }
        }
    }
}
