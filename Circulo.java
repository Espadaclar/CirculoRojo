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

//PARA CREAR EL CRON”METRO.
import java.util.Timer;
import java.util.TimerTask;

import java.util.ArrayList;
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
    //velocidad del c√≠rculo.
    private int velocidadX = 1;
    private int velocidadY = 1;
    //velocidad de la barra.
    private int velocidadEnBarra;

    //para crear un contador de tiempo
    private int tiempoEnSegundos ;

    public static void main(String[] args){
        //Esto se utiliza para ejecutar la aplicaci√≥n 
        //es como el new Contructor()
        launch(args);
    }

    public void start(Stage ventana){//par√°metro que va ha ser la ventan de la aplicaci√≥n
         Random aleatorio = new Random();//-
        ArrayList<Rectangle> rectangulos = new ArrayList<>();
        
        int ANCHO_ESCENA = 700;
        int ALTO_ESCENA = 600;
        Group root = new Group(); //contenedor que colocamos dentro de la escena.

        Scene escena = new Scene(root, ANCHO_ESCENA, ALTO_ESCENA, Color.YELLOW);//Se crea la escena con el contenedor que contiene los objetos.
        ventana.setScene(escena);//pasamos al par√°metro ventana el objeto escena.

        /////////////// CREAR CONTADOR DE TIEMPO
        Label tiempoPasado = new Label("0");
        root.getChildren().add(tiempoPasado);
        tiempoPasado.setStyle("-fx-font-size: 3em;");
        tiempoPasado.setLayoutX(55);
        tiempoPasado.setLayoutY(15);

        //////////////////////////////para pasar coordenadas aleatorias a la situaci√≥n inicias del c√≠rculo:
        Random ale = new Random();
        int coordenadaX = ale.nextInt(ANCHO_ESCENA - 10) +10; 
        int coordenadaY = ale.nextInt(ALTO_ESCENA - 10) +10; 
        float RADIO = 20.0f;
        //Se crea el c√≠rculo
        Circle circle = new Circle();
        circle.setCenterX(coordenadaX);
        circle.setCenterY(coordenadaY);
        circle.setCenterY(250.0f);
        circle.setFill(Color.RED);
        circle.setRadius(RADIO);
        //coloca el c√≠rculo dentro del contenedor root.
        root.getChildren().add(circle);

        //se crea un rect√°ngulo
        Rectangle rectangulo = new Rectangle();
        rectangulo.setLayoutY(ALTO_ESCENA -40);
        rectangulo.setLayoutX(ANCHO_ESCENA /2);

        rectangulo.setWidth(150);
        rectangulo.setHeight(10);
        root.getChildren().add(rectangulo);
        rectangulo.setFill(Color.BLUE);
        //
        
        //////////////////////////////////////////SE CREAN VARIAS BARRITAS/////////////////SE CREAN VARIAS BARRITAS.....
        int ALTO_BARRITAS = 25;
        int EJE_Y = 50;//-------------POSICI”N ININCIAL DE LA 1∫ FILA DE BARRITAS EN EL EJE Y.
        int NUM_FILAS_EN_Y = 4;// ---ES EL N∫ DE FILAS.
        int BARRITAS_EN_Y = 7;
        int cont2 = 0;//// ------- CUENTA EL N∫ DE FILAS DE BARRITAS que se van creando EN EL EJE Y.
        //--CADA VEZ QUE SE HA CREADO UNA FILA DESPLAZA LA COORDENADA EN Y, PARA LA SIGUIENTE FILA
        if(BARRITAS_EN_Y == 0){ // -------------SE CREAN ALEATORIAMENTE.
            for(int i = 0; i < NUM_FILAS_EN_Y; i ++ ){
                int cont = 0;//--- CUANDO cont SEA == AL ANCHO DEL ESCENARIO EL BUCLE WHILE FINALIZA.
                double sumBarritas = 0;// ---- ACUMULA LA SUMA DE LA LONGITUD DE LAS BARRITAS QUE SE VAN CREANDO.
                while(cont != ANCHO_ESCENA ){
                    // Random aleatorio = new Random();//-
                    Color color = new Color(aleatorio.nextDouble(), aleatorio.nextDouble(), aleatorio.nextDouble(), aleatorio.nextDouble());

                    double barritas = aleatorio.nextInt(60) +70;//-- CADA BARRITA TIENE UNA LONGITUD ALEATORIA. 
                    Rectangle rectangulo2 = new Rectangle();
                    if(cont2 == i){//-- PASA LA COORDENDA Y DE LAS NUM_BARRITAS_EN_Y  FILAS DE BARRITAS EN EL EJE Y.
                        rectangulo2.setLayoutY(EJE_Y + (cont2 * ALTO_BARRITAS));
                    }

                    //--- CONDICI”N PARA HALLAR LA MENDIDA DE LA ⁄LTIMA BARRITA EN LOS EJES Y.
                    if(sumBarritas >= (ANCHO_ESCENA-130) ){
                        barritas = (ANCHO_ESCENA - sumBarritas);
                        cont += barritas + sumBarritas;
                    }
                    rectangulo2.setLayoutX(sumBarritas);
                    rectangulo2.setWidth(barritas);//LONGITUD ALEATORIA DE LAS BARRITAS, EXCEPTO LA DE LA ⁄LTIMA BARRITA.
                    rectangulo2.setHeight(ALTO_BARRITAS);
                    root.getChildren().add(rectangulo2);
                    rectangulo2.setStroke(Color.BLACK);
                    rectangulo2.setFill(color);
                    sumBarritas += barritas;// ---- ACUMULA LA SUMA DE LA LONGITUD DE LAS BARRITAS QUE SE VAN CREANDO.

                    rectangulos.add(rectangulo2);
                }

                cont2 ++; //cont2 * ALTO_BARRITAS --> proporciona la coordenada de Y para cada fila.
            }
        }
        else{
            int val = 0;
            while(val < BARRITAS_EN_Y){
                int largoR = aleatorio.nextInt(60) +70; //largo de la barra; aleatorio.
                int coorX = aleatorio.nextInt(ANCHO_ESCENA - largoR) +largoR; //coordenada X de la barra, aleatoria.

                Rectangle rectan = new Rectangle();
                rectan.setLayoutX(coorX);
                rectan.setLayoutY(EJE_Y);
                rectan.setWidth(largoR);//LONGITUD ALEATORIA DE LAS BARRITAS, EXCEPTO LA DE LA ⁄LTIMA BARRITA.
                rectan.setHeight(ALTO_BARRITAS);
                root.getChildren().add(rectan);
                rectan.setStroke(Color.BLACK);
                rectan.setFill(Color.GREEN);

                rectangulos.add(rectan);
                val ++;
            }
            
        }
        /////////////////////////////////////////////////CREACI√ìN DE UN BOT√ìN
        Button boton = new Button("Stop / Move");
        boton.setDefaultButton(true);
        boton.setLayoutX(15);
        boton.setLayoutY(ALTO_ESCENA -25);
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
                        else if(circle.getBoundsInParent().getMaxY() >= (escena.getHeight()))
                        {
                            Label label1 = new Label();
                            label1.setText(" -- GANE  OVER -- ");        
                            label1.setLayoutX( (ANCHO_ESCENA /2) -90);
                            label1.setLayoutY(ALTO_ESCENA /2);
                            label1.setTextFill(Color.RED);
                            label1.setStyle("-fx-font-size: 2em;");
                            root.getChildren().add(label1);
                            velocidadY = 0;
                            velocidadX = 0;
                            escena.setFill(Color.WHITE);
                            timeline.stop();//PARA EL CRONOMETRO CUANDO TERMINA LA PARTIDA.
                        }

                        double rec_X = rectangulo.getBoundsInParent().getMaxX();
                        double rec_MMX = rectangulo.getBoundsInParent().getMinX();
                        double rec_MMY = rectangulo.getBoundsInParent().getMinY();

                        double cir_X = circle.getBoundsInParent().getMaxX() -RADIO;
                        double cir_MMX = circle.getBoundsInParent().getMinX() +RADIO;
                        double cir_MMY = circle.getBoundsInParent().getMinY(); 
                        double cir_Y = circle.getBoundsInParent().getMaxY();

                        //SI LAS VARIABLES DEL CIR, SE RELACIONAN CON LAS VARIABLES DEL RECT, EL C√?RCULO REBOTA SOBRE LA BARRA.
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

                        /// ACTUALIZA LAS ETIQUETAS DE TIEMPOPASADO
                        int minutos = tiempoEnSegundos / 60;
                        int segundos = tiempoEnSegundos % 60;
                        tiempoPasado.setText(minutos + ":" + segundos);  

                        //PARA QUE SE MUEVA LA BARRA .
                        rectangulo.setTranslateX(rectangulo.getTranslateX() + velocidadEnBarra);

                        /////para que la barra no se salga de los l√≠mites de la escena.
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

        //////////////////////  PARA ACTIVAR Y DESACTIVAR EL BOT”N CUANDO …STE EST¡ ACTIVADO.
        boton.setOnAction(event2 -> {
                if (timeline.getStatus() == Status.PAUSED){
                    timeline.play();
                }
                else{
                    timeline.pause();
                }
            });

        timeline.getKeyFrames().add(kf);
        timeline.play();
        ventana.show();

        //La clase Timer permite definir una tarea en un intervalo0o de tiempl
        //TimerTask nos pide un metodo abstracto°°°°
        TimerTask tarea = new TimerTask(){
                //creacion de una clase anonima que va a heredar de TimerTask, va ha ser hija de ella
                //en esa clase anÛnima definimos el mt de la interface
                @Override
                public void run(){
                    //esto se actualiza en el KeiFrem
                    tiempoEnSegundos++; 
                }

            };
        //ahora necesito crear un obfeto Timer 
        Timer timer = new Timer();
        timer.schedule(tarea, 0, 1000);//cada cierto tiempo sale lo que se codifiqwu
    }
}

