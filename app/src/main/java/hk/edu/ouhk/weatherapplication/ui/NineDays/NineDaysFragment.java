package hk.edu.ouhk.weatherapplication.ui.NineDays;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.HashMap;

import hk.edu.ouhk.weatherapplication.APIHandler.FndAPIHandler.FndAPIHandler;
import hk.edu.ouhk.weatherapplication.MainActivity;
import hk.edu.ouhk.weatherapplication.R;
import hk.edu.ouhk.weatherapplication.APIHandler.FndAPIHandler.WeatherForecast_9Days;
import hk.edu.ouhk.weatherapplication.ui.LocalForecast.LocalForecastViewModel;

public class NineDaysFragment extends Fragment {
    private static ListView listview;
    public static View root;
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
    FndAPIHandler fnd = new FndAPIHandler(MainActivity.datalang);
    WeatherForecast_9Days wf9 = new WeatherForecast_9Days();
    //ArrayList<HashMap<String, String>> wf9List = WeatherForecast_9Days.weatherForecast_9Days.get(WeatherForecast_9Days.weatherForecast_9Days.size()-1)
    SimpleAdapter adapter = new SimpleAdapter(
            MainActivity.getContext(),
            WeatherForecast_9Days.weatherForecast_9Days,
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

}
