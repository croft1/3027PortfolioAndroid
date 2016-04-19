package croft.portfolio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import croft.portfolio.Calculator.CalculatorMain;
import croft.portfolio.MonsterPartySQL.SQLMonsterMain;
import croft.portfolio.NameGenerator.NameGenMain;
import croft.portfolio.BasicReminder.BasicViewListActivity;
import croft.portfolio.NewsReader.NewsFullArticleActivity;
import croft.portfolio.NewsReader.NewsMain;
import croft.portfolio.PersistentReminder.MainViewListActivity;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("FIT3027 - Portfolio Activities");
        setContentView(R.layout.main);


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
                i = new Intent(MainActivity.this, BasicViewListActivity.class);
                startActivity(i);
                break;
            case R.id.tute4ButtonRecycler:
                Toast.makeText(this, "Available soon!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tute5SuppButton:
                i = new Intent(MainActivity.this, SQLMonsterMain.class);
                startActivity(i);
                break;
            case R.id.tute5Button:
                i = new Intent(this, MainViewListActivity.class);
                startActivity(i);
                break;
            case R.id.tute6Button:
                //i = new Intent(this, NewsMain.class);
                i = new Intent(this, NewsMain.class);
                startActivity(i);
                break;
            default:
                Toast.makeText(this, "Button has not been assigned an action", Toast.LENGTH_SHORT).show();
        }
    }
}
