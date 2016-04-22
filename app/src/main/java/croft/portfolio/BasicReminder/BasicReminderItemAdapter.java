package croft.portfolio.BasicReminder;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import croft.portfolio.BasicReminder.models.BasicReminder;
import croft.portfolio.R;

/**
 * Created by Michaels on 12/4/2016.
 */
public class BasicReminderItemAdapter extends BaseAdapter {

    //the adapter is used to create the cells and populate their UI elements (the text fields and so on)

    private Context context;        //we establish the context, or when the adapter is created, which activity/layout the adapter will be used for
    private ArrayList<BasicReminder> basicReminders;        //stores all reminders that will be used to populate the list
    private int count;  //for adding count at the bottom

    public BasicReminderItemAdapter(Context context, ArrayList<BasicReminder> basicReminders) {
        this.context = context; //constructor for adapter - simply setting up what is passes in on creation
        this.basicReminders = basicReminders;
    }

    public static class ViewHolder {        //creating the view holder which 'holds the view'. It is the temporary storage of a view while we set it up to plug into another view
        TextView title;
        TextView description;
        TextView date;
        TextView complete;
    }

    public int getCount() {
        return basicReminders.size();
    }       //needed to determine how many cells needed

    public BasicReminder getItem(int i) {
        return basicReminders.get(i);
    }       //gets the item to put its data into the cell

    public long getItemId(int i) {
        return i;
    }       //id

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
//check if we've already created the view, if it exists
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);      //layout inflater is used to populate the lists

            view = inflater.inflate(R.layout.reminder_item_list, null);     //we are using the layout file - item list - which contains the xml layout of how each cell is supposed to be formatted to we know what view will be inflated with what

            vh = new ViewHolder();
            vh.title = (TextView) view.findViewById(R.id.titleLabel);
            vh.description = (TextView) view.findViewById(R.id.descriptionLabel);
            vh.date = (TextView) view.findViewById(R.id.viewDate);
            vh.complete = (TextView) view.findViewById(R.id.completeText);      //view holder has a cel instance inside it and a reference to each of the UI elements - textView, buttons if they were there etc.

            view.setTag(vh);            //the tag is how we identify it when moving around different activities or reusing it
        } else {
            vh = (ViewHolder) view.getTag();            //we get the already made view by looking at its tag
        }

        String title = basicReminders.get(i).getTitle();
        String description = basicReminders.get(i).getDescription();
        Date dueDate = basicReminders.get(i).getDueDate();
        Boolean completed = basicReminders.get(i).isComplete();

        vh.title.setText(title);
        vh.description.setText(description);
        vh.date.setText(basicReminders.get(i).getDueDateString());      //and we assign its text values according to the currently focussed reminders details
        if(completed){
            vh.complete.setText("Complete!");
            vh.complete.setTextColor(Color.GREEN);          //simple text color assignment
        }else{
            vh.complete.setText("Not Completed!");
            vh.complete.setTextColor(Color.RED);
        }

        return view;        //overall we return the view to wherever requires it
    }


}

