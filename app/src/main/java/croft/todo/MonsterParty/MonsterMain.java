package croft.todo.MonsterParty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import croft.todo.R;

public class MonsterMain extends AppCompatActivity {

    public static final int ADD_MONSTER_REQUEST = 1;

    private ListView partyList;
    private MonsterAdapter adapter;
            private ArrayList<Monster> partyMonsters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monster_main_activity);

        partyList = (ListView) findViewById(R.id.listView);
        partyMonsters = new ArrayList<Monster>();

        adapter = new MonsterAdapter(this, partyMonsters);
        partyList.setAdapter(adapter);


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
                Monster m = data.getParcelableExtra("result");
                partyMonsters.add(m);
                adapter.notifyDataSetChanged();

                updateMonsterCount();
            }
        }
    }


    private void updateMonsterCount(){

        int totalMonsters = partyMonsters.size();
        setTitle("Monsters: " + totalMonsters);
    }
}
