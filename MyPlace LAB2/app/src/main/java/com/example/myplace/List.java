package com.example.myplace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class List extends AppCompatActivity {

    ArrayList<String> places;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        places = new ArrayList<String>();
        places.add("Tvrdjava");
        places.add("Cair");
        places.add("Park Svetog Save");
        places.add("Trg Kralja Milana");
        ListView lw= (ListView)findViewById(R.id.listaaa);
        lw.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,places));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_places_menu, menu);
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
        else if (id == R.id.new_place) {
            Toast.makeText(this, "New Place!", Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.show_map){
            Toast.makeText(this,"Show Map!", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
