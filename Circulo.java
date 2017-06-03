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
import javafx.scene.shape.Shape;
/**
 * 
 */
public class Circulo extends Application 
{
    private Circle cicle;
    private float RADIO = 10.0f;
    private Rectangle rectangulo;
    //velocidad del c√≠rculo.
    private int velocidadX = 1;
    private int velocidadY = 1;
    //velocidad de la barra.
    private int velocidadEnBarra;

    //Contador de tiempo
    private int tiempoEnSegundos ;
    private int eliminados = 0;

    //PARA ALTERAR EL N∫ Y LA FORMA DE MOSTRARSE LAS BARRITAS EN LA PANTALLA.... 
    private int BARRITAS_ALEATORIAS_EN_Y = 40;//SI ES IGUAL A 0, NO APARECEN DE FORMA ALEATORIA Y 'NUM_FILAS_EN_Y y EJE_Y' SE ANULA.
    private int ALTO_BARRITAS = 20;
    private int EJE_Y = 50;//-POSICI”N ININCIAL DE LA 1∫ FILA DE BARRITAS EN EL EJE Y.
    private int NUM_FILAS_EN_Y = 14;// ---ES EL N∫ DE FILAS QUE APARECERAN SI 'BARRITAS_ALEATORIAS_EN_Y' ES 0.
    private int LARGO_RAQUETA = 70; // BARRA PARA TRATAR DE PARAR LA BOLA.
    
    public static void main(String[] args){
        //Esto se utiliza para ejecutar la aplicaci√≥n 
        //es como el new Contructor()
        launch(args);
    }

