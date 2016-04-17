package croft.portfolio.PersistentReminder;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import croft.portfolio.R;
import croft.portfolio.PersistentReminder.models.Reminder;

public class ViewDetailedReminderActivity extends AppCompatActivity {
    private EditText titleLabel;
    private EditText descriptionLabel;
    private Button dateLabel;
    private CheckBox completeCheckBox;
    private Reminder currentReminder;
    private Button save;
    private Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.reminder_activity_detailed);

        titleLabel = (EditText) findViewById(R.id.editTitleField);
        descriptionLabel = (EditText) findViewById(R.id.editDescriptionField);
        dateLabel = (Button) findViewById(R.id.editDateButton);
        completeCheckBox = (CheckBox) findViewById(R.id.completeCheck);
        save = (Button) findViewById(R.id.saveButton);

        Intent i = getIntent();
        currentReminder = i.getParcelableExtra(MainViewListActivity.EDIT_REMINDER_INTENT);

        setTitle("Edit Reminder");
        titleLabel.setText(currentReminder.getTitle());
        descriptionLabel.setText(currentReminder.getDescription());
        dateLabel.setText(currentReminder.getDueDateString());
        completeCheckBox.setChecked(currentReminder.isComplete());
    }

    public void onClick(View v) {
        //MainViewListActivity listActivity = new MainViewListActivity();
        // v.getReminderList().setComplete(completeCheckBox.isChecked());
        //TODO don't know how to get complete to update in previous list
        Intent i;
        switch (v.getId()) {
            case R.id.saveButton:
                currentReminder.setComplete(!currentReminder.isComplete());
                //TODO edit text fields in detailed reminder
                i = new Intent(this, MainViewListActivity.class);
                i.putExtra(MainViewListActivity.EDIT_REMINDER_INTENT, currentReminder);
                setResult(RESULT_OK);
                startActivity(i);
                finish();
                break;

            case R.id.editDateButton:
                //same code as add...
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

                new DatePickerDialog(ViewDetailedReminderActivity.this, date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();


                break;
            case R.id.deleteReminder:
                i = new Intent(this, MainViewListActivity.class);
                i.putExtra(MainViewListActivity.DELETE_REMINDER_INTENT, currentReminder);
                setResult(RESULT_OK);
                startActivity(i);
                finish();
                break;
        }
    }

    private void updateLabel() {

        String dateFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);

        dateLabel.setText(sdf.format(calendar.getTime()));
    }

}
