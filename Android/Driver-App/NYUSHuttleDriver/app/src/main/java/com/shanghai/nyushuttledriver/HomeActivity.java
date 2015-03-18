package com.shanghai.nyushuttledriver;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import javax.xml.datatype.Duration;


public class HomeActivity extends Activity implements LocationListener,AdapterView.OnItemSelectedListener {
    private TextView latituteField;
    private TextView longitudeField;
    private String provider;
    private LocationManager locationManager;
    public String selected_route = "none";
    public int route_started = 0;
    public String self_defined_version = "drv1.13";
    public static String host_name = "http://nyushuttle.lixter.com";
    public String AndroidDeviceId ="noID";
    PowerManager powerManager;
    PowerManager.WakeLock wakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        URL url = null;
        try {
            url = new URL(host_name + "/shuttle/get_version.php");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (internetConnected())
            new getVersionFromDB(this).execute(url);
        else
        {
            Toast.makeText(this,"No internet connection :(",Toast.LENGTH_LONG).show();
            super.finish();
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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

        latituteField = (TextView) findViewById(R.id.TextView02);
        longitudeField = (TextView) findViewById(R.id.TextView04);


        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            latituteField.setText("Location not available");
            longitudeField.setText("Location not available");
        }
    }
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    public void startRoute(View view) {
        if (!selected_route.contains("none"))
        {
            powerManager = (PowerManager) getSystemService(POWER_SERVICE);
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    "alexWakelock");
            wakeLock.acquire();
            new UpdateLogs(this).execute("started", selected_route, AndroidDeviceId, "01");
            route_started = 1;
            TextView someView = (TextView) findViewById(R.id.TextView06);
            someView.setText(R.string.started);
        }
        else
        {
            Toast.makeText(this,R.string.please_select_route,Toast.LENGTH_LONG).show();
        }
    }

    public void finishRoute(View view) {
        new UpdateLogs(this).execute("stopped", selected_route, AndroidDeviceId, "01");
        route_started = 0;
        TextView someView = (TextView) findViewById(R.id.TextView06);
        someView.setText(R.string.stopped);
        if (wakeLock.isHeld())
            wakeLock.release();
    }


    private class SelfUpdate extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... bla) {
            try {
                //set the download URL, a url that points to a file on the internet
                //this is the file to be downloaded
                URL url = new URL(host_name + "/shuttle/update/nyushuttledriver_update.apk");

                //create the new connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //set up some things on the connection
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);

                //and connect!
                urlConnection.connect();

                //set the path where we want to save the file
                //in this case, going to save it on the root directory of the
                //sd card.
                File SDCardRoot = Environment.getExternalStorageDirectory();
                //create a new file, specifying the path, and the filename
                //which we want to save the file as.
                File file = new File(SDCardRoot,"nyushuttledriver_update.apk");

                //this will be used to write the downloaded data into the file we created
                FileOutputStream fileOutput = new FileOutputStream(file);

                //this will be used in reading the data from the internet
                InputStream inputStream = urlConnection.getInputStream();



                //create a buffer...
                byte[] buffer = new byte[16384];
                int bufferLength = 0; //used to store a temporary size of the buffer

                //now, read through the input buffer and write the contents to the file
                while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                    //add the data in the buffer to the file in the file output stream (the file on the sd card
                    fileOutput.write(buffer, 0, bufferLength);
                    //add up the size so we know how much is downloaded
                }
                //close the output stream when done
                fileOutput.close();

//catch some possible errors...
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "success";
        }

        protected void onPostExecute(String result) {
            File SDCardRoot = Environment.getExternalStorageDirectory();
            String apkfile = "file:///" + SDCardRoot.getAbsolutePath() + "/nyushuttledriver_update.apk";
            Intent promptInstall = new Intent(Intent.ACTION_VIEW)
                    .setDataAndType(Uri.parse(apkfile),
                            "application/vnd.android.package-archive");
            startActivity(promptInstall);
        }
    }



    private class getVersionFromDB extends AsyncTask<URL, String, String> {



        private getVersionFromDB(Context ctx) {

        }


        @Override
        protected void onPreExecute(){
            super.onPreExecute();

        }




        protected String doInBackground(URL... urls) {

            // Here starts the connection to the database
            ///////////////////////////////////////////////////////////////////////////////////////
            JSONArray jArray = null;
            String result = null;
            StringBuilder sb = null;
            InputStream is = null;
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            try{
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(urls[0].toString());
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
            }catch(Exception e){
                //error_string +=e.toString();
            }
            //convert response to string
            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                sb = new StringBuilder();
                sb.append(reader.readLine() + "\n");

                String line="0";
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result=sb.toString();
            }catch(Exception e){
                //error_string +=e.toString();
            }

            return result;

        }
        protected void onProgressUpdate(Integer... progress) {
            //Yet to code
        }
        protected void onPostExecute(String result) {
            if (result.contains(self_defined_version))
            {
                Toast.makeText(HomeActivity.this,R.string.up_to_date,Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(HomeActivity.this, R.string.application_outdated, Toast.LENGTH_LONG).show();
                new SelfUpdate().execute("up");
            }

        }
    }


    private boolean internetConnected()
    {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());

    }

    @Override
    public void onLocationChanged(Location location) {
        double lat = (double) (location.getLatitude());
        double lng = (double) (location.getLongitude());
        latituteField.setText(String.valueOf(lat));
        longitudeField.setText(String.valueOf(lng));

        if (!selected_route.contains("none") && route_started == 1) {
            new UpdateLocation(this).execute(selected_route, String.valueOf(lat), String.valueOf(lng));
            Log.w("alex-log","Executing |" + selected_route + "|");
        }
    }




    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
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
            //case R.id.action_settings:
                //Intent intent = new Intent(this, SettingsActivity.class);
                //startActivity(intent);
                //return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {
                selected_route = "none";
                Log.w("alex-log", "Selected none");
            } else if (position == 1) {
                selected_route = "A";
                Log.w("alex-log", "Selected A");
            } else if (position == 2) {
                selected_route = "B";
                Log.w("alex-log", "Selected B");
            } else if (position == 3) {
                selected_route = "C";
                Log.w("alex-log", "Selected C");
            }
        //TODO: What if the driver changes the route while driving?
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
