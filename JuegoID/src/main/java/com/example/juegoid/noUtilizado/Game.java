package com.example.juegoid.noUtilizado;

import com.example.juegoid.Sprite;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Game extends BorderPane {
    public int score;
    private int height = 600;
    private int width = 800;
    private int rotationSpaceship = 270;

    private int asteroidCount = 6;

    public Game() {
        iniciarElementos();
    }

    private void iniciarElementos() {


        Canvas canvas = new Canvas(width, height);
        GraphicsContext context = canvas.getGraphicsContext2D();
        this.setCenter(canvas);

        Sprite background = loadSprite("estelarMorado.jpg");
        background.position.set(width / 2, height / 2);

        Sprite spaceship = loadSprite("playerShip1_blue.png");
        spaceship.position.set(width / 2, height / 1.25);


        ArrayList<String> keyPressed = new ArrayList<>();
        keyListeners(mainScene, keyPressed);
        ArrayList<String> onlyOneKey = new ArrayList<>();


        ArrayList<Sprite> asteroidList = loadAsteroids();

        ArrayList<Sprite> laserList = new ArrayList<>();

        iniciarMusica();
        AudioClip explosion = new AudioClip(getClass().getResource("/sounds/explosion.mp3").toString());
        AudioClip laserSound = new AudioClip(getClass().getResource("/sounds/laserSound.mp3").toString());
        laserSound.setVolume(0.15);

        Timeline gameLoop = gameLoop(context, keyPressed, spaceship, laserList, onlyOneKey, laserSound, explosion, asteroidList, background);
        gameLoop.setCycleCount(Animation.INDEFINITE);
        gameLoop.play();

        score = 0;
        marcador(context);

    }

    public void start() {
        public void start (Stage stage){
            stage.setTitle("Láseres al vacío");
            BorderPane root = new BorderPane();
            Scene mainScene = new Scene(root);
            stage.setScene(mainScene);


            int height = 600;
            int width = 800;
            int rotationSpaceship = 270;


            String rutaImages = "file:///" + System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources"
                    + File.separator + "images" + File.separator;

            Canvas canvas = new Canvas(width, height);
            GraphicsContext context = canvas.getGraphicsContext2D();
            root.setCenter(canvas);


            stage.show();
        }
    }

    private Sprite loadSprite(String nombreImagen) {
        String rutaImages = "file:///" + System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources"
                + File.separator + "images" + File.separator;
        Sprite sprite = new Sprite(rutaImages + nombreImagen);
        return sprite;
    }

    private ArrayList<Sprite> loadAsteroids() {
        int asteroidCount = 6;
        ArrayList<Sprite> asteroidList = new ArrayList<>();
        for (int i = 0; i < asteroidCount; i++) {
            Sprite asteroid = loadSprite("meteorMed.png");
            //TODO: Se puede hacer que vayan generando de varios tamaños con un random y añadiendo más images de meteoros
            //TODO: Quizá, que se generen más a lo lago del tiempo (con un botón, al pulsar una tecla o simplemente por tiempo)
            double x = Math.random() * 500 + 300;
            double y = Math.random() * 400 + 100;
            asteroid.position.set(x, y);
            double angle = Math.random() * 360;
            asteroid.speed.setLength(4);
            asteroid.speed.setAngle(angle);
            asteroidList.add(asteroid);
        }
        return asteroidList;
    }

    private void shootLaser(Sprite spaceship, List<Sprite> laserList) {
        Sprite laser = loadSprite("laserBlue.png");
        laser.position.set(spaceship.position.x, spaceship.position.y);
        laser.speed.setLength(3.5);
        laser.speed.setAngle(spaceship.rotation + rotationSpaceship);
        laser.rotation = spaceship.rotation;
        laserList.add(laser);
    }

    private void controles(ArrayList<String> keyPressed, Sprite spaceship, ArrayList<Sprite> laserList, ArrayList<String> onlyOneKey, AudioClip laserSound) {
        if (keyPressed.contains("LEFT") || keyPressed.contains("A")) {
            spaceship.rotation -= 3;
        }
        if (keyPressed.contains("RIGHT") || keyPressed.contains("D")) {
            spaceship.rotation += 3;
        }
        if (keyPressed.contains("UP") || keyPressed.contains("W")) {
            spaceship.speed.setLength(3.5);
            spaceship.speed.setAngle(spaceship.rotation + rotationSpaceship);
        } else if (keyPressed.contains("DOWN") || keyPressed.contains("S")) {
            spaceship.speed.setLength(2);
            spaceship.speed.setAngle(spaceship.rotation + rotationSpaceship + 180);
        } else {
            spaceship.speed.setLength(0);
        }

        if (keyPressed.contains("SPACE")) {
            shootLaser(spaceship, laserList);
            laserSound.play();

        }
        onlyOneKey.clear();
    }

    private Timeline gameLoop(GraphicsContext context, ArrayList<String> keyPressed, Sprite spaceship, ArrayList<Sprite> laserList, ArrayList<String> onlyOneKey, AudioClip laserSound, AudioClip explosion, ArrayList<Sprite> asteroidList, Sprite background) {
        var gameLoop = new Timeline(new KeyFrame(Duration.millis(17), a -> {

            controles(keyPressed, spaceship, laserList, onlyOneKey, laserSound);

            spaceship.update();

            for (Sprite asteroid : asteroidList) {
                asteroid.rotation -= 2;
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
                        laserList.remove(laserList.get(laserNum));
                        asteroidList.remove(asteroidNum);
                        explosion.play();
                        score += 100;
                    }
                }
            }


            spaceship.evitarSalida(width, height);
            background.render(context);
            spaceship.render(context);
            for (Sprite laser : laserList) {

                laser.getLimites();
                laser.render(context);
            }
            for (Sprite asteroid : asteroidList) {
                asteroid.getLimites();
                asteroid.render(context);
            }

        }));
        return gameLoop;
    }

    private void iniciarMusica() {
        Media cancion = new Media(getClass().getResource("/sounds/spaceOdyssey.mp3").toString());
        MediaPlayer cancionmp = new MediaPlayer(cancion);
        cancionmp.play();
    }

    private void marcador(GraphicsContext context) {
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

}
