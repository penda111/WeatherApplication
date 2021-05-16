package hk.edu.ouhk.weatherapplication.APIHandler.HhotAPIHandler;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import hk.edu.ouhk.weatherapplication.APIHandler.Database.DatabaseHandlerThread;
import hk.edu.ouhk.weatherapplication.APIHandler.JsonHandlerThread;
import hk.edu.ouhk.weatherapplication.MainActivity;

public class HhotAPIHandler {

    private static final String TAG = "HhotAPIHandler";
    private static final String DATATYPE = "HHOT";
    private static final String FORMAT = "json";
    private static String lang = "tc";

    public static String year;
    public static String month;
    public static String day;
    public static String station;

    private String url;
    public static JSONObject jsonObject;

    public HhotAPIHandler(int year, String station){
        this.year = Integer.toString(year);
        //this.month = Integer.toString(month);
        //this.day = Integer.toString(day);
        this.station = station;

        url = "https://data.weather.gov.hk/weatherAPI/opendata/opendata.php?dataType="+DATATYPE+"&lang="+MainActivity.datalang+"&rformat="+FORMAT+"&station="+station+"&year="+year;
        JsonHandlerThread jsonHandlerThread = new JsonHandlerThread(url,"Hhot2");
        jsonHandlerThread.start();
        try {
            jsonHandlerThread.join();
            DatabaseHandlerThread databaseHandlerThread = new DatabaseHandlerThread("Hhot2");
            databaseHandlerThread.start();
        }catch (InterruptedException e){
        }
    }

    public HhotAPIHandler(String station){
        List<String> todayDate = getTodayDate();
        String day = todayDate.get(0);
        String month = todayDate.get(1);
        String year = todayDate.get(2);

        this.day = day;
        this.month = month;
        this.year = year;
        this.station = station;

        url = "https://data.weather.gov.hk/weatherAPI/opendata/opendata.php?dataType="+DATATYPE+"&lang="+lang+"&rformat="+FORMAT+"&station="+station+"&year="+year+"&month="+month+"&day="+day;
        JsonHandlerThread jsonHandlerThread = new JsonHandlerThread(url,"Hhot");
        jsonHandlerThread.start();
        try {
            jsonHandlerThread.join();
            DatabaseHandlerThread databaseHandlerThread = new DatabaseHandlerThread("Hhot");
            databaseHandlerThread.start();
        }catch (InterruptedException e){
        }
    }

    public List<String> getTodayDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        Log.d(TAG, "Check Date: " + formatter.format(date));
        return Arrays.asList(formatter.format(date).split("/"));
    }

    public static void getJsonData(){
        getHhotJson();
        Log.d(TAG, "getJsonData(): "+ Hhot.hhotList);
    }

    public static void getJsonData2(){
        getHhotJson2();
        Log.d(TAG, "getJsonData(): "+ Hhot.hhotList);
    }

    public static void getHhotJson(){
        try {
            ArrayList<String> hhotDataList = new ArrayList<>();

            JSONArray hhot = jsonObject.getJSONArray("data");

            JSONArray hhotData = hhot.getJSONArray(0);

            for (int i = 2; i < hhotData.length(); i++) {
                hhotDataList.add(Double.toString(hhotData.getDouble(i)));
            }

            String date = day+"/"+month+"/"+year;

            Hhot.addHhotData(date, station, hhotDataList);

        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }
    public static void getHhotJson2(){
        try {
            ArrayList<String> hhotDataList = new ArrayList<>();
            String date;

            JSONArray hhot = jsonObject.getJSONArray("data");

            for (int i = 0; i < hhot.length(); i++) {
                hhotDataList.clear();
                date = year;

                JSONArray hhotData = hhot.getJSONArray(i);

                for (int j = 0; j < hhotData.length(); j++) {
                    if(j<2){
                        date = hhotData.getString(j) +"/"+ date;
                    }else{
                        hhotDataList.add(hhotData.getString(j));
                    }

                }
                Hhot.addHhotData(date, station, hhotDataList);
            }

        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    public static void storeDB(){
        //MainActivity.db.rebuildTable_Hhot();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        for(HashMap<String, String> hhot : Hhot.hhotList){
            try {
                long days = TimeUnit.MILLISECONDS.toDays(formatter.parse(hhot.get("date")).getTime() - date.getTime());
                if(days < 9 & days >=0 ) {
                    MainActivity.db.createHhot(hhot);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

}
