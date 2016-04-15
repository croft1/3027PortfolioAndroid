package croft.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import croft.todo.Calculator.CalculatorMain;
import croft.todo.MonsterParty.MonsterMain;
import croft.todo.NameGenerator.NameGenMain;
import croft.todo.ReminderApplication.ViewListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("FIT3027 - Portfolio Activities");
        setContentView(R.layout.portfolio_main);
    }

    public void onClick(View v){
        Intent i;
        Toast toast;
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
            case R.id.tute4ButtonRecycler:
                toast = Toast.makeText(this, "Available soon!", Toast.LENGTH_SHORT);
                toast.show();
                break;
            case R.id.tute5SuppButton:
                i = new Intent(MainActivity.this, MonsterMain.class);
                startActivity(i);
                break;
            default:
                toast = Toast.makeText(this, "Button has not been assigned an action", Toast.LENGTH_SHORT);
                toast.show();
        }
    }
}
