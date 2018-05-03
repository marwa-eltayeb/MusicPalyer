package com.example.marwa.musicplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.marwa.musicplayer.R;
import com.example.marwa.musicplayer.model.Singer;
import com.example.marwa.musicplayer.adapter.SingerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SingerAdapter.SingerAdapterOnClickHandler {

    // Constant Variable
    public static String NAME_OF_SINGER = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Using findViewById, we get a reference to our RecyclerView from xml. This allows us to
         * do things like set the adapter of the RecyclerView and toggle the visibility.
         */
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_singers);

        /*
         * GridLayoutManager
         */
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        // Set the layoutManager on mRecyclerView
        mRecyclerView.setLayoutManager(layoutManager);


        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerView.setHasFixedSize(true);

        // Create a list of singers
        final ArrayList<Singer> singers = new ArrayList<>();
        singers.add(new Singer("Celine Dion", R.drawable.celin_dion));
        singers.add(new Singer("James Blunt", R.drawable.james_blunt));
        singers.add(new Singer("Enrique Iglesias", R.drawable.enrique_iglesias));
        singers.add(new Singer("Bryan Adams", R.drawable.bryan_adams));

        // Add Item Decoration
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        // Set mForecastAdapter equal to a new ForecastAdapter
        SingerAdapter mSingerAdapter = new SingerAdapter(this, singers);

        // Use mRecyclerView.setAdapter and pass in mSingerAdapter
        mRecyclerView.setAdapter(mSingerAdapter);

    }

    /**
     * Click on GridLayout and pass the name of singer as an intent
     */
    @Override
    public void onClick(String nameOfSinger) {
        Intent intent = new Intent(MainActivity.this,Songs_Activity.class);
        intent.putExtra(NAME_OF_SINGER,nameOfSinger);
        startActivity(intent);
    }

}
