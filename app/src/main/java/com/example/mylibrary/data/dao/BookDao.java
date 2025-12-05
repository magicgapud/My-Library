package com.example.mylibrary.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mylibrary.data.model.Book;

import java.util.List;

@Dao
public interface BookDao {

    @Insert
    long insert(Book book);

    @Query("SELECT * FROM Book")
    LiveData<List<Book>> getAllBooks();

    @Query("SELECT * FROM Book WHERE currentBooks = 'Y'")
    LiveData<List<Book>> getCurrentReads();

    @Query("SELECT * FROM Book WHERE id = :id LIMIT 1")
    Book findById(int id);

    @Query("UPDATE Book SET currentBooks  = 'Y' WHERE id = :id")
    int addCurrentRead(int id);

    @Delete
    void delete(Book book);


}
