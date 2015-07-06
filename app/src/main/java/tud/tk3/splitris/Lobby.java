package tud.tk3.splitris;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import tud.tk3.splitris.network.GameController;
import tud.tk3.splitris.network.GameControllerInterface;
import tud.tk3.splitscreen.Util;
import tud.tk3.splitscreen.network.DiscoveryHandler;

public class Lobby extends Activity {

    class AddrNickSet {
        public String nick;
        public InetAddress addr;
        public String toString() { return nick; }
    }

    private final static String TAG = "Lobby";
    private ArrayAdapter<AddrNickSet> mAdapter;
    SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby);

        Context mContext = this.getApplicationContext();
        //0 = mode private. only this app can read these preferences
        mPrefs = mContext.getSharedPreferences("myAppPrefs", 0);


        ListView list = (ListView)findViewById(R.id.listofCurrentServerSessions);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        list.setAdapter(mAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // handle click on item in server list
                final AddrNickSet set = mAdapter.getItem(position);
                Toast.makeText(Lobby.this, "Connecting to " + set.nick, Toast.LENGTH_LONG).show();
                // init client lib
                GameContext.initClient();
                new AsyncTask<Void, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... params) {
                        try {
                            // connect to server and register with it
                            GameContext.Client.connect(2000, set.addr, GameContext.PORT);
                            // get remote gamecontroller instance and set it in our context
                            GameContext.Client.getEndPoint().getKryo().register(GameControllerInterface.class);
                            GameContext.Controller = GameContext.Client.getRemoteObject(GameContext.RMI_GAMECONTROLLER, GameControllerInterface.class);
                            //GameContext.Controller.enterGame(getNickname());
                        } catch (IOException e) {
                            e.printStackTrace();
                            return false;
                        }
                        return true;
                    }
                    @Override
                    protected void onPostExecute(Boolean success) {
                        if(success) {
                            Toast.makeText(Lobby.this, "Connected ", Toast.LENGTH_LONG).show();
                            // TODO: we are now connected. what now?
                        } else {
                            Toast.makeText(Lobby.this, "Could NOT connect to " + set.nick, Toast.LENGTH_LONG).show();
                        }
                    }
                }.execute();
            }
        });
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
        mAdapter.clear();
        GameContext.Client.discoverScreenServers(GameContext.PORT, new DiscoveryHandler() {
            @Override
            public void onFound(InetAddress address, String nickname) {
                AddrNickSet item = new AddrNickSet();
                item.nick = nickname;
                item.addr = address;
                InetAddress localHostAddr = null;
                try {
                    localHostAddr =InetAddress.getByName("127.0.0.1");
                } catch(UnknownHostException e) {
                    e.printStackTrace();
                }
                if(localHostAddr != null && ! address.equals(localHostAddr)) {
                    mAdapter.add(item);
                }
                Log.d(TAG, "Found: " + nickname);
            }
        });
    }

    private void server() {
        Log.d(TAG, "Server started");
        try {
            GameContext.initServer(getNickname());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent gamelobby = new Intent(this, GameLobby.class);

        gamelobby.putExtra("USER_NAME", getNickname());

        startActivity(gamelobby);
    }

    private String getNickname() {
        TextView userNameText = (TextView) findViewById(R.id.editUsername);
        return userNameText.getText().toString();
    }
}
