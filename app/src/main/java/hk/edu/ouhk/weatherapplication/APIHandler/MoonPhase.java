package hk.edu.ouhk.weatherapplication.APIHandler;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MoonPhase {

    private static final String TAG = "MoonPhase";

    // The duration in days of a lunar cycle
    private static final double lunardays = 29.53058770576;
    // Seconds in lunar cycle
    private static final double lunarsecs = lunardays * (24 * 60 *60);
    // Date time of first new moon in year 2000

    // Array with start and end of each phase
    // In this array 'new', 'first quarter', 'full' and 'last quarter' each get a duration of 2 days.

    Object[][] moonPhases =
            {{"new", 0, 1}, {"waxing crescent", 1, 6.38264692644}, {"first quarter",
                    6.38264692644, 8.38264692644}, {"waxing gibbous", 8.38264692644, 13.76529385288},{"full",
                    13.76529385288, 15.76529385288}, {"waning gibbous", 15.76529385288, 21.14794077932}, {"last " +
                    "quarter", 21.14794077932, 23.14794077932},{"waning crescent", 23.14794077932, 28.53058770576},{
                "new", 28.53058770576, 29.53058770576}};

    String thephase;

    public MoonPhase() {

        // Date time of first new moon in year 2000
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date1 = "2000-01-06 18:14";
        Date new2000 = null;
        try {
            new2000 = ft.parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date today = new Date();

        // Calculate seconds between date and new moon 2000
        long totalsecs = (today.getTime()-new2000.getTime())/1000;

        // Calculate modulus to drop completed cycles
        // Note: for real numbers use fmod() instead of % operator
        double currentsecs = totalsecs % lunarsecs;

        // Calculate the fraction of the moon cycle
        double currentfrac = currentsecs / lunarsecs;

        // Calculate days in current cycle (moon age)
        double currentdays = currentfrac * lunardays;

        // Find current phase in the array
        for ( int i=0; i<9; i++ ){
            if ( (currentdays >= new Double(moonPhases[i][1].toString())) && (currentdays <= new Double(moonPhases[i][2].toString())) ) {
                thephase = moonPhases[i][0].toString();
                break;
            }
        }
        Log.d(TAG, "moonPhases: "+moonPhases);
        Log.d(TAG, "thephase: "+thephase+ currentdays);
    }

}
