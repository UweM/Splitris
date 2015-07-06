package tud.tk3.splitscreen;

import android.os.AsyncTask;

public abstract class OnMainThread implements Runnable {
    public OnMainThread() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                run();
            }
        }.execute();
    }
}
