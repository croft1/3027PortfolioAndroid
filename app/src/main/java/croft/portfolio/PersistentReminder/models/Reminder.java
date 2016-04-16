package croft.portfolio.PersistentReminder.models;

import android.annotation.TargetApi;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Michaels on 26/3/2016.
 */
public class Reminder implements Parcelable, Comparable<Reminder>{

    public static final String TABLE_NAME = "reminders";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DUEDATE = "duedate";
    public static final String COLUMN_COMPLETE = "complete";

    public static final String CREATE_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_TITLE + " TEXT NOT NULL, " +
                    COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                    COLUMN_DUEDATE + " INTEGER NOT NULL, " +
                    COLUMN_COMPLETE + " INTEGER NOT NULL" +     //boolean are int, 1 true 0 false
                    ")";


    private static long generated_id;
    private long id;
    private String title;
    private String description;
    private Date dueDate;
    private boolean complete = false;
    public static int totalIncomplete = 0;

    @TargetApi(19)      //not available to target API 14 we're running
    public int compareTo(Reminder other){
        if (getDueDate() == null || other.getDueDate() == null)
            return 0;
        return getDueDate().compareTo(other.getDueDate());
    }

    //default
    public Reminder(){
        setTitle("Undefined");
        setDescription("Undefined");
        setDueDate(new Date((long) 0));
        generated_id++;
    }

    //was gonna leave bool out of constructor. When editing values on detailed activity, recreating and storing objects couldnt have compelte changed. It was pointless to have another identical constructor with just the bool added in
    //for adding previously existing, from db, dummy etc
    public Reminder(long id, String newTitle, String newDescription, String ddMMyyyy, boolean complete){
        setId(id);
        setDueDate(stringToDate(ddMMyyyy));
        setTitle(newTitle);
        setDescription(newDescription);
        setComplete(complete);
        generated_id++;
    }

    //for adding new ones
    public Reminder(String newTitle, String newDescription, String ddMMyyyy, boolean complete){
        setId(generated_id++);
        setDueDate(stringToDate(ddMMyyyy));
        setTitle(newTitle);
        setDescription(newDescription);
        setComplete(complete);

    }



    public Reminder(Parcel in){
        id = in.readLong();
        title = in.readString();
        description = in.readString();
        dueDate = new Date(in.readLong());
        complete = in.readByte() != 0;
        generated_id++;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeLong(dueDate.getTime());
        parcel.writeByte( (byte) (complete ? 1 : 0));
    }



    private Date stringToDate(String ddMMyyyy){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date d;
        try {
            d = df.parse(ddMMyyyy);
            setDueDate(d);
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.println("Reminder date input was in the wrong format");

            d = new Date((long) 0);
        }
        return d;
    }


    public static final Creator<Reminder> CREATOR = new Creator<Reminder>() {
        @Override
        public Reminder createFromParcel(Parcel in) {
            return new Reminder(in);
        }
        @Override
        public Reminder[] newArray(int size) {
            return new Reminder[size];
        }
    };

    // used to describe special objects, not modified very often
    @Override
    public int describeContents() {
        return 0;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getDueDateString(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(dueDate);
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
        if(complete){
            totalIncomplete --;
        }else{
            totalIncomplete ++;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String toString(){

        return title + " is due on " + getDueDateString() + " and involves: " + description;
    }



}
