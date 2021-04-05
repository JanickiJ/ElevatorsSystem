package agh.cs.elevators.system;

import agh.cs.elevators.basics.Elevator;
import agh.cs.elevators.basics.Task;

public class ElevatorsSystemEngine {
    private final ElevatorsSystem system;

    public ElevatorsSystemEngine(ElevatorsSystem system){
        this.system = system;
    }


    public void addPickUp(Task task){
        int Fs = Integer.MAX_VALUE;
        Elevator elevatorToPickUp = system.getElevators().get(0);
        for(Elevator elevator : system.getElevators()){
            if(Fs > elevator.countFs(task)){
                Fs = elevator.countFs(task);
                elevatorToPickUp = elevator;
            }
        }
        elevatorToPickUp.addTask(task);
    }

    public void step(){
        for(Elevator elevator : system.getElevators()){
            elevator.move();
        }
    }

}
