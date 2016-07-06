package com.example.hp.smartstor.CloudFileExplorer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hp.smartstor.BaseActivity;
import com.example.hp.smartstor.R;

public class ExplorerView extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer_view);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.explorer_rview);
        mRecyclerView.setHasFixedSize(true);
        CardAdapter cardAdapter =new CardAdapter(getApplicationContext());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(cardAdapter);
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

}
