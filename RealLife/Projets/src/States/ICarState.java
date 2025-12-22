package States ;

import Cars.*;

public abstract class ICarState {
    protected Car car ;
    
    public ICarState(Car c) {
        this.car = c ;
    }

    public abstract void upgradeState() ;
    public abstract void downgradeState() ;
    public abstract StateName getState() ;
    public abstract void move(int number) ;
    public abstract void useFuel() ;
    public abstract int random() ;
}
