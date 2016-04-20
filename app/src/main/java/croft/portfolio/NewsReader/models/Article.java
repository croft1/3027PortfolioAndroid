package croft.portfolio.NewsReader.models;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Michaels on 17/4/2016.
 */
public class Article implements Comparable<Article>, Serializable{
//SQL focussed constants
    public static final String TABLE_NAME = "Articles";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_HEADLINE = "headline";
    public static final String COLUMN_CREATOR = "creator";
    public static final String COLUMN_PUBLISHED = "published";
    public static final String COLUMN_DIRECT_LINK = "article_link";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_ICON_LINK = "icon_link";
    public static final String CREATE_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_HEADLINE + " TEXT NOT NULL, " +
                    COLUMN_CREATOR + " TEXT NOT NULL, " +
                    COLUMN_PUBLISHED + " TEXT NOT NULL, " +
                    COLUMN_DIRECT_LINK + " TEXT NOT NULL, " +
                    COLUMN_CATEGORY + " TEXT NOT NULL, " +
                    COLUMN_ICON_LINK + " TEXT" +                    //can try work on storing images in sql to get thsi going
                    ")"
            ;

    public static final String DATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss Z";        //eg. Sun, 17 Apr 2016 15:02:58 +1000

    private static long assignId = 999999;
    private long id;
    private String headline;
    private Date publishDate;
    private String directLink;
    private String category;        //if multiple categories we'll append them together
    private String creator;
    private String imageLink;

    public Article() {
        setId(1);
        setHeadline("Undefined");
        setPublishDate(stringToDate("Wed, 30 Mar 2016 16:20:00 +1000"));
        setCategory("Undefined");
        setDirectLink("http://www.google.com");
    }
    //may need to vararg Category to cope with multiple cats
    public Article(Long id, String headline, String creator, String publishDate, String Category, String directLink, String iconLink)   {




        if(id == null){
            setId(assignId++);          //random default id just in case
        }else{
            setId(id);
        }

        setHeadline(headline);
        if(creator == null){
            setCreator("ABC News");
        }else {
            setCreator(creator);            //sometimes is null
        }

        setPublishDate(
                stringToDate(publishDate));     //it is assumed abc will always have the same date format
        setCategory(Category);
        setDirectLink(directLink);
        setIconLink(iconLink);
    }
/*
    public Article(Parcel in){

        icon = in.readString();
        id = in.readLong();
        headline = in.readString();
        creator = in.readString();
        publishDate = new Date(in.readLong());
        category = in.readString();
        directLink = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(icon);
        dest.writeLong(id);
        dest.writeString(headline);
        dest.writeString(creator);
        dest.writeLong(publishDate.getTime());
        dest.writeString(category);
        dest.writeString(directLink);
    }

    /*
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

    @Override
    public int describeContents() {
        return 0;
    }
*/


    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
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

    public void setIconLink(String link){ this.imageLink = link; }

    public String getIconLink(){return imageLink;}

    @TargetApi(19)
    @Override
    public int compareTo(Article another) {
        if (getPublishDate() == null || another.getPublishDate() == null)
            return 0;
        return getPublishDate().compareTo(another.getPublishDate());
    }



    @Override
    public String toString() {
        return "ID: " + getId() +
                "creator" + getCreator() +
                "headline" + getHeadline() +
                "publishDate" + getPublishDate() +
                "category" + getCategory() +
                "directLink" + getDirectLink() ;
    }

    private Date stringToDate(String date){
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        Date d;
        try {
            d = df.parse(date);
            setPublishDate(d);
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.println("Article date input was in the wrong format");

            d = new Date((long) 0);
        }
        return d;
    }
}
