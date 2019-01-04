package cz.logan.jutsudetection;

import java.sql.Timestamp;

class JutsuGesture {

    String status = "active";
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    Integer[] currentData;
    Integer[] previousData;
    // TODO: combine these into one ordered list(all data) of ordered lists(sets of values)

    JutsuGesture(Integer[] data) {
        this.currentData = data;
    }

    void updateData(Integer[] data) {

        // TODO: have this method add to all data array

        this.previousData = this.currentData;
        this.currentData = data;

        if (((new Timestamp(System.currentTimeMillis())).getTime() - this.timestamp.getTime()) /
                1000 >= 5) {
            // After 5 seconds, JutsuGesture ends
            this.status = "finished";
        }
    }

    void identificationAttempt() {
        // TODO: iterate through all data and try to match it against one of the Specific Jutsu
        // TODO: classes. 
    }
}
