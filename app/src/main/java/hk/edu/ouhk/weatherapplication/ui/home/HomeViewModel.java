package hk.edu.ouhk.weatherapplication.ui.home;



import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import hk.edu.ouhk.weatherapplication.APIHandler.RhrreadAPIHandler.Humidity;
import hk.edu.ouhk.weatherapplication.APIHandler.RhrreadAPIHandler.RhrreadAPIHandler;
import hk.edu.ouhk.weatherapplication.MainActivity;
import hk.edu.ouhk.weatherapplication.R;


public class HomeViewModel extends ViewModel {

    //private MutableLiveData<String> humidity;
    public MutableLiveData<String> humidity;

    public HomeViewModel() {
        RhrreadAPIHandler rhrreadAPIHandler = new RhrreadAPIHandler();
        String humidityValue = Humidity.humidityList.get(0).get("humidityValue");
        humidity = new MutableLiveData<>();
        humidity.setValue(humidityValue);
    }
    public LiveData<String> getHumidity() {
        return humidity;
    }
}