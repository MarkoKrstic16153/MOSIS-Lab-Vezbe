package com.example.myplace;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

public class List extends AppCompatActivity {

    static int NEW_PLACE = 1;
    ListView lw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        lw= (ListView)findViewById(R.id.listaaa);
        //Toolbar tb=(Toolbar)findViewById(R.id.toolbar);
        //setSupportActionBar(tb);

        lw.setAdapter(new ArrayAdapter<MyPlace>(this,android.R.layout.simple_list_item_1,MyPlacesData.getInstance().getMyPlaces()));
        lw.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener(){
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                MyPlace place = MyPlacesData.getInstance().getPlace(info.position);
                menu.setHeaderTitle(place.getName());
                menu.add(0,1,1,"View Place");
                menu.add(0,2,2,"Edit Place");
                menu.add(0,3,3,"Delete Place");
                menu.add(0,4,4,"Show on Map");

            }


           /* public  boolean onContextItemSelected(MenuItem item){

                return List.super.onContextItemSelected(item);
            }*/

        });





        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyPlace place = (MyPlace)parent.getAdapter().getItem(position);
                Bundle positionBundle = new Bundle();
                positionBundle.putInt("position",position);
                Intent intent = new Intent(getApplicationContext(),ViewPlace.class);
                intent.putExtras(positionBundle);
                startActivity(intent);
            }


        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        Bundle positionBundle = new Bundle();
        positionBundle.putInt("position",info.position);
        Intent i = null;
        if(item.getItemId() == 1){
            i = new Intent(this,ViewPlace.class);
            i.putExtras(positionBundle);
            startActivity(i);
        }
        else if (item.getItemId() == 2){
            i = new Intent(this,EditMyPlace.class);
            i.putExtras(positionBundle);
            startActivityForResult(i,1);
        }
        else if (item.getItemId() == 3){
            MyPlacesData.getInstance().deletePlace(info.position);
            lw.setAdapter(new ArrayAdapter<MyPlace>(this,android.R.layout.simple_list_item_1,MyPlacesData.getInstance().getMyPlaces()));
        }
        else if (item.getItemId() == 4){
            Intent intent = new Intent(this,MyPlacesMaps.class);
            intent.putExtra("state",MyPlacesMaps.CENTER_PLACE_ON_MAP);
            MyPlace mp = MyPlacesData.getInstance().getPlace(info.position);
            intent.putExtra("lat",mp.getLatitude());
            intent.putExtra("lon",mp.getLongitude());
            startActivity(intent);
        }
        return super.onContextItemSelected(item);
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
            Intent i = new Intent(this,EditMyPlace.class);
            startActivityForResult(i,NEW_PLACE);
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
                lw.setAdapter(new ArrayAdapter<MyPlace>(this,android.R.layout.simple_list_item_1,MyPlacesData.getInstance().getMyPlaces()));
                Toast.makeText(this,"New Place Added!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
