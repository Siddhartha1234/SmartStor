package com.example.hp.smartstor.CloudFileExplorer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.hp.smartstor.R;

import java.util.ArrayList;

/**
 * Created by SahilYerawar on 07-07-2016.
 */
public class FolderStructure extends Activity{
    private int state;
    private ArrayList<String> fName;
    private ArrayList<String> fSize;
    private  ArrayList<String> fDate;
    private ArrayList<String> fExt;
    private  ArrayList<Boolean> directory;
    Context context;
    public FolderStructure(Context context){
    this.context = context;
        this.state = 0;
}


    public void creatensetButtonID(){
        Button B = new Button(context);
        B.setId(state);

    }
    public void popButtonTillState(){

    }
    public set<int,String> getCurrentKey(){

    }

    public void addFolderToPanel(String folder_name, int state){
        final Button folder = new Button(context);
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
