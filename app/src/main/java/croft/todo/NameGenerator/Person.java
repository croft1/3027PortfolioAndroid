package croft.todo.NameGenerator;


        import android.os.Parcel;
        import android.os.Parcelable;

        import java.util.ArrayList;

/**
 * Created by Michaels on 22/3/2016.
 */


public class Person implements Parcelable {

    String firstName;
    String lastName;
    String maidenName;
    String birthPlace;
    String favBrand;

//    String newFirstName;
//    String newLastName;
//    String newPlanetName;


    public Person(){

        setBirthPlace("Unknown");
        setFavBrand("Unknown");
        setFirstName("Unknown");
        setLastName("Unknown");
        setMaidenName("Unknown");

    }

    public Person(Parcel in){

        setFirstName(in.readString());
        setLastName(in.readString());
        setMaidenName(in.readString());
        setBirthPlace(in.readString());
        setFavBrand(in.readString());
    }

    public Person(String firstName, String lastName, String maiden, String birthplace, String brand){

        setBirthPlace(birthplace);
        setFavBrand(brand);
        setFirstName(firstName);
        setLastName(lastName);
        setMaidenName(maiden);

    }

    public String generateNewFirstName(){
        return (getFirstName().substring(0, 3) + getLastName().substring(0, 2).toLowerCase());
    }

    public String generateNewLastName(){
        return (getMaidenName().substring(0, 2) + getBirthPlace().substring(0, 3).toLowerCase());
    }

    public String generatedNewPlanetName(){
        return((getLastName().substring
                ((getLastName().length())- 2)) + getFavBrand().toLowerCase());
    }

    public String getFavBrand() {
        return favBrand;
    }

    public void setFavBrand(String favBrand) {
        this.favBrand = favBrand;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getMaidenName() {
        return maidenName;
    }

    public void setMaidenName(String mMaidenName) {
        this.maidenName = mMaidenName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }
        public Person [] newArray(int size){
            return new Person[size];

        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(maidenName);
        dest.writeString(birthPlace);
        dest.writeString(favBrand);

    }
}

