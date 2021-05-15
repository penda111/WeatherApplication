package hk.edu.ouhk.weatherapplication.APIHandler.MrsAPIHandler;

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

public class MrsAPIHandler {
    private static final String TAG = "MoonAPIHandler";
    private static final String DATATYPE = "MRS";
    private static final String FORMAT = "json";
    private static final String lang = "tc";
    private String url;
    public static JSONObject jsonObject;

    public static String todayDate;
    public static String todayMoonRise;
    public static String todayMoonSet;

    private static final Object lock = new Object();

    public MrsAPIHandler(){
        List<String> todayDate = getTodayDate();
        String year = todayDate.get(2);
        url = "https://data.weather.gov.hk/weatherAPI/opendata/opendata.php?dataType="+DATATYPE+"&lang="+lang+"&rformat="+FORMAT+"&year="+year;
        JsonHandlerThread jsonHandlerThread = new JsonHandlerThread(url,"Mrs");
        jsonHandlerThread.start();
        try {
            jsonHandlerThread.join();
            if(MainActivity.isConnected) {
                DatabaseHandlerThread databaseHandlerThread = new DatabaseHandlerThread("Mrs");
                databaseHandlerThread.start();
            }
        }catch (InterruptedException e){
        }
    }

    /*public MrsAPIHandler(){
        List<String> todayDate = getTodayDate();
        String day = todayDate.get(0);
        String month = todayDate.get(1);
        String year = todayDate.get(2);

        url = "https://data.weather.gov.hk/weatherAPI/opendata/opendata.php?dataType="+DATATYPE+"&lang="+lang+"&rformat="+FORMAT+"&year="+year+"&month="+month+"&day="+day;
        JsonHandlerThread jsonHandlerThread = new JsonHandlerThread(url,"Mrs");
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

    public float calMoonTimePass(){
        synchronized(lock) {
            float f = -1;

            Log.d(TAG, "(calMoonTimePass)Thread name=: " + Thread.currentThread().getName());
            //Log.d(TAG, "Check jsonObject: " + jsonObject);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime riseTime = LocalDateTime.parse(todayDate + " " + todayMoonRise, formatter);
            LocalDateTime setTime = LocalDateTime.parse(todayDate + " " + todayMoonSet, formatter);

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

    public static void getMrsJson(){
        synchronized(lock) {
            try {

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date today = new Date();
                //Log.d(TAG, "getMrsJson Check Date: " + formatter.format(today));

                JSONArray srs = jsonObject.getJSONArray("data");

                for (int i = 0; i < srs.length(); i++) {
                    JSONArray c = srs.getJSONArray(i);

                    String date = c.getString(0);
                    String rise = c.getString(1);
                    //String tran = c.getString(2);
                    String set = c.getString(3);

                    if (formatter.format(today).equals(date)) {
                        todayDate = date;
                        todayMoonRise = rise;
                        todayMoonSet = set;
                    }

                    Mrs.addMrsData(date, rise, set);
                }
                Log.d(TAG, "getMrsJson: "+ todayDate+", "+ todayMoonRise+", "+ todayMoonSet);
                Log.d(TAG, "getMrsJson: "+ Mrs.mrsList);

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
        for(HashMap<String, String> mrs : Mrs.mrsList){
            try {
                long days = TimeUnit.MILLISECONDS.toDays(formatter.parse(mrs.get("date")).getTime() - date.getTime());
                if(days < 9 & days >=0 ) {
                    Log.d(TAG, "storeDB: "+mrs);
                    MainActivity.db.createMrs(mrs);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

}
