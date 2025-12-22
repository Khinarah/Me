package States ;

import java.util.Random;

import Cars.*;
import Game.GameObservable;

public class LowState extends ICarState {

    public LowState(Car c) {
        super(c) ;
    }

    @Override
    public void move(int number) {
        int random = number ;
        int oldIndex, newIndex ;
        GameObservable observable = car.getObservable() ;
        if(car.getFuel() >= random) {
            // Si on dépasse la piste alors on recommence au début de la piste avec un tour effectué en plus
            if(car.getIndex() + random >= observable.getPath().size()) car.nextTour();
            if (random >= 0) {
                oldIndex = car.getIndex() ;
                car.setIndex((car.getIndex() + random) % observable.getPath().size());
                useFuel();
                newIndex = car.getIndex() ;
                for (int i = 0 ; i < observable.getIndexConstraint().length ; i++) {
                    int currentBox = observable.getIndexConstraint()[i] ;
                    if(oldIndex <= currentBox && currentBox < newIndex) {
                        int dist = newIndex - currentBox ;
                        if(dist >= observable.getValueConstraint()[i]) {
                            car.newState(new DamagedState(car));
                        }
                    }
                }
            }
        }else System.out.println(car.getName() + " n'a plus de carburant.");
    } 

    @Override
    public void useFuel() {
        car.setFuel(1) ;
    }

    @Override
    public void upgradeState(){
        car.newState(new NormalState(car)) ;
        System.out.println(car.getName() + " passe à l'état Normal...") ;
    }

    @Override
    public void downgradeState() {
        car.newState(new StoppedState(car)) ;
        System.out.println(car.getName() + " s'arrête...") ;
    }

    @Override
    public int random() {
        return new Random().nextInt(1,4) ;
    }

    @Override
    public StateName getState() {
        return StateName.Low ;
    }
}
