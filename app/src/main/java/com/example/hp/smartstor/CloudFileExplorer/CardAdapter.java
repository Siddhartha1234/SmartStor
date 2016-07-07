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
import java.util.Arrays;
import java.util.List;

/**
 * Created by hp on 5/31/2016.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    String[] music1={".Mp3",".Wav"}, movie1={".Mov",".Mp4",".Flv",".Avi",".3gp",".Mpeg"},picture1={".Png",".Jpeg",".Jpg",".Gif",".Ico"},
            document1={".Css",".Csv",".Doc",".Docx",".Html",".Jar",".Js",".Pdf",".Php",".Ppt",".Txt",".Dwg"},
            compressed1={".7z",".Rar",".Tar",".Gz",".Zip"};
    List music= Arrays.asList(music1);
    List movie=Arrays.asList(movie1);
    List picture=Arrays.asList(picture1);
    List document=Arrays.asList(document1);
    List compressed=Arrays.asList(compressed1);
    List<ListItem> items;
    Context context;
    public CardAdapter(Context context,String a[],String b[],String c[],String d[],int e){
        super();
        this.context=context;
        items = new ArrayList<ListItem>();

        for(int i = 0; i < e;i++ ) {
            String thumbnail = matchThumbnail(d[i]);
            int extId = getResourceId(context,thumbnail,"mipmap",context.getPackageName());
            String theme = getFiletheme(d[i]);
            int themeId = getResourceId(context,theme,"mipmap",context.getPackageName());
            Bitmap extimage = BitmapFactory.decodeResource(context.getResources(), extId);
            Bitmap filetheme= BitmapFactory.decodeResource(context.getResources(),themeId);
            String filename = a[i];
            String filesize = b[i];
            String date = c[i];
            ListItem test = new ListItem(extimage, filetheme, filename, filesize, date);
            items.add(test);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filerow, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItem list =  items.get(position);
        holder.extview.setImageBitmap(list.getExtImage());
        holder.fileview.setImageBitmap(list.getFileTheme());
        holder.fnameview.setText(list.getFileName());
        holder.fsizeview.setText(list.getFileSize());
        holder.fdateview.setText(list.getDate());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView extview;
        public ImageView fileview;
        public TextView fnameview;
        public TextView fsizeview;
        public TextView fdateview;

        public ViewHolder(View itemView) {
            super(itemView);

            extview= (ImageView) itemView.findViewById(R.id.file_ext);
            fileview = (ImageView) itemView.findViewById(R.id.filetheme);
            fnameview = (TextView) itemView.findViewById(R.id.file_name);
            fsizeview = (TextView) itemView.findViewById(R.id.filesize);
            fdateview = (TextView) itemView.findViewById(R.id.date_created);

        }
    }
    public  int getResourceId(Context context,String pVariableName, String pResourcename, String pPackageName)
    {
        try {
            return context.getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    public String matchThumbnail(String ext)
    {
        String DrawableExt="a"+ext.substring(1).toLowerCase();
        return DrawableExt;
    }
    public String getFiletheme(String x)
    {
        if(music.contains(x))
            return "music";
        else if(movie.contains(x))
            return "video";
        else if(picture.contains(x))
            return "image";
        else if(document.contains(x))
            return "docs";
        else if(compressed.contains(x))
            return "compressed";
        else
            return "harddisk";


    }
}