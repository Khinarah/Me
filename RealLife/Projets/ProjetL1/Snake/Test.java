import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Color;

class Test {
    JFrame frameTest=new JFrame("Snake-Blockade");
    JPanel panelTest=new JPanel();
    JPanel gameover=new JPanel();
    JButton buttonTest=new JButton("Jouer");
    JButton options=new JButton("Options");
    JButton buttonTest2=new JButton("Quitter");


    public void buttonColor(){ //Fonction pour l'apparence des boutons du menu
        buttonTest.setBackground(Color.BLACK);
        buttonTest.setForeground(Color.GREEN);
        buttonTest2.setBackground(Color.BLACK);
        buttonTest2.setForeground(Color.GREEN);
        options.setBackground(Color.BLACK);
        options.setForeground(Color.GREEN);
    }

    public void ajoutsButton(){ //Fonction pour ajouter des boutons au menu
        panelTest.add(buttonTest);
        panelTest.add(options);
        panelTest.add(buttonTest2);
    }

    public void setPanel(){ //Fonction pour paramétrer l'interface (sans les bordures de la fenêtre)
        panelTest.setSize(800,500);
        panelTest.setBackground(Color.BLACK);
    }

    public void setFrame(){ //Fonction pour paramétrer la fenêtre
        frameTest.setSize(800,500);
        frameTest.getRootPane().setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.GREEN));
        frameTest.add(panelTest);
        frameTest.add(gameover);
        frameTest.setVisible(true);
    }
    
    public Test(){
        ajoutsButton();
        setPanel();
        setFrame();
        buttonColor();
    }

    public static void main(String[] args) {
        Test t1=new Test();
    }
}
