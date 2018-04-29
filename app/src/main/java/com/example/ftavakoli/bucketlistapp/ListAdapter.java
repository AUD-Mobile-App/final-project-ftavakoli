package com.example.ftavakoli.bucketlistapp;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by FTavakoli on 3/22/18.
 */

public class ListAdapter extends ArrayAdapter {

    DatabaseReference mDatabaseReference;

    private final Activity context;
    private final ArrayList<Item> listOfItems;
    // private final ArrayList<ItemDataStore> iconID;

    private Date today;
    public ListAdapter(Activity context, ArrayList<Item> items, DatabaseReference mDatabaseReference) {
        super(context, R.layout.list,items);
        this.context = context;
        this.listOfItems = items;
        this.mDatabaseReference = mDatabaseReference;
        today = new Date();
        //this.iconID = iconId;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //mDateFormat = new SimpleDateFormat("dd-mm-yyyy");

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list, null, true);
        View view = rowView.findViewById(R.id.view);
        TextView textView = rowView.findViewById(R.id.titleTextView);
        CheckBox checkBox = rowView.findViewById(R.id.checkBox);
        textView.setText(listOfItems.get(position).getTitle());

        Date date = new Date(listOfItems.get(position).getDate());

        if(date.before(today))
        {
            //Over due
            rowView.setBackgroundResource(R.drawable.roundoverdue);

        }else{
            if (date.getTime() - today.getTime() < (2*86400000))
            {
                rowView.setBackgroundResource(R.drawable.roundtwodays);            }
            else if (date.getTime() - today.getTime() < (5*86400000))
            {
                rowView.setBackgroundResource(R.drawable.roundfivedays);            }
            else if(date.getTime() - today.getTime()< (7*86400000))
            {
                rowView.setBackgroundResource(R.drawable.roundoneweek);
            }
            else if (date.getTime() - today.getTime() < (14*86400000))
            {
                rowView.setBackgroundResource(R.drawable.roundtwoweek);            }
            else{
                rowView.setBackgroundResource(R.drawable.roundmorethantwoweek);
            }

        }
        //in order to update the database after we checked/unchecked !
        checkBox.setChecked(listOfItems.get(position).isStatus());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                mDatabaseReference.child(listOfItems.get(position).getId()).child("status")
                        .setValue(isChecked);

            }
        });
        switch (listOfItems.get(position).getColor()){
            case 1:
                view.setBackgroundResource(R.drawable.color1);
                break;
            case 2:
                view.setBackgroundResource(R.drawable.color2);
                break;
            case 3:
                view.setBackgroundResource(R.drawable.color3);
                break;
            case 4:
                view.setBackgroundResource(R.drawable.color4);
                break;
            case 5:
                view.setBackgroundResource(R.drawable.color5);
                break;
            case 6:
                view.setBackgroundResource(R.drawable.color6);
                break;
        }
            return rowView;
    }
}
