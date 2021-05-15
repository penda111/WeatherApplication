package hk.edu.ouhk.weatherapplication.APIHandler.ffcWeatherAPIHandler;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import hk.edu.ouhk.weatherapplication.APIHandler.FeltearthquakeAPIHandler.Feltearthquake;
import hk.edu.ouhk.weatherapplication.APIHandler.JsonHandlerThread;

public class ffcWeatherAPIHandler {
    private static final String TAG ="ffcWeatherAPIHandler";
    private String url;
    public static JSONObject jsonObject;
    public static String lat = "22.4458108";
    public static String lon= "113.9967013";
    public ffcWeatherAPIHandler(){
        url = "https://fcc-weather-api.glitch.me/api/current?lat="+ lat +"&lon=" +lon;
        JsonHandlerThread jsonHandlerThread = new JsonHandlerThread(url,"ffc");
        jsonHandlerThread.start();
        try {
            jsonHandlerThread.join();
            //getJsonData();
        }catch (InterruptedException e){
        }

}

    public static void getJsonData(){

        if(jsonObject.has("main") && jsonObject.has("wind")) {
            getffcJson();
        }

        Log.d(TAG, "getJsonData(): "+ ffcWeather.ffcList);

    }
    public static void setLocation(String alat, String alon){
        lat = alat;
        lon = alon;
    }

    public static void getffcJson(){
        try {
            ffcWeather.ffcList.clear();
            String temp = Double.toString(jsonObject.getJSONObject("main").getDouble("temp"));
            String tempmin = Double.toString(jsonObject.getJSONObject("main").getDouble("temp_min"));
            String tempmax = Double.toString(jsonObject.getJSONObject("main").getDouble("temp_max"));
            String windspeed = Double.toString(jsonObject.getJSONObject("wind").getDouble("speed"));

            ffcWeather.addffcData(temp, tempmin, tempmax, windspeed);
        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

}
