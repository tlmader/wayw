package uno.wayw;

import java.util.ArrayList;

/**
 * Created by Breezy on 10/26/15.
 */
public class User {

    public String name;
    public String userName;
    public String password;
    public ArrayList friends;

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

}
