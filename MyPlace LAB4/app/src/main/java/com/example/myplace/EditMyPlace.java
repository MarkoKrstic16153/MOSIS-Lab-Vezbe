package com.example.myplace;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditMyPlace extends AppCompatActivity {

    boolean editMode = true;
    int position = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_place);
        try {
            Intent i = getIntent();
            Bundle positionBundle = i.getExtras();
            if(positionBundle != null){
                position = positionBundle.getInt("position");
            }
            else{
                editMode = false;
            }
        }
        catch (Exception e){
            editMode = false;
        }





        Button cancelBtn = (Button)findViewById(R.id.cancel_button);
        Button addBtn = (Button)findViewById(R.id.add_button);
        Button locationBtn = (Button)findViewById(R.id.button2);

        final EditText nameText= (EditText)findViewById(R.id.name_text);
        final EditText descText = (EditText)findViewById(R.id.desc_text);
        final EditText latText= (EditText)findViewById(R.id.lat);
        final EditText longText = (EditText)findViewById(R.id.longi);
        if(!editMode){
            addBtn.setText("Add New Place");
        }
        if(position>=0){
            addBtn.setText("Change Place");
            MyPlace place = MyPlacesData.getInstance().getPlace(position);
            nameText.setText(place.getName());
            descText.setText(place.getDescription());
        }

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nameText.getText().toString().equals("") && !descText.getText().toString().equals("")) {
                    MyPlace newPlace = new MyPlace(nameText.getText().toString(), descText.getText().toString());
                    if(!editMode) {
                        newPlace.setLatitude(latText.getText().toString());
                        newPlace.setLongitude(longText.getText().toString());
                        MyPlacesData.getInstance().addNewPlace(newPlace);
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                    else if (!nameText.getText().toString().equals("") && !descText.getText().toString().equals("")){
                        MyPlace editPlace = MyPlacesData.getInstance().getPlace(position);
                        editPlace.setName(nameText.getText().toString());
                        editPlace.setDescription(descText.getText().toString());
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Wrong Input", Toast.LENGTH_LONG).show();
                }
            }
        });
        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             fja();
            }
        });
    }
    private void fja(){
        Intent i = new Intent(this,MyPlacesMaps.class);
        i.putExtra("state",MyPlacesMaps.SELECT_COORDINATES);
        startActivityForResult(i,1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_my_places_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.about){
            Toast.makeText(this,"About!", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this,About.class);
            startActivity(i);
        }
        else if (id == R.id.new_place){
            Toast.makeText(this,"New Place!", Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.my_places){
            Toast.makeText(this,"My Places!", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, List.class);
            startActivity(i);
        }
        else if (id == R.id.show_map){
            Toast.makeText(this,"Show Map!", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, MyPlacesMaps.class);
            i.putExtra("state",MyPlacesMaps.SHOW_MAP);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(this,"AAAAAAA!", Toast.LENGTH_LONG).show();
        try {
            if (resultCode == Activity.RESULT_OK) {
                String lon = data.getExtras().getString("lon");
                String lat = data.getExtras().getString("lat");
                final EditText latText = (EditText) findViewById(R.id.lat);
                final EditText longText = (EditText) findViewById(R.id.longi);
                latText.setText(lat);
                longText.setText(lon);
            }
        }
        catch (Exception e){
            //Toast.makeText(this,"BBBBBBBB!", Toast.LENGTH_LONG).show();
        }
    }
}
