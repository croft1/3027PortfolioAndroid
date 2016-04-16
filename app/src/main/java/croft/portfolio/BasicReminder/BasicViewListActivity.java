package croft.portfolio.BasicReminder;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import croft.portfolio.BasicReminder.models.BasicReminder;
import croft.portfolio.PersistentReminder.ViewDetailedReminderActivity;
import croft.portfolio.R;

public class BasicViewListActivity extends AppCompatActivity {

    public static final int ADD_REMINDER_REQUEST = 0;
    public static final int EDIT_REQUEST = 1;

    private ListView reminderListView;
    private BasicReminderItemAdapter adapter;
    private static ArrayList<BasicReminder> basicReminders = new ArrayList<BasicReminder>();
    private TextView complete;
    private static boolean isSorted;
    //private Item or Menu sortButton;
    private static int selectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("W4 - Basic Reminder");
        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.FAB);

        setContentView(R.layout.reminder_main_activity);

        reminderListView = (ListView) findViewById(R.id.reminderList);

        //sortButton = (Menu) findViewById(R.id.action_sort);

        adapter = new BasicReminderItemAdapter(this, basicReminders);

        reminderListView.setChoiceMode(reminderListView.CHOICE_MODE_SINGLE);
        reminderListView.setSelector(android.R.color.holo_blue_light);
        reminderListView.setAdapter(adapter);

        //updateIncompleteCount();

        reminderListView.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> av, View v, int i, long l) {
                    BasicReminder result = (BasicReminder)
                            reminderListView.getAdapter().getItem(i);

                    Intent intent = new Intent(BasicViewListActivity.this, BasicViewDetailedReminderActivity.class);
                    intent.putExtra("detailedReminder", result);
                    startActivityForResult(intent, EDIT_REQUEST);
                    finish();
                }
            }
        );
    }

    public void onFABClick(View v) {
        Intent intent = new Intent(BasicViewListActivity.this, BasicAddReminderActivity.class);
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

                String sort;

                if (basicReminders.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Can't sort without basicReminders!", Toast.LENGTH_SHORT).show();
                    return false;
                }

                if (isSorted) {
                    Collections.reverse(basicReminders);
                    sort = "Descending order";
                } else {
                    Collections.sort(basicReminders);
                    sort = "Ascending order";
                }

                isSorted = !isSorted;

                Toast.makeText(getApplicationContext(), sort, Toast.LENGTH_SHORT).show();

                //without notifyDataSetChanged, the screen only updates if list items are out of view...

                adapter.notifyDataSetChanged();     //tells the adapter i changed list, it needs change itself
                Intent update = new Intent(this, BasicViewListActivity.class);
                this.finish();
                startActivity(update);
                return true;

            case R.id.clear_reminders:
                //think this is the first option inside the 3 dot menu

                basicReminders.clear();
                Intent clear = new Intent(this, BasicViewListActivity.class);
                this.finish();
                startActivity(clear);
                return true;


            case R.id.add_dummy_data:
                populateDummyData(6);

                Intent i = new Intent(this, BasicViewListActivity.class);
                isSorted = false;
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
            case ADD_REMINDER_REQUEST:
                if (resultCode == RESULT_OK) {

                    //updateIncompleteCount();
                    isSorted = false;

                    BasicReminder r = data.getParcelableExtra("reminder");
                    basicReminders.add(r);

                    Intent update = new Intent(this, BasicViewListActivity.class);

                    this.finish();
                    startActivity(update);
                }
                break;

            case EDIT_REQUEST:
                if (resultCode == RESULT_OK) {


                    BasicReminder r = data.getParcelableExtra("edit");   //breaks here if you hit back after editing reminder
                    basicReminders.add(r);


                    adapter.notifyDataSetChanged();
                    Intent update = new Intent(this, BasicViewListActivity.class);

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
            basicReminders.add(new BasicReminder((Integer.toString((i + 1000)))
                    , "almost there Our main activity needs to get the ListView instance from the inflated layout and then the best thing in the world is the grae t nasinf jkelna"
                    , ("02/01/" + (3000 + i)), true));

        }
    }
}
