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

    ArrayList<ArrayList<Float>> allData = new ArrayList<>();

    private float threshold;
    private Timestamp timestamp;
    private int pagerPosition;
    private Integer audioClipResourceID;
    private ArrayList<Float[]> jutsuData;

    JutsuGesture(int pagerPosition, ArrayList<Float> data) {
        allData.add(data);
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.pagerPosition = pagerPosition;
        setDataFromPosition(pagerPosition);
}

    private void setDataFromPosition(int pagerPosition) {
        // TODO: set complex data structure based on screen
        switch (pagerPosition) {
            case 0:
                this.audioClipResourceID = R.raw.headband_tighten;
                this.threshold = 0;
                break;
            case 1:
                this.audioClipResourceID = R.raw.punch;
                this.threshold = 0;
                break;
            case 2:
                this.audioClipResourceID = R.raw.shadow_clone;
                this.threshold = 0;
                break;
            case 3:
                this.audioClipResourceID = R.raw.rasengan;
                this.threshold = 0;
                break;
            case 4:
                this.audioClipResourceID = R.raw.summoning_jutsu;
                this.threshold = 0;
                break;
            case 5:
                this.audioClipResourceID = R.raw.sage_mode;
                this.threshold = 0;
                break;
            default:
                this.audioClipResourceID = R.raw.sexy_jutsu;
                this.threshold = 0;
                break;
        }
    }

    void updateData(ArrayList<Float> newData) {

        allData.add(newData);

        if (((new Timestamp(System.currentTimeMillis())).getTime() - this.timestamp.getTime()) /
                1000 >= 5) {
            // After 5 seconds, JutsuGesture ends
            this.status = "inactive";
        }
    }

    // play audio clip
    void playAudioClip() {

    }
}
