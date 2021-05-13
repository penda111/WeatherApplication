package hk.edu.ouhk.weatherapplication.APIHandler.SwtAPIHandler;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import hk.edu.ouhk.weatherapplication.APIHandler.JsonHandlerThread;
import hk.edu.ouhk.weatherapplication.APIHandler.WarningInfoAPIHandler.WarningInfo;


public class SwtAPIHandler {

    private static final String TAG = "SwtAPIHandler";
    private static final String DATATYPE = "swt";
    private static String lang = "tc";

    private String url;
    public static JSONObject jsonObject;

    public SwtAPIHandler(){
        url = "https://data.weather.gov.hk/weatherAPI/opendata/weather.php?dataType="+DATATYPE+"&lang="+lang;
        JsonHandlerThread jsonHandlerThread = new JsonHandlerThread(url,"Swt");
        jsonHandlerThread.start();
        try {
            jsonHandlerThread.join();
            //getJsonData();
        }catch (InterruptedException e){
        }
    }

    public void loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("swtTest.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            //return null;
        }
        try {
            jsonObject = new JSONObject(json);
        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
        //return json;
    }

    public static void getJsonData(){

        if(jsonObject.has("swt")) {

            getSwtJson();
        }

        Log.d(TAG, "getJsonData(): "+ Swt.swtList);

    }

    public static void getSwtJson(){
        try {
            //Log.d(TAG, "(getRainfallJson)Thread name=: " +Thread.currentThread().getName());
            String desc = null;
            String updateTime= null;
            JSONArray swt = jsonObject.getJSONArray("swt");

            for (int i = 0; i < swt.length(); i++) {
                desc = null;
                updateTime= null;
                JSONObject c = swt.getJSONObject(i);
                if(c.has("desc")) {
                    desc = c.getString("desc");
                }
                if(c.has("updateTime")) {
                    updateTime = c.getString("updateTime");
                }

                Swt.addSwtData(desc, updateTime);
            }
        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }


    public void changeLang(){
        if(lang.equals("tc")){
            lang = "en";
        }else{
            lang = "tc";
        }
    }
}
