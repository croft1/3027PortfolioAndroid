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

    private Context context;
    private ArrayList<BasicReminder> basicReminders;
    private int count;  //for adding count at the bottom

    public BasicReminderItemAdapter(Context context, ArrayList<BasicReminder> basicReminders) {
        this.context = context;
        this.basicReminders = basicReminders;
    }

    public static class ViewHolder {
        TextView title;
        TextView description;
        TextView date;
        TextView complete;
    }

    public int getCount() {
        return basicReminders.size();
    }

    public BasicReminder getItem(int i) {
        return basicReminders.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.reminder_item_list, null);

            vh = new ViewHolder();
            vh.title = (TextView) view.findViewById(R.id.titleLabel);
            vh.description = (TextView) view.findViewById(R.id.descriptionLabel);
            vh.date = (TextView) view.findViewById(R.id.viewDate);
            vh.complete = (TextView) view.findViewById(R.id.completeText);

            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        String title = basicReminders.get(i).getTitle();
        String description = basicReminders.get(i).getDescription();
        Date dueDate = basicReminders.get(i).getDueDate();
        Boolean completed = basicReminders.get(i).isComplete();

        vh.title.setText(title);
        vh.description.setText(description);
        vh.date.setText(basicReminders.get(i).getDueDateString());
        if(completed){
            vh.complete.setText("Complete!");
            vh.complete.setTextColor(Color.GREEN);
        }else{
            vh.complete.setText("Not Completed!");
            vh.complete.setTextColor(Color.RED);
        }

        return view;
    }


}

