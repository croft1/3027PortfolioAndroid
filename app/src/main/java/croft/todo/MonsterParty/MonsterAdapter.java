package croft.todo.MonsterParty;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import croft.todo.MonsterParty.models.Monster;
import croft.todo.R;

/**
 * Created by Michaels on 26/3/2016.
 */
public class MonsterAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Monster> monsters;

    public MonsterAdapter(Context context, ArrayList<Monster> monsters) {
        this.context = context;
        this.monsters = monsters;
    }

    public static class ViewHolder {
        TextView monsterName;
        TextView attackPower;
    }
    @Override
    public int getCount() {
        return monsters.size();
    }
    @Override
    public Monster getItem(int i) {
        return monsters.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
// Check if view has been created for the row. If not, lets inflate it
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
// Reference list item layout here
            view = inflater.inflate(R.layout.monster_list_item, null);
// Setup ViewHolder and attach to view
            vh = new ViewHolder();
            vh.monsterName = (TextView) view.findViewById(R.id.monsterNameText);
            vh.attackPower = (TextView) view.findViewById(R.id.attackPowerText);
            view.setTag(vh);
        } else {
// View has already been created, fetch our ViewHolder
            vh = (ViewHolder) view.getTag();
        }
// Assign values to the TextViews using the Monster object
        vh.monsterName.setText(monsters.get(i).getName());
        vh.attackPower.setText("Attack Power: " +
                monsters.get(i).getAttackPower());
// Change the colour depending on the monster type
        String monsterType = monsters.get(i).getType();
        if (monsterType.equals("Good"))
            vh.attackPower.setTextColor(Color.parseColor("#00FF00")); // Green
        if (monsterType.equals("Neutral"))
            vh.attackPower.setTextColor(Color.parseColor("#0000FF")); // Blue
        if (monsterType.equals("Evil"))
            vh.attackPower.setTextColor(Color.parseColor("#FF0000")); // Red
// Return the completed View of the row being processed
        return view;
    }
}

