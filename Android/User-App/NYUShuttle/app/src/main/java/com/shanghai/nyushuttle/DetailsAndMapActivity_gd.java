package com.shanghai.nyushuttle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;


public class DetailsAndMapActivity_gd extends Activity {

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

    BitmapDescriptor bdA;
    BitmapDescriptor bdB;
    BitmapDescriptor bdC;
    BitmapDescriptor bdD;
    BitmapDescriptor bd;
    BitmapDescriptor bdGround;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        SDKInitializer.initialize(getApplication());

        bdA = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_marka);
        bdB = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_markb);
        bdC = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_markc);
        bdD = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_markd);
        bd = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_gcoding);
        bdGround = BitmapDescriptorFactory
                .fromResource(R.drawable.ground_overlay);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_and_map_activity);

        Intent intent = getIntent();
        String route_details = intent.getStringExtra("com.shanghai.nyushuttle.ROUTE_DETAIL");
        TextView route_detail_tv = (TextView) findViewById(R.id.route_detail_tv);
        route_detail_tv.setText(route_details);

        // åˆå§‹åŒ–åœ°å›¾ Initialise Map
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();

        initOverlay();
    }


    public void initOverlay() {
        // add marker overlay
        LatLng ll0 = new LatLng(31.232451, 121.541163);
        LatLng ll1 = new LatLng(31.231479, 121.542902);
        LatLng ll2 = new LatLng(31.23156, 121.543027);
        LatLng ll3 = new LatLng(31.231911, 121.544007);
        LatLng ll4 = new LatLng(31.231973, 121.544105);
        LatLng ll5 = new LatLng(31.235025, 121.542646);
        LatLng ll6 = new LatLng(31.235203, 121.542996);
        LatLng ll7 = new LatLng(31.236739, 121.545489);


        List<LatLng> my_points = new ArrayList<LatLng>();
        my_points.add(ll0);
        my_points.add(ll1);
        my_points.add(ll2);
        my_points.add(ll3);
        my_points.add(ll4);
        my_points.add(ll5);
        my_points.add(ll6);
        my_points.add(ll7);

        OverlayOptions ooA = new MarkerOptions().position(ll0).icon(bdA)
                .zIndex(9).draggable(true);
        mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
        OverlayOptions ooB = new MarkerOptions().position(ll7).icon(bdB)
                .zIndex(5);
        mMarkerB = (Marker) (mBaiduMap.addOverlay(ooB));
   //     OverlayOptions ooC = new MarkerOptions().position(llC).icon(bdStudent)
  //              .perspective(false).anchor(0.5f, 0.5f).rotate(30).zIndex(7);
  //      mMarkerC = (Marker) (mBaiduMap.addOverlay(ooC));
   //     OverlayOptions ooD = new MarkerOptions().position(llD).icon(bdD)
  //              .perspective(false).zIndex(7);
   //     mMarkerD = (Marker) (mBaiduMap.addOverlay(ooD));

        OverlayOptions ooPolyline = new PolylineOptions().width(7)
                .color(0xAAFF0000).points(my_points);
        mPolyA = (Polyline) mBaiduMap.addOverlay(ooPolyline);

        // add ground overlay
        LatLng southwest = new LatLng(31.232451, 121.541163);
        LatLng northeast = new LatLng(31.236739, 121.545489);
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
        bdA.recycle();
        bdB.recycle();
        bdC.recycle();
        bdD.recycle();
        bd.recycle();
        bdGround.recycle();
    }



}
