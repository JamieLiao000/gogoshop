package com.example.liaochieh_yu.gogo2.model;

/**
 * Created by liaochieh-yu on 2018/3/21.
 */

public class Promotion {
    private String name;
    private int pic;
    public  Promotion(){}
    public Promotion(String name, int pic) {
        this.name = name;
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }
}
