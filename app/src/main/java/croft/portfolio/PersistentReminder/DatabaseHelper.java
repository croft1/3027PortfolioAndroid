package croft.portfolio.PersistentReminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import croft.portfolio.MonsterParty.models.Monster;
import croft.portfolio.PersistentReminder.models.Reminder;

/**
 * Created by Michaels on 16/4/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "ReminderDB";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Reminder.TABLE_NAME);
        onCreate(db);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Reminder.CREATE_STATEMENT);
    }


    ////REMINDER METHODS

    //If multiuser support was needed, we'd need to add in another layer: Look in user, then their reminders

    public void addReminder(Reminder reminder){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Reminder.COLUMN_TITLE, reminder.getTitle());
        values.put(Reminder.COLUMN_DESCRIPTION, reminder.getDescription());
        values.put(Reminder.COLUMN_DUEDATE, reminder.getDueDateString());
        values.put(Reminder.COLUMN_COMPLETE, reminder.isComplete());
        db.insert(Reminder.TABLE_NAME, null, values);
        db.close();

    }


    public HashMap<Long, Reminder> getAllReminders() {

        HashMap<Long, Reminder> reminders = new LinkedHashMap<Long, Reminder>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Reminder.TABLE_NAME, null);

        // Add monster to hash map - ID is key, object is value (where value can be objects, var etc)
        if (cursor.moveToFirst()) {

            do {
                boolean retrieved = false;
                if(cursor.getInt(4) == 0)
                    retrieved = true;

                Reminder r = new Reminder(cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        retrieved);

                reminders.put(r.getId(), r);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return reminders;
    }

    public void removeReminder(Reminder r){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Reminder.TABLE_NAME,                   //in TABLE
                Reminder.COLUMN_ID + " = ?",             //with this key
                new String[]{String.valueOf(r.getId())}  //delete this item
        );
        db.close();
    }

}
