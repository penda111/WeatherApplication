package hk.edu.ouhk.weatherapplication.APIHandler;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


public class FlwAPIHandler {

    private static final String TAG = "FlwAPIHandler";
    private static final String DATATYPE = "flw";
    private static String lang = "tc";

    public static String generalSituation;
    public static String tcInfo;
    public static String fireDangerWarning;
    public static String forecastPeriod;
    public static String forecastDesc;
    public static String outlook;
    public static String updateTime;

    private String url;
    public static JSONObject jsonObject;

    public FlwAPIHandler(){
        url = "https://data.weather.gov.hk/weatherAPI/opendata/weather.php?dataType="+DATATYPE+"&lang="+lang;
        JsonHandlerThread jsonHandlerThread = new JsonHandlerThread(url,"Flw");
        jsonHandlerThread.start();
        try {
            jsonHandlerThread.join();
            getJsonData();
        }catch (InterruptedException e){
        }
    }

    public static void getJsonData(){
        try {
            generalSituation = jsonObject.getString("generalSituation");
            tcInfo = jsonObject.getString("tcInfo");
            fireDangerWarning = jsonObject.getString("fireDangerWarning");
            forecastPeriod = jsonObject.getString("forecastPeriod");
            forecastDesc = jsonObject.getString("forecastDesc");
            outlook = jsonObject.getString("outlook");
            updateTime = jsonObject.getString("updateTime");

            Log.d(TAG, "getJsonData(): " +generalSituation +", "+ tcInfo +", "+ fireDangerWarning +", "+ forecastPeriod+", "+forecastDesc +", "+outlook+", "+updateTime);


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
