package tud.tk3.splitris;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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

    private final static String TAG = "GameLobby";

    private List<Player> mGameMember = new ArrayList<>();
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
                   Log.d(TAG, "New Player: " + p.getNickname());
                   mGameMember.add(p);
                   mAdapter.notifyDataSetChanged();
               }
           }
        );
    }

    public void onStartButtonClicked(View view) {

    }

    public void oneLeftBtnClicked(View view) {
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

    public void onRightBtnClicked(View view) {
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
