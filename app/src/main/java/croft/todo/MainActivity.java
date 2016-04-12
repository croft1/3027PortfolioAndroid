package croft.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("FIT3027 - Portfolio Activities");
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v){

        if(v.getId() == findViewById(R.id.tute4Button).getId()){

            Intent i = new Intent(MainActivity.this, ViewListActivity.class);
            startActivity(i);
            //finish();


        }

    }
}
