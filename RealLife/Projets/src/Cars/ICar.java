package Cars ;

import States.*;
import Game.*;

import java.awt.Color ;

public interface ICar {
    void setIndex(int newIndex) ;
    void useFuel() ;
    void setFuel(int fuelUsed) ;
    void nextTour() ;
    void move() ;
    void newState(ICarState newsState) ;
    void accelerate() ;
    void slow() ;
    boolean finished() ;
    void newDecorator(ICar decorator) ;
    int getFuel() ;
    int getTour() ;
    Color getColor() ;
    int getMove() ;
    int getIndex() ;
    String getName() ;
    StateName getState() ;
    GameObservable getObservable() ;
    ICarState getCurrentState() ;

    
}
