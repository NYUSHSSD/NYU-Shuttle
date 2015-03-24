package com.shanghai.nyushuttle;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by Alexandru on 3/20/2015.
 */
public class AlarmBootReceiver extends BroadcastReceiver {
    String[] days = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
    SharedPreferences sharedPref;
    AlarmManager alarmMgr;
    PendingIntent alarmIntent;
    Intent otherIntent;
    int important_unique_id;
    Calendar calendar;
    int number_of_alarms;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w("alex-log-alarm","Caught boot!");
            sharedPref = context.getApplicationContext().getSharedPreferences("nyushuttlepref",Context.MODE_PRIVATE);
            number_of_alarms = sharedPref.getInt("alarm_count",0);
        Log.w("alex-log-alarm",sharedPref.getAll() + "");
            for (int i = 1; i <= number_of_alarms; i++) {
                String[] alarm_details = (sharedPref.getString("alarm_nr_" + i,"")).split("#");
                int alarm_day = Integer.parseInt(alarm_details[0]);
                int alarm_hour = Integer.parseInt(alarm_details[1]);
                int alarm_minute = Integer.parseInt(alarm_details[2]);
                String route_name = alarm_details[3];

//NEWW ALARM!
                    alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    otherIntent = new Intent(context.getApplicationContext(), AlarmReceiver.class);
                    important_unique_id = ((int) route_name.charAt(0)) * 1000 + ((int) route_name.charAt(2)) * 100 + ((int) route_name.charAt(3)) * 10 + i;
                    alarmIntent = PendingIntent.getBroadcast(context.getApplicationContext(), important_unique_id, otherIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.DAY_OF_WEEK, alarm_day);
                    calendar.set(Calendar.HOUR_OF_DAY, alarm_hour);
                    calendar.set(Calendar.MINUTE, alarm_minute);
                    calendar.set(Calendar.SECOND, 0);

                    calendar.add(Calendar.MINUTE,-10); // TODO: Replace with user preference
                    if (calendar.getTimeInMillis() <= System.currentTimeMillis())
                        calendar.add(Calendar.DAY_OF_YEAR,7);

                    alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                            7 * 24 * 60 * 60 * 1000, alarmIntent);
                    Log.w("alex-log-alarm", i + " " + calendar.getTimeInMillis() + " " + days[i] + " " + alarm_hour + " " + alarm_minute + " " + route_name);
//UP TO HERE
                }
            }
        }