package hk.edu.ouhk.weatherapplication;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import hk.edu.ouhk.weatherapplication.APIHandler.Database.DatabaseHelper;
import hk.edu.ouhk.weatherapplication.APIHandler.FeltearthquakeAPIHandler.FeltearthquakeAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.FlwAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.FndAPIHandler.FndAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.HhotAPIHandler.HhotAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.HltAPIHandler.HltAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.MoonPhase;
import hk.edu.ouhk.weatherapplication.APIHandler.MrsAPIHandler.MrsAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.QemAPIHandler.QemAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.RhrreadAPIHandler.RhrreadAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.SrsAPIHandler.SrsAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.SwtAPIHandler.SwtAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.WarningInfoAPIHandler.WarningInfo;
import hk.edu.ouhk.weatherapplication.APIHandler.WarningInfoAPIHandler.WarningInfoAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.WarnsumAPIHandler.Warnsum;
import hk.edu.ouhk.weatherapplication.APIHandler.WarnsumAPIHandler.WarnsumAPIHandler;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import hk.edu.ouhk.weatherapplication.APIHandler.ffcWeatherAPIHandler.ffcWeatherAPIHandler;
import hk.edu.ouhk.weatherapplication.ServiceHandler.Restarter;
import hk.edu.ouhk.weatherapplication.ServiceHandler.WarningInfo_Service;
import hk.edu.ouhk.weatherapplication.ServiceHandler.WeatherNoticeService;
import hk.edu.ouhk.weatherapplication.ui.LocalForecast.LocalForecastViewModel;
import hk.edu.ouhk.weatherapplication.ui.NineDays.NineDaysFragment;
import hk.edu.ouhk.weatherapplication.ui.gallery.GalleryFragment;
import hk.edu.ouhk.weatherapplication.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActionBar mActionBar;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    public static Context mContext;
    public static DatabaseHelper db;

    MrsAPIHandler mrsAPIHandler;
    SrsAPIHandler srsAPIHandler;

    private static final Object mrslock = new Object();
    private static final Object srslock = new Object();

    private static final String LOGTAG = "MainActivity";
    static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private FusedLocationProviderClient fusedLocationClient;
    private String latitude;
    private String longitude;
    private String address;
    public Double latitudeGet;
    public Double longitudeGet;

    public SharedPreferences sharedPreferences;
    private static boolean isEnglish = true;
    public static String lang = "en";
    public static String datalang = "en";
    public static Boolean isConnected;

    private boolean mToolBarNavigationListenerIsRegistered = false;

    Intent mServiceIntent;
    private WeatherNoticeService weatherNoticeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("Setting" , MODE_PRIVATE);
        setLocale(this);
        lang = sharedPreferences.getString("Language", "en");
        isEnglish = sharedPreferences.getBoolean("isEnglish", true);
        datalang = sharedPreferences.getString("DataLang", "en");

        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        //updateLanguageVariable();
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new DatabaseHelper(mContext);
        mrsAPIHandler = new MrsAPIHandler();
        srsAPIHandler = new SrsAPIHandler();

        mActionBar = getSupportActionBar();
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        sharedPreferences.edit().putString("Language", lang).apply();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_local, R.id.nav_9_day, R.id.nav_abouticons)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        /*actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer , toolbar, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        drawer.addDrawerListener(actionBarDrawerToggle);*/
        if (!checkPermission()) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        } else {
            setUpLocationClient();
        }

        // animate the sun
        //new updateUI().execute();
        //FlwAPIHandler flwAPIHandler = new FlwAPIHandler();
        //updateLocalForecast();
        //FndAPIHandler fndAPIHandler = new FndAPIHandler();
        //RhrreadAPIHandler rhrreadAPIHandler = new RhrreadAPIHandler();

        //WarnsumAPIHandler warnsumAPIHandler = new WarnsumAPIHandler();
        //Test warnsumAPIHandler with json example (warnsumTest.json)
        /*warnsumAPIHandler.loadJSONFromAsset(this);
        warnsumAPIHandler.getJsonData();*/

        //WarningInfoAPIHandler warningInfoAPIHandler = new WarningInfoAPIHandler();
        //Test warningInfoAPIHandler with json example (warningInfoTest.json)
        //warningInfoAPIHandler.loadJSONFromAsset(this);
        //warningInfoAPIHandler.getJsonData();

        //SwtAPIHandler swtAPIHandler = new SwtAPIHandler();
        //Test swtAPIHandler with json example (swtTest.json)
        /*swtAPIHandler.loadJSONFromAsset(this);
        swtAPIHandler.getJsonData();*/

        //QemAPIHandler qemAPIHandler = new QemAPIHandler();
        //FeltearthquakeAPIHandler feltearthquakeAPIHandler = new FeltearthquakeAPIHandler();

        HhotAPIHandler hhotAPIHandler = new HhotAPIHandler(2021,"CCH");
        HltAPIHandler hltAPIHandler = new HltAPIHandler("CCH");

        if(! HomeFragment.isFirstTime) {
            HomeFragment.callAPIData();
        }

        //startService(new Intent(this, WeatherNoticeService.class));

        weatherNoticeService = new WeatherNoticeService();
        mServiceIntent = new Intent(this, weatherNoticeService.getClass());
        if (!isMyServiceRunning(weatherNoticeService.getClass())) {
            startService(mServiceIntent);
        }



    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("Service status", "Running");
                return true;
            }
        }
        Log.i ("Service status", "Not running");
        return false;
    }

    @Override
    protected void onDestroy() {
        //stopService(mServiceIntent);
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, Restarter.class);
        this.sendBroadcast(broadcastIntent);
        super.onDestroy();
    }

    public boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setUpLocationClient();
                } else {
                    String msg = "Please run the app again and grant the required permission.";
                    Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                    finish();
                }
                return;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void setUpLocationClient() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
    }

    private void getLastLocation() {
        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this,
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            longitudeGet = location.getLongitude();
                            latitudeGet = location.getLatitude();
                        }
                    });
        }*/

    }

    public static Context getContext(){
        return mContext;
    }
    @Override
    public void onResume() {

        super.onResume();
    }
    @Override
    public void onRestart() {
        super.onRestart();
    }
    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    // method for animate the sun, will called by fragment
    public void animateSun(){
        new updateSun().execute();
    }

    public void animateMoon(){
        new updateMoon().execute();
    }

    //UI render, changing
    public void changeBackground(float ratio){
        View root = HomeFragment.root;
        ConstraintLayout home = root.findViewById(R.id.fragment_home);
        ImageView grass = root.findViewById(R.id.grass);
        if(ratio == -1.0f){
            home.setBackgroundResource(R.drawable.night_2);
            grass.setImageResource(R.drawable.bg_11_2);
        }else if(ratio <= 25.0f ){
            home.setBackgroundResource(R.drawable.cloud_3);
            grass.setImageResource(R.drawable.bg_11_1);
        } else if (ratio <= 85.0f){
            home.setBackgroundResource(R.drawable.cloud_5);
            grass.setImageResource(R.drawable.bg_11_1);
        } else {
            home.setBackgroundResource(R.drawable.cloud_2);
            grass.setImageResource(R.drawable.bg_11_2);
        }
    }
    public void changeToolbarColor(float ratio){
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer , toolbar, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        drawer.addDrawerListener(actionBarDrawerToggle);
        Drawable overflowicon = toolbar.getOverflowIcon();

        if( ratio == -1.0f || ratio > 25.0f){
            toolbar.setTitleTextColor(Color.WHITE);
            overflowicon.setTint(Color.WHITE);
            actionBarDrawerToggle.getDrawerArrowDrawable().setColor(Color.WHITE);
            actionBarDrawerToggle.syncState();

        } else{
            toolbar.setTitleTextColor(Color.BLACK);
            overflowicon.setTint(Color.BLACK);
            actionBarDrawerToggle.getDrawerArrowDrawable().setColor(Color.BLACK);
            actionBarDrawerToggle.syncState();

        }
    }

   @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
                super.onBackPressed();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void updateAllWeather(){
        NineDaysFragment.update9day();
    }
    public void setLanguage(String lang, Boolean isEnglish, String datalang){
        sharedPreferences.edit().putString("Language", lang).apply();
        sharedPreferences.edit().putBoolean("isEnglish", isEnglish).apply();
        sharedPreferences.edit().putString("DataLang", datalang).apply();
    }
    public void updateLanguageVariable(){
        lang = sharedPreferences.getString("Language", "en");
        isEnglish = sharedPreferences.getBoolean("isEnglish", true);
        datalang = sharedPreferences.getString("DataLang", "en");
        Log.d("datalang", datalang);
    }

    public void setLocale(Context context) {
        String newLang = sharedPreferences.getString("Language", lang);
        Locale locale = new Locale(newLang);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        context.createConfigurationContext(config);
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        Locale.setDefault(locale);
        //HomeFragment.callAPIData();
    }
    public void setLocale(Context context, String languageCode) {
        Locale locale = new Locale(languageCode);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        context.createConfigurationContext(config);
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        Locale.setDefault(locale);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
        //HomeFragment.callAPIData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        View root = HomeFragment.root;
        int id = item.getItemId();
        //Testing Menu Item Click
        //AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        /*dialog.setTitle("Menu Item Click Test");
        dialog.setMessage("Selected Item: " + String.valueOf(id));
        AlertDialog ad = dialog.create();
        ad.show();*/
        float ratio;
        if(id == R.id.action_settings){
            ratio = 78.0f;
            changeBackground(ratio);
            changeToolbarColor(ratio);
        }
        else if (id == R.id.action_language) {
            /*ratio = 25.0f;
            changeBackground(ratio);
            changeToolbarColor(ratio);*/
            if(!isEnglish){
                isEnglish = !isEnglish;
                lang = "en";
                datalang = "en";
                setLanguage(lang, isEnglish, datalang);
                setLocale(this, sharedPreferences.getString("Language", lang));
            }


        }
        else if (id == R.id.action_language_2) {
            /*ratio = 25.0f;
            changeBackground(ratio);
            changeToolbarColor(ratio);*/
            //setLocale(lang);
            if(isEnglish) {
                isEnglish = !isEnglish;
                lang = "zh";
                datalang = "tc";
                setLanguage(lang, isEnglish, datalang);
                setLocale(this, sharedPreferences.getString("Language", lang));
            }

        }
        else if (id == R.id.action_test){
            ratio = 85.0f;
            changeBackground(ratio);
            changeToolbarColor(ratio);
        }
        else if (id == R.id.action_about){
            StringBuilder sb = new StringBuilder();
            sb.append(getResources().getString(R.string.about_content_name));
            sb.append("\n");
            sb.append(getResources().getString(R.string.about_content_author));
            sb.append("\n");
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle(R.string.about);
            dialog.setMessage(sb.toString());
            dialog.show();
        }
        else if (id == R.id.action_refresh){
            /*AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle("Refresh Option");
            dialog.setMessage("Refresh weather information method invoked here");
            dialog.show();*/

            //NineDaysFragment.update9day();
            //RhrreadAPIHandler.changeLang();
            RhrreadAPIHandler.lang = sharedPreferences.getString("DataLang",datalang);
            HomeFragment.callAPIData();
            HomeFragment.getWeatherData();
            //HomeFragment.removeWarningIcon();
        }
            else if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


    public void onLocationChanged(@NonNull Location location) {}


    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    private class updateSun extends AsyncTask<Void , Void , Float> {

        @Override
        protected void onPreExecute() {
            //執行前 設定可以在這邊設定
        }

        @Override
        protected Float doInBackground(Void... params) {
            //執行中 在背景做事情
            //SrsAPIHandler srsAPIHandler = new SrsAPIHandler();

            //float percentage = srsAPIHandler.calSunTimePass();
            float percentage = srsAPIHandler.calSunTimePass();
            Log.d("AsyncTask", "doInBackground: "+percentage);


            return percentage;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //執行中 可以在這邊告知使用者進度

        }

        @Override
        protected void onPostExecute(Float percentage) {

            //執行後 完成背景任務
            super.onPostExecute(percentage);
            ImageView sunView = findViewById(R.id.sunView);

            // set the layout size to double of sun image size

            Path newPath = new Path();
            final RectF oval = new RectF();

            float screenHeight = (float) getScreenHeight();
            float screenWidth = (float) getScreenWidth();

            float screenWidthCenter_x, screenHeightCenter_y;

            screenWidthCenter_x = screenWidth / 2;
            screenHeightCenter_y = screenHeight / 2;

            oval.set(0,
                    screenWidth,
                    screenHeightCenter_y - (sunView.getDrawable().getIntrinsicWidth()/2.0f),
                    screenWidth + (sunView.getDrawable().getIntrinsicHeight())
            );
            newPath.addArc(oval, 180, 180);

            if (percentage > 0) {
                sunView.setVisibility(View.VISIBLE);
            }

            long duration = (long) (percentage / 100 * 1000 * 2.5);
            //Changing UI component attributes value
            changeToolbarColor(percentage);
            changeBackground(percentage);
            if(duration>0) {

                PathMeasure measure = new PathMeasure(newPath, false);
                float length = measure.getLength();
                Path partialPath = new Path();
                measure.getSegment(0.0f, (length * percentage) / 100.0f, partialPath, true);
                partialPath.rLineTo(0.0f, 0.0f); // workaround to display on hardware accelerated canvas as described in docs

                //ValueAnimator pathAnimator = ObjectAnimator.ofFloat(sunView,"x","y", partialPath);
                ValueAnimator pathAnimator = ObjectAnimator.ofFloat(sunView, "x", "y", partialPath);
                pathAnimator.setDuration(duration);
                pathAnimator.start();
            }

        }
    }

    private class updateMoon extends AsyncTask<Void , Void , Float> {

        @Override
        protected void onPreExecute() {
            //執行前 設定可以在這邊設定
        }

        @Override
        protected Float doInBackground(Void... params) {
            //執行中 在背景做事情
            //MrsAPIHandler mrsAPIHandler = new MrsAPIHandler();

            //float percentage = srsAPIHandler.calSunTimePass();
            float percentage = mrsAPIHandler.calMoonTimePass();
            Log.d("AsyncTask", "doInBackground: "+percentage);

            //float percentage = -1.0f; // initialize to your desired percentage

            return percentage;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //執行中 可以在這邊告知使用者進度

        }

        @Override
        protected void onPostExecute(Float percentage) {
            //執行後 完成背景任務
            super.onPostExecute(percentage);
            ImageView moonView = findViewById(R.id.moonView);


            MoonPhase moonPhase = new MoonPhase();
            String phase = moonPhase.calMoonPhase();

            if(phase.equals("new")){
                moonView.setImageResource(R.drawable.newmoon);
            }else if(phase.equals("waxing crescent")){
                moonView.setImageResource(R.drawable.waningcrescent);
            }else if(phase.equals("first quarter")){
                moonView.setImageResource(R.drawable.firstquarter);
            }else if(phase.equals("waxing gibbous")){
                moonView.setImageResource(R.drawable.waxinggibbous);
            }else if(phase.equals("full")){
                moonView.setImageResource(R.drawable.fullmoon);
            }else if(phase.equals("waning gibbous")){
                moonView.setImageResource(R.drawable.waninggibbous);
            }else if(phase.equals("last quarter")){
                moonView.setImageResource(R.drawable.thirdquarter);
            }else if(phase.equals("waning crescent")){
                moonView.setImageResource(R.drawable.waningcrescent);
            }

            Path newPath = new Path();
            final RectF oval = new RectF();

            float screenHeight = (float) getScreenHeight();
            float screenWidth = (float) getScreenWidth();

            float screenWidthCenter_x, screenHeightCenter_y;

            screenWidthCenter_x = screenWidth / 2;
            screenHeightCenter_y = screenHeight / 2;

            oval.set(0,
                    screenWidth,
                    screenHeightCenter_y - (moonView.getDrawable().getIntrinsicWidth()/2.0f),
                    screenWidth + (moonView.getDrawable().getIntrinsicHeight())
            );
            newPath.addArc(oval, 180, 180);

            if(percentage > 0){
                moonView.setVisibility(View.VISIBLE);
            }

            long duration = (long) (percentage / 100 * 1000 * 2.5);
            //Changing UI component attributes value

            if(duration>0) {
                PathMeasure measure = new PathMeasure(newPath, false);
                float length = measure.getLength();
                Path partialPath = new Path();
                measure.getSegment(0.0f, (length * percentage) / 100.0f, partialPath, true);
                partialPath.rLineTo(0.0f, 0.0f); // workaround to display on hardware accelerated canvas as described in docs

                //ValueAnimator pathAnimator = ObjectAnimator.ofFloat(sunView,"x","y", partialPath);
                ValueAnimator pathAnimator = ObjectAnimator.ofFloat(moonView, "x", "y", partialPath);
                pathAnimator.setDuration(duration);
                pathAnimator.start();
            }

        }
    }
}