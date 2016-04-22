package croft.portfolio.BasicReminder.models;

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
public class BasicReminder implements Parcelable, Comparable<BasicReminder>{



    String title;
    String description;
    Date dueDate;
    boolean complete = false;
    public static int totalIncomplete = 0;

    @TargetApi(19)      //not available to target API 14 we're running
    public int compareTo(BasicReminder other){

        //this is used to take 2 objects, their date data compare and reshuffle their position
        if (getDueDate() == null || other.getDueDate() == null)
            return 0;
        return getDueDate().compareTo(other.getDueDate());
    }

    //default

    //default constructor
    public BasicReminder(){
        setTitle("Undefined");
        setDescription("Undefined");
        setDueDate(new Date((long) 0));
    }

    //was gonna leave bool out of constructor. When editing values on detailed activity, recreating and storing objects couldnt have compelte changed. It was pointless to have another identical constructor with just the bool added in

    //
    public BasicReminder(String newTitle, String newDescription, String ddMMyyyy, boolean complete){

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date d = null;

        //try catch to ensure that the string input is of a valid format
        try {
            d = df.parse(ddMMyyyy);
            setDueDate(d);
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.println("BasicReminder date input was in the wrong format");

            d = new Date((long) 0); ///when theres an error in the date, you set it to 0, which i s0 from epoch
        }

        //now we simple set the variables to retrieve later
        setTitle(newTitle);
        setDescription(newDescription);
        setComplete(complete);


    }

    public BasicReminder(String newTitle, String newDescription, Date date, Boolean completed){
        setTitle(newTitle);
        setDescription(newDescription);
        setDueDate(date);
        setComplete(completed);

    }

    public BasicReminder(Parcel in){
        title = in.readString();
        description = in.readString();
        dueDate = new Date(in.readLong());
        complete = in.readByte() != 0;

    }

        //creator is used as a parcelable method that condesnses and expands the data of each reminder through their obects
    public static final Creator<BasicReminder> CREATOR = new Creator<BasicReminder>() {


        @Override
        public BasicReminder createFromParcel(Parcel in) {
            return new BasicReminder(in);
        }
        @Override
        public BasicReminder[] newArray(int size) {
            return new BasicReminder[size];
        }
    };

    // used to describe special objects, not modified bery often
    @Override
    public int describeContents() {
        return 0;
    }

    // outputs the format the parcel writes values
    @Override
    public void writeToParcel(Parcel parcel, int flags) {       //need to be in  same order of reading and writing
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

    public void setComplete(Boolean complete) {

        //flips the boolean since this is only ever called when we detect a change, not everytime we save an edit
        this.complete = !this.complete;

        //cout objects, to display when you show counting

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
