package hk.edu.ouhk.weatherapplication.ui.NineDays;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;

import hk.edu.ouhk.weatherapplication.APIHandler.Database.DatabaseHelper;
import hk.edu.ouhk.weatherapplication.APIHandler.FndAPIHandler.FndAPIHandler;
import hk.edu.ouhk.weatherapplication.MainActivity;
import hk.edu.ouhk.weatherapplication.R;
import hk.edu.ouhk.weatherapplication.APIHandler.FndAPIHandler.WeatherForecast_9Days;

public class NineDaysFragment extends Fragment {
    private static ListView listview;
    public static View root;

    public static ArrayList<HashMap<String, String>> wf9List = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_9_day, container, false);
        listview = root.findViewById(R.id.listView_9days);
        update9day();
        /*WeatherForecast_9Days wf9 = new WeatherForecast_9Days();
        SimpleAdapter adapter = new SimpleAdapter(
                MainActivity.getContext(),
                WeatherForecast_9Days.weatherForecast_9Days,
                R.layout.item,
                new String[] { WeatherForecast_9Days.FORECASTDATE,
                        WeatherForecast_9Days.WEEK,
                        WeatherForecast_9Days.FORECASTWIND,
                        WeatherForecast_9Days.FORECASTMINTEMPVALUE,
                        WeatherForecast_9Days.FORECASTMAXTEMPVALUE,
                        WeatherForecast_9Days.FORECASTMINRHVALUE,
                        WeatherForecast_9Days.FORECASTMAXRHVALUE,
                        WeatherForecast_9Days.FORECASTWEATHER,
                        WeatherForecast_9Days.ICONID,
                        WeatherForecast_9Days.PSRString },
                new int[] { R.id.text_date,
                        R.id.text_week,
                        R.id.text_wind,
                        R.id.text_temp_min,
                        R.id.text_temp_max,
                        R.id.text_humidity_min,
                        R.id.text_humidity_max,
                        R.id.text_info,
                        R.id.icon_9day,
                        R.id.psr
                }
        );
        listview.setAdapter(adapter);*/


        return root;
    }
public static void update9day(){
    ListView listview = NineDaysFragment.root.findViewById(R.id.listView_9days);
    //callAPIData();
    if(MainActivity.isNetworkAvailable(MainActivity.getContext())) {
        callAPIData();
    }  else {
        DatabaseHelper dbh = new DatabaseHelper(MainActivity.getContext());
        wf9List = dbh.getDay();
        wf9List.remove(0);
    }
/*    DatabaseHelper dbh = new DatabaseHelper(MainActivity.getContext());
    wf9List = dbh.getDay();
    wf9List.remove(0);*/
//    WeatherForecast_9Days wf9 = new WeatherForecast_9Days();
//    ArrayList<HashMap<String, String>> wf9List = WeatherForecast_9Days.weatherForecast_9Days;
    SimpleAdapter adapter = new SimpleAdapter(
            MainActivity.getContext(),
            wf9List,
            R.layout.item,
            new String[] { WeatherForecast_9Days.FORMATTED,
                    WeatherForecast_9Days.WEEK,
                    WeatherForecast_9Days.FORECASTWIND,
                    WeatherForecast_9Days.FORECASTMINTEMPVALUE,
                    WeatherForecast_9Days.FORECASTMAXTEMPVALUE,
                    WeatherForecast_9Days.FORECASTMINRHVALUE,
                    WeatherForecast_9Days.FORECASTMAXRHVALUE,
                    WeatherForecast_9Days.FORECASTWEATHER,
                    WeatherForecast_9Days.ICONID,
                    WeatherForecast_9Days.PSRString
            },
            new int[] { R.id.text_date,
                    R.id.text_week,
                    R.id.text_wind,
                    R.id.text_temp_min,
                    R.id.text_temp_max,
                    R.id.text_humidity_min,
                    R.id.text_humidity_max,
                    R.id.text_info,
                    R.id.icon_9day,
                    R.id.psr
            }
    );

    listview.setAdapter(adapter);
}
    public static void callAPIData() {
        if (MainActivity.isNetworkAvailable(MainActivity.getContext())) {
            Thread th = new Thread() {
                public void run() {
                    FndAPIHandler fnd = new FndAPIHandler();

                    WeatherForecast_9Days wf9 = new WeatherForecast_9Days();
                    wf9List = WeatherForecast_9Days.weatherForecast_9Days;
                }
            };
            th.start();
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
