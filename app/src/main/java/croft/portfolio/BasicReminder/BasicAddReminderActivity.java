package croft.portfolio.BasicReminder;

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
import java.util.Calendar;
import java.util.Locale;

import croft.portfolio.BasicReminder.models.BasicReminder;
import croft.portfolio.R;


public class BasicAddReminderActivity extends AppCompatActivity {


    private Button addReminder;
    private EditText inputTitle;
    private Button inputDate;
    private EditText inputDescription;
    private Button calendarDialogButton;
    static Calendar calendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_activity_add);
        setTitle("Add BasicReminder");


        //formatting the UI elements
        addReminder = (Button) findViewById(R.id.addReminderButton);
        addReminder.setEnabled(false);
        addReminder.setHighlightColor(Color.LTGRAY);

        inputTitle = (EditText) findViewById(R.id.inputTitle);
        inputDate = (Button) findViewById(R.id.inputDate);
        inputDescription = (EditText) findViewById(R.id.inputDescription);


        //text changed listeners monitor before during and after text is input to perform actions
        inputTitle.addTextChangedListener(w);
        inputDescription.addTextChangedListener(w);


    }

    private final TextWatcher w = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            //ensure that the text entered in each field, and only will enable the button when it is enabled

            if (inputTitle.getText().toString().length() > 0 &&
                    inputDate.getText().toString() != "Press to choose date" &&
                    inputDescription.getText().toString().length() > 0) {
                addReminder.setEnabled(true);
                addReminder.setHighlightColor(Color.CYAN);
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

/*
                    //checking
                if(inputDate.getText().toString().length() > 0 &&
                inputDescription.getText().toString().length() > 0 &&           //doubled up
                        inputTitle.getText().toString().length() > 0){
*/

                //set variables to create objects with
                String submittedTitle = inputTitle.getText().toString();
                String submittedDescription = inputDescription.getText().toString();
                String submittedDate = inputDate.getText().toString();

                //object created
                BasicReminder submittedBasicReminder = new BasicReminder(submittedTitle, submittedDescription, submittedDate,false);

                //notify
                t = Toast.makeText(getApplicationContext(), ("BasicReminder: " + submittedBasicReminder.getTitle() + " created."), Toast.LENGTH_SHORT);

                //pass intent fro here to the basic view list activity with the reminder object as an extra, which will be fetched using the rmeinder 'tag', 'id', or whatever
                Intent i = new Intent(this, BasicViewListActivity.class);
                i.putExtra("reminder", submittedBasicReminder);

                setResult(RESULT_OK, i);
                t.show();       //show toast, don't need to create another activity. We close this one as the other is in the background
                finish();       //closing activity
/*
                }else{
                    break;          //escape when values are empty
                }
                   */

                //BasicViewListActivity.addReminder(submittedBasicReminder);

                //startActivity(i);


                break;

            case R.id.inputDate:

                //listening onto the date picker the input of a date picker
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
                //which is set up here , and shown. thats monitored from there
                new DatePickerDialog(BasicAddReminderActivity.this, date, calendar
                        .get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();


                break;

            default:
                t = Toast.makeText(getApplicationContext(), "Unassigned button", Toast.LENGTH_SHORT);
                t.show();
        }

    }

    private void updateLabel() {


        //using formatter tools, it takes the datepicker value and sets the text of the button to open the dialog to the date selected
        String dateFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);

        inputDate.setText(sdf.format(calendar.getTime()));
    }

}
