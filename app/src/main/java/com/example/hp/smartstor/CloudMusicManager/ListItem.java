package com.example.hp.smartstor.CloudMusicManager;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by hp on 5/31/2016.
 */
public class ListItem {
    private Bitmap extImage;
    private Bitmap fileTheme;
    private String fileName;

    public ListItem(Bitmap extImage, Bitmap fileTheme, String fileName) {
        this.extImage = extImage;
        this.fileTheme = fileTheme;
        this.fileName = fileName;
    }

    public Bitmap getExtImage() {
        return extImage;
    }

    public void setExtImage(Bitmap extImage) {
        this.extImage = extImage;
    }

    public Bitmap getFileTheme() {
        return fileTheme;
    }

    public void setFileTheme(Bitmap fileTheme) {
        this.fileTheme = fileTheme;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


}
