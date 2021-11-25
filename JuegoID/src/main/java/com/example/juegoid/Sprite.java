package com.example.juegoid;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {
    public Vector position;
    public Vector speed;
    public double rotation;
    //public Rectangle limites;
    public Rectangulo2 limites;
    public Image image;

    public Sprite() {

        this.position = new Vector();
        this.speed = new Vector();
        this.rotation = 0;
        // this.limites = new Rectangle();
        this.limites = new Rectangulo2();
    }

    public Sprite(String rutaImagen) {
        this();
        setImage(rutaImagen);
    }

    public void setImage(String ruta) {
        this.image = new Image(ruta);
        //this.limites.setSize(this.image.getWidth(), this.image.getHeight());
        this.limites.setWidth(this.image.getWidth());
        this.limites.setHeight(this.image.getHeight());
    }

    // public Rectangle getLimites() {
    public Bounds getLimites() {
        // this.limites.setPosition(this.position.x, this.position.y);
        this.limites.setY(this.position.y);
        this.limites.setX(this.position.x);

        return this.limites.getBoundsInLocal();
    }

    public boolean colisiona(Sprite sprite2) {
        return this.limites.getBoundsInLocal().intersects(sprite2.getLimites());
    }

    public void evitarSalida(double screenWidth, double screenHeight) {
        if (this.position.x + this.image.getWidth() / 2 < 0)
            this.position.x = screenWidth + this.image.getWidth() / 2;
        if (this.position.x > screenWidth + this.image.getWidth() / 2)
            this.position.x = -this.image.getWidth() / 2;
        if (this.position.y + this.image.getHeight() / 2 < 0)
            this.position.y = screenHeight;
        if (this.position.y > screenHeight)
            this.position.y = -this.image.getHeight() / 2;
    }

    public void update(double time) {
        this.position.add(this.speed.x * time, this.speed.y * time);
    }

    public void update() {
        this.position.add(this.speed.x, this.speed.y);
    }

    public void render(GraphicsContext gc) {
        gc.save();
        gc.translate(this.position.x, this.position.y);
        gc.rotate(this.rotation);
        gc.translate(-this.image.getWidth() / 2, -this.image.getHeight() / 2);
        gc.drawImage(this.image, 0, 0);
        gc.restore();
    }
}
