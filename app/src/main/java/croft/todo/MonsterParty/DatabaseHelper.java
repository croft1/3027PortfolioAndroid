package croft.todo.MonsterParty;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Telephony;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import croft.todo.MonsterParty.models.Monster;
import croft.todo.MonsterParty.models.MonsterParty;
import croft.todo.MonsterParty.models.Party;

/**
 * Created by Michaels on 16/4/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MonsterDB";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Monster.CREATE_STATEMENT);
        db.execSQL(Party.CREATE_STATEMENT);
        db.execSQL(MonsterParty.CREATE_STATEMENT);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Monster.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Party.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MonsterParty.TABLE_NAME);
        onCreate(db);
    }

    // MONSTER DB

    public void addMonster (Monster m){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Monster.COLUMN_NAME, m.getName());
        values.put(Monster.COLUMN_ATTACK_POWER, m.getAttackPower());
        values.put(Monster.COLUMN_TYPE, m.getType());
        db.insert(Monster.TABLE_NAME, null, values);
        db.close();
    }

    public HashMap<Long, Monster> getAllMonsters() {
        HashMap<Long, Monster> monsters = new LinkedHashMap<Long, Monster>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Monster.TABLE_NAME, null);


// Add monster to hash map - ID is key, object is value
        if (cursor.moveToFirst()) {

            do {
//                Monster m = new Monster(cursor.getLong(0), cursor.getString(1),
//                        cursor.getString(2), cursor.getInt(3));

                Monster m = new Monster(cursor.getLong(0), cursor.getString(1),
                        cursor.getString(2), cursor.getInt(3));
                monsters.put(m.getId(), m);
            } while (cursor.moveToNext());
        }
// Close cursor
        cursor.close();
        return monsters;
    }

    public void addParty ( Party p){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Party.COLUMN_NAME, p.getName());
        long id = db.insert(Party.TABLE_NAME, null, values);
        db.close();
    }

    //method to populate db w test data
    public Party getDefaultParty(){
        SQLiteDatabase db = this.getWritableDatabase();
        Party p = null;
        Cursor cursor = db.rawQuery("SELECT * FROM " + Party.TABLE_NAME, null);
        if(cursor.moveToFirst()) {
            p = new Party(cursor.getLong(0), cursor.getString(1));
        }
        cursor.close();
            db.close();
            return p;
    }

    public void addMonsterToParty(Party p, Monster m){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MonsterParty.COLUMN_PARTY_ID, p.getId());
        values.put(MonsterParty.COLUMN_MONSTER_ID, p.getId());
        db.insert(MonsterParty.TABLE_NAME, null, values);
    }

    public ArrayList<Monster> getMonstersFromParty(Party p){
        SQLiteDatabase db = this.getWritableDatabase();

        HashMap<Long, Monster> monsterData = getAllMonsters();
        ArrayList<Monster> resultMonsters = new ArrayList<Monster>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + MonsterParty.TABLE_NAME +
                " WHERE " + MonsterParty.COLUMN_PARTY_ID + " = 1", null);

        //add m to list for each row
        if (cursor.moveToFirst()) {
            do {
                long monsterId = cursor.getLong(0);
                resultMonsters.add(monsterData.get(monsterId));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return resultMonsters;

    }

    public void removeMonsterFromParty(Party p, Monster m){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MonsterParty.TABLE_NAME,
                MonsterParty.COLUMN_MONSTER_ID + " = ? AND " +
                        MonsterParty.COLUMN_PARTY_ID + " = ?",
                new String[]{String.valueOf(m.getId()), String.valueOf(p.getId())}
        );
        db.close();
    }

    /*
    public double averageAttackPower(Party p){
        int sum = 0;
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MonsterParty.TABLE_NAME , null);

        cursor.moveToFirst();
        sum += cursor.getInt();
        sum += cursor.getInt(cursor.getColumnIndex(MonsterParty.COLUMN_MONSTER_ID));

        count++;


        int scale = (int) Math.pow(10, 1);
        return (double) Math.round((sum / count) * scale) / scale;
    }
*/

}
