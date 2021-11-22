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
        int rotationSpaceship = 270;
        int asteroidCount = 6;
        String rutaImages = "file:///" + System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources"
                + File.separator + "images" + File.separator;

        Canvas canvas = new Canvas(width, height);
        GraphicsContext context = canvas.getGraphicsContext2D();
        root.setCenter(canvas);

        ArrayList<String> keyPressed = new ArrayList<>();

        ArrayList<String> onlyOneKey = new ArrayList<>();


        mainScene.setOnKeyPressed((KeyEvent event) -> {
                    String keyName = event.getCode().toString();
                    if (!keyPressed.contains(keyName)) {
                        keyPressed.add(keyName);
                        onlyOneKey.add(keyName);
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

        ArrayList<Sprite> laserList = new ArrayList<>();
        ArrayList<Sprite> asteroidList = new ArrayList<>();

        for (int i = 0; i < asteroidCount; i++) {
            Sprite asteroid = new Sprite(rutaImages + "meteorMed.png");
            //TODO: poner estos números como deribados de las medidas iniciales
            double x = Math.random() * 500 + 300;
            double y = Math.random() * 400 + 100;
            asteroid.position.set(x, y);
            double angle = Math.random() * 360;
            asteroid.speed.setLength(50);
            asteroid.speed.setAngle(angle);
            asteroidList.add(asteroid);
        }


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
                    spaceship.speed.setAngle(spaceship.rotation + rotationSpaceship);
                } else {
                    spaceship.speed.setLength(0);
                }

                if (keyPressed.contains("SPACE")) {
                    Sprite laser = new Sprite(rutaImages + "laserBlue.png");
                    laser.position.set(spaceship.position.x, spaceship.position.y);
                    laser.speed.setLength(400);
                    laser.speed.setAngle(spaceship.rotation + rotationSpaceship);
                    laserList.add(laser);
                }

                //Para limpiar la generación de lasers
                onlyOneKey.clear();


                spaceship.update(1 / 60.0);
                for (Sprite asteroid : asteroidList) {
                    asteroid.update(1 / 60.0);
                }
                for (Sprite laser : laserList) {
                    laser.update(1 / 60.0);
                }
                spaceship.evitarSalida(width, height);
                background.render(context);
                spaceship.render(context);
                for (Sprite laser : laserList) {
                    laser.render(context);
                }
                for (Sprite asteroid : asteroidList) {
                    asteroid.render(context);
                }
            }
        };
        gameloop.start();

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}