package hk.edu.ouhk.weatherapplication.APIHandler.WarnsumAPIHandler;

import java.util.ArrayList;
import java.util.HashMap;

import hk.edu.ouhk.weatherapplication.R;

public class Warnsum {
    private static final String NAME = "name";
    private static final String CODE = "code";
    private static final String TYPE = "type";
    private static final String ACTIONCODE = "actionCode";
    private static final String ISSUETIME = "issueTime";
    private static final String EXPIRETIME = "expireTime";
    private static final String UPDATETIME = "updateTime";
    public static String ICONID = "iconid";

    public static ArrayList<HashMap<String, String>> warnsumList = new ArrayList<>();
    public static HashMap<String, String> iconMap = new HashMap<>();

    // Creates and add contact to contact list
    public static void addWarnsumData(String name, String code,String type, String actionCode, String issueTime, String expireTime, String updateTime) {
        checkIconMap();
        // Create contact
        HashMap<String, String> warnsumData = new HashMap<>();

        warnsumData.put(NAME, name);
        warnsumData.put(CODE, code);
        warnsumData.put(TYPE, type);
        warnsumData.put(ACTIONCODE, actionCode);
        warnsumData.put(ISSUETIME, issueTime);
        warnsumData.put(EXPIRETIME, expireTime);
        warnsumData.put(UPDATETIME, updateTime);

        String iconid = iconMap.get(String.valueOf(code));
        warnsumData.put(ICONID, iconid);
        // Add contact to contact list
        warnsumList.add(warnsumData);
    }
    public static void clearList(){
        warnsumList.clear();
    }
    public static void checkIconMap(){
        if(iconMap.isEmpty()) {
            iconMap.put("WFIREY", Integer.toString(R.drawable.firey));
            iconMap.put("WFIRER", Integer.toString(R.drawable.firer));
            iconMap.put("WHOT", Integer.toString(R.drawable.vhot));
            iconMap.put("WCOLD", Integer.toString(R.drawable.cold));
            iconMap.put("WMSGNL", Integer.toString(R.drawable.sms));
            iconMap.put("WRAINA", Integer.toString(R.drawable.raina));
            iconMap.put("WRAINR", Integer.toString(R.drawable.rainr));
            iconMap.put("WRAINB", Integer.toString(R.drawable.rainb));
            iconMap.put("WFNTSA", Integer.toString(R.drawable.ntfl));
            iconMap.put("WL", Integer.toString(R.drawable.landslip));
            iconMap.put("WTMW", Integer.toString(R.drawable.tsunamiwarn));
            iconMap.put("WTS", Integer.toString(R.drawable.ts));
            iconMap.put("WFROST", Integer.toString(R.drawable.frost));

            iconMap.put("TC1", Integer.toString(R.drawable.tc1));
            iconMap.put("TC3", Integer.toString(R.drawable.tc3));
            iconMap.put("TC8NE", Integer.toString(R.drawable.tc8ne));
            iconMap.put("TC8SE", Integer.toString(R.drawable.tc8b));
            iconMap.put("TC8NW", Integer.toString(R.drawable.tc8d));
            iconMap.put("TC8SW", Integer.toString(R.drawable.tc8c));
            iconMap.put("TC9", Integer.toString(R.drawable.tc9));
            iconMap.put("TC10", Integer.toString(R.drawable.tc10));

        }
    }
}
