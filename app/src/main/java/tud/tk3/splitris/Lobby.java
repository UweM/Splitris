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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import tud.tk3.splitris.network.GameControllerInterface;
import tud.tk3.splitscreen.network.DiscoveryHandler;

public class Lobby extends Activity {
    // Main class called lobby - first activity the user is displayed
    // choose whether you want to act as a server or a client - connect to available servers

    class AddrNickSet {
        // enclosing class for nickname & address
        public String nick;
        public InetAddress addr;
        public String toString() { return nick; }
    }

    private final static String TAG = "Lobby";
    private ArrayAdapter<AddrNickSet> mAdapter;
    SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // onCreate method invoked when starting the app
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby);

        Context mContext = this.getApplicationContext();
        //0 = mode private. only this app can read these preferences
        mPrefs = mContext.getSharedPreferences("myAppPrefs", 0);

        //com.esotericsoftware.minlog.Log.TRACE = true;

        // ListView listing all the available servers on the network
        ListView list = (ListView)findViewById(R.id.listofCurrentServerSessions);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        list.setAdapter(mAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // handle click on item in server list
                final AddrNickSet set = mAdapter.getItem(position);
                Toast.makeText(Lobby.this, "Connecting to " + set.nick, Toast.LENGTH_SHORT).show();
                // init client lib
                GameContext.initClient();
                new AsyncTask<Void, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... params) {
                        // Background task for connecting to the server
                        try {
                            // connect to server and register with it
                            GameContext.Client.connect(2000, set.addr, GameContext.PORT, GameContext.PORT);
                            // get remote gamecontroller instance and set it in our context
                            GameContext.Client.getEndPoint().getKryo().register(GameControllerInterface.class);
                            GameContext.Controller = GameContext.Client.getRemoteObject(GameContext.RMI_GAMECONTROLLER, GameControllerInterface.class);
                            GameContext.Controller.enterGame(getNickname());
                        } catch (IOException e) {
                            e.printStackTrace();
                            return false;
                        }
                        return true;
                    }
                    @Override
                    protected void onPostExecute(Boolean success) {
                        if(success) {
                            Toast.makeText(Lobby.this, "Connected", Toast.LENGTH_SHORT).show();
                            Intent gamelobby = new Intent(Lobby.this, GameActivity.class);
                            startActivity(gamelobby);
                        } else {
                            Toast.makeText(Lobby.this, "Could NOT connect to " + set.nick, Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute();
            }
        });
    }

    public void onServerBtnClicked(View view) {
        server(); // start server
    }

    public void onClientBtnClicked(View view) {
        client(); // start client
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
        // initiate client code & load client nickname and ip address
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
        // initiate server code
        GameContext.Client = null;
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
        // handle the usernames
        TextView userNameText = (TextView) findViewById(R.id.editUsername);
        String user = userNameText.getText().toString();
        if(user.isEmpty()) {
            return "Hans Gruber";
        } else {
            return user;
        }
    }
}
