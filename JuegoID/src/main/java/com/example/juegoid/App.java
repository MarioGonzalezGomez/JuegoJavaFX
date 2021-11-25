package com.example.juegoid;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;

public class App extends Application {
    int score;


    @Override
    public void start(Stage stage) {
        stage.setTitle("Láseres al vacío");
        BorderPane root = new BorderPane();
        Scene mainScene = new Scene(root);
        stage.setScene(mainScene);


        int height = 600;
        int width = 800;
        int rotationSpaceship = 270;
        int asteroidCount = 3;
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
            //TODO: poner estos números como derivados de las medidas iniciales
            //TODO: Quizá hacer que vayan girando
            double x = Math.random() * 500 + 300;
            double y = Math.random() * 400 + 100;
            asteroid.position.set(x, y);
            double angle = Math.random() * 360;
            asteroid.speed.setLength(50);
            asteroid.speed.setAngle(angle);
            asteroidList.add(asteroid);
        }

        score = 0;

        var gameLoop = new Timeline(new KeyFrame(Duration.millis(17),a->{
            if (keyPressed.contains("LEFT") || keyPressed.contains("A")) {
                spaceship.rotation -= 3;
            }
            if (keyPressed.contains("RIGHT") || keyPressed.contains("D")) {
                spaceship.rotation += 3;
            }
            if (keyPressed.contains("UP") || keyPressed.contains("W")) {
                spaceship.speed.setLength(150);
                spaceship.speed.setAngle(spaceship.rotation + rotationSpaceship);
            } else if (keyPressed.contains("DOWN") || keyPressed.contains("S")) {
                spaceship.speed.setLength(50);
                spaceship.speed.setAngle(spaceship.rotation + rotationSpaceship + 180);
            } else {
                spaceship.speed.setLength(0);
            }

            if (keyPressed.contains("SPACE")) {
                Sprite laser = new Sprite(rutaImages + "laserBlue.png");
                laser.position.set(spaceship.position.x, spaceship.position.y);
                laser.speed.setLength(400);
                laser.speed.setAngle(spaceship.rotation + rotationSpaceship);
                laser.rotation = spaceship.rotation;
                laserList.add(laser);
            }

            //Para limpiar la generación de lasers
            onlyOneKey.clear();


            spaceship.update();
            for (Sprite asteroid : asteroidList) {
                asteroid.update();
                asteroid.evitarSalida(width, height);
            }
            for (Sprite laser : laserList) {
                laser.update();
            }

            for (int laserNum = 0; laserNum < laserList.size(); laserNum++) {
                Sprite laser = laserList.get(laserNum);
                for (int asteroidNum = 0; asteroidNum < asteroidList.size(); asteroidNum++) {
                    Sprite asteroid = asteroidList.get(asteroidNum);
                    if (laser.colisiona(asteroid)) {
                        laserList.remove(laserNum);
                        asteroidList.remove(asteroidNum);
                        score += 100;
                    }
                    //TODO:se podría implementar algo de este estilo
                    //if (spaceship.colisiona(asteroid)) {
                    //parpadea() Con fadetransition
                    //quitarVida()
                    //ruidoColisión()
                    //}
                }
            }


            spaceship.evitarSalida(width, height);
            background.render(context);
            spaceship.render(context);
            for (Sprite laser : laserList) {

                System.out.println("Laser: " + laser.getLimites());
                laser.render(context);
            }
            for (Sprite asteroid : asteroidList) {
                System.out.println("Asteroide: " + asteroid.getLimites());
                asteroid.render(context);
            }

            //Para dibujar el marcador en la pantalla
            context.setFill(Color.WHITE);
            context.setStroke(Color.GREEN);
            context.setFont(new Font("Arial Black", 48));
            context.setLineWidth(3);
            String text = "Score: " + score;
            int textX = 500;
            int textY = 50;
            context.fillText(text, textX, textY);
            context.strokeText(text, textX, textY);
        }));
        /*AnimationTimer gameloop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (keyPressed.contains("LEFT") || keyPressed.contains("A")) {
                    spaceship.rotation -= 3;
                }
                if (keyPressed.contains("RIGHT") || keyPressed.contains("D")) {
                    spaceship.rotation += 3;
                }
                if (keyPressed.contains("UP") || keyPressed.contains("W")) {
                    spaceship.speed.setLength(150);
                    spaceship.speed.setAngle(spaceship.rotation + rotationSpaceship);
                } else if (keyPressed.contains("DOWN") || keyPressed.contains("S")) {
                    spaceship.speed.setLength(50);
                    spaceship.speed.setAngle(spaceship.rotation + rotationSpaceship + 180);
                } else {
                    spaceship.speed.setLength(0);
                }

                if (keyPressed.contains("SPACE")) {
                    Sprite laser = new Sprite(rutaImages + "laserBlue.png");
                    laser.position.set(spaceship.position.x, spaceship.position.y);
                    laser.speed.setLength(400);
                    laser.speed.setAngle(spaceship.rotation + rotationSpaceship);
                    laser.rotation = spaceship.rotation;
                    laserList.add(laser);
                }

                //Para limpiar la generación de lasers
                onlyOneKey.clear();


                spaceship.update(1 / 200.0);
                for (Sprite asteroid : asteroidList) {
                    asteroid.update(1 / 200.0);
                    asteroid.evitarSalida(width, height);
                }
                for (Sprite laser : laserList) {
                    laser.update(1 / 200.0);
                }

                for (int laserNum = 0; laserNum < laserList.size(); laserNum++) {
                    Sprite laser = laserList.get(laserNum);
                    for (int asteroidNum = 0; asteroidNum < asteroidList.size(); asteroidNum++) {
                        Sprite asteroid = asteroidList.get(asteroidNum);
                        if (laser.colisiona(asteroid)) {
                            laserList.remove(laserNum);
                            asteroidList.remove(asteroidNum);
                            score += 100;
                        }
                        //TODO:se podría implementar algo de este estilo
                        //if (spaceship.colisiona(asteroid)) {
                        //parpadea() Con fadetransition
                        //quitarVida()
                        //ruidoColisión()
                        //}
                    }
                }


                spaceship.evitarSalida(width, height);
                background.render(context);
                spaceship.render(context);
                for (Sprite laser : laserList) {

                    System.out.println("Laser: " + laser.getLimites());
                    laser.render(context);
                }
                for (Sprite asteroid : asteroidList) {
                    System.out.println("Asteroide: " + asteroid.getLimites());
                    asteroid.render(context);
                }

                //Para dibujar el marcador en la pantalla
                context.setFill(Color.WHITE);
                context.setStroke(Color.GREEN);
                context.setFont(new Font("Arial Black", 48));
                context.setLineWidth(3);
                String text = "Score: " + score;
                int textX = 500;
                int textY = 50;
                context.fillText(text, textX, textY);
                context.strokeText(text, textX, textY);
            }
        }*/

        gameLoop.setCycleCount(Animation.INDEFINITE);
        gameLoop.play();

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}