package com.example.mylibrary;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylibrary.data.repository.BookRepository;

public class CurrentlyReadingBooks extends AppCompatActivity {

    private BookRecViewAdapter adapter;
    private RecyclerView currentlyReading;
    private BookRepository bookRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_currently_reading_books);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BookRepository bookRepository = new BookRepository(getApplication());

        currentlyReading = findViewById(R.id.currentlyReading);
        currentlyReading.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookRecViewAdapter(this, "CurrentReads", bookRepository);
        //adapter.setBooks(Utils.getInstance().getCurrentlyReadingBooks());
        bookRepository = new BookRepository(getApplication());
        bookRepository.getCurrentReads().observe(this,book ->{
                adapter.setBooks(book);
        });
        currentlyReading.setAdapter(adapter);



        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(CurrentlyReadingBooks.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
    }
}