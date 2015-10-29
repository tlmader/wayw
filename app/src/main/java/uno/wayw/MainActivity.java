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
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public Button registerButton;
    public Button loginButton;
    public EditText loginName;
    public EditText loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Call register method
        register();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void register(){
        registerButton = (Button) findViewById(R.id.registerButton);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginName = (EditText) findViewById(R.id.loginName);
        loginPassword = (EditText) findViewById(R.id.loginPassword);

        registerButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Log.d("clicked","hit register");
                        //Navigate to RegisterActivity
                        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                    }
                }
        );

        loginButton.setOnClickListener(new View.OnClickListener(){
                  @Override
                  public void onClick(View v) {
                      Log.d("clicked","hit login");
                      Log.d("EditText", loginName.getText().toString());
                      Log.d("EditText", loginPassword.getText().toString());

                      //Get the User with that login name
                      List<User> test = User.getByUserName(loginName.getText().toString());

                      /**
                      for(User t : test){
                          Log.d("DataBase", t.userName);
                          Log.d("DataBase", t.password);
                      }
                      Log.d("DataBase Count" , " " + test.size());
                       **/

                      //Check to make sure User is in the db and the userName/password are equal to user input
                      if( (test.size() != 0 ) && (test.get(0).userName.equals(loginName.getText().toString()))
                              && (test.get(0).password.equals(loginPassword.getText().toString()))  ){
                          //If the User is in the database then Navigate to FeedActivity
                          startActivity(new Intent(MainActivity.this, FeedActivity.class));
                      }
                      else{
                          Toast.makeText(getApplicationContext(), "The name or password is not found. Please try again.",
                                  Toast.LENGTH_LONG).show();
                      }
                  }
              }
        );

    }
}
