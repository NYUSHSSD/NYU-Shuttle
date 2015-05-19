package com.shanghai.nyushuttle;

/**
 * Created by Alexandru on 5/10/2015.
 */
import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

public class APP_BOOT extends Application {
    @Override
    public  void onCreate()
    {

        Parse.initialize(this, "yAUBrYN6b1ulhRDRjqcEeZ4vT4g9spcWeQ6P9hJ1", "dxmYF05SArSoiWAXjyGqLqAfIKyOpTBZsixyK3Dq");
        ParseInstallation.getCurrentInstallation().saveInBackground();

    }
}
