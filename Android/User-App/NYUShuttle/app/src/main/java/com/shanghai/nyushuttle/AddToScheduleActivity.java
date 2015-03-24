package com.shanghai.nyushuttle;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
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

import java.util.Calendar;
import java.util.Map;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class AddToScheduleActivity extends Activity {
    public String route_name;
    public String new_fav_details = "";
    public SharedPreferences sharedPref;
    public int alarm_day = 0;
    public int alarm_hour = 0;
    public int alarm_minute = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_schedule);

        sharedPref = getApplicationContext().getSharedPreferences("nyushuttlepref",Context.MODE_PRIVATE);
        Log.w("alex-sharedpref",getApplication()+"");

        Intent intent = getIntent();
        String schedule_details = intent.getStringExtra("com.shanghai.nyushuttle.SCHEDULE_DETAIL");
      // TextView schedule_details_tv = (TextView) findViewById(R.id.scheduleDetails);
      //  schedule_details_tv.setText(schedule_details);
        String[] detail_items = schedule_details.split(" ");
        if (detail_items[0].contains("Route")) {
            for (int i = 1; i < detail_items.length; i++)
                if (detail_items[i].contains("(")) {
                    String hour_min;
                    hour_min = detail_items[i].replace("(", "");
                    hour_min = hour_min.replace(")", "");
                    String[] hour_and_min = hour_min.split(":");
                    alarm_hour = Integer.parseInt((hour_and_min[0]));
                    alarm_minute = Integer.parseInt((hour_and_min[1]));
                }
        }
        else
        {
            String[] hour_and_min = detail_items[0].split(":");
            alarm_hour = Integer.parseInt((hour_and_min[0]));
            alarm_minute = Integer.parseInt((hour_and_min[1]));
        }

        Log.w("alex-log-details",alarm_hour + " " + alarm_minute);
        Log.w("alex-log-details",schedule_details);
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
        Log.w("alex-log_newfav",new_fav_details + " " + route_name);

        //Here we start alarms

        AlarmManager alarmMgr;
        PendingIntent alarmIntent;
        Intent otherIntent;
        int important_unique_id;
        Calendar calendar;



        //and here we end

        for (int i=0; i<days.length;i++)
            {
                int number_of_alarms = sharedPref.getInt("alarm_count",0);
                String current_routes_for_day = sharedPref.getString(days[i], "");
                if (new_fav_details.contains(days[i]) && !current_routes_for_day.contains(route_name))
                {
                    current_routes_for_day += route_name;
                    current_routes_for_day += " ";
                    editor.putString(days[i],current_routes_for_day);
                    editor.apply();
//NEWW ALARM!
                    alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
                    otherIntent = new Intent(this.getApplicationContext(), AlarmReceiver.class);
                    important_unique_id = ((int)route_name.charAt(0)) * 1000 + ((int)route_name.charAt(2)) * 100 + ((int)route_name.charAt(3)) * 10 + i;
                    alarmIntent = PendingIntent.getBroadcast(this.getApplicationContext(), important_unique_id, otherIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    alarm_day = (i+1)%7+1;
                    calendar.set(Calendar.DAY_OF_WEEK,alarm_day);
                    calendar.set(Calendar.HOUR_OF_DAY, alarm_hour);
                    calendar.set(Calendar.MINUTE, alarm_minute);
                    calendar.set(Calendar.SECOND,0);

                    calendar.add(Calendar.MINUTE,-10); // TODO: Replace with user preference
                    if (calendar.getTimeInMillis() <= System.currentTimeMillis())
                        calendar.add(Calendar.DAY_OF_YEAR,7);

                    alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                            7 * 24 * 60 * 60 * 1000, alarmIntent);
                    Log.w("alex-log-alarm",i+ " " + calendar.getTimeInMillis() + " " + days[i] + " " + alarm_hour + " " + alarm_minute + " " + route_name);

                    editor.putInt("alarm_count",number_of_alarms+1);
                    editor.apply();

                    editor.putString("alarm_nr_" + (number_of_alarms+1),alarm_day + "#" + alarm_hour + "#" + alarm_minute + "#" + route_name + "#");
                    editor.apply();

//UP TO HERE
                }
                if(!new_fav_details.contains(days[i]) && current_routes_for_day.contains(route_name))
                {
                    current_routes_for_day = current_routes_for_day.replace(route_name,"");
                    editor.putString(days[i],current_routes_for_day);
                    editor.apply();

                    //REMOVE ALARM!
                    alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
                    otherIntent = new Intent(this, Landing.class);
                    important_unique_id = ((int)route_name.charAt(0)) * 1000 + ((int)route_name.charAt(2)) * 100 + ((int)route_name.charAt(3)) * 10 + i;
                    alarmIntent = PendingIntent.getBroadcast(this, important_unique_id, otherIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    alarmMgr.cancel(alarmIntent);
//UP TO HERE
                }
            }

        Log.w("alex-log", String.valueOf(sharedPref.getAll()));

        Intent intent = new Intent(this, Landing.class);
        startActivity(intent);
    }

    public void removeFav(View view) {
        SharedPreferences.Editor editor = sharedPref.edit();
        String[] days = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};

        AlarmManager alarmMgr;
        PendingIntent alarmIntent;
        Intent otherIntent;
        int important_unique_id;

        for (int i=0; i<days.length;i++)
        {
            String current_routes_for_day = sharedPref.getString(days[i], "");
            if (current_routes_for_day.contains(route_name))
            {
                current_routes_for_day = current_routes_for_day.replace(route_name,"");
                editor.putString(days[i],current_routes_for_day);
                editor.apply();
//remove alarm
                alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
                otherIntent = new Intent(this, Landing.class);
                important_unique_id = ((int)route_name.charAt(0)) * 1000 + ((int)route_name.charAt(2)) * 100 + ((int)route_name.charAt(3)) * 10 + i;
                alarmIntent = PendingIntent.getBroadcast(this, important_unique_id, otherIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmMgr.cancel(alarmIntent);
            }
        }
        Log.w("alex-log", String.valueOf(sharedPref.getAll()));

        Intent intent = new Intent(this, Landing.class);
        startActivity(intent);
    }
}
