package com.shanghai.nyushuttledriver;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
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

import static android.preference.PreferenceManager.getDefaultSharedPreferences;


public class SplashActivity extends Activity {

    public String host_name,api_dir,apk_dir,get_version_script,update_file_name;

    SharedPreferences sharedPref;
    String appVersionName = "0.00";
    int appVersionCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        appVersionName = pInfo.versionName;
        appVersionCode = pInfo.versionCode;

        sharedPref = getDefaultSharedPreferences(getApplication());
        host_name = sharedPref.getString("host_name",Config.bk_host_name);
        api_dir = sharedPref.getString("api_dir",Config.bk_api_dir);
        apk_dir = sharedPref.getString("apk_dir",Config.bk_apk_dir);
        get_version_script = sharedPref.getString("get_version_script",Config.bk_get_version_script);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("app_version_name", appVersionName);
        editor.commit();

        URL url = null;
        try {
            url = new URL(host_name + api_dir + get_version_script);
        } catch (MalformedURLException e) {
            Config.restoreDefaults(this);
        }

        if (Config.internetConnected(this))
            new getVersionFromDB(this).execute(url);
        else
        {
            Toast.makeText(this, "No internet connection :(", Toast.LENGTH_LONG).show();
            super.finish();
        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private class getVersionFromDB extends AsyncTask<URL, String, String> {


        private getVersionFromDB(Context ctx) {

        }


        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            //progressDialog.show();
        }




        protected String doInBackground(URL... urls) {

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
                Log.e("alex-error", e + "");
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
                Log.e("alex-error",e + "");
            }

            return result;

        }
        protected void onProgressUpdate(Integer... progress) {
            //Yet to code
        }
        protected void onPostExecute(String result) {
            if (result==null)
            {
                Toast.makeText(SplashActivity.this,"Could not contact server. Please try again.",Toast.LENGTH_LONG).show();
                Config.restoreDefaults(SplashActivity.this);
                SplashActivity.this.finish();
            }
            else
            {
                Log.w("alex-log-version",result + "");
                String words[]=result.split("\":\"|\\\",\"");
                int latest_version = 0;
                for (int i=0;i<words.length;i++)
                {
                    if (words[i].contains("driver_ver"))
                        latest_version = Integer.parseInt(words[i+1]);
                }
                update_file_name = Config.bk_update_file_name + latest_version + ".apk";
                Log.w("alex-log-version",appVersionName + " " + appVersionCode + " " + update_file_name);
                //progressDialog.dismiss();

                if (latest_version <= appVersionCode) {
                    Toast.makeText(SplashActivity.this, "Up to date!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(SplashActivity.this, "Application outdated. Downloading current version...", Toast.LENGTH_LONG).show();
                    new SelfUpdate().execute("up");
                }
            }

        }
    }



    private class SelfUpdate extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... bla) {
            try {
                //set the download URL, a url that points to a file on the internet
                //this is the file to be downloaded
                URL url = new URL(host_name + apk_dir + update_file_name);

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
                File file = new File(SDCardRoot, update_file_name);

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
            String apkfile = "file:///" + SDCardRoot.getAbsolutePath() + "/" + update_file_name;
            Intent promptInstall = new Intent(Intent.ACTION_VIEW)
                    .setDataAndType(Uri.parse(apkfile),
                            "application/vnd.android.package-archive");
            startActivity(promptInstall);
        }
    }

}
