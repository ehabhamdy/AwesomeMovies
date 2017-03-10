package com.ehab.awesomemovies.model;

/**
 * Created by ehabhamdy on 3/10/17.
 */

public class Trailer {
    private String name;
    private String size;
    private String source;
    private String type;

    public Trailer() {
    }

    public Trailer(String name, String size, String source, String type) {
        this.name = name;
        this.size = size;
        this.source = source;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getSource() {
        return source;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setType(String type) {
        this.type = type;
    }
}
