package croft.todo.ReminderApplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import croft.todo.R;
import croft.todo.ViewDetailedReminderActivity;

public class ViewListActivity extends AppCompatActivity {
    public static final int ADD_REMINDER_REQUEST = 0;

    private ListView reminderList;
    private ToDoItemdapter adapter;
    private static ArrayList<Reminder> reminders;
    private CheckBox completeBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("W4 - To Do List");
        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.FAB);

        setContentView(R.layout.activity_view_list);

        reminderList = (ListView) findViewById(R.id.todoList);
        completeBox = (CheckBox) findViewById(R.id.viewCompleteCheck);

        reminders = new ArrayList<Reminder>();

        populateDummyData();

        adapter = new ToDoItemdapter(this, reminders);

        reminderList.setAdapter(adapter);

        //updateIncompleteCount();

    }

    public void onFABClick(View v) {
        Intent i = new Intent(ViewListActivity.this, addReminderActivity.class);
        startActivity(i);
        //finish();             want to be able to return if user presses back
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_REMINDER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Reminder r = data.getParcelableExtra("result");
                reminders.add(r);

                adapter.notifyDataSetChanged();
                //updateIncompleteCount();
            }
        }
    }


    /*public void onClick(View v) {
        Reminder selected = reminders.get(reminderList.indexOfChild(v));
        if (v.isSelected()) {
            Reminder.totalIncomplete++;
        } else {
            Reminder.totalIncomplete--;
        }
        selected.setComplete(v.isSelected());
        //updateIncompleteCount();


    }*/

    public void doAlertDialog(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title).setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setCancelable(false);
        builder.create().show();
    }

    /* public void updateIncompleteCount(){
         setTitle("Incomplete Reminders: " + Reminder.totalIncomplete);
     }//TODO isssues with setting onClick to checkbox in list

 */
    public void populateDummyData() {

        for (int i = 0; i < 3; i++) {

            Reminder t = new Reminder(Integer.toString(i), "almost there Our main activity needs to get the ListView instance from the inflated layout and then the best thing in the world is the grae t nasinf jkelna", ("02/01/" + (3000 + i)));
            reminders.add(t);
        }

    }

    public ArrayList<Reminder> getReminderList() {
        return reminders;
    }

    public void listItemClicked(View v){
        Reminder reminder = reminders.get(reminderList.indexOfChild(v));
        Intent i = new Intent(this, ViewDetailedReminderActivity.class);
        i.putExtra("reminder", reminder);
        startActivity(i);
    }

    @Override
    public void onRestart(){
        super.onRestart();


    }

}
