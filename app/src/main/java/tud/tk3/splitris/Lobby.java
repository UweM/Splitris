package tud.tk3.splitris;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import tud.tk3.splitscreen.Util;
public class Lobby extends Activity {

    private Button mServerBtn, mClientBtn;
    private final static String TAG = "Lobby";
    private String mIpAddr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby);

        mIpAddr = Util.wifiIpAddress(this);
    }

    public void onServerBtnClicked(View view) {
        server();
    }

    public void onClientBtnClicked(View view) {
        client();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void client() {

        Log.d(TAG, "Client started");

    }
    private void server() {
        Log.d(TAG, "Server started");
    }
}
