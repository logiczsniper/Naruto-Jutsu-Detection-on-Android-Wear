package cz.logan.jutsudetection;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.reflect.TypeToken;


class JsonInteraction {

    private ArrayList<Integer> allJutsuDataResources = new ArrayList<>(7);
    private Integer jutsuDataJsonResource;
    final private Context context;
    private String jsonString;
    private Gson gson = new GsonBuilder().create();

    JsonInteraction(Integer jutsuDataJsonResource, Context context) {
        this.jutsuDataJsonResource = jutsuDataJsonResource;
        this.allJutsuDataResources.addAll(Arrays.asList(R.raw.jutsu_data_0, R.raw.jutsu_data_1, R.raw.jutsu_data_2,
                R.raw.jutsu_data_3, R.raw.jutsu_data_4, R.raw.jutsu_data_5, R.raw.jutsu_data_6));
        this.context = context;
    }

    void clearAll() {

        for (Integer jsonFileResource : allJutsuDataResources) {

        }

    }

    void clearSpecific() {

    }

    ArrayList<ArrayList<Float>> readJutsuDataFromJson() {
        try (InputStream inputStream = context.getResources().openRawResource(this.jutsuDataJsonResource)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            Type type = new TypeToken<HashMap<String, ArrayList<ArrayList<Float>>>>() {
            }.getType();
            HashMap<String, ArrayList<ArrayList<Float>>> gsonOutput = gson.fromJson(reader, type);
            return gsonOutput.get("values");

        } catch (IOException ignored) {
        }

        return null;
    }

    void writeJutsuDataToJson() {
        try (InputStream inputStream = context.getResources().openRawResource(this.jutsuDataJsonResource)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            Type type = new TypeToken<HashMap<String, ArrayList<ArrayList<Float>>>>() {
            }.getType();
            HashMap<String, ArrayList<ArrayList<Float>>> gsonOutput = gson.fromJson(reader, type);

        } catch (IOException ignored) {
        }
    }
}
