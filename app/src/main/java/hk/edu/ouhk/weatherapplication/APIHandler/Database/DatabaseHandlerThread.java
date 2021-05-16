package hk.edu.ouhk.weatherapplication.APIHandler.Database;


import hk.edu.ouhk.weatherapplication.APIHandler.FndAPIHandler.FndAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.HhotAPIHandler.HhotAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.HltAPIHandler.HltAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.MrsAPIHandler.MrsAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.SrsAPIHandler.SrsAPIHandler;
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
                MainActivity.db.deleteOldMoon();
                //MainActivity.db.getMrs();
                MrsAPIHandler.storeDB();
                MainActivity.db.getMrs();
                break;
            case "Srs":
                MainActivity.db.checkDay();
                MainActivity.db.deleteOldSun();
                //MainActivity.db.getSrs();
                SrsAPIHandler.storeDB();
                MainActivity.db.getSrs();
                break;
            case "Fnd":
                MainActivity.db.deleteOldDay();
                //MainActivity.db.getDay();
                FndAPIHandler.storeDB();
                MainActivity.db.getDay();
                break;
            case "Hhot2":
                MainActivity.db.deleteOldHhot();
                //MainActivity.db.getHhot();
                HhotAPIHandler.storeDB();
                MainActivity.db.getHhot();
                break;
            case "Hlt":
                MainActivity.db.deleteOldHlt();
                //MainActivity.db.getHlt();
                HltAPIHandler.storeDB();
                MainActivity.db.getHlt();
                break;


        }
    }

}
