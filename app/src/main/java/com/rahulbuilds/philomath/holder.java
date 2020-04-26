package com.rahulbuilds.philomath;

/**
 * Created by undefined on 28/9/17.
 */

public class holder {
    public String text;
    public String imgId;
    public String pdfUrl;
    public String pdfName;
    public String next;
    public String path;
    public holder(){

    }
    public holder(String text, String imgId, String pdfUrl, String pdfName){
        this.text = text;
        this.imgId =imgId;
        this.pdfUrl = pdfUrl;
        this.pdfName =pdfName;
    }
    public holder(String text , String imgId, String pdfUrl, String pdfName, String next, String path){
        this.text = text;
        this.imgId =imgId;
        this.pdfUrl = pdfUrl;
        this.pdfName =pdfName;
        this.next = next;
        this.path = path;
    }
}
