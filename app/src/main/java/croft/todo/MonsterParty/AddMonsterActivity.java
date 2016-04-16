package croft.todo.MonsterParty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import croft.todo.MonsterParty.models.Monster;
import croft.todo.R;

/**
 * Created by Michaels on 26/3/2016.
 */
public class AddMonsterActivity extends AppCompatActivity {

    ListView monsterAddList;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monster_add_activity);

        monsterAddList = (ListView) findViewById(R.id.monsterListView);

        //create dapter and populate
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        ArrayList<Monster> monsters = new
                ArrayList<>(dbHelper.getAllMonsters().values());

        MonsterAdapter adapter = new MonsterAdapter(this, monsters);
        monsterAddList.setAdapter(adapter);

        setTitle("Select Monster to Add");


        monsterAddList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> av, View v, int i, long l) {
                        Monster result = (Monster)
                                monsterAddList.getAdapter().getItem(i);
                        Intent intent = new Intent();
                        intent.putExtra("result", result);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
    }


}
