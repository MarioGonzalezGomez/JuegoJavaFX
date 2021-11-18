package com.example.juegoid;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class JuegoController {
    private StackPane principal;

    private Rectangle leftWall;
    private Rectangle rightWall;
    private Rectangle topWall;
    private Rectangle botWall;

    private double movement;

    private Rectangle player;

    private Timeline animation;

    public JuegoController(Rectangle leftWall, Rectangle rightWall, Rectangle topWall, Rectangle botWall, Rectangle player, StackPane principal) {
        this.leftWall = leftWall;
        this.rightWall = rightWall;
        this.topWall = topWall;
        this.botWall = botWall;
        this.player = player;
        this.principal = principal;
        this.movement = 1;
        inicializarJuego();
        inicializarController();
    }

    private void inicializarController() {
        principal.setOnKeyPressed(e -> {
            animation.play();
            switch (e.getCode()) {
                case A:
                    moverPlayerL();
                    break;
                case W:
                    moverPlayerT();
                    break;
                case S:
                    moverPlayerB();
                    break;
                case D:
                    moverPlayerR();
                    break;
                case LEFT:
                    moverPlayerL();
                    break;
                case UP:
                    moverPlayerT();
                    break;
                case DOWN:
                    moverPlayerB();
                    break;
                case RIGHT:
                    moverPlayerR();
                    break;
            }

        });

        principal.setOnKeyReleased(e -> animation.stop());
        principal.setFocusTraversable(true);
    }

    private void inicializarJuego() {
        this.animation = new Timeline(new KeyFrame(Duration.millis(17), t -> {
            detectarColision();
        }));
        animation.setCycleCount(Animation.INDEFINITE);
        //Esto se debería poner después de pulsar algún botón de inicio
        //animation.play();
    }

    private void detectarColision() {
        if (player.getBoundsInParent().intersects(leftWall.getBoundsInParent()) ||
                player.getBoundsInParent().intersects(rightWall.getBoundsInParent()) ||
                player.getBoundsInParent().intersects(topWall.getBoundsInParent()) ||
                player.getBoundsInParent().intersects(botWall.getBoundsInParent())) {
            player.setTranslateX(0);
            player.setTranslateY(0);
        }
    }

    private void moverPlayerR() {

        player.setTranslateX(player.getTranslateX() + 1);
    }

    private void moverPlayerL() {

        player.setTranslateX(player.getTranslateX() - 1);
    }

    private void moverPlayerT() {

        player.setTranslateY(player.getTranslateY() - 1);
    }

    private void moverPlayerB() {

        player.setTranslateY(player.getTranslateY() + 1);
    }


}
