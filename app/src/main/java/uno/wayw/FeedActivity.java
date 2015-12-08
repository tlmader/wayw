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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uno.wayw.adapter.FeedListAdapter;
import uno.wayw.data.FeedItem;
import uno.wayw.data.User;

public class FeedActivity extends AppCompatActivity {
    private static final String TAG = FeedActivity.class.getSimpleName();
    public SharedPreferences preferences;
    public User loggedInUser;
    private ListView listView;
    private FeedListAdapter listAdapter;
    private List<FeedItem> feedItems;
    private String URL_FEED = "http://api.androidhive.info/feed/feed.json";

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.list);

        feedItems = new ArrayList<FeedItem>();

        listAdapter = new FeedListAdapter(this, feedItems);
        listView.setAdapter(listAdapter);

        //Gets the current loggedIn User
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //Get the logged user by using SharedPreferences
        String userName = preferences.getString("loggedInName", "");
        List<User> name = User.getByUserName(userName);
        loggedInUser = name.get(0);

        //TODO: Need to get the string to pull from the phone Photos

        //TESTING PURPOSES -- Mocking adding a FeedItem to the DB
         /**
         FeedItem test1FeedItem = new FeedItem("teddy", "http://api.androidhive.info/feed/img/cosmos.jpg", "Please let this work",
         "http://api.androidhive.info/feed/img/nat.jpg", "1403375851930", "http://www.google.com");
         FeedItem test2FeedItem = new FeedItem("E", "http://api.androidhive.info/feed/img/nav_drawer.jpg", "Please let this work",
         "http://api.androidhive.info/feed/img/nat.jpg", "1403375851930", "http://www.google.com");
         FeedItem test3FeedItem = new FeedItem("teddy", "http://api.androidhive.info/feed/img/discovery_mos.jpg", "Please let this work",
         "http://api.androidhive.info/feed/img/nat.jpg", "1403375851930", "http://www.google.com");
         test1FeedItem.save();
         test2FeedItem.save();
         test3FeedItem.save();
          **/
        //Pull from the Database
        List<FeedItem> feedFromDB = new ArrayList<>();
        feedFromDB = FeedItem.getAll();
        for (FeedItem t : feedFromDB) {
            feedItems.add(t);
        }
        feedItems = feedFromDB;
        listAdapter.notifyDataSetChanged();

/**
 * USED WITH JSON

        // We first check for cached request
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL_FEED);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URL_FEED, (String) null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        parseJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
        }
**/
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

    /**
     * Parsing json reponse and passing the data to feed view list adapter
     * */
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("feed");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                FeedItem item = new FeedItem();
                //item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("name"));

                // Image might be null sometimes
                String image = feedObj.isNull("image") ? null : feedObj
                        .getString("image");
                item.setImage(image);
                item.setStatus(feedObj.getString("status"));
                item.setProfilePic(feedObj.getString("profilePic"));
                item.setTimeStamp(feedObj.getString("timeStamp"));

                // url might be null sometimes
                String feedUrl = feedObj.isNull("url") ? null : feedObj
                        .getString("url");
                item.setUrl(feedUrl);

                feedItems.add(item);
            }

            // notify data changes to list adapater
            listAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}