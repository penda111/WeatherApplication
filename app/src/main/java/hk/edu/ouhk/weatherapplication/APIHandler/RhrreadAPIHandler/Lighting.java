package hk.edu.ouhk.weatherapplication.APIHandler.RhrreadAPIHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class Lighting {
    private static final String PLACE = "place";
    private static final String LIGHTNINGOCCUR = "lightningOccur";
    private static final String LIGHTNINGSTARTTIME = "lightningStartTime";
    private static final String LIGHTNINGENDTIME = "lightningEndTime";


    public static ArrayList<HashMap<String, String>> lightingList = new ArrayList<>();

    // Creates and add contact to contact list
    public static void addLightingData(String place, String lightningOccur, String lightningStartTime, String lightningEndTime) {
        // Create contact
        HashMap<String, String> lightingData = new HashMap<>();
        lightingData.put(PLACE, place);
        lightingData.put(LIGHTNINGOCCUR, lightningOccur);
        lightingData.put(LIGHTNINGSTARTTIME, lightningStartTime);
        lightingData.put(LIGHTNINGENDTIME, lightningEndTime);

        // Add contact to contact list
        lightingList.add(lightingData);
    }
}
