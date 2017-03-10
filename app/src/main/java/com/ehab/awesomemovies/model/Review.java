package com.ehab.awesomemovies.model;

/**
 * Created by ehabhamdy on 3/10/17.
 */

public class Review {
    private String id;
    private String author;
    private String content;

    public Review() {
    }

    public Review(String id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
