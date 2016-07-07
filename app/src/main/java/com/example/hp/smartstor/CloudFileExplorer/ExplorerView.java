package com.example.hp.smartstor.CloudFileExplorer;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
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

import com.example.hp.smartstor.BaseActivity;
import com.example.hp.smartstor.CloudMusicManager.ListItem;
import com.example.hp.smartstor.R;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ExplorerView extends BaseActivity{
    ArrayList<String> Fnames,Fextensions,FDateCreatedList,FSizeList =new ArrayList<>();
    ArrayList<Boolean> isdirectoryList =new ArrayList<>();
    clientFileExplorer client =new clientFileExplorer(rooturl);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer_view);
        HorizontalScrollView hs =(HorizontalScrollView)findViewById(R.id.hscroll);
        hs.setHorizontalScrollBarEnabled(false);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.explorer_rview);
        mRecyclerView.setHasFixedSize(true);
        CardAdapter cardAdapter =new CardAdapter(getApplicationContext());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(cardAdapter);
        addFolderToPanel("c:");
        addFolderToPanel("SmartStor");
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
                        JSONObject jsonObject = response.getJSONObject(i);
                        {
                            String fname = (getFilenamefrompath(jsonObject.getString("path")));
                            if (fname != "err") {
                                /*TODO add fname to Listitem's new object here */
                                Fnames.add(fname);
                            }
                            Fextensions.add(jsonObject.getString("Ext"));
                            /*TODO add extension to Listitem's new object here */
                            FSizeList.add(jsonObject.getString("size"));
                            /*TODO add size to Listitem's new object here */
                            FDateCreatedList.add(getDateHelper(jsonObject.getString("dateCreated")));
                            /*TODO add dateCreated to Listitem's new object here */
                            isdirectoryList.add(jsonObject.getBoolean("isDirectory"));
                            /*TODO add isDirectory to Listitem's new object here */

                            /*TODO adapter.items.add(new object)*/
                            if (i == response.length() - 1) {
                                afterLoadingdone();
                            }

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public void afterLoadingdone(){
        /*TODO add notifydatasetChanged here () as the above method is an Asynchronous(multithread) method so works on callbacks*/

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

}
