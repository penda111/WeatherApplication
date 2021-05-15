package hk.edu.ouhk.weatherapplication.APIHandler.FndAPIHandler;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import hk.edu.ouhk.weatherapplication.MainActivity;
import hk.edu.ouhk.weatherapplication.R;

public class WeatherForecast_9Days {
    public static String FORECASTDATE = "forecastDate";
    public static String WEEK = "week";
    public static String FORECASTWIND = "forecastWind";
    public static String FORECASTWEATHER = "forecastWeather";
    public static String FORECASTMAXTEMPVALUE = "forecastMaxtempValue";
    public static String FORECASTMINTEMPVALUE = "forecastMintempValue";
    public static String FORECASTMAXRHVALUE = "forecastMaxrhValue";
    public static String FORECASTMINRHVALUE = "forecastMinrhValue";
    public static String FORECASTICON = "forecastIcon";
    public static String PSRString = "PSR";
    public static String ICONID = "iconid";
    public static String CORTAG = "cortag";

    public static ArrayList<HashMap<String, String>> weatherForecast_9Days = new ArrayList<>();
    private static HashMap<String, String> iconMap = new HashMap<>();

    // Creates and add contact to contact list
    public static void addWeatherForecast(String forecastDate, String week, String forecastWind, String forecastWeather, String forecastMaxtempValue, String forecastMintempValue, String forecastMaxrhValue, String forecastMinrhValue, String ForecastIcon, String PSR) {
        checkIconMap();
        // Create contact
        HashMap<String, String> weatherForecast = new HashMap<>();
        String formatted = formatDate(forecastDate);
        weatherForecast.put(FORECASTDATE, formatted);
        weatherForecast.put(WEEK, week);
        weatherForecast.put(FORECASTWIND, forecastWind);
        weatherForecast.put(FORECASTWEATHER, forecastWeather);
        weatherForecast.put(FORECASTMAXTEMPVALUE, forecastMaxtempValue);
        weatherForecast.put(FORECASTMINTEMPVALUE, forecastMintempValue);
        weatherForecast.put(FORECASTMAXRHVALUE, forecastMaxrhValue);
        weatherForecast.put(FORECASTMINRHVALUE, forecastMinrhValue);
        weatherForecast.put(FORECASTICON, ForecastIcon);
        weatherForecast.put(PSRString, PSR);
        String iconid = iconMap.get(String.valueOf(ForecastIcon));
        weatherForecast.put(ICONID, iconid);
        String tag = MainActivity.getContext().getString(R.string.chanceofrain);
        //Log.d(CORTAG, tag);
        weatherForecast.put(CORTAG, tag);

        // Add contact to contact list
        weatherForecast_9Days.add(weatherForecast);
    }
    public static void checkIconMap(){
        if(iconMap.isEmpty()) {
            iconMap.put("50", Integer.toString(R.drawable.pic50));
            iconMap.put("51", Integer.toString(R.drawable.pic51));
            iconMap.put("52", Integer.toString(R.drawable.pic52));
            iconMap.put("53", Integer.toString(R.drawable.pic53));
            iconMap.put("54", Integer.toString(R.drawable.pic54));

            iconMap.put("60", Integer.toString(R.drawable.pic60));
            iconMap.put("61", Integer.toString(R.drawable.pic61));
            iconMap.put("62", Integer.toString(R.drawable.pic62));
            iconMap.put("63", Integer.toString(R.drawable.pic63));
            iconMap.put("64", Integer.toString(R.drawable.pic64));
            iconMap.put("65", Integer.toString(R.drawable.pic65));

            iconMap.put("70", Integer.toString(R.drawable.pic70));
            iconMap.put("71", Integer.toString(R.drawable.pic71));
            iconMap.put("72", Integer.toString(R.drawable.pic72));
            iconMap.put("73", Integer.toString(R.drawable.pic73));
            iconMap.put("74", Integer.toString(R.drawable.pic74));
            iconMap.put("75", Integer.toString(R.drawable.pic75));
            iconMap.put("76", Integer.toString(R.drawable.pic76));
            iconMap.put("77", Integer.toString(R.drawable.pic77));

            iconMap.put("80", Integer.toString(R.drawable.pic80));
            iconMap.put("81", Integer.toString(R.drawable.pic81));
            iconMap.put("82", Integer.toString(R.drawable.pic82));
            iconMap.put("83", Integer.toString(R.drawable.pic83));
            iconMap.put("84", Integer.toString(R.drawable.pic84));
            iconMap.put("85", Integer.toString(R.drawable.pic85));

            iconMap.put("90", Integer.toString(R.drawable.pic90));
            iconMap.put("91", Integer.toString(R.drawable.pic91));
            iconMap.put("92", Integer.toString(R.drawable.pic92));
            iconMap.put("93", Integer.toString(R.drawable.pic93));
        }
    }
    public static String formatDate(String date){
        String formatted;
        String y, m, d;
        y = date.substring(0,4);
        m = date.substring(4,6);
        d = date.substring(6);
        formatted = m+"-"+d;
        return formatted;

    }
}
