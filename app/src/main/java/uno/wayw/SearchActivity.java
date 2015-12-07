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

import uno.wayw.adapter.SearchListAdapter;
import uno.wayw.data.User;

public class SearchActivity extends AppCompatActivity {

    public SharedPreferences preferences;
    public User loggedInUser;
    private ListView listView;
    private SearchListAdapter listAdapter;
    private List<User> searchItems;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.searchList);

        searchItems = new ArrayList<User>();

        listAdapter = new SearchListAdapter(this, searchItems);
        listView.setAdapter(listAdapter);

        //Gets the current loggedIn User
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //Get the logged user by using SharedPreferences
        String userName = preferences.getString("loggedInName", "");
        List<User> name = User.getByUserName(userName);
        loggedInUser = name.get(0);

        //Pull from the Database
        List<User> usersFromDB = new ArrayList<>();
        usersFromDB = User.getAll();
        for (User t : usersFromDB) {
            if( !(t.userName.equals(loggedInUser.userName)) )
            {
                searchItems.add(t);
            }
        }
        searchItems = usersFromDB;
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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
            // Navigate to Search Page
            startActivity(new Intent(SearchActivity.this, FeedActivity.class));
        } else if (id == R.id.action_upload) {
            Log.d("Upload", "Pressed Upload");
            // Navigate to Upload Page
            startActivity(new Intent(SearchActivity.this, UploadActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

}
