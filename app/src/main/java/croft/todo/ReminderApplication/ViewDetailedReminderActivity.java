package croft.todo.ReminderApplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import croft.todo.R;

public class ViewDetailedReminderActivity extends AppCompatActivity {
    private TextView titleLabel;
    private TextView descriptionLabel;
    private TextView dateLabel;
    private CheckBox completeCheckBox;
    private Reminder currentReminder;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.reminder_detailed_activity);

        titleLabel = (TextView) findViewById(R.id.titleLabel);
        descriptionLabel = (TextView) findViewById(R.id.descriptionLabel);
        dateLabel = (TextView) findViewById(R.id.dateLabel);
        completeCheckBox = (CheckBox) findViewById(R.id.completeCheckBox);
        save = (Button) findViewById(R.id.saveButton);

        Intent i = getIntent();
        currentReminder = i.getParcelableExtra("reminder");

        setTitle(currentReminder.getTitle());
        titleLabel.setText(currentReminder.getTitle());
        descriptionLabel.setText(currentReminder.getDescription());
        dateLabel.setText(currentReminder.getDueDateString());

        completeCheckBox.setChecked(currentReminder.isComplete());
    }

    public void onClick(View v) {
        //ViewListActivity listActivity = new ViewListActivity();
        // v.getReminderList().setComplete(completeCheckBox.isChecked());
        //TODO don't know how to get complete to update in previous list

        switch (v.getId()) {
            case R.id.saveButton:
                currentReminder.setComplete(!currentReminder.isComplete());
                //TODO edit text fields in detailed reminder
                Intent i = new Intent(this, ViewListActivity.class);
                i.putExtra("reminder", currentReminder);
                Toast.makeText(getApplicationContext(), "Reminder Complete?  " + currentReminder.isComplete(), Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                startActivity(i);
                finish();
                break;

            case android.R.id.home:

        }
    }

}
