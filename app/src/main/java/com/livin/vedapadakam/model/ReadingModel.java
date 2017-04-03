package com.livin.vedapadakam.model;

import android.database.Cursor;
/**
 * Created by Livin D'cruz on 17-04-2016.
 */
public class ReadingModel {
    private int id;
    private String heading;
    private String r1;
    private String r2;
    private String s;
    private String p;
    private String p2;
    private String date;
    private int special;

    public ReadingModel(Cursor c)
    {
        this.id= c.getInt(0);
        this.date = c.getString(1);
        this.heading = c.getString(2);
        this.r1 = c.getString(3);
        this.p = c.getString(4);
        this.r2 = c.getString(5);
        this.s = c.getString(6);
        this.p2 = c.getString(7);
        this.special = c.getInt(8);
    }

    public int getId() {
        return id;
    }

    public String getHeading() {
        return heading;
    }

    public String getR1() {
        return r1;
    }

    public String getR2() {
        return r2;
    }

    public String getS() {
        return s;
    }

    public String getP() {
        return p;
    }

    public String getP2() {
        return p2;
    }

    public String getDate() {
        return date;
    }

    public boolean isSpecial() { return special == 1;}
}
