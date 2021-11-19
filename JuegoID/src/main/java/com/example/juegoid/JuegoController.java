package com.example.juegoid;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class JuegoController {
    private StackPane principal;

    private Rectangle leftWall;
    private Rectangle rightWall;
    private Rectangle topWall;
    private Rectangle botWall;

    private double velocidad = 1;
    private int direction = 1;

    private Rectangle player;

    private Timeline animation;
    private double desplazamientoX;
    private double desplazamientoY;
    private int saltarPixel = 2;

    public JuegoController(Rectangle leftWall, Rectangle rightWall, Rectangle topWall, Rectangle botWall, Rectangle player, StackPane principal) {
        this.leftWall = leftWall;
        this.rightWall = rightWall;
        this.topWall = topWall;
        this.botWall = botWall;
        this.player = player;
        this.principal = principal;
        this.velocidad = 2;
        inicializarJuego();
        inicializarController();
    }

    private void inicializarController() {
        principal.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case A:
                case LEFT:
                    desplazamientoX = -1;
                    break;
                case W:
                case UP:
                    desplazamientoY = -1;
                    break;
                case S:
                case DOWN:
                    desplazamientoY = +1;
                    break;
                case D:
                case RIGHT:
                    desplazamientoX = +1;
                    break;
                case SPACE:
                    animation.play();
                    break;
            }

        });
        principal.setOnKeyReleased(e -> {
            if (e.getCode().toString().matches("A|W|S|D|RIGHT|LEFT|UP|DOWN")) {
                desplazamientoY = 0;
                desplazamientoX = 0;
            }
        });
        principal.setFocusTraversable(true);
    }

    private void inicializarJuego() {
        this.animation = new Timeline(new KeyFrame(Duration.millis(17), t -> {
            moverPlayer();
            detectarColisionX();
            detectarColisionY();
        }));
        animation.setCycleCount(Animation.INDEFINITE);
        //Esto se debería poner después de pulsar algún botón de inicio
    }

    private void detectarColisionX() {
        if (player.getBoundsInParent().intersects(leftWall.getBoundsInParent()) ||
                player.getBoundsInParent().intersects(rightWall.getBoundsInParent())) {
            if (player.getTranslateX() < 0) {
                player.setTranslateX(saltarPixel * -1 + player.getTranslateX() * -1);
            } else {
                player.setTranslateX(saltarPixel + player.getTranslateX() * -1);
            }
        }
    }

    private void detectarColisionY() {
        if (player.getBoundsInParent().intersects(topWall.getBoundsInParent()) ||
                player.getBoundsInParent().intersects(botWall.getBoundsInParent())) {
            if (player.getTranslateY() < 0) {
                player.setTranslateY(saltarPixel * -1 + player.getTranslateY() * -1);
            } else {
                player.setTranslateY(saltarPixel + player.getTranslateY() * -1);
            }
        }
    }

    private void moverPlayer() {
        player.setTranslateX(player.getTranslateX() + desplazamientoX * velocidad);
        player.setTranslateY(player.getTranslateY() + desplazamientoY * velocidad);
    }

    private double magnitudDiagonal() {
        //movimiento X e Y es un valor 1, -1 para indicar la dirección del movimiento
       // desplazamientoX = movimientoX / Math.hypot(movimientoX, movimientoY);

        return Math.sqrt(desplazamientoX * desplazamientoX + desplazamientoY * desplazamientoY);
    }
}