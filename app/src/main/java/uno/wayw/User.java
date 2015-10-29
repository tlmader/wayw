package uno.wayw;

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
public class User extends Model{

    @Column(name = "Name")
    public String name;
    @Column(name = "UserName")
    public String userName;
    @Column(name = "Password")
    public String password;
    public ArrayList<String> friends;
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

    public ArrayList getFriends() {
        return friends;
    }

    public void setFriends(ArrayList friends) {
        this.friends = friends;
    }

    public static List<User> getAll() {
        return new Select()
                .from(User.class)
                .execute();
    }

    public static List<User> getByUserName(String searchUserName){
        return new Select()
                .from(User.class)
                .where("UserName = ?", searchUserName )
                .execute();
    }

}
