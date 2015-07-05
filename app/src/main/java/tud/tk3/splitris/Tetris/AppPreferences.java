package tud.tk3.splitris.Tetris;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import tud.tk3.splitris.R;

public class AppPreferences extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

}