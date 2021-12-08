package com.example.pong;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javax.sound.sampled.*;

import java.io.File;
import java.io.IOException;

public class PongControlador {

    private Rectangle raqueta1, raqueta2, techo, suelo;
    private Circle pelota;
    private StackPane pista;
    private Timeline animacion;
    private Timeline colorPelota;
    private Rectangle paredI;
    private Rectangle paredD;
    private Label puntosI;
    private Label puntosD;

    private double velocidadPelota = 1;
    private double velocidadPala = 6;
    private boolean arriba = false;
    private boolean abajo = true;
    private boolean izquierda = false;
    private boolean derecha = true;
    private boolean funcionando = true;

    private int puntuacionI=0;
    private int puntuacionD=0;

    private String pointSound = System.getProperty("user.dir")+ File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"audio"+File.separator+"point.wav";
    private String colisionSound = System.getProperty("user.dir")+ File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"audio"+File.separator+"colision.wav";
    //private String bgm = System.getProperty("user.dir")+ File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"audio"+File.separator+"bgm.wav";
    private String bgm = System.getProperty("user.dir")+ File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"audio"+File.separator+"bgm2.wav";
    private String pause = System.getProperty("user.dir")+ File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"audio"+File.separator+"pause.wav";
    private String unpause = System.getProperty("user.dir")+ File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"audio"+File.separator+"unpause.wav";
    private String restart = System.getProperty("user.dir")+ File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"audio"+File.separator+"restart.wav";

    private AudioInputStream bgMusic;
    private Clip music;

    public PongControlador(Rectangle raqueta1, Rectangle raqueta2, Circle pelota, StackPane pista, Rectangle techo, Rectangle suelo, Rectangle paredI, Rectangle paredD, Label puntosI, Label puntosD) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.raqueta1=raqueta1;
        this.raqueta2=raqueta2;
        this.pelota=pelota;
        this.pista=pista;
        this.techo=techo;
        this.suelo=suelo;
        this.paredI=paredI;
        this.paredD=paredD;
        this.puntosI=puntosI;;
        this.puntosD=puntosD;;


