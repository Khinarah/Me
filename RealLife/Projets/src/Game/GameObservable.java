package Game;

import java.util.ArrayList;

import Cars.Car;

/* DESCRIPTION DU FICHIER

Contient tous les éléments (méthodes & champs) relatifs aux données ainsi que leur manipulation
au cours du jeu.

*/

public class GameObservable {

    /* Liste des positions des chemins : 
    1er chemin : 9e ligne ; 
    2e chemin : 2e colonne ; 
    (...) */ 
    private final static int[] PATHTYPE = new int[]{9,2,3,13,6,16,2,19}; 
    
    /* Liste des valeurs des cases tournantes */
    private final static int[] VALUECONSTRAINT = new int[]{5,3,2,2,2,2,5};
    private final static int[] INDEXCONSTRAINT = new int[] {16, 22, 33, 36, 39, 43, 46} ;

    private ArrayList<int[]> path; // Liste des cases de la piste
    private ArrayList<int[]> boxConstraint; // Cases tournantes
    private ArrayList<GameObserver> observers;

    /* Liste des voitures */
    private ArrayList<Car> cars ;

    private boolean pause ;

    public GameObservable(){
        path = new ArrayList<int[]>();
        boxConstraint = new ArrayList<int[]>();
        observers = new ArrayList<GameObserver>();
        cars = new ArrayList<Car>();  
        pause = false ;
    }

    public void register(GameObserver o) { observers.add(o); }

    public void notifyObservers(){
        for (GameObserver o : observers){
            o.update();
        }
    }
    
    /* Ajout des voitures dans la liste (positions initiales) */
    public void initCars(Car c) { cars.add(c) ; }

    /* Avancement des voitures */
    public void play(){
        for (Car c : cars){
            c.move();
        }
        leaderboard();
        notifyObservers();
    }

    // Ajout d’une case dans la piste par indices
    public void makeCase(int line , int column) { path.add(new int[]{line - 1 , column - 1}); }

    /* Ajout de chemin horizontal */
    public void addLinePath(int line, int from, int to){
        if(from < to){ // On dessine le chemin de gauche à droite
            for (int i = from; i <= to; i++){
                makeCase(line, i);
            }    
        }else{ // de droite à gauche
            for (int i = from; i >= to; i--){
                makeCase(line, i);
            }
        }
    }

    /* Ajout de chemin vertical */
    public void addColumnPath(int column, int from, int to){
        if(from < to){ // On dessine le chemin de haut en bas
            for (int i = from; i <= to; i++){
                makeCase(i, column);
            }
        }else{ // de bas en haut
            for (int i = from; i >= to; i--){ 
                makeCase(i, column);
            }
        }
    }

    /* Ajout des chemins à la piste */
    public void addTrack(){
        addLinePath(PATHTYPE[0], 18, 2);
        addColumnPath(PATHTYPE[1], 8, 3);
        addLinePath(PATHTYPE[2], 3, 13);
        addColumnPath(PATHTYPE[3], 4, 6);
        addLinePath(PATHTYPE[4], 14, 16);
        addColumnPath(PATHTYPE[5], 5, 2);
        addLinePath(PATHTYPE[6], 17, 19);
        addColumnPath(PATHTYPE[7], 3, 9);
    }
    
    /* Ajout des cases contraintes */
    public void makeConstraint(){
        for (int i = 0 ; i < INDEXCONSTRAINT.length ; i++) {
            boxConstraint.add(path.get(INDEXCONSTRAINT[i])) ;
        }
    }   

    /* Mise en pause du jeu */
    public void toPause(){ pause = !pause ; }
    
    /* Début & déroulement du jeu avec conditions de fin de jeu */
    public void startGame() {
        while (!allFinished()) {
            if (!onPause()) {
                play(); 
            }
            try {
                Thread.sleep(2000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /* Classement des voitures */
    public ArrayList<Car> leaderboard() {
        cars.sort((c1, c2) -> {
            int cmpTour = Integer.compare(c2.getTour(), c1.getTour());
            if (cmpTour != 0) {
                return cmpTour;
            }
            return Integer.compare(c2.getIndex(), c1.getIndex());
        });
        return cars;
    }
    
    /* Conditions de fin de jeu */
    public boolean allFinished(){
        for (Car c : cars){
            if(!c.finished() && c.getFuel() > 0){
                return false;
            }
        }
        return true ;
    }
    
    
    /*   Getter   */
    public boolean onPause(){ return pause ; }
    
    public ArrayList<int[]> getPath(){ return path; }
    
    public ArrayList<int[]> getboxConstraint(){ return boxConstraint; }
    
    public int[] getValueConstraint(){ return VALUECONSTRAINT ; }

    public int[] getIndexConstraint() { return INDEXCONSTRAINT ; }
    
    public ArrayList<Car> getCars(){ return cars ; }

    
}
