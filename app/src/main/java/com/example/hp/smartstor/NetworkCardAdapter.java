package com.example.hp.smartstor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 6/2/2016.
 */
public class NetworkCardAdapter extends RecyclerView.Adapter<NetworkCardAdapter.ViewHolder> {
    onFabClickedListener mcallback;

    public static interface onFabClickedListener{
        public  void setURL(String link);
    }

    List<rowItem> items;
    Context context;
    public NetworkCardAdapter(Context context){
        super();
        this.context=context;
        items= new ArrayList<rowItem>();


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rowdisc, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        mcallback=(onFabClickedListener)parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        rowItem list =  items.get(position);
        holder.dname.setText(list.getDname());
        holder.dip.setText(list.getDip());


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView dname;
        public TextView dip;
        public Button addbtn;

        public ViewHolder(View itemView) {
            super(itemView);
            dname=(TextView)itemView.findViewById(R.id.devname);
            dip = (TextView) itemView.findViewById(R.id.devip);
            addbtn=(Button) itemView.findViewById(R.id.addbutton);
            addbtn.setOnClickListener(this);

        }
        public void onClick(View v)
        {

            //Toast.makeText(context,items.get(getAdapterPosition()).getDname()+"\n"+items.get(getAdapterPosition()).getDip(),Toast.LENGTH_LONG).show();
            mcallback.setURL(items.get(getAdapterPosition()).getDip());
        }
    }
}