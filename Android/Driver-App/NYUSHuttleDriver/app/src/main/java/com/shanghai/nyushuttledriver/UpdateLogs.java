package com.shanghai.nyushuttledriver;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;


public class UpdateLogs extends AsyncTask<String,Void,String>{
    private Context ctx;
    public SharedPreferences sharedPref;
    public String host_name,api_dir,add_log_script;
    public UpdateLogs(Context context) {
        this.ctx = context;
    }

    protected void onPreExecute(){

    }
    @Override
    protected String doInBackground(String... arg0) {
            try {
                String status = (String) arg0[0];
                String route = (String) arg0[1];
                String driver = (String) arg0[2];
                String bus = (String) arg0[3];

                sharedPref = getDefaultSharedPreferences(((Activity)ctx).getApplication());
                host_name = sharedPref.getString("host_name",Config.bk_host_name);
                api_dir = sharedPref.getString("api_dir",Config.bk_api_dir);
                add_log_script = sharedPref.getString("add_log_script",Config.bk_add_log_script);

                String link = host_name + api_dir + add_log_script + "?status="
                        +status+"&route="+route+"&driver="+driver+"&bus="+bus;
                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader
                        (new InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line="";
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();
                return sb.toString();
            }catch(Exception e){
                return ("Exception: " + e.getMessage());
            }
        }
    @Override
    protected void onPostExecute(String result){
        Log.w("alex-log",result);
    }
}