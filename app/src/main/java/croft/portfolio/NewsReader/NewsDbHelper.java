package croft.portfolio.NewsReader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.LinkedHashMap;

import croft.portfolio.NewsReader.models.Article;

/**
 * Created by Michaels on 16/4/2016.
 */
public class NewsDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ArticleDB";
    public static final int DATABASE_VERSION = 1;


    public static final int ARTICLE_ID = 0;
    public static final int ARTICLE_HEADLINE = 1;
    public static final int ARTICLE_PUBLISHED = 2;
    public static final int ARTICLE_DIRECT_LINK = 3;
    public static final int ARTICLE_CATEGORY = 4;

    public NewsDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Article.TABLE_NAME);
        onCreate(db);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Article.CREATE_STATEMENT);
    }


    ////Article METHODS

    //If multiuser support was needed, we'd need to add in another layer: Look in user, then their Articles

    public void addArticle(Article article){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(article.COLUMN_HEADLINE, article.getHeadline());
        values.put(article.COLUMN_PUBLISHED, article.getPublishDateString());
        values.put(article.COLUMN_DIRECT_LINK, article.getDirectLink());
        values.put(article.COLUMN_CATEGORY, article.getCategory());

        db.insert(article.TABLE_NAME, null, values);
        db.close();

    }


    public HashMap<Long, Article> getAllArticles() {

        HashMap<Long, Article> Articles = new LinkedHashMap<Long, Article>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Article.TABLE_NAME, null);

        // Add monster to hash map - ID is key, object is value (where value can be objects, var etc)
        if (cursor.moveToFirst()) {

            do {

                Article r = new Article(
                        cursor.getLong(ARTICLE_ID),
                        cursor.getString(ARTICLE_HEADLINE),
                        cursor.getString(ARTICLE_PUBLISHED),
                        cursor.getString(ARTICLE_DIRECT_LINK),
                        cursor.getString(ARTICLE_CATEGORY)
                        );

                Articles.put(r.getId(), r);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();
        return Articles;
    }

    public void removeArticle(Article r){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Article.TABLE_NAME,                   //in TABLE
                Article.COLUMN_ID + " = ?",             //with this key
                new String[]{String.valueOf(r.getId())}  //delete this item
        );
        db.close();
    }

}


