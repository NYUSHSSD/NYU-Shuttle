package com.shanghai.nyushuttle;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.expandablelayout.library.ExpandableLayoutListView;

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

public class Landing extends Activity implements AdapterView.OnItemSelectedListener {

    public final static String ROUTE_DETAIL = "com.shanghai.nyushuttle.ROUTE_DETAIL";
    public final static String SCHEDULE_DETAIL = "com.shanghai.nyushuttle.SCHEDULE_DETAIL";
    public int current_weekday, current_dayofm, current_month;
    String[] weekDays = {"", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    String[] original_months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    String[] landingText_array = {"", "My favorites for:", "My favorites for:", "My favorites for:", "My favorites for:", "My favorites for:", "My favorites for:", "My favorites for:"};
    String landing_text = "";
    public String day_selected = "";
    public static String host_name = "http://nyushuttle.lixter.com";
    public static String backup_host_name = "http://nyushuttle.lixter.com";
    public int chosen_host_name = 0;
    public SharedPreferences sharedPref;

    public String self_defined_version = "usr1.14";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        sharedPref = getDefaultSharedPreferences(getApplication());

        String newServer = sharedPref.getString("pref_server","http://nyushuttle.lixter.com");
        Log.w("new-server",newServer);
        host_name = newServer;

        URL url = null;
        try {
            url = new URL(host_name + "/shuttle/get_version.php");
        } catch (MalformedURLException e) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("pref_server", backup_host_name);
            editor.commit();
            super.finish();
        }

        if (internetConnected())
            new getVersionFromDB(this).execute(url);
        else
        {
            Toast.makeText(this,"No internet connection :(",Toast.LENGTH_LONG).show();
            super.finish();
        }

        Calendar calendar = Calendar.getInstance();
        current_weekday = calendar.get(Calendar.DAY_OF_WEEK);
        current_dayofm = calendar.get(Calendar.DAY_OF_MONTH);
        current_month = calendar.get(Calendar.MONTH);

        landing_text = "My favorites for: " + weekDays[current_weekday] + ", " + months[current_month] + " " + current_dayofm;
        //TextView landing_tv = (TextView) findViewById(R.id.landing_text);
        //landing_tv.setText(landing_text);

        landingText_array[0]=landing_text;

        Spinner spinner = (Spinner) findViewById(R.id.days_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.weekdays_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(Landing.this);

        weekDays[0]=weekDays[current_weekday];
        //day_selected = weekDays[current_weekday];



    }

