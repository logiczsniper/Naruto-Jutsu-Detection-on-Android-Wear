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
    private Integer duration;
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
            case 1: // done
                this.audioClipResourceID = R.raw.headband_tighten;
                this.threshold = 8.95F;
                this.jsonJutsuDataKey = "values_headband";
                this.duration = 3;
                break;
            case 2: // done
                this.audioClipResourceID = R.raw.punch;
                this.threshold = 10.55F;
                this.jsonJutsuDataKey = "values_punch";
                this.duration = 2;
                break;
            case 3: // done
                this.audioClipResourceID = R.raw.shadow_clone;
                this.threshold = 11.95F;
                this.jsonJutsuDataKey = "values_shadow_clone";
                this.duration = 3;
                break;
            case 4: // done
                this.audioClipResourceID = R.raw.rasengan;
                this.threshold = 9.95F;
                this.jsonJutsuDataKey = "values_rasengan";
                this.duration = 4;
                break;
            case 5: // done
                this.audioClipResourceID = R.raw.summoning_jutsu;
                this.threshold = 19.00F;
                this.jsonJutsuDataKey = "values_summoning";
                this.duration = 3;
                break;
            case 6: // done
                this.audioClipResourceID = R.raw.sage_mode;
                this.threshold = 2.65F;
                this.jsonJutsuDataKey = "values_sage_mode";
                this.duration = 5;
                break;
            default: // done
                this.audioClipResourceID = R.raw.sexy_jutsu;
                this.threshold = 10.35F;
                this.jsonJutsuDataKey = "values_sexy";
                this.duration = 4;
                break;
        }
    }

    void updateData(ArrayList<Float> newData) {

        allData.add(newData);

        if (((new Timestamp(System.currentTimeMillis())).getTime() - this.timestamp.getTime()) /
                1000 >= this.duration) {

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

            if (outsideThresholdCount <= totalLength * 0.25) {
                // if the number of mistakes is less than 25 % of all data, it is accepted.
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
