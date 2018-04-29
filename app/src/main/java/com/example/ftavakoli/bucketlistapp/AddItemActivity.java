package com.example.ftavakoli.bucketlistapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class AddItemActivity extends FragmentActivity implements OnMapReadyCallback {


    Item mItem;
    private GoogleMap mMap;
    EditText titleEditText, descriptEditText;
    Button addButton;
    TextView date;
    MarkerOptions mMarker;

    int color = 0;
    View mView1, mView2, mView3, mView4, mView5, mView6;


    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mReference;

    Calendar mCalendar;
    DateFormat calendarTxtDate;
    private FirebaseAuth myFireBaseAuth;
    private LatLng currentPosition;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        titleEditText = findViewById(R.id.itemTitleEditText);
        descriptEditText = findViewById(R.id.descriptionItemEditText);
        addButton = findViewById(R.id.addButton);
        date = findViewById(R.id.dateTextView);


        mView1 = findViewById(R.id.view1);
        mView2 = findViewById(R.id.view2);
        mView3 = findViewById(R.id.view3);
        mView4 = findViewById(R.id.view4);
        mView5 = findViewById(R.id.view5);
        mView6 = findViewById(R.id.view6);



        mItem = new Item();
        myFireBaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        //reference is pointing to the user
        mReference = mFirebaseDatabase.getReference().child("Students").child(myFireBaseAuth.getCurrentUser().getUid()).push();


        mView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView1.setBackgroundResource(R.drawable.color1selected);
                color = 1;
                mView2.setBackgroundResource(R.drawable.color2);
                mView3.setBackgroundResource(R.drawable.color3);
                mView4.setBackgroundResource(R.drawable.color4);
                mView5.setBackgroundResource(R.drawable.color5);
                mView6.setBackgroundResource(R.drawable.color6);
            }
        });
        mView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView2.setBackgroundResource(R.drawable.color2selected);
                color = 2;
                mView1.setBackgroundResource(R.drawable.color1);
                mView3.setBackgroundResource(R.drawable.color3);
                mView4.setBackgroundResource(R.drawable.color4);
                mView5.setBackgroundResource(R.drawable.color5);
                mView6.setBackgroundResource(R.drawable.color6);

            }
        });
        mView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView3.setBackgroundResource(R.drawable.color3selected);
                color = 3;
                mView2.setBackgroundResource(R.drawable.color2);
                mView1.setBackgroundResource(R.drawable.color1);
                mView4.setBackgroundResource(R.drawable.color4);
                mView5.setBackgroundResource(R.drawable.color5);
                mView6.setBackgroundResource(R.drawable.color6);

            }
        });
        mView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView4.setBackgroundResource(R.drawable.color4selected);
                color = 4;
                mView2.setBackgroundResource(R.drawable.color2);
                mView3.setBackgroundResource(R.drawable.color3);
                mView1.setBackgroundResource(R.drawable.color1);
                mView5.setBackgroundResource(R.drawable.color5);
                mView6.setBackgroundResource(R.drawable.color6);

            }
        });
        mView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView5.setBackgroundResource(R.drawable.color5selected);
                color = 5;
                mView2.setBackgroundResource(R.drawable.color2);
                mView3.setBackgroundResource(R.drawable.color3);
                mView4.setBackgroundResource(R.drawable.color4);
                mView1.setBackgroundResource(R.drawable.color1);
                mView6.setBackgroundResource(R.drawable.color6);
            }
        });
        mView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView6.setBackgroundResource(R.drawable.color6selected);
                color = 6;
                mView2.setBackgroundResource(R.drawable.color2);
                mView3.setBackgroundResource(R.drawable.color3);
                mView4.setBackgroundResource(R.drawable.color4);
                mView5.setBackgroundResource(R.drawable.color5);
                mView1.setBackgroundResource(R.drawable.color1);
            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setActionBar(toolbar);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ItemListActivity.class));
                finish();
            }
        });

        calendarTxtDate = DateFormat.getDateInstance();
        mCalendar = Calendar.getInstance();
        /////////////////////////////////////////////mCalendar.get(Calendar.MILLISECOND);
        final DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mCalendar.set(Calendar.YEAR, i);
                mCalendar.set(Calendar.MONTH, i1);
                mCalendar.set(Calendar.DAY_OF_MONTH, i2);
                date.setText(calendarTxtDate.format(mCalendar.getTime()));
            }
        };

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(AddItemActivity.this, listener, mCalendar.get(Calendar.YEAR),
                        mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isValid()){
                mItem.setTitle(titleEditText.getText().toString());
                mItem.setDescription(descriptEditText.getText().toString());
                mItem.setColor(color);
                mItem.setDate(mCalendar.getTimeInMillis());
                mItem.setId(mReference.getKey());
                mItem.setCoordinates(new Coordinate(currentPosition.latitude,currentPosition.longitude));
                mReference.setValue(mItem);

                finish();
                }
                else
                {
                    Toast.makeText(AddItemActivity.this, "Please Enter Valid Information...", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney, Australia, and move the camera.

        currentPosition = new LatLng(25.0912, 55.1561);
        mMarker = new MarkerOptions().position(currentPosition);
        mMap.addMarker(mMarker);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 12));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            //listening to the map clicks
            @Override
            public void onMapClick(LatLng latLng) {
                currentPosition = latLng;
                mMarker = new MarkerOptions().position(latLng);
                mMap.clear();
                mMap.addMarker(mMarker);
            }
        });

    }



    //method to check if the user entered valid stuff
    private boolean isValid(){
        return !titleEditText.getText().toString().equals("") || !descriptEditText.getText().toString().equals("");
    }

}

