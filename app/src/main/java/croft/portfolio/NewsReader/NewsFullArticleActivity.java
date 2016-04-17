package croft.portfolio.NewsReader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import croft.portfolio.R;

public class NewsFullArticleActivity extends AppCompatActivity {


    private WebView webView;
    private static final String DEFAULT_PAGE_URL = "http://www.google.com";// file:///android_asset/web_view.html

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity_article);

        setTitle("Viewing article: " + "");

        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());


        webView.loadUrl(DEFAULT_PAGE_URL);

        webView.getSettings().setJavaScriptEnabled(true);


        //get intent with request url from list objects data

    }
}
