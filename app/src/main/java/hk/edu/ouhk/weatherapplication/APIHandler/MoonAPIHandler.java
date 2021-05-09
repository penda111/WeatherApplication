package hk.edu.ouhk.weatherapplication.APIHandler;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MoonAPIHandler {
    private static final String TAG = "MoonAPIHandler";
    private String url;
    public static JSONObject jsonObject;

    public MoonAPIHandler(int year,int month,int day){
        url = "https://data.weather.gov.hk/weatherAPI/opendata/opendata.php?dataType=MRS&lang=tc&rformat=json&year="+year+"&month="+month+"&day="+day;
        JsonHandlerThread jsonHandlerThread = new JsonHandlerThread(this,url,"Moon");
        jsonHandlerThread.start();
        try {
            jsonHandlerThread.join();
        }catch (InterruptedException e){

        }
    }

    public static void replaceJsonObj(JSONObject jsonObj){
        jsonObject = jsonObj;
    }

    public float calMoonTimePass(){
        float f = 0;

            try {

                //Log.d(TAG, "Check jsonObject: " + jsonObject);
                Log.e(TAG, "Test1: " +jsonObject);
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
