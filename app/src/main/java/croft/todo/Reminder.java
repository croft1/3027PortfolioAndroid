package croft.todo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Michaels on 26/3/2016.
 */
public class Reminder {

    String title;
    String description;
    Date dueDate;
    boolean complete = false;
    public static int totalIncomplete = 0;

    public Reminder(String newTitle, String newDescription, String ddMMyyyy){

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


        totalIncomplete++;
    }

    public Reminder(String newTitle, String newDescription, Date date){
        setTitle(newTitle);
        setDescription(newDescription);
        setDueDate(date);
        totalIncomplete++;
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
    }



}
