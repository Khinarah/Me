package States ;

import Cars.*;

public class StoppedState extends ICarState{

    public StoppedState(Car c) {
        super(c) ;
    }
    
    @Override
    public void move(int number) { return ; }

    @Override
    public void upgradeState(){
        car.newState(new LowState(car)) ;
        System.out.println(car.getName() + " passe à l'état Low...") ;
    }

    @Override
    public void downgradeState() {
        //System.out.println("La voiture " + car.getName() + " est déjà arrêtée.") ;
    }

    @Override
    public void useFuel() { return ;}

    @Override
    public int random() { return 0 ;}

    @Override
    public StateName getState() {
        return StateName.Stopped ;
    }
}