package hk.edu.ouhk.weatherapplication.APIHandler.FndAPIHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class SoilTemp {
    public static String PLACE = "place";
    public static String SOILTEMPVALUE = "soilTempValue";
    public static String UNIT = "unit";
    public static String RECORDTIME = "recordTime";
    public static String SOILDEPTHUNIT = "soilDepthUnit";
    public static String SOILDEPTHVALUE = "soilDepthValue";


    public static ArrayList<HashMap<String, String>> soilTemp = new ArrayList<>();

    // Creates and add contact to contact list
    public static void addSoilTemp(String place, String soilTempValue, String unit, String recordTime, String soilDepthUnit, String soilDepthValue) {
        // Create contact
        HashMap<String, String> soilTempData = new HashMap<>();
        soilTempData.put(PLACE, place);
        soilTempData.put(SOILTEMPVALUE, soilTempValue);
        soilTempData.put(UNIT, unit);
        soilTempData.put(RECORDTIME, recordTime);
        soilTempData.put(SOILDEPTHUNIT, soilDepthUnit);
        soilTempData.put(SOILDEPTHVALUE, soilDepthValue);

        // Add contact to contact list
        soilTemp.add(soilTempData);
    }
}
