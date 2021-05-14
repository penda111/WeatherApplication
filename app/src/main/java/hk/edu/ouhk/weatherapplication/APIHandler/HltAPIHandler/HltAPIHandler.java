package hk.edu.ouhk.weatherapplication.APIHandler.HltAPIHandler;

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
import hk.edu.ouhk.weatherapplication.APIHandler.FndAPIHandler.WeatherForecast_9Days;
import hk.edu.ouhk.weatherapplication.APIHandler.JsonHandlerThread;
import hk.edu.ouhk.weatherapplication.MainActivity;

public class HltAPIHandler {

    private static final String TAG = "HltAPIHandler";
    private static final String DATATYPE = "HLT";
    private static final String FORMAT = "json";
    private static String lang = "tc";

    public static String year;
    public static String month;
    public static String day;
    public static String station;

    private String url;
    public static JSONObject jsonObject;

    public HltAPIHandler(int year, int month, int day, String station){
        this.year = Integer.toString(year);
        this.month = Integer.toString(month);
        this.day = Integer.toString(day);
        this.station = station;

        url = "https://data.weather.gov.hk/weatherAPI/opendata/opendata.php?dataType="+DATATYPE+"&lang="+lang+"&rformat="+FORMAT+"&station="+station+"&year="+year+"&month="+month+"&day="+day;
        JsonHandlerThread jsonHandlerThread = new JsonHandlerThread(url,"Hlt");
        jsonHandlerThread.start();
        try {
            jsonHandlerThread.join();
        }catch (InterruptedException e){
        }
    }

    public HltAPIHandler(String station){
        List<String> todayDate = getTodayDate();
        String day = todayDate.get(0);
        String month = todayDate.get(1);
        String year = todayDate.get(2);

        this.day = day;
        this.month = month;
        this.year = year;
        this.station = station;

        url = "https://data.weather.gov.hk/weatherAPI/opendata/opendata.php?dataType="+DATATYPE+"&lang="+lang+"&rformat="+FORMAT+"&station="+station+"&year="+year+"&month="+month+"&day="+day;
        JsonHandlerThread jsonHandlerThread = new JsonHandlerThread(url,"Hlt");
        jsonHandlerThread.start();
        try {
            jsonHandlerThread.join();
            DatabaseHandlerThread databaseHandlerThread = new DatabaseHandlerThread("Hlt");
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
        getHltJson();
        Log.d(TAG, "getJsonData(): "+ Hlt.hltList);

    }

    public static void getHltJson(){
        try {
            ArrayList<String> hltDataList = new ArrayList<>();
            String date;

            JSONArray hlt = jsonObject.getJSONArray("data");

            for (int i = 0; i < hlt.length(); i++) {
                hltDataList.clear();
                date = year;

                JSONArray hltData = hlt.getJSONArray(i);

                for (int j = 0; j < hltData.length(); j++) {
                    if(j<2){
                        date = hltData.getString(j) +"/"+ date;
                    }else{
                        hltDataList.add(hltData.getString(j));
                    }

                }
                Hlt.addHltData(date, station, hltDataList);
            }

        }catch (final JSONException e ) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    public static void storeDB(){
        MainActivity.db.rebuildTable_Hlt();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        for(HashMap<String, String> hlt : Hlt.hltList){
            try {
                long days = TimeUnit.MILLISECONDS.toDays(formatter.parse(hlt.get("date")).getTime() - date.getTime());
                if(days < 9 & days >=0 ) {
                    MainActivity.db.createHlt(hlt);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

}
