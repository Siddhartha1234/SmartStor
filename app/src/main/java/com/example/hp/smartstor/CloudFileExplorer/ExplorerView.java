package com.example.hp.smartstor.CloudFileExplorer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

public class ExplorerView extends BaseActivity{
    ArrayList<String> Fnames,Fextensions,FDateCreatedList,FSizeList =new ArrayList<>();
    String[] music1={".Mp3",".Wav"}, movie1={".Mov",".Mp4",".Flv",".Avi",".3gp",".Mpeg"},picture1={".Png",".Jpeg",".Jpg",".Gif",".Ico"},
            document1={".Css",".Csv",".Doc",".Docx",".Html",".Jar",".Js",".Pdf",".Php",".Ppt",".Txt",".Dwg"},
            compressed1={".7z",".Rar",".Tar",".Gz",".Zip"};
    List music= Arrays.asList(music1);
    List movie=Arrays.asList(movie1);
    List picture=Arrays.asList(picture1);
    List document=Arrays.asList(document1);
    List compressed=Arrays.asList(compressed1);
    ArrayList<Boolean> isdirectoryList =new ArrayList<>();
    clientFileExplorer client =new clientFileExplorer(rooturl);
    RecyclerView mRecyclerView;
    CardAdapter card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer_view);
        HorizontalScrollView hs =(HorizontalScrollView)findViewById(R.id.hscroll);
        hs.setHorizontalScrollBarEnabled(false);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        mRecyclerView = (RecyclerView) findViewById(R.id.explorer_rview);
        mRecyclerView.setHasFixedSize(true);
       /* String fNameArray[]={"Concrete Mathematics","Percy Jackson- the Last Olympian","Fahrenheit 451"};
        String fSizeArray[]={"810kB","1.2MB","400kB"};
        String fDateArray[]={"26-10-15","4-2-16","3-6-16"};
        String fExtArray[]={".Pdf",".Docx",".Doc"};*/
        //arrayToCard(fNameArray,fSizeArray,fDateArray,fExtArray,mRecyclerView);
        addFolderToPanel("c:");
        addFolderToPanel("SmartStor");
        card = new CardAdapter(getApplicationContext());
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
                    JSONArray response = new JSONArray(responseString);
                    Log.i("size", String.valueOf(response.length()));
                    for (int i = 0; i < response.length(); i++) {
                        String filename="err";
                        JSONObject jsonObject = response.getJSONObject(i);
                        {

                            String fname = (getFilenamefrompath(jsonObject.getString("path")));
                            if (fname != "err") {
                                /*TODO add fname to Listitem's new object here */
                                Fnames.add(fname);
                                filename = fname;

                            }
                            Fextensions.add(jsonObject.getString("Ext"));
                            /*TODO add extension to Listitem's new object here */
                            String fExt = jsonObject.getString("Ext");

                            FSizeList.add(jsonObject.getString("size"));
                            /*TODO add size to Listitem's new object here */
                            String fSize = jsonObject.getString("Size");

                            FDateCreatedList.add(getDateHelper(jsonObject.getString("dateCreated")));
                            /*TODO add dateCreated to Listitem's new object here */
                            String fDate = jsonObject.getString("dateCreated");

                            isdirectoryList.add(jsonObject.getBoolean("isDirectory"));
                            /*TODO add isDirectory to Listitem's new object here */
                            Boolean isDirectory = jsonObject.getBoolean("isDirectory");

                            /*TODO adapter.items.add(new object)*/
                            String tnail = matchThumbnail(fExt);
                            int extId = getResourceId(getApplicationContext(),tnail,"mipmap",getApplicationContext().getPackageName());
                            String theme = getFiletheme(fExt);
                            int ThemeId = getResourceId(getApplicationContext(),theme,"mipmap",getApplicationContext().getPackageName());
                            Bitmap extimage = BitmapFactory.decodeResource(getApplicationContext().getResources(), extId);
                            Bitmap filetheme= BitmapFactory.decodeResource(getApplicationContext().getResources(),ThemeId);
                            ListItem test = new ListItem(extimage,filetheme,filename,fSize,fDate,isDirectory);
                            card.items.add(test);
                            if (i == response.length() - 1) {
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
   /* public void arrayToCard(String a[],String b[],String c[],String d[],RecyclerView m){

            CardAdapter card = new CardAdapter(getApplicationContext(),a,b,c,d,a.length);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            m.setLayoutManager(mLayoutManager);
            m.setAdapter(card);

    }*/
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
