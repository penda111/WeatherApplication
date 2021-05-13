package hk.edu.ouhk.weatherapplication.APIHandler.RhrreadAPIHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class Rainfall {
    private static final String PLACE = "place";
    private static final String RAINFALLMAXVALUE = "rainfallMaxValue";
    private static final String RAINFALLMINVALUE = "rainfallMinValue";
    private static final String RAINFALLUNIT = "rainfallUnit";
    private static final String RAINFALLMAINTAIN = "rainfallMaintain";
    private static final String RAINFALLSTARTTIME = "rainfallStartTime";
    private static final String RAINFALLENDTIME = "rainfallEndTime";

    public static ArrayList<HashMap<String, String>> rainfallList = new ArrayList<>();

    // Creates and add contact to contact list
    public static void addRainfallData(String place, String rainfallMaxValue, String rainfallMinValue, String rainfallUnit, String rainfallMaintain, String rainfallStartTime, String rainfallEndTime) {
        // Create contact
        HashMap<String, String> rainfallData = new HashMap<>();
        rainfallData.put(PLACE, place);
        rainfallData.put(RAINFALLMAXVALUE, rainfallMaxValue);
        rainfallData.put(RAINFALLMINVALUE, rainfallMinValue);
        rainfallData.put(RAINFALLUNIT, rainfallUnit);
        rainfallData.put(RAINFALLMAINTAIN, rainfallMaintain);
        rainfallData.put(RAINFALLSTARTTIME, rainfallStartTime);
        rainfallData.put(RAINFALLENDTIME, rainfallEndTime);

        // Add contact to contact list
        rainfallList.add(rainfallData);
    }
}
