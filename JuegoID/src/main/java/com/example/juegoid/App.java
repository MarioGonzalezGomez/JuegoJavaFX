package com.example.juegoid;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class App extends Application {


    @Override
    public void start(Stage stage) {
        stage.setTitle("Láseres al vacío");
        BorderPane root = new BorderPane();
        Scene mainScene = new Scene(root);
        stage.setScene(mainScene);


        int height = 600;
        int width = 800;
        String rutaImages = "file:///" + System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources"
                + File.separator + "images" + File.separator;

        Canvas canvas = new Canvas(width, height);
        GraphicsContext context = canvas.getGraphicsContext2D();
        root.setCenter(canvas);

        ArrayList<String> keyPressed = new ArrayList<>();
        mainScene.setOnKeyPressed((KeyEvent event) -> {
                    String keyName = event.getCode().toString();
                    if (!keyPressed.contains(keyName)) {
                        keyPressed.add(keyName);
                    }
                }
        );

        mainScene.setOnKeyReleased((KeyEvent event) -> {
                    String keyName = event.getCode().toString();
                    if (keyPressed.contains(keyName)) {
                        keyPressed.remove(keyName);
                    }
                }
        );


        Sprite background = new Sprite(rutaImages + "estelarMorado.jpg");
        //Para que nuestro personaje aparezca en el centro de la pantalla
        background.position.set(width / 2, height / 2);


        Sprite spaceship = new Sprite(rutaImages + "playerShip1_blue.png");
        spaceship.position.set(width / 2, height / 1.25);

        AnimationTimer gameloop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (keyPressed.contains("LEFT")) {
                    spaceship.rotation -= 3;
                }
                if (keyPressed.contains("RIGHT")) {
                    spaceship.rotation += 3;
                }
                if (keyPressed.contains("UP")) {
                    spaceship.speed.setLength(150);
                    spaceship.speed.setAngle(spaceship.rotation + 270);
                } else {
                    spaceship.speed.setLength(0);
                }
                spaceship.update(1 / 60.0);
                spaceship.evitarSalida(width, height);
                background.render(context);
                spaceship.render(context);
            }
        };
        gameloop.start();

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}