package tud.tk3.splitris;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tud.tk3.splitscreen.Util;

public class GameLobby extends Activity {

    private Button mLeft, mRight;
    private final static String TAG = "GameLobby";

    private List<String> mGameMember = new ArrayList<>();
    private int mSelectedItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelobby);


        ListView memberListView = (ListView) findViewById(R.id.listofCurrentServerSessions);

        mGameMember.add("member1");
        mGameMember.add("member2");
        mGameMember.add("member3");
        mGameMember.add("member4");

        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, mGameMember);

        memberListView.setAdapter(adapter);

        memberListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                //final String item = (String) parent.getItemAtPosition(position);

                mSelectedItemId = position;

                //mGameMember.remove(item);

                Log.d(TAG, "item selected...");

                adapter.notifyDataSetChanged();
                view.setBackgroundColor(8);
                view.setAlpha(1);
            }

        });
    }

    public void oneLeftBtnClicked(View view) {
        //
        //mGameMember.get(mSelectedItemId)
    }

    public void onRightBtnClicked(View view) {
        //
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
