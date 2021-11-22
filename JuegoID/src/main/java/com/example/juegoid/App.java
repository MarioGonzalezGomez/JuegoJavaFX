package com.example.juegoid;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Scene scene = new Scene(new Juego(), 500, 700);
        stage.setTitle("Láseres al vacío");
        stage.setScene(scene);

        Sprite background = new Sprite(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "images" + File.separator + "playerShip1_blue.png");
        background.position.set(250, 700);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}