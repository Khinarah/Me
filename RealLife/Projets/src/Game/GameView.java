package Game ;
import States.* ;
import Cars.*;
import Decorator.BoosterSound;
import Decorator.DrunkPilot;
import Decorator.HybridSystem;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;


/* DESCRIPTION DU FICHIER

Contient tous les éléments (méthodes & champs) visuels relatifs à l'interface du jeu 
(voitures, circuit, point de départ/arrivée...); 

*/

public class GameView extends JComponent implements GameObserver {

    private GameObservable o;
    private JPanel places ; // Contenu du classement
    private JLabel[] rank ; // Tableau du classement
    private Map<Car, JLabel> fuels = new HashMap<>(), rounds = new HashMap<>(), msgs = new HashMap<>() ;
    private Map<Car, JButton> pauses = new HashMap<>(), slow = new HashMap<>(), speedUp = new HashMap<>() ;
    private List<JCheckBox[]> checkboxes = new ArrayList<>() ;

    /* Création des fenêtres */
    public GameView(GameObservable o, int sizeW, int sizeH, String title) {
        this.o = o;
        
        o.register(this);
        
        // Initialisation du jeu
        o.addTrack();
        o.makeConstraint();

        /* Initialisation du classement */
        rank = new JLabel[o.getCars().size()] ;
        places = new JPanel();
        places.setLayout(new BoxLayout(places, BoxLayout.Y_AXIS)); // Aligner verticalement le classement

        /* Fenêtre de jeu */
        JFrame game = new JFrame(title);
        game.setContentPane(this);
        game.setSize(sizeW, sizeH);
        game.setLocation(100, 20);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        /* Fenêtre de classement */
        JDialog leaderboard = new JDialog(game, "Classement") ;
        leaderboard.setSize(300, 400);
        leaderboard.setLocation(800, 20);
        leaderboard.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        leaderboard.add(places);
        
        /* FENÊTRE DE DÉCORATIONS */
        JDialog decoration = new JDialog(game, "Choix des décorations", true);
        decoration.setSize(400, 300);
        decoration.setLayout(new BorderLayout());

        JPanel allCarsPanel = new JPanel();
        allCarsPanel.setLayout(new BoxLayout(allCarsPanel, BoxLayout.Y_AXIS));

        for (Car c : o.getCars()) {
            // Un panel par voiture avec son nom
            JPanel carPanel = new JPanel();
            // Ajoute un cadre avec le nom de la voiture
            carPanel.setBorder(BorderFactory.createTitledBorder(c.getName()));

            // Les 3 options directes
            JCheckBox sound = new JCheckBox("Sound");
            JCheckBox drunk = new JCheckBox("Ivre");
            JCheckBox hybrid = new JCheckBox("Hybride");
            checkboxes.add(new JCheckBox[]{sound, drunk, hybrid}) ;

            carPanel.add(sound);
            carPanel.add(drunk);
            carPanel.add(hybrid);
            allCarsPanel.add(carPanel);
        }

        JButton btn = new JButton("Valider");
        setDecorator(btn);
        btn.addActionListener(e -> decoration.dispose());

        decoration.add(new JScrollPane(allCarsPanel), BorderLayout.CENTER);
        decoration.add(btn, BorderLayout.SOUTH);

       
        decoration.setVisible(true); // Bloque ici jusqu'au clic sur Valider
        
        /* Initialiser les positions dans le classement */
        for (int i = 0 ; i < o.getCars().size() ; i++){
            rank[i] = new JLabel() ;
            places.add(rank[i]) ;
        }
        
        /* Initialisation des positions des voitures */
        for (int i = 0 ; i < o.getCars().size() ; i++){
            Car c = o.getCars().get(i) ;
            JLabel fuel = new JLabel("Carburant : " + c.getFuel()) ;
            JLabel round = new JLabel("Tour : " + c.getTour()) ;
            JLabel msg = new JLabel(" ") ;
            JButton pauseButton = new JButton("Pause") ;
            JButton toSlow = new JButton("Ralentir") ;
            JButton toSpeedUp = new JButton("Accélérer") ;
            
            fuels.put(c, fuel);
            rounds.put(c, round);
            pauses.put(c, pauseButton) ;
            slow.put(c, toSlow) ;
            speedUp.put(c, toSpeedUp) ;
            msgs.put(c, msg) ;
            setDashboard(game, o, c.getName(), pauseButton, toSlow, toSpeedUp, c.getColor(), c, fuel, round, msg);
        }
        game.setVisible(true);
        leaderboard.setVisible(true);
    }
    
