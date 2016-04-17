package croft.portfolio.NewsReader.models;

import android.annotation.TargetApi;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Michaels on 17/4/2016.
 */
public class Article implements Parcelable, Comparable<Article>{
//SQL focussed constants
    public static final String TABLE_NAME = "Articles";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_HEADLINE = "headline";
    public static final String COLUMN_PUBLISHED = "published";
    public static final String COLUMN_DIRECT_LINK = "duedate";
    public static final String COLUMN_CATEGORY = "complete";
    public static final String CREATE_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_HEADLINE + " TEXT NOT NULL, " +
                    COLUMN_PUBLISHED + " TEXT NOT NULL, " +
                    COLUMN_DIRECT_LINK + " TEXT NOT NULL, " +
                    COLUMN_CATEGORY + " TEXT NOT NULL" +
                    ")"
            ;

    public static final String DATE_FORMAT = "ddd, d MMM yyyy HH:mm:ss Z";        //eg. Sun, 17 Apr 2016 15:02:58 +1000

    private long id;
    private String headline;
    private Date publishDate;
    private String directLink;
    private String category;        //if multiple categories we'll append them together


    public Article() {
        setId(1);
        setHeadline("Undefined");
        setPublishDate(stringToDate("Wed, 30 Mar 2016 16:20:00 +1000"));
        setCategory("Undefined");
        setDirectLink("http://www.google.com");
    }
    //may need to vararg Category to cope with multiple cats
    public Article(long id, String headline, String publishDate, String Category, String directLink)   {
        setId(id);
        setHeadline(headline);
        setPublishDate(stringToDate(publishDate));
        setCategory(Category);
        setDirectLink(directLink);

    }

    public Article(Parcel in){
        id = in.readLong();
        headline = in.readString();
        publishDate = new Date(in.readLong());
        category = in.readString();
        directLink = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(headline);
        dest.writeLong(publishDate.getTime());
        dest.writeString(category);
        dest.writeString(directLink);
    }




    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getPublishDateString(){
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        return df.format(publishDate);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDirectLink() {
        return directLink;
    }

    public void setDirectLink(String directLink) {
        this.directLink = directLink;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }
        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    @TargetApi(19)
    @Override
    public int compareTo(Article another) {
        if (getPublishDate() == null || another.getPublishDate() == null)
            return 0;
        return getPublishDate().compareTo(another.getPublishDate());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    private Date stringToDate(String ddMMyyyy){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date d;
        try {
            d = df.parse(ddMMyyyy);
            setPublishDate(d);
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.println("Article date input was in the wrong format");

            d = new Date((long) 0);
        }
        return d;
    }
}
