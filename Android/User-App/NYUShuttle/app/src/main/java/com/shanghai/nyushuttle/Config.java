package com.shanghai.nyushuttle;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Created by Alexandru on 5/6/2015.
 */

public class Config {

    public static String bk_host_name = "http://che.shanghai.nyu.edu/";
    public static String bk_api_dir = "api/";
    public static String bk_get_version_script = "get_version.php";
    public static String bk_apk_dir = "apks/";
    public static String bk_update_file_name = "shuttle-";
    public static String bk_get_all_routes_script = "select_all.php";
    public static String bk_get_bus_location_script = "get_bus_location.php";

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
