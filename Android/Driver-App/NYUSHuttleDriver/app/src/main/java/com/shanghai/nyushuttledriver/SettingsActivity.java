package com.shanghai.nyushuttledriver;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Alexandru on 3/19/2015.
 */
public class SettingsActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}