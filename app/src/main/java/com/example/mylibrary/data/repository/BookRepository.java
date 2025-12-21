package com.example.mylibrary.data.repository;

import static android.content.ContentValues.TAG;
import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.LiveData;

import com.bumptech.glide.Glide;
import com.example.mylibrary.data.dao.BookDao;
import com.example.mylibrary.data.local.AppDatabase;
import com.example.mylibrary.data.model.Book;
import com.example.mylibrary.data.utils.InsertCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookRepository {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final BookDao bookDao;

    private Context context;
    private LiveData<List<Book>> allBooks;
    private Book book;
    public BookRepository(Application application){
        AppDatabase db = AppDatabase.getINSTANCE(application);
        context = application.getApplicationContext();
        bookDao = db.bookDao();
    }

public void addBook(Uri uri, Book book, InsertCallback callback){

        executorService.execute(()->{


            try{
                File file = copyImageToStorage(uri);

                book.setImageUrl(file.getAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            long rowId = bookDao.insert(book);

            boolean result = rowId > 0;

            callback.onResult(result);

        });
}


    private File copyImageToStorage(Uri uri) throws IOException {

        File dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (dir == null) {
            throw new IOException("External storage not available");
        }

        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Failed to create directory");
        }

        File file = new File(dir, "IMG_" + System.currentTimeMillis() + ".jpg");

        try (
                InputStream inputStream = context.getContentResolver().openInputStream(uri);
                OutputStream outputStream = new FileOutputStream(file)
        ) {
            if (inputStream == null) {
                throw new IOException("Unable to open input stream");
            }

            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
        }

        return file;
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




