package com.example.myplace;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ViewPlace extends AppCompatActivity {


    static int NEW_PLACE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_place);
        Button okBtn = (Button)findViewById(R.id.ok_button);
        TextView nameText = (TextView)findViewById(R.id.name_text);
        TextView descText = (TextView)findViewById(R.id.desc_text);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        int position = -1;
        try{
            Intent listIntent = getIntent();
            Bundle positionBundle = listIntent.getExtras();
            position = positionBundle.getInt("position");
        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            finish();
        }
        if(position >= 0){
            MyPlace place = MyPlacesData.getInstance().getPlace(position);
            nameText.setText(place.getName());
            descText.setText(place.getDescription());
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.maps_menu, menu);
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
            Intent i = new Intent(this, EditMyPlace.class);
            startActivityForResult(i,NEW_PLACE);
        }
        else if (id == R.id.my_places){
            Toast.makeText(this,"My Places!", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, List.class);
            startActivity(i);
        }
        else if (id == R.id.show_map){
            Toast.makeText(this,"Show Map!", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, MyPlacesMaps.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_PLACE){
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(this,"New Place Added!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
