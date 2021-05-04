package hk.edu.ouhk.weatherapplication;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.navigation.NavigationView;

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
import hk.edu.ouhk.weatherapplication.APIHandler.MoonPhase;
import hk.edu.ouhk.weatherapplication.APIHandler.QemAPIHandler.QemAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.RhrreadAPIHandler.RhrreadAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.SwtAPIHandler.SwtAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.WarningInfoAPIHandler.WarningInfoAPIHandler;
import hk.edu.ouhk.weatherapplication.APIHandler.WarnsumAPIHandler.WarnsumAPIHandler;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

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

        HhotAPIHandler hhotAPIHandler = new HhotAPIHandler("CCH");
        HltAPIHandler hltAPIHandler = new HltAPIHandler("CCH");
        MoonPhase moonPhase = new MoonPhase();


    }

    // method for animate the sun, will called by fragment
    public void animateSun(){
        new updateUI().execute();
    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void displaySun(){
        //Sun display
        View sunView = findViewById(R.id.sunView);
        LinearLayout sunLayout = findViewById(R.id.sunLayout);
        // change sun display after all ui done their works
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Path newPath = new Path();
                ImageView sunView = findViewById(R.id.sunView);

                LinearLayout sunLayout = findViewById(R.id.sunLayout);
                // set the layout size to double of sun image size
                sunLayout.setMinimumHeight(sunView.getMeasuredHeight()*2);

                float height = (float) sunLayout.getHeight();
                float width = (float) sunLayout.getWidth();

                Log.d("TAG2", "run: "+height);
                Log.d("TAG2", "run: "+width);

//                float radius;
//
//                if (width > height) {
//                    radius = height / 2;
//                } else {
//                    radius = width / 2;
//                }

                float center_x, center_y;
                final RectF oval = new RectF();

                center_x = width/2;
                center_y = height/2;

                // draw half oval path
                sunView.setVisibility(View.VISIBLE);
                oval.set(0,
                        center_y,
                        center_x +(sunView.getDrawable().getIntrinsicHeight()/2.0f),
                        height + (sunView.getDrawable().getIntrinsicHeight()));
                newPath.addArc(oval, 180, 180);

                //MoonAPIHandler moonAPIHandler = new MoonAPIHandler(2021,5,10);
                //SunAPIHandler sunAPIHandler = new SunAPIHandler(2021,5,10);


                //SunAPIHandler sunAPIHandler = new SunAPIHandler();
                //float percentage = sunAPIHandler.calSunTimePass();

                float percentage = 10.0f; // initialize to your desired percentage
                int duration = Math.round(3 * percentage/100) * 1000;

                PathMeasure measure = new PathMeasure(newPath, false);
                float length = measure.getLength();
                Path partialPath = new Path();
                measure.getSegment(0.0f, (length * percentage) / 100.0f, partialPath, true);
                partialPath.rLineTo(0.0f, 0.0f); // workaround to display on hardware accelerated canvas as described in docs

                ValueAnimator pathAnimator = ObjectAnimator.ofFloat(sunView,"x","y", partialPath);
                pathAnimator.setDuration(duration);
                pathAnimator.start();
            }
        });
    }

    private class updateUI extends AsyncTask<Void , Void , Void> {

        @Override
        protected void onPreExecute() {
            //執行前 設定可以在這邊設定
        }

        @Override
        protected Void doInBackground(Void... params) {
            //執行中 在背景做事情
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
            return null;

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //執行中 可以在這邊告知使用者進度

        }

        @Override
        protected void onPostExecute(Void result) {
            //執行後 完成背景任務
            displaySun();

        }
    }
}

//this code is for checking version control (push 1)