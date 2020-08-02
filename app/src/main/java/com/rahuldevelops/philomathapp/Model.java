package com.rahuldevelops.philomathapp;

import android.widget.TextView;

public class Model {

    private String WORD;
    private String Meaning;
    private String Example;
    private String phoentics;



    private String category;

    public Model(String word,String meaning,String example,String phoentics,String category){
        this.WORD=word;
        this.Meaning=meaning;
        this.Example=example;
        this.phoentics = phoentics;
        this.category=category;

    }
    public String getPhoentics() {
        return phoentics;
    }

    public void setPhoentics(String phoentics) {
        this.phoentics = phoentics;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getWORD() {
        return WORD;
    }

    public void setWORD(String WORD) {
        this.WORD = WORD;
    }

    public String getMeaning() {
        return Meaning;
    }

    public void setMeaning(String meaning) {
        Meaning = meaning;
    }

    public String getExample() {
        return Example;
    }

    public void setExample(String example) {
        Example = example;
    }
}
