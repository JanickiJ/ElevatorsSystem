package agh.cs.elevators.system;

import agh.cs.elevators.basics.Elevator;
import agh.cs.elevators.basics.Task;

public class ElevatorsSystemEngineIncorrect extends AbstractElevatorsSystem {

    public void addPickUp(Task task){
        int Fs = Integer.MAX_VALUE;
        Elevator elevatorToPickUp = system.getElevators().get(0);
        elevatorToPickUp.addTask(task);
    }


}
