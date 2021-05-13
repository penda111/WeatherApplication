package hk.edu.ouhk.weatherapplication.APIHandler.HltAPIHandler;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import hk.edu.ouhk.weatherapplication.APIHandler.JsonHandlerThread;

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

}
