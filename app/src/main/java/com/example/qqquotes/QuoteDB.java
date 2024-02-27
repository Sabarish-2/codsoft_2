package com.example.qqquotes;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Quote.class, exportSchema = false, version = 1)
public abstract class QuoteDB extends RoomDatabase {
    private static final String DATABASE_NAME = "quote";

    public static QuoteDB instance;

    public static synchronized QuoteDB getDB(Context context)
    {
        if (instance == null) {
            instance = Room.databaseBuilder(context, QuoteDB.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }

    public abstract QuoteDao quoteDao();
}