package croft.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import croft.todo.ReminderApplication.Reminder;

public class ViewDetailedReminderActivity extends AppCompatActivity {
    private TextView titleLabel;
    private TextView descriptionLabel;
    private TextView dateLabel;
    private CheckBox completeCheckBox;
    private Reminder currentReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detailed_reminder);

        titleLabel = (TextView) findViewById(R.id.titleLabel);
        descriptionLabel = (TextView) findViewById(R.id.descriptionLabel);
        dateLabel = (TextView) findViewById(R.id.dateLabel);
        completeCheckBox = (CheckBox) findViewById(R.id.completeCheckBox);

        Intent i = getIntent();
        currentReminder = i.getParcelableExtra("reminder");

        setTitle(currentReminder.getTitle());
        titleLabel.setText(currentReminder.getTitle());
        descriptionLabel.setText(currentReminder.getDescription());
        dateLabel.setText(currentReminder.getDueDateString());

        completeCheckBox.setChecked(currentReminder.isComplete());



    }

    public void onClick(View v){
         currentReminder.setComplete(completeCheckBox.isChecked());

     }




}
