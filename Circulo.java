import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.paint.*;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.scene.control.Button;
import javafx.animation.Animation.Status;
import java.util.Random;
import javafx.scene.shape.Rectangle;

import javafx.scene.input.KeyCode;
import javafx.scene.control.Label;

/**
 * Write a description of class Circulo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Circulo extends Application 
{
    private Circle cicle;
    private Rectangle rectangulo;
    //velocidad del círculo.
    private int velocidadX = 1;
    private int velocidadY = 1;
    //velocidad de la barra.
    private int velocidadEnBarra;
    
    //para crear un contador de tiempo
    // private class constant and some variables
    private static final Integer STARTTIME = 15;
    private Timeline timeline;
    private Label timerLabel = new Label();
    private Integer timeSeconds = STARTTIME;

    public static void main(String[] args){
        //Esto se utiliza para ejecutar la aplicación 
        //es como el new Contructor()
        launch(args);
    }

    public void start(Stage ventana){//parámetro que va ha ser la ventan de la aplicación
        Group root = new Group(); //contenedor que colocamos dentro de la escena.

        Scene escena = new Scene(root, 600, 700, Color.YELLOW);//Se crea la escena con el contenedor que contiene los objetos.
        ventana.setScene(escena);//pasamos al parámetro ventana el objeto escena.
        
        /////////////// CREAR CONTADOR DE TIEMPO
         // Configure the Label
        timerLabel.setText(timeSeconds.toString());
        timerLabel.setTextFill(Color.RED);
        timerLabel.setStyle("-fx-font-size: 4em;");
        timerLabel.setLayoutX(55);
        timerLabel.setLayoutY(15);
        
        root.getChildren().add(timerLabel);
        //////////////////////////////para pasar coordenadas aleatorias a la situación inicias del círculo:
        Random ale = new Random();
        int coordenadaX = ale.nextInt(430) +10; 
        int coordenadaY = ale.nextInt(430) +10; 
        float RADIO = 20.0f;
        //Se crea el círculo
        Circle circle = new Circle();
        circle.setCenterX(coordenadaX);
        circle.setCenterY(coordenadaY);
        circle.setCenterY(250.0f);
        circle.setFill(Color.RED);
        circle.setRadius(RADIO);
        //coloca el círculo dentro del contenedor root.
        root.getChildren().add(circle);

        //se crea un rectángulo
        Rectangle rectangulo = new Rectangle();
        rectangulo.setLayoutY(560);
        rectangulo.setLayoutX(220);

        rectangulo.setWidth(150);
        rectangulo.setHeight(10);
        root.getChildren().add(rectangulo);
        rectangulo.setFill(Color.BLUE);
        //
        /////////////////////////////////////////////////CREACIÓN DE UN BOTÓN
        Button boton = new Button("Stop / Move");
        boton.setDefaultButton(true);
        boton.setLayoutX(15);
        boton.setLayoutY(15);
        boton.setPrefSize(100, 18);
        root.getChildren().add(boton);

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        //define un valor de movimiento en los ejes x / y.
        KeyFrame kf = new KeyFrame(Duration.seconds(.007), new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {

                        circle.setTranslateX(circle.getTranslateX() + velocidadX);
                        circle.setTranslateY(circle.getTranslateY() + velocidadY);
                        if(circle.getBoundsInParent().getMinX() <= 0 || 
                        circle.getBoundsInParent().getMaxX() >= (escena.getWidth()) ){
                            velocidadX = -velocidadX;

                        }
                        else if(circle.getBoundsInParent().getMinY() <=  0)
                        {
                            velocidadY = -velocidadY;
                        }
                        //      SI LA BOLA SE SALE POR ABAJO APARECE UN MENSAJE DE GANE OVER.
                        else if(circle.getBoundsInParent().getMinY() >= (escena.getHeight()))
                        {
                            Label label1 = new Label();
                            label1.setText(" -- GANE  OVER -- ");        
                            label1.setLayoutX(230);
                            label1.setLayoutY(350);
                            root.getChildren().add(label1);
                            velocidadY = 0;
                            velocidadX = 0;
                            escena.setFill(Color.WHITE);
                            rectangulo.setFill(Color.WHITE);
                        }

                        double rec_X = rectangulo.getBoundsInParent().getMaxX();
                        double rec_MMX = rectangulo.getBoundsInParent().getMinX();
                        double rec_MMY = rectangulo.getBoundsInParent().getMinY();

                        double cir_X = circle.getBoundsInParent().getMaxX() -RADIO;
                        double cir_MMX = circle.getBoundsInParent().getMinX() +RADIO;
                        double cir_MMY = circle.getBoundsInParent().getMinY(); 
                        double cir_Y = circle.getBoundsInParent().getMaxY();

                        //SI LAS VARIABLES DEL CIR, SE RELACIONAN CON LAS VARIABLES DEL RECT, EL C�?RCULO REBOTA SOBRE LA BARRA.
                        if( (velocidadY == 1 && velocidadX == -1) && (cir_Y == rec_MMY)  ){
                            if( cir_X < rec_X && cir_MMX > rec_MMX ){
                                velocidadY = -velocidadY;
                                velocidadX = velocidadX;
                            }
                        }
                        else {
                            if( (velocidadY == 1 && velocidadX == 1) && (cir_Y == rec_MMY) ){
                                if( cir_X < rec_X && cir_MMX> rec_MMX ){
                                    velocidadY = -velocidadY;
                                    velocidadX = velocidadX;
                                }
                            }
                        }

                        //PARA QUE SE MUEVA LA BARRA .
                        rectangulo.setTranslateX(rectangulo.getTranslateX() + velocidadEnBarra);

                        /////para que la barra no se salga de los límites de la escena.
                        if(rectangulo.getBoundsInParent().getMinX() <= 0 ){
                            rectangulo.setTranslateX(rectangulo.getTranslateX() - velocidadEnBarra);
                            //velocidadEnBarra = 0;
                        }
                        else if( rectangulo.getBoundsInParent().getMaxX() >= (escena.getWidth()) ){
                            rectangulo.setTranslateX(rectangulo.getTranslateX() - velocidadEnBarra);
                            //velocidadEnBarra = 0;
                        }
                    }
                });

        //para controlar la barra con los botones de izquierda/derecha.
        root.setOnKeyPressed(event  ->{
                if(event .getCode() == KeyCode.RIGHT){
                    velocidadEnBarra = 1;
                }
                else if(event .getCode() == KeyCode.LEFT){
                    velocidadEnBarra = -1;
                }
            });

        timeline.getKeyFrames().add(kf);
        timeline.play();
        ventana.show();
    }
}

