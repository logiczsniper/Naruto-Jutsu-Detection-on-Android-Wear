package cz.logan.jutsudetection;

import android.content.Context;
import android.media.MediaPlayer;

import java.sql.Timestamp;
import java.util.ArrayList;

class JutsuGesture {

    final private Context context;
    /*
    Different possible status values:
        "active" - the gesture attempts to identify a gesture every update
        "inactive" - the time ran out before the gesture was identified
        "completed" - the gesture was identified
     */
    String status = "active";
    private ArrayList<ArrayList<Float>> allData = new ArrayList<>();
    private float threshold;
    private Timestamp timestamp;
    private Integer audioClipResourceID;
    private String jsonJutsuDataKey;

    JutsuGesture(int pagerPosition, Context context) {
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.context = context;
        setDataFromPosition(pagerPosition);
    }

    private void setDataFromPosition(int pagerPosition) {

        switch (pagerPosition) {
            case 0:
                this.audioClipResourceID = R.raw.headband_tighten;
                this.threshold = 0;
                this.jsonJutsuDataKey = "values_headband";
                break;
            case 1:
                this.audioClipResourceID = R.raw.punch;
                this.threshold = 0;
                this.jsonJutsuDataKey = "values_punch";
                break;
            case 2:
                this.audioClipResourceID = R.raw.shadow_clone;
                this.threshold = 0;
                this.jsonJutsuDataKey = "values_shadow_clone";
                break;
            case 3:
                this.audioClipResourceID = R.raw.rasengan;
                this.threshold = 0;
                this.jsonJutsuDataKey = "values_rasengan";
                break;
            case 4:
                this.audioClipResourceID = R.raw.summoning_jutsu;
                this.threshold = 0;
                this.jsonJutsuDataKey = "values_summoning";
                break;
            case 5:
                this.audioClipResourceID = R.raw.sage_mode;
                this.threshold = 0;
                this.jsonJutsuDataKey = "values_sage_mode";
                break;
            default:
                this.audioClipResourceID = R.raw.sexy_jutsu;
                this.threshold = 0;
                this.jsonJutsuDataKey = "values_sexy";
                break;
        }
    }

    void updateData(ArrayList<Float> newData) {

        allData.add(newData);

        if (((new Timestamp(System.currentTimeMillis())).getTime() - this.timestamp.getTime()) /
                1000 >= 5) {

            // Assess the similarity between allData and the jutsuData in defined_jutsu_data
            JsonInteraction jsonInteraction = new JsonInteraction(jsonJutsuDataKey, context);
            ArrayList<ArrayList<Float>> jutsuData = jsonInteraction.readJutsuDataFromJson();

            int totalLength = jutsuData.size();
            int outsideThresholdCount = 0;

            for (ArrayList<Float> eventValuesJutsuData : jutsuData) {

                // save the current index.
                int currentIndex = jutsuData.indexOf(eventValuesJutsuData);

                // access the equivalent data in allData using the current index.
                try {
                    ArrayList<Float> eventValuesAllData = allData.get(currentIndex);

                    // count the number of times the change in the two data structures was greater
                    // than the allowed threshold.
                    DataAnalyser dataAnalyser = new DataAnalyser();

                    ArrayList<Float> dEventValues = dataAnalyser.getDataGap(eventValuesAllData,
                            eventValuesJutsuData);

                    for (Float value : dEventValues) {
                        if (value >= threshold) {
                            outsideThresholdCount++;
                        }

                    }

                } catch (IndexOutOfBoundsException ignored) {
                }

            }

            if (outsideThresholdCount <= totalLength * 0.1) {
                // if 90 percent or greater of the data is in alignment (within the threshold) with
                // the jutsu data, this is classified as completed.
                this.status = "completed";
            } else {
                // else, it is not accepted and the gesture becomes inactive.
                this.status = "inactive";
            }
        }
    }

    // play audio clip
    void playAudioClip() {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, this.audioClipResourceID);
        mediaPlayer.start();
    }
}
