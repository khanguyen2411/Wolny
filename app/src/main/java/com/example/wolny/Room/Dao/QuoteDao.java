package com.example.wolny.Room.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.wolny.Model.Quote;

@Dao
public interface QuoteDao {

    @Insert()
    void insertQuote(Quote quote);

    @Query("SELECT * FROM QUOTE ORDER BY RANDOM() LIMIT 1;")
    Quote getRandomQuote();
}
