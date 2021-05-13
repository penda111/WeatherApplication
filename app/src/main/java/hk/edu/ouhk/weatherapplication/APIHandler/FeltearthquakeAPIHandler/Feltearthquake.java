package hk.edu.ouhk.weatherapplication.APIHandler.FeltearthquakeAPIHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class Feltearthquake {
    private static final String LAT = "lat";
    private static final String LON = "lon";
    private static final String MAG = "mag";
    private static final String REGION = "region";
    private static final String INTENSITY = "intensity";
    private static final String DETAILS = "details";
    private static final String PTIME = "ptime";
    private static final String UPDATETIME = "updateTime";



    public static ArrayList<HashMap<String, String>> feltearthquakeList = new ArrayList<>();

    // Creates and add contact to contact list
    public static void addFeltearthquakeData(String lat, String lon, String mag, String region, String intensity, String details, String ptime, String updateTime) {
        // Create contact
        HashMap<String, String> feltearthquakeData = new HashMap<>();

        feltearthquakeData.put(LAT, lat);
        feltearthquakeData.put(LON, lon);
        feltearthquakeData.put(MAG, mag);
        feltearthquakeData.put(REGION, region);
        feltearthquakeData.put(INTENSITY, intensity);
        feltearthquakeData.put(DETAILS, details);
        feltearthquakeData.put(PTIME, ptime);
        feltearthquakeData.put(UPDATETIME, updateTime);

        // Add contact to contact list
        feltearthquakeList.add(feltearthquakeData);
    }
}
