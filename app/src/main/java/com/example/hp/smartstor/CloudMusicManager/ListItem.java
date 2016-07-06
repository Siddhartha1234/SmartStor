package com.example.hp.smartstor.CloudMusicManager;

import android.graphics.Bitmap;

/**
 * Created by hp on 5/31/2016.
 */
public class ListItem {
    private Bitmap extImage;
    private Bitmap fileTheme;
    private String fileName;
    private String fileSize;
    private String date;

    public ListItem(Bitmap extImage, Bitmap fileTheme, String fileName,String fileSize, String date) {
        this.extImage = extImage;
        this.fileTheme = fileTheme;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.date = date;
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

    public String getFileSize(){return fileSize;}

    public void setFileSize(String fileSize){this.fileSize = fileSize;}

    public String getDate(){return date;}

    public void setDate(String date){this.date = date;}



}
