package hk.edu.ouhk.weatherapplication.APIHandler.WarningInfoAPIHandler;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import hk.edu.ouhk.weatherapplication.APIHandler.JsonHandlerThread;


public class WarningInfoAPIHandler {

    private static final String TAG = "WarningInfoAPIHandler";
    private static final String DATATYPE = "warningInfo";
    private static String lang = "tc";

    private String url;
    public static JSONObject jsonObject;

    public WarningInfoAPIHandler(){
        url = "https://data.weather.gov.hk/weatherAPI/opendata/weather.php?dataType="+DATATYPE+"&lang="+lang;
        JsonHandlerThread jsonHandlerThread = new JsonHandlerThread(url,"WarningInfo");
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
            InputStream is = context.getAssets().open("warningInfoTest.json");
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

        if(jsonObject.has("details")) {

            getWarningInfoJson();
        }

        Log.d(TAG, "getJsonData(): "+ WarningInfo.warningInfoList);

    }

    public static void getWarningInfoJson(){
        try {
            //Log.d(TAG, "(getRainfallJson)Thread name=: " +Thread.currentThread().getName());
            String subtype = null;
            JSONArray details = jsonObject.getJSONArray("details");

            for (int i = 0; i < details.length(); i++) {
                JSONObject c = details.getJSONObject(i);
                JSONArray contents = c.getJSONArray("contents");

                ArrayList<String> contentsArrayList = new ArrayList<String>();
                if (contents != null) {
                    for (int j=0;j<contents.length();j++){
                        contentsArrayList.add(contents.getString(j));
                    }
                }

                String warningStatementCode = c.getString("warningStatementCode");
                if (c.has("subtype")) {
                    subtype = c.getString("subtype");
                }
                String updateTime = c.getString("updateTime");

                WarningInfo.addWarningInfoData(contentsArrayList, warningStatementCode, subtype, updateTime);
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
