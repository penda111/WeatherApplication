package hk.edu.ouhk.weatherapplication.APIHandler.QemAPIHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class Qem {
    private static final String LAT = "lat";
    private static final String LON = "lon";
    private static final String MAG = "mag";
    private static final String REGION = "region";
    private static final String PTIME = "ptime";
    private static final String UPDATETIME = "updateTime";

    public static ArrayList<HashMap<String, String>> qemList = new ArrayList<>();

    // Creates and add contact to contact list
    public static void addQemData(String lat, String lon, String mag, String region, String ptime, String updateTime) {
        // Create contact
        HashMap<String, String> qemData = new HashMap<>();

        qemData.put(LAT, lat);
        qemData.put(LON, lon);
        qemData.put(MAG, mag);
        qemData.put(REGION, region);
        qemData.put(PTIME, ptime);
        qemData.put(UPDATETIME, updateTime);

        // Add contact to contact list
        qemList.add(qemData);
    }
}
