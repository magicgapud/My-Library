package com.example.mylibrary;

import com.example.mylibrary.data.model.Book;

import java.util.ArrayList;

public class Utils {

    private static Utils instance;

    private static ArrayList<Book> allBooks;
    private static ArrayList<Book> alreadyReadBooks;
    private static ArrayList<Book> wantToReadBooks;
    private static ArrayList<Book> currentlyReadingBooks;
    private static ArrayList<Book> favoriteBooks;

    private Utils() {
        if (null == allBooks) {
            allBooks = new ArrayList<>();
           // initData();
        }

        if (null == alreadyReadBooks) {
            alreadyReadBooks = new ArrayList<>();
        }

        if (null == wantToReadBooks) {
            wantToReadBooks = new ArrayList<>();
        }

        if (null == currentlyReadingBooks) {
            currentlyReadingBooks = new ArrayList<>();
        }

        if (null == favoriteBooks) {
            favoriteBooks = new ArrayList<>();
        }

    }

//    private void initData() {
//        //TODO: add initial data
//        allBooks.add(new Book(1, "1Q84", "Haruka Murakami", "1350", "https://images-na.ssl-images-amazon" +
//                ".com/images/S/compressed.photo.goodreads.com/books/1483103331i/10357575.jpg", "A " +
//                "work of maddening brilliance", "Long Description"));
//        allBooks.add(new Book(2, "The Myth of Sisyphus", "Albert Camus", "250", "https://cdn.kobo.com/book-images/6fb78c53-4a0f-49ad-bd80-c138be1f02d3/353/569/90/False/the-myth-of-sisyphus-4.jpg", "A " +
//                "The Myth of Sisyphus\" by Albert Camus is a philosophical exploration of absurdism, existentialism, and the human condition within French literature", "Long Description"));
//
//    }

    public static Utils getInstance() {
        if (null != instance) {
            return instance;
        } else {
            instance = new Utils();
            return instance;
        }
    }

    public static ArrayList<Book> getAllBooks() {
        return allBooks;
    }

    public static ArrayList<Book> getAlreadyReadBooks() {
        return alreadyReadBooks;
    }

    public static ArrayList<Book> getWantToReadBooks() {
        return wantToReadBooks;
    }

    public static ArrayList<Book> getCurrentlyReadingBooks() {
        return currentlyReadingBooks;
    }

    public static ArrayList<Book> getFavoriteBooks() {
        return favoriteBooks;
    }

    public Book getBookById(int id) {
        for (Book b : allBooks) {
            if (b.getId() == id) {
                return b;
            }
        }

        return null;
    }

    public boolean addToAlreadyRead(Book book) {
        return alreadyReadBooks.add(book);
    }

    public boolean addWantToReadBooks(Book book) {
        return wantToReadBooks.add(book);
    }

    public boolean addFavorites(Book book){
        return favoriteBooks.add(book);
    }

    public boolean addCurrentReads(Book book){
        return currentlyReadingBooks.add(book);
    }

    public boolean removeToAlreadyRead(Book book) {
        return alreadyReadBooks.remove(book);
    }

    public boolean removeWantToReadBooks(Book book) {
        return wantToReadBooks.remove(book);
    }

    public boolean removeFavorites(Book book){
        return favoriteBooks.remove(book);
    }

    public boolean removeCurrentReads(Book book) {
        return currentlyReadingBooks.remove(book);
    }
}
