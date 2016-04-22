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

    public static int indexOfEdited;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.basic_reminder_detailed_activity);

        titleLabel = (TextView) findViewById(R.id.titleLabel);
        descriptionLabel = (TextView) findViewById(R.id.descriptionLabel);
        dateLabel = (TextView) findViewById(R.id.dateLabel);
        completeCheckBox = (CheckBox) findViewById(R.id.compCheck);
        save = (Button) findViewById(R.id.saveB);
                                                                                //setting up the references to UI elements so we can use the member variables as references
        Intent i = getIntent();             //the intent that was passed from the amin activity of thi task is retrieved here in its own object
        currentBasicReminder = i.getParcelableExtra("detailedReminder");            //we named the extra item - a reminder object - so we can fetch it and assign it to a new reminder variable

        //getting id's of objects was werid and slow, this seemed a simple way to do it
        indexOfEdited = i.getIntExtra("index", indexOfEdited);                      //to track which exact item in the list is edited so we can pass it back and have a reference to go off


        //setting text of ui items
        setTitle(currentBasicReminder.getTitle());
        titleLabel.setText(currentBasicReminder.getTitle());
        descriptionLabel.setText(currentBasicReminder.getDescription());
        dateLabel.setText(currentBasicReminder.getDueDateString());

        completeCheckBox.setChecked(currentBasicReminder.isComplete());
    }

    public void onClick(View v) {
        //BasicViewListActivity listActivity = new BasicViewListActivity();
        // v.getReminderList().setComplete(completeCheckBox.isChecked());


        //update to previous list was achieved by using the member variable passed in intensts

        switch (v.getId()) {
            case R.id.saveB:

                Intent i = new Intent(this, BasicViewListActivity.class);

                if(completeCheckBox.isChecked() && currentBasicReminder.isComplete()){      //when checked is unchanged, cancel the intent and end up just closig the activity anyway
                    setResult(RESULT_CANCELED);

                }else{
                    i.putExtra("index", indexOfEdited);             //when index is changed, we confirm results and the index of the one we were after, to the main activity can work on that
                    setResult(RESULT_OK, i);

                }

                Toast.makeText(getApplicationContext(), " Completion status: " + currentBasicReminder.isComplete(), Toast.LENGTH_SHORT).show();


                finish();       //close activity as it was started ontop of the main one.
                break;

        }
    }

}
