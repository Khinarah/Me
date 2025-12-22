package States ;

import Cars.*;

public class DamagedState extends ICarState{
    private int cpt = 0;
    
    public DamagedState(Car c) {
        super(c) ;
        System.out.println("La voiture " + car.getName() + " ne peut plus avancer.\n");
    }

    @Override
    public void move(int number) { 
        cpt++ ;
        //System.out.println("cpt : " + cpt);
        if(cpt >= 5) {
            //System.out.println("cpt = 5 => passage en Low...");
            car.newState(new LowState(car));
        }
    }

    @Override
    public void upgradeState(){
        System.out.println("Voiture "+ car.getName() + " endommagée");
    }

    @Override
    public void downgradeState() {
        upgradeState() ;
    }

    @Override
    public void useFuel() { return ;}

    @Override
    public int random() { return 0 ;}

    @Override
    public StateName getState() {
        return StateName.Damaged ;
    }

    
}