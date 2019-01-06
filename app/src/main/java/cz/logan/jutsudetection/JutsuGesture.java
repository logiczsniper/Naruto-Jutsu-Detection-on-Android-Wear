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
    Structure of this data package:
        { {ALL X VALUES}, {ALL Y VALUES}, {ALL Z VALUES} }
     */
    ArrayList<float[]> allData = new ArrayList<>();

    private int threshold;
    private Timestamp timestamp;
    private int pagerPosition;
    private String audioClipPath;
    private ArrayList<float[]> jutsuData;

    JutsuGesture(int pagerPosition, float[] data) {
        allData.add(data);
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.pagerPosition = pagerPosition;
        setDataFromPosition(pagerPosition);
}

    private void setDataFromPosition(int pagerPosition) {
        // TODO: set complex data structure based on screen
        switch (pagerPosition) {
            case 0:
                this.audioClipPath = "";
                this.threshold = 0;
                break;
            case 1:
                this.audioClipPath = "";
                this.threshold = 0;
                break;
            case 2:
                this.audioClipPath = "";
                this.threshold = 0;
                break;
            case 3:
                this.audioClipPath = "";
                this.threshold = 0;
                break;
            case 4:
                this.audioClipPath = "";
                this.threshold = 0;
                break;
            case 5:
                this.audioClipPath = "";
                this.threshold = 0;
                break;
            default:
                this.audioClipPath = "";
                this.threshold = 0;
                break;
        }
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

    // play audio clip
    void playAudioClip() {

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
