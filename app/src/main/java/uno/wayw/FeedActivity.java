package uno.wayw;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class FeedActivity extends AppCompatActivity implements FeedActivityFragment.FeedActivityFragmentItemCLickListener{

    //Global Variable logged in user
    final GlobalClass currentUser = (GlobalClass) getApplicationContext();
    public User loggedInUser = currentUser.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onItemClick(User friend) {
            Intent intent = new Intent(this, DetailUserActivity.class);
            intent.putExtra(DetailUserActivity.CONTACT_ARG, friend);

            startActivity(intent);
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }
}
