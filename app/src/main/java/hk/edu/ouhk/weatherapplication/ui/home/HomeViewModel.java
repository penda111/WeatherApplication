package hk.edu.ouhk.weatherapplication.ui.home;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import hk.edu.ouhk.weatherapplication.MainActivity;
import hk.edu.ouhk.weatherapplication.R;


public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("High °C");
    }

    public LiveData<String> getText() {
        return mText;
    }
    public static void updateWeatherInfo(int viewId, int tagId, String str, int unitId){
        View root = HomeFragment.root;
        TextView humidity = root.findViewById(viewId);
        String tag = MainActivity.getContext().getString(tagId);
        String unit = MainActivity.getContext().getString(unitId);
        humidity.setText(tag + ": " + str + " " +unit);
    }
    public static void updateWeatherInfo(int id, String tag, String value, String unit){
        View root = HomeFragment.root;
        TextView humidity = root.findViewById(id);
        humidity.setText(tag + ": " + value + " " +unit);
    }
    public static void updateAllWeatherInfo(){
        updateWeatherInfo(R.id.windspeed, R.string.windspeed, "100", R.string.mph);
        updateWeatherInfo(R.id.temp_high, R.string.temp_high, "33", R.string.celsius);
        updateWeatherInfo(R.id.temp_low, R.string.temp_low, "28", R.string.celsius);
        updateWeatherInfo(R.id.humidity, R.string.humidity, "88", R.string.percentage);
        updateWeatherInfo(R.id.rainingchance, R.string.rainingchance, "30", R.string.percentage);
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
    }
}