package com.shanghai.nyushuttle;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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


public class RouteListActivity extends Activity {

    public final static String ROUTE_DETAIL = "com.shanghai.nyushuttle.ROUTE_DETAIL";
    public final static String SCHEDULE_DETAIL = "com.shanghai.nyushuttle.SCHEDULE_DETAIL";
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routelist);

         URL url = null;
         try {
             url = new URL(Landing.host_name + "/shuttle/select_all.php");
         } catch (MalformedURLException e) {
             e.printStackTrace();
         }
         new getDataFromDB(this).execute(url);


    }



    public void setTitle(CharSequence title) {
        getActionBar().setTitle(title);
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.routelist, menu);
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
            }

            Spanned[] formatted_going_from = new Spanned[d];
            Spanned[] formatted_going_to = new Spanned[d];
            Spanned[] formatted_route = new Spanned[d];
            Spanned[] formatted_days = new Spanned[d];
            for (int i=0;i<d;i++)
            {
                formatted_route[i] = Html.fromHtml("<strong><big>Route " + route[i] + "</strong></big>: <strong>" + going_from[i] + "</strong> to <strong>" + going_to[i] + "</strong> (" + departure[i] + ")");
                formatted_going_from[i] = Html.fromHtml("Leaves from <strong>" + going_from[i] + "</strong> at <strong>" + departure[i] + "</strong>");
                formatted_going_to[i] = Html.fromHtml("Arrives at <strong>" + going_to[i] + "</strong> at <strong>" + arrival[i] + "</strong>");
                formatted_days[i] = Html.fromHtml("Days: <strong>" + days[i] + "</strong>");
            }

            final ExpandableLayoutListView expandableLayoutListView = (ExpandableLayoutListView) findViewById(R.id.listview);

            ArrayList<RouteClass> arrayOfRoutes = new ArrayList<RouteClass>();

            for (int i=0;i<d;i++)
            {
                arrayOfRoutes.add(i, new RouteClass(formatted_route[i], formatted_going_from[i], formatted_going_to[i],formatted_days[i]));
            }

            RoutesAdapter adapter = new RoutesAdapter(RouteListActivity.this, arrayOfRoutes);
            expandableLayoutListView.setAdapter(adapter);

            progressDialog.hide();
        }
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
