package com.example.hp.smartstor.CloudMusicManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.hp.smartstor.BaseActivity;
import com.example.hp.smartstor.CloudFileExplorer.CardAdapter;
import com.example.hp.smartstor.MainActivity;
import com.example.hp.smartstor.R;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MusicActivity extends BaseActivity {
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;
    MusicCardAdapter musicCardAdapter;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_hamburger);
        FloatingActionButton floatingActionButton =(FloatingActionButton)findViewById(R.id.fab);
        floatingActionButton.setVisibility(View.GONE);
        recyclerView = (RecyclerView) findViewById(R.id.music_recycler);
        recyclerView.hasFixedSize();
        musicCardAdapter= new MusicCardAdapter(getApplicationContext(),rooturl);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(musicCardAdapter);
        progress = new ProgressDialog(MusicActivity.this);
        progress.setMessage("Loading list of songs from peer");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);

        progress.setCancelable(false);
        ListdatafromJSON();


    }
    public void ListdatafromJSON()
    {
        progress.show();
        clientMusic clientMusic=new clientMusic(musicCardAdapter.url);
        clientMusic.get("/get-music",null , new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {

                        try {
                            JSONArray response=new JSONArray(responseString);
                            Log.i("size",String.valueOf(response.length()));
                            for(int i=0;i<response.length();i++)
                            {
                                JSONObject jsonObject = response.getJSONObject(i);
                                {
                                    String fname = (getFilenamefrompath(jsonObject.getString("path")));
                                    if(fname!="err")
                                        musicCardAdapter.items.add(new ListItem(musicCardAdapter.extimage, musicCardAdapter.filetheme,fname ));

                                    if(i==response.length()-1)
                                    {
                                        afterLoadingdone();
                                    }

                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }
    public void afterLoadingdone()
    {
        progress.dismiss();
        musicCardAdapter.notifyDataSetChanged();
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


    @Override
    protected boolean useDrawerToggle() {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_music)
            return true;

        if (item.getItemId() == android.R.id.home)
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }
}
