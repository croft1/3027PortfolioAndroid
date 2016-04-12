package croft.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import croft.todo.Calculator.Calculator;
import croft.todo.Calculator.CalculatorMain;
import croft.todo.NameGenerator.NameGenMain;
import croft.todo.ReminderApplication.ViewListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("FIT3027 - Portfolio Activities");
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v){
        Intent i;
        switch(v.getId()) {
            case R.id.tute2Button:
                i = new Intent(MainActivity.this, CalculatorMain.class);
                startActivity(i);
                break;
            case R.id.tute3Button:
                i = new Intent(MainActivity.this, NameGenMain.class);
                startActivity(i);
                break;
            case R.id.tute4Button:
                i = new Intent(MainActivity.this, ViewListActivity.class);
                startActivity(i);
                break;
            default:
                Toast toast = Toast.makeText(this, "Unable to start activity", Toast.LENGTH_SHORT);
                toast.show();
        }
    }
}
