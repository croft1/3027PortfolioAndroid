package croft.portfolio.NameGenerator;

/**
 * Created by Michaels on 12/4/2016.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import croft.portfolio.R;

public class NameViewActivity extends AppCompatActivity {


    private Person currentPerson;
    private TextView firstNameLabel;
    private TextView lastNameLabel;
    private TextView planetLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_gen_activity);

        setTitle("Your Star Wars Name");

        firstNameLabel = (TextView) findViewById(R.id.firstNameLabel);
        lastNameLabel = (TextView) findViewById(R.id.lastNameLabel);
        planetLabel = (TextView) findViewById(R.id.planetNameLabel);

        Intent i = getIntent();
        currentPerson = i.getParcelableExtra("person");

        firstNameLabel.setText(currentPerson.generateNewFirstName());
        lastNameLabel.setText(currentPerson.generateNewLastName());
        planetLabel.setText(currentPerson.generatedNewPlanetName());



    }


}