package tud.tk3.splitris.Tetris;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import java.util.List;

import tud.tk3.splitris.R;

public class AppPreferences extends PreferenceActivity {

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