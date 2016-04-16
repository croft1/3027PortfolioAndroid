package croft.portfolio.PersistentReminder;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import croft.portfolio.PersistentReminder.models.Reminder;
import croft.portfolio.R;

public class MainViewListActivity extends AppCompatActivity {

    public static final int ADD_REMINDER_REQUEST = 0;
    public static final int MARK_COMPLETE_REQUEST = 1;

    private ListView reminderListView;
    private ReminderAdapter adapter;
    private static ArrayList<Reminder> reminders = new ArrayList<Reminder>();
    private Toast toastHelper;
    private static boolean isSorted;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_main_activity);
        setTitle("W5 - Persistent Reminder + SQL");

        dbHelper = new DatabaseHelper((getApplicationContext()));

        if (dbHelper.getAllReminders().size() == 0){
            populateDummyData(4);
        }

        reminderListView = (ListView) findViewById(R.id.reminderList);

        adapter = new ReminderAdapter(this, reminders);
        reminderListView.setAdapter(adapter);


        reminderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
                Toast.makeText(MainViewListActivity.this, "Index clicked: " + String.valueOf(pos),Toast.LENGTH_SHORT).show();
            }
        });
        /*
        * long lcick ont working
        reminderListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                //build to delete item
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(view.getContext());
                builder.setTitle("Remove Reminder?");
                builder.setMessage("Are you sure you wish to remove this reminder?");


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    //listen for ok click
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    //Remove from db
                        //Reminder m = partyMonsters.remove(position);
                        //dbHelper.removeMonsterFromParty(defaultParty, m);
                        refreshListView();
                        Toast.makeText(getBaseContext(), "Reminder removed.", Toast.LENGTH_SHORT).show();

                    }
                });

                //do cancel operation
                builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
                return false;

            }
        });
    */

    }

    public void onFABClick(View v) {
        Intent intent = new Intent(MainViewListActivity.this, AddReminderActivity.class);
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
                Intent update = new Intent(this, MainViewListActivity.class);
                this.finish();
                startActivity(update);
                return true;

            case R.id.clear_reminders:
                //think this is the first option inside the 3 dot menu
                for(Reminder toDelete : reminders){
                    dbHelper.removeReminder(toDelete);
                }
                reminders.clear();
                Intent clear = new Intent(this, MainViewListActivity.class);
                this.finish();
                startActivity(clear);

                //TODO remove all from db
                return true;


            case R.id.add_dummy_data:
                populateDummyData(6);

                Intent i = new Intent(this, MainViewListActivity.class);
                isSorted = false;
                refreshListView();
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
                    boolean canAdd = true;
                    //updateIncompleteCount();
                    isSorted = false;

                    Reminder r = data.getParcelableExtra("reminder");

                    for (Reminder existing : reminders) {
                        if (r.getId() == existing.getId()) {
                            Toast.makeText(MainViewListActivity.this,
                                    "I don't think you were intending on adding a duplicate reminder",
                                    Toast.LENGTH_SHORT).show();
                            canAdd = false;
                        }
                    }

                    if(canAdd){

                        reminders.add(r);    // old week 4 version of adding
                        dbHelper.addReminder(r);
                        refreshListView();
                    }

                    Toast.makeText(getApplicationContext(), "Reminder added", Toast.LENGTH_SHORT).show();

                    //an intent to itself to restart activity, which refreshes list
                    Intent update = new Intent(this, MainViewListActivity.class);
                    this.finish();
                    startActivity(update);
                }
                break;

            case MARK_COMPLETE_REQUEST:
                if (resultCode == RESULT_OK) {


                    Reminder r = data.getParcelableExtra("edit");   //breaks here if you hit back after editing reminder
                    reminders.add(r);


                    adapter.notifyDataSetChanged();
                    Intent update = new Intent(MainViewListActivity.this, MainViewListActivity.class);

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
            dbHelper.addReminder(new Reminder
                    ((long) i, (Integer.toString((i + 1000)))
                    , "almost there mate and then the best thing in the world is the grae t nasinf jkelna"
                    , ("02/01/" + (3000 + i)), true));

        }
    }

    private void refreshListView() {
        // Update ListView and monster count
        adapter.notifyDataSetChanged();

    }
}
