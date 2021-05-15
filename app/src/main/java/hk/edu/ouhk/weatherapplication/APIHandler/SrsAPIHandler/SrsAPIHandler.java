package hk.edu.ouhk.weatherapplication.APIHandler.SrsAPIHandler;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import hk.edu.ouhk.weatherapplication.APIHandler.Database.DatabaseHandlerThread;
import hk.edu.ouhk.weatherapplication.APIHandler.JsonHandlerThread;
import hk.edu.ouhk.weatherapplication.MainActivity;

public class SrsAPIHandler {

    private static final String TAG = "SunAPIHandler";
    private static final String DATATYPE = "SRS";
    private static final String FORMAT = "json";
    private static String lang = "tc";
    private String url;
    public static JSONObject jsonObject;

    public static String todayDate;
    public static String todaySunRise;
    public static String todaySunSet;

    private static final Object lock = new Object();

    public SrsAPIHandler(){
        List<String> todayDate = getTodayDate();
        String year = todayDate.get(2);

        url = "https://data.weather.gov.hk/weatherAPI/opendata/opendata.php?dataType="+DATATYPE+"&lang="+lang+"&rformat="+FORMAT+"&year="+year;

        JsonHandlerThread jsonHandlerThread = new JsonHandlerThread(url,"Srs");
        jsonHandlerThread.start();
        try {
            jsonHandlerThread.join();
            if(MainActivity.isConnected) {
                DatabaseHandlerThread databaseHandlerThread = new DatabaseHandlerThread("Srs");
                databaseHandlerThread.start();
            }
        }catch (InterruptedException e){
        }
    }

    /*public SrsAPIHandler(){
        List<String> todayDate = getTodayDate();
        String day = todayDate.get(0);
        String month = todayDate.get(1);
        String year = todayDate.get(2);

        url = "https://data.weather.gov.hk/weatherAPI/opendata/opendata.php?dataType="+DATATYPE+"&lang="+lang+"&rformat="+FORMAT+"&year="+year+"&month="+month+"&day="+day;
        JsonHandlerThread jsonHandlerThread = new JsonHandlerThread(url,"Srs");
        jsonHandlerThread.start();
        try {
            jsonHandlerThread.join();
        }catch (InterruptedException e){
        }
    }*/

    public List<String> getTodayDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        Log.d(TAG, "Check Date: " + formatter.format(date));
        return Arrays.asList(formatter.format(date).split("/"));
    }

    public  float calSunTimePass(){
        synchronized(lock) {
        float f = -1;

            //Log.d(TAG, "Check jsonObject: " + jsonObject);
            //Log.e(TAG, "Test1: " +jsonObject);
            Log.d(TAG, "(calSunTimePass)Thread name=: " + Thread.currentThread().getName());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            LocalDateTime riseTime = LocalDateTime.parse(todayDate + " " + todaySunRise,
                    formatter);
            LocalDateTime setTime = LocalDateTime.parse(todayDate + " " + todaySunSet,
                    formatter);

            LocalDateTime nowTime = LocalDateTime.now();
            Log.d(TAG, "Check riseTime: " + riseTime + "," + setTime);

            if (nowTime.compareTo(riseTime) >= 0 && nowTime.compareTo(setTime) <= 0) {
                long diffInMinutes = java.time.Duration.between(riseTime, setTime).toMinutes();
                long timePass = java.time.Duration.between(riseTime, nowTime).toMinutes();

                f = ((float) timePass / (float) diffInMinutes) * 100;
                Log.d(TAG, "Check difference: " + timePass + "," + diffInMinutes + "," + riseTime + "," + setTime);
            } else {
                f = (float) -1;
            }
            Log.d(TAG, "Check difference (f): " + f + "," + nowTime);

        return f;
        }
    }

    public static void getSrsJson(){
        synchronized(lock) {
            try {

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date today = new Date();
                //Log.d(TAG, "getSrsJson Check Date: " + formatter.format(today));

                JSONArray srs = jsonObject.getJSONArray("data");

                for (int i = 0; i < srs.length(); i++) {
                    JSONArray c = srs.getJSONArray(i);

                    String date = c.getString(0);
                    String rise = c.getString(1);
                    //String tran = c.getString(2);
                    String set = c.getString(3);

                    if (formatter.format(today).equals(date)) {
                        todayDate = date;
                        todaySunRise = rise;
                        todaySunSet = set;
                    }

                    Srs.addSrsData(date, rise, set);
                }

                Log.d(TAG, "getSrsJson: "+ todayDate+", "+ todaySunRise+", "+ todaySunSet);
                Log.d(TAG, "getSrsJson: "+ Srs.srsList);

            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }
    }

    public static void storeDB(){
        //MainActivity.db.rebuildTable_Hlt();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Log.d("Check", formatter.format(date));
        for(HashMap<String, String> srs : Srs.srsList){
            try {
                long days = TimeUnit.MILLISECONDS.toDays(formatter.parse(srs.get("date")).getTime() - date.getTime());
                if(days < 9 & days >=0 ) {
                    MainActivity.db.createSrs(srs);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }
}
