package com.example.pong;

import javafx.scene.Scene;

public class Intermedia {

    private static Intermedia ntermedia = null;
    private Intermedia(){}

    private boolean iniciado = false;

    public static Intermedia getInstance(){
        if(ntermedia==null){
            ntermedia=new Intermedia();
        }
        return ntermedia;
    }

    App app = new App();

    public void startGame(Scene escena, boolean full){

        app.startGame(full);
        iniciado = true;
    }
}
