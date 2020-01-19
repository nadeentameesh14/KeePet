package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {


   // ListView feed;

    JSONArray pets;

    int mImages[];

    String mBreeds[], mGenders[], mAges[];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)

    {




        return inflater.inflate(R.layout.fragment_home, container, false);


    }

    class home extends AppCompatActivity {
       ListView feed = (ListView) findViewById(R.id.listView);
    }
    class myAdapter extends ArrayAdapter<String> {

        Context context;
        int rImg[];
        String rBreeds[], rGenders[], rAges[];


        myAdapter( Context c, int imgs[], String breeds[], String genders[], String ages[]) {
            super(c, R.layout.post, R.id.imageView);

            this.context = c;
            this.rAges = ages;
            this.rBreeds = breeds;
            this.rGenders = genders;
            this.rImg = imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getApplicationContext().getSystemService(context.LAYOUT_INFLATER_SERVICE)
            return super.getView(position, convertView, parent);
        }
    }
}
