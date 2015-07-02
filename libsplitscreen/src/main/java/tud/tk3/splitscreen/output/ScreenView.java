package tud.tk3.splitscreen.output;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;

public class ScreenView extends View implements IScreenView {
    private Bitmap mBitmap;
    private Paint mPaint = new Paint();

    public ScreenView(Context context) {
        super(context);
    }
    public ScreenView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ScreenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setBitmap(final byte[] bytes) {
        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... params) {
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                mBitmap = result;
                invalidate();
            }
        }.execute();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if(mBitmap != null) {
            canvas.scale((float)getWidth() / (float)mBitmap.getWidth(), (float)getHeight() / (float)mBitmap.getHeight());
            canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        }
    }
}
