/*package com.example.juegoid.noUtilizado;

import com.example.juegoid.Sprite;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Game extends BorderPane {
    public int score;

    public Game() {
        iniciarElementos();
    }

    private void iniciarElementos() {
        int height = 600;
        int width = 800;
        int rotationSpaceship = 270;

        Canvas canvas = new Canvas(width, height);
        GraphicsContext context = canvas.getGraphicsContext2D();
        this.setCenter(canvas);

        Sprite background = loadSprite("estelarMorado.jpg");
        background.position.set(width / 2, height / 2);

        Sprite spaceship = loadSprite("playerShip1_blue.png");
        spaceship.position.set(width / 2, height / 1.25);

        ArrayList<String> keyPressed = new ArrayList<>();
      //keyListeners(mainScene, keyPressed);


        ArrayList<Sprite> asteroidList = new ArrayList<>();
        loadAsteroids(asteroidList);

        ArrayList<Sprite> laserList = new ArrayList<>();

        score = 0;

    }

    public void start() {


        //stage.setTitle("Láseres al vacío");
        Scene mainScene = new Scene(this);
        // stage.setScene(mainScene);



//ArrayList<String> onlyOneKey = new ArrayList<>();



        AnimationTimer gameloop = new AnimationTimer() {
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
                    shootLaser(spaceship, rotationSpaceship, laserList);
                }

                //Para limpiar la generación de lasers
                //onlyOneKey.clear();


                spaceship.update(1 / 60.0);
                for (Sprite asteroid : asteroidList) {
                    asteroid.update(1 / 60.0);
                    asteroid.evitarSalida(width, height);
                }
                for (Sprite laser : laserList) {
                    laser.update(1 / 60.0);
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
                    }
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
        };

        gameloop.start();

        stage.show();
    }

    private Sprite loadSprite(String nombreImagen) {
        String rutaImages = "file:///" + System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources"
                + File.separator + "images" + File.separator;
        Sprite sprite = new Sprite(rutaImages + nombreImagen);
        return sprite;
    }

    private void loadAsteroids(List<Sprite> asteroidList) {
        int asteroidCount = 6;

        for (int i = 0; i < asteroidCount; i++) {
            Sprite asteroid = loadSprite("meteorMed.png");
            //TODO: poner estos números como derivados de las medidas iniciales
            //TODO: Quizá hacer que vayan girando
            //TODO: Que se vayan generando de varios tamaños
            //TODO: Quizá, que se generen más a lo lago del tiempo
            double x = Math.random() * 500 + 300;
            double y = Math.random() * 400 + 100;
            asteroid.position.set(x, y);
            double angle = Math.random() * 360;
            asteroid.speed.setLength(50);
            asteroid.speed.setAngle(angle);
            asteroidList.add(asteroid);
        }
    }

    private void shootLaser(Sprite spaceship, int rotationSpaceship, List<Sprite> laserList) {
        Sprite laser = loadSprite("laserBlue.png");
        laser.position.set(spaceship.position.x, spaceship.position.y);
        laser.speed.setLength(400);
        laser.speed.setAngle(spaceship.rotation + rotationSpaceship);
        laserList.add(laser);
    }

    private void keyListeners(Scene mainScene, List<String> keyPressed) {
        mainScene.setOnKeyPressed((KeyEvent event) -> {
                    String keyName = event.getCode().toString();
                    if (!keyPressed.contains(keyName)) {
                        keyPressed.add(keyName);
                        // onlyOneKey.add(keyName);
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
    }

}*/
