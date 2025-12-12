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
    LiveData<Book> findById(int id);

    @Query("UPDATE Book SET currentBooks  = 'Y' WHERE id = :id")
    int addCurrentRead(int id);

    @Delete
    void delete(Book book);

    @Query("UPDATE Book SET currentBooks  = 'N' WHERE id = :id")
    void deleteCurrentReads(int id);

    @Query("SELECT * FROM Book WHERE favorite = 'Y'")
    LiveData<List<Book>> getFaveBook();

    @Query("UPDATE Book SET favorite = 'Y' WHERE id = :id")
    int addFavorite(int id);

    @Query("SELECT * FROm Book WHERE wishlist = 'Y'")
    LiveData<List<Book>> getWantToReadBook();

    @Query("UPDATE Book SET wishlist = 'Y' WHERE id = :id LIMIT 1")
    int addWant2ReadBooks(int id);
}
