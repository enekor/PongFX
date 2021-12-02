package com.example.pong;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class MenuControlador {

    private VBox textos;
    private Label start,fullSize,exit;
    private boolean full = false;

    MenuControlador(VBox textos, Label start, Label fullSize, Label exit) {
        this.textos=textos;
        this.start=start;
        this.fullSize=fullSize;
        this.exit=exit;

        initMenu();
    }

    private void initMenu(){
        start.setOnMouseClicked(x->{
            Stage juego = new Stage();
            try {
                juego.setMaximized(full);
                juego.setScene(new Scene(new PongVista(),500,500));
                juego.setTitle("PONG FX");
                juego.show();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
        });

        fullSize.setOnMouseClicked(x->{
            if(full){
                full=false;
                fullSize.setText("FULL SIZE: FALSE");
            }
            else{
                full=true;
                fullSize.setText("FULL SIZE: TRUE");
            }
        });

        exit.setOnMouseClicked(x->System.exit(0));
    }
}
