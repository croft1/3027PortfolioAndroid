package croft.todo.LabFiveSupplementary;

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
        setContentView(R.layout.activity_monster_main);

        partyList = (ListView) findViewById(R.id.listView);
        partyMonsters = new ArrayList<Monster>();
        adapter = new MonsterAdapter(this, partyMonsters);

        partyList.setAdapter(adapter);

        updateMonsterCount();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

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