    public void start(Stage ventana){//par√°metro que va ha ser la ventan de la aplicaci√≥n
        Random aleatorio = new Random();//-
        ArrayList<Rectangle> rectangulos = new ArrayList<>();

        int ANCHO_ESCENA = 600;
        int ALTO_ESCENA = 700;
        Group root = new Group(); //contenedor que colocamos dentro de la escena.
        
        //Se crea la escena con el contenedor que contiene los objetos.
        Scene escena = new Scene(root, ANCHO_ESCENA, ALTO_ESCENA, Color.WHITE);
        ventana.setScene(escena);//pasamos al par√°metro ventana el objeto escena.

        // CONTADOR DE TIEMPO
        Label tiempoPasado = new Label("0");
        root.getChildren().add(tiempoPasado);
        tiempoPasado.setStyle("-fx-font-size: 1em;");
        tiempoPasado.setLayoutX(55);
        tiempoPasado.setLayoutY(15);

        //para pasar coordenadas aleatorias a la situacion inicias del circulo:
        Random ale = new Random();
        int coordenadaX = ale.nextInt(ANCHO_ESCENA - 10) +10; 
        int coordenadaY = ale.nextInt(ALTO_ESCENA - 10) +10; 

        //RECTANGULO RAQUETA PARA IR AL ENCUENTRO DE LA BOLA.
        Rectangle rectangulo = new Rectangle();
        rectangulo.setLayoutY(ALTO_ESCENA - 35);
        rectangulo.setLayoutX(5);
        rectangulo.setWidth(LARGO_RAQUETA);
        rectangulo.setHeight(10);
        root.getChildren().add(rectangulo);
        rectangulo.setFill(Color.BLUE);

        // MueSTRA EL N∫ DE  BARRITAS QUE SE VAN ELIMINANDO,
        Label barritasEliminadas = new Label();
        barritasEliminadas.setTranslateX(150);
        root.getChildren().add(barritasEliminadas);
        barritasEliminadas.setStyle("-fx-font-size: 1em;");
        barritasEliminadas.setLayoutY(10);

        if(BARRITAS_ALEATORIAS_EN_Y == 0){
            for(int i = 0; i < NUM_FILAS_EN_Y; i ++ ){
                int sumaLongitudBarritas = 0;//--- CUANDO sumaLongitudBarritas SEA == AL ANCHO DEL ESCENARIO EL BUCLE WHILE FINALIZA.
                double sumBarritas = 0;// ---- ACUMULA LA SUMA DE LA LONGITUD DE LAS BARRITAS QUE SE VAN CREANDO.
                int cuentaFilasEnY = 0;//// ------- CUENTA EL N∫ DE FILAS DE BARRITAS que se van creando EN EL EJE Y.
                                    //--CADA VEZ QUE SE HA CREADO UNA FILA DESPLAZA LA COORDENADA EN Y, PARA LA SIGUIENTE FILA
                while(sumaLongitudBarritas != ANCHO_ESCENA ){
                    Color color = new Color(aleatorio.nextDouble(), aleatorio.nextDouble(), aleatorio.nextDouble(), aleatorio.nextDouble());
                    
                    double barritas = aleatorio.nextInt(60) +70;//-- CADA BARRITA TIENE UNA LONGITUD ALEATORIA. 
                    Rectangle rectangulo2 = new Rectangle();
                    if(cuentaFilasEnY == i){//-- PASA LA COORDENDA Y DE LAS NUM_BARRITAS_EN_Y  FILAS DE BARRITAS EN EL EJE Y.
                        rectangulo2.setLayoutY(EJE_Y + (cuentaFilasEnY * ALTO_BARRITAS));
                    }

                    //--- CONDICI”N PARA HALLAR LA MENDIDA DE LA ⁄LTIMA BARRITA EN LOS EJES Y.
                    if(sumBarritas >= (ANCHO_ESCENA-130) ){
                        barritas = (ANCHO_ESCENA - sumBarritas);
                        sumaLongitudBarritas += barritas + sumBarritas;
                    }
                    rectangulo2.setLayoutX(sumBarritas);
                    rectangulo2.setWidth(barritas);//LONGITUD ALEATORIA DE LAS BARRITAS, EXCEPTO LA DE LA ⁄LTIMA BARRITA.
                    rectangulo2.setHeight(ALTO_BARRITAS);
                    rectangulo2.setStroke(Color.BLACK);
                    rectangulo2.setFill(color);
                    sumBarritas += barritas;// ---- ACUMULA LA SUMA DE LA LONGITUD DE LAS BARRITAS QUE SE VAN CREANDO.
                    root.getChildren().add(rectangulo2);
                    rectangulos.add(rectangulo2);
                }

                cuentaFilasEnY ++; //cuentaFilasEnY * ALTO_BARRITAS --> proporciona la coordenada de Y para cada fila.
            }
        }
        else{///----------SI ELEGIMOS EL N∫ DE BARRITAS.
            int val = 0;
            while(val < BARRITAS_ALEATORIAS_EN_Y){
                Rectangle rectan = new Rectangle();
                int largoR = aleatorio.nextInt(60) +70; //largo de la barra; aleatorio.
                int coorX = aleatorio.nextInt(ANCHO_ESCENA - (largoR *2) +largoR); //coordenada X de la barra, aleatoria.
                int coorY =  aleatorio.nextInt(ALTO_ESCENA /2) +ALTO_BARRITAS;
                if(rectangulos.isEmpty()){
                    rectan.setLayoutX(coorX);
                    rectan.setLayoutY(coorY);
                    rectan.setWidth(largoR);
                    rectan.setHeight(ALTO_BARRITAS);
                    root.getChildren().add(rectan);
                    rectan.setStroke(Color.BLACK);
                    rectan.setFill(Color.GREEN);

                    rectangulos.add(rectan);
                }
                else if(!rectangulos.isEmpty()){
                    boolean barritaValida = true;
                    boolean add = false;
                    while(barritaValida == true && add == false){
                        Color color2 = new Color(aleatorio.nextDouble(), aleatorio.nextDouble(), aleatorio.nextDouble(), aleatorio.nextDouble());
                        rectan.setLayoutX(coorX);
                        rectan.setLayoutY(coorY);
                        rectan.setWidth(largoR);
                        rectan.setHeight(ALTO_BARRITAS);
                        rectan.setStroke(Color.BLACK);
                        rectan.setFill(color2);

                        for(int i = 0; i <rectangulos.size(); i ++){
                            Shape c = Shape.intersect(rectangulos.get(i), rectan);
                            if(c.getBoundsInParent().getWidth() != -1){
                                barritaValida = false;
                            }
                        }
                        if(barritaValida == true){
                            rectangulos.add(rectan);
                            root.getChildren().add(rectan); 
                            add = true;
                        }
                        largoR = aleatorio.nextInt(60) +70; //largo de la barra; aleatorio.
                        coorX = aleatorio.nextInt(ANCHO_ESCENA - largoR) +largoR; //coordenada X de la barra, aleatoria.
                        coorY = aleatorio.nextInt(ALTO_ESCENA /2) +ALTO_BARRITAS;
                    }

                }
                val ++;
            }
        }

        //Se crea el circulo
        Circle circle = new Circle();
        circle.setCenterX(coordenadaX);
        circle.setCenterY(coordenadaY);
        circle.setCenterY(250.0f);
        circle.setFill(Color.RED);
        circle.setRadius(RADIO);
        //coloca el c√≠rculo dentro del contenedor root.
        root.getChildren().add(circle);

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
        KeyFrame kf = new KeyFrame(Duration.seconds(0.001), new EventHandler<ActionEvent>() {
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
                            //escena.setFill(Color.WHITE);
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

                        /////para que la barra no se salga de los limites de la escena.
                        if(rectangulo.getBoundsInParent().getMinX() <= 0 ){
                            rectangulo.setTranslateX(rectangulo.getTranslateX() - velocidadEnBarra);
                        }
                        else if( rectangulo.getBoundsInParent().getMaxX() >= (escena.getWidth()) ){
                            rectangulo.setTranslateX(rectangulo.getTranslateX() - velocidadEnBarra);
                        }

                        //PARA ELIMINAR LAS BARRITAS AL COLISIONAR LA BOLA CON ELLAS.
                        for(int i = 0; i < rectangulos.size(); i ++ ){
                            Shape c = Shape.intersect(rectangulos.get(i), circle);

                            //COORDENADAS LATERALES DE CADA BARRITA Y DE LA BOLA.
                            double longitud_Barrita = rectangulos.get(i).getWidth();
                            double minimoDe_X_Barrita =  rectangulos.get(i).getBoundsInParent().getMinX();
                            double maximo_X_Barrita =  minimoDe_X_Barrita + longitud_Barrita;
                            double minimoDe_Y_Barrita = rectangulos.get(i).getBoundsInParent().getMinY();
                            double maximoDe_Y_Barrita = rectangulos.get(i).getBoundsInParent().getMaxY();

                            double maximoDe_X_Bolita = circle.getBoundsInParent().getMaxX() -0.5;
                            double minimoDe_X_Bolita =  circle.getBoundsInParent().getMinX() -0.5;
                            double maximoDe_Y_Bolita = circle.getBoundsInParent().getMaxY() ;
                            double minimoDe_Y_Bolita = circle.getBoundsInParent().getMinY() ;

                            if(c.getBoundsInParent().getWidth() != -1){
                                rectangulos.get(i).setFill(Color.WHITE);
                                rectangulos.get(i).setStroke(Color.WHITE);
                                rectangulos.remove(i);

                                if( (maximoDe_X_Bolita ) == minimoDe_X_Barrita && maximoDe_Y_Bolita >= minimoDe_Y_Barrita &&
                                minimoDe_Y_Bolita <= maximoDe_Y_Barrita ){
                                    velocidadX = -velocidadX;                                  
                                    eliminados ++;
                                }
                                else if( (maximoDe_X_Bolita +1) == minimoDe_X_Barrita && maximoDe_Y_Bolita >= minimoDe_Y_Barrita &&
                                minimoDe_Y_Bolita <= maximoDe_Y_Barrita ){
                                    velocidadX = -velocidadX;                                  
                                    eliminados ++;
                                }
                                else if( (minimoDe_X_Bolita ) == (minimoDe_X_Barrita + longitud_Barrita)
                                && maximoDe_Y_Bolita >= minimoDe_Y_Barrita &&
                                minimoDe_Y_Bolita <= maximoDe_Y_Barrita){
                                    velocidadX = -velocidadX;
                                    eliminados ++;
                                }
                                else{
                                    velocidadY = -velocidadY;
                                    eliminados ++;
                                }
                                barritasEliminadas.setText("Barritas eliminadas; " +eliminados);
                            }
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

