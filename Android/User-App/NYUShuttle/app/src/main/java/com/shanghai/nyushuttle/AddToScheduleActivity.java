package com.shanghai.nyushuttle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.shanghai.nyushuttle.R;

import java.util.Map;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class AddToScheduleActivity extends Activity {
    public String route_name;
    public String new_fav_details = "";
    public SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_schedule);

        sharedPref = getDefaultSharedPreferences(getApplication());

        Intent intent = getIntent();
        String schedule_details = intent.getStringExtra("com.shanghai.nyushuttle.SCHEDULE_DETAIL");
      // TextView schedule_details_tv = (TextView) findViewById(R.id.scheduleDetails);
      //  schedule_details_tv.setText(schedule_details);
        String[] detail_items = schedule_details.split(" ");
        CheckBox cb1 = (CheckBox) findViewById(R.id.checkbox_mon);
        CheckBox cb2 = (CheckBox) findViewById(R.id.checkbox_tue);
        CheckBox cb3 = (CheckBox) findViewById(R.id.checkbox_wed);
        CheckBox cb4 = (CheckBox) findViewById(R.id.checkbox_thu);
        CheckBox cb5 = (CheckBox) findViewById(R.id.checkbox_fri);
        CheckBox cb6 = (CheckBox) findViewById(R.id.checkbox_sat);
        CheckBox cb7 = (CheckBox) findViewById(R.id.checkbox_sun);
        cb1.setEnabled(false);
        cb2.setEnabled(false);
        cb3.setEnabled(false);
        cb4.setEnabled(false);
        cb5.setEnabled(false);
        cb6.setEnabled(false);
        cb7.setEnabled(false);


        for (int i=0; i<detail_items.length; i++)
        {
            if (detail_items[i].contains("Route"))
            {
                route_name = detail_items[i+1];
                TextView scheduleTV1 = (TextView) findViewById(R.id.scheduleTV1);
                scheduleTV1.setText("Route " + route_name);
            }
            if (detail_items[i].contains("Leaves"))
            {
                String leaves_from = detail_items[i] + " " + detail_items[i+1] + " " + detail_items[i+2] + " " + detail_items[i+3] + " " + detail_items[i+4] + " " + detail_items[i+5];
                TextView scheduleTV2 = (TextView) findViewById(R.id.scheduleTV2);
                scheduleTV2.setText(leaves_from);
            }
            if (detail_items[i].contains("Arrives"))
            {
                String arrives_at = detail_items[i] + " " + detail_items[i+1] + " " + detail_items[i+2] + " " + detail_items[i+3] + " " + detail_items[i+4] + " " + detail_items[i+5];
                TextView scheduleTV3 = (TextView) findViewById(R.id.scheduleTV3);
                scheduleTV3.setText(arrives_at);
            }



            if (detail_items[i].contains("Mon"))
            {
                cb1.setEnabled(true);
                String current_routes_for_day = sharedPref.getString("Mon", "");
                if(current_routes_for_day.contains(route_name))
                    cb1.setChecked(true);
            }

            if (detail_items[i].contains("Tue"))
            {
                cb2.setEnabled(true);
                String current_routes_for_day = sharedPref.getString("Tue", "");
                if(current_routes_for_day.contains(route_name))
                    cb2.setChecked(true);
            }

            if (detail_items[i].contains("Wed"))
            {
                cb3.setEnabled(true);
                String current_routes_for_day = sharedPref.getString("Wed", "");
                if(current_routes_for_day.contains(route_name))
                    cb3.setChecked(true);
            }

            if (detail_items[i].contains("Thu"))
            {
                cb4.setEnabled(true);
                String current_routes_for_day = sharedPref.getString("Thu", "");
                if(current_routes_for_day.contains(route_name))
                    cb4.setChecked(true);
            }

            if (detail_items[i].contains("Fri"))
            {
                cb5.setEnabled(true);
                String current_routes_for_day = sharedPref.getString("Fri", "");
                if(current_routes_for_day.contains(route_name))
                    cb5.setChecked(true);
            }

            if (detail_items[i].contains("Sat"))
            {
                cb6.setEnabled(true);
                String current_routes_for_day = sharedPref.getString("Sat", "");
                if(current_routes_for_day.contains(route_name))
                    cb6.setChecked(true);
            }

            if (detail_items[i].contains("Sun"))
            {
                cb7.setEnabled(true);
                String current_routes_for_day = sharedPref.getString("Sun", "");
                if(current_routes_for_day.contains(route_name))
                    cb7.setChecked(true);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_to_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
int x;
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_mon:
                if (checked)
                    new_fav_details += " Mon";
                break;
            case R.id.checkbox_tue:
                if (checked)
                    new_fav_details += " Tue";
                break;
            case R.id.checkbox_wed:
                if (checked)
                    new_fav_details += " Wed";
                break;
            case R.id.checkbox_thu:
                if (checked)
                    new_fav_details += " Thu";
                break;
            case R.id.checkbox_fri:
                if (checked)
                    new_fav_details += " Fri";
                break;
            case R.id.checkbox_sat:
                if (checked)
                    new_fav_details += " Sat";
                break;
            case R.id.checkbox_sun:
                if (checked)
                    new_fav_details += " Sun";
                break;

        }
    }

    public void updateFav(View view) {
        SharedPreferences.Editor editor = sharedPref.edit();
        String[] days = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
        Log.w("alex-log",new_fav_details + " " + route_name);
        for (int i=0; i<days.length;i++)
            {
                String current_routes_for_day = sharedPref.getString(days[i], "");
                if (new_fav_details.contains(days[i]) && !current_routes_for_day.contains(route_name))
                {
                    current_routes_for_day += route_name;
                    current_routes_for_day += " ";
                    editor.putString(days[i],current_routes_for_day);
                    editor.apply();
                }
                if(!new_fav_details.contains(days[i]) && current_routes_for_day.contains(route_name))
                {
                    current_routes_for_day = current_routes_for_day.replace(route_name,"");
                    editor.putString(days[i],current_routes_for_day);
                    editor.apply();
                }
            }

        Log.w("alex-log", String.valueOf(sharedPref.getAll()));

        Intent intent = new Intent(this, Landing.class);
        startActivity(intent);
    }

    public void removeFav(View view) {
        SharedPreferences.Editor editor = sharedPref.edit();
        String[] days = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
        for (int i=0; i<days.length;i++)
        {
            String current_routes_for_day = sharedPref.getString(days[i], "");
            if (current_routes_for_day.contains(route_name))
            {
                current_routes_for_day = current_routes_for_day.replace(route_name,"");
                editor.putString(days[i],current_routes_for_day);
                editor.apply();
            }
        }
        Log.w("alex-log", String.valueOf(sharedPref.getAll()));

        Intent intent = new Intent(this, Landing.class);
        startActivity(intent);
    }
}
