package tud.tk3.splitris;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import tud.tk3.splitris.Tetris.AppPreferences;

import java.io.IOException;
import java.net.InetAddress;

import tud.tk3.splitscreen.Util;
import tud.tk3.splitscreen.network.DiscoveryHandler;

public class Lobby extends Activity {

    class AddrNickSet {
        public String nick;
        public InetAddress addr;
        public String toString() { return nick; }
    }

    private final static String TAG = "Lobby";
    private String mIpAddr;
    SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby);

        Context mContext = this.getApplicationContext();
        
        //0 = mode private. only this app can read these preferences
        mPrefs = mContext.getSharedPreferences("myAppPrefs", 0);

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
        ListView list = (ListView)findViewById(R.id.listofCurrentServerSessions);
        final ArrayAdapter<AddrNickSet> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        list.setAdapter(adapter);
        GameContext.Client.discoverScreenServers(GameContext.PORT, new DiscoveryHandler() {
            @Override
            public void onFound(InetAddress address, String nickname) {
                AddrNickSet item = new AddrNickSet();
                item.nick = nickname;
                item.addr = address;
                adapter.add(item);
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
