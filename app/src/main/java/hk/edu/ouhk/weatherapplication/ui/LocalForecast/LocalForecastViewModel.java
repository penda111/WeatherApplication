package hk.edu.ouhk.weatherapplication.ui.LocalForecast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import hk.edu.ouhk.weatherapplication.APIHandler.FlwAPIHandler;
import hk.edu.ouhk.weatherapplication.MainActivity;
import hk.edu.ouhk.weatherapplication.R;
public class LocalForecastViewModel extends ViewModel{
    private MutableLiveData<String> mText;

    public LocalForecastViewModel(){
        mText = new MutableLiveData<>();
        FlwAPIHandler flwAPIHandler = new FlwAPIHandler();
        StringBuilder sb = new StringBuilder();
        String generalSituation = FlwAPIHandler.generalSituation;
        String forecastPeriod = FlwAPIHandler.forecastPeriod;
        String forecastDesc = FlwAPIHandler.forecastDesc;
        String updateTime = FlwAPIHandler.updateTime;
        sb.append("\n");
        sb.append(generalSituation);
        sb.append("\n" + "\n");
        sb.append(forecastPeriod);
        sb.append("\n" + "\n");
        sb.append(forecastDesc);
        sb.append("\n\n" );
        sb.append(updateTime);
        mText.setValue(sb.toString());
    }
    public LiveData<String> getText() {
        if(mText == null){
            mText = new MutableLiveData<>();
        }
        return mText;
    }
}


