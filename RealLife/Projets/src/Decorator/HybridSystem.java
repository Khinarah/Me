package Decorator;

import Cars.* ;
import States.* ;
import Game.* ;

import java.awt.Color;

public class HybridSystem extends CarDecorator {
    
    public HybridSystem(ICar car) { 
        super(car) ; 
        car.setFuel(-40);
        System.out.println("Flora hybride créée");
    }
    
    @Override
    public void useFuel(){ car.setFuel(10) ; }

    @Override
    public void setIndex(int newIndex){ car.setIndex(newIndex); }

    @Override
    public void setFuel(int fuelUsed) { car.setFuel(fuelUsed) ; }

    @Override
    public void nextTour(){ car.nextTour() ; }

    @Override
    public void move() { car.getCurrentState().move(car.getCurrentState().random()) ; }

    @Override
    public void newState(ICarState newState) { car.newState(newState) ; }

    @Override
    public void accelerate() { car.getCurrentState().upgradeState(); }

    @Override
    public void slow() { 
        car.getCurrentState().downgradeState() ;
        car.setFuel(-5) ; 
        System.out.println("La batterie a regagné 5%");
    }

    @Override
    public void newDecorator(ICar newDecorator) { car.newDecorator(newDecorator) ; }

    @Override
    public boolean finished(){ return car.finished() ; }

    /* Getters */
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
