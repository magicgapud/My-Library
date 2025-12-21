package com.example.mylibrary;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.example.mylibrary.data.model.Book;
import com.example.mylibrary.data.repository.BookRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AddBooks extends AppCompatActivity {

    private MutableLiveData<String>  mLedttxtName, mLedttxtAuthor, mLedttxtPages,
            mLedttxtShortDesc, mLedttxtLongDesc, mlimageView ;


    private EditText edttxtName, edttxtAuthor, edttxtPages, edttxtShortDesc, edttxtLongDesc;

    private TextView tvInfoName, tvInfoAuthor, tvInfoImage, tvInfoPages, tvInfoShortDesc,
            tvInfoLongDesc;

    private boolean isResetting = false;

    private Button btnAddBook, btnEditPhoto;

    private ArrayList Books;
    private ImageView imageView;
    private Uri selectedImage;

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
        //initialize data
        initialize();


        //add the book to record
        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validateField()) {
                    String name = edttxtName.getText().toString();
                    String author = edttxtAuthor.getText().toString();
                    String pages = edttxtPages.getText().toString();
                    String shortDesc = edttxtShortDesc.getText().toString();
                    String longDesc = edttxtLongDesc.getText().toString();


                    Book book = new Book(name, author, pages, "", shortDesc, longDesc, "", "", "", ""
                            , false);

                    bookRepository.addBook(selectedImage, book, result -> {

                        runOnUiThread(() -> {
                            if (result) {

                                Toast.makeText(AddBooks.this, book.getName() + " is inserted",
                                        Toast.LENGTH_SHORT).show();
                                resetFields();

                            } else {
                                Toast.makeText(AddBooks.this, "Inserting the book failed!", Toast.LENGTH_SHORT).show();
                            }
                            ;
                        });
                    });
                }else{
                    Toast.makeText(AddBooks.this, "Required field should be entered!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        //select photo
        btnEditPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePicker.launch("image/*");
            }
        });




        //change in name field
        edttxtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                if(isResetting) return;
                mLedttxtName.setValue(editable.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        });

        mLedttxtName.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(isResetting) return;

                if(!s.isEmpty()){
                    tvInfoName.setTextColor(Color.parseColor("#4caf50"));
                    tvInfoName.setText("Valid");
                }else{
                    tvInfoName.setTextColor(Color.parseColor("#d32f2f"));
                    tvInfoName.setText("Name is Required");
                }
                }
        });


        //change in author field
        edttxtAuthor.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                if(isResetting) return;
                mLedttxtAuthor.setValue(editable.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        });

        mLedttxtAuthor.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(isResetting) return;
                if(!s.isEmpty()){
                    tvInfoAuthor.setTextColor(Color.parseColor("#4caf50"));
                    tvInfoAuthor.setText("Valid");
                }else{
                    tvInfoAuthor.setTextColor(Color.parseColor("#d32f2f"));
                    tvInfoAuthor.setText("Author is Required");
                }
                }

        });


        //change in pages field
        edttxtPages.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                if(isResetting) return;
                mLedttxtPages.setValue(editable.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        });

        mLedttxtPages.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(isResetting) return;
                if(!s.isEmpty()){
                    tvInfoPages.setTextColor(Color.parseColor("#4caf50"));
                    tvInfoPages.setText("Valid");
                }else{
                    tvInfoPages.setTextColor(Color.parseColor("#d32f2f"));
                    tvInfoPages.setText("Pages is Required");
                }
                }

        });

        //change in short desc field
        edttxtShortDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                if(isResetting) return;
                mLedttxtShortDesc.setValue(editable.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        });

        mLedttxtShortDesc.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(isResetting) return;
                if(!s.isEmpty()){
                    tvInfoShortDesc.setTextColor(Color.parseColor("#4caf50"));
                    tvInfoShortDesc.setText("Valid");
                }else{
                    tvInfoShortDesc.setTextColor(Color.parseColor("#d32f2f"));
                    tvInfoShortDesc.setText("Short description is Required");
                }
                }

        });


        //change in long desc field
        edttxtLongDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                if(isResetting) return;
                mLedttxtLongDesc.setValue(editable.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        });

        mLedttxtLongDesc.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(isResetting) return;
                if(!s.isEmpty()){
                    tvInfoLongDesc.setTextColor(Color.parseColor("#4caf50"));
                    tvInfoLongDesc.setText("Valid");
                }else{
                    tvInfoLongDesc.setTextColor(Color.parseColor("#d32f2f"));
                    tvInfoLongDesc.setText("Long description is Required");
                }
                }

        });


        //change in images field
        mlimageView.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(isResetting) return;
                if(!s.isEmpty()){
                    tvInfoImage.setTextColor(Color.parseColor("#4caf50"));
                    tvInfoImage.setText("Valid");
                }else{
                    tvInfoImage.setTextColor(Color.parseColor("#d32f2f"));
                    tvInfoImage.setText("Image is Required");
                }
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

        tvInfoName = findViewById(R.id.tvInfoName);
        tvInfoAuthor = findViewById(R.id.tvInfoAuthor);
        tvInfoImage = findViewById(R.id.tvInfoImage);
        tvInfoPages = findViewById(R.id.tvInfoPages);
        tvInfoShortDesc = findViewById(R.id.tvInfoShortDesc);
        tvInfoLongDesc = findViewById(R.id.tvInfoLongDesc);

        //livedata initialize
        mLedttxtName = new MutableLiveData<>();
        mLedttxtAuthor = new MutableLiveData<>();
        mLedttxtPages = new MutableLiveData<>();
        mLedttxtShortDesc = new MutableLiveData<>();
        mLedttxtLongDesc = new MutableLiveData<>();
        mlimageView = new MutableLiveData<>();

    }


    private ActivityResultLauncher<String> imagePicker =
            registerForActivityResult(new ActivityResultContracts.GetContent(),uri ->{
                if(uri != null){
                    selectedImage = uri;
                    Glide.with(this).load(uri).into(imageView);
                    imageView.setTag(selectedImage);
                    if(!isResetting){
                        mlimageView.setValue(imageView.getTag().toString());
                    } ;
                }
            });

    private boolean validateField(){
        boolean isNotEmpty = true;
        if(edttxtName.getText().toString().trim().isEmpty()){
            tvInfoName.setTextColor(Color.parseColor("#d32f2f"));
            tvInfoName.setText("Name is Required");
            isNotEmpty = false;
        } else {
            tvInfoName.setTextColor(Color.parseColor("#4caf50"));
            tvInfoName.setText("Valid");
        };

        if(edttxtAuthor.getText().toString().trim().isEmpty()){
            tvInfoAuthor.setTextColor(Color.parseColor("#d32f2f"));
            tvInfoAuthor.setText("Author is Required");
            isNotEmpty = false;
        } else {
            tvInfoAuthor.setTextColor(Color.parseColor("#4caf50"));
            tvInfoAuthor.setText("Valid");
        };

        if(imageView.getTag() ==  null){
            tvInfoImage.setTextColor(Color.parseColor("#d32f2f"));
            tvInfoImage.setText("Image is Required");
            isNotEmpty = false;
        } else {
            tvInfoImage.setTextColor(Color.parseColor("#4caf50"));
            tvInfoImage.setText("Valid");
        };

        if(edttxtPages.getText().toString().trim().isEmpty()){
            tvInfoPages.setTextColor(Color.parseColor("#d32f2f"));
            tvInfoPages.setText("Pages is Required");
            isNotEmpty = false;
        } else {
            tvInfoPages.setTextColor(Color.parseColor("#4caf50"));
            tvInfoPages.setText("Valid");
        };

        if(edttxtShortDesc.getText().toString().trim().isEmpty()){
            tvInfoShortDesc.setTextColor(Color.parseColor("#d32f2f"));
            tvInfoShortDesc.setText("Short description is Required");
            isNotEmpty = false;
        } else {
            tvInfoShortDesc.setTextColor(Color.parseColor("#4caf50"));
            tvInfoShortDesc.setText("Valid");
        };

        if(edttxtLongDesc.getText().toString().trim().isEmpty()){
            tvInfoLongDesc.setTextColor(Color.parseColor("#d32f2f"));
            tvInfoLongDesc.setText("Long description is Required");
            isNotEmpty = false;
        } else {
            tvInfoLongDesc.setTextColor(Color.parseColor("#4caf50"));
            tvInfoLongDesc.setText("Valid");
        };

        return isNotEmpty;
    };

    private void resetFields(){
        isResetting = true;

        edttxtName.setText(null);
        edttxtAuthor.setText(null);
        edttxtPages.setText(null);
        edttxtShortDesc.setText(null);
        edttxtLongDesc.setText(null);
        selectedImage = null;
        imageView.setTag(null);

        tvInfoName.setText("");
        tvInfoAuthor.setText("");
        tvInfoImage.setText("");
        tvInfoPages.setText("");
        tvInfoShortDesc.setText("");
        tvInfoLongDesc.setText("");

        mLedttxtName.setValue("");
        mLedttxtAuthor.setValue("");
        mLedttxtPages.setValue("");
        mLedttxtShortDesc.setValue("");
        mLedttxtLongDesc.setValue("");
        mlimageView.setValue("");


        Glide.with(this).clear(imageView);


        imageView.setImageDrawable(null);
        imageView.setImageResource(R.mipmap.librarylogo);
        imageView.setTag(null);


        isResetting = false;
    }


}