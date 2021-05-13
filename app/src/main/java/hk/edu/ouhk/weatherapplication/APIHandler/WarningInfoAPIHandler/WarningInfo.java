package hk.edu.ouhk.weatherapplication.APIHandler.WarningInfoAPIHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class WarningInfo {
    private static final String CONTENTS = "contents";
    private static final String WARNINGSTATEMENTCODE = "warningStatementCode";
    private static final String SUBTYPE = "subtype";
    private static final String UPDATETIME = "updateTime";

    public static ArrayList<HashMap<String, Object>> warningInfoList = new ArrayList<>();

    // Creates and add contact to contact list
    public static void addWarningInfoData(ArrayList<String> contents, String warningStatementCode, String subtype, String updateTime) {
        // Create contact
        HashMap<String, Object> warningInfoData = new HashMap<>();

        warningInfoData.put(CONTENTS, contents);
        warningInfoData.put(WARNINGSTATEMENTCODE, warningStatementCode);
        warningInfoData.put(SUBTYPE, subtype);
        warningInfoData.put(UPDATETIME, updateTime);

        // Add contact to contact list
        warningInfoList.add(warningInfoData);
    }
}
