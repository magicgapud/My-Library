package com.example.mylibrary.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mylibrary.data.dao.BookDao;
import com.example.mylibrary.data.model.Book;

@Database(version = 1, entities = {Book.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract BookDao bookDao();
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getINSTANCE(Context context) {
        if(INSTANCE == null){
                synchronized (AppDatabase.class){
                    if(INSTANCE == null){
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                AppDatabase.class, "bookdb").build();
                    }
                }
        }
        return INSTANCE;
    }

}
