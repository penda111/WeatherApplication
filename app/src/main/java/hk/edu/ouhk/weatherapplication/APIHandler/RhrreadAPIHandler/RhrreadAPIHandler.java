package hk.edu.ouhk.weatherapplication.APIHandler.RhrreadAPIHandler;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hk.edu.ouhk.weatherapplication.APIHandler.JsonHandlerThread;
import hk.edu.ouhk.weatherapplication.MainActivity;
import hk.edu.ouhk.weatherapplication.ui.home.HomeFragment;


public class RhrreadAPIHandler {

    private static final String TAG = "RhrreadAPIHandler";
    private static final String DATATYPE = "rhrread";
    public static String lang = "tc";

    public static ArrayList<String> iconList = new ArrayList<>();
    public static ArrayList<String> warningMessageList = new ArrayList<>();
    public static ArrayList<String> specialWxTipsList = new ArrayList<>();
    public static ArrayList<String> tcmessageList = new ArrayList<>();
    public static String updateTime;
    public static String rainstormReminder;

    private String url;
    public static JSONObject jsonObject;

    public RhrreadAPIHandler(){
        url = "https://data.weather.gov.hk/weatherAPI/opendata/weather.php?dataType="+DATATYPE+"&lang="+MainActivity.datalang;
        JsonHandlerThread jsonHandlerThread = new JsonHandlerThread(url,"Rhrread");
        jsonHandlerThread.start();
        try {
            jsonHandlerThread.join();
            //getJsonData();
        }catch (InterruptedException e){
        }
    }
    
