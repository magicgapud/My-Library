package com.example.mylibrary.data.repository;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.LiveData;

import com.example.mylibrary.data.dao.BookDao;
import com.example.mylibrary.data.local.AppDatabase;
import com.example.mylibrary.data.model.Book;
import com.example.mylibrary.data.utils.InsertCallback;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookRepository {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final BookDao bookDao;

    private Context context;
    private LiveData<List<Book>> allBooks;
    private Book book;
    public BookRepository(Application application, Context context){
        AppDatabase db = AppDatabase.getINSTANCE(application);
        bookDao = db.bookDao();
    }

public void addBook(Book book, InsertCallback callback){

        executorService.execute(()->{


            long rowId = bookDao.insert(book);

            boolean result = rowId > 0;

            callback.onResult(result);

        });
}


private File copyImageToStorage(Uri uri){

    InputStream inputStream = getContentResolver().openInputStream(uri);
}

    private ContentResolver getContentResolver() {
    }

    ;

public LiveData<Book> findById(int id){
        return bookDao.findById(id);
}

public LiveData<List<Book>> getAllBooks(){
    return bookDao.getAllBooks();
}

public void deleteBook(Book book){
        executorService.execute(()->{
            bookDao.delete(book);
        });
}

public LiveData<List<Book>> getCurrentReads(){
    return bookDao.getCurrentReads();
}

public void addCurrentRead(int id, InsertCallback callback) {
    executorService.execute(() -> {
        int rowId = bookDao.addCurrentRead(id);

        boolean result = rowId > 0;
        callback.onResult(result);
    });
}

public void deleteCurrentReads(Book book){
        executorService.execute(()->{
            bookDao.deleteCurrentReads(book.getId());
        });
}


public LiveData<List<Book>> getFaveBook(){
        return bookDao.getFaveBook();

}

public void addFavorite(Book book, InsertCallback callback){
        executorService.execute(() -> {
            int rowid = bookDao.addFavorite(book.getId());

            boolean result = rowid > 0;

            callback.onResult(result);
        });

}

    public LiveData<List<Book>> getWantToReadBook() {
        return bookDao.getWantToReadBook();
    }

    public void addWant2ReadBooks(int id, InsertCallback callback){
        executorService.execute(()->{
            int rowid = bookDao.addWant2ReadBooks(id);

            boolean result = rowid > 0;
            callback.onResult(result);
        });
    }

    public LiveData<List<Book>> getAlreadyRead() {
        return bookDao.getAllAlreadyRead();
    }

    public void addAlreadyReadBook(Book book, InsertCallback callback) {
        executorService.execute(()->{
            int rowid = bookDao.addAlreadyReadBook(book.getId());

            boolean result = rowid > 0;
            callback.onResult(result);
        });
    }

    public void deleteFavorite(Book book) {
        executorService.execute(()->{
            bookDao.deleteFavorite(book.getId());
        });
    }

    public void deleteWishlist(Book book) {
        executorService.execute(()->{
            bookDao.deleteWishlist(book.getId());
        });
    }

    public void deleteAlreadyReadBooks(Book book) {
        executorService.execute(()->{
            bookDao.deleteAlreadyReadBooks(book.getId());
        });
    }
}




