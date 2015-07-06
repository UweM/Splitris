package tud.tk3.splitris;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

import tud.tk3.splitris.tetris.Game;
import tud.tk3.splitscreen.network.Client;
import tud.tk3.splitscreen.network.Server;
import tud.tk3.splitscreen.network.ServerConnection;
import tud.tk3.splitscreen.screen.BlockScreen;
import tud.tk3.splitscreen.output.ScreenView;
import tud.tk3.splitscreen.output.Viewport;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //client();
        server();
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

        new Thread() {
            @Override
            public void run() {
                try {
                    Client c = new Client();
                    c.registerView(0, (ScreenView)findViewById(R.id.splitscreen));

                    c.registerView(1, (ScreenView)findViewById(R.id.splitscreen2));
                    c.connect(5000, "192.168.178.36", 4562);
                    while(true) Thread.sleep(5000);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
    private void server() {

        final int width = 40;
        final int height = 20;
        final BlockScreen bs = new BlockScreen(30, width, height);
        bs.setOccupied(Color.BLUE);


        final Viewport mirror = new Viewport(bs, 0, 0, bs.getVirtualWidth(), bs.getVirtualHeight());
        final Viewport vp1 =new Viewport(bs, 0, 0, bs.getVirtualWidth()/2, bs.getVirtualHeight());
        final Viewport vp2 =new Viewport(bs, bs.getVirtualWidth()/2, 0, bs.getVirtualWidth()/2, bs.getVirtualHeight());

        vp1.addView((ScreenView) findViewById(R.id.splitscreen));
        mirror.addView((ScreenView) findViewById(R.id.splitscreen2));





        //vp.setView((ScreenView)findViewById(R.id.splitscreen2));

        new Thread() {
            @Override
            public void run() {


                Server s;
                try {
                    s = new Server(4562, "");
                    s.start();
                    s.addListener(new Listener() {
                        public void connected (Connection connection) {
                            ServerConnection s = (ServerConnection) connection;
                            vp2.addView(s.getRemoteView(0));
                            mirror.addView(s.getRemoteView(1));
                        }
                    });



                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                try {
                    Game tetris = new Game(bs, width, height);

                    boolean active = true;
                    while(true) {
                        for(int y=0;y<height;y++) {
                            for(int x=0;x<width;x++) {
                                //bs.setActive(x, y, active);
                                tetris.tick();
                                bs.render();
                                Thread.sleep(25);
                            }
                        }
                        active = !active;
                    }

                } catch (InterruptedException e) {
                    
                }
            }
        }.start();
    }
}
