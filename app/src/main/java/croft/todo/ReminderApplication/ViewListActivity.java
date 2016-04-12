package croft.todo.ReminderApplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import croft.todo.R;

public class ViewListActivity extends AppCompatActivity {
    public static final int ADD_REMINDER_REQUEST = 0;

    private ListView reminderList;
    private ToDoItemdapter  adapter;
    private ArrayList<Reminder> reminders;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("W4 - To Do List");
        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.FAB);

        setContentView(R.layout.activity_view_list);

        reminderList = (ListView) findViewById(R.id.todoList);

        reminders = new ArrayList<Reminder>();

        populateDummyData();

        adapter = new ToDoItemdapter(this, reminders);

        reminderList.setAdapter(adapter);

        updateIncompleteCount();

    }

    public void onFABClick(View v){
        Intent i = new Intent(ViewListActivity.this, addReminderActivity.class);
        startActivity(i);
        //finish();             want to be able to return if user presses back
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_REMINDER_REQUEST){
            if(resultCode == RESULT_OK){
                Reminder r = data.getParcelableExtra("result");
                reminders.add(r);

                adapter.notifyDataSetChanged();
                updateIncompleteCount();
            }
        }
    }

    private void updateIncompleteCount(){
        int total = Reminder.totalIncomplete;
        TextView incompleteText = (TextView) findViewById(R.id.incompleteCount);
        incompleteText.setText("Incomplete Reminders: " + total);
    }


    public void populateDummyData() {


        Reminder s = new Reminder("First", "To finish this assignment", "12/11/1888");
        Reminder t = new Reminder("Second", "almost there Our main activity needs to get the ListView instance from the inflated layout and then the best thing in the world is the grae t nasinf jkelna" , "02/01/3632");

        reminders.add(s);
        reminders.add(t);
    }
}
