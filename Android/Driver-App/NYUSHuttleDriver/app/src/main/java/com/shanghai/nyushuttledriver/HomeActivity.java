package com.shanghai.nyushuttledriver;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;


public class HomeActivity extends Activity implements AdapterView.OnItemSelectedListener {

    public String[] list_of_routes = {"none","A","BC"};
    public SharedPreferences sharedPref;
    public String host_name,api_dir;
    private LocationManager locationManager;
    public String selected_route = "none";
    public int route_started = 0;
    public String AndroidDeviceId ="noID";
    LinearLayout ll3;
    Spinner spin1;
    TextView tv06;

    AlarmManager alarmMgr;
    PendingIntent alarmIntent;
    Intent otherIntent;
    int important_unique_id = 0;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        sharedPref = getDefaultSharedPreferences(getApplication());
        host_name = sharedPref.getString("host_name",Config.bk_host_name);
        api_dir = sharedPref.getString("api_dir",Config.bk_api_dir);

        int route_already_started = sharedPref.getInt("route_started",0);
   //     int route_already_chosen = sharedPref.getInt("route_already_chosen",0);
    //    Log.w("alex-log-route-chosen",route_already_chosen + "");
        if (route_already_started == 1)
        {
            route_started = 1;
            ll3 = (LinearLayout) findViewById(R.id.linearLayout3);
            tv06 = (TextView) findViewById(R.id.TextView06);
            spin1 = (Spinner) findViewById(R.id.spinner);
            ll3.setBackgroundColor(Color.parseColor("#ff57068c"));
            spin1.setBackgroundColor(Color.parseColor("#ff57068c"));
            spin1.setEnabled(false);
       //     spin1.setSelection(route_already_chosen);
       //     selected_route = list_of_routes[route_already_chosen];
            tv06.setText(R.string.started);
            Toast.makeText(this,R.string.started,Toast.LENGTH_LONG).show();
        }


        final TelephonyManager mTelephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (mTelephony.getDeviceId() != null){
            AndroidDeviceId = mTelephony.getDeviceId(); //*** use for mobiles
        }
        else{
            AndroidDeviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID); //*** use for tablets
        }

        Log.w("alex-log",AndroidDeviceId);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.routes_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!enabled) {
            Toast.makeText(HomeActivity.this,R.string.please_enable_location,Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        ll3 = (LinearLayout) findViewById(R.id.linearLayout3);
        tv06 = (TextView) findViewById(R.id.TextView06);
        spin1 = (Spinner) findViewById(R.id.spinner);
    }
    protected void onResume() {
        super.onResume();
    }
    protected void onPause() {
        super.onPause();
    }

    public void startRoute(View view) {
        if (!selected_route.contains("none") && route_started == 0)
        {
            new UpdateLogs(this).execute("started", selected_route, AndroidDeviceId, "01");
            route_started = 1;
            ll3.setBackgroundColor(Color.parseColor("#ff57068c"));
            spin1.setBackgroundColor(Color.parseColor("#ff57068c"));
            spin1.setEnabled(false);
            tv06.setText(R.string.started);
            Toast.makeText(this,R.string.started,Toast.LENGTH_LONG).show();

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("route_started", 1);
            editor.putInt("route_already_chosen",spin1.getSelectedItemPosition());
            editor.commit();
Log.w("alex-log","a1");
            alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
            otherIntent = new Intent(this.getApplicationContext(), AlarmReceiver.class);
            otherIntent.putExtra("route",selected_route);
            alarmIntent = PendingIntent.getBroadcast(this.getApplicationContext(), important_unique_id, otherIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    5 * 1000, alarmIntent);
            Log.w("alex-log","a2");
        }
        else
        {
            Toast.makeText(this,R.string.please_select_route,Toast.LENGTH_LONG).show();
        }
    }

    public void finishRoute(View view) {
        if (route_started == 1)
        {
            new UpdateLogs(this).execute("stopped", selected_route, AndroidDeviceId, "01");
            route_started = 0;
            ll3.setBackgroundColor(Color.parseColor("#d11255"));
            spin1.setBackgroundColor(Color.parseColor("#d11255"));
            tv06.setText(R.string.stopped);
            spin1.setEnabled(true);
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("route_started", 0);
            editor.putInt("route_already_chosen",0);
            editor.commit();

        }
        try{
            alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
            otherIntent = new Intent(this.getApplicationContext(), AlarmReceiver.class);
            otherIntent.putExtra("route",selected_route);
            alarmIntent = PendingIntent.getBroadcast(this.getApplicationContext(), important_unique_id, otherIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
   //         alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
     //               5 * 1000 * 100000, alarmIntent);
            alarmMgr.cancel(alarmIntent);}
        catch(Exception e)
        {

        }
        Toast.makeText(this,R.string.stopped,Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int old_pos = sharedPref.getInt("route_already_chosen",0);
        spin1 = (Spinner) findViewById(R.id.spinner);
        if (old_pos!=0)
        {
            selected_route = list_of_routes[old_pos];
            spin1.setSelection(old_pos);
        }
        else {
            selected_route = list_of_routes[position];
        }

        //TODO: What if the driver changes the route while driving?
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
