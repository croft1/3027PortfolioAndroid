package croft.portfolio;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Michaels on 19/4/2016.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap>  {


    ImageView iv;


    public DownloadImageTask(ImageView iv) {
        this.iv = iv;
    }

    public DownloadImageTask(){}

    protected Bitmap doInBackground(String... urls) {
        String urlDisplay = urls[0];
        Bitmap image = null;
        try {
            InputStream in = new java.net.URL(urlDisplay).openStream();
            image = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("ERROR getting image", e.getMessage());
            e.printStackTrace();
        }
        return image;
    }

    protected void onPostExecute(Bitmap result) {
        iv.setImageBitmap(result);
    }
}

