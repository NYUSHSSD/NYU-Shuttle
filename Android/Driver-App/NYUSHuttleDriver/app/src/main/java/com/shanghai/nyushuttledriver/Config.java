package com.shanghai.nyushuttledriver;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Created by Alexandru on 5/10/2015.
 */
public class Config {

    public static String bk_host_name = "http://nyushuttle.ddns.net/";
    public static String bk_api_dir = "api/";
    public static String bk_get_version_script = "get_version.php";
    public static String bk_apk_dir = "apks/";
    public static String bk_update_file_name = "driver-";
    public static String bk_update_location_script = "driver_update_location.php";
    public static String bk_add_log_script = "add_log.php";

    public static boolean internetConnected(Context ctx)
    {
        ConnectivityManager connMgr = (ConnectivityManager)
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());

    }

    public static void restoreDefaults(Activity ctx)
    {
        SharedPreferences sharedPref = getDefaultSharedPreferences(ctx.getApplication());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("host_name", Config.bk_host_name);
        editor.commit();

    }


}
