package com.example.juegoid;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {
    public Vector position;
    public Vector speed;
    public double rotation;
    public Rectangle limites;
    public Image image;

    public Sprite() {

        this.position = new Vector();
        this.speed = new Vector();
        this.rotation = 0;
        this.limites = new Rectangle();
    }

    public Sprite(String rutaImagen) {
        this();
        setImage(rutaImagen);
    }

    public void setImage(String ruta) {
        this.image = new Image(ruta);
        this.limites.setSize(this.image.getWidth(), this.image.getHeight());
    }

    public Rectangle getLimites() {
        this.limites.setPosition(this.position.x, this.position.y);
        return this.limites;
    }

    public boolean colisionNave(Sprite sprite2) {
        return this.limites.coliasion(sprite2.getLimites());
    }

    public void evitarSalida(double screenWidth, double screenHeight) {
        if (this.position.x + this.image.getWidth() < 0)
            this.position.x = screenWidth;
        if (this.position.x > screenWidth)
            this.position.x = -this.image.getWidth();
        if (this.position.y + this.image.getHeight() < 0)
            this.position.y = screenHeight;
        if (this.position.y > screenHeight)
            this.position.y = -this.image.getHeight();
    }

    public void update(double time) {
        this.position.add(this.speed.x * time, this.speed.y * time);
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
