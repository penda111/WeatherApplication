package hk.edu.ouhk.weatherapplication.APIHandler.WarnsumAPIHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class Warnsum {
    private static final String NAME = "name";
    private static final String CODE = "code";
    private static final String TYPE = "type";
    private static final String ACTIONCODE = "actionCode";
    private static final String ISSUETIME = "issueTime";
    private static final String EXPIRETIME = "expireTime";
    private static final String UPDATETIME = "updateTime";

    public static ArrayList<HashMap<String, String>> warnsumList = new ArrayList<>();

    // Creates and add contact to contact list
    public static void addWarnsumData(String name, String code,String type, String actionCode, String issueTime, String expireTime, String updateTime) {
        // Create contact
        HashMap<String, String> warnsumData = new HashMap<>();

        warnsumData.put(NAME, name);
        warnsumData.put(CODE, code);
        warnsumData.put(TYPE, type);
        warnsumData.put(ACTIONCODE, actionCode);
        warnsumData.put(ISSUETIME, issueTime);
        warnsumData.put(EXPIRETIME, expireTime);
        warnsumData.put(UPDATETIME, updateTime);

        // Add contact to contact list
        warnsumList.add(warnsumData);
    }
}
