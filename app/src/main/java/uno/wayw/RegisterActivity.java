package uno.wayw;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uno.wayw.data.User;

public class RegisterActivity extends AppCompatActivity {

    public Button saveRegistrationButton;
    public EditText regName;
    public EditText regUserName;
    public EditText regPassword;
    private Spinner spinner;
    public String genreSelection;

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
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayList<String> genreCategories = new ArrayList<>();
        //TODO: Add more genre categories
        genreCategories.add("Select a genre...");
        genreCategories.add("Casual");
        genreCategories.add("Formal");
        genreCategories.add("Sporty");
        //Create adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genreCategories){
            @Override
            public boolean isEnabled (int position){
             if(position == 0){
                 return false;
             }
             else{
                 return true;
             }
            }
            @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent){
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    tv.setTextColor(Color.GRAY);
                }
                else{
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        //Drop down layout type
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    genreSelection = parent.getItemAtPosition(position).toString();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        saveRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("clicked", "completed registration");
                Log.d("EditText", regName.getText().toString());
                Log.d("EditText", regUserName.getText().toString());
                Log.d("EditText", regPassword.getText().toString());

                List<User> test = User.getByUserName(regUserName.getText().toString());

                // Check to make sure User Name is not already in use
                if ( (test.size() == 0) && (genreSelection != null) ) {
                    // Creates a new User
                    User newUser = new User();

                    newUser.name = regName.getText().toString();
                    newUser.userName = regUserName.getText().toString();
                    newUser.password = regPassword.getText().toString();
                    newUser.genre = genreSelection;
                    newUser.save();

                    // Navigate to Login Page
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                } else if (!(test.size() == 0)) {
                    Toast.makeText(getApplicationContext(), "Sorry, username is already in use. Please try again.",
                            Toast.LENGTH_LONG).show();
                }
                else if (genreSelection == null) {
                    Toast.makeText(getApplicationContext(), "Sorry, genre has not been selected. Please try again.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
