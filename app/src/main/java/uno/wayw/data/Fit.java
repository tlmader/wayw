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
    public User owner;

    public static List<Fit> getAll() {
        return new Select()
                .from(Fit.class)
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
