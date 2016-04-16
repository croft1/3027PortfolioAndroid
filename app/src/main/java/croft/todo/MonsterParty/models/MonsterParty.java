package croft.todo.MonsterParty.models;

/**
 * Created by Michaels on 16/4/2016.
 */
public class MonsterParty {

    public static final String TABLE_NAME = "monster_parties";
    public static final String COLUMN_MONSTER_ID = "monster_id";
    public static final String COLUMN_PARTY_ID = "party_id";

    public static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME +
            "(" +
            COLUMN_MONSTER_ID + " INTEGER NOT NULL, " +
            COLUMN_PARTY_ID + " INTEGER NOT NULL, " +
            "PRIMARY KEY ("  + COLUMN_MONSTER_ID + ", " + COLUMN_PARTY_ID +
            "))";

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    private Monster monster;
    private Party party;

    public MonsterParty(Monster monster, Party party){
        this.monster = monster;
        this.party = party;
    }


}



