package hk.edu.ouhk.weatherapplication;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import hk.edu.ouhk.weatherapplication.APIHandler.FeltearthquakeAPIHandler.FeltearthquakeAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.FlwAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.FndAPIHandler.FndAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.HhotAPIHandler.HhotAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.HltAPIHandler.HltAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.MrsAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.MoonPhase;
import hk.edu.ouhk.weatherapplication.APIHandler.QemAPIHandler.QemAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.RhrreadAPIHandler.RhrreadAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.SrsAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.SwtAPIHandler.SwtAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.WarningInfoAPIHandler.WarningInfoAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.WarnsumAPIHandler.Warnsum;
import hk.edu.ouhk.weatherapplication.APIHandler.WarnsumAPIHandler.WarnsumAPIHandler;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import hk.edu.ouhk.weatherapplication.ui.LocalForecast.LocalForecastViewModel;
import hk.edu.ouhk.weatherapplication.ui.gallery.GalleryFragment;
import hk.edu.ouhk.weatherapplication.ui.home.HomeFragment;
import hk.edu.ouhk.weatherapplication.ui.home.HomeViewModel;
import hk.edu.ouhk.weatherapplication.ui.slideshow.SlideshowFragment;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActionBar mActionBar;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    public static Context mContext;

    private static final String LOGTAG = "MainActivity";
    static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private FusedLocationProviderClient fusedLocationClient;
    private String latitude;
    private String longitude;
    private String address;
    public Double latitudeGet;
    public Double longitudeGet;



    private boolean mToolBarNavigationListenerIsRegistered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mActionBar = getSupportActionBar();
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


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
                R.id.nav_home, R.id.nav_local, R.id.nav_9_day, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_abouticons)
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

        WarnsumAPIHandler warnsumAPIHandler = new WarnsumAPIHandler();
        //Test warnsumAPIHandler with json example (warnsumTest.json)
        /*warnsumAPIHandler.loadJSONFromAsset(this);
        warnsumAPIHandler.getJsonData();*/

        WarningInfoAPIHandler warningInfoAPIHandler = new WarningInfoAPIHandler();
        //Test warningInfoAPIHandler with json example (warningInfoTest.json)
        warningInfoAPIHandler.loadJSONFromAsset(this);
        warningInfoAPIHandler.getJsonData();

        SwtAPIHandler swtAPIHandler = new SwtAPIHandler();
        //Test swtAPIHandler with json example (swtTest.json)
        /*swtAPIHandler.loadJSONFromAsset(this);
        swtAPIHandler.getJsonData();*/

        QemAPIHandler qemAPIHandler = new QemAPIHandler();
        FeltearthquakeAPIHandler feltearthquakeAPIHandler = new FeltearthquakeAPIHandler();

        HhotAPIHandler hhotAPIHandler = new HhotAPIHandler("CCH");
        HltAPIHandler hltAPIHandler = new HltAPIHandler("CCH");
        MoonPhase moonPhase = new MoonPhase();

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
        if(ratio <= 25.0f ){
            home.setBackgroundResource(R.drawable.cloud_3);
            grass.setImageResource(R.drawable.bg_11_1);
        } else if (ratio <= 75.0f){
            home.setBackgroundResource(R.drawable.cloud_5);
            grass.setImageResource(R.drawable.bg_11_1);
        } else if (ratio <= 83.3f){
            home.setBackgroundResource(R.drawable.cloud_2);
            grass.setImageResource(R.drawable.bg_11_2);
        } else{
            home.setBackgroundResource(R.drawable.night_2);
            grass.setImageResource(R.drawable.bg_11_2);
        }
    }
    public void changeToolbarColor(float ratio){
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer , toolbar, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        drawer.addDrawerListener(actionBarDrawerToggle);
        Drawable overflowicon = toolbar.getOverflowIcon();

        if( ratio > 25.0f){
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
            ratio = 25.0f;
            changeBackground(ratio);
            changeToolbarColor(ratio);

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
            dialog.setMessage(longitude);
            dialog.show();*/
            //HomeFragment.updateWeatherInfo(R.id.current, "30", R.string.celsius);
            //HomeFragment.getWeatherData();
            updateLocalForecast();
            //HomeFragment.getWeatherData();
            HomeFragment.removeWarningIcon();
        }
            else if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


    public void updateLocalForecast(){
        LocalForecastViewModel lfvm =
            new ViewModelProvider(this).get(LocalForecastViewModel.class);
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    private class updateSun extends AsyncTask<Void , Void , Float> {

        @Override
        protected void onPreExecute() {
            //執行前 設定可以在這邊設定
        }

        @Override
        protected Float doInBackground(Void... params) {
            //執行中 在背景做事情
            SrsAPIHandler srsAPIHandler = new SrsAPIHandler();

            //float percentage = srsAPIHandler.calSunTimePass();
            float percentage = srsAPIHandler.calSunTimePass();
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
            ImageView sunView = findViewById(R.id.sunView);

            LinearLayout sunLayout = findViewById(R.id.sunLayout);
            // set the layout size to double of sun image size
            sunLayout.setMinimumHeight(sunView.getMeasuredHeight()*2);

            Path newPath = new Path();
            final RectF oval = new RectF();

            float sunLayoutHeight = (float) sunLayout.getHeight();
            float sunLayoutWidth = (float) sunLayout.getWidth();

            float sunLayoutCenter_x, sunLayoutCenter_y;


            sunLayoutCenter_x = sunLayoutWidth/2;
            sunLayoutCenter_y = sunLayoutHeight/2;

            oval.set(0,
                    0,
                    sunLayoutCenter_x +(sunView.getDrawable().getIntrinsicHeight()/2.0f),
                    sunLayoutHeight + (sunView.getDrawable().getIntrinsicHeight())
            );
            newPath.addArc(oval, 180, 180);

            if(percentage > 0){
                sunView.setVisibility(View.VISIBLE);
            }

            int duration = Math.round(3 * percentage/100) * 1000;
            //Changing UI component attributes value

            PathMeasure measure = new PathMeasure(newPath, false);
            float length = measure.getLength();
            Path partialPath = new Path();
            measure.getSegment(0.0f, (length * percentage) / 100.0f, partialPath, true);
            partialPath.rLineTo(0.0f, 0.0f); // workaround to display on hardware accelerated canvas as described in docs

            //ValueAnimator pathAnimator = ObjectAnimator.ofFloat(sunView,"x","y", partialPath);
            ValueAnimator pathAnimator = ObjectAnimator.ofFloat(sunView,"x","y", partialPath);
            pathAnimator.setDuration(duration);
            pathAnimator.start();

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
            MrsAPIHandler mrsAPIHandler = new MrsAPIHandler();

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

            LinearLayout moonLayout = findViewById(R.id.moonLayout);
            // set the layout size to double of sun image size
            moonLayout.setMinimumHeight(moonView.getMeasuredHeight()*2);

            Path newPath = new Path();
            final RectF oval = new RectF();

            float moonLayoutHeight = (float) moonLayout.getHeight();
            float moonLayoutWidth = (float) moonLayout.getWidth();

            float moonLayoutCenter_x, moonLayoutCenter_y;

            moonLayoutCenter_x = moonLayoutWidth/2;
            moonLayoutCenter_y = moonLayoutHeight/2;

            oval.set(0,
                    0,
                    moonLayoutCenter_x +(moonView.getDrawable().getIntrinsicHeight()/2.0f),
                    moonLayoutCenter_y + (moonView.getDrawable().getIntrinsicHeight())
            );
            newPath.addArc(oval, 180, 180);

            if(percentage > 0){
                moonView.setVisibility(View.VISIBLE);
            }

            int duration = Math.round(3 * percentage/100) * 1000;
            //Changing UI component attributes value
            changeToolbarColor(percentage);
            changeBackground(percentage);

            PathMeasure measure = new PathMeasure(newPath, false);
            float length = measure.getLength();
            Path partialPath = new Path();
            measure.getSegment(0.0f, (length * percentage) / 100.0f, partialPath, true);
            partialPath.rLineTo(0.0f, 0.0f); // workaround to display on hardware accelerated canvas as described in docs

            //ValueAnimator pathAnimator = ObjectAnimator.ofFloat(sunView,"x","y", partialPath);
            ValueAnimator pathAnimator = ObjectAnimator.ofFloat(moonView,"x","y", partialPath);
            pathAnimator.setDuration(duration);
            pathAnimator.start();

        }
    }
}