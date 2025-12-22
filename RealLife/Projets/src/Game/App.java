package Game ;
import java.awt.Color;


import Cars.Car;

/* Fichier de test pour le jeu */

public class App {
    public static void main(String[] args) {
        GameObservable g = new GameObservable();
        new Car(g, "Orange", Color.ORANGE) ;
        new Car(g, "Blue", Color.BLUE) ;
        new Car(g, "Red", Color.RED) ;
        
        new GameView(g, 700, 400, "Car Race 2025-2026");
        
        try{
            Thread.sleep(800);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        g.startGame();
    }
}
