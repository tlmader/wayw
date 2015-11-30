package uno.wayw.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Breezy on 11/29/15.
 */

@Table(name = "FeedItem")
public class FeedItem extends Model{

    //@Column(name = "Id")
    //public int id;

    @Column(name = "UserName")
    public String userName;

    @Column(name = "Status")
    public String status;

    @Column(name = "Image")
    public String image;

    @Column(name = "ProfilePic")
    public String profilePic;

    @Column(name = "TimeStamp")
    public String timeStamp;

    @Column(name = "Url")
    public String url;

    @Column(name = "Genre")
    public String genre;

    public FeedItem() {
    }

    public FeedItem(String name, String image, String status,
                    String profilePic, String timeStamp, String url) {
        super();
        //this.id = id;
        this.userName = name;
        this.image = image;
        this.status = status;
        this.profilePic = profilePic;
        this.timeStamp = timeStamp;
        this.url = url;
    }

    public FeedItem(String name, String image, String status,
                    String profilePic, String timeStamp, String url, String genre) {
        super();
        //this.id = id;
        this.userName = name;
        this.image = image;
        this.status = status;
        this.profilePic = profilePic;
        this.timeStamp = timeStamp;
        this.url = url;
        this.genre = genre;
    }

    //public int getId() {
    //    return id;
    //}

    //public void setId(int id) {
      //  this.id = id;
    //}

    public String getName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = name;
    }

    public String getImge() {
        return image;
    }

    public void setImge(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
