package Decorator;

import Cars.*;

abstract class CarDecorator implements ICar{
    protected ICar car ;

    public CarDecorator(ICar car) { this.car = car ; }

    public void accelerate() { car.accelerate() ; }

}