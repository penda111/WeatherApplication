package hk.edu.ouhk.weatherapplication.APIHandler.HltAPIHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class Hlt {
    private static final String DATE = "date";
    private static final String STATION = "station";

    public static ArrayList<HashMap<String, String>> hltList = new ArrayList<>();

    // Creates and add contact to contact list
    public static void addHltData(String date, String station, ArrayList<String> hltDataList) {
        // Create contact
        HashMap<String, String> hltData = new HashMap<>();
        hltData.put(DATE, date);
        hltData.put(STATION, station);
        for (int i = 0; i < hltDataList.size(); i++) {
            hltData.put(Integer.toString(i), hltDataList.get(i));
        }

        // Add contact to contact list
        hltList.add(hltData);
    }

}
