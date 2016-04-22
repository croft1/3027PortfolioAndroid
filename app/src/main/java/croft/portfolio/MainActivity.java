package croft.portfolio;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import croft.portfolio.Calculator.CalculatorMain;
import croft.portfolio.MonsterPartySQL.SQLMonsterMain;
import croft.portfolio.NameGenerator.NameGenMain;
import croft.portfolio.BasicReminder.BasicViewListActivity;
import croft.portfolio.NewsReader.NewsFullArticleActivity;
import croft.portfolio.NewsReader.NewsMain;
import croft.portfolio.PersistentReminder.MainViewListActivity;

public class MainActivity extends AppCompatActivity {



    //hello and welcome to my portfolio
    //Michael Carter 26922452 Monash University Semester 1 FIT3027 2016

    boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {        //we begin in this main activity, where it is created
        super.onCreate(savedInstanceState);
        setTitle("FIT3027 - Portfolio Activities");     //set the title of the activity
        setContentView(R.layout.main);                  //and content view to the xml layout of main


            //we call a connectivity manager which handles certain features and services of android, depending on the service selected.
        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);         //and you can retrieve a whole lot of information on the networking of the device fthrough the connectivity service
                                                                                                     //http://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html


        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo(); //network objects gives us access to more detailed network information about current network
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();     //boolean helps manage connection status and reactions of on or off

        if(!isConnected){
            Button t6 = (Button) findViewById(R.id.tute6Button);        //disables and visually demonstrates repucusisons of not having a network connection
            t6.setEnabled(false);       //tute 6 requries a network, so we disable it if there is no conenction
            t6.setBackgroundColor(Color.RED);

            Toast.makeText(getApplicationContext(), "No network connection, some features disabled", Toast.LENGTH_SHORT);       //a simple toast to display what happened
        }


    }

    public void onClick(View v){            //the view is full of buttons that open up the activity that it is needed
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
