package hk.edu.ouhk.weatherapplication.APIHandler.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import hk.edu.ouhk.weatherapplication.APIHandler.FndAPIHandler.WeatherForecast_9Days;
import hk.edu.ouhk.weatherapplication.APIHandler.HhotAPIHandler.Hhot;
import hk.edu.ouhk.weatherapplication.APIHandler.HltAPIHandler.Hlt;
import hk.edu.ouhk.weatherapplication.APIHandler.MrsAPIHandler.Mrs;
import hk.edu.ouhk.weatherapplication.APIHandler.SrsAPIHandler.Srs;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String TAG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "weatherInfo_9days";

    // Table Names
    private static final String TABLE_DAY = "day";
    private static final String TABLE_SUN = "sun";
    private static final String TABLE_MOON = "moon";
    private static final String TABLE_HLT = "hlt";
    private static final String TABLE_HHOT = "hhot";


    // Common column names
    private static final String KEY_DATE = "date";

    // Day Table - column nmaes
    private static final String KEY_WEEK = "week";
    private static final String KEY_FORECASTWIND = "forecastWind";
    private static final String KEY_FORECASTWEATHER = "forecastWeather";
    private static final String KEY_FORECASTMAXTEMPVALUE = "forecastMaxtempValue";
    private static final String KEY_FORECASTMINTEMPVALUE = "forecastMintempValue";
    private static final String KEY_FORECASTMAXRHVALUE = "forecastMaxrhValue";
    private static final String KEY_FORECASTMINRHVALUE = "forecastMinrhValue";
    private static final String KEY_FORECASTICON = "ForecastIcon";
    private static final String KEY_PSR = "PSR";

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


    // Table Create Statements
    // Day table create statement
    private static final String CREATE_TABLE_DAY = "CREATE TABLE "
            + TABLE_DAY + "("
            + KEY_DATE + " DATETIME PRIMARY KEY,"
            + KEY_WEEK + " TEXT,"
            + KEY_FORECASTWIND + " TEXT,"
            + KEY_FORECASTWEATHER + " TEXT,"
            + KEY_FORECASTMAXTEMPVALUE + " TEXT,"
            + KEY_FORECASTMINTEMPVALUE + " TEXT,"
            + KEY_FORECASTMAXRHVALUE + " TEXT,"
            + KEY_FORECASTMINRHVALUE + " TEXT,"
            + KEY_FORECASTICON + " TEXT,"
            + KEY_PSR + " TEXT"
            + ")";

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
            + KEY_HOUR + "0 TEXT," + KEY_HOUR + "1 TEXT," +
            KEY_HOUR + "2 TEXT," + KEY_HOUR + "3 TEXT," +
            KEY_HOUR + "4 TEXT," + KEY_HOUR + "5 TEXT," +
            KEY_HOUR + "6 TEXT," + KEY_HOUR + "7 TEXT," +
            KEY_HOUR + "8 TEXT," + KEY_HOUR + "9 TEXT," +
            KEY_HOUR + "10 TEXT," + KEY_HOUR + "11 TEXT," +
            KEY_HOUR + "12 TEXT," + KEY_HOUR + "13 TEXT," +
            KEY_HOUR + "14 TEXT," + KEY_HOUR + "15 TEXT," +
            KEY_HOUR + "16 TEXT," + KEY_HOUR + "17 TEXT," +
            KEY_HOUR + "18 TEXT," + KEY_HOUR + "19 TEXT," +
            KEY_HOUR + "20 TEXT," + KEY_HOUR + "21 TEXT," +
            KEY_HOUR + "22 TEXT," + KEY_HOUR + "23 TEXT," +
            KEY_STATION + " TEXT,"
            + " FOREIGN KEY ("+KEY_DATE+") REFERENCES" +
            " "+TABLE_DAY+"("+KEY_DATE+"))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_DAY);
        db.execSQL(CREATE_TABLE_SUN);
        db.execSQL(CREATE_TABLE_MOON);
        db.execSQL(CREATE_TABLE_HLT);
        db.execSQL(CREATE_TABLE_HHOT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAY);
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

    public synchronized void rebuildTable_Hlt(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HLT);
        db.execSQL(CREATE_TABLE_HLT);
    }

    public synchronized void rebuildTable_Hhot(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HHOT);
        db.execSQL(CREATE_TABLE_HHOT);
    }

    public synchronized void deleteOldDay() {
        Log.d(TAG, "(deleteOldDay)Thread name=: " +Thread.currentThread().getName());


        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        //Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24)*3);

        db.execSQL("DELETE FROM "+ TABLE_DAY + " where " +KEY_DATE+" < Datetime(\""+dateFormat.format(today)+"\")");
        //Cursor c = db.rawQuery("select * from "+ TABLE_DAY ,null);

    }

    public synchronized void deleteOldHlt() {

        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        //Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24)*3);

        db.execSQL("DELETE FROM "+ TABLE_HLT + " where " +KEY_DATE+" < Datetime(\""+dateFormat.format(today)+"\")");
        //Cursor c = db.rawQuery("select * from "+ TABLE_DAY ,null);

    }

    public synchronized void deleteOldHhot() {

        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        //Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24)*3);

        db.execSQL("DELETE FROM "+ TABLE_HHOT + " where " +KEY_DATE+" < Datetime(\""+dateFormat.format(today)+"\")");
        //Cursor c = db.rawQuery("select * from "+ TABLE_DAY ,null);

    }

    public synchronized void deleteOldSun() {

        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        //Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24)*3);

        db.execSQL("DELETE FROM "+ TABLE_SUN + " where " +KEY_DATE+" < Datetime(\""+dateFormat.format(today)+"\")");
        //Cursor c = db.rawQuery("select * from "+ TABLE_DAY ,null);

    }

    public synchronized void deleteOldMoon() {

        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        //Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24)*3);

        db.execSQL("DELETE FROM "+ TABLE_MOON + " where " +KEY_DATE+" < Datetime(\""+dateFormat.format(today)+"\")");
        //Cursor c = db.rawQuery("select * from "+ TABLE_DAY ,null);

    }

    public synchronized void createDay(HashMap<String, String> weatherForecast_9Days) {
        Log.d(TAG, "(createDay)Thread name=: " +Thread.currentThread().getName());
        SQLiteDatabase db = this.getReadableDatabase();
        String forecastDate = weatherForecast_9Days.get("forecastDate");
        String date = forecastDate.substring(0,4)+"-"+forecastDate.substring(4,6)+"-"+forecastDate.substring(6,8);

        String sql ="SELECT * FROM "+TABLE_DAY+" WHERE "+KEY_DATE +" = \""+date+"\"";
        //Log.d(TAG, "sql: " + sql);

        Cursor c = db.rawQuery(sql,null);
        //Log.d(TAG, "date_from_db: " + c);

        String date_from_db = "";

        if (c != null & c.getCount() >= 1){
            c.moveToFirst();
            date_from_db = c.getString(c.getColumnIndex(KEY_DATE));
            Log.d(TAG, "date_from_db: " + date_from_db);
        }

        if(!date_from_db.equals(date)) {
            ContentValues values = new ContentValues();
            values.put(KEY_DATE, date);
            values.put(KEY_WEEK, weatherForecast_9Days.get("week"));
            values.put(KEY_FORECASTWIND, weatherForecast_9Days.get("forecastWind"));
            values.put(KEY_FORECASTWEATHER, weatherForecast_9Days.get("forecastWeather"));
            values.put(KEY_FORECASTMAXTEMPVALUE, weatherForecast_9Days.get("forecastMaxtempValue"));
            values.put(KEY_FORECASTMINTEMPVALUE, weatherForecast_9Days.get("forecastMintempValue"));
            values.put(KEY_FORECASTMAXRHVALUE, weatherForecast_9Days.get("forecastMaxrhValue"));
            values.put(KEY_FORECASTMINRHVALUE, weatherForecast_9Days.get("forecastMinrhValue"));
            values.put(KEY_FORECASTICON, weatherForecast_9Days.get("forecastIcon"));
            values.put(KEY_PSR, weatherForecast_9Days.get("PSR"));

            // insert row
            long todo_id = db.insert(TABLE_DAY, null, values);
        }

        //return todo_id;
    }

    public synchronized void createHlt(HashMap<String, String> hltList) {
        SQLiteDatabase db = this.getReadableDatabase();

        String hltDate = hltList.get("date");
        String date = hltDate.substring(6,10) + "-" + hltDate.substring(3,5) + "-" + hltDate.substring(0,2);
        String sql ="SELECT * FROM "+TABLE_HLT+" WHERE "+KEY_DATE +" = \""+date+"\"";
        //Log.d(TAG, "sql: " + sql);

        Cursor c = db.rawQuery(sql,null);
        //Log.d(TAG, "date_from_db: " + c);

        String date_from_db = "";

        if (c != null & c.getCount() >= 1){
            c.moveToFirst();
            date_from_db = c.getString(c.getColumnIndex(KEY_DATE));
            //Log.d(TAG, "date_from_db: " + date_from_db);
        }

        if(!date_from_db.equals(date)) {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_DATE, date);
            values.put(KEY_HOUR + "1", hltList.get("0"));
            values.put(KEY_HEIGHT + "1", hltList.get("1"));
            values.put(KEY_HOUR + "2", hltList.get("2"));
            values.put(KEY_HEIGHT + "2", hltList.get("3"));
            values.put(KEY_HOUR + "3", hltList.get("4"));
            values.put(KEY_HEIGHT + "3", hltList.get("5"));
            values.put(KEY_HOUR + "4", hltList.get("6"));
            values.put(KEY_HEIGHT + "4", hltList.get("7"));
            values.put(KEY_STATION, hltList.get("station"));

            // insert row
            db.insert(TABLE_HLT, null, values);
        }

    }

    public synchronized void createHhot(HashMap<String, String> hhotList) {
        Log.d(TAG, "(createHhot)Thread name=: " +Thread.currentThread().getName());
        SQLiteDatabase db = this.getReadableDatabase();
        String HhotDate = hhotList.get("date");
        String date = HhotDate.substring(6,10) + "-" + HhotDate.substring(3,5) + "-" + HhotDate.substring(0,2);

        String sql ="SELECT * FROM "+TABLE_HHOT+" WHERE "+KEY_DATE +" = \""+date+"\"";
        //Log.d(TAG, "sql: " + sql);

        Cursor c = db.rawQuery(sql,null);
        //Log.d(TAG, "date_from_db: " + c);

        String date_from_db = "";

        if (c != null & c.getCount() >= 1){
            c.moveToFirst();
            date_from_db = c.getString(c.getColumnIndex(KEY_DATE));
            //Log.d(TAG, "date_from_db: " + date_from_db);
        }
        if(!date_from_db.equals(date)) {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_DATE, date);
            for (int i = 0; i < 24; i++) {
                values.put(KEY_HOUR + Integer.toString(i), hhotList.get(Integer.toString(i)));
            }
            values.put(KEY_STATION, hhotList.get("station"));

            // insert row
            long todo_id = db.insert(TABLE_HHOT, null, values);
        }
        //return todo_id;
    }

    public synchronized void createSrs(HashMap<String, String> srsList) {
        Log.d(TAG, "(createSrs)Thread name=: " +Thread.currentThread().getName());
        SQLiteDatabase db = this.getReadableDatabase();
        String date = srsList.get("date");

        String sql ="SELECT * FROM "+TABLE_SUN+" WHERE "+KEY_DATE +" = \""+date+"\"";
        Log.d(TAG, "sql: " + sql);

        Cursor c = db.rawQuery(sql,null);
        //Log.d(TAG, "date_from_db: " + c);

        String date_from_db = "";

        if (c != null & c.getCount() >= 1){
            c.moveToFirst();
            date_from_db = c.getString(c.getColumnIndex(KEY_DATE));
            //Log.d(TAG, "date_from_db: " + date_from_db);
        }
        if(!date_from_db.equals(date)) {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_DATE, date);
            values.put(KEY_SUNRISE, srsList.get("sunRise"));
            values.put(KEY_SUNSET, srsList.get("sunSet"));

            // insert row
            long todo_id = db.insert(TABLE_SUN, null, values);
        }
        //return todo_id;
    }

    public synchronized void createMrs(HashMap<String, String> mrsList) {
        Log.d(TAG, "(createMrs)Thread name=: " +Thread.currentThread().getName());
        SQLiteDatabase db = this.getReadableDatabase();
        String date = mrsList.get("date");

        String sql ="SELECT * FROM "+TABLE_MOON+" WHERE "+KEY_DATE +" = \""+date+"\"";
        //Log.d(TAG, "sql: " + sql);

        Cursor c = db.rawQuery(sql,null);
        //Log.d(TAG, "date_from_db: " + c);

        String date_from_db = "";

        if (c != null & c.getCount() >= 1){
            c.moveToFirst();
            date_from_db = c.getString(c.getColumnIndex(KEY_DATE));
            //Log.d(TAG, "date_from_db: " + date_from_db);
        }
        if(!date_from_db.equals(date)) {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_DATE, date);
            values.put(KEY_MOONRISE, mrsList.get("moonRise"));
            values.put(KEY_MOONSET, mrsList.get("moonSet"));

            // insert row
            long todo_id = db.insert(TABLE_MOON, null, values);
        }
        //return todo_id;
    }

    public synchronized ArrayList<HashMap<String, String>> getDay() {
        Log.d(TAG, "(getDay)Thread name=: " +Thread.currentThread().getName());

        ArrayList<HashMap<String, String>> weatherForecast_9Days_Database = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Date tomorrow = new Date(date.getTime() + (1000 * 60 * 60 * 24));

        /*Cursor c =
                db.rawQuery("select * from "+ TABLE_DAY + " where " +KEY_DATE+" > Datetime(\""+dateFormat.format(tomorrow)+"\")",null);*/
        Cursor c = db.rawQuery("select * from "+ TABLE_DAY ,null);

        if (c.moveToFirst()) {
            do {
                HashMap<String, String> weatherForecast = new HashMap<>();

                weatherForecast.put(WeatherForecast_9Days.FORECASTDATE, c.getString((c.getColumnIndex(KEY_DATE))));
                weatherForecast.put(WeatherForecast_9Days.WEEK, c.getString((c.getColumnIndex(KEY_WEEK))));
                weatherForecast.put(WeatherForecast_9Days.FORECASTWIND, c.getString((c.getColumnIndex(KEY_FORECASTWIND))));
                weatherForecast.put(WeatherForecast_9Days.FORECASTWEATHER, c.getString((c.getColumnIndex(KEY_FORECASTWEATHER))));
                weatherForecast.put(WeatherForecast_9Days.FORECASTMAXTEMPVALUE, c.getString((c.getColumnIndex(KEY_FORECASTMAXTEMPVALUE))));
                weatherForecast.put(WeatherForecast_9Days.FORECASTMINTEMPVALUE, c.getString((c.getColumnIndex(KEY_FORECASTMINTEMPVALUE))));
                weatherForecast.put(WeatherForecast_9Days.FORECASTMAXRHVALUE, c.getString((c.getColumnIndex(KEY_FORECASTMAXRHVALUE))));
                weatherForecast.put(WeatherForecast_9Days.FORECASTMINRHVALUE, c.getString((c.getColumnIndex(KEY_FORECASTMINRHVALUE))));
                weatherForecast.put(WeatherForecast_9Days.FORECASTICON, c.getString((c.getColumnIndex(KEY_FORECASTICON))));
                weatherForecast.put(WeatherForecast_9Days.PSRString, c.getString((c.getColumnIndex(KEY_PSR))));


                weatherForecast_9Days_Database.add(weatherForecast);
            } while (c.moveToNext());
        }

        Log.d(TAG, "getDay: "+ weatherForecast_9Days_Database);
        return weatherForecast_9Days_Database;
    }

    public synchronized ArrayList<HashMap<String, String>> getHlt() {
        Log.d(TAG, "(getHlt)Thread name=: " +Thread.currentThread().getName());

        ArrayList<HashMap<String, String>> hltList_Database = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Date tomorrow = new Date(date.getTime() + (1000 * 60 * 60 * 24));
/*
        Cursor c = db.rawQuery("select * from "+ TABLE_DAY + " where " +KEY_DATE+" > Datetime(\""+dateFormat.format(tomorrow)+"\")",null);
*/
        Cursor c = db.rawQuery("select * from "+ TABLE_HLT ,null);

        if (c.moveToFirst()) {
            do {
                HashMap<String, String> hlt = new HashMap<>();

                hlt.put(Hlt.DATE, c.getString((c.getColumnIndex(KEY_DATE))));
                hlt.put("0", c.getString((c.getColumnIndex(KEY_HOUR+"1"))));
                hlt.put("1", c.getString((c.getColumnIndex(KEY_HEIGHT+"1"))));
                hlt.put("2", c.getString((c.getColumnIndex(KEY_HOUR+"2"))));
                hlt.put("3", c.getString((c.getColumnIndex(KEY_HEIGHT+"2"))));
                hlt.put("4", c.getString((c.getColumnIndex(KEY_HOUR+"3"))));
                hlt.put("5", c.getString((c.getColumnIndex(KEY_HEIGHT+"3"))));
                hlt.put("6", c.getString((c.getColumnIndex(KEY_HOUR+"4"))));
                hlt.put("7", c.getString((c.getColumnIndex(KEY_HEIGHT+"4"))));
                hlt.put(Hlt.STATION, c.getString((c.getColumnIndex(KEY_STATION))));

                hltList_Database.add(hlt);
            } while (c.moveToNext());
        }
        Log.d(TAG, "getHlt: "+ hltList_Database);
        return hltList_Database;
    }

    public synchronized ArrayList<HashMap<String, String>> getHhot() {
        Log.d(TAG, "(getHhot)Thread name=: " +Thread.currentThread().getName());

        ArrayList<HashMap<String, String>> hhotList_Database = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Date tomorrow = new Date(date.getTime() + (1000 * 60 * 60 * 24));
/*
        Cursor c = db.rawQuery("select * from "+ TABLE_DAY + " where " +KEY_DATE+" > Datetime(\""+dateFormat.format(tomorrow)+"\")",null);
*/
        Cursor c = db.rawQuery("select * from "+ TABLE_HHOT ,null);

        if (c.moveToFirst()) {
            do {
                HashMap<String, String> hhot = new HashMap<>();

                hhot.put(Hhot.DATE, c.getString((c.getColumnIndex(KEY_DATE))));
                for (int i = 0; i < 24; i++) {
                    hhot.put(Integer.toString(i), c.getString((c.getColumnIndex(KEY_HOUR+Integer.toString(i)))));
                }

                hhot.put(Hhot.STATION, c.getString((c.getColumnIndex(KEY_STATION))));

                hhotList_Database.add(hhot);
            } while (c.moveToNext());
        }
        Log.d(TAG, "getHhot: "+ hhotList_Database);
        return hhotList_Database;
    }

    public synchronized ArrayList<HashMap<String, String>> getSrs() {
        Log.d(TAG, "(getSrs)Thread name=: " +Thread.currentThread().getName());

        ArrayList<HashMap<String, String>> srsList_Database = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Date tomorrow = new Date(date.getTime() + (1000 * 60 * 60 * 24));
/*
        Cursor c = db.rawQuery("select * from "+ TABLE_DAY + " where " +KEY_DATE+" > Datetime(\""+dateFormat.format(tomorrow)+"\")",null);
*/
        Cursor c = db.rawQuery("select * from "+ TABLE_SUN ,null);

        if (c.moveToFirst()) {
            do {
                HashMap<String, String> srs = new HashMap<>();

                srs.put(Srs.DATE, c.getString((c.getColumnIndex(KEY_DATE))));
                srs.put(Srs.SUNRISE, c.getString((c.getColumnIndex(KEY_SUNRISE))));
                srs.put(Srs.SUNSET, c.getString((c.getColumnIndex(KEY_SUNSET))));

                srsList_Database.add(srs);
            } while (c.moveToNext());
        }
        Log.d(TAG, "getSrs: "+ srsList_Database);
        return srsList_Database;
    }

    public synchronized ArrayList<HashMap<String, String>> getMrs() {
        //Log.d(TAG, "(getMrs)Thread name=: " +Thread.currentThread().getName());

        ArrayList<HashMap<String, String>> mrsList_Database = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Date tomorrow = new Date(date.getTime() + (1000 * 60 * 60 * 24));
/*
        Cursor c = db.rawQuery("select * from "+ TABLE_DAY + " where " +KEY_DATE+" > Datetime(\""+dateFormat.format(tomorrow)+"\")",null);
*/
        Cursor c = db.rawQuery("select * from "+ TABLE_MOON ,null);

        if (c.moveToFirst()) {
            do {
                HashMap<String, String> mrs = new HashMap<>();

                mrs.put(Mrs.DATE, c.getString((c.getColumnIndex(KEY_DATE))));
                mrs.put(Mrs.MOONRISE, c.getString((c.getColumnIndex(KEY_MOONRISE))));
                mrs.put(Mrs.MOONSET, c.getString((c.getColumnIndex(KEY_MOONSET))));
                
                mrsList_Database.add(mrs);
            } while (c.moveToNext());
        }
        Log.d(TAG, "getMrs: "+ mrsList_Database);
        return mrsList_Database;
    }





}
