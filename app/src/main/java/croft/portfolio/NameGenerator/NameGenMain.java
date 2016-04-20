package croft.portfolio.NameGenerator;

/**
 * Created by Michaels on 12/4/2016.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;


import croft.portfolio.R;

public class NameGenMain extends AppCompatActivity {

    private EditText inputFirstName;
    private EditText inputLastName;
    private EditText inputMaiden;
    private EditText inputBirth;
    private EditText inputBrand;
    private Button generateButton;
    ArrayList <EditText> inputs = new ArrayList<EditText>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_main_activity);
        setTitle("Star Wars Name Generator");

        inputFirstName = (EditText) findViewById(R.id.inputFirstName);
        inputLastName = (EditText) findViewById(R.id.inputLastName);
        inputMaiden = (EditText) findViewById(R.id.inputMaiden);
        inputBirth = (EditText) findViewById(R.id.inputBirth);
        inputBrand = (EditText) findViewById(R.id.inputBrand);
        generateButton = (Button) findViewById(R.id.generateButton);


        inputs.add(inputBirth);
        inputs.add(inputBrand);
        inputs.add(inputFirstName);
        inputs.add(inputLastName);
        inputs.add(inputMaiden);




    }

    public void generateButtonClicked(View v){
        boolean empty = false;
        for(EditText i: inputs){
            if(i.getText().toString().isEmpty())
                empty = true;
        }
        Person gen;
        if (inputBirth.getText().toString().length() > 2 &&
                inputBrand.getText().toString().length() > 2 &&
                inputFirstName.getText().toString().length() > 2 &&
                inputLastName.getText().toString().length() > 2 &&
                inputMaiden.getText().toString().length() > 2)
        {
            gen = new Person(inputFirstName.getText().toString(),
                    inputLastName.getText().toString(), inputMaiden.getText().toString(),
                    inputBirth.getText().toString(), inputBrand.getText().toString());



            Intent i = new Intent(this, NameViewActivity.class);
            i.putExtra("person", gen);
            startActivity(i);

        }else{
            doAlertDialog("Alert", "One or more inputs are empty or have too few inputs");
        }


    }



    public void doAlertDialog(String title, String message){

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
}


