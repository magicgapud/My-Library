package com.example.mylibrary.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mylibrary.data.dao.BookDao;
import com.example.mylibrary.data.local.AppDatabase;
import com.example.mylibrary.data.model.Book;
import com.example.mylibrary.data.utils.InsertCallback;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookRepository {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final BookDao bookDao;

    private LiveData<List<Book>> allBooks;
    private Book book;
    public BookRepository(Application application){
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

}
