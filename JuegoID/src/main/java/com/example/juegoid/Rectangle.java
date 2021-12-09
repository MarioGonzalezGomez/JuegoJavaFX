package com.example.juegoid;

public class Rectangle {
    public double x, y, width, height;

    public Rectangle() {
        this.setPosition(0, 0);
        this.setSize(1, 1);
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(double w, double h) {
        this.width = w;
        this.height = h;
    }

}
