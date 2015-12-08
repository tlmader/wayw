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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import uno.wayw.adapter.FeedListAdapter;
import uno.wayw.data.Fit;
import uno.wayw.data.User;

public class FeedActivity extends AppCompatActivity {
    private static final String TAG = FeedActivity.class.getSimpleName();
    public SharedPreferences preferences;
    public User loggedInUser;
    private ListView listView;
    private FeedListAdapter listAdapter;
    private List<Fit> feedItems;
    private String URL_FEED = "http://api.androidhive.info/feed/feed.json";

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.list);

        feedItems = new ArrayList<>();

        listAdapter = new FeedListAdapter(this, feedItems);
        listView.setAdapter(listAdapter);

        //Gets the current loggedIn User
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //Get the logged user by using SharedPreferences
        String userName = preferences.getString("loggedInName", "");
        List<User> name = User.getByUserName(userName);
        loggedInUser = name.get(0);

        //Pull from the Database
        List<Fit> feedFromDB = Fit.getAll();
        for (Fit t : feedFromDB) {
            feedItems.add(t);
        }
        listAdapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User clickedUser = (User)listAdapter.getItem(position);

                //Saving the detail User into SharedPreferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("detailUser", clickedUser.userName);
                editor.apply();

                // Navigate to Detail Page
                startActivity(new Intent(FeedActivity.this, DetailActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_upload) {
            Log.d("Upload", "Pressed Upload");
            // Navigate to Upload Page
            startActivity(new Intent(this, UploadActivity.class));
            finish();
        }
        else if(id == R.id.action_logoff) {
            // Navigate to Main Page
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        else if (id == R.id.action_search) {
            Log.d("Search", "Pressed Search");
            // Navigate to Search Page
            startActivity(new Intent(this, SearchActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}