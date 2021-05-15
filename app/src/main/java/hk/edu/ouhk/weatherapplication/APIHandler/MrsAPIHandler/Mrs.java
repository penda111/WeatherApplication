package hk.edu.ouhk.weatherapplication.APIHandler.MrsAPIHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class Mrs {
    public static final String DATE = "date";
    public static final String MOONRISE = "moonRise";
    public static final String MOONSET = "moonSet";

    public static ArrayList<HashMap<String, String>> mrsList = new ArrayList<>();

    // Creates and add contact to contact list
    public static void addMrsData(String date, String moonRise, String moonSet) {
        // Create contact
        HashMap<String, String> mrsData = new HashMap<>();

        mrsData.put(DATE, date);
        mrsData.put(MOONRISE, moonRise);
        mrsData.put(MOONSET, moonSet);

        // Add contact to contact list
        mrsList.add(mrsData);
    }
}
