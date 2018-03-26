package com.example.liaochieh_yu.gogo2.PurchaseCase;

/**
 * Created by liaochieh-yu on 2018/3/25.
 */

public class PurchaseCaseItem {
    private String title;
    private String content;
    private String date;
    private String owner;
    private int pic;



    public PurchaseCaseItem(String title, String content, String date, String owner, int pic) {

        this.title = title;
        this.content = content;
        this.date = date;
        this.owner = owner;
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }
}
