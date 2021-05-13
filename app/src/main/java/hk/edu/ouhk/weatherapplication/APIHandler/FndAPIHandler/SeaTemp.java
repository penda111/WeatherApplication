package hk.edu.ouhk.weatherapplication.APIHandler.FndAPIHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class SeaTemp {
    public static String PLACE = "place";
    public static String SEATEMPVALUE = "seaTempValue";
    public static String UNIT = "unit";
    public static String RECORDTIME = "recordTime";

    public static ArrayList<HashMap<String, String>> seaTemp = new ArrayList<>();

    // Creates and add contact to contact list
    public static void addSeaTemp(String place, String seaTempValue, String unit, String recordTime) {
        // Create contact
        HashMap<String, String> seaTempData = new HashMap<>();
        seaTempData.put(PLACE, place);
        seaTempData.put(SEATEMPVALUE, seaTempValue);
        seaTempData.put(UNIT, unit);
        seaTempData.put(RECORDTIME, recordTime);

        // Add contact to contact list
        seaTemp.add(seaTempData);
    }
}
