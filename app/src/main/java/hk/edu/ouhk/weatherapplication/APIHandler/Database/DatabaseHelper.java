package hk.edu.ouhk.weatherapplication.APIHandler.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String TAG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "weatherInfo_9days";

    // Table Names
    private static final String TABLE_DAY = "day";
    private static final String TABLE_TEMPERATURE = "temperature";
    private static final String TABLE_SUN = "sun";
    private static final String TABLE_MOON = "moon";
    private static final String TABLE_HLT = "hlt";
    private static final String TABLE_HHOT = "hhot";


    // Common column names
    private static final String KEY_DATE = "date";

    // Day Table - column nmaes
    private static final String KEY_RAINDROPRATE = "rainDropRate";

    // Sun Table - column names
    private static final String KEY_SUNRISE = "sunRise";
    private static final String KEY_SUNSET = "sunSet";

    // Moon Table - column names
    private static final String KEY_MOONRISE = "moonRise";
    private static final String KEY_MOONSET = "moonSet";

    // Hlt / Hhot Table - column names
    private static final String KEY_HOUR = "hour";
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_STATION = "station";

    // Temperature Table - column names
    private static final String KEY_MaxTemperature = "maxTemperature";
    private static final String KEY_MinTemperature = "minTemperature";

    // Table Create Statements
    // Day table create statement
    private static final String CREATE_TABLE_DAY = "CREATE TABLE "
            + TABLE_DAY + "(" + KEY_DATE + " DATETIME PRIMARY KEY," +
            KEY_RAINDROPRATE + " TEXT" + ")";

    // Temperature table create statement
    private static final String CREATE_TABLE_Temperature = "CREATE TABLE "
            + TABLE_TEMPERATURE + "(" + KEY_DATE + " DATETIME PRIMARY KEY,"
            + KEY_MaxTemperature + " TEXT," + KEY_MinTemperature + " TEXT," + " FOREIGN KEY ("+KEY_DATE+") REFERENCES" +
            " "+TABLE_DAY+"("+KEY_DATE+"))";

    // Sun table create statement
    private static final String CREATE_TABLE_SUN = "CREATE TABLE " + TABLE_SUN
            + "(" + KEY_DATE + " DATETIME PRIMARY KEY," + KEY_SUNRISE + " TEXT,"
            + KEY_SUNSET + " TEXT," + " FOREIGN KEY ("+KEY_DATE+") REFERENCES" +
            " "+TABLE_DAY+"("+KEY_DATE+"))";

    // Moon table create statement
    private static final String CREATE_TABLE_MOON = "CREATE TABLE "
            + TABLE_MOON + "(" + KEY_DATE + " DATETIME PRIMARY KEY,"
            + KEY_MOONRISE + " TEXT," + KEY_MOONSET + " TEXT," + " FOREIGN KEY ("+KEY_DATE+") REFERENCES" +
            " "+TABLE_DAY+"("+KEY_DATE+"))";

    // Hlt table create statement
    private static final String CREATE_TABLE_HLT = "CREATE TABLE "
            + TABLE_HLT + "(" + KEY_DATE + " DATETIME PRIMARY KEY,"
            + KEY_HOUR + "1 TEXT," + KEY_HEIGHT + "1 TEXT," +
            KEY_HOUR + "2 TEXT," + KEY_HEIGHT + "2 TEXT," +
            KEY_HOUR + "3 TEXT," + KEY_HEIGHT + "3 TEXT," +
            KEY_HOUR + "4 TEXT," + KEY_HEIGHT + "4 TEXT," +
            KEY_STATION + " TEXT,"+ " FOREIGN KEY ("+KEY_DATE+") " +
            "REFERENCES" + " "+TABLE_DAY+"("+KEY_DATE+"))";

    // Hhot table create statement
    private static final String CREATE_TABLE_HHOT = "CREATE TABLE "
            + TABLE_HHOT + "(" + KEY_DATE + " DATETIME PRIMARY KEY,"
            + KEY_HOUR + " TEXT," + KEY_HEIGHT + " TEXT," + " FOREIGN KEY ("+KEY_DATE+") REFERENCES" +
            " "+TABLE_DAY+"("+KEY_DATE+"))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_DAY);
        db.execSQL(CREATE_TABLE_Temperature);
        db.execSQL(CREATE_TABLE_SUN);
        db.execSQL(CREATE_TABLE_MOON);
        db.execSQL(CREATE_TABLE_HLT);
        db.execSQL(CREATE_TABLE_HHOT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMPERATURE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOON);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HLT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HHOT);

        // create new tables
        onCreate(db);
    }

    public synchronized void rebuildTable_Day(){
        Log.d(TAG, "(rebuildTable_Day)Thread name=: " +Thread.currentThread().getName());
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAY);
        db.execSQL(CREATE_TABLE_DAY);
    }

    public synchronized void rebuildTable_Temperature(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMPERATURE);
        db.execSQL(CREATE_TABLE_Temperature);
    }

    public synchronized void rebuildTable_Hlt(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HLT);
        db.execSQL(CREATE_TABLE_HLT);
    }

    /*
     * Creating a day
     */
    public synchronized void createDay(HashMap<String, String> weatherForecast_9Days) {
        Log.d(TAG, "(createDay)Thread name=: " +Thread.currentThread().getName());

        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String forecastDate = weatherForecast_9Days.get("forecastDate");
        String date = forecastDate.substring(0,4)+"-"+forecastDate.substring(4,6)+"-"+forecastDate.substring(6,8);

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, date);
        values.put(KEY_RAINDROPRATE, weatherForecast_9Days.get("PSR"));

        // insert row
        long todo_id = db.insert(TABLE_DAY, null, values);

        //return todo_id;
    }

    public synchronized void createTemperature(HashMap<String, String> weatherForecast_9Days) {
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String forecastDate = weatherForecast_9Days.get("forecastDate");
        String date = forecastDate.substring(0,4)+"-"+forecastDate.substring(4,6)+"-"+forecastDate.substring(6,8);

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, date);
        values.put(KEY_MaxTemperature, weatherForecast_9Days.get("forecastMaxtempValue"));
        values.put(KEY_MinTemperature, weatherForecast_9Days.get("forecastMintempValue"));

        // insert row
        db.insert(TABLE_TEMPERATURE, null, values);

    }

    public synchronized void createHlt(HashMap<String, String> hltList) {
        SQLiteDatabase db = this.getWritableDatabase();

        String hltDate = hltList.get("date");
        String date = hltDate.substring(6,10) + "-" + hltDate.substring(3,5) + "-" + hltDate.substring(0,2);

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, date);
        values.put(KEY_HOUR+"1", hltList.get("0"));
        values.put(KEY_HEIGHT+"1", hltList.get("1"));
        values.put(KEY_HOUR+"2", hltList.get("2"));
        values.put(KEY_HEIGHT+"2", hltList.get("3"));
        values.put(KEY_HOUR+"3", hltList.get("4"));
        values.put(KEY_HEIGHT+"3", hltList.get("5"));
        values.put(KEY_HOUR+"4", hltList.get("6"));
        values.put(KEY_HEIGHT+"4", hltList.get("7"));
        values.put(KEY_STATION, hltList.get("station"));

        // insert row
        db.insert(TABLE_HLT, null, values);

    }
}
