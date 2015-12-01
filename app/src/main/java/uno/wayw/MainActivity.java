package uno.wayw;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import uno.wayw.data.User;

public class MainActivity extends AppCompatActivity {

    public Button registerButton;
    public Button loginButton;
    public EditText loginName;
    public EditText loginPassword;

    public SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        createInterface();
    }

    public void createInterface(){
        registerButton = (Button) findViewById(R.id.registerButton);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginName = (EditText) findViewById(R.id.loginName);
        loginPassword = (EditText) findViewById(R.id.loginPassword);

        registerButton.setOnClickListener(new View.OnClickListener(){
              @Override
              public void onClick(View v) {
            Log.d("clicked", "hit register");
            // Navigate to RegisterActivity
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        }
        });

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("clicked","hit login");
                Log.d("EditText", loginName.getText().toString());
                Log.d("EditText", loginPassword.getText().toString());

                // Get the User with that login name
                List<User> test = User.getByUserName(loginName.getText().toString());

                for(User t : test){
                Log.d("DataBase", t.userName);
                Log.d("DataBase", t.password);
                }
                Log.d("DataBase Count", " " + test.size());

                //Check to make sure User is in the db and the userName/password are equal to user input
                if ((test.size() != 0) && (test.get(0).userName.equals(loginName.getText().toString()))
                       && (test.get(0).password.equals(loginPassword.getText().toString()))) {

                   //Saving the loggedIn User into SharedPreferences
                   SharedPreferences.Editor editor = preferences.edit();
                   editor.putString("loggedInName", test.get(0).userName);
                   editor.apply();

                   //If the User is in the database then Navigate to FeedActivity
                   startActivity(new Intent(MainActivity.this, FeedActivity.class));
                }
                else {
                   Toast.makeText(getApplicationContext(),
                           "The name or password is not found. Please try again.",
                           Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
