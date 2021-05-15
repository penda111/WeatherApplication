package hk.edu.ouhk.weatherapplication.ui.home;

import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import hk.edu.ouhk.weatherapplication.APIHandler.FndAPIHandler.FndAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.MrsAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.RhrreadAPIHandler.Humidity;
import hk.edu.ouhk.weatherapplication.APIHandler.RhrreadAPIHandler.Rainfall;
import hk.edu.ouhk.weatherapplication.APIHandler.RhrreadAPIHandler.RhrreadAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.RhrreadAPIHandler.Temperature;
import hk.edu.ouhk.weatherapplication.APIHandler.RhrreadAPIHandler.UVindex;
import hk.edu.ouhk.weatherapplication.APIHandler.SrsAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.WarnsumAPIHandler.Warnsum;
import hk.edu.ouhk.weatherapplication.APIHandler.WarnsumAPIHandler.WarnsumAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.ffcWeatherAPIHandler.ffcWeather;
import hk.edu.ouhk.weatherapplication.APIHandler.ffcWeatherAPIHandler.ffcWeatherAPIHandler;
import hk.edu.ouhk.weatherapplication.MainActivity;
import hk.edu.ouhk.weatherapplication.R;
import hk.edu.ouhk.weatherapplication.ui.LocalForecast.LocalForecastFragment;
import hk.edu.ouhk.weatherapplication.ui.NineDays.NineDaysFragment;

