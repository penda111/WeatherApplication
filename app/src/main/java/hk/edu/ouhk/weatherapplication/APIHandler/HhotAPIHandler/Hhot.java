package hk.edu.ouhk.weatherapplication.APIHandler.HhotAPIHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class Hhot {
    public static final String DATE = "date";
    public static final String STATION = "station";

    public static ArrayList<HashMap<String, String>> hhotList = new ArrayList<>();

    // Creates and add contact to contact list
    public static void addHhotData(String date, String station, ArrayList<String> hhotDataList) {
        // Create contact
        HashMap<String, String> hhotData = new HashMap<>();
        hhotData.put(DATE, date);
        hhotData.put(STATION, station);
        for (int i = 0; i < hhotDataList.size(); i++) {
            hhotData.put(Integer.toString(i), hhotDataList.get(i));
        }

        // Add contact to contact list
        hhotList.add(hhotData);
    }

}
