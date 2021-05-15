package hk.edu.ouhk.weatherapplication.APIHandler.WarnsumAPIHandler;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import hk.edu.ouhk.weatherapplication.APIHandler.JsonHandlerThread;


public class WarnsumAPIHandler {

    private static final String TAG = "WarnsumAPIHandler";
    private static final String DATATYPE = "warnsum";
    private static String lang = "tc";

    private String url;
    public static JSONObject jsonObject;

    public WarnsumAPIHandler(){
        url = "https://data.weather.gov.hk/weatherAPI/opendata/weather.php?dataType="+DATATYPE+"&lang="+lang;
        JsonHandlerThread jsonHandlerThread = new JsonHandlerThread(url,"Warnsum");
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
            InputStream is = context.getAssets().open("warnsumTest.json");
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
        Warnsum.clearList();
        getWfireJson();
        getWfrostJson();
        getWhotJson();
        getWcoldJson();
        getWmshnlJson();
        getWrainJson();
        getWfntsaJson();
        getWlJson();
        getWtcsgnlJson();
        getWtmwJson();
        getWtsJson();

        Log.d(TAG, "getJsonData(): "+ Warnsum.warnsumList);

    }

    public static void getWfireJson(){
        try {

            if (jsonObject.has("WFIRE")) {
                String expireTime = null;
                String type = null;
                JSONObject wfire = jsonObject.getJSONObject("WFIRE");

                String name = wfire.getString("name");
                String code = wfire.getString("code");
                if (wfire.has("type")) {
                    type = wfire.getString("type");
                }
                String actionCode = wfire.getString("actionCode");
                String issueTime = wfire.getString("issueTime");
                if (wfire.has("expireTime")){
                    expireTime = wfire.getString("expireTime");
                }
                String updateTime = wfire.getString("updateTime");

                Warnsum.addWarnsumData(name, code, type, actionCode, issueTime, expireTime, updateTime);
            }
        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    public static void getWfrostJson(){
        try {
            if (jsonObject.has("WFROST")) {
                String expireTime = null;
                String type = null;
                JSONObject wfrost = jsonObject.getJSONObject("WFROST");

                String name = wfrost.getString("name");
                String code = wfrost.getString("code");
                if (wfrost.has("type")) {
                    type = wfrost.getString("type");
                }
                String actionCode = wfrost.getString("actionCode");
                String issueTime = wfrost.getString("issueTime");
                if (wfrost.has("expireTime")){
                    expireTime = wfrost.getString("expireTime");
                }
                String updateTime = wfrost.getString("updateTime");

                Warnsum.addWarnsumData(name, code, type, actionCode, issueTime, expireTime, updateTime);
            }
        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    public static void getWhotJson(){
        try {
            if (jsonObject.has("WHOT")) {
                String expireTime = null;
                String type = null;
                JSONObject whot = jsonObject.getJSONObject("WHOT");

                String name = whot.getString("name");
                String code = whot.getString("code");
                if (whot.has("type")) {
                    type = whot.getString("type");
                }
                String actionCode = whot.getString("actionCode");
                String issueTime = whot.getString("issueTime");
                if (whot.has("expireTime")){
                    expireTime = whot.getString("expireTime");
                }
                String updateTime = whot.getString("updateTime");

                Warnsum.addWarnsumData(name, code, type, actionCode, issueTime, expireTime, updateTime);
            }
        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    public static void getWcoldJson(){
        try {
            if (jsonObject.has("WCOLD")) {
                String expireTime = null;
                String type = null;
                JSONObject wcold = jsonObject.getJSONObject("WCOLD");

                String name = wcold.getString("name");
                String code = wcold.getString("code");
                if (wcold.has("type")) {
                    type = wcold.getString("type");
                }
                String actionCode = wcold.getString("actionCode");
                String issueTime = wcold.getString("issueTime");
                if (wcold.has("expireTime")){
                    expireTime = wcold.getString("expireTime");
                }
                String updateTime = wcold.getString("updateTime");

                Warnsum.addWarnsumData(name, code, type, actionCode, issueTime, expireTime, updateTime);
            }
        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    public static void getWmshnlJson(){
        try {
            if (jsonObject.has("WMSGNL")) {
                String expireTime = null;
                String type = null;
                JSONObject wmsgnl = jsonObject.getJSONObject("WMSGNL");

                String name = wmsgnl.getString("name");
                String code = wmsgnl.getString("code");
                if (wmsgnl.has("type")) {
                    type = wmsgnl.getString("type");
                }
                String actionCode = wmsgnl.getString("actionCode");
                String issueTime = wmsgnl.getString("issueTime");
                if (wmsgnl.has("expireTime")){
                    expireTime = wmsgnl.getString("expireTime");
                }
                String updateTime = wmsgnl.getString("updateTime");

                Warnsum.addWarnsumData(name, code, type, actionCode, issueTime, expireTime, updateTime);
            }
        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    public static void getWrainJson(){
        try {
            if (jsonObject.has("WRAIN")) {
                String expireTime = null;
                String type = null;
                JSONObject wrain = jsonObject.getJSONObject("WRAIN");

                String name = wrain.getString("name");
                String code = wrain.getString("code");
                if (wrain.has("type")) {
                    type = wrain.getString("type");
                }
                String actionCode = wrain.getString("actionCode");
                String issueTime = wrain.getString("issueTime");
                if (wrain.has("expireTime")){
                    expireTime = wrain.getString("expireTime");
                }
                String updateTime = wrain.getString("updateTime");

                Warnsum.addWarnsumData(name, code, type, actionCode, issueTime, expireTime, updateTime);
            }
        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    public static void getWfntsaJson(){
        try {
            if (jsonObject.has("WFNTSA")) {
                String expireTime = null;
                String type = null;
                JSONObject wfntsa = jsonObject.getJSONObject("WFNTSA");

                String name = wfntsa.getString("name");
                String code = wfntsa.getString("code");
                if (wfntsa.has("type")) {
                    type = wfntsa.getString("type");
                }
                String actionCode = wfntsa.getString("actionCode");
                String issueTime = wfntsa.getString("issueTime");
                if (wfntsa.has("expireTime")){
                    expireTime = wfntsa.getString("expireTime");
                }
                String updateTime = wfntsa.getString("updateTime");

                Warnsum.addWarnsumData(name, code, type, actionCode, issueTime, expireTime, updateTime);
            }
        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    public static void getWlJson(){
        try {
            if (jsonObject.has("WL")) {
                String expireTime = null;
                String type = null;
                JSONObject wl = jsonObject.getJSONObject("WL");

                String name = wl.getString("name");
                String code = wl.getString("code");
                if (wl.has("type")) {
                    type = wl.getString("type");
                }
                String actionCode = wl.getString("actionCode");
                String issueTime = wl.getString("issueTime");
                if (wl.has("expireTime")){
                    expireTime = wl.getString("expireTime");
                }
                String updateTime = wl.getString("updateTime");

                Warnsum.addWarnsumData(name, code, type, actionCode, issueTime, expireTime, updateTime);
            }
        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    public static void getWtcsgnlJson(){
        try {
            if (jsonObject.has("WTCSGNL")) {
                String expireTime = null;
                String type = null;
                JSONObject wtcsgnl = jsonObject.getJSONObject("WTCSGNL");

                String name = wtcsgnl.getString("name");
                String code = wtcsgnl.getString("code");
                if (wtcsgnl.has("type")) {
                    type = wtcsgnl.getString("type");
                }
                String actionCode = wtcsgnl.getString("actionCode");
                String issueTime = wtcsgnl.getString("issueTime");
                if (wtcsgnl.has("expireTime")){
                    expireTime = wtcsgnl.getString("expireTime");
                }
                String updateTime = wtcsgnl.getString("updateTime");

                Warnsum.addWarnsumData(name, code, type, actionCode, issueTime, expireTime, updateTime);
            }
        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    public static void getWtmwJson(){
        try {
            if (jsonObject.has("WTMW")) {
                String expireTime = null;
                String type = null;
                JSONObject wtmw = jsonObject.getJSONObject("WTMW");

                String name = wtmw.getString("name");
                String code = wtmw.getString("code");
                if (wtmw.has("type")) {
                    type = wtmw.getString("type");
                }
                String actionCode = wtmw.getString("actionCode");
                String issueTime = wtmw.getString("issueTime");
                if (wtmw.has("expireTime")){
                    expireTime = wtmw.getString("expireTime");
                }
                String updateTime = wtmw.getString("updateTime");

                Warnsum.addWarnsumData(name, code, type, actionCode, issueTime, expireTime, updateTime);
            }
        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    public static void getWtsJson(){
        try {
            if (jsonObject.has("WTS")) {
                String expireTime = null;
                String type = null;
                JSONObject wts = jsonObject.getJSONObject("WTS");

                String name = wts.getString("name");
                String code = wts.getString("code");
                if (wts.has("type")) {
                    type = wts.getString("type");
                }
                String actionCode = wts.getString("actionCode");
                String issueTime = wts.getString("issueTime");
                if (wts.has("expireTime")){
                    expireTime = wts.getString("expireTime");
                }
                String updateTime = wts.getString("updateTime");

                Warnsum.addWarnsumData(name, code, type, actionCode, issueTime, expireTime, updateTime);
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
