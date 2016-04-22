package croft.portfolio.NewsReader;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import croft.portfolio.NewsReader.models.Article;
import croft.portfolio.R;



public class NewsMain extends AppCompatActivity {

    public static final String REQUEST_ARTICLE_URL = "article_url_request";

    public static final String ARTICLE_XML_FEED_URL = "http://www.abc.net.au/news/feed/51120/rss.xml";
    public static final String ARTICLE_FEED_URL = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20feed%20where%20url=%27www.abc.net.au%2Fnews%2Ffeed%2F51120%2Frss.xml%27&format=json";

    private static final String ITEM_TAG = "item";
    private static final String HEADLINE_TAG = "title";
    private static final String PUBLISH_DATE_TAG = "pubDate";
    private static final String CATEGORY_TAG = "category";
    private static final String CREATOR_TAG = "creator";        //sometimes can be null
    private static final String IMAGE_TAG = "thumbnail";
    private static final String LINK_TAG = "link";

    //private NewsDbHelper dbHelper;
    private NewsAdapter adapter;
    private ArrayList<Article> articles = new ArrayList<>();
    private ListView articleListView;


    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity_main);
        setTitle("ABC news");
        //dbHelper = new NewsDbHelper(getApplicationContext());

        /*if (dbHelper.getAllArticles().size() != 0) {

            ArrayList<Article> convertList = new ArrayList<>(dbHelper.getAllArticles().values());
            articles = convertList;
        }*/

        //new SetupArticleDatasetTaskFromXML().execute(ARTICLE_XML_FEED_LOCATION);


        articleListView = (ListView) findViewById(R.id.articleListView);

        new SetupArticleDatasetTaskJSON().execute(ARTICLE_FEED_URL);

        adapter = new NewsAdapter(this, articles);

        System.out.println(adapter.getCount());

        articleListView.setChoiceMode(articleListView.CHOICE_MODE_SINGLE);
        articleListView.setSelector(android.R.color.darker_gray);

        articleListView.setAdapter(adapter);


        //adapter.notifyDataSetChanged();

        articleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {

                Article result = (Article) articleListView.getAdapter().getItem(pos);
                String url = result.getDirectLink();
                Intent intent = new Intent(NewsMain.this, NewsFullArticleActivity.class);

                //pass through url to article to open
                intent.putExtra(REQUEST_ARTICLE_URL, url);
                startActivity(intent);

            }
        });


    }

    private class SetupArticleDatasetTaskJSON extends AsyncTask<String, Void, String> {

        ProgressBar progressBar;
        JSONObject jsonObject;

        @Override
        protected String doInBackground(String... urls) {
            //android.os.Debug.waitForDebugger();
            try {

                URL downloadURL = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) downloadURL.openConnection();
                InputStream input = connection.getInputStream();

                //GEt json into a string
                String result = "";
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder sb = new StringBuilder();

                System.out.println("Fetching results");
                while ((result = reader.readLine()) != null) {
                    sb.append(result);
                }
                System.out.println("fetch complete");


                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setIndeterminate(true);


        }

        @Override
        protected void onCancelled() {
            super.onCancelled();


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {
                try {

                    // reference:  got confused with terminology and the logic flow http://stackoverflow.com/questions/9787461/get-string-from-json-with-nested-json-object-and-nested-json-arrays-with-multipl
                    //take the entire string and put it in a JSON {object}
                    jsonObject = new JSONObject(s.toString());

                    //move inward to the results of the json in order

                    jsonObject = (JSONObject) jsonObject.get("query");
                    int count = jsonObject.getInt("count");
                    String created = jsonObject.getString("created");
                    String language = jsonObject.getString("lang");
                    jsonObject = (JSONObject) jsonObject.get("results");

                    //when json array is named "item", take the array of articles from the json files that have all the corresponding {objects}
                    JSONArray organisedJsonArticle = jsonObject.getJSONArray("item");



                    for (int i = 1; i < count; i++) {       //TODO change to i < count when testing done

                        jsonObject = organisedJsonArticle.getJSONObject(i);   //select to i'th object. This can then get information extracted from it

                        String headline = jsonObject.getString(HEADLINE_TAG);    //going through each item in the json
                        String link = jsonObject.getString(LINK_TAG);
                        String creator = "ABC News";

                        if(!jsonObject.isNull(CREATOR_TAG)){            //article doesn't always have a creator
                            creator = jsonObject.getString(CREATOR_TAG);
                        }

                        String pubDate = jsonObject.getString(PUBLISH_DATE_TAG);

                        String categories;
                        JSONArray jArray;

                        try {
                            jArray = jsonObject.getJSONArray(CATEGORY_TAG);   //get the array of categories
                            categories = "";

                            for (int counter = 0; counter < jArray.length(); counter++) {     //if there is no category object (a singular category)
                                categories += jArray.getString(counter);
                                categories += ", ";

                            }
                        } catch (Exception e) {
                            categories = jsonObject.getString(CATEGORY_TAG);
                        }
                        String thumbURL;
                        if(jsonObject.has("group")){
                            JSONObject jsonGroup = jsonObject.getJSONObject("group");
                            JSONObject jsonThumbnail = jsonGroup.getJSONObject(IMAGE_TAG);
                            thumbURL = jsonThumbnail.getString("url");
                        }else{
                            thumbURL = "http://www.abc.net.au/homepage/2013/styles/img/abc.png";        //set default
                        }

                        //overridden as this one is cleaner, dont really care for it - not using it though.




                        //to process the bitmap for the constructor, porobably should do it after and have the link stored in the object

                        //create object
                        long id = (long) i;

                        Article extractedArticle = new Article(id,
                                headline,
                                creator,
                                pubDate,
                                categories,
                                link,
                                thumbURL
                        );
                        if((i % 50) == 0){
                            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@ "  + extractedArticle.toString() );
                        }

                        articles.add(extractedArticle);
                        //dbHelper.addArticle(extractedArticle);      //saving the bitmap probably won't work
                    }



                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                progressBar.setVisibility(View.GONE);
                articleListView.setVisibility(View.VISIBLE);

                adapter.notifyDataSetChanged();

            }
        }

        private class SetupArticleDatasetTaskFromXML extends AsyncTask<String, Void, String> {

            private final String ns = null;      //namespace used

            //tags found in the abc rss xml feed
       /* private final String ITEM_TAG = "item";
        private final String HEADLINE_TAG = "title";
        private final String PUBLISHDATE_TAG = "pubDate";
        private final String CATEGORY_TAG = "category";
        private final String CREATOR_TAG = "dc:creator";        //sometimes can be null
        private final String IMAGE_TAG = "media:thumbnail";
        private final String LINK_TAG = "link"; */

            protected String doInBackground(String... urls) {
                try {
                    URL downloadUrl = new URL(ARTICLE_FEED_URL);

                    HttpURLConnection connection = (HttpURLConnection) downloadUrl.openConnection();
                    InputStream input = connection.getInputStream();

                    //FOR JSON
                    String result = "";
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(input));
                    StringBuilder builder = new StringBuilder();

                    //iterate through json and get the data with tags etc.
                    while ((result = reader.readLine()) != null) {
                        builder.append(result);
                    }

                    //FOR STRAIGHT XML

                    articles = (ArrayList<Article>) parseXml(input);

                    for (int i = 0; i < articles.size(); i++) {
                       // dbHelper.addArticle(articles.get(i));
                    }


                    return builder.toString();

                    //process each line
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } finally {

                }


                return null;
            }

            protected void onPostExecute(String result) {

                if (result != null) {
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            //http://developer.android.com/training/basics/network-ops/xml.html#instantiate

            public List parseXml(InputStream in) throws XmlPullParserException, IOException {


                try {
                    XmlPullParser parser = Xml.newPullParser();

                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(in, null);
                    parser.nextTag();
                    return readFeed(parser);
                } finally {
                    in.close();
                }
            }

            private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
                List entries = new ArrayList();

                parser.require(XmlPullParser.START_TAG, ns, "");        //start from the start

                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {       //until the end tag and not the start, go
                        continue;
                    }
                    String name = parser.getName();

                    if (name.equals(ITEM_TAG)) {        //get the item tag, and fetch its nested tag: that's the info we want
                        entries.add(readArticle(parser));
                    } else {
                        skip(parser);
                    }
                }
                return entries;
            }

            //create objects out of data retrived from xml
            private Article readArticle(XmlPullParser parser) throws XmlPullParserException, IOException {
                parser.require(XmlPullParser.START_TAG, ns, "item");       //look for item tag after start tag is seen

                String headline = null;
                String publishDate = null;     //it is expected the date will be the same format all the time
                String directLink = null;
                String category = "";
                String creator = null;
                String imageLink = null;

                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    String elementName = parser.getName();

                    //switch and strings are weird
                    if (elementName.equals(HEADLINE_TAG)) {
                        headline = readSimpleText(parser, HEADLINE_TAG);
                    } else if (elementName.equals(LINK_TAG)) {
                        directLink = readSimpleText(parser, LINK_TAG);
                    } else if (elementName.equals(CREATOR_TAG)) {
                        creator = readSimpleText(parser, CATEGORY_TAG);
                    } else if (elementName.equals(PUBLISH_DATE_TAG)) {
                        publishDate = readSimpleText(parser, PUBLISH_DATE_TAG);
                    } else if (elementName.equals(CATEGORY_TAG)) {
                        category = category + " " + readSimpleText(parser, CATEGORY_TAG);         //allow for multiple categories
                    } else if (elementName.equals(IMAGE_TAG)) {
                        directLink = readSimpleText(parser, IMAGE_TAG);
                    } else {
                        skip(parser);       //whatever tag didn't match up, we skip it
                    }
                }

                return null;//new Article(null, headline, creator, publishDate, category, directLink, imageLink);
            }

            //called when building object, parser remains in position it was
            private String readSimpleText(XmlPullParser parser, String tagName) throws XmlPullParserException, IOException {
                parser.require(XmlPullParser.START_TAG, ns, tagName);       //find start tag of part we are after
                String simpleText = readText(parser);        //extract it
                parser.require(XmlPullParser.END_TAG, ns, tagName);     //ensures right format + we found end of text we want
                return simpleText;
            }


            //called to fetch text item as a String
            private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
                String result = "";
                if (parser.next() == XmlPullParser.TEXT) {
                    result = parser.getText();
                    parser.nextTag();           //when the text we want is found, move the position of parser to next
                }
                return result;
            }

            //when a tag doesn't match the ones i want, it mvoes the parser over it
            private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
                if (parser.getEventType() != XmlPullParser.START_TAG) { //kills it if next event isnt start tag
                    throw new IllegalStateException();
                }

                int depth = 1;      //how many nested tags we've ventured into so we match up with correct end tag. ie. avoid stopping at <list> <name> </name>
                while (depth != 0) {
                    switch (parser.next()) {
                        case XmlPullParser.END_TAG:
                            depth--;
                            break;
                        case XmlPullParser.START_TAG:
                            depth++;
                            break;
                    }
                }
            }
        }





    }
}