        initGame();
    }

    /**
     * loop
     */
    private void initGame() throws UnsupportedAudioFileException, IOException, LineUnavailableException {


        initControls();

        animacion = new Timeline(new KeyFrame(Duration.millis(17),t->{
            try {
                detectarColision();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
            moverPelota();
        }));
        animacion.setCycleCount(Animation.INDEFINITE);

        colorPelota= new Timeline(new KeyFrame(Duration.millis(100),t->{
                cambiarColor();
        }));
        colorPelota.setCycleCount(Animation.INDEFINITE);

        colorPelota.play();
    }

    private void initControls(){
        pista.setFocusTraversable(true);

        pista.setOnKeyPressed(v->{
            if(v.getCode().equals(KeyCode.UP)){
                raqueta2.setTranslateY(raqueta2.getTranslateY()-velocidadPala);
                animacion.play();
            }
            if(v.getCode().equals(KeyCode.DOWN)){
                raqueta2.setTranslateY(raqueta2.getTranslateY()+velocidadPala);
                animacion.play();
            }
            if(v.getCode().equals(KeyCode.W)){
                raqueta1.setTranslateY(raqueta1.getTranslateY()-velocidadPala);
                animacion.play();
            }
            if(v.getCode().equals(KeyCode.S)){
                raqueta1.setTranslateY(raqueta1.getTranslateY()+velocidadPala);
                animacion.play();
            }
            if(v.getCode().equals(KeyCode.SPACE)){
                try {
                    bgMusic = AudioSystem.getAudioInputStream(new File(bgm).getAbsoluteFile());
                    music = AudioSystem.getClip();
                    music.open(bgMusic);
                    music.start();
                    animacion.play();
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                    e.printStackTrace();
                }
            }
            if(v.getCode().equals(KeyCode.P)){
                if (funcionando) {
                    try {
                        pauseSound();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                        e.printStackTrace();
                    }
                    music.stop();
                    animacion.stop();
                    funcionando=false;
                }
                else{
                    try {
                        unpauseSound();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                        e.printStackTrace();
                    }
                    music.start();
                    animacion.play();
                    funcionando=true;
                }
            }
            if(v.getCode().equals(KeyCode.R)){
                try {
                    restartSound();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    e.printStackTrace();
                }
                puntosD.setText("0 puntos");
                puntosI.setText("0 puntos");
                puntuacionD=0;
                puntuacionI=0;
                pelota.setTranslateY(0);
                pelota.setTranslateX(0);
                music.close();
                raqueta1.setTranslateX(0);
                raqueta1.setTranslateY(0);
                raqueta2.setTranslateX(0);
                raqueta2.setTranslateY(0);
                animacion.stop();

            }
        });
    }

    private void detectarColision() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if(raqueta1.getBoundsInParent().intersects(techo.getBoundsInParent())){
            //System.out.println("raqueta 1 toco pared");
            raqueta1.setTranslateY(raqueta1.getTranslateY()+4);
        }
        if(raqueta1.getBoundsInParent().intersects(suelo.getBoundsInParent())){
            //System.out.println("raqueta 1 toco suelo");
            raqueta1.setTranslateY(raqueta1.getTranslateY()-4);
        }
        if(raqueta2.getBoundsInParent().intersects(techo.getBoundsInParent())){
            //System.out.println("raqueta 2 toco pared");
            raqueta2.setTranslateY(raqueta2.getTranslateY()+4);
        }
        if(raqueta2.getBoundsInParent().intersects(suelo.getBoundsInParent())){
            //System.out.println("raqueta 2 toco suelo");
            raqueta2.setTranslateY(raqueta2.getTranslateY()-4);
        }
        if(pelota.getBoundsInParent().intersects(suelo.getBoundsInParent())){
            colisionSound();
            arriba = true;
            abajo = false;
        }
        if(pelota.getBoundsInParent().intersects(techo.getBoundsInParent())){
            colisionSound();
            arriba = false;
            abajo = true;
        }
        if(pelota.getBoundsInParent().intersects(raqueta1.getBoundsInParent())){
            colisionSound();
            derecha = true;
            izquierda = false;
            velocidadPelota+=0.5;
            if((int)(Math.random()*10)+1>5){
                arriba=true;
                abajo=false;
            }else{
                arriba=false;
                abajo=true;
            }
        }
        if(pelota.getBoundsInParent().intersects(raqueta2.getBoundsInParent())){
            colisionSound();
            derecha = false;
            izquierda = true;
            velocidadPelota+=0.5;
            if((int)(Math.random()*2)+1==2){
                arriba=true;
                abajo=false;
            }else{
                arriba=false;
                abajo=true;
            }
        }
        if(pelota.getBoundsInParent().intersects(paredI.getBoundsInParent())){

            sumaPuntuaciones('I');
            pointSound();
            velocidadPelota=1;
            pelota.setTranslateX(0);
            pelota.setTranslateY(0);
            animacion.stop();
        }
        if(pelota.getBoundsInParent().intersects(paredD.getBoundsInParent())){
            sumaPuntuaciones('D');
            pointSound();
            velocidadPelota=1;
            pelota.setTranslateX(0);
            pelota.setTranslateY(0);
            animacion.stop();
        }
    }

    private void moverPelota(){
        if(abajo && derecha) {
            pelota.setTranslateX(pelota.getTranslateX() + velocidadPelota);
            pelota.setTranslateY(pelota.getTranslateY() + velocidadPelota);
        }
        else if(abajo && izquierda){
            pelota.setTranslateX(pelota.getTranslateX()-velocidadPelota);
            pelota.setTranslateY(pelota.getTranslateY() + velocidadPelota);
        }
        else if (arriba && derecha){
            pelota.setTranslateX(pelota.getTranslateX() + velocidadPelota);
            pelota.setTranslateY(pelota.getTranslateY() - velocidadPelota);
        }
        else if (arriba && izquierda){
            pelota.setTranslateX(pelota.getTranslateX() - velocidadPelota);
            pelota.setTranslateY(pelota.getTranslateY() - velocidadPelota);
        }
    }

    private void sumaPuntuaciones(char jugador){
        if(jugador=='I'){
            puntuacionI++;
            puntosI.setText(puntuacionI+" puntos");
        }
        else{
            puntuacionD++;
            puntosD.setText(puntuacionD+" puntos");
        }
    }

    private void pointSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(pointSound).getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }

    private void colisionSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(colisionSound).getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }

    private void pauseSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(pause).getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }

    private void unpauseSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(unpause).getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }

    private void restartSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(restart).getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }

    private void cambiarColor(){

        pelota.setFill(Color.color(Math.random(),Math.random(),Math.random()));
    }
}
