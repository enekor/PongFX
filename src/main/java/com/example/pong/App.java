package com.example.pong;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    //private static Stage stage;

    //public Stage getStage(){return this.stage;}

    @Override
    public void start(Stage primaryStage) throws Exception {
        //primaryStage.setMaximized(true);
        Scene scene  = new Scene(new MenuVista(),500,500);
        primaryStage.setTitle("pong");
        primaryStage.setScene(scene);
        //this.stage = primaryStage;
        primaryStage.show();
    }

    public static void main(String[] args) {launch();}
}
