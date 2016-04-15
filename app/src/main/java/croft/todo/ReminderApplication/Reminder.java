package croft.todo.ReminderApplication;

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



    String title;
    String description;
    Date dueDate;
    boolean complete = false;
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
    }

    //was gonna leave bool out of constructor. When editing values on detailed activity, recreating and storing objects couldnt have compelte changed. It was pointless to have another identical constructor with just the bool added in
    public Reminder(String newTitle, String newDescription, String ddMMyyyy, boolean complete){

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date d = null;
        try {
            d = df.parse(ddMMyyyy);
            setDueDate(d);
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.println("Reminder date input was in the wrong format");

            d = new Date((long) 0);
        }

        setTitle(newTitle);
        setDescription(newDescription);
        setComplete(complete);


    }

    public Reminder(String newTitle, String newDescription, Date date, Boolean completed){
        setTitle(newTitle);
        setDescription(newDescription);
        setDueDate(date);
        setComplete(completed);

    }

    public Reminder(Parcel in){
        title = in.readString();
        description = in.readString();
        dueDate = new Date(in.readLong());
        complete = in.readByte() != 0;

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

    // used to describe special objects, not modified bery often
    @Override
    public int describeContents() {
        return 0;
    }

    // outputs the format the parcel writes values
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeLong(dueDate.getTime());
        parcel.writeByte( (byte) (complete ? 1 : 0));
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

    public String toString(){

        return title + " is due on " + getDueDateString() + " and involves: " + description;
    }



}
