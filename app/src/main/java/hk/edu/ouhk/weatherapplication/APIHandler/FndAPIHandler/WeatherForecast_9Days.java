package hk.edu.ouhk.weatherapplication.APIHandler.FndAPIHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class WeatherForecast_9Days {
    public static String FORECASTDATE = "forecastDate";
    public static String WEEK = "week";
    public static String FORECASTWIND = "forecastWind";
    public static String FORECASTWEATHER = "forecastWeather";
    public static String FORECASTMAXTEMPVALUE = "forecastMaxtempValue";
    public static String FORECASTMINTEMPVALUE = "forecastMintempValue";
    public static String FORECASTMAXRHVALUE = "forecastMaxrhValue";
    public static String FORECASTMINRHVALUE = "forecastMinrhValue";
    public static String FORECASTICON = "ForecastIcon";
    public static String PSRString = "PSR";

    public static ArrayList<HashMap<String, String>> weatherForecast_9Days = new ArrayList<>();

    // Creates and add contact to contact list
    public static void addWeatherForecast(String forecastDate, String week, String forecastWind, String forecastWeather, String forecastMaxtempValue, String forecastMintempValue, String forecastMaxrhValue, String forecastMinrhValue, String ForecastIcon, String PSR) {
        // Create contact
        HashMap<String, String> weatherForecast = new HashMap<>();
        weatherForecast.put(FORECASTDATE, forecastDate);
        weatherForecast.put(WEEK, week);
        weatherForecast.put(FORECASTWIND, forecastWind);
        weatherForecast.put(FORECASTWEATHER, forecastWeather);
        weatherForecast.put(FORECASTMAXTEMPVALUE, forecastMaxtempValue);
        weatherForecast.put(FORECASTMINTEMPVALUE, forecastMintempValue);
        weatherForecast.put(FORECASTMAXRHVALUE, forecastMaxrhValue);
        weatherForecast.put(FORECASTMINRHVALUE, forecastMinrhValue);
        weatherForecast.put(FORECASTICON, ForecastIcon);
        weatherForecast.put(PSRString, PSR);

        // Add contact to contact list
        weatherForecast_9Days.add(weatherForecast);
    }
}
