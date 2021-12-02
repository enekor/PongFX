package com.example.pong;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class PongVista extends BorderPane {
    private Rectangle raqueta1, raqueta2, techo, suelo,paredI,paredD;
    private Circle pelota;
    private StackPane pista;
    private PongControlador controlador;
    private Label puntosI,puntosD;

    public PongVista() throws UnsupportedAudioFileException, LineUnavailableException, IOException {init();}

    private void init() throws UnsupportedAudioFileException, LineUnavailableException, IOException {

        pista = new StackPane();

        raqueta1 = new Rectangle();
        raqueta1.heightProperty().bind(pista.heightProperty().divide(10));
        raqueta1.setWidth(12);

        raqueta2 = new Rectangle();
        raqueta2.heightProperty().bind(pista.heightProperty().divide(10));
        raqueta2.setWidth(12);

        paredI = new Rectangle();
        paredI.heightProperty().bind(pista.heightProperty());
        paredI.widthProperty().bind(pista.widthProperty().divide(1000));

        paredD = new Rectangle();
        paredD.heightProperty().bind(pista.heightProperty());
        paredD.widthProperty().bind(pista.widthProperty().divide(1000));

        techo = new Rectangle();
        techo.heightProperty().bind(pista.heightProperty().divide(20));
        techo.widthProperty().bind(pista.widthProperty());

        suelo = new Rectangle();
        suelo.heightProperty().bind(pista.heightProperty().divide(20));
        suelo.widthProperty().bind(pista.widthProperty());

        pelota = new Circle();
        pelota.setRadius(8);

        puntosI = new Label("0 puntos");
        puntosI.setTextFill(Color.WHITE);

        puntosD = new Label("0 puntos");
        puntosD.setTextFill(Color.WHITE);

        raqueta1.setFill(Color.BLACK);
        raqueta2.setFill(Color.BLACK);
        pelota.setFill(Color.WHITE);
        paredI.setFill(Color.RED);
        paredD.setFill(Color.RED);
        pista.setStyle("-fx-background-color: #61b400");

        pista.getChildren().addAll(raqueta1,raqueta2,pelota,techo,suelo,paredI,paredD,puntosI,puntosD);
        pista.setAlignment(raqueta1, Pos.CENTER_LEFT);
        pista.setAlignment(raqueta2, Pos.CENTER_RIGHT);
        pista.setAlignment(techo, Pos.TOP_CENTER);
        pista.setAlignment(suelo, Pos.BOTTOM_CENTER);
        pista.setAlignment(pelota, Pos.CENTER);
        pista.setAlignment(paredI,Pos.CENTER_LEFT);
        pista.setAlignment(paredD,Pos.CENTER_RIGHT);
        pista.setAlignment(puntosD,Pos.BOTTOM_LEFT);
        pista.setAlignment(puntosI,Pos.TOP_RIGHT);

        this.setCenter(pista);

        controlador = new PongControlador(raqueta1,raqueta2,pelota,pista,techo,suelo,paredI,paredD,puntosI,puntosD);
    }
}
