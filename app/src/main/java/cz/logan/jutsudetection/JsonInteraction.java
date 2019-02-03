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


class JsonInteraction {

    private String jsonJutsuDataKey;
    final private Context context;
    private Gson gson = new GsonBuilder().create();

    JsonInteraction(String jsonJutsuDataKey, Context context) {
        this.jsonJutsuDataKey = jsonJutsuDataKey;
        this.context = context;
    }

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
