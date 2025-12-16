package com.example.mylibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.bumptech.glide.Glide;
import com.example.mylibrary.data.model.Book;
import com.example.mylibrary.data.repository.BookRepository;

import java.util.ArrayList;

public class AddBooks extends AppCompatActivity {

    private EditText edttxtName, edttxtAuthor, edttxtPages, edttxtShortDesc, edttxtLongDesc;

    private Button btnAddBook, btnEditPhoto;

    private ArrayList Books;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_books);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BookRepository bookRepository = new BookRepository(getApplication());
        initialize();

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = edttxtName.getText().toString();
                String author = edttxtAuthor.getText().toString();
                String pages = edttxtPages.getText().toString();
                String shortDesc = edttxtShortDesc.getText().toString();
                String longDesc = edttxtLongDesc.getText().toString();

                Book book = new Book(name, author, pages,shortDesc, longDesc,"" ,"" ,"","","",
                        false);

                bookRepository.addBook(book, result -> {

                    runOnUiThread( ()->{
                        if(result){
                            Toast.makeText(AddBooks.this, book.getName()+ " is inserted",
                                    Toast.LENGTH_SHORT).show();
                            edttxtName.setText(null);
                            edttxtAuthor.setText(null);
                            edttxtPages.setText(null);
                            edttxtShortDesc.setText(null);
                            edttxtLongDesc.setText(null);
                        }else{
                            Toast.makeText(AddBooks.this, "Inserting the book failed!", Toast.LENGTH_SHORT).show();
                        };
                    });
                });
            }
        });


        btnEditPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePicker.launch("image/*");
            }
        });


    }

    private void initialize() {
        edttxtName = findViewById(R.id.edttxtName);
        edttxtAuthor = findViewById(R.id.edttxtAuthor);
        edttxtPages = findViewById(R.id.edttxtPages);
        edttxtShortDesc = findViewById(R.id.edttxtShortDesc);
        edttxtLongDesc = findViewById(R.id.edttxtLongDesc);

        btnAddBook = findViewById(R.id.btnAddBook);
        btnEditPhoto = findViewById(R.id.btnEditPhoto);

        imageView = findViewById(R.id.imageView);
    }


    private ActivityResultLauncher<String> imagePicker =
            registerForActivityResult(new ActivityResultContracts.GetContent(),uri ->{
                if(uri != null){
                    Glide.with(this).load(uri).into(imageView);
                }
            });


}