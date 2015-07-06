package tud.tk3.splitris;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class GameActivity extends Activity {

    private final static String TAG = "GameActivity";

    private GestureDetectorCompat myDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameactivity);
        myDetector = new GestureDetectorCompat(this,null);
    }

    public boolean onLeftBtnClicked(View view) {
        return false;
        //
    }

    public boolean onRightBtnClicked(View view) {
        return false;
        //
    }

    public boolean onTurnBtnClicked(View view) {
        return false;
        //
    }

    public boolean onDownBtnClicked(View view) {
        return false;
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


    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {


    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    private View myView;


        public MyGestureListener(View v){
            myView = v;
        }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        boolean result = false;
        try {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        result = onRightBtnClicked(myView);
                    } else {
                        result = onLeftBtnClicked(myView);
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            result = onDownBtnClicked(myView);
                        } else {
                            result = onTurnBtnClicked(myView);
                        }
                    }
                }
                }
            }catch(Exception exception){
                exception.printStackTrace();
            }
            return result;
        }
    }

}

