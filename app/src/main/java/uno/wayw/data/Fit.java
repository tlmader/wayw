package uno.wayw.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Ted on 12/8/2015.
 */
@Table(name = "Fit")
public class Fit extends Model {

    @Column(name = "Title")
    public String title;

    @Column(name = "Style")
    public String style;

    @Column(name = "Image")
    public String image;

    @Column(name = "Owner")
    public String owner;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Column(name = "Timestamp")
    public String timestamp;

    public static List<Fit> getAll() {
        return new Select()
                .from(Fit.class)
                .execute();
    }

    public static List<Fit> getByFilterOwner(String searchTerm){
        return new Select()
                .from(Fit.class)
                .where("Owner LIKE ?", new String[]{'%' + searchTerm + '%'})
                .orderBy("Title ASC")
                .execute();
    }

    public static List<Fit> getByFilterTitle(String searchTerm){
        return new Select()
                .from(Fit.class)
                .where("Title LIKE ?", new String[]{'%' + searchTerm + '%'})
                .orderBy("Title ASC")
                .execute();
    }

    public static List<Fit> getByFilterStyle(String searchTerm){
        return new Select()
                .from(Fit.class)
                .where("Style = ?", searchTerm)
                .orderBy("Title ASC")
                .execute();
    }
}
