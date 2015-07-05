package tud.tk3.splitris;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import java.io.IOException;
import java.net.InetAddress;

import tud.tk3.splitscreen.Util;
import tud.tk3.splitscreen.network.DiscoveryHandler;

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
        GameContext.initClient();
        GameContext.Client.discoverScreenServers(GameContext.PORT, new DiscoveryHandler() {
            @Override
            public void onFound(InetAddress address, String nickname) {
                Log.d(TAG, "Found: " + nickname);
            }
        });
    }

    private void server() {
        Log.d(TAG, "Server started");

        try {
            GameContext.initServer("Nickname!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent gamelobby = new Intent(this, GameLobby.class);
        startActivity(gamelobby);
    }
}
