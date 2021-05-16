package hk.edu.ouhk.weatherapplication.APIHandler.QemAPIHandler;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import hk.edu.ouhk.weatherapplication.APIHandler.JsonHandlerThread;
import hk.edu.ouhk.weatherapplication.MainActivity;


public class QemAPIHandler {

    private static final String TAG = "QemAPIHandler";
    private static final String DATATYPE = "qem";
    private static String lang = "tc";

    private String url;
    public static JSONObject jsonObject;

    public QemAPIHandler(){
        url = "https://data.weather.gov.hk/weatherAPI/opendata/earthquake.php?dataType="+DATATYPE+"&lang="+ MainActivity.datalang;
        JsonHandlerThread jsonHandlerThread = new JsonHandlerThread(url,"Qem");
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

        if(jsonObject != null) {

            getQemJson();
        }

        Log.d(TAG, "getJsonData(): "+ Qem.qemList);

    }

    public static void getQemJson(){
        try {
            String lat = Double.toString(jsonObject.getDouble("lat"));
            String lon = Double.toString(jsonObject.getDouble("lon"));
            String mag = Double.toString(jsonObject.getDouble("mag"));
            String region = jsonObject.getString("region");
            String ptime = jsonObject.getString("ptime");
            String updateTime = jsonObject.getString("updateTime");

            Qem.addQemData(lat, lon, mag, region, ptime, updateTime);

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
