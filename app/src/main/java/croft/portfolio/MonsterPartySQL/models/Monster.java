package croft.portfolio.MonsterPartySQL.models;

        import android.os.Parcelable;
        import android.os.Parcel;

/**
 * Created by Michaels on 22/3/2016.
 */

public class Monster implements Parcelable {

    //database

    public static final String TABLE_NAME = "monsters";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ATTACK_POWER = "attack_power";
    public static final String COLUMN_TYPE = "type";

    public static final String CREATE_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_TYPE + " TEXT NOT NULL, " +
                    COLUMN_ATTACK_POWER + " INTEGER NOT NULL" +
                    ")";

    private static long monster_id_counter = 1000;

    private long _id;
    private String name;
    private String type;
    int attackPower;

//default
    public Monster (){
        setId(0);
        setName("Unknown");
        setType("Unknown");
        setAttackPower(0);
    }

    //for brand new monster, auto assign id number
    public Monster(String name, String type, int attackPower){
        this._id = monster_id_counter++;
        this.name = name;
        this.type = type;
        this.attackPower = attackPower;

    }
    //database constructor so we can recreate monster from db with same id
    public Monster(long id, String name, String type, int attackPower){
        this._id = id;
        this.name = name;
        this.type = type;
        this.attackPower = attackPower;

    }

    public Monster(Parcel in){
        //read values in same order as written
        setId(in.readLong());
        setName(in.readString());
        setType(in.readString());
        setAttackPower(in.readInt());
    }

    public static final Creator<Monster> CREATOR = new Creator<Monster>() {
        @Override
        public Monster createFromParcel(Parcel in) {
            return new Monster(in);
        }
        public Monster [] newArray(int size){
            return new Monster[size];

        }
    };

    public int describeContents(){
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags){
        parcel.writeLong(_id);
        parcel.writeString(name);
        parcel.writeString(type);

        parcel.writeInt(attackPower);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getAttackPower(){
        return attackPower;
    }

    public long getId(){
        return _id;
    }

    public void setId(long id){
        this._id = id;
    }

}

