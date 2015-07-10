package tud.tk3.splitris.tetris;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import java.util.List;

import tud.tk3.splitris.R;

public class AppPreferences extends PreferenceActivity {
    // default class for handling the apps preferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // addPreferencesFromResource(R.xml.preferences);
    }


    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.preferences, target);
    }

}