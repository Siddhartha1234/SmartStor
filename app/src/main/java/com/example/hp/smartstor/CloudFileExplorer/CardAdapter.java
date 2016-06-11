package com.example.hp.smartstor.CloudFileExplorer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.smartstor.CloudMusicManager.ListItem;
import com.example.hp.smartstor.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 5/31/2016.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    List<ListItem> items;
    Context context;
    public CardAdapter(Context context){
        super();
        this.context=context;
        items = new ArrayList<ListItem>();
        Bitmap extimage = BitmapFactory.decodeResource(context.getResources(), R.mipmap.amp3);
        Bitmap filetheme= BitmapFactory.decodeResource(context.getResources(),R.mipmap.music);
        String filename="Pitbull feat Flo rida";
        ListItem test=new ListItem(extimage,filetheme,filename);
        items.add(test);
        items.add(test);
        items.add(test);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gridunit, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItem list =  items.get(position);
        holder.extview.setImageBitmap(list.getExtImage());
        holder.fileview.setImageBitmap(list.getFileTheme());
        holder.fnameview.setText(list.getFileName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView extview;
        public ImageView fileview;
        public TextView fnameview;

        public ViewHolder(View itemView) {
            super(itemView);

            extview= (ImageView) itemView.findViewById(R.id.ExtImage);
            fileview = (ImageView) itemView.findViewById(R.id.filetheme);
            fnameview = (TextView) itemView.findViewById(R.id.filename);

        }
    }
}