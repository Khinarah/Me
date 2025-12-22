import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JOptionPane;

class Joueur {
    JFrame fenetreMenu=new JFrame("Snake-Blockade");
    JPanel interfaceMenu=new JPanel();
    JPanel gameover=new JPanel();
    JButton jouer=new JButton("Jouer");
    JButton options=new JButton("Options");
    JButton quitter=new JButton("Quitter");


    public void buttonColor(){ //Fonction pour l'apparence des boutons du menu
        jouer.setBackground(Color.BLACK);
        jouer.setForeground(Color.GREEN);
        quitter.setBackground(Color.BLACK);
        quitter.setForeground(Color.GREEN);
        options.setBackground(Color.BLACK);
        options.setForeground(Color.GREEN);
    }

    public void ajoutsButton(){ //Fonction pour ajouter des boutons au menu
        interfaceMenu.add(jouer);
        interfaceMenu.add(options);
        interfaceMenu.add(quitter);
    }

    public void setPanel(){ //Fonction pour paramétrer l'interfaceMenu (sans les bordures de la fenêtre)
        interfaceMenu.setSize(800,500);
        interfaceMenu.setBackground(Color.BLACK);
    }

    public void setFrame(){ //Fonction pour paramétrer la fenêtre
        fenetreMenu.setSize(800,500);
        fenetreMenu.getRootPane().setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.GREEN));
        fenetreMenu.add(interfaceMenu);
        fenetreMenu.add(gameover);
        fenetreMenu.setVisible(true);
    }
    
    public Joueur(){
        ajoutsButton();
        setPanel();
        setFrame();
        buttonColor();
    }

    public static String commandeJoueur(){ //Fonction permettant la saisie d'une direction par rapport au serpent
        String reponse=JOptionPane.showInputDialog("Entrez une direction parmi les suivantes: haut | bas | gauche | droite");
        return reponse;
    }

    public static void main(String[] args) {
        Joueur t1=new Joueur();
    }
}
