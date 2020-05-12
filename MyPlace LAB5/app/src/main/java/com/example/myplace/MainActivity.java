package com.example.myplace;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.fragment.NavHostFragment;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static int NEW_PLACE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
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
            i.putExtra("state",MyPlacesMaps.SHOW_MAP);
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
