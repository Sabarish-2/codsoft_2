package com.example.qqquotes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "quote")
public class Quote {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "fav")
    private boolean fav;

    public Quote(int id, boolean fav) {
        this.id = id;
        this.fav = fav;
    }

    @Ignore
    public Quote(boolean fav) {
        this.fav = fav;
    }

    public int getId() {
        return id;
    }

    public boolean getFav() {
        return fav;
    }

}
