package com.example.wolny.Room.Dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.wolny.Model.Quote;

@Database(entities = {Quote.class}, version = 1)
public abstract class QuoteDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "quote_database";
    private static QuoteDatabase instance;

    public static synchronized QuoteDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    QuoteDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }

    public abstract QuoteDao quoteDao();

}
