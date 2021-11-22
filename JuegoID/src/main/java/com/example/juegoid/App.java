package com.example.juegoid;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class App extends Application {
    private final int height = 600;
    private final int width = 800;

    @Override
    public void start(Stage stage) {

        stage.setTitle("Láseres al vacío");
        BorderPane root = new BorderPane();
        Scene mainScene = new Scene(root);
        stage.setScene(mainScene);

        Canvas canvas = new Canvas(width, height);
        GraphicsContext context = canvas.getGraphicsContext2D();
        root.setCenter(canvas);


        //System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources"
        //                + File.separator + "com" + File.separator + "example" + File.separator + "juegoid" + "images" + File.separator + "black.png"
        Sprite background = new Sprite("C:\\Users\\Mario\\Desktop\\juegazo\\JuegoJavaFX\\JuegoID\\src\\main\\resources\\com\\example\\juegoid\\images\\black.png");
        //Para que nuestro personaje aparezca en el centro de la pantalla
        background.position.set(width / 2, height / 2);
        background.render(context);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}