    @Override
    public void update() {
        if (o.getCars().isEmpty()){
            return;
        }
        // Actualisation du carburant et du tour par voiture 
        for (Car c : o.getCars()){
            fuels.get(c).setText("Carburant : " + c.getFuel());
            rounds.get(c).setText("Tour : " + c.getTour());
        }
        // Actualisation du classement
        for (int i = 0; i < o.leaderboard().size(); i++) {
            rank[i].setText((i + 1) + " : " + o.leaderboard().get(i).getName());
        }
        repaint();
    }

    /* Remplir la fenêtre */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int widthContent = getWidth() ;
        int heightContent = getHeight();
        g.setColor(Color.GREEN.darker());
        g.fillRect(0, 0, widthContent, heightContent);
        drawPath(g, widthContent, heightContent);
        drawStartFinish(g, widthContent, heightContent);    
        drawBoxConstraint(g, widthContent, heightContent);
        drawCar(g, widthContent, heightContent);
        g.setColor(Color.BLACK);
        drawGrid(g, widthContent, heightContent);
    }

    /* Grille */
    private void drawGrid(Graphics g, int widthContent, int heightContent) {
        for (int i = 1; i < 21; i++) {
            g.drawLine(widthContent * i / 20, 0, widthContent * i / 20, heightContent);
        }
        for (int j = 1; j < 11; j++) {
            g.drawLine(0, heightContent * j / 10, widthContent, heightContent * j / 10);
        }
    }

    /* Piste */
    private void drawPath(Graphics g, int widthContent, int heightContent) {
        g.setColor(Color.GRAY);
        for (int[] cell : o.getPath()) {
            int line = cell[0];
            int column = cell[1];
            g.fillRect(widthContent * column / 20, heightContent * line / 10, widthContent / 20, heightContent / 10);
        }
    }

    /* Départ et arrivée */
    private void drawStartFinish(Graphics g, int widthContent, int heightContent) {
        g.setColor(Color.YELLOW);
        int[] start = o.getPath().get(0);
        int[] end = o.getPath().get(o.getPath().size() - 1);
        g.fillRect(widthContent * start[1] / 20, heightContent * start[0] / 10, widthContent / 20, heightContent / 10);
        g.fillRect(widthContent * end[1] / 20, heightContent * end[0] / 10, widthContent / 20, heightContent / 10);
        g.setColor(Color.BLACK);
        g.drawString("S", widthContent * start[1] / 20 + widthContent / 40 - 4, heightContent * start[0] / 10 + heightContent / 20 + 5);
        g.drawString("F", widthContent * end[1] / 20 + widthContent / 40 - 4, heightContent * end[0] / 10 + heightContent / 20 + 5);
    }

    /* Cases contraintes */
    private void drawBoxConstraint(Graphics g, int widthContent, int heightContent) {
        g.setColor(Color.YELLOW);
        for (int i = 0; i < o.getboxConstraint().size(); i++){
            int[] yellowCell = o.getboxConstraint().get(i) ; // Chaque case des contraintes
            g.fillRect(widthContent * yellowCell[1] / 20, heightContent * yellowCell[0] / 10, widthContent / 20, heightContent / 10);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(o.getValueConstraint()[i]), widthContent * yellowCell[1] / 20 + widthContent / 40 - 4, heightContent * yellowCell[0] / 10 + heightContent / 20 + 5);
            g.setColor(Color.YELLOW);
        }   
    }

    /* Voitures */
    public void drawCar(Graphics g, int widthContent, int heightContent){
        int marginX = 0 ;
        //int marginY = 0 ;
        for (int i = 0 ; i < o.getCars().size() ; i++){
            Car c = o.getCars().get(i) ;
            g.setColor(c.getColor());
            int posX = (widthContent ) * o.getPath().get(c.getIndex())[1] / 20 ;
            int posY = (heightContent + marginX) * o.getPath().get(c.getIndex())[0] / 10 ;
            int pasX = (widthContent / 3) / 20 ;
            int pasY = (heightContent  / 3) / 10 ;

            g.fillRect(posX, posY, pasX, pasY);
            marginX += heightContent / 3 / 10 ;
          //  marginY += widthContent / 3 / 20 ;

        }
    }

    /* Paramètres de tableau de bord */
    public void setDashboard(JFrame frame, GameObservable o, String title, JButton pressedButton, JButton slow, JButton speedUp, Color color, Car car, JLabel fuel, JLabel tour, JLabel msg){
        toPause(pressedButton);
        toSlow(slow);
        toSpeedUp(speedUp);
        JDialog dialog = new JDialog(frame, title);
        JPanel dashboard = new JPanel(new BorderLayout());
        JPanel subDashboard = new JPanel();
        JPanel buttons = new JPanel() ;
        subDashboard.setBackground(color);
        
        subDashboard.add(msg) ;
        subDashboard.add(fuel);
        subDashboard.add(tour);
        dashboard.setBackground(color);
        buttons.add(pressedButton);
        buttons.add(slow) ;
        buttons.add(speedUp) ;
        dashboard.add(buttons, BorderLayout.SOUTH) ;
        dashboard.add(subDashboard);
        dialog.setSize(300,300);
        dialog.setLocation(500,400);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.add(dashboard);
        dialog.setVisible(true);
    }

    /* Mettre en pause ou reprendre le jeu */
    public void toPause(JButton pressedButton){
        pressedButton.addActionListener(e -> {
            o.toPause();
            for (JButton button : pauses.values()){
                button.setText(o.onPause() ? "Reprendre" : "Pause");
            }
        });
    }

    /* Callback pour ralentir */
    public void toSlow(JButton slowButton) {
        slowButton.addActionListener(e -> {
            for(Map.Entry<Car,JButton> keyValue : slow.entrySet()){
                if (keyValue.getValue() == slowButton) {
                    //System.out.println("Tu parles de ralentir " + keyValue.getKey().getName());
                    if (keyValue.getKey().getState() == StateName.Stopped) {
                        msgs.get(keyValue.getKey()).setText("La voiture est arrêtée") ;
                    }else{
                        msgs.get(keyValue.getKey()).setText("") ;
                        keyValue.getKey().slow() ;
                    }
                }
            }
        });
    }
    
    /* Callback pour accélérer */
    public void toSpeedUp(JButton speedUpButton) {
        speedUpButton.addActionListener(e -> {
            for(Map.Entry<Car,JButton> keyValue : speedUp.entrySet()){
                if (keyValue.getValue() == speedUpButton) {
                    if (keyValue.getKey().getState() == StateName.Boost) {
                        msgs.get(keyValue.getKey()).setText("La voiture est à la vitesse maximale") ;
                    }else {
                        msgs.get(keyValue.getKey()).setText("") ;
                        keyValue.getKey().accelerate() ;
                    }
                }
            }
                
        });
    }

    /* Choix du décorateur */
    public void setDecorator(JButton validate) {
        validate.addActionListener(e -> {
            for (int car = 0 ; car < o.getCars().size() ; car++) {
                JCheckBox[] boxes = checkboxes.get(car) ;
                Car c = o.getCars().get(car) ;
                if(boxes[0].isSelected()) c.newDecorator(new BoosterSound(c)) ;
                else if(boxes[1].isSelected()) c.newDecorator(new DrunkPilot(c)); 
                else if(boxes[2].isSelected()) c.newDecorator(new HybridSystem(c)) ;
                o.getCars().set(car, c) ;
            }
        });
    }
}




