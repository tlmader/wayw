package uno.wayw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import uno.wayw.data.User;

public class RegisterActivity extends AppCompatActivity {

    public Button saveRegistrationButton;
    public EditText regName;
    public EditText regUserName;
    public EditText regPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        createInterface();
    }

    public void createInterface(){
        saveRegistrationButton = (Button) findViewById(R.id.finishedRegistrationButton);
        regName = (EditText) findViewById(R.id.registerName);
        regUserName = (EditText) findViewById(R.id.registerUserName);
        regPassword = (EditText) findViewById(R.id.registerPassword);

        saveRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("clicked", "completed registration");
                Log.d("EditText", regName.getText().toString());
                Log.d("EditText", regUserName.getText().toString());
                Log.d("EditText", regPassword.getText().toString());

                List<User> test = User.getByUserName(regUserName.getText().toString());

                // Check to make sure User Name is not already in use
                if (test.size() == 0) {
                    // Creates a new User
                    User newUser = new User();

                    newUser.name = regName.getText().toString();
                    newUser.userName = regUserName.getText().toString();
                    newUser.password = regPassword.getText().toString();
                    newUser.save();

                    // Navigate to Login Page
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Sorry, username is already in use. Please try again.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
