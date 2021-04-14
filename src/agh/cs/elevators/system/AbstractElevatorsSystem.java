package agh.cs.elevators.system;

import agh.cs.elevators.basics.Elevator;
import agh.cs.elevators.basics.Task;

public abstract class AbstractElevatorsSystem implements ElevatorsSystemI {
    protected ElevatorsSystem system;



    @Override
    public void addPickUp(Task task) {

    }

    public void step(){
        for(Elevator elevator : system.getElevators()){
            elevator.move();
        }
    }
}
