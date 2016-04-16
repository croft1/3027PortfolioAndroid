package croft.portfolio.BasicReminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import croft.portfolio.BasicReminder.models.BasicReminder;
import croft.portfolio.R;

public class BasicViewDetailedReminderActivity extends AppCompatActivity {
    private TextView titleLabel;
    private TextView descriptionLabel;
    private TextView dateLabel;
    private CheckBox completeCheckBox;
    private BasicReminder currentBasicReminder;
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
        currentBasicReminder = i.getParcelableExtra("detailedReminder");

        setTitle(currentBasicReminder.getTitle());
        titleLabel.setText(currentBasicReminder.getTitle());
        descriptionLabel.setText(currentBasicReminder.getDescription());
        dateLabel.setText(currentBasicReminder.getDueDateString());

        completeCheckBox.setChecked(currentBasicReminder.isComplete());
    }

    public void onClick(View v) {
        //BasicViewListActivity listActivity = new BasicViewListActivity();
        // v.getReminderList().setComplete(completeCheckBox.isChecked());
        //TODO don't know how to get complete to update in previous list

        switch (v.getId()) {
            case R.id.saveButton:
                currentBasicReminder.setComplete(!currentBasicReminder.isComplete());
                //TODO edit text fields in detailed reminder
                Intent i = new Intent(this, BasicViewListActivity.class);
                i.putExtra("reminder", currentBasicReminder);
                Toast.makeText(getApplicationContext(), " Completion status: " + currentBasicReminder.isComplete(), Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, i);
                startActivity(i);
                finish();
                break;

            case android.R.id.home:

        }
    }

}
