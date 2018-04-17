package com.example.marwa.musicplayer.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marwa.musicplayer.Model.Singer;
import com.example.marwa.musicplayer.R;

import java.util.ArrayList;

/**
 * Created by Marwa on 4/3/2018.
 */

public class SingerAdapter extends RecyclerView.Adapter<SingerAdapter.SingerAdapterViewHolder> {

    // Create a final private SingerAdapterOnClickHandler called mClickHandler
    private final SingerAdapterOnClickHandler clickHandler;

    // Declare an arrayList for singers
    private ArrayList<Singer> singers;

    /**
     * The interface that receives onClick messages.
     */
    public interface SingerAdapterOnClickHandler {
        void onClick(String nameOfSinger);
    }


    public SingerAdapter(SingerAdapterOnClickHandler clickHandler,ArrayList<Singer> singers) {
        this.clickHandler = clickHandler;
        this.singers = singers;
    }

    @Override
    public SingerAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.singer_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new SingerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SingerAdapterViewHolder holder, int position) {
        Singer currentSinger = singers.get(position);
        String singer = currentSinger.getNameOfSinger();
        holder.nameOfSinger.setText(singer);
        int image = currentSinger.getImageResourceId();
        holder.photoOfSinger.setImageResource(image);
    }

    @Override
    public int getItemCount() {
        if (singers == null) {
            return 0;
        }
        return singers.size();
    }


    /**
     * Cache of the children views for a Singer list item.
     */
    public class SingerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView nameOfSinger;
        public ImageView photoOfSinger;

        public SingerAdapterViewHolder(View view) {
            super(view);
            nameOfSinger = (TextView) view.findViewById(R.id.singerName);
            photoOfSinger = (ImageView) view.findViewById(R.id.photoSinger);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Get the name of singer
            String singerName = nameOfSinger.getText().toString();
            clickHandler.onClick(singerName);
        }
    }

}
