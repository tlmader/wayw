package uno.wayw;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailUserActivity extends AppCompatActivity {

    static final String FRIEND_ARG = "friend_arg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        // should use the getExtras to fetch the data put in via
        // Intent.putExtra()
        User friend = getIntent().getExtras().getParcelable(FRIEND_ARG);
        Fragment fragment;

        if (friend != null) {
            fragment = DetailUserActivityFragment.newInstance(friend);
        } else {
            fragment = new DetailUserActivityFragment();
        }

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_holder, fragment)
                .commit();

        /**
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
         **/
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
