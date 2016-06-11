package com.example.hp.smartstor.CloudMusicManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.smartstor.R;
import com.example.hp.smartstor.NetworkDiscovery;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

/**
 * Created by hp on 5/31/2016.
 */
public class MusicCardAdapter extends RecyclerView.Adapter<MusicCardAdapter.ViewHolder> {

    List<ListItem> items;
    Context context;
    Bitmap extimage;
    Bitmap filetheme;
    public String url;
    public List<String> MusicpathList=new List<String>() {
        @Override
        public void add(int location, String object) {

        }

        @Override
        public boolean add(String object) {
            return false;
        }

        @Override
        public boolean addAll(int location, Collection<? extends String> collection) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends String> collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean contains(Object object) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return false;
        }

        @Override
        public String get(int location) {
            return null;
        }

        @Override
        public int indexOf(Object object) {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @NonNull
        @Override
        public Iterator<String> iterator() {
            return null;
        }

        @Override
        public int lastIndexOf(Object object) {
            return 0;
        }

        @Override
        public ListIterator<String> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<String> listIterator(int location) {
            return null;
        }

        @Override
        public String remove(int location) {
            return null;
        }

        @Override
        public boolean remove(Object object) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return false;
        }

        @Override
        public String set(int location, String object) {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @NonNull
        @Override
        public List<String> subList(int start, int end) {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(T[] array) {
            return null;
        }
    };
    String TAG="Index";
    public MusicCardAdapter(Context context,String url)
    {
        super();
        this.context=context;
        this.url=url;
        items = new ArrayList<ListItem>();
        extimage = BitmapFactory.decodeResource(context.getResources(), R.mipmap.amp3);
        filetheme= BitmapFactory.decodeResource(context.getResources(),R.mipmap.music);


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