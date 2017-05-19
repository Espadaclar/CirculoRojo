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
// import javafx.animation.TranslateTransition;
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

    public static void main(String[] args){
        //Esto se utiliza para ejecutar la aplicación 
        //es como el new Contructor()
        launch(args);
    }

    public void start(Stage ventana){//parámetro que va ha ser la ventan de la aplicación
        Group root = new Group(); //contenedor que colocamos dentro de la escena.
        Scene escena = new Scene(root, 500, 500, Color.BLUE);//Se crea la escena con el contenedor que contiene los objetos.
        ventana.setScene(escena);//pasamos al parámetro ventana el objeto escena.

        //Se crea el círculo
        Circle circle = new Circle();
        circle.setCenterX(250.0f);
        circle.setCenterY(250.0f);
        circle.setFill(Color.RED);
        circle.setRadius(20.0f);
        //coloca el círculo dentro del contenedor root.
        root.getChildren().add(circle);

        /////////////////////////////////////////////////CREACIÓN DE UN BOTÓN
        Button boton = new Button("Stop");
        boton.setDefaultButton(true);
        boton.setLayoutX(15);
        boton.setLayoutY(15);
        boton.setPrefSize(80, 18);
        root.getChildren().add(boton);

        //////////////////////////////PARA DESPLAZAR EL CÍRCULO CUANDO ES ACTIVA EL BOTÓN. 
        boton.setOnAction(event -> {

                Timeline timeline = new Timeline();
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.setAutoReverse(true);
                KeyFrame kf = new KeyFrame(Duration.seconds(.01), new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent event) {
                                circle.setTranslateX(circle.getTranslateX() + 1);
                                circle.setTranslateY(circle.getTranslateX() + 1);
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

