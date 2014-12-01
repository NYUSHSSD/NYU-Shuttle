package baidumapsdk.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Angela.
 */
public class RouteMarker extends android.app.Activity {

    // 地图相关 Map Stuff
    MapView mMapView;
    BaiduMap mBaiduMap;
    // UI相关 UI Stuff
    Button resetBtn;
    Button clearBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geometry);
        // 初始化地图 Initialise Map
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        // UI初始化 UI Initialisation
        clearBtn = (Button) findViewById(R.id.button1);
        resetBtn = (Button) findViewById(R.id.button2);

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

        // 界面加载时添加绘制图层 Add time for drawing layer interface
        addCustomElementsDemo();
    }
    public void addCustomElementsDemo() {
        // 添加折线 Drawing Polylines
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

    public void resetClick() {
        // 添加绘制元素 Adding drawn elements
        addCustomElementsDemo();
    }

    public void clearClick() {
        // 清除所有图层 Clear all layers
        mMapView.getMap().clear();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

}
