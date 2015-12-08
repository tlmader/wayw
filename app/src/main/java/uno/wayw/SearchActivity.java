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
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import uno.wayw.adapter.SearchListAdapter;
import uno.wayw.data.Fit;
import uno.wayw.data.User;

public class SearchActivity extends AppCompatActivity {

    public SharedPreferences preferences;
    public User loggedInUser;
    private ListView listView;
    private SearchListAdapter listAdapter;
    private List<User> searchItems;
    public List<User> styleUser;

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
        listAdapter.notifyDataSetChanged();

        SearchView searchView = (SearchView) findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Filter", query);
                searchItems.clear();
                List<User> filterUserName = new ArrayList<User>();
                filterUserName = User.getByFilterUserName(query);
                for (User t : filterUserName) {
                    if (!(t.userName.equals(loggedInUser.userName))) {
                        if(!searchItems.contains(t)) {
                            searchItems.add(t);
                        }
                    }
                }
                List<User> filterName = new ArrayList<User>();
                filterName = User.getByFilterName(query);
                for (User t : filterName) {
                    if (!(t.userName.equals(loggedInUser.userName))) {
                        if(! searchItems.contains(t)) {
                            searchItems.add(t);
                        }
                    }
                }
                List<Fit> filterFit = new ArrayList<>();
                filterFit = Fit.getByFilterStyleSearch(query);
                for (Fit t : filterFit) {
                    if (!(t.owner.equals(loggedInUser.userName))) {
                        styleUser = new ArrayList<User>();
                        styleUser = User.getByUserName(t.owner);

                    }
                }
                Log.d("Style", "" + styleUser.size());

                if(styleUser != null) {
                    for (User u : styleUser) {
                        if (!searchItems.contains(u)) {
                            Log.d("Style", "" + searchItems.size());
                            searchItems.add(u);
                        }
                    }
                }
                listAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                //Populating the list with all the Users again
                searchItems.clear();
                List<User> usersFromDB = new ArrayList<>();
                usersFromDB = User.getAll();
                for (User t : usersFromDB) {
                    if (!(t.userName.equals(loggedInUser.userName))) {
                        searchItems.add(t);
                    }
                }
                listAdapter.notifyDataSetChanged();
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User clickedUser = (User)listAdapter.getItem(position);

                //Saving the detail User into SharedPreferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("detailUser", clickedUser.userName);
                editor.apply();

                // Navigate to Detail Page
                startActivity(new Intent(SearchActivity.this, DetailActivity.class));
            }
        });
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
            startActivity(new Intent(this, FeedActivity.class));
            finish();
        } else if (id == R.id.action_upload) {
            Log.d("Upload", "Pressed Upload");
            // Navigate to Upload Page
            startActivity(new Intent(this, UploadActivity.class));
            finish();
        } else if(id == R.id.action_logoff) {
            // Navigate to Main Page
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
