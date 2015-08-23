package com.shanghai.nyushuttle;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;


public class OneDayViewActivity extends Activity implements AdapterView.OnItemSelectedListener{

    public final static String ROUTE_DETAIL = "com.shanghai.nyushuttle.ROUTE_DETAIL";
    public final static String SCHEDULE_DETAIL = "com.shanghai.nyushuttle.SCHEDULE_DETAIL";
    public int current_weekday, current_dayofm, current_month;
    String[] weekDays = {"", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    String[] original_months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    String[] odvText_array = {"Schedule for:", "Schedule for:", "Schedule for:", "Schedule for:", "Schedule for:", "Schedule for:", "Schedule for:", "Schedule for:"};
    //String landing_text = "";
    public String day_selected = "";
    public SharedPreferences sharedPref;
    public String host_name,api_dir,apk_dir,get_version_script,get_all_routes_script,update_file_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_day_view);

        sharedPref = getDefaultSharedPreferences(getApplication());
        host_name = sharedPref.getString("host_name",Config.bk_host_name);
        api_dir = sharedPref.getString("api_dir",Config.bk_api_dir);
        get_all_routes_script = sharedPref.getString("get_all_routes_script",Config.bk_get_all_routes_script);

        Calendar calendar = Calendar.getInstance();
        current_weekday = calendar.get(Calendar.DAY_OF_WEEK);
        current_dayofm = calendar.get(Calendar.DAY_OF_MONTH);
        current_month = calendar.get(Calendar.MONTH);

        Spinner odv_spinner = (Spinner) findViewById(R.id.odv_days_spinner);

        ArrayAdapter<CharSequence> odv_adapter = ArrayAdapter.createFromResource(this,
                R.array.weekdays_array, android.R.layout.simple_spinner_item);
        odv_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        odv_spinner.setAdapter(odv_adapter);
        odv_spinner.setOnItemSelectedListener(this);
        weekDays[0]=weekDays[current_weekday];
        day_selected=weekDays[0];

/*
        URL url = null;
        try {
            url = new URL("http://nyushapp.comli.com/shuttle/select_all.php");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        new getDataFromDB(this).execute(url);
*/

    }



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

            final ExpandableLayoutListView expandableLayoutListView = (ExpandableLayoutListView) findViewById(R.id.onedayview_routelist);

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

            for (int i=0;i<d;i++)
            {
                //try {
                if ((formatted_days[order[i]].toString()).contains(day_selected.substring(0, 2))) {
                    arrayOfRoutes.add(route_count, new RouteClass(formatted_route[order[i]], formatted_going_from[order[i]], formatted_going_to[order[i]], formatted_days[order[i]]));
                    route_count++;
                }
                //  }
                //  catch(IndexOutOfBoundsException e) {

                //   }
            }


            RoutesAdapter adapter = new RoutesAdapter(OneDayViewActivity.this, arrayOfRoutes);
            expandableLayoutListView.setAdapter(adapter);
            progressDialog.dismiss();
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        day_selected = weekDays[pos];

        TextView odv_tv = (TextView) findViewById(R.id.odv_text);
        odv_tv.setText(odvText_array[pos]);

        URL url = null;
        try {
            url = new URL(host_name + api_dir + get_all_routes_script);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        new getDataFromDB(this).execute(url);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.one_day_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

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
