package com.example.hp.smartstor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.hp.smartstor.CloudFileExplorer.CardAdapter;
import com.example.hp.smartstor.CloudMusicManager.MusicActivity;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (url == null) {
            setContentView(R.layout.activity_main_nopeers);
        } else {
            startActivity(new Intent(this, MusicActivity.class));

        }
    }
    public void connectpeer(View v)
    {
        startActivity(new Intent(this,NetworkDiscovery.class));
    }


}
