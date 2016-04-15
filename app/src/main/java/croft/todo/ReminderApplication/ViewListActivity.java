package croft.todo.ReminderApplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import croft.todo.R;

public class ViewListActivity extends AppCompatActivity {

    public static final int ADD_REMINDER_REQUEST = 0;
    public static final int MARK_COMPLETE_REQUEST = 1;

    private ListView reminderListView;
    private ToDoItemAdapter adapter;
    private static ArrayList<Reminder> reminders = new ArrayList<Reminder>();
    private CheckBox completeBox;
    private Toast toastHelper;
    private static boolean isSorted;
    //private Item or Menu sortButton;
    private static int selectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("W4 - To Do List");
        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.FAB);

        setContentView(R.layout.reminder_main_activity);

        reminderListView = (ListView) findViewById(R.id.todoList);
        completeBox = (CheckBox) findViewById(R.id.viewCompleteCheck);

        //sortButton = (Menu) findViewById(R.id.action_sort);


        adapter = new ToDoItemAdapter(this, reminders);
        reminderListView.setChoiceMode(reminderListView.CHOICE_MODE_SINGLE);
        reminderListView.setSelector(android.R.color.holo_blue_light);
        reminderListView.setAdapter(adapter);

        //updateIncompleteCount();

        reminderListView.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> av, View v, int i, long l) {
                    Reminder result = (Reminder)
                            reminderListView.getAdapter().getItem(i);
                    Intent intent = new Intent();
                    intent.putExtra("edit", result);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
    }

    public void onFABClick(View v) {
        Intent intent = new Intent(ViewListActivity.this, addReminderActivity.class);
        startActivityForResult(intent, ADD_REMINDER_REQUEST);
        //finish();             want to be able to return if user presses back
    }


    //when first building the options menu, this runs
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sort_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //runs when item created onto menu is selected.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                //go to addMonsterActivity and await result

                String sort;

                if (reminders.isEmpty()) {
                    toastHelper = Toast.makeText(getApplicationContext(), "Can't sort without reminders!", Toast.LENGTH_SHORT);
                    return false;
                }

                if (isSorted) {
                    Collections.reverse(reminders);
                    sort = "Descending order";
                } else {
                    Collections.sort(reminders);
                    isSorted = true;
                    sort = "Ascending order";
                }
                toastHelper = Toast.makeText(getApplicationContext(), sort, Toast.LENGTH_SHORT);
                toastHelper.show();

                //without notifyDataSetChanged, the screen only updates if list items are out of view...

                adapter.notifyDataSetChanged();     //tells the adapter i changed list, it needs change itself
                Intent update = new Intent(this, ViewListActivity.class);
                this.finish();
                startActivity(update);
                return true;

            case R.id.clear_reminders:
                //think this is the first option inside the 3 dot menu

                reminders.clear();
                Intent clear = new Intent(this, ViewListActivity.class);
                this.finish();
                startActivity(clear);
                return true;


            case R.id.add_dummy_data:
                populateDummyData(6);

                Intent i = new Intent(this, ViewListActivity.class);
                isSorted = false;
                this.finish();
                startActivity(i);
                return true;

            default:
                toastHelper = Toast.makeText(getApplicationContext(), "A Petty Failure", Toast.LENGTH_LONG);
                toastHelper.show();
        }
        return super.onOptionsItemSelected((item));
    }

    //after the activity that was run from pressing add finished, this runs
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ADD_REMINDER_REQUEST:
                if (resultCode == RESULT_OK) {

                    //updateIncompleteCount();
                    isSorted = false;

                    Reminder r = data.getParcelableExtra("reminder");
                    reminders.add(r);

                    Intent update = new Intent(this, ViewListActivity.class);

                    this.finish();
                    startActivity(update);
                }
                break;

            case MARK_COMPLETE_REQUEST:
                if (resultCode == RESULT_OK) {


                    Reminder r = data.getParcelableExtra("edit");   //breaks here if you hit back after editing reminder
                    reminders.add(r);


                    adapter.notifyDataSetChanged();
                    Intent update = new Intent(this, ViewListActivity.class);

                    this.finish();
                    startActivity(update);
                }
                break;
            default:
                toastHelper = Toast.makeText(getApplicationContext(), "FAILED", Toast.LENGTH_SHORT);

        }
    }

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

    public void populateDummyData(int size) {
        for (int i = 0; i < size; i++) {
            reminders.add(new Reminder((Integer.toString((i + 1000)))
                    , "almost there Our main activity needs to get the ListView instance from the inflated layout and then the best thing in the world is the grae t nasinf jkelna"
                    , ("02/01/" + (3000 + i)), true));

        }
    }

    /*
    protected void onListItemClicked(View v) {
       //super.onListItemClick(l, v, position, id);
          //  gets index of visible cells, not the actual index of the item in the whole list (screen may only show 7, can't get 20th item)

        //TODO get the item index of whole list, not just index of what's visible
        Reminder r = reminders.get(reminderListView.indexOfChild(v));
        Intent intent = new Intent(this, ViewDetailedReminderActivity.class);
        intent.putExtra("reminder", r);
        startActivityForResult(intent, MARK_COMPLETE_REQUEST);
        //finish();     should be able to press back to return. testing how
    }
    */
}
