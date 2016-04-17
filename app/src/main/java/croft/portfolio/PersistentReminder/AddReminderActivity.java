package croft.portfolio.PersistentReminder;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import croft.portfolio.PersistentReminder.models.Reminder;
import croft.portfolio.R;

public class AddReminderActivity extends AppCompatActivity {


    private Button addReminder;
    private EditText inputTitle;
    private Button inputDate;
    private EditText inputDescription;
    private Button calendarDialogButton;
    static Calendar calendar = Calendar.getInstance();
    public static final int TEXT_LENGTH_LIMIT = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_activity_add);
        setTitle("Add Reminder");

        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        ArrayList<Reminder> reminders = new ArrayList<>(
                dbHelper.getAllReminders().values()
        );



        addReminder = (Button) findViewById(R.id.addReminderButton);
        addReminder.setEnabled(false);
        addReminder.setHighlightColor(Color.LTGRAY);

        inputTitle = (EditText) findViewById(R.id.inputTitle);
        inputDate = (Button) findViewById(R.id.inputDate);
        inputDescription = (EditText) findViewById(R.id.inputDescription);

        inputTitle.addTextChangedListener(w);
        inputDescription.addTextChangedListener(w);


    }

    private final TextWatcher w = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //TODO: set limits for the number of characters for input and output.
            if (inputTitle.getText().toString().length() > 0 &&
                    inputDate.getText().toString() != "Press to choose date" &&
                    inputDescription.getText().toString().length() > 0) {
                addReminder.setEnabled(true);
                addReminder.setBackgroundColor(Color.CYAN);
            }
            if(inputDescription.getText().toString().length() > TEXT_LENGTH_LIMIT ||
                    inputTitle.getText().toString().length() > (TEXT_LENGTH_LIMIT/4)){
                addReminder.setEnabled(false);
                addReminder.setBackgroundColor(Color.LTGRAY);
                inputDescription.clearFocus();
                inputTitle.clearFocus();        //clearing desc focus moves to next focusable

                Toast.makeText(getApplicationContext(), "Description limit reached", Toast.LENGTH_SHORT);

            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            //allows if requirements are met


        }
    };

    public void onClick(View v) {
        Toast t;

        switch(v.getId()) {
            case R.id.addReminderButton:
                String submittedTitle = inputTitle.getText().toString();
                String submittedDescription = inputDescription.getText().toString();
                String submittedDate = inputDate.getText().toString();

                Reminder submittedReminder = new Reminder(submittedTitle, submittedDescription, submittedDate,false);



                Intent i = new Intent(this, MainViewListActivity.class);
                i.putExtra(MainViewListActivity.ADD_REMINDER_INTENT, submittedReminder);
                //MainViewListActivity.addReminder(submittedReminder);

                //startActivity(i);
                /*
                * doubled up the toast
                Toast.makeText(getApplicationContext(),
                        ("Reminder: " + submittedReminder.getTitle() + " created."),
                        Toast.LENGTH_SHORT).show();
                */

                setResult(RESULT_OK, i);
                finish();

                break;


            case R.id.inputDate:
                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabel();

                    }
                };

                new DatePickerDialog(AddReminderActivity.this, date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();


                break;

            default:
                t = Toast.makeText(getApplicationContext(), "Unassigned button", Toast.LENGTH_SHORT);
                t.show();
        }

    }

    private void updateLabel() {

        String dateFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);

        inputDate.setText(sdf.format(calendar.getTime()));
    }

}