    public static void getJsonData(){
        synchronized (HomeFragment.homelock) {
            try {

                updateTime = jsonObject.getString("updateTime");

                getLightningJson();
                getRainfallJson();
                getIconJson();
                getUvindexJson();
                getWarningMessageJson();
                getRainstormReminderJson();
                getSpecialWxTipsJson();
                getTcmessageJson();
                getTemperatureJson();
                getHumidityJson();

                Log.d(TAG, "Thread name=: " + Thread.currentThread().getName());
                Log.d(TAG, "iconList: " + iconList);
                Log.d(TAG, "warningMessageList: " + warningMessageList);
                Log.d(TAG, "specialWxTipsList: " + specialWxTipsList);
                Log.d(TAG, "tcmessageList: " + tcmessageList);
                Log.d(TAG, "updateTime: " + updateTime);
                Log.d(TAG, "rainstormReminder: " + rainstormReminder);

                Log.d(TAG, "Rainfall: " + Rainfall.rainfallList);
                Log.d(TAG, "Temperature: " + Temperature.tempList);

                Log.d(TAG, "Humidity: " + Humidity.humidityList);
                Log.d(TAG, "UVindex: " + UVindex.uvList);
                Log.d(TAG, "Lighting: " + Lighting.lightingList);

            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }
    }

    public static void getLightningJson(){
        try {
            if(jsonObject.has("lightning")){
                JSONObject lightning = jsonObject.getJSONObject("lightning");
                JSONArray lightningData = lightning.getJSONArray("data");
                for (int i = 0; i < lightningData.length(); i++) {
                    JSONObject c = lightningData.getJSONObject(i);

                    String place = c.getString("place");
                    String lightningOccur = c.getString("occur");
                    String lightningStartTime = c.getString("startTime");
                    String lightningEndTime = c.getString("endTime");

                    Lighting.addLightingData(place,lightningOccur,lightningStartTime,lightningEndTime);
                }
            }
        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    public static void getRainfallJson(){
        try {
            //Log.d(TAG, "(getRainfallJson)Thread name=: " +Thread.currentThread().getName());
            String rainfallMaxValue = null;
            String rainfallMinValue = null;
            JSONObject rainfall = jsonObject.getJSONObject("rainfall");
            JSONArray rainfallData = rainfall.getJSONArray("data");

            for (int i = 0; i < rainfallData.length(); i++) {
                JSONObject c = rainfallData.getJSONObject(i);

                String rainfallUnit = c.getString("unit");
                String place = c.getString("place");
                if (c.has("max")) {
                    rainfallMaxValue = c.getString("max");
                }
                if (c.has("min")) {
                    rainfallMinValue = c.getString("min");
                }
                String rainfallMaintain = c.getString("main");
                String rainfallStartTime = rainfall.getString("startTime");
                String rainfallEndTime = rainfall.getString("endTime");


                Rainfall.addRainfallData(place, rainfallMaxValue, rainfallMinValue, rainfallUnit, rainfallMaintain, rainfallStartTime, rainfallEndTime);
            }
        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    public static void getIconJson(){
        try {
            JSONArray iconJsonList = jsonObject.getJSONArray("icon");
            for (int i = 0; i < iconJsonList.length(); i++) {

                String iconNum = Integer.toString(iconJsonList.getInt(i));

                iconList.add(iconNum);
            }
        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    public static void getUvindexJson(){
        try {
            if(jsonObject.has("uvindex")) {
                if(jsonObject.get("uvindex") instanceof JSONObject) {
                    String uvMessage = "";
                    JSONObject unindex = jsonObject.getJSONObject("uvindex");
                    JSONArray unindexData = unindex.getJSONArray("data");

                    for (int i = 0; i < unindexData.length(); i++) {
                        JSONObject c = unindexData.getJSONObject(i);

                        String place = c.getString("place");
                        String uvValue = c.getString("value");
                        String uvDesc = c.getString("desc");

                        if (c.has("message")) {
                            uvMessage = c.getString("message");
                        }

                        UVindex.addUV(place, uvValue, uvDesc, uvMessage);
                    }
                }
            }
        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    public static void getWarningMessageJson(){
        try {
            if (jsonObject.has("warningMessage")) {

                if(jsonObject.get("warningMessage") instanceof JSONArray) {
                    JSONArray warningMessage = jsonObject.getJSONArray("warningMessage");

                    for (int i = 0; i < warningMessage.length(); i++) {

                        String warningMessageData = warningMessage.getString(i);

                        warningMessageList.add(warningMessageData);
                    }
                }
            }
        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    public static void getRainstormReminderJson(){
        try {
            if (jsonObject.has("rainstormReminder")) {
                rainstormReminder = jsonObject.getString("rainstormReminder");
            }
        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    public static void getSpecialWxTipsJson(){
        try {
            if (jsonObject.has("specialWxTips")) {
                JSONArray specialWxTips = jsonObject.getJSONArray("specialWxTips");

                for (int i = 0; i < specialWxTips.length(); i++) {

                    String specialWxTipsData = specialWxTips.getString(i);

                    specialWxTipsList.add(specialWxTipsData);
                }
            }
        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    public static void getTcmessageJson(){
        try {
            if (jsonObject.has("tcmessage")) {
                if(jsonObject.get("tcmessage") instanceof JSONArray) {
                    JSONArray tcmessage = jsonObject.getJSONArray("tcmessage");

                    for (int i = 0; i < tcmessage.length(); i++) {

                        String tcmessageData = tcmessage.getString(i);

                        tcmessageList.add(tcmessageData);
                    }
                }
            }
        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    public static void getTemperatureJson(){
        try {
            JSONObject temperature = jsonObject.getJSONObject("temperature");
            JSONArray temperatureData = temperature.getJSONArray("data");

            for (int i = 0; i < temperatureData.length(); i++) {
                JSONObject c = temperatureData.getJSONObject(i);

                String place = c.getString("place");
                String temperatureValue = c.getString("value");
                String temperatureUnit = c.getString("unit");
                String recordTime = temperature.getString("recordTime");

                Temperature.addTempData(place, temperatureValue, temperatureUnit, recordTime);
            }
        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    public static void getHumidityJson(){
        try {
            JSONObject humidity = jsonObject.getJSONObject("humidity");
            JSONArray humidityData = humidity.getJSONArray("data");

            for (int i = 0; i < humidityData.length(); i++) {
                JSONObject c = humidityData.getJSONObject(i);

                String place = c.getString("place");
                String humidityValue = c.getString("value");
                String humidityUnit = c.getString("unit");
                String recordTime = humidity.getString("recordTime");

                Humidity.addHumidity(place, humidityValue, humidityUnit, recordTime);
            }
        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    public static void changeLang(){
        if(lang.equals("tc")){
            lang = "en";
        }else{
            lang = "tc";
        }
    }
}
