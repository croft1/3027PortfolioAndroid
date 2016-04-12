package croft.todo.ReminderApplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import croft.todo.R;

/**
 * Created by Michaels on 12/4/2016.
 */
public class ToDoItemdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Reminder> reminders;



    public ToDoItemdapter(Context context, ArrayList<Reminder> reminders) {
        this.context = context;
        this.reminders = reminders;
    }

    public static class ViewHolder {
        TextView title;
        TextView description;
        TextView date;
        CheckBox completed;
    }

    public int getCount() {
        return reminders.size();
    }

    public Reminder getItem(int i) {
        return reminders.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.list_view_item, null);

            vh = new ViewHolder();
            vh.title = (TextView) view.findViewById(R.id.viewTitle);
            vh.description = (TextView) view.findViewById(R.id.viewDescription);
            vh.date = (TextView) view.findViewById(R.id.viewDate);
            vh.completed = (CheckBox) view.findViewById(R.id.viewCompleteCheck);

            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        String title = reminders.get(i).getTitle();
        String description = reminders.get(i).getDescription();
        Date dueDate = reminders.get(i).getDueDate();
        Boolean completed = reminders.get(i).isComplete();

        vh.title.setText(title);
        vh.description.setText(description);
        vh.date.setText(reminders.get(i).getDueDateString());
        vh.completed.setChecked(completed);

        //if(reminders.get(i).getDueDate() )    //TODO compare dates
        //  vh.date.setTextColor(Color.parseColor("#00FF00"));


        return view;
    }
}

