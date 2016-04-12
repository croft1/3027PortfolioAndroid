package croft.todo.LabFiveSupplementary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import croft.todo.R;

/**
 * Created by Michaels on 26/3/2016.
 */
public class AddMonsterActivity extends AppCompatActivity {

    ListView monsterAddList;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_monster);

        monsterAddList = (ListView) findViewById(R.id.listView);

        ArrayList<Monster> monsters = new ArrayList < Monster>();
        monsters.add(new Monster("Fairy", "Good", 1, 90));
        monsters.add(new Monster("Druid", "Neutral", 1, 105));
        monsters.add(new Monster("Devil", "Evil", 1, 110));

        MonsterAdapter adapter = new MonsterAdapter(this, monsters);


        monsterAddList.setAdapter(adapter);


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
