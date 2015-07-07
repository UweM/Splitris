package tud.tk3.splitris;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import tud.tk3.splitris.network.GameController;
import tud.tk3.splitris.network.GameControllerInterface;
import tud.tk3.splitris.network.Player;
import tud.tk3.splitris.tetris.Initiator;
import tud.tk3.splitscreen.output.IScreenView;
import tud.tk3.splitscreen.output.ScreenView;
import tud.tk3.splitscreen.output.Viewport;
import tud.tk3.splitscreen.screen.BlockScreen;

public class GameActivity extends Activity implements GestureDetector.OnGestureListener {

    private final static String TAG = "GameActivity";

    private final static int swipe_Min_Distance = 100;
    private final static int swipe_Max_Distance = 1000;
    private final static int swipe_Min_Velocity = 50;
    private GestureDetector gDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameactivity);
        //gDetector = new GestureDetectorCompat(this,null);

        ScreenView game = (ScreenView) findViewById(R.id.game_screen);
        ScreenView info = (ScreenView) findViewById(R.id.info_screen);
        if(GameContext.Client != null) {
            GameContext.Client.registerView(0, game);
            GameContext.Client.registerView(1, info);
        }
        else {
            Initiator init = new Initiator();
            init.configureBlockScreens(GameContext.Players, game);
            GameContext.Controller = new GameController(null, null);
            BlockScreen bs = init.configureBlockScreens(GameContext.Players, game);
            BlockScreen preview = new BlockScreen(1, 4, 4);
            Viewport vp = new Viewport(preview, 0, 0, preview.getWidth(), preview.getHeight());
            vp.addView(info);
            for(Player p : GameContext.Players) {
                if(p.getConnection() != null)
                    vp.addView(p.getConnection().getRemoteView(1));
            }
            preview.setOccupied(Color.BLUE);
            GameContext.startGame(bs, preview);
        }


        gDetector = new GestureDetector(this, this);
    }

    public boolean onLeftBtnClicked(View view) {
        Log.d(TAG, "onLeftBtnClicked()");
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if(GameContext.Controller != null)
                    GameContext.Controller.moveLeft();
                return null;
            }
        }.execute();

        return false;
    }

    public boolean onRightBtnClicked(View view) {
        Log.d(TAG, "onRightBtnClicked()");
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if(GameContext.Controller != null)
                    GameContext.Controller.moveRight();
                return null;
            }
        }.execute();

        return false;
    }

    public boolean onTurnBtnClicked(View view) {
        Log.d(TAG, "onTurnBtnClicked()");
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if(GameContext.Controller != null)
                    GameContext.Controller.rotate();
                return null;
            }
        }.execute();

        return false;
    }

    public boolean onDownBtnClicked(View view) {
        Log.d(TAG, "onDownBtnClicked()");
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if(GameContext.Controller != null)
                    GameContext.Controller.moveDown();
                return null;
            }
        }.execute();

        return false;
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

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent me) {
        return gDetector.onTouchEvent(me);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        final float xDistance = Math.abs(e1.getX() - e2.getX());
        final float yDistance = Math.abs(e1.getY() - e2.getY());

        if(xDistance > swipe_Max_Distance || yDistance > swipe_Max_Distance)
            return false;

        velocityX = Math.abs(velocityX);
        velocityY = Math.abs(velocityY);

        //Log.d(TAG, "velocityX: " + velocityX + ", velocityY: " + velocityY);

        if(velocityX > swipe_Min_Velocity && xDistance > swipe_Min_Distance){
            if(e1.getX() > e2.getX()){ // right to left
                Log.d(TAG, "left");
                return onLeftBtnClicked(null);
            }
            else // left to right
            {
                Log.d(TAG, "right");
                return onRightBtnClicked(null);
            }
        }
        else if(velocityY > swipe_Min_Velocity && yDistance > swipe_Min_Distance){
            if(e1.getY() > e2.getY()) // bottom to up
            {
                Log.d(TAG, "up");
                return onTurnBtnClicked(null);
            }
            else // up to bottom
            {
                Log.d(TAG,"down");
                return onDownBtnClicked(null);
            }
        }

        return false;
    }
}

