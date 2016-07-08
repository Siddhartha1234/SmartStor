package com.example.hp.smartstor.CloudFileExplorer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.hp.smartstor.BaseActivity;
import com.example.hp.smartstor.CloudMusicManager.ListItem;
import com.example.hp.smartstor.R;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ExplorerView extends BaseActivity implements CardAdapter.Listen,FolderStructure.serverdata{

    String[] music1={".mp3",".wav"}, movie1={".mov",".mp4",".flv",".avi",".3gp",".mpeg"},picture1={".png",".jpeg",".jpg",".gif",".ico"},
            document1={".css",".csv",".doc",".docx",".html",".jar",".js",".pdf",".php",".ppt",".txt",".dwg"},
            compressed1={".7z",".rar",".rar",".gz",".zip"};
    List music= Arrays.asList(music1);
    List movie=Arrays.asList(movie1);
    List picture=Arrays.asList(picture1);
    List document=Arrays.asList(document1);
    List compressed=Arrays.asList(compressed1);
    clientFileExplorer client =new clientFileExplorer(rooturl);
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    CardAdapter card;
    FolderStructure folderStructure=new FolderStructure(getApplicationContext());
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer_view);
        HorizontalScrollView hs =(HorizontalScrollView)findViewById(R.id.hscroll);
        hs.setHorizontalScrollBarEnabled(false);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        mLayoutManager=new LinearLayoutManager(getApplicationContext());
        mRecyclerView = (RecyclerView) findViewById(R.id.explorer_rview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        card = new CardAdapter(getApplicationContext());
        mRecyclerView.setAdapter(card);
        getDataFromServer("root");
        folderStructure.createnSetButtonID("root");
    }
    public void function(String s){
        /*CardAdapter Card1 = new CardAdapter();
        setContentView(R.layout.activity_explorer_view);
        LinearLayoutManager lManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView recycle =(RecyclerView)findViewById(R.id.explorer_rview);
        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(lManager);
        recycle.setAdapter(Card1);*/
        RecyclerView recycle = new RecyclerView(getApplicationContext());
        for(int i = 0; i < card.items.size();i++){
            card.items.remove(card.items.size()-1);
        }
        getDataFromServer(s);


    }

    public void traceback(Pair<Integer,String> foldername){


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id)
        {
            case R.id.nav_files: return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void getDataFromServer(String foldername){
        final ArrayList<ListItem>items = new ArrayList<ListItem>();
        RequestParams params =new RequestParams();
        params.put("foldername",foldername);
        client.get("/",params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i("ExplorerView","Failed to Parse JSON");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    ArrayList<String> Fnames=new ArrayList<>(),Fextensions=new ArrayList<>(),FDateCreatedList=new ArrayList<>(),FSizeList =new ArrayList<>();
                    ArrayList<Boolean> isdirectoryList =new ArrayList<>();
                    JSONArray response = new JSONArray(responseString);
                    Log.i("size", String.valueOf(response.length()));
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject jsonObject = response.getJSONObject(i);
                        {

                            Boolean isDirectory=jsonObject.getBoolean("IsDirectory");
                            if(isDirectory) {
                                isdirectoryList.add(true);
                                String fname=jsonObject.getString("Name");
                                //int extId = getResourceId(getApplicationContext(), , "mipmap", getApplicationContext().getPackageName());
                                int ThemeId = getResourceId(getApplicationContext(),"folder", "mipmap", getApplicationContext().getPackageName());
                              //  Bitmap extimage = BitmapFactory.decodeResource(getApplicationContext().getResources(), extId);
                                Bitmap filetheme = BitmapFactory.decodeResource(getApplicationContext().getResources(), ThemeId);
                                ListItem test = new ListItem( filetheme, fname, false);
                                card.items.add(test);


                            }
                            else {
                                String fname = (jsonObject.getString("Name"));
                                Fnames.add(fname);
                                Fextensions.add(jsonObject.getString("Ext"));
                                String fExt = jsonObject.getString("Ext");
                                FSizeList.add(jsonObject.getString("Size"));
                                String fSize = jsonObject.getString("Size");
                                FDateCreatedList.add(getDateHelper(jsonObject.getString("DateCreated")));
                                String fDate = getDateHelper(jsonObject.getString("DateCreated"));
                                String tnail = matchThumbnail(fExt);
                                int extId = getResourceId(getApplicationContext(), tnail, "mipmap", getApplicationContext().getPackageName());
                                String theme = getFiletheme(fExt);
                                int ThemeId = getResourceId(getApplicationContext(), theme, "mipmap", getApplicationContext().getPackageName());
                                Bitmap extimage = BitmapFactory.decodeResource(getApplicationContext().getResources(), extId);
                                Bitmap filetheme = BitmapFactory.decodeResource(getApplicationContext().getResources(), ThemeId);
                                ListItem test = new ListItem(extimage, filetheme, fname, fSize, fDate, false);
                                card.items.add(test);
                            }
                            if (i == response.length() - 1) {
                                folderStructure.folderArrayList.add(new Folder(isdirectoryList,FDateCreatedList,Fextensions,Fnames,FSizeList));
                                afterLoadingdone();
                            }

                        }

                    }
                    ;


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public void afterLoadingdone(){
        /*TODO add notifydatasetChanged here () as the above method is an Asynchronous(multithread) method so works on callbacks*/
        card.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(),"Completed sync",Toast.LENGTH_LONG).show();

    }
    public String getDateHelper(String x){
        String[] y=x.split("T");
        return y[0];
    }
    public String getFilenamefrompath(String path) {
        StringBuffer sb=new StringBuffer();
        if(path.length() >6 && path.contains("\\")) {
            for (int i = path.length() - 6; path.charAt(i) != '\\'; i--) {
                sb.append(path.charAt(i));
            }
            sb.reverse();
            return sb.toString();
        }
        else
            return "err";

    }


    public void addFolderToPanel(String folder_name){
        final Button folder = new Button(getApplicationContext());
        folder.setText(folder_name);
        folder.setTypeface(null, Typeface.BOLD);
        folder.setGravity(Gravity.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            folder.setBackgroundColor(getResources().getColor(R.color.light_grey,getTheme()));
            folder.setTextColor(getResources().getColor(R.color.black,getTheme()));

        }
        else{
            folder.setBackgroundColor(getResources().getColor(R.color.light_grey));
            folder.setTextColor(getResources().getColor(R.color.black));
        }

        folder.setOnClickListener(new View.OnClickListener(){public void onClick(View view){

            /*TODO add functionality of clicking button by removing it from the panel
            * removeButtonFromPanel(folder)*/

        }});
        ImageView image= new ImageView(getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            image.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_keyboard_arrow_right_black_24dp,getApplicationContext().getTheme()));
        } else {
            image.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_keyboard_arrow_right_black_24dp));
        }
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        image.setAdjustViewBounds(true);
        LinearLayout scroll = (LinearLayout) findViewById(R.id.toolLinear);
        scroll.addView(folder);
        scroll.addView(image);

    }

    public  int getResourceId(Context context, String pVariableName, String pResourcename, String pPackageName)
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
