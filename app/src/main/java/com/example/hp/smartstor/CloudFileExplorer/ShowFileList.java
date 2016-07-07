package com.example.hp.smartstor.CloudFileExplorer;

import android.content.Context;

import com.example.hp.smartstor.MainActivity;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hp on 5/28/2016.
 */

public class ShowFileList extends MainActivity {
    Context context;
    String url;
    String[] music1={".Mp3",".Wav"}, movie1={".Mov",".Mp4",".Flv",".Avi",".3gp",".Mpeg"},picture1={".Png",".Jpeg",".Jpg",".Gif",".Ico"},
             document1={".Css",".Csv",".Doc",".Docx",".Html",".Jar",".Js",".Pdf",".Php",".Ppt",".Txt",".Dwg"},
             compressed1={".7z",".Rar",".Tar",".Gz",".Zip"};
    List music= Arrays.asList(music1);
    List movie=Arrays.asList(movie1);
    List picture=Arrays.asList(picture1);
    List document=Arrays.asList(document1);
    List compressed=Arrays.asList(compressed1);

   /* ShowFileList(Context context, String URL)
    {
        this.context=context;
        getRootFromURL(URL);


    }
    public void getFileList()
    {
        clientFile client = new clientFile();
        client.get(url,null, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Toast.makeText(context, "File parsed" + responseString, Toast.LENGTH_LONG).show();
                try {
                    JSONArray response = new JSONArray(responseString);
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject file = response.getJSONObject(i);
                        String filename = file.getString("Name");
                        String extension = file.getString("Ext");
                        FileList.add(filename);
                        ExtensionList.add(extension);


                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        });
    }



    public void getRootFromURL(String URL)
    {
        int i;
        for(i=0;;i++)
        {
         if(URL.charAt(i)==':' && URL.charAt(i+1)!='/')
             break;
        }
        this.url=URL.substring(0,i+6);

    }
    public List<ItemObject> getAllItemList()
    {
        List<ItemObject> OList = new ArrayList<ItemObject>();
     for(int i=0;i<FileList.size();i++)
     {
         ItemObject temp = new ItemObject(null,0,0);
         temp.setName(FileList.get(i));
         String x=matchThumbnail(ExtensionList.get(i));
         if( context.getResources().getIdentifier(x, "mipmap", context.getPackageName()) != 0)
         {
             temp.setPhoto(context.getResources().getIdentifier(x, "mipmap", context.getPackageName()));
             String abc =getFiletheme(ExtensionList.get(i));
             temp.setFiletheme(context.getResources().getIdentifier(abc,"mipmap",context.getPackageName()));
             OList.add(temp);

         }
         else
         {
             Toast.makeText(context,"Extension/Filetheme not in drawable",Toast.LENGTH_SHORT).show();
         }



     }
        return OList;
    }
*/
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
