package com.example.hp.smartstor;

/**
 * Created by hp on 6/2/2016.
 */
public class rowItem {
    String dname;
    String dip;

    public rowItem(String dname, String dip) {
        this.dname = dname;
        this.dip = dip;
    }

    public String getDname() {
        return dname;

    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getDip() {
        return dip;
    }

    public void setDip(String dip) {
        this.dip = dip;
    }
}
