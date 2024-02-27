package com.example.qqquotes;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface QuoteDao {

    @Query("select * from   quote")
    List<Quote> getAllQuotes();

    @Query("select fav from quote where id =:id")
    Boolean chkFav(int id);

    @Insert
    void addQuote(Quote quote);

    @Update
    void favQuote(Quote quote);

}
