package uno.wayw;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;
    Button uploadButton;
    EditText nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = (ImageView) findViewById(R.id.image_upload);
        uploadButton = (Button) findViewById(R.id.button_upload);
        nameText = (EditText) findViewById(R.id.text_upload);

        imageView.setOnClickListener(this);
        uploadButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

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

        if (id == R.id.action_feed) {
            Log.d("Feed", "Pressed Feed");
            // Navigate to Upload Page
            startActivity(new Intent(this, FeedActivity.class));
            finish();
        } else if (id == R.id.action_search) {
            Log.d("Search", "Pressed Search");
            // Navigate to Search Page
            startActivity(new Intent(this, SearchActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
