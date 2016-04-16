package croft.portfolio.MonsterParty;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import croft.portfolio.MonsterParty.models.Monster;
import croft.portfolio.MonsterParty.models.Party;
import croft.portfolio.R;

public class MonsterMain extends AppCompatActivity {

    public static final int ADD_MONSTER_REQUEST = 1;

    private ListView partyList;
    private MonsterAdapter adapter;
    private ArrayList<Monster> partyMonsters;
    private Party defaultParty;
    private MonsterDatabaseHelper dbHelper;

    public void testButtonClick(View v){

        double sum = 0;
        int count = 0;

        for(int i = 0; i < partyMonsters.size(); i++){
            sum += partyMonsters.get(i).getAttackPower();
            count++;
        }

        int scale = (int) Math.pow(10, 1);

        makeToast(Double.toString((double) Math.round((sum / count) * scale) / scale));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monster_main_activity);

        //get db helper

        dbHelper = new MonsterDatabaseHelper(getApplicationContext());

        if(dbHelper.getAllMonsters().size() == 0){

            //add DUMMY to db
            dbHelper.addMonster(new Monster("Dragon", "Evil", 13));
            dbHelper.addMonster(new Monster("Lizard", "Neutral", 10));
            dbHelper.addMonster(new Monster("Killer Rabbit", "Evil", 3));
            dbHelper.addMonster(new Monster("Holy Panda", "Good", 6));
            dbHelper.addMonster(new Monster("Fairy", "Good", 90));
            dbHelper.addMonster(new Monster("Druid", "Neutral", 105));
            dbHelper.addMonster(new Monster("Devil", "Evil", 110));

            //add default party (use for main ListView
            Party p = new Party (10, "Default Party");
            dbHelper.addParty(p);
        }

        //get layout list view
        partyList = (ListView) findViewById(R.id.listView);

        defaultParty = dbHelper.getDefaultParty();

        partyMonsters = dbHelper.getMonstersFromParty(defaultParty);
        //partyMonsters = new ArrayList<Monster>();

        adapter = new MonsterAdapter(this, partyMonsters);
        partyList.setAdapter(adapter);

        partyList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                //build to delete item
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(view.getContext());
                builder.setTitle("Remove Monster?");
                builder.setMessage("Are you sure you wish to remove this monster?");


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    //listen for ok click
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Remove from db
                        Monster m = partyMonsters.remove(position);
                        dbHelper.removeMonsterFromParty(defaultParty, m);
                        refreshListView();
                        Toast.makeText(getBaseContext(), "Monster has been removed.", Toast.LENGTH_SHORT).show();

                    }
                });

                //do cancel operation
                builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
                return false;

            }
        });


        updateMonsterCount();


    }

    //when first building the options menu, this runs
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_main, menu);
        updateMonsterCount();
        return super.onCreateOptionsMenu(menu);
    }

    //runs when item created onto menu is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch ( item.getItemId()){
            case R.id.action_add:
                //go to addMonsterActivity and await result
                Intent i = new Intent(this, AddMonsterActivity.class);
                startActivityForResult(i, ADD_MONSTER_REQUEST);
                return true;
            default:
                return super.onOptionsItemSelected((item));
        }
    }

    //after the activity that was run from pressing add finished, this runs
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_MONSTER_REQUEST){
            if( resultCode == RESULT_OK){
                boolean canAddMonster = true;
                Monster m = data.getParcelableExtra("result");

                refreshListView();
//          Check if we can add monster
                for(Monster existingMonster : partyMonsters){
                    if(m.getId() == existingMonster.getId()){
                        Toast.makeText(MonsterMain.this, "Monster already in party",
                                Toast.LENGTH_SHORT).show();
                        canAddMonster = false;
                    }
                }

            //add m to db
                if(canAddMonster){
                    dbHelper.addMonsterToParty(defaultParty, m);
                    partyMonsters.add(m);
                    refreshListView();
                    Toast.makeText(MonsterMain.this, "Monster added.",
                            Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    private void refreshListView() {
    // Update ListView and monster count
        adapter.notifyDataSetChanged();
        updateMonsterCount();
    }


    private void updateMonsterCount(){

        int totalMonsters = partyMonsters.size();
        setTitle("Monsters: " + totalMonsters);
    }





    private void makeToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}
