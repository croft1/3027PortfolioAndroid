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
import java.util.Arrays;
import java.util.Collections;

import croft.portfolio.PersistentReminder.models.Reminder;
import croft.portfolio.R;

public class MainViewListActivity extends AppCompatActivity {

    public static final int ADD_REQUEST = 0;
    public static final int EDIT_REQUEST = 1;

    public static final String EDIT_REMINDER_INTENT = "edit";
    public static final String DELETE_REMINDER_INTENT = "delete";
    public static final String ADD_REMINDER_INTENT = "add";

    private ListView reminderListView;
    private ReminderAdapter adapter;
    private static ArrayList<Reminder> reminders = new ArrayList<>();
    private static boolean isSorted;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("W5 - Reminders + SQL");
        setContentView(R.layout.reminder_main_activity);

        dbHelper = new DatabaseHelper(getApplicationContext());

        reminderListView = (ListView) findViewById(R.id.reminderList);

        adapter = new ReminderAdapter(this, reminders);


        reminderListView.setChoiceMode(reminderListView.CHOICE_MODE_SINGLE);
        reminderListView.setSelector(android.R.color.holo_blue_light);
        reminderListView.setAdapter(adapter);

        //if (dbHelper.getAllReminders().size() == 0){
            //populateDummyData(3);

        //}else{
            ArrayList<Reminder> convertList = new ArrayList<>(dbHelper.getAllReminders().values());
            reminders = convertList;
       // }

        refreshListView();

        reminderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
                Toast.makeText(MainViewListActivity.this, "Index clicked: " + String.valueOf(pos),Toast.LENGTH_SHORT).show();
                Reminder result = (Reminder)
                        reminderListView.getAdapter().getItem(pos);

                Intent intent = new Intent(MainViewListActivity.this, ViewDetailedReminderActivity.class);
                intent.putExtra(EDIT_REMINDER_INTENT, result);
                startActivityForResult(intent, EDIT_REQUEST);
                finish();
            }
        });

/*
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
                        Reminder toRemove = reminders.get(position);
                        reminders.remove(position);
                        //dbHelper.removeMonsterFromParty(defaultParty, m);\
                        dbHelper.removeReminder(toRemove);
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
        startActivityForResult(intent, ADD_REQUEST);
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

                String sort;

                if (reminders.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Can't sort without reminders!", Toast.LENGTH_SHORT).show();
                    return false;
                }

                if (isSorted) {
                   Collections.sort(reminders, Collections.<Reminder>reverseOrder());
                  sort = "Descending order";
                } else {
                    Collections.sort(reminders);
                    sort = "Ascending order";
                }

                isSorted = !isSorted;

                Toast.makeText(getApplicationContext(), sort, Toast.LENGTH_SHORT).show();


                //without notifyDataSetChanged, the screen only updates if list items are out of view...

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


                return true;


            case R.id.add_dummy_data:
                populateDummyData(1);

                Intent i = new Intent(this, MainViewListActivity.class);
                isSorted = false;
                refreshListView();
                this.finish();
                startActivity(i);
                return true;

            default:
                Toast.makeText(getApplicationContext(), "A Petty Failure", Toast.LENGTH_LONG).show();

        }
        return super.onOptionsItemSelected((item));
    }

    //after the activity that was run from pressing add finished, this runs
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ADD_REQUEST:
                if (resultCode == RESULT_OK) {
                    boolean canAdd = true;
                    //updateIncompleteCount();
                    isSorted = false;

                    Reminder r = data.getParcelableExtra(ADD_REMINDER_INTENT);

                    for (Reminder existing : reminders) {
                        if (r.getId() == existing.getId()) {
                            Toast.makeText(MainViewListActivity.this,
                                    "I don't think you were intending on adding a duplicate reminder",
                                    Toast.LENGTH_SHORT).show();
                            canAdd = false;
                        }
                    }

                    if(canAdd){
                        reminders.add(r);
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

            case EDIT_REQUEST:
                if (resultCode == RESULT_OK) {

                    Reminder r;

                    //if parce key

                    Intent i = getIntent();
                    Bundle extras = i.getExtras();


                    if(i.hasExtra(DELETE_REMINDER_INTENT)){
                        r = data.getParcelableExtra(DELETE_REMINDER_INTENT);
                        dbHelper.removeReminder(r);
                    }else{
                        if(i.hasExtra(EDIT_REMINDER_INTENT)){
                            r = data.getParcelableExtra(EDIT_REMINDER_INTENT);

                        }
                    }


                       //breaks here if you hit back after editing reminder



                    adapter.notifyDataSetChanged();
                    Intent update = new Intent(MainViewListActivity.this, MainViewListActivity.class);

                    this.finish();
                    startActivity(update);
                }
                break;
            default:
                Toast.makeText(getApplicationContext(), "FAILED", Toast.LENGTH_SHORT).show();

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
            Reminder r = new Reminder
                    ((long) i, (Integer.toString((i + 1000)))
                            , "almost there mate and then the best thing in the world is the grae t nasinf jkelna"
                            , ("02/01/" + (3000 + i)), true);
            dbHelper.addReminder(r);
            reminders.add(r);

        }
    }

    private void refreshListView() {
        // Update ListView and monster count
        adapter.notifyDataSetChanged();

    }
}
