package Decorator;
import Cars.* ;
import States.* ;
import Game.* ;

import java.awt.Color;

public class BoosterSound extends CarDecorator {
    
    public BoosterSound(ICar car) { super(car) ; }

    @Override
    public void setIndex(int newIndex){ car.setIndex(newIndex); }

    @Override
    public void useFuel(){ car.getCurrentState().useFuel() ; }

    @Override
    public void setFuel(int fuelUsed) { car.setFuel(fuelUsed) ; }

    @Override
    public void nextTour(){ car.nextTour() ; }

    @Override
    public void move() { car.getCurrentState().move(car.getCurrentState().random()) ; }

    @Override
    public void newState(ICarState newState) { car.newState(newState) ; }

    @Override
    public void accelerate() {
        car.getCurrentState().upgradeState();
        if(car.getState() != StateName.Damaged) System.out.println("VROUUUUM !") ;
    }

    @Override
    public void slow() { car.getCurrentState().downgradeState(); }

    @Override
    public void newDecorator(ICar newDecorator) { car.newDecorator(newDecorator) ; }

    @Override
    public boolean finished(){ return car.finished() ; }

    /* GETTERS */
    @Override
    public int getFuel(){ return car.getFuel() ; }

    @Override
    public int getTour(){ return car.getTour() ; }

    @Override
    public Color getColor(){ return car.getColor() ; }

    @Override
    public int getMove(){ return car.getMove() ; }

    @Override
    public int getIndex(){ return car.getIndex() ; }

    @Override
    public String getName(){ return car.getName() ; }

    @Override
    public StateName getState() { return car.getCurrentState().getState() ; }

    @Override
    public ICarState getCurrentState() { return car.getCurrentState() ; }

    @Override
    public GameObservable getObservable() { return car.getObservable() ; }
}
