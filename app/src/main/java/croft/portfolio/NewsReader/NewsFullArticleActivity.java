package croft.portfolio.NewsReader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import croft.portfolio.R;

public class NewsFullArticleActivity extends AppCompatActivity {


    private WebView webView;
    private static final String DEFAULT_PAGE_URL = "http://www.abc.net.au";// file:///android_asset/web_view.html
    private String requestedURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity_article);

        setTitle("Viewing article: " + "");

        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());

        //get intent with request url from list objects data
        Intent i = getIntent();
        requestedURL = i.getParcelableExtra(NewsMain.REQUEST_ARTICLE_URL);


        //simply show the web page of item clicked in full inside the app
        if(requestedURL == null){
            webView.loadUrl(DEFAULT_PAGE_URL);
        }else{
            webView.loadUrl(requestedURL);
        }

        webView.getSettings().setJavaScriptEnabled(true);

    }
}
