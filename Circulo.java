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
// import javafx.util.Duration;
// import javafx.event.EventHandler;
// import javafx.event.ActionEvent;
/**
 * Write a description of class Circulo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Circulo extends Application 
{
    private int velocidadX = 1;
    private int velocidadY = 1;
    public static void main(String[] args){
        //Esto se utiliza para ejecutar la aplicación 
        //es como el new Contructor()
        launch(args);
    }

    public void start(Stage ventana){//parámetro que va ha ser la ventan de la aplicación
        Group root = new Group(); //contenedor que colocamos dentro de la escena.

        Scene escena = new Scene(root, 450, 450, Color.BLUE);//Se crea la escena con el contenedor que contiene los objetos.
        ventana.setScene(escena);//pasamos al parámetro ventana el objeto escena.

        //////////////////////////////para pasar coordenadas aleatorias a la situación inicias del círculo:
        Random ale = new Random();
        int coordenadaX = ale.nextInt(430) +10; 
        int coordenadaY = ale.nextInt(430) +10; 

        //Se crea el círculo
        Circle circle = new Circle();
        circle.setCenterX(coordenadaX);
        circle.setCenterY(coordenadaY);
        //circle.setCenterY(250.0f);
        circle.setFill(Color.RED);
        circle.setRadius(20.0f);
        //coloca el círculo dentro del contenedor root.
        root.getChildren().add(circle);

        /////////////////////////////////////////////////CREACIÓN DE UN BOTÓN
        Button boton = new Button("Stop / Move");
        boton.setDefaultButton(true);
        boton.setLayoutX(15);
        boton.setLayoutY(15);
        boton.setPrefSize(100, 18);
        root.getChildren().add(boton);

        //////////////////////////////PARA DESPLAZAR EL CÍRCULO CUANDO ES ACTIVA EL BOTÓN. 
        boton.setOnAction(event -> {

                Timeline timeline = new Timeline();
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.setAutoReverse(true);
                //define un valor de movimiento en los ejes x / y.
                KeyFrame kf = new KeyFrame(Duration.seconds(.001), new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent event) {

                                circle.setTranslateX(circle.getTranslateX() + velocidadX);
                                circle.setTranslateY(circle.getTranslateX() + velocidadY);
                                if(circle.getBoundsInParent().getMinX() <= 10 || 
                                circle.getBoundsInParent().getMaxX() >= (escena.getWidth() - 10) ){
                                    velocidadX = -velocidadX;
                                }
                                if(circle.getBoundsInParent().getMinY() <=  50 || 
                                circle.getBoundsInParent().getMaxY() >= (escena.getHeight() - 50) ){
                                    velocidadY = -velocidadY;
                                }
                                
                                
                            }
                        });
                timeline.getKeyFrames().add(kf);

                timeline.play();

                //////////////////////  PARA ACTIVAR Y DESACTIVAR EL BOTÓN CUANDO ÉSTE ESTÁ ACTIVADO.
                boton.setOnAction(event2 -> {
                        if (timeline.getStatus() == Status.PAUSED){
                            timeline.play();
                        }
                        else{
                            timeline.pause();
                        }
                    });

            } );
        /////////////////////////////////////
        ventana.show();
    }

}

