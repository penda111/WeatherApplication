package hk.edu.ouhk.weatherapplication.APIHandler.SwtAPIHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class Swt {
    private static final String DESC = "desc";
    private static final String UPDATETIME = "updateTime";

    public static ArrayList<HashMap<String, String>> swtList = new ArrayList<>();

    // Creates and add contact to contact list
    public static void addSwtData(String desc, String updateTime) {
        // Create contact
        HashMap<String, String> swtData = new HashMap<>();

        swtData.put(DESC, desc);
        swtData.put(UPDATETIME, updateTime);

        // Add contact to contact list
        swtList.add(swtData);
    }
}
