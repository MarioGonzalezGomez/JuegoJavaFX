package com.example.juegoid;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;

/**
 * Esta es la clase que actualmente ejecuta el juego. TE dejo también una carpeta "noUtilizado" donde incluye
 * el primer prototipo de juego, con un cuadrado que se adapta al tamaño de la ventana, cuatro paredes, movimiento...
 * en la clase "Juego" y "JuegoController". Por otro lado, "Game" y "GameMain" son una reestructuración de este mismo
 * proyecto pero ordenado en métodos y con un "start" mucho más legible y con pocas líneas de código
 */
public class App extends Application {
    int score = 0;

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

        //Esta ruta de resources nos ayudará a acortar la llamada de imágenes al crear diferentes objetos en pantalla
        String rutaImages = "file:///" + System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources"
                + File.separator + "images" + File.separator;

        //La pantalla principal de nuestro juego
        Canvas canvas = new Canvas(width, height);
        GraphicsContext context = canvas.getGraphicsContext2D();
        root.setCenter(canvas);

        ArrayList<String> keyPressed = new ArrayList<>();

        ArrayList<String> onlyOneKey = new ArrayList<>();

        //Un controlador de movimiento y disparo: si se mantiene una tecla, el movimiento será regular y no a trazos,
        //ya que mientras contenga ese elemento la lista, la nave mantiene su velocidad. Al dejar de pulsar, esta
        //desaparece de la lista, y por tanto, haremos que su velocidad pase a 0
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

        //En estas líneas colocamos el fondo y al personaje principal, la nave spacechip
        //Tengo más modelos de nave, por lo que se podría en un futuro crear una pantalla de inicio donde
        //el usuario selecciona una, y solo se necesitaría cargar que spaceship tiene esa imagen
        Sprite background = new Sprite(rutaImages + "estelarMorado.jpg");
        background.position.set(width / 2, height / 2);

        Sprite spaceship = new Sprite(rutaImages + "playerShip1_blue.png");
        spaceship.position.set(width / 2, height / 1.25);

        //Estas listas nos ayudarán a mantener elementos y quitarlos de pantalla cuando colisionen
        ArrayList<Sprite> laserList = new ArrayList<>();
        ArrayList<Sprite> asteroidList = new ArrayList<>();

        //Una mejora posible sería hacer que las medidas del random tuviesen como referencia variables con el tamaño de la pantalla
        //haciendo más fácil un futuro cambio de resolución
        //Este método genera los meteoritos, por ahora, solo de un tipo, aunque sería muy sencillo añadir otros tipos. Solo hace falta
        //crear un random y dependiendo del valor, generar un sprite con la imagen de cada tipo de meteorito, ya que en el objeto Sprite
        //se especifica su tamaño = al tamaño de su imagen
        for (int i = 0; i < asteroidCount; i++) {
            Sprite asteroid = new Sprite(rutaImages + "meteorMed.png");
            double x = Math.random() * 500 + 300;
            double y = Math.random() * 400 + 100;
            asteroid.position.set(x, y);
            double angle = Math.random() * 360;
            asteroid.speed.setLength(4);
            asteroid.speed.setAngle(angle);
            asteroidList.add(asteroid);
        }

        //Aquí preparo con la clase Media y AudioCLip la canción y los efectos que sonarán durante el juego
        Media cancion = new Media(getClass().getResource("/sounds/spaceOdyssey.mp3").toString());
        MediaPlayer cancionmp = new MediaPlayer(cancion);
        AudioClip explosion = new AudioClip(getClass().getResource("/sounds/explosion.mp3").toString());
        AudioClip laserSound = new AudioClip(getClass().getResource("/sounds/laserSound.mp3").toString());
        laserSound.setVolume(0.15);


        var gameLoop = new Timeline(new KeyFrame(Duration.millis(17), a -> {
            //La canción suena desde el inicio y se mantendrá durante el nivel
            cancionmp.play();

            //Los controles izquierda y derecha cambian la rotación del objeto.
            //Posteriormente con up podremos avanzar, añadiendo velocidad al objeto spaceship
            //Si pulsamos down, este irá marcha atrás, pero a una menor velocidad
            //En cualquier caso, si se deja de pulsar, la nave se detendrá, es decir, su velocidad pasa a ser 0
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
            //Con space disparamos, generando objetos láser cuya imagen estará girada dependiendo de la actual
            //rotación de la nave, e irán en la dirección a la que actualmente apunta.
            if (keyPressed.contains("SPACE")) {
                Sprite laser = new Sprite(rutaImages + "laserBlue.png");
                laser.position.set(spaceship.position.x, spaceship.position.y);
                laser.speed.setLength(3.5);
                laser.speed.setAngle(spaceship.rotation + rotationSpaceship);
                laser.rotation = spaceship.rotation;
                laserList.add(laser);
                laserSound.play();
            }
            onlyOneKey.clear();

            //Esta serie de updates actualizarán la posición de los elementos cada frame, además, como añadido,
            //harán que los asteroides giren continuamente
            spaceship.update();
            for (Sprite asteroid : asteroidList) {
                asteroid.rotation -= 2;
                asteroid.update();
                asteroid.evitarSalida(width, height);
            }
            for (Sprite laser : laserList) {
                laser.update();
            }

            //Manejamos la colisión de los láseres y los asteroides. Cuando chocan, sonará una explosión,
            //se sumarán 100 puntos al marcador y ambos elementos desaparecerán. Como la cadencia de disparo
            //es muy alta, cuesta percibirlo, pero desaparece tanto asteroide como el láser que lo impacta
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

            //Evitamos la salida de la nave, haciéndola aparecer por el otro lado cada vez que supera unos límites
            //en este caso, el width y el height de la pantalla
            spaceship.evitarSalida(width, height);

            //Con render mostramos al jugador el estado actual de los elementos a cada frame
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

            //Para dibujar el marcador en la pantalla. Le damos una fuente, un tamaño, una localización, un color...
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

        //El gameloop se hará continuamente, por eso necesitamos que se haga de forma indefinida
        //Podemos crear vías de escape con if por ejemplo, al alcanzar una pountuación máxima,
        //al no quedar más asteroides, al pasar un tiempo en una pantalla determinada...
        gameLoop.setCycleCount(Animation.INDEFINITE);
        gameLoop.play();

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}