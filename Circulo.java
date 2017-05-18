import javafx.stage.Stage;             // |
import javafx.scene.Group;             // |\ Librerías necesarias
import javafx.scene.Scene;             // |/ Para el ejemplo
import javafx.application.Application; // |
import javafx.scene.control.Button;    // |

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.shape.Shape;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
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
        Scene escena = new Scene(root, 500, 500);//Se crea la escena con el contenedor que contiene los objetos.
        ventana.setScene(escena);//pasamos al parámetro ventana el objeto escena.
        
        //Se crea el círculo
        Circle circle = new Circle();
        circle.setCenterX(250.0f);
        circle.setCenterY(250.0f);
        circle.setFill(Color.RED);
        circle.setRadius(20.0f);
        
        //coloca el círculo dentro del contenedor root.
        root.getChildren().add(circle);
        

        ventana.show();
    }
}
