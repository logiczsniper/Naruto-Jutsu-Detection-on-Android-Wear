package cz.logan.jutsudetection;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Allows for the interaction with the JSON file found in R.raw. This file contains the default
 * data for each jutsu that the user must come close to matching in order to classify as a
 * completed jutsu gesture.
 */
class JsonInteraction {

    final private Context context;
    private String jsonJutsuDataKey;
    private Gson gson = new GsonBuilder().create();

    /**
     * Saves the key to the value in the JSON file for the jutsu data that the interaction is
     * aimed at retrieving.
     *
     * @param jsonJutsuDataKey key to the value of required jutsuData in defined_jutsu_data.
     * @param context          MainActivity
     */
    JsonInteraction(String jsonJutsuDataKey, Context context) {
        this.jsonJutsuDataKey = jsonJutsuDataKey;
        this.context = context;
    }

    /**
     * Access defined_jutsu_data, retrieve and return the data that is specified by the
     * jsonJutsuDataKey.
     *
     * @return ArrayList<ArrayList<Float>> the defined jutsu data for the specified jutsu.
     * @see #jsonJutsuDataKey
     */
    ArrayList<ArrayList<Float>> readJutsuDataFromJson() {
        Integer jutsuDataJsonResource = R.raw.defined_jutsu_data;
        try (InputStream inputStream = context.getResources().openRawResource(jutsuDataJsonResource)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            Type type = new TypeToken<HashMap<String, ArrayList<ArrayList<Float>>>>() {
            }.getType();
            HashMap<String, ArrayList<ArrayList<Float>>> gsonOutput = gson.fromJson(reader, type);
            return gsonOutput.get(this.jsonJutsuDataKey);

        } catch (IOException ignored) {
        }

        return null;
    }
}
