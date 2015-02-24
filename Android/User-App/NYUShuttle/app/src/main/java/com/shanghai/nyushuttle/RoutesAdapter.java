package com.shanghai.nyushuttle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Alexandru on 10/10/2014.
 */
public class RoutesAdapter extends ArrayAdapter<RouteClass> {
    public RoutesAdapter(Context context, ArrayList<RouteClass> routeClasses) {
        super(context, 0, routeClasses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        RouteClass routeClass = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_row, parent, false);
        }
        // Lookup view for data population
        TextView tv1 = (TextView) convertView.findViewById(R.id.header_text);
        TextView tv2 = (TextView) convertView.findViewById(R.id.content_text1);
        TextView tv3 = (TextView) convertView.findViewById(R.id.content_text2);
        TextView tv4 = (TextView) convertView.findViewById(R.id.content_text3);
        // Populate the data into the template view using the data object
        tv1.setText(routeClass.route_number);
        tv2.setText(routeClass.going_from);
        tv3.setText(routeClass.going_to);
        tv4.setText(routeClass.days);
        // Return the completed view to render on screen

        Button detailMapButton = (Button) convertView.findViewById(R.id.view_map);
        detailMapButton.setHint(routeClass.route_number);

        Button alarmButton = (Button) convertView.findViewById(R.id.choose_shuttle);
        alarmButton.setHint("" + routeClass.route_number + " " + routeClass.going_from + " " +routeClass.going_to + " " + routeClass.days);

        return convertView;
    }
}
