package Cars ;

import Game.* ;
import States.*;

import java.awt.Color ;


/* DESCRIPTION DU FICHIER

Contient tous les éléments (méthodes & champs ) relatifs à une voiture

*/

public class Car implements ICar{
    private GameObservable o ;
    private String nameColor ;
    private Color color ;
    private int index, fuel, tour, move ;
    private ICarState state ;
    private ICar decorator ;

    public Car(GameObservable o, String nameColor, Color color){
        this.nameColor = nameColor ;
        this.fuel = 60 ;
        this.tour = 0 ;
        this.color = color ;
        this.move = 0 ;
        this.index = 0 ;
        this.o = o ;
        this.state = new NormalState(this) ;
        this.o.initCars(this);
    }

    @Override
    public void move() { decorator.move() ; }

    /* SETTERS */
    @Override
    public void setIndex(int newIndex){ this.index = newIndex ; }

    @Override
    public void useFuel(){ state.useFuel() ; }

    @Override
    public void setFuel(int fuelUsed) { this.fuel -= fuelUsed ; }

    @Override
    public void nextTour(){ this.tour++ ; }

    public void newDecorator(ICar newDecorator) { decorator = newDecorator ; }

    @Override
    public void newState(ICarState newState) { state = newState ; }

    /* MODIFICATION DE VITESSE */
    @Override
    public void accelerate() { decorator.accelerate() ; }

    @Override
    public void slow() { decorator.slow() ; }


    @Override
    public boolean finished(){
        if(tour == 3){
            return true ;
        }
        return false ;
    }

    /* GETTERS */
    @Override
    public int getFuel(){ return fuel ; }

    @Override
    public int getTour(){ return tour ; }

    @Override
    public Color getColor(){ return color ; }

    @Override
    public int getMove(){ return move ; }

    @Override
    public int getIndex(){ return index ; }

    @Override
    public String getName(){ return nameColor ; }

    @Override
    public StateName getState() { return state.getState() ; }

    @Override
    public ICarState getCurrentState() { return state ; }

    @Override
    public GameObservable getObservable() { return o ; }
}

