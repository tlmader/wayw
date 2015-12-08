package uno.wayw;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import uno.wayw.adapter.DetailListAdapter;
import uno.wayw.data.FeedItem;
import uno.wayw.data.User;

public class DetailActivity extends AppCompatActivity {

    public SharedPreferences preferences;
    public User loggedInUser;
    public User detailUser;
    private ListView listView;
    private DetailListAdapter listAdapter;
    private List<FeedItem> detailItems;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.detailList);

        detailItems = new ArrayList<FeedItem>();

        listAdapter = new DetailListAdapter(this, detailItems);
        listView.setAdapter(listAdapter);

        //Gets the current loggedIn User
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //Get the logged user by using SharedPreferences
        String userName = preferences.getString("loggedInName", "");
        String detailName = preferences.getString("detailUser", "");

        List<User> name = User.getByUserName(userName);
        loggedInUser = name.get(0);

        List<User> detailClick = User.getByUserName(detailName);
        detailUser = detailClick.get(0);

        //Pull from the Database
        List<FeedItem> detailFeedFromDB = new ArrayList<>();
        detailFeedFromDB = FeedItem.getByUserName(detailUser.userName);
        for (FeedItem t : detailFeedFromDB) {
            if( !(t.userName.equals(loggedInUser.userName)) )
            {
                detailItems.add(t);
            }
        }
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_feed){
            Log.d("Feed", "Pressed Feed");
            // Navigate to Feed Page
            startActivity(new Intent(this, FeedActivity.class));
            finish();
        }
        else if(id == R.id.action_signOut) {
            // Navigate to Upload Page
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        else if(id == R.id.action_search){
            Log.d("Search", "Pressed Search");
            // Navigate to Search Page
            startActivity(new Intent(this, SearchActivity.class));
            finish();
        }else if (id == R.id.action_upload) {
            Log.d("Upload", "Pressed Upload");
            // Navigate to Upload Page
            startActivity(new Intent(this, UploadActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