    private boolean internetConnected()
    {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());

    }


    // DB Downloading and displaying route info
    private class getDataFromDB extends AsyncTask<URL, String, String> {

        private final ProgressDialog progressDialog;

        private getDataFromDB(Context ctx) {
            progressDialog = CustomLoading.ctor(ctx);
        }


        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog.show();
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




            String words[]=result.split("\":\"|\\\",\"");
            String route[] = new String[100];
            String going_from[] = new String[100];
            String going_to[] = new String[100];
            String departure[] = new String[100];
            String arrival[] = new String[100];
            String days[] = new String[100];


            int d=0;
            for (int i=0;i<words.length;i++)
            {
                if(words[i].contains("route")){
                    route[d] = words[i + 1];
                    days[d] = "";
                }
                else if (words[i].contains("going_from"))
                    going_from[d]=words[i+1];
                else if (words[i].contains("going_to"))
                    going_to[d]=words[i+1];
                else if (words[i].contains("departure"))
                    departure[d]=words[i+1].split(":")[0] + ":" + words[i+1].split(":")[1];
                else if (words[i].contains("arrival"))
                    arrival[d] = words[i + 1].split(":")[0] + ":" + words[i + 1].split(":")[1];
                if (words[i].contains("monday") && (words[i+1].contains("1")))
                    days[d]+="Mon ";
                else if (words[i].contains("tuesday") && (words[i+1].contains("1")))
                    days[d]+="Tue ";
                else if (words[i].contains("wednesday") && (words[i+1].contains("1")))
                    days[d]+="Wed ";
                else if (words[i].contains("thursday") && (words[i+1].contains("1")))
                    days[d]+="Thu ";
                else if (words[i].contains("friday") && (words[i+1].contains("1")))
                    days[d]+="Fri ";
                else if (words[i].contains("saturday") && (words[i+1].contains("1")))
                    days[d]+="Sat ";
                else if (words[i].contains("sunday")){
                    if (words[i+1].contains("1"))
                        days[d] += "Sun ";
                    d++;
                }
               // if (words[i].contains(day_selected.substring(0,2)) && words[i+1].contains("0"))
                 //   d--;

            }

            Spanned[] formatted_going_from = new Spanned[d];
            Spanned[] formatted_going_to = new Spanned[d];
            Spanned[] formatted_route = new Spanned[d];
            Spanned[] formatted_days = new Spanned[d];

            for (int i=0;i<d;i++)
            {
                formatted_route[i] = Html.fromHtml("<strong>" + departure[i] + "</strong> | Route " + route[i] + " | <strong>" + going_from[i] + "</strong> to <strong>" + going_to[i] + "</strong>");
                formatted_going_from[i] = Html.fromHtml("Leaves from <strong>" + going_from[i] + "</strong> at <strong>" + departure[i] + "</strong>");
                formatted_going_to[i] = Html.fromHtml("Arrives at <strong>" + going_to[i] + "</strong> at <strong>" + arrival[i] + "</strong>");
                formatted_days[i] = Html.fromHtml("Days: <strong>" + days[i] + "</strong>");
            }

            final ExpandableLayoutListView expandableLayoutListView = (ExpandableLayoutListView) findViewById(R.id.landing_routelist);

            ArrayList<RouteClass> arrayOfRoutes = new ArrayList<RouteClass>();
            int[] order = new int[d];
            int aux;
            for (int i=0;i<d;i++)
                order[i]=i;
            for (int i=0;i<d;i++)
                for (int j=0;j<d-1;j++)
                    if (departure[order[j]].compareTo(departure[order[j+1]])>0)
                    {
                        aux=order[j];
                        order[j]=order[j+1];
                        order[j+1]=aux;
                    }

            int route_count = 0;
            String favorite_routes_for_today = sharedPref.getString(day_selected.substring(0,3), "");
            Log.w("alex-log",favorite_routes_for_today);
            for (int i=0;i<d;i++)
            {
                //try {
                    if (favorite_routes_for_today.contains(route[order[i]]) && (formatted_days[order[i]].toString()).contains(day_selected.substring(0, 2))) {
                        arrayOfRoutes.add(route_count, new RouteClass(formatted_route[order[i]], formatted_going_from[order[i]], formatted_going_to[order[i]], formatted_days[order[i]]));
                        route_count++;
                    }
              //  }
              //  catch(IndexOutOfBoundsException e) {

             //   }
            }


            RoutesAdapter adapter = new RoutesAdapter(Landing.this, arrayOfRoutes);
            expandableLayoutListView.setAdapter(adapter);
            progressDialog.dismiss();
        }
    }

    // UP TO HERE


    private class SelfUpdate extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... bla) {
            try {
                //set the download URL, a url that points to a file on the internet
                //this is the file to be downloaded
                URL url = new URL(host_name + "/shuttle/update/nyushuttle_update.apk");

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
                File file = new File(SDCardRoot,"nyushuttle_update.apk");

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
            String apkfile = "file:///" + SDCardRoot.getAbsolutePath() + "/nyushuttle_update.apk";
            Intent promptInstall = new Intent(Intent.ACTION_VIEW)
                    .setDataAndType(Uri.parse(apkfile),
                            "application/vnd.android.package-archive");
            startActivity(promptInstall);
        }
    }



    private class getVersionFromDB extends AsyncTask<URL, String, String> {

        private final ProgressDialog progressDialog;

        private getVersionFromDB(Context ctx) {
            progressDialog = CustomLoading.ctor(ctx);
        }


        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog.show();
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
            if (result==null)
            {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Landing.this);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("pref_server", backup_host_name);
                editor.commit();
                Landing.this.finish();
                Log.w("diee","here I die");
            }
            else
            if (result.contains(self_defined_version)) {
                Toast.makeText(Landing.this, "Up to date!", Toast.LENGTH_LONG).show();
            }
            else {
                Log.w("outdated",result);
                Toast.makeText(Landing.this, "Application outdated. Downloading current version...", Toast.LENGTH_LONG).show();
                new SelfUpdate().execute("up");
            }
            progressDialog.dismiss();
        }
    }





    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        day_selected = weekDays[pos];

        TextView landing_tv = (TextView) findViewById(R.id.landing_text);
        landing_tv.setText(landingText_array[pos]);

        URL url = null;
        try {
            url = new URL(host_name + "/shuttle/select_all.php");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (internetConnected())
            new getDataFromDB(this).execute(url);
        else
            Toast.makeText(this,"No internet connection :(",Toast.LENGTH_SHORT).show();

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


    public void setTitle(CharSequence title) {
        getActionBar().setTitle(title);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.landing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_routelist)
        {
            Intent intent = new Intent(this, RouteListActivity.class);
            //intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }

        else if (id == R.id.action_onedayview)
        {
            Intent intent = new Intent(this, OneDayViewActivity.class);
            //intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }

        else if (id == R.id.action_settings)
        {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void launchDetailPage(View view)
    {
        Intent intent = new Intent(this, DetailsAndMapActivity.class);
        intent.putExtra(ROUTE_DETAIL,"" + ((Button) view).getHint() );
        startActivity(intent);
    }

    public void launchAlarmPage(View view)
    {
        Intent intent = new Intent(this, AddToScheduleActivity.class);
        intent.putExtra(SCHEDULE_DETAIL,"" + ((Button) view).getHint() );
        startActivity(intent);
    }



}
