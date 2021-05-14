package hk.edu.ouhk.weatherapplication.ui.home;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import hk.edu.ouhk.weatherapplication.MainActivity;
import hk.edu.ouhk.weatherapplication.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public static View root = null;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.temp_high);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                HomeFragment.updateAllWeatherInfo();
                HomeFragment.showDate();
            }
        });

        setUserVisibleHint(false);
        setUserVisibleHint(true);

        //ConstraintLayout home = root.findViewById(R.id.fragment_home);
        //home.setBackgroundResource(R.drawable.bg_1);

        return root;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //Code executes EVERY TIME user views the fragment
        ((MainActivity)getActivity()).animateSun();
    }
    public static void updateWeatherInfo(int viewId, int tagId, String str, int unitId){
        View root = HomeFragment.root;
        TextView info = root.findViewById(viewId);
        String tag = MainActivity.getContext().getString(tagId);
        String unit = MainActivity.getContext().getString(unitId);
        info.setText(" " +str + " " +unit);
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
        updateWeatherInfo(R.id.windspeed,"100", R.string.mph);
        updateWeatherInfo(R.id.temp_high,  "33", R.string.celsius);
        updateWeatherInfo(R.id.temp_low, "28", R.string.celsius);
        updateWeatherInfo(R.id.current,"32", R.string.celsius);
        updateWeatherInfo(R.id.humidity, "88", R.string.percentage);
        updateWeatherInfo(R.id.rainingchance, "30", R.string.percentage);
        updateWeatherInfo(R.id.updatetime, "2021-05-14 17:30");
        updateWeatherInfo(R.id.pastrainfall, "10", R.string.mm);

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
// try to replace Function By UI