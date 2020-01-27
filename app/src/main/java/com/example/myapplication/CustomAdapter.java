package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomAdapter extends ArrayAdapter<Card> {

    private Context context;
    int [] images = {R.drawable.doggo2,R.drawable.kitty2,R.drawable.doggo3,R.drawable.kitty3,R.drawable.doggo4,
            R.drawable.kitty4,R.drawable.paws,R.drawable.paws};

    public CustomAdapter(Context context, int resourceID, List<Card> cards) {

        super(context,resourceID,cards);


        Log.i( " THE SIZE OF THE LIST IS FINALYYYYYY 2222222 : " ,  String.valueOf(cards.size()) );


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Card card_item = getItem(position);

        if(convertView == null) {
            Log.i( " enshalla la22222 "  , "enshalla la2222 " );

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.swipecard,parent,false);
        }

        ImageView img = (ImageView) convertView.findViewById(R.id.displayPet);
        CircleImageView circ = (CircleImageView)convertView.findViewById(R.id.displayPic);
        TextView a = (TextView) convertView.findViewById(R.id.viewBreed);
        TextView b = (TextView) convertView.findViewById(R.id.viewGender);
        TextView c = (TextView) convertView.findViewById(R.id.viewAge);
        TextView d = (TextView) convertView.findViewById(R.id.username);

//        assert card_item != null;
        a.setText(card_item.getBreed());
        b.setText(card_item.getGender());
        c.setText(String.valueOf(card_item.getAge()));
        d.setText(card_item.getUsername());
        img.setImageResource(R.drawable.doggo2);
        img.setVisibility(View.VISIBLE);
        circ.setImageResource(R.drawable.user_profile);
        circ.setVisibility(View.VISIBLE);

        Log.i( " INSIDE ADAPTER " ,  "I AM HERE2" );



        return convertView;



    }
}
