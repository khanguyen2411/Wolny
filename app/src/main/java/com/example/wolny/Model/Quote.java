package com.example.wolny.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "QUOTE")
public class Quote {
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "author")
    String author;

    @ColumnInfo(name = "content")
    String content;

    public Quote() {
    }

    public Quote(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
