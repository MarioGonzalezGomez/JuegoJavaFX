package com.example.juegoid;

public class Rectangle {
    public double x, y, width, height;

    public Rectangle() {
        this.setPosition(0, 0);
        this.setSize(1, 1);
    }

    public Rectangle(double x, double y, double width, double height) {
        this.setPosition(x, y);
        this.setSize(width, height);
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(double w, double h) {
        this.width = w;
        this.height = h;
    }

    public boolean colisiona(Rectangle rec2) {


        boolean noColision = this.x + this.width < rec2.x || rec2.x + rec2.width < this.x ||
               this.y + this.height < rec2.y || rec2.y + this.height < this.y;
        return !noColision;
    }
}
