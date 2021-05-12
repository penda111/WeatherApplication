package hk.edu.ouhk.weatherapplication.APIHandler.RhrreadAPIHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class Humidity {
    private static final String PLACE = "place";
    private static final String HUMIDITYUNIT = "humidityUnit";
    private static final String HUMIDITYVALUE = "humidityValue";
    private static final String HUMIDITYRECORDTIME = "humidityRecordTime";

    public static ArrayList<HashMap<String, String>> humidityList = new ArrayList<>();

    // Creates and add contact to contact list
    public static void addHumidity(String place, String humidityValue, String humidityUnit, String humidityRecordTime) {
        // Create contact
        HashMap<String, String> humidityData = new HashMap<>();
        humidityData.put(PLACE, place);
        humidityData.put(HUMIDITYUNIT, humidityUnit);
        humidityData.put(HUMIDITYVALUE, humidityValue);
        humidityData.put(HUMIDITYRECORDTIME, humidityRecordTime);

        // Add contact to contact list
        humidityList.add(humidityData);
    }
}
