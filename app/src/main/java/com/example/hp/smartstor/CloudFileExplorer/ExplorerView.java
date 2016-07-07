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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.hp.smartstor.BaseActivity;
import com.example.hp.smartstor.R;

public class ExplorerView extends BaseActivity{

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
