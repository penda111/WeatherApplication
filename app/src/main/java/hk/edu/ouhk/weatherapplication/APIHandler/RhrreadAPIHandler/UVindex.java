package hk.edu.ouhk.weatherapplication.APIHandler.RhrreadAPIHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class UVindex {
    private static final String PLACE = "place";
    private static final String UVVALUE = "uvValue";
    private static final String UVDESC = "uvDesc";
    private static final String UVMESAAGE = "uvMesaage";



    public static ArrayList<HashMap<String, String>> uvList = new ArrayList<>();

    // Creates and add contact to contact list
    public static void addUV(String place, String uvValue, String uvDesc, String uvMesaage) {
        // Create contact
        HashMap<String, String> uvData = new HashMap<>();
        uvData.put(PLACE, place);
        uvData.put(UVVALUE, uvValue);
        uvData.put(UVDESC, uvDesc);
        uvData.put(UVMESAAGE, uvMesaage);

        // Add contact to contact list
        uvList.add(uvData);
    }
}
