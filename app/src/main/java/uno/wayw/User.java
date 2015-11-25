package uno.wayw;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Breezy on 10/26/15.
 */

@Table(name = "User")
public class User extends Model implements Parcelable{

    //@Column(name = "Id")
    //public long id;

    @Column(name = "Name")
    public String name;

    @Column(name = "UserName")
    public String userName;

    @Column(name = "Password")
    public String password;

    @Column(name = "Friends")
    public ArrayList<User> friends;

    @Column(name = "Genre")
    public String genre;

    //TODO:Add Genre to Users
    //public Genre genre;

    public User(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(userName);
    }

    public static final Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            User mUser = new User();
            mUser.userName = in.readString();
            return mUser;
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public static List<User> getAll() {
        return new Select()
                .from(User.class)
                .execute();
    }

    public static List<User> getByUserName(String searchUserName){
        return new Select()
                .from(User.class)
                .where("UserName = ?", searchUserName)
                .execute();
    }

    public static List<User> getAllFriends() {
        return new Select("Friends")
                .from(User.class)
                .execute();
    }

}
