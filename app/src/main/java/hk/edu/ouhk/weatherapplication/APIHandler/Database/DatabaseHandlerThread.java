package hk.edu.ouhk.weatherapplication.APIHandler.Database;


import android.util.Log;

import hk.edu.ouhk.weatherapplication.APIHandler.FeltearthquakeAPIHandler.FeltearthquakeAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.FlwAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.FndAPIHandler.FndAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.HhotAPIHandler.HhotAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.HltAPIHandler.HltAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.MrsAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.QemAPIHandler.QemAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.RhrreadAPIHandler.RhrreadAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.SrsAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.SwtAPIHandler.SwtAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.WarningInfoAPIHandler.WarningInfoAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.WarnsumAPIHandler.WarnsumAPIHandler;
import hk.edu.ouhk.weatherapplication.MainActivity;

public class DatabaseHandlerThread extends Thread {
    private static final String TAG = "DatabaseHandlerThread";
    // URL to get contacts JSON file
    private String api;


    public DatabaseHandlerThread(String api) {
        this.api = api;
    }

    public void run() {
        switch(api)
        {
            case "Mrs":

                break;
            case "Srs":

                break;
            case "Fnd":

                FndAPIHandler.storeDB();
                break;
            case "Hhot":

                HhotAPIHandler.getJsonData();
                break;
            case "Hlt":

                HltAPIHandler.storeDB();
                break;


        }
    }

}
