package uno.wayw;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uno.wayw.data.FeedItem;
import uno.wayw.data.Fit;
import uno.wayw.data.User;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;
    Button uploadButton;
    EditText titleText;
    Spinner stylesSpinner;
    String selectedStyle;
    Uri selectedImage;

    private static final int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = (ImageView) findViewById(R.id.image_upload);
        uploadButton = (Button) findViewById(R.id.button_upload);
        titleText = (EditText) findViewById(R.id.text_upload_title);
        stylesSpinner = (Spinner) findViewById(R.id.spinner);

        imageView.setOnClickListener(this);
        uploadButton.setOnClickListener(this);

        ArrayList<String> styleList = new ArrayList<>();
        //TODO: Add more genre categories
        styleList.add("Select a style...");
        styleList.add("High Fashion");
        styleList.add("Menswear");
        styleList.add("Prep");
        styleList.add("Smart Casual");
        styleList.add("Sportswear");
        styleList.add("Urban");
        styleList.add("Techwear");
        styleList.add("Vintage");
        styleList.add("Other");

        //Create adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, styleList){
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
        stylesSpinner.setAdapter(dataAdapter);

        stylesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedStyle = parent.getItemAtPosition(position).toString();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.image_upload:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                break;
            case R.id.button_upload:
                // Validate fit for upload
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                String currentUser = preferences.getString("loggedInName", "");

                if (selectedImage == null || selectedImage.toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Please upload an image of your fit.",
                        Toast.LENGTH_LONG).show();
                } else if (titleText.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter a title for your fit.",
                            Toast.LENGTH_LONG).show();
                } else if (selectedStyle == null || selectedStyle.equals("Select a style...")) {
                    Toast.makeText(getApplicationContext(), "Please select the style of your fit.",
                            Toast.LENGTH_LONG).show();
                } else if (currentUser == null) {
                    Toast.makeText(getApplicationContext(), "Please reload the app and try again.",
                            Toast.LENGTH_LONG).show();
                } else {
                    // Creates a new User
                    Fit fit = new Fit();

                    // fit.title = titleText.getText().toString();
                    fit.title = convertImage(selectedImage);
                    fit.style = selectedStyle;
                    fit.image = convertImage(selectedImage);
                    fit.owner = currentUser;
                    fit.timestamp = Long.toString(System.currentTimeMillis());
                    fit.save();

                    // Navigate to Login Page
                    startActivity(new Intent(this, FeedActivity.class));
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImage = data.getData();
            imageView.setImageURI(selectedImage);
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_upload, menu);
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
            // Navigate to Feed Page
            startActivity(new Intent(this, FeedActivity.class));
            finish();
        } else if (id == R.id.action_search) {
            Log.d("Search", "Pressed Search");
            // Navigate to Search Page
            startActivity(new Intent(this, SearchActivity.class));
            finish();
        } else if(id == R.id.action_logoff) {
            // Navigate to Main Page
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private String convertImage(Uri uri) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte [] b = baos.toByteArray();
            String temp = Base64.encodeToString(b, Base64.DEFAULT);
            return temp;
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Error uploading image!",
                    Toast.LENGTH_LONG).show();
            return null;
        }
    }
}