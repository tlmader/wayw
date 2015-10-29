package uno.wayw;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    public Button saveRegistrationButton;
    public EditText name; //Might want two fields for first and last name?
    public EditText userName;
    public EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        registerInformation();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void registerInformation(){
        saveRegistrationButton = (Button) findViewById(R.id.finishedRegistrationButton);
        name = (EditText) findViewById(R.id.registerName);
        userName = (EditText) findViewById(R.id.registerUserName);
        password = (EditText) findViewById(R.id.registerPassword);

        saveRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("clicked", "completed registration");
                //TODO: Save name, userName, and password to the database
                Log.d("EditText", name.getText().toString());
                Log.d("EditText", userName.getText().toString());
                Log.d("EditText", password.getText().toString());
                //Creates a new User
                User newUser = new User();
                newUser.setName(name.getText().toString());
                newUser.setUserName(userName.getText().toString());
                newUser.setPassword(password.getText().toString());
                //Navigate to Login Page
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
    }

}
