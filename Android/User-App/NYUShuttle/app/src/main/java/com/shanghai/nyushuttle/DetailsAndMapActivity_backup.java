package com.shanghai.nyushuttle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.GroundOverlayOptions;
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


public class DetailsAndMapActivity_backup extends Activity {

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
        String route_details = intent.getStringExtra(Landing.ROUTE_DETAIL);
        TextView route_detail_tv = (TextView) findViewById(R.id.route_detail_tv);
        route_detail_tv.setText(route_details);

        // åˆå§‹åŒ–åœ°å›¾ Initialise Map
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        // UIåˆå§‹åŒ– UI Initialisation
    //    clearBtn = (Button) findViewById(R.id.button1);
    //    resetBtn = (Button) findViewById(R.id.button2);

        View.OnClickListener clearListener = new View.OnClickListener() {
            public void onClick(View v) {
                clearClick();
            }
        };
        View.OnClickListener restListener = new View.OnClickListener() {
            public void onClick(View v) {
                resetClick();
            }
        };

        clearBtn.setOnClickListener(clearListener);
        resetBtn.setOnClickListener(restListener);

        // ç•Œé¢åŠ è½½æ—¶æ·»åŠ ç»˜åˆ¶å›¾å±‚ Add time for drawing layer interface
        initOverlay();
    }
    public void addCustomElementsDemo() {
        // æ·»åŠ æŠ˜çº¿ Drawing Polylines
        LatLng p1 = new LatLng(31.23067, 121.53917);
        LatLng p2 = new LatLng(31.224682, 121.534279);
        //LatLng p3 = new LatLng(39.97923, 116.437428);
        //can add new points depending on the number of stops.
        //Insert way to add Curent User Posn and Drivers' GPS position
        List<LatLng> points = new ArrayList<LatLng>();
        points.add(p1);
        points.add(p2);
        //points.add(p3); etc
        OverlayOptions ooPolyline = new PolylineOptions().width(7)
                .color(0xAAFF0000).points(points);
        mBaiduMap.addOverlay(ooPolyline);

    }


    public void initOverlay() {
        // add marker overlay
        LatLng llA = new LatLng(31.23067, 121.53917);
        LatLng ll2 = new LatLng(31.225374, 121.536378);
        LatLng ll3 = new LatLng(31.225777, 121.537563);
        LatLng ll4 = new LatLng(31.22889, 121.536147);
        LatLng llB = new LatLng(31.224682, 121.534279);
        LatLng llC = new LatLng(39.939723, 116.425541);
        LatLng llD = new LatLng(39.906965, 116.401394);

        List<LatLng> my_points = new ArrayList<LatLng>();
        my_points.add(llA);
      //  my_points.add(ll2);
      //  my_points.add(ll3);
      //  my_points.add(ll4);
        my_points.add(llB);

        OverlayOptions ooA = new MarkerOptions().position(llA).icon(bdA)
                .zIndex(9).draggable(true);
        mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
        OverlayOptions ooB = new MarkerOptions().position(llB).icon(bdB)
                .zIndex(5);
        mMarkerB = (Marker) (mBaiduMap.addOverlay(ooB));
        OverlayOptions ooC = new MarkerOptions().position(llC).icon(bdC)
                .perspective(false).anchor(0.5f, 0.5f).rotate(30).zIndex(7);
        mMarkerC = (Marker) (mBaiduMap.addOverlay(ooC));
        OverlayOptions ooD = new MarkerOptions().position(llD).icon(bdD)
                .perspective(false).zIndex(7);
        mMarkerD = (Marker) (mBaiduMap.addOverlay(ooD));

        OverlayOptions ooPolyline = new PolylineOptions().width(7)
                .color(0xAAFF0000).points(my_points);
        mPolyA = (Polyline) mBaiduMap.addOverlay(ooPolyline);

        // add ground overlay
        LatLng southwest = new LatLng(31.224682, 121.534279);
        LatLng northeast = new LatLng(31.23067, 121.53917);
        LatLngBounds bounds = new LatLngBounds.Builder().include(northeast)
                .include(southwest).build();

        OverlayOptions ooGround = new GroundOverlayOptions()
                .positionFromBounds(bounds).image(bdGround).transparency(0.8f);
        mBaiduMap.addOverlay(ooGround);

        MapStatusUpdate u = MapStatusUpdateFactory
                .newLatLng(bounds.getCenter());
        mBaiduMap.setMapStatus(u);




    }


    public void clearOverlay(View view) {
        mBaiduMap.clear();
    }


    public void resetOverlay(View view) {
        clearOverlay(null);
        initOverlay();
    }



    public void resetClick() {
        // æ·»åŠ ç»˜åˆ¶å…ƒç´  Adding drawn elements
        resetOverlay(null);
        Toast.makeText(this,"Hello",Toast.LENGTH_SHORT).show();
    }

    public void clearClick() {
        // æ¸…é™¤æ‰€æœ‰å›¾å±‚ Clear all layers
        clearOverlay(null);
        Toast.makeText(this,"Hello",Toast.LENGTH_SHORT).show();
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
