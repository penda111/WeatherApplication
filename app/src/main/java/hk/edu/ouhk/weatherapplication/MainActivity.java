package hk.edu.ouhk.weatherapplication;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
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
import hk.edu.ouhk.weatherapplication.APIHandler.WarningInfoAPIHandler.WarningInfoAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.WarnsumAPIHandler.WarnsumAPIHandler;
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

    private boolean mToolBarNavigationListenerIsRegistered = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new DatabaseHelper(mContext);
        mrsAPIHandler = new MrsAPIHandler();
        srsAPIHandler = new SrsAPIHandler();

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
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
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer , toolbar, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        drawer.addDrawerListener(actionBarDrawerToggle);


        // animate the sun
        //new updateUI().execute();
        FlwAPIHandler flwAPIHandler = new FlwAPIHandler();
        FndAPIHandler fndAPIHandler = new FndAPIHandler();
        RhrreadAPIHandler rhrreadAPIHandler = new RhrreadAPIHandler();

        WarnsumAPIHandler warnsumAPIHandler = new WarnsumAPIHandler();
        //Test warnsumAPIHandler with json example (warnsumTest.json)
        /*warnsumAPIHandler.loadJSONFromAsset(this);
        warnsumAPIHandler.getJsonData();*/

        WarningInfoAPIHandler warningInfoAPIHandler = new WarningInfoAPIHandler();
        //Test warningInfoAPIHandler with json example (warningInfoTest.json)
        /*warningInfoAPIHandler.loadJSONFromAsset(this);
        warningInfoAPIHandler.getJsonData();*/

        SwtAPIHandler swtAPIHandler = new SwtAPIHandler();
        //Test swtAPIHandler with json example (swtTest.json)
        /*swtAPIHandler.loadJSONFromAsset(this);
        swtAPIHandler.getJsonData();*/

        QemAPIHandler qemAPIHandler = new QemAPIHandler();
        FeltearthquakeAPIHandler feltearthquakeAPIHandler = new FeltearthquakeAPIHandler();

        HhotAPIHandler hhotAPIHandler = new HhotAPIHandler(2021,"CCH");
        HltAPIHandler hltAPIHandler = new HltAPIHandler("CCH");



    }
    public static Context getContext(){
        return mContext;
    }
    @Override
    public void onResume() {
        /*actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer , toolbar, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        drawer.addDrawerListener(actionBarDrawerToggle);*/
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
        } else if (ratio <= 75.0f){
            home.setBackgroundResource(R.drawable.cloud_5);
            grass.setImageResource(R.drawable.bg_11_1);
        } else if (ratio <= 83.3f){
            home.setBackgroundResource(R.drawable.cloud_2);
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

        } else{
            toolbar.setTitleTextColor(Color.BLACK);
            overflowicon.setTint(Color.BLACK);
            actionBarDrawerToggle.getDrawerArrowDrawable().setColor(Color.BLACK);

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
            dialog.setMessage("Refresh weather information method invoked here");
            dialog.show();*/

        }
            else if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    /*public void updateWeatherInfo(int id, String tag, String value, String unit){
        View root = HomeFragment.root;
        TextView humidity = root.findViewById(id);
        humidity.setText(tag + ": " + value + " " +unit);
    }

    public void updateWeatherInfo(int viewId, int tagId, String str, int unitId){
        View root = HomeFragment.root;
        TextView humidity = root.findViewById(viewId);
        String tag = getResources().getString(tagId);
        String unit = getResources().getString(unitId);
        humidity.setText(tag + ": " + str + " " +unit);
    }
    public void updateAllWeatherInfo(){
        updateWeatherInfo(R.id.windspeed, R.string.windspeed, "100", R.string.mph);
        updateWeatherInfo(R.id.temp_high, R.string.temp_high, "33", R.string.celsius);
        updateWeatherInfo(R.id.temp_low, R.string.temp_low, "28", R.string.celsius);
        updateWeatherInfo(R.id.humidity, R.string.humidity, "88", R.string.percentage);
        updateWeatherInfo(R.id.rainingchance, R.string.rainingchance, "30", R.string.percentage);
    }
    public void showDate(){
        displayDate = HomeFragment.root.findViewById(R.id.display_date);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy (EEE)");
        //dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        date = dateFormat.format(calendar.getTime());
        displayDate.setText(date);
    }*/

    /*public void displaySun(){
        //Sun display
        View sunView = findViewById(R.id.sunView);
        LinearLayout sunLayout = findViewById(R.id.sunLayout);
        // change sun display after all ui done their works
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Path newPath = new Path();
                ImageView sunView = findViewById(R.id.sunView);
                ImageView moonView = findViewById(R.id.moonView);

                LinearLayout sunLayout = findViewById(R.id.sunLayout);
                LinearLayout moonLayout = findViewById(R.id.moonLayout);
                // set the layout size to double of sun image size
                sunLayout.setMinimumHeight(sunView.getMeasuredHeight()*2);
                moonLayout.setMinimumHeight(moonView.getMeasuredHeight()*2);

                float sunLayoutHeight = (float) sunLayout.getHeight();
                float sunLayoutWidth = (float) sunLayout.getWidth();

                float moonLayoutHeight = (float) moonLayout.getHeight();
                float moonLayoutWidth = (float) moonLayout.getWidth();

                float sunLayoutCenter_x, sunLayoutCenter_y, moonLayoutCenter_x, moonLayoutCenter_y;
                final RectF oval = new RectF();

                sunLayoutCenter_x = sunLayoutWidth/2;
                sunLayoutCenter_y = sunLayoutHeight/2;

                moonLayoutCenter_x = moonLayoutWidth/2;
                moonLayoutCenter_y = moonLayoutHeight/2;

                // draw half oval path
                oval.set(0,
                        0,
                        sunLayoutCenter_x +(moonView.getDrawable().getIntrinsicHeight()/2.0f),
                        sunLayoutHeight + (moonView.getDrawable().getIntrinsicHeight())
                );
                newPath.addArc(oval, 180, 180);
                SrsAPIHandler srsAPIHandler = new SrsAPIHandler();
                MrsAPIHandler mrsAPIHandler = new MrsAPIHandler();

                //float percentage = srsAPIHandler.calSunTimePass();
                float percentage = mrsAPIHandler.calMoonTimePass();

                //float percentage = -1.0f; // initialize to your desired percentage

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

                oval.set(0,
                        0,
                        moonLayoutCenter_x +(sunView.getDrawable().getIntrinsicHeight()/2.0f),
                        sunLayoutHeight + (sunView.getDrawable().getIntrinsicHeight())
                );

                percentage = srsAPIHandler.calSunTimePass();
                //percentage = 50.0f;

                if(percentage > 0){
                    sunView.setVisibility(View.VISIBLE);
                }

                duration = Math.round(3 * percentage/100) * 1000;
                //Changing UI component attributes value

                measure = new PathMeasure(newPath, false);
                length = measure.getLength();
                partialPath = new Path();
                measure.getSegment(0.0f, (length * percentage) / 100.0f, partialPath, true);
                partialPath.rLineTo(0.0f, 0.0f); // workaround to display on hardware accelerated canvas as described in docs

                //ValueAnimator pathAnimator = ObjectAnimator.ofFloat(sunView,"x","y", partialPath);
                pathAnimator = ObjectAnimator.ofFloat(sunView,"x","y", partialPath);
                pathAnimator.setDuration(duration);
                pathAnimator.start();

            }
        });
    }*/

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