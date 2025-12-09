package com.example.mylibrary;

import static com.example.mylibrary.Utils.favoriteBooks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.mylibrary.data.model.Book;
import com.example.mylibrary.data.repository.BookRepository;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    private Button btnCurrRead, btnWant2Read, btnAlreadyRead, btnAddFavorites;
    private TextView txtBookNameLbl, txtAuthorLbl, txtDesc, txtLongDescLbl, txtBookName,
            txtAuthor, txtPagesLbl, txtPages;

    private ImageView imgBookImage;
    private static final String TAG = "BookActivity";

    BookRepository bookRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bookActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();

        Intent intent = getIntent();
        if (null != intent) {

            bookRepository = new BookRepository(getApplication());
            int bookId = intent.getIntExtra("bookId", -1);
            Log.d(TAG, "onCreate: "+ bookId);
            if (bookId != -1) {
                bookRepository.findById(bookId).observe(this, book -> {
                    Log.d(TAG, "onCreate: "+ book.getId() + "sample");
                    if (null != book) {
                        setData(book);

                        handleAlreadyReadBook(book);
                        handleWantToReadBook(book);
                        handleFavoriteBook(book);
                        handleCurrentReads(book);

                    }

                });
            }

        }
    }

    private void handleCurrentReads(Book book) {
        btnCurrRead.setOnClickListener(view -> {
            bookRepository.addCurrentRead(book.getId(), success -> {
                runOnUiThread(()->{
                    if (success) {
                        Toast.makeText(this,
                                "Saved " + book.getName() + " to Currently Reading",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(BookActivity.this, CurrentlyReadingBooks.class));
                    } else {
                        Toast.makeText(this,
                                "Something went wrong!!",
                                Toast.LENGTH_SHORT).show();
                    }
                });

            });
        });

        bookRepository.getCurrentReads().observe(this, currentReads -> {
            boolean exists = false;
            for (Book b : currentReads) {
                if (b.getId() == book.getId()) {
                    exists = true;
                    break;
                }
            }
            btnCurrRead.setEnabled(!exists);
        });
    };



    private void handleFavoriteBook(Book incomingBook) {
        //ArrayList<Book> favoriteBooks = Utils.getInstance().getFavoriteBooks();
        
        bookRepository.getFaveBook().observe(this,favorite -> {
            boolean exists = false;
            for (Book b: favorite){
                if(b.getId() == incomingBook.getId()){
                    exists = true;
                    break;
                }
            }
            btnAddFavorites.setEnabled(!exists);
        });
        
        boolean isExistFavorites = false;

        for(Book b: favoriteBooks){
            if (b.getId() == incomingBook.getId()){
                isExistFavorites = true;
            }

        }

        if(isExistFavorites){
            btnAddFavorites.setEnabled(false);
        }else{
            btnAddFavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Utils.getInstance().addFavorites(incomingBook)){
                        Toast.makeText(BookActivity.this, "Saved " + incomingBook.getName() + " to " +
                                "Favorites", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, FavoritesBook.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(BookActivity.this, "Something went wrong",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleWantToReadBook(Book book) {

        ArrayList<Book> wantToReadBooks = Utils.getInstance().getWantToReadBooks();
        boolean isExistWantToReadBooks = false;

        for (Book b : wantToReadBooks) {
            if (b.getId() == book.getId()) {
                isExistWantToReadBooks = true;
            }
        }
        if (isExistWantToReadBooks) {
            btnWant2Read.setEnabled(false);
        } else {
            btnWant2Read.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.getInstance().addWantToReadBooks(book)) {
                        Toast.makeText(BookActivity.this, "Saved " + book.getName() + " to " +
                                "Wishlist", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, WantToRead.class);
                        BookActivity.this.startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something went wrong",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleAlreadyReadBook(Book book) {

        ArrayList<Book> alreadyReadBooks = Utils.getInstance().getAlreadyReadBooks();
        boolean isExistInAlreadyReadBooks = false;

        for (Book b : alreadyReadBooks) {
            if (b.getId() == book.getId()) {
                isExistInAlreadyReadBooks = true;
            }
        }

       /*
       to handle already read books
        */
        if (isExistInAlreadyReadBooks) {
            btnAlreadyRead.setEnabled(false);
        } else {
            btnAlreadyRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.getInstance().addToAlreadyRead(book)) {
                        Toast.makeText(BookActivity.this, "Saved " + book.getName() + " to Already " +
                                        "Read Books",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, AlreadyReadBookActivity.class);
                        BookActivity.this.startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something wrong happened, try again",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void setData(Book book) {
        txtBookName.setText(book.getName());
        txtAuthor.setText(book.getAuthor());
        txtPages.setText(String.valueOf(book.getPages()));
        txtDesc.setText(book.getShortDesc());
        txtLongDescLbl.setText(book.getLongDesc());
        Glide.with(this)
                .asBitmap()
                .load(book.getImageUrl())
                .into(imgBookImage);
    }

    private void initViews() {
        //buttons
        btnCurrRead = findViewById(R.id.btnCurrRead);
        btnWant2Read = findViewById(R.id.btnWant2Read);
        btnAlreadyRead = findViewById(R.id.btnAlreadyRead);
        btnAddFavorites = findViewById(R.id.btnAddFavorites);

        //Text
        txtBookNameLbl = findViewById(R.id.txtBookNameLbl);
        txtAuthorLbl = findViewById(R.id.txtAuthorLbl);
        txtDesc = findViewById(R.id.txtDesc);
        txtLongDescLbl = findViewById(R.id.txtLongDescLbl);
        txtBookName = findViewById(R.id.txtBookName);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtPagesLbl = findViewById(R.id.txtPagesLbl);
        txtPages = findViewById(R.id.txtPages);

        //Image
        imgBookImage = findViewById(R.id.imgBookImage);

    }

}