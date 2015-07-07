package tud.tk3.splitris;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import tud.tk3.splitris.network.GameEventHandler;
import tud.tk3.splitris.network.Player;

public class GameLobby extends Activity {

    private static final int SELECT_PICTURE = 1;
    private final static String TAG = "GameLobby";

    private ArrayList<Player> mGameMember = new ArrayList<>();
    private int mSelectedItemId = -1;
    private boolean mSelectedItemHightlighted = false;
    private ListView mMemberListView;
    private String mOwnUserName;

    private StableArrayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelobby);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mOwnUserName = extras.getString("USER_NAME");
        }

        mMemberListView = (ListView) findViewById(R.id.listofCurrentServerSessions);

        mAdapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, mGameMember);

        mGameMember.add(new Player(null, mOwnUserName));
        mAdapter.notifyDataSetChanged();

        mMemberListView.setAdapter(mAdapter);

        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        int ip = wm.getConnectionInfo().getIpAddress();
        final String subset_ip = Integer.toString(ip).substring(Integer.toString(ip).length() - 3);

        mMemberListView.setSelector(R.color.material_blue_grey_800);
        mMemberListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        mMemberListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                //final String item = (String) parent.getItemAtPosition(position);
                mSelectedItemId = position;

                Log.d(TAG, "item selected...");
                //mAdapter.notifyDataSetChanged();

            }

        });

        GameContext.Server.setGameEventHandler(new GameEventHandler() {
               @Override
               public void onNewPlayer(Player p) {
                   Log.d(TAG, "New Player: " + p.getNickname() + subset_ip);
                   mGameMember.add(p);
                   mAdapter.notifyDataSetChanged();
               }
           }
        );
    }

    public void onStartButtonClicked(View view) {
        GameContext.Players = mGameMember;

        Intent gamelobby = new Intent(this, GameActivity.class);
        startActivity(gamelobby);
    }


    private void onDemoButtonClicked() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                ArrayList<Uri> list = new ArrayList<Uri>();
                // only one selected?
                if(data.getData() != null) {
                    list.add(data.getData());
                }
                else { // multiple selected
                    ClipData d = data.getClipData();
                    for (int i = 0; i < d.getItemCount(); i++) {
                        list.add(d.getItemAt(i).getUri());
                    }
                }
                // TODO

            }
        }
    }

    public void onUpBtnClicked(View view) {
        if(mSelectedItemId == -1) {
            return;
        }

        if(mSelectedItemId != 0) {
            Collections.swap(mGameMember, mSelectedItemId - 1, mSelectedItemId);
            mAdapter.notifyDataSetChanged();

            mMemberListView.requestFocusFromTouch();
            mMemberListView.setSelection(mSelectedItemId - 1);
            mSelectedItemId--;
        } else if(mSelectedItemId != -1) {
            mMemberListView.requestFocusFromTouch();
            mMemberListView.setSelection(mSelectedItemId);
        }
    }

    public void onDownBtnClicked(View view) {
        if(mSelectedItemId == -1) {
            return;
        }

        if(mSelectedItemId < mGameMember.size() - 1) {
            Collections.swap(mGameMember, mSelectedItemId + 1, mSelectedItemId);
            mAdapter.notifyDataSetChanged();

            mMemberListView.requestFocusFromTouch();
            mMemberListView.setSelection(mSelectedItemId  + 1);
            mSelectedItemId++;
        } else if(mSelectedItemId != -1) {
            mMemberListView.requestFocusFromTouch();
            mMemberListView.setSelection(mSelectedItemId);
        }
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


    private class StableArrayAdapter extends ArrayAdapter<Player> {

        HashMap<Player, Integer> mIdMap = new HashMap<>();
        List<Player> mList;
        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<Player> objects) {
            super(context, textViewResourceId, objects);
            mList = objects;
        }

        @Override
        public long getItemId(int position) {
            return mIdMap.get(getItem(position));
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            mIdMap.clear();
            for (int i = 0; i < mList.size(); ++i) {
                mIdMap.put(mList.get(i), i);
            }
        }
    }
}
