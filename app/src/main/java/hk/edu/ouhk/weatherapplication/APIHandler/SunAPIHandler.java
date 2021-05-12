package hk.edu.ouhk.weatherapplication.APIHandler;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SunAPIHandler {

    private static final String TAG = "SunAPIHandler";
    private static final String DATATYPE = "SRS";
    private static final String FORMAT = "json";
    private static String lang = "tc";

    private String url;
    public static JSONObject jsonObject;

    public SunAPIHandler(int year,int month,int day){
        url = "https://data.weather.gov.hk/weatherAPI/opendata/opendata.php?dataType="+DATATYPE+"&lang="+lang+"&rformat="+FORMAT+"&year="+year+"&month="+month+"&day="+day;
        JsonHandlerThread jsonHandlerThread = new JsonHandlerThread(url,"Sun");
        jsonHandlerThread.start();
        try {
            jsonHandlerThread.join();
        }catch (InterruptedException e){
        }
    }

    public SunAPIHandler(){
        List<String> todayDate = getTodayDate();
        String day = todayDate.get(0);
        String month = todayDate.get(1);
        String year = todayDate.get(2);

        url = "https://data.weather.gov.hk/weatherAPI/opendata/opendata.php?dataType="+DATATYPE+"&lang="+lang+"&rformat="+FORMAT+"&year="+year+"&month="+month+"&day="+day;
        JsonHandlerThread jsonHandlerThread = new JsonHandlerThread(url,"Sun");
        jsonHandlerThread.start();
        try {
            jsonHandlerThread.join();
        }catch (InterruptedException e){
        }
    }

    public List<String> getTodayDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        Log.d(TAG, "Check Date: " + formatter.format(date));
        return Arrays.asList(formatter.format(date).split("/"));
    }

    public float calSunTimePass(){
        float f = 0;

        try {
            //Log.d(TAG, "Check jsonObject: " + jsonObject);
            //Log.e(TAG, "Test1: " +jsonObject);
            JSONArray data = jsonObject.getJSONArray("data");

            JSONArray c = data.getJSONArray(0);

            String date = c.getString(0);
            String rise = c.getString(1);
            String tran = c.getString(2);
            String set = c.getString(3);
            Log.d(TAG, "rise: " +date+ rise + tran + set);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime riseTime = LocalDateTime.parse(date +" "+ rise, formatter);
            LocalDateTime setTime = LocalDateTime.parse(date + " "+ set, formatter);

            LocalDateTime nowTime = LocalDateTime.now();
            Log.d(TAG, "Check riseTime: " +riseTime+","+setTime);

            if(nowTime.compareTo(riseTime) >= 0 &&  nowTime.compareTo(setTime) <= 0){
                long diffInMinutes = java.time.Duration.between(riseTime, setTime).toMinutes();
                long timePass = java.time.Duration.between(riseTime, nowTime).toMinutes();

                f = ((float)timePass / (float)diffInMinutes) * 100;
                Log.d(TAG, "Check difference: " + timePass +","+ diffInMinutes +","+riseTime+","+setTime);
            }else{
                f = (float) -1;
            }
            Log.d(TAG, "Check difference (f): " + f +","+nowTime);

        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }

        return f;
    }
}
