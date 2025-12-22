package Decorator;

import Cars.* ;
import States.* ;
import Game.* ;

import java.awt.Color;
import java.util.Random;

public class DrunkPilot extends CarDecorator {
    
    public DrunkPilot(ICar car) { super(car) ; System.out.println("Voiture ivre créée");}

    @Override
    public void setIndex(int newIndex){ car.setIndex(newIndex); }

    @Override
    public void useFuel(){ car.getCurrentState().useFuel() ; }

    @Override
    public void setFuel(int fuelUsed) { car.setFuel(fuelUsed) ; }

    @Override
    public void nextTour(){ car.nextTour() ; }

    @Override
    public void move() { 
        boolean backward = new Random().nextBoolean() ;
        ICarState state = car.getCurrentState() ;
        int number = state.random() ;
        if(backward) state.move(-number);
        else state.move(number);  
    }

    /* Setter pour le changement d'état */
    @Override
    public void newState(ICarState newState) { car.newState(newState) ; }

    @Override
    public void accelerate() { car.getCurrentState().upgradeState(); }

    @Override
    public void slow() { car.getCurrentState().downgradeState(); }

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