public class HomeFragment extends Fragment {
    private static String today;
    private HomeViewModel homeViewModel;
    public static View root = null;
    public static LinearLayout ll;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.temp_high);
        ll = root.findViewById(R.id.warning);
        showDate();
        updateAllWeatherInfo();
        getWeatherData();
        /*homeViewModel.getHumidity().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //updateWeatherInfo(R.id.humidity, s, R.string.percentage);

            }
        });*/


        //setUserVisibleHint(false);
        setUserVisibleHint(true);


        return root;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //Code executes EVERY TIME user views the fragment
        ((MainActivity)getActivity()).animateSun();
        ((MainActivity)getActivity()).animateMoon();
    }
    public static void getWeatherData() {
        try {

        RhrreadAPIHandler rhrreadAPIHandler = new RhrreadAPIHandler();
        FndAPIHandler fndAPIHandler = new FndAPIHandler();
        WarnsumAPIHandler warnsumAPIHandler = new WarnsumAPIHandler();
        ffcWeatherAPIHandler ffc = new ffcWeatherAPIHandler();
        SrsAPIHandler srs = new SrsAPIHandler();
        MrsAPIHandler mrs = new MrsAPIHandler();


        addWarningIcon();
        setffcdata();
        setRainfall("元朗");
        setCurrentTemp("元朗公園");
        setHumidity();
        setRiseSet();
        setUV();

    }
        catch(Exception e){
             e.printStackTrace();
        }
    }
    public static void updateWeatherInfo(int viewId, int tagId, String str){
        View root = HomeFragment.root;
        TextView info = root.findViewById(viewId);
        String tag = MainActivity.getContext().getString(tagId);

        info.setText(tag +": " +str);
    }
    public static void updateWeatherInfo(int id, String str){
        View root = HomeFragment.root;
        TextView info = root.findViewById(id);
        info.setText(str);
    }
    public static void updateWeatherInfo(int id, String str, int unitId){
        View root = HomeFragment.root;
        TextView info = root.findViewById(id);
        String unit = MainActivity.getContext().getString(unitId);
        info.setText(str + " " + unit);
    }
    public static void updateAllWeatherInfo(){
        //updateWeatherInfo(R.id.windspeed,"100", R.string.mph);
        //updateWeatherInfo(R.id.temp_high,  "33", R.string.celsius);
        //updateWeatherInfo(R.id.temp_low, "28", R.string.celsius);
        //updateWeatherInfo(R.id.current,"32", R.string.celsius);
        //updateWeatherInfo(R.id.humidity, "88", R.string.percentage);
        updateWeatherInfo(R.id.rainingchance, "", R.string.percentage);
        //updateWeatherInfo(R.id.updatetime, "2021-05-14 17:30");
        //updateWeatherInfo(R.id.pastrainfall, "10", R.string.mm);
        //updateWeatherInfo(R.id.uv, R.string.uv, "2");
        //update sun, moon rise/set
        //updateRiseSetName(R.id.sunTime, R.string.sunrise, R.string.sunset);
        //updateRiseSetTime(R.id.sunTimeValue, "06:32", "19:00");
        //updateRiseSetName(R.id.moonTime, R.string.moonrise, R.string.moonset);
        //updateRiseSetTime(R.id.moonTimeValue, "05:12", "17:52");

    }
    public static void updateRiseSetTime(int id, String riseTime, String setTime){
        View root = HomeFragment.root;
        TextView time = root.findViewById(id);
        time.setText(riseTime +"/" +setTime);
    }

    public static void showDate(){
        TextView displayDate;
        Calendar calendar;
        SimpleDateFormat dateFormat;
        String date;
        displayDate = HomeFragment.root.findViewById(R.id.display_date);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy (EEE)");
        //dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        date = dateFormat.format(calendar.getTime());
        displayDate.setText(date);
        dateFormat = new SimpleDateFormat("yyyyMMdd");
        today = dateFormat.format(calendar.getTime());
    }
    public static void removeWarningIcon(){
        LinearLayout ll = HomeFragment.ll;
        ll.removeAllViewsInLayout();
    }
    public static void addWarningIcon(){
        removeWarningIcon();
        LinearLayout ll = HomeFragment.ll;
        for(HashMap<String, String> warn : Warnsum.warnsumList){
            int iconId = Integer.parseInt(warn.get(Warnsum.ICONID));
            Log.d("Warn", ""+iconId);
            ImageView ii = new ImageView(MainActivity.getContext());
            ii.setImageResource(iconId);
            ii.setPadding(5,0,0,0);
            ll.addView(ii);
        }
    }
    public static void setffcdata(){
        String min = ffcWeather.ffcList.get(0).get("temp_min").substring(0,4);
        String max = ffcWeather.ffcList.get(0).get("temp_max").substring(0,4);
        String wp = ffcWeather.ffcList.get(0).get("speed");
        updateWeatherInfo(R.id.temp_high,  max, R.string.celsius);
        updateWeatherInfo(R.id.temp_low, min, R.string.celsius);
        updateWeatherInfo(R.id.windspeed, wp);
    }
    public static void setUV(){
        TextView uvView = root.findViewById(R.id.uv);
        if (UVindex.uvList.isEmpty()) {
            uvView.setVisibility(View.INVISIBLE);
        } else {
            String uv = UVindex.uvList.get(UVindex.uvList.size() - 1).get("uvValue") + " " +UVindex.uvList.get(UVindex.uvList.size() - 1).get("uvDesc");
            updateWeatherInfo(R.id.uv, R.string.uv, uv);
            uvView.setVisibility(View.VISIBLE);
        }

    }
    public static void setHumidity(){
        String humidityValue = Humidity.humidityList.get(Humidity.humidityList.size() - 1).get("humidityValue");
        updateWeatherInfo(R.id.humidity, humidityValue, R.string.percentage);
    }
    public static void setRainfall(String place){
        String rainfallValue = "0";
        for (HashMap<String, String> rf : Rainfall.rainfallList) {

            if (rf.get("place").equals(place)) {
                rainfallValue = rf.get("rainfallMaxValue");
                //Log.d("RainfallValue", rainfallValue);
                updateWeatherInfo(R.id.pastrainfall, rainfallValue, R.string.mm);
                break;
            }
        }
    }
    public static void setCurrentTemp(String place){
        String cTemp = "0";
        String time = "";
        for (HashMap<String, String> temp : Temperature.tempList) {
            if (temp.get("place").equals(place)) {
                cTemp = temp.get("tempValue");
                time = temp.get("recordTime").substring(0,10)+" "+temp.get("recordTime").substring(11,16);
                updateWeatherInfo(R.id.current, cTemp, R.string.celsius);
                updateWeatherInfo(R.id.updatetime, time);
                break;
            }
        }
    }
    public static void setRiseSet(){
        try {
            String sunrise = SrsAPIHandler.jsonObject.getJSONArray("data").getJSONArray(0).getString(1);
            String sunset = SrsAPIHandler.jsonObject.getJSONArray("data").getJSONArray(0).getString(3);
            String moonrise = MrsAPIHandler.jsonObject.getJSONArray("data").getJSONArray(0).getString(1);
            String moonset = MrsAPIHandler.jsonObject.getJSONArray("data").getJSONArray(0).getString(3);
            updateRiseSetTime(R.id.sunTimeValue, sunrise, sunset);
            updateRiseSetTime(R.id.moonTimeValue, moonrise, moonset);
        } catch(Exception e){
            e.printStackTrace();
        }
    }


}
// try to replace Function By UI