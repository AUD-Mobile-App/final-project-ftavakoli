package com.example.ftavakoli.bucketlistapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ItemListActivity extends AppCompatActivity {

    //declaring the variable
    ListView listView;
    FloatingActionButton addFab;
    DatabaseReference databaseReference;
    String title;
    boolean status;
    private FirebaseAuth firebaseAuth;
    String TAG = "Hi";

    //creating array of items, each one of them is ItemDataStore Object,
    //and each object has whatever the ItemDataStore has
    public static ArrayList<Item> arrayList;
    ArrayList<Item> checkedList;
    ArrayList<Item> unCheckedList;

    //List for checked
    //list for unchecked


    ListAdapter adapter;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sign_out) {
            firebaseAuth.signOut();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        //initializing the variables

        listView = findViewById(R.id.listView);
        addFab = findViewById(R.id.addFab);
        arrayList = new ArrayList<Item>();
        checkedList = new ArrayList<Item>();
        unCheckedList = new ArrayList<Item>();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //setting the reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Students")
                .child(user.getUid());
        //intializing the list adapter
        adapter = new ListAdapter(ItemListActivity.this,arrayList, databaseReference);
        listView.setAdapter(adapter);
        // Read from the database which is ordered by date
        databaseReference.orderByChild("date").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                arrayList.clear();
                checkedList.clear();
                unCheckedList.clear();
                adapter.notifyDataSetChanged();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot d : dataSnapshot.getChildren()) {
                    Item value = d.getValue(Item.class);
                    Log.d(TAG, "Value is: " + value.getTitle());

                    if(value.isStatus()){
                        checkedList.add(value);
                    }else{
                        unCheckedList.add(value);
                    }


                }
                arrayList.addAll(unCheckedList);
                arrayList.addAll(checkedList);

                adapter.notifyDataSetChanged();




            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



            addFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ItemListActivity.this, AddItemActivity.class);
                    startActivity(intent);
                    //Toast.makeText(ItemListActivity.this, "U are in the AddItemActivity", Toast.LENGTH_SHORT).show();

                }
            });


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ItemListActivity.this, EditItemActivity.class);
                    intent.putExtra("ID", position);
                    startActivity(intent);

                }
            });





    }
}
