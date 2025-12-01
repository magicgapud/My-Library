package com.example.mylibrary.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Book")
public class Book {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "author")
    private String author;

    @ColumnInfo(name = "pages")
    private String pages;

    @ColumnInfo(name = "imageUrl")
    private String imageUrl;

    @ColumnInfo(name = "shortDesc")
    private String shortDesc;

    @ColumnInfo(name = "longDesc")
    private String longDesc;

    @ColumnInfo(name = "alreadyreadbooks")
    private String alreadyReadBooks;

    @ColumnInfo(name = "currentBooks")
    private String currentBooks;

    @ColumnInfo(name = "wishlist")
    private String wishlist;

    @ColumnInfo(name = "favorite")
    private String favorite;

    @ColumnInfo(name = "isExpanded")
    private boolean isExpanded;


    public Book(String name, String author, String pages, String imageUrl, String shortDesc, String longDesc, String alreadyReadBooks, String currentBooks, String wishlist, String favorite, boolean isExpanded) {
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.imageUrl = imageUrl;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.alreadyReadBooks = alreadyReadBooks;
        this.currentBooks = currentBooks;
        this.wishlist = wishlist;
        this.favorite = favorite;
        this.isExpanded = isExpanded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public String getAlreadyReadBooks() {
        return alreadyReadBooks;
    }

    public void setAlreadyReadBooks(String alreadyReadBooks) {
        this.alreadyReadBooks = alreadyReadBooks;
    }

    public String getCurrentBooks() {
        return currentBooks;
    }

    public void setCurrentBooks(String currentBooks) {
        this.currentBooks = currentBooks;
    }

    public String getWishlist() {
        return wishlist;
    }

    public void setWishlist(String wishlist) {
        this.wishlist = wishlist;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
