package uno.wayw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class FeedActivity extends AppCompatActivity implements FeedActivityFragment.FeedActivityFragmentItemCLickListener{

    //Global Variable logged in user
    //public GlobalClass currentUser;
    //public SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //currentUser = (GlobalClass) getApplicationContext();
        //Log.d("GlobalGET", currentUser.getCurrentUser().name);
        //preferences = PreferenceManager.getDefaultSharedPreferences(this);

    }

    @Override
    public void onItemClick(User friend) {
            Intent intent = new Intent(this, DetailUserActivity.class);
            intent.putExtra(DetailUserActivity.FRIEND_ARG, friend);

            startActivity(intent);
    }

    public User getLoggedInUser(){
         //System.out.println(currentUser.getCurrentUser());

        try {
            //return currentUser.getCurrentUser();

        }
        catch (Exception e){

        }
        return null;
    }
}
