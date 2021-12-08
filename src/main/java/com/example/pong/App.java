package com.example.pong;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class App extends Application {
    private static Stage stage;

    //public Stage getStage(){return this.stage;}


    private boolean full = false;

    /*public void setScene(Scene scene) {
        this.scene = scene;
    }*/

    public void setFull(boolean full) {
        this.full = full;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Scene scene  = new Scene(new MenuVista(),500,500);
        primaryStage.setMaximized(full);

        primaryStage.setTitle("pong");
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public void startGame(boolean full)  {
        try {
            Scene juego = new Scene(new PongVista(),500,500);
            stage.setMaximized(full);
            stage.setScene(juego);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch();
    }
}
