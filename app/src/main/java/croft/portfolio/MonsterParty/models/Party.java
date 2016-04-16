package croft.portfolio.MonsterParty.models;

/**
 * Created by Michaels on 16/4/2016.
 */
public class Party {

    public static final String TABLE_NAME = "parties";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String CREATE_STATEMENT =
            "CREATE TABLE " + TABLE_NAME +
                    "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_NAME + " TEXT NOT NULL" +
                    ")";

    private long _id;
    private String name;

    public Party( long id, String name) {
        this._id = id;
        this.name = name;
    }

    public Party(String name){
        this.name = name;
    }

    public long getId() {
        return _id;
    }
    public void setId(long id) {
        this._id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
    }
}
