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
import android.util.FloatMath;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.andexert.expandablelayout.library.ExpandableLayoutListView;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.utils.CoordinateConverter;

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
import java.util.List;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;


public class DetailsAndMapActivity extends Activity {

    // åœ°å›¾ç›¸å…³ Map Stuff
    MapView mMapView;
    BaiduMap mBaiduMap;
    // UIç›¸å…³ UI Stuff
    Button resetBtn;
    Button clearBtn;
    private Marker mMarkerA;
    private Marker mMarkerB;
    private Marker mMarkerC;
    private Marker mMarkerD;
    private Polyline mPolyA;
    BitmapDescriptor bdMapMarker;
    BitmapDescriptor bdBus;
    BitmapDescriptor bdStudent;

    public String route_name = "none";
    public LatLng llBus = new LatLng(0,0);
    public URL url = null;

    public OverlayOptions ooC;

    public getBusLocationFromDB getLocTask= new getBusLocationFromDB(DetailsAndMapActivity.this);

    SharedPreferences sharedPref;
    String host_name,api_dir,get_bus_location_script;

    public double transformLon (double x, double y)
    {
        double ret;
        ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * 3.141592) + 20.0 * Math.sin(2.0 * x * 3.141592)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * 3.141592) + 40.0 * Math.sin(x / 3.0 * 3.141592)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * 3.141592) + 300.0 * Math.sin(x / 30.0 * 3.141592)) * 2.0 / 3.0;
        return ret;
    }

    public double transformLat (double x, double y)
    {
        double ret;
        ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * 3.141592) + 20.0 * Math.sin(2.0 * x * 3.141592)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * 3.141592) + 40.0 * Math.sin(y / 3.0 * 3.141592)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * 3.141592) + 320 * Math.sin(y * 3.141592 / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    public LatLng convertCoords(double wgLat, double wgLon)
    {
        double a = 6378245.0;
        double ee = 0.00669342162296594323;
        double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
        double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
        double radLat = wgLat / 180.0 * 3.141592;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * 3.141592);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * 3.141592);
        double mgLat = wgLat + dLat;
        double mgLon = wgLon + dLon;
        return new LatLng(mgLat, mgLon);
    }
    public LatLng convertCoords2(double wgLat, double wgLon)
    {
        return new LatLng(wgLat+0.00379, wgLon+0.01038);
    }

    public LatLng convertCoords3(double wgLat, double wgLon)
    {
        LatLng x = new LatLng(wgLat,wgLon);
        CoordinateConverter convertor = new CoordinateConverter();
        convertor.from(CoordinateConverter.CoordType.GPS);
        convertor.coord(x);
        LatLng y = convertor.convert();
        return y;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        SDKInitializer.initialize(getApplication());

        bdMapMarker = BitmapDescriptorFactory
                .fromResource(R.drawable.map_marker);
        bdBus = BitmapDescriptorFactory
                .fromResource(R.drawable.ic_logo_bus_2);
        bdStudent = BitmapDescriptorFactory
                .fromResource(R.drawable.flower_logo);

        sharedPref = getDefaultSharedPreferences(getApplication());
        host_name = sharedPref.getString("host_name",Config.bk_host_name);
        api_dir = sharedPref.getString("api_dir",Config.bk_api_dir);
        get_bus_location_script = sharedPref.getString("get_bus_location_script",Config.bk_get_bus_location_script);


        try {
            url = new URL(host_name + api_dir + get_bus_location_script);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_and_map_activity);

        Intent intent = getIntent();
        String route_details = intent.getStringExtra("com.shanghai.nyushuttle.ROUTE_DETAIL");
        Log.w("alex-logg",route_details);
        TextView route_detail_tv = (TextView) findViewById(R.id.route_detail_tv);
        route_detail_tv.setText(route_details);
        if (route_details.contains("A-"))
            route_name = "A";
        else if (route_details.contains("B-"))
            route_name = "B";
        else if (route_details.contains("C-"))
            route_name = "C"; //ATTENTION! This is temporary, since there is only 1 bus for B and C

        // åˆå§‹åŒ–åœ°å›¾ Initialise Map
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();

        initOverlay();

        getLocTask.execute(url);


    }



    public void initOverlay() {
        List<LatLng> my_points = new ArrayList<LatLng>();
        LatLng southwest = new LatLng(0,0);
        LatLng northeast = new LatLng(0,0);

        if (route_name == "A") {
            LatLng ll0 = new LatLng(31.232451, 121.541163);
            LatLng ll1 = new LatLng(31.231479, 121.542902);
            LatLng ll2 = new LatLng(31.23156, 121.543027);
            LatLng ll3 = new LatLng(31.231911, 121.544007);
            LatLng ll4 = new LatLng(31.231973, 121.544105);
            LatLng ll5 = new LatLng(31.235025, 121.542646);
            LatLng ll6 = new LatLng(31.235203, 121.542996);
            LatLng ll7 = new LatLng(31.236739, 121.545489);

            southwest = ll0;
            northeast = ll7;
            llBus = ll0;

            my_points.add(ll0);
            my_points.add(ll1);
            my_points.add(ll2);
            my_points.add(ll3);
            my_points.add(ll4);
            my_points.add(ll5);
            my_points.add(ll6);
            my_points.add(ll7);

            OverlayOptions ooA = new MarkerOptions().position(ll0).icon(bdMapMarker);
            mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
            OverlayOptions ooB = new MarkerOptions().position(ll7).icon(bdMapMarker);
            mMarkerB = (Marker) (mBaiduMap.addOverlay(ooB));
            ooC = new MarkerOptions().position(llBus).icon(bdBus);
            mMarkerC = (Marker) (mBaiduMap.addOverlay(ooC));


        }

        else if (route_name == "B") {
            LatLng ll0 = new LatLng(31.232794,121.54058);
            LatLng ll1 = new LatLng(31.233115,121.539974);
            LatLng ll2 = new LatLng(31.232532,121.539516);
            LatLng ll3 = new LatLng(31.231775,121.538936);
            LatLng ll4 = new LatLng(31.230116,121.541887);
            LatLng ll5 = new LatLng(31.229988,121.541784);
            LatLng ll6 = new LatLng(31.228437,121.544537);
            LatLng ll7 = new LatLng(31.227545,121.543792);
            LatLng ll8 = new LatLng(31.227545,121.543792);
            LatLng ll9 = new LatLng(31.226846,121.543244);
            LatLng ll10 = new LatLng(31.225542,121.54275);
            LatLng ll11 = new LatLng(31.224388,121.542354);
            LatLng ll12 = new LatLng(31.221948,121.541371);
            LatLng ll13 = new LatLng(31.220632,121.540872);
            LatLng ll14 = new LatLng(31.218925,121.540221);
            LatLng ll15 = new LatLng(31.217123,121.539507);
            LatLng ll16 = new LatLng(31.216235,121.539116);
            LatLng ll17 = new LatLng(31.216,121.539062);
            LatLng ll18 = new LatLng(31.215783,121.538815);
            LatLng ll19 = new LatLng(31.215165,121.538554);
            LatLng ll20 = new LatLng(31.214594,121.538321);

            southwest = ll0;
            northeast = ll20;
            llBus = ll0;

            my_points.add(ll0);
            my_points.add(ll1);
            my_points.add(ll2);
            my_points.add(ll3);
            my_points.add(ll4);
            my_points.add(ll5);
            my_points.add(ll6);
            my_points.add(ll7);
            my_points.add(ll8);
            my_points.add(ll9);
            my_points.add(ll10);
            my_points.add(ll11);
            my_points.add(ll12);
            my_points.add(ll13);
            my_points.add(ll14);
            my_points.add(ll15);
            my_points.add(ll16);
            my_points.add(ll17);
            my_points.add(ll18);
            my_points.add(ll19);
            my_points.add(ll20);

            OverlayOptions ooA = new MarkerOptions().position(ll0).icon(bdMapMarker).title("Grand Pujian");
            mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
            
            OverlayOptions ooB = new MarkerOptions().position(ll20).icon(bdMapMarker);
            mMarkerB = (Marker) (mBaiduMap.addOverlay(ooB));
            ooC = new MarkerOptions().position(llBus).icon(bdBus);
            mMarkerC = (Marker) (mBaiduMap.addOverlay(ooC));


        }

        else if (route_name == "C") {
            LatLng ll0 = new LatLng(31.213868,121.536973);
            LatLng ll1 = new LatLng(31.213969,121.532437);
            LatLng ll2 = new LatLng(31.214424,121.532671);
            LatLng ll3 = new LatLng(31.216779,121.533955);
            LatLng ll4 = new LatLng(31.218501,121.53488);
            LatLng ll5 = new LatLng(31.219149,121.535159);
            LatLng ll6 = new LatLng(31.220578,121.535689);
            LatLng ll7 = new LatLng(31.222477,121.535985);
            LatLng ll8 = new LatLng(31.22504,121.535824);
            LatLng ll9 = new LatLng(31.2267,121.535312);
            LatLng ll10 = new LatLng(31.227379,121.537099);
            LatLng ll11 = new LatLng(31.228506,121.540198);
            LatLng ll12 = new LatLng(31.228838,121.540701);
            LatLng ll13 = new LatLng(31.229475,121.541375);
            LatLng ll14 = new LatLng(31.230131,121.541878);
            LatLng ll15 = new LatLng(31.231787,121.538945);
            LatLng ll16 = new LatLng(31.233111,121.539974);
            LatLng ll17 = new LatLng(31.232802,121.540558);

            southwest = ll0;
            northeast = ll17;
            llBus = ll0;

            my_points.add(ll0);
            my_points.add(ll1);
            my_points.add(ll2);
            my_points.add(ll3);
            my_points.add(ll4);
            my_points.add(ll5);
            my_points.add(ll6);
            my_points.add(ll7);
            my_points.add(ll8);
            my_points.add(ll9);
            my_points.add(ll10);
            my_points.add(ll11);
            my_points.add(ll12);
            my_points.add(ll13);
            my_points.add(ll14);
            my_points.add(ll15);
            my_points.add(ll16);
            my_points.add(ll17);

            OverlayOptions ooA = new MarkerOptions().position(ll0).icon(bdMapMarker);
            mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
            OverlayOptions ooB = new MarkerOptions().position(ll17).icon(bdMapMarker);
            mMarkerB = (Marker) (mBaiduMap.addOverlay(ooB));
            ooC = new MarkerOptions().position(llBus).icon(bdBus);
            mMarkerC = (Marker) (mBaiduMap.addOverlay(ooC));


        }

        OverlayOptions ooPolyline = new PolylineOptions().width(7)
                .color(0xAAFF0000).points(my_points);
        mPolyA = (Polyline) mBaiduMap.addOverlay(ooPolyline);

        // add ground overlay

        LatLngBounds bounds = new LatLngBounds.Builder().include(northeast)
                .include(southwest).build();

 //      OverlayOptions ooGround = new GroundOverlayOptions()
   //             .positionFromBounds(bounds).image(bdGround).transparency(0.8f);
     //   mBaiduMap.addOverlay(ooGround);

        MapStatusUpdate u = MapStatusUpdateFactory
                .newLatLng(bounds.getCenter());
        MapStatusUpdate v = MapStatusUpdateFactory.zoomBy(4);
        mBaiduMap.setMapStatus(u);
        mBaiduMap.setMapStatus(v);


    }


    public void clearOverlay(View view) {
        mBaiduMap.clear();
    }


    public void resetOverlay(View view) {
        clearOverlay(null);
        initOverlay();
    }



    private class getBusLocationFromDB extends AsyncTask<URL, String, String> {

      //  private final ProgressDialog progressDialog;

        private getBusLocationFromDB(Context ctx) {
          //  progressDialog = CustomLoading.ctor(ctx);
        }


        @Override
        protected void onPreExecute(){
            super.onPreExecute();
          //  progressDialog.show();
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
            for (int i=0;i<words.length;i++)
            {
                if (words[i].contains("route") && words[i+1].contains(route_name))
                {
                    float bus_lat = Float.parseFloat(words[i+3]);
                    float bus_long = Float.parseFloat(words[i+7]);
                    llBus = convertCoords3(bus_lat,bus_long); // Here we do the conversion. Experimental!
                    Log.w("transform initial", bus_lat + " " + bus_long);
                    Log.w("transform final", llBus.toString());
                   // ooC = new MarkerOptions().position(llBus).icon(bdBus);
                    mMarkerC.setPosition(llBus);
                  //  mMarkerC = (Marker) (mBaiduMap.addOverlay(ooC));
                    Log.w("alex-log",bus_lat + " " + bus_long);
                    break;
                }
            }
            getLocTask= new getBusLocationFromDB(DetailsAndMapActivity.this);
            getLocTask.execute(url);
          //  progressDialog.dismiss();
        }
    }





    @Override
    protected void onPause() {
        // MapViewçš„ç”Ÿå‘½å‘¨æœŸä¸ŽActivityåŒæ­¥ï¼Œå½“activityæŒ‚èµ·æ—¶éœ€è°ƒç”¨MapView.onPause()
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        // MapViewçš„ç”Ÿå‘½å‘¨æœŸä¸ŽActivityåŒæ­¥ï¼Œå½“activityæ¢å¤æ—¶éœ€è°ƒç”¨MapView.onResume()
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // MapViewçš„ç”Ÿå‘½å‘¨æœŸä¸ŽActivityåŒæ­¥ï¼Œå½“activityé”€æ¯æ—¶éœ€è°ƒç”¨MapView.destroy()
        mMapView.onDestroy();
        super.onDestroy();
        // å›žæ”¶ bitmap èµ„æº
        bdMapMarker.recycle();
        bdBus.recycle();
        bdStudent.recycle();

    }

    @Override
    public void onBackPressed() {
        getLocTask.cancel(true);
        super.onBackPressed();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus) {
            getLocTask.cancel(true);
        }
    }


}
