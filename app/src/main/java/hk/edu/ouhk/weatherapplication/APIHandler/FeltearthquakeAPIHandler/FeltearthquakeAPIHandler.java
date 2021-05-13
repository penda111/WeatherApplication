package hk.edu.ouhk.weatherapplication.APIHandler.FeltearthquakeAPIHandler;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import hk.edu.ouhk.weatherapplication.APIHandler.JsonHandlerThread;
import hk.edu.ouhk.weatherapplication.APIHandler.QemAPIHandler.Qem;


public class FeltearthquakeAPIHandler {

    private static final String TAG = "FeltearthquakeAPIHandler";
    private static final String DATATYPE = "feltearthquake";
    private static String lang = "tc";

    private String url;
    public static JSONObject jsonObject;

    public FeltearthquakeAPIHandler(){
        url = "https://data.weather.gov.hk/weatherAPI/opendata/earthquake.php?dataType="+DATATYPE+"&lang="+lang;
        JsonHandlerThread jsonHandlerThread = new JsonHandlerThread(url,"Feltearthquake");
        jsonHandlerThread.start();
        try {
            jsonHandlerThread.join();
            //getJsonData();
        }catch (InterruptedException e){
        }
    }


    public static void getJsonData(){

        if(jsonObject.has("ptime")) {

            getfeltearthquakeJson();
        }

        Log.d(TAG, "getJsonData(): "+ Feltearthquake.feltearthquakeList);

    }

    public static void getfeltearthquakeJson(){
        try {
            String lat = Double.toString(jsonObject.getDouble("lat"));
            String lon = Double.toString(jsonObject.getDouble("lon"));
            String mag = Double.toString(jsonObject.getDouble("mag"));
            String region = jsonObject.getString("region");
            String intensity = Double.toString(jsonObject.getDouble("intensity"));
            String details = jsonObject.getString("details");
            String ptime = jsonObject.getString("ptime");
            String updateTime = jsonObject.getString("updateTime");

            Feltearthquake.addFeltearthquakeData(lat, lon, mag, region, intensity, details, ptime, updateTime);

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
