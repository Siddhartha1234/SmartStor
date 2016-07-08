package com.example.hp.smartstor.CloudFileExplorer;

import java.util.ArrayList;

/**
 * Created by hp on 7/8/2016.
 */
public class Folder {
    private ArrayList<String> fName;
    private ArrayList<String> fSize;
    private  ArrayList<String> fDate;
    private ArrayList<String> fExt;
    private  ArrayList<Boolean> directory;

    public Folder(ArrayList<Boolean> directory, ArrayList<String> fDate, ArrayList<String> fExt, ArrayList<String> fName, ArrayList<String> fSize) {
        this.directory = directory;
        this.fDate = fDate;
        this.fExt = fExt;
        this.fName = fName;
        this.fSize = fSize;
    }

    public ArrayList<Boolean> getDirectory() {
        return directory;
    }

    public void setDirectory(ArrayList<Boolean> directory) {
        this.directory = directory;
    }

    public ArrayList<String> getfDate() {
        return fDate;
    }

    public void setfDate(ArrayList<String> fDate) {
        this.fDate = fDate;
    }

    public ArrayList<String> getfExt() {
        return fExt;
    }

    public void setfExt(ArrayList<String> fExt) {
        this.fExt = fExt;
    }

    public ArrayList<String> getfName() {
        return fName;
    }

    public void setfName(ArrayList<String> fName) {
        this.fName = fName;
    }

    public ArrayList<String> getfSize() {
        return fSize;
    }

    public void setfSize(ArrayList<String> fSize) {
        this.fSize = fSize;
    }
}
