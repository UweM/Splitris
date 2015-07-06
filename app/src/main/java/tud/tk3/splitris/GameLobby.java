package tud.tk3.splitris;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class GameLobby extends Activity {

    private final static String TAG = "GameLobby";

    private List<String> mGameMember = new ArrayList<>();
    private int mSelectedItemId = -1;
    private boolean mSelectedItemHightlighted = false;
    private ListView mMemberListView;
    private String mOwnUserName;

    private StableArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelobby);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mOwnUserName = extras.getString("USER_NAME");
        }

        mMemberListView = (ListView) findViewById(R.id.listofCurrentServerSessions);

        mGameMember.add(mOwnUserName);

        adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, mGameMember);

        mMemberListView.setAdapter(adapter);

        mMemberListView.setSelector(R.color.material_blue_grey_800);
        mMemberListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        mMemberListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                //final String item = (String) parent.getItemAtPosition(position);
                mSelectedItemId = position;

                Log.d(TAG, "item selected...");
                //adapter.notifyDataSetChanged();

            }

        });
    }

    public void onStartButtonClicked(View view) {

        try {
            GameContext.initServer(mOwnUserName);

            Log.d(TAG, "Server started");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void oneLeftBtnClicked(View view) {
        Log.d(TAG, "left btn..., mSelectedItemid BEFORE: " + mSelectedItemId);
        if(mSelectedItemId != 0) {
            Collections.swap(mGameMember, mSelectedItemId - 1, mSelectedItemId);
            mMemberListView.requestFocusFromTouch();
            mMemberListView.setSelection(mSelectedItemId - 1);
            adapter.notifyDataSetChanged();
            mSelectedItemId--;
            Log.d(TAG, "left btn..., mSelectedItemid AFTER: " + mSelectedItemId);
        } else {
            mMemberListView.requestFocusFromTouch();
            mMemberListView.setSelection(mSelectedItemId);
            adapter.notifyDataSetChanged();
        }
    }

    public void onRightBtnClicked(View view) {
        if(mSelectedItemId < 3) {
            Collections.swap(mGameMember, mSelectedItemId + 1, mSelectedItemId);
            mMemberListView.requestFocusFromTouch();
            mMemberListView.setSelection(mSelectedItemId  + 1);
            adapter.notifyDataSetChanged();
            mSelectedItemId++;
        } else {
            mMemberListView.requestFocusFromTouch();
            mMemberListView.setSelection(mSelectedItemId);
            adapter.notifyDataSetChanged();
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


    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}
