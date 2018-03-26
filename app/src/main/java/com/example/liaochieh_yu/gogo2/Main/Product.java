package com.example.liaochieh_yu.gogo2.Main;

/**
 * Created by liaochieh-yu on 2018/3/21.
 */

public class Product {
    private String name;
    private int price;
    private int pic;

    public Product(String name, int price, int pic) {
        this.name = name;
        this.price = price;
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }
}
