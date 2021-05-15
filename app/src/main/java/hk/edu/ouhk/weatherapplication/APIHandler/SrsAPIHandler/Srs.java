package hk.edu.ouhk.weatherapplication.APIHandler.SrsAPIHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class Srs {
    public static final String DATE = "date";
    public static final String SUNRISE = "sunRise";
    public static final String SUNSET = "sunSet";

    public static ArrayList<HashMap<String, String>> srsList = new ArrayList<>();

    // Creates and add contact to contact list
    public static void addSrsData(String date, String sunRise, String sunSet) {
        // Create contact
        HashMap<String, String> srsData = new HashMap<>();

        srsData.put(DATE, date);
        srsData.put(SUNRISE, sunRise);
        srsData.put(SUNSET, sunSet);

        // Add contact to contact list
        srsList.add(srsData);
    }
}
