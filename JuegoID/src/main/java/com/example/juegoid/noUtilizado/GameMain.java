package com.example.juegoid.noUtilizado;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameMain {

    @Override
    public void start(Stage stage) throws IOException {
        Game vista = new Game();
        Scene scene = new Scene(vista, 320, 240);
        stage.setTitle("MiniCalculadora");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
}
