package com.example.pong;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MenuVista extends StackPane {

    private VBox textos;
    private Label start,fullSize,exit;
    private MenuControlador controller;

    MenuVista(){init();}

    private void init(){
        textos = new VBox();
        start = new Label("START");
        fullSize = new Label("FULL SIZE: FALSE");
        exit = new Label("EXIT");
        this.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        this.setMinSize(0,0);

        textos.getChildren().addAll(start,fullSize,exit);
        this.getChildren().add(textos);
        this.setAlignment(textos, Pos.CENTER);

        textos.translateXProperty().bind(this.widthProperty().divide(2.5));
        textos.translateYProperty().bind(this.heightProperty().divide(2.5));

        controller = new MenuControlador(textos,start,fullSize,exit);

    }
}
