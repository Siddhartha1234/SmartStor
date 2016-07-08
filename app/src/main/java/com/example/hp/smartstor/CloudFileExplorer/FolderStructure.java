package com.example.hp.smartstor.CloudFileExplorer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.util.Pair;
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
public class FolderStructure {
    private int state;
    ArrayList<Folder> folderArrayList;
    Context context;
    public FolderStructure(Context context){
        this.context = context;
        this.state = 0;
        this.folderArrayList=new ArrayList<>();
    }


    public void popButtonTillState(){

    }
    public Folder getFolderDataFromKey(Pair<Integer,String> key){
        /*TODO right now */
        return this.folderArrayList.get(key.first);
    }
    public Pair<Integer,String> getCurrentKey(String name){

        return Pair.create(state,name);
    }

    public void createnSetButtonID(String folder_name){
        final Button folder = new Button(context);
        folder.setText(folder_name);
        folder.setTypeface(null, Typeface.BOLD);
        folder.setGravity(Gravity.CENTER);
        folder.setId(state);
        if(!folder_name.equals("root"))
            this.state+=1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            folder.setBackgroundColor(context.getResources().getColor(R.color.light_grey,context.getTheme()));
            folder.setTextColor(context.getResources().getColor(R.color.black,context.getTheme()));
        }
        else{
            folder.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
            folder.setTextColor(context.getResources().getColor(R.color.black));
        }

        folder.setOnClickListener(new View.OnClickListener(){public void onClick(View view){

            /*TODO add functionality of clicking button by removing it from the panel
            * removeButtonFromPanel(folder)*/
                getDataFromServer(folder.getText().toString());

        }});
        ImageView image= new ImageView(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            image.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_keyboard_arrow_right_black_24dp,context.getTheme()));
        } else {
            image.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_keyboard_arrow_right_black_24dp));
        }
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        image.setAdjustViewBounds(true);
        LinearLayout scroll = (LinearLayout)((Activity)context).findViewById(R.id.toolLinear);
        scroll.addView(folder);
        scroll.addView(image);

    }
}
