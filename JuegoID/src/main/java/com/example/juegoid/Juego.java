package com.example.juegoid;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Juego extends BorderPane {
    private StackPane principal;

    private Rectangle leftWall;
    private Rectangle rightWall;
    private Rectangle topWall;
    private Rectangle botWall;

    private Rectangle player;

    JuegoController controlador;

    public Juego() {
        iniciarElementos();
    }

    private void iniciarElementos() {
        //Instanciar
        principal = new StackPane();

        leftWall = new Rectangle();
        rightWall = new Rectangle();
        topWall = new Rectangle();
        botWall = new Rectangle();

        player = new Rectangle();

        //Inicializar
        leftWall.setFill(Color.rgb(161, 1, 162));
        leftWall.heightProperty().bind(principal.heightProperty());
        leftWall.widthProperty().bind(principal.widthProperty().divide(40));

        rightWall.setFill(Color.rgb(161, 1, 162));
        rightWall.heightProperty().bind(principal.heightProperty());
        rightWall.widthProperty().bind(principal.widthProperty().divide(40));

        botWall.setFill(Color.rgb(161, 1, 162));
        botWall.heightProperty().bind(principal.widthProperty().divide(40));
        botWall.widthProperty().bind(principal.heightProperty());

        topWall.setFill(Color.rgb(161, 1, 162));
        topWall.heightProperty().bind(principal.widthProperty().divide(40));
        topWall.widthProperty().bind(principal.heightProperty());

        player.setFill(Color.rgb(0, 0, 0));
        player.heightProperty().bind(leftWall.widthProperty());
        player.widthProperty().bind(leftWall.widthProperty());


        //Colocar
        principal.getChildren().addAll(leftWall, rightWall, topWall, botWall, player);
        principal.setAlignment(leftWall, Pos.CENTER_LEFT);
        principal.setAlignment(rightWall, Pos.CENTER_RIGHT);
        principal.setAlignment(topWall, Pos.TOP_CENTER);
        principal.setAlignment(botWall, Pos.BASELINE_CENTER);

        principal.setAlignment(player, Pos.CENTER);
        this.setCenter(principal);

        //Instanciar el controlador
        controlador = new JuegoController(leftWall, rightWall, topWall, botWall, player, principal);
    }

}
