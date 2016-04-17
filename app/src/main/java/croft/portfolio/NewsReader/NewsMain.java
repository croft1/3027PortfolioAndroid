package croft.portfolio.NewsReader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import croft.portfolio.NewsReader.models.Article;
import croft.portfolio.PersistentReminder.DatabaseHelper;
import croft.portfolio.R;

public class NewsMain extends AppCompatActivity {


    private NewsDbHelper dbHelper;
    private NewsAdapter adapter;
    private ArrayList<Article> articles;
    private ListView articleListView;

    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity_main);

        dbHelper = new NewsDbHelper(getApplicationContext());
        image = (ImageView) findViewById(R.id.imageFromUrl);

        new DownloadImageTask((ImageView) findViewById(R.id.imageFromUrl)).execute(
                "https://lh3.googleusercontent.com/gD0sQmzHmaG9m7excJhB3T0cyaqAjrhxU2elCuQ92IKBwY2SpfiSeS6Y3cQJasN83g=w300"
        );




    }



    private class SetupArticleDatasetTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls){

            return "";
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView iv;

        public DownloadImageTask(ImageView iv) {
            this.iv = iv;
        }
        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap image = null;
            try{
                InputStream in = new java.net.URL(urlDisplay).openStream();
                image = BitmapFactory.decodeStream(in);
            }catch(Exception e){
                Log.e("ERROR getting image", e.getMessage());
                e.printStackTrace();
            }
            return image;
        }

        protected void onPostExecute(Bitmap result){
            iv.setImageBitmap(result);
        }
    }
}
