package hk.edu.ouhk.weatherapplication.ui.LocalForecast;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import hk.edu.ouhk.weatherapplication.APIHandler.FlwAPIHandler;
import hk.edu.ouhk.weatherapplication.MainActivity;
import hk.edu.ouhk.weatherapplication.R;

import hk.edu.ouhk.weatherapplication.R;


public class LocalForecastFragment extends Fragment{
    private LocalForecastViewModel lfvm;

    public static View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_local, container, false);
        final TextView textView = root.findViewById(R.id.text_localforecast);
        textView.setMovementMethod(new ScrollingMovementMethod());
        /*lfvm = new ViewModelProvider(this).get(LocalForecastViewModel.class);
        lfvm.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        updateLocalForecast();
        return root;
    }
public static void updateLocalForecast(){
    TextView textView = LocalForecastFragment.root.findViewById(R.id.text_localforecast);
    FlwAPIHandler flwAPIHandler = new FlwAPIHandler(MainActivity.datalang);
    StringBuilder sb = new StringBuilder();
    String generalSituation = FlwAPIHandler.generalSituation;
    String forecastPeriod = FlwAPIHandler.forecastPeriod;
    String forecastDesc = FlwAPIHandler.forecastDesc;
    String updateTime = FlwAPIHandler.updateTime.substring(0,10) + " " + FlwAPIHandler.updateTime.substring(11,16);
    sb.append("\n");
    sb.append(generalSituation);
    sb.append("\n" + "\n");
    sb.append(forecastPeriod);
    sb.append("\n" + "\n");
    sb.append(forecastDesc);
    sb.append("\n\n" );
    sb.append(updateTime);
    textView.setText(sb.toString());
    }
}


