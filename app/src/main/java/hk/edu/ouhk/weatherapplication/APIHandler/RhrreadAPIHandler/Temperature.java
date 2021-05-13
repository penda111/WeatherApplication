package hk.edu.ouhk.weatherapplication.APIHandler.RhrreadAPIHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class Temperature {
    private static final String PLACE = "place";
    private static final String TEMPVALUE = "tempValue";
    private static final String TEMPUNIT = "tempUnit";
    private static final String RECORDTIME = "recordTime";

    public static ArrayList<HashMap<String, String>> tempList = new ArrayList<>();

    // Creates and add contact to contact list
    public static void addTempData(String place, String tempValue, String tempUnit, String recordTime) {
        // Create contact
        HashMap<String, String> tempData = new HashMap<>();
        tempData.put(PLACE, place);
        tempData.put(TEMPVALUE, tempValue);
        tempData.put(TEMPUNIT, tempUnit);
        tempData.put(RECORDTIME, recordTime);

        // Add contact to contact list
        tempList.add(tempData);
    }
}
