package croft.todo.MonsterParty;

        import android.os.Parcelable;
        import android.os.Parcel;

/**
 * Created by Michaels on 22/3/2016.
 */

public class Monster implements Parcelable {

    String name;
    String type;
    int level;

    int attackPower;


    public Monster (){
        setName("Unknown");
        setType("Unknown");
        setLevel(0);
        setAttackPower(0);
    }

    public Monster(Parcel in){
        //read values in same order as written

        setName(in.readString());
        setType(in.readString());
        setLevel(in.readInt());
        setAttackPower(in.readInt());
    }

    public Monster(String name, String type, int level, int attackPower){
        setName(name);
        setType(type);
        setLevel(level);
        setAttackPower(attackPower);
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
        parcel.writeString(name);
        parcel.writeString(type);
        parcel.writeInt(level);
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getAttackPower(){
        return attackPower;
    }
}
