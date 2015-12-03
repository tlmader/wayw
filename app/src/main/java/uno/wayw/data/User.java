package uno.wayw.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Breezy on 11/29/15.
 */

@Table(name = "User")
public class User extends Model{

    @Column(name = "Name")
    public String name;

    @Column(name = "UserName")
    public String userName;

    @Column(name = "Password")
    public String password;

    @Column(name = "Genre")
    public String genre;

    @Column(name = "ProfilePic")
    public String profilePic;

    //@Column(name = "FeedItemId")
    //public ArrayList<Integer> feedItemId;

    //@Column(name = "ConnectionId")
    //public ArrayList<Integer> connectionId;

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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

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
}
