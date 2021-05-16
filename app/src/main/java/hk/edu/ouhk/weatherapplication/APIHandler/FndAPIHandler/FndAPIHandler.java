package hk.edu.ouhk.weatherapplication.APIHandler.FndAPIHandler;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import hk.edu.ouhk.weatherapplication.APIHandler.Database.DatabaseHandlerThread;
import hk.edu.ouhk.weatherapplication.APIHandler.JsonHandlerThread;
import hk.edu.ouhk.weatherapplication.MainActivity;


public class FndAPIHandler {

    private static final String TAG = "FndAPIHandler";
    private static final String DATATYPE = "fnd";
    public static String lang = "en";

    //static DatabaseHelper db = new DatabaseHelper(MainActivity.getContext());


    public static String generalSituation;
    public static String updateTime;

    private String url;
    public static JSONObject jsonObject;

    public FndAPIHandler(){
        if(lang != MainActivity.datalang){
            Log.d("System lang", MainActivity.datalang);
            changeLang();
        }

        url = "https://data.weather.gov.hk/weatherAPI/opendata/weather.php?dataType="+DATATYPE+"&lang="+MainActivity.datalang;
        JsonHandlerThread jsonHandlerThread = new JsonHandlerThread(url,"Fnd");
        jsonHandlerThread.start();
        try {
            jsonHandlerThread.join();
            DatabaseHandlerThread databaseHandlerThread = new DatabaseHandlerThread("Fnd");
            databaseHandlerThread.start();
            //getJsonData();
        }catch (InterruptedException e){
        }
    }
    public FndAPIHandler(String datalang){
        if(lang != MainActivity.datalang){
            Log.d("System lang", MainActivity.datalang);
            changeLang();
        }

        url = "https://data.weather.gov.hk/weatherAPI/opendata/weather.php?dataType="+DATATYPE+"&lang="+datalang;
        JsonHandlerThread jsonHandlerThread = new JsonHandlerThread(url,"Fnd");
        jsonHandlerThread.start();
        try {
            jsonHandlerThread.join();
            //getJsonData();
        }catch (InterruptedException e){
        }
    }


    public static void getJsonData(){
        try {
            generalSituation = jsonObject.getString("generalSituation");
            updateTime = jsonObject.getString("updateTime");

            getWeatherForecastJson();
            getSeaTempJson();
            getSoilTempJson();

            Log.d(TAG, "Thread name=: " +Thread.currentThread().getName());
            Log.d(TAG, "getJsonData: " + generalSituation+", "+updateTime);
            Log.d(TAG, "weatherForecast_9Days: " + WeatherForecast_9Days.weatherForecast_9Days);
            Log.d(TAG, "SeaTemp: " + SeaTemp.seaTemp);
            Log.d(TAG, "SoilTemp: " + SoilTemp.soilTemp);

        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    public static void getWeatherForecastJson(){
        try {
            JSONArray weatherForecast = jsonObject.getJSONArray("weatherForecast");
            WeatherForecast_9Days.weatherForecast_9Days.clear();
            for (int i = 0; i < weatherForecast.length(); i++) {
                JSONObject c = weatherForecast.getJSONObject(i);

                String forecastDate = c.getString("forecastDate");
                String week = c.getString("week");
                String forecastWind = c.getString("forecastWind");
                String forecastWeather = c.getString("forecastWeather");

                JSONObject forecastMaxtemp = c.getJSONObject("forecastMaxtemp");
                String forecastMaxtempValue = Integer.toString(forecastMaxtemp.getInt("value"));

                JSONObject forecastMintemp = c.getJSONObject("forecastMintemp");
                String forecastMintempValue = Integer.toString(forecastMintemp.getInt("value"));

                JSONObject forecastMaxrh = c.getJSONObject("forecastMaxrh");
                String forecastMaxrhValue = Integer.toString(forecastMaxrh.getInt("value"));

                JSONObject forecastMinrh = c.getJSONObject("forecastMinrh");
                String forecastMinrhValue = Integer.toString(forecastMinrh.getInt("value"));

                String forecastIcon = Integer.toString(c.getInt("ForecastIcon"));

                String PSR = c.getString("PSR");

                WeatherForecast_9Days.addWeatherForecast(forecastDate, week, forecastWind, forecastWeather, forecastMaxtempValue, forecastMintempValue, forecastMaxrhValue, forecastMinrhValue, forecastIcon, PSR);
            }
        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    public static void getSeaTempJson(){
        try {
            JSONObject seaTemp = jsonObject.getJSONObject("seaTemp");
            String seaTempPlace = seaTemp.getString("place");
            String seaTempValue = Integer.toString(seaTemp.getInt("value"));
            String seaTempUnit = seaTemp.getString("unit");
            String seaTempRecordTime = seaTemp.getString("recordTime");

            SeaTemp.addSeaTemp(seaTempPlace, seaTempValue, seaTempUnit, seaTempRecordTime);
        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    public static void getSoilTempJson(){
        try {
            JSONArray soilTemp = jsonObject.getJSONArray("soilTemp");
            for (int i = 0; i < soilTemp.length(); i++) {
                JSONObject c = soilTemp.getJSONObject(i);

                String soilTempPlace = c.getString("place");
                String soilTempValue = Integer.toString(c.getInt("value"));
                String soilTempUnit = c.getString("unit");
                String soilTempRecordTime = c.getString("recordTime");

                JSONObject depth = c.getJSONObject("depth");
                String soilDepthUnit = depth.getString("unit");
                String soilDepthValue = depth.getString("value");

                SoilTemp.addSoilTemp(soilTempPlace, soilTempValue, soilTempUnit, soilTempRecordTime, soilDepthUnit, soilDepthValue);
            }
        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    public static void storeDB(){

        //MainActivity.db.rebuildTable_Day();
        for(HashMap<String, String> weatherForecast : WeatherForecast_9Days.weatherForecast_9Days){
            MainActivity.db.createDay(weatherForecast);
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
