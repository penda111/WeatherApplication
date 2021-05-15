package hk.edu.ouhk.weatherapplication.APIHandler.ffcWeatherAPIHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class ffcWeather {
    private static final String TEMP = "temp";
    private static final String TEMPMIN = "temp_min";
    private static final String TEMPMAX = "temp_max";
    private static final String WINDSPEED = "speed";

    public static ArrayList<HashMap<String, String>> ffcList = new ArrayList<>();

    public static void addffcData(String temp, String temp_min, String temp_max, String windspeed){
        HashMap<String, String> ffcData = new HashMap<>();
        ffcData.put(TEMP, temp);
        ffcData.put(TEMPMIN, temp_min);
        ffcData.put(TEMPMAX, temp_max);
        ffcData.put(WINDSPEED, windspeed);

        ffcList.add(ffcData);
    }
}
