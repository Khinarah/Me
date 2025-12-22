import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

class Affichage {
    JFrame area=new JFrame("Snake - Blockade"); //Fenêtre pour afficher le jeu
    JPanel test=new JPanel(); //Interface du jeu

    public void setArene(){ //Fonction pour paramétrer l'arène
        area.add(test);
        area.setSize(800, 500);
        test.setBackground(Color.BLACK);
        area.setVisible(true);
    }

    public static void main(String[] args) {
        Affichage a1=new Affichage();
        a1.setArene();
        Graphics g=new Graphics(); 
        System.out.println(g);
    }
    
    
}
