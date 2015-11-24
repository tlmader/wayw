package uno.wayw;

/**
 * Created by Breezy on 11/23/15.
 */

public class GlobalClass extends com.activeandroid.app.Application {
    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
