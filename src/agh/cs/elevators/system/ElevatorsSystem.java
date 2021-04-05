package agh.cs.elevators.system;

import agh.cs.elevators.basics.Direction;
import agh.cs.elevators.basics.Elevator;
import agh.cs.elevators.basics.Floor;
import agh.cs.elevators.basics.Task;
import agh.cs.elevators.engine.OptionsParser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ElevatorsSystem {
    private final LinkedList<Elevator> elevators = new LinkedList<>();
    private final Map<Integer, Floor> floors = new HashMap<>();
    private final ElevatorsSystemEngine engine;
    private final ElevatorSystemGenerator generator;
    private final OptionsParser optionsParser;

    public ElevatorsSystem(OptionsParser optionsParser){
        this.engine = new ElevatorsSystemEngine(this);
        this.generator = new ElevatorSystemGenerator(this,optionsParser);
        this.optionsParser = optionsParser;

        for(int i =0; i<optionsParser.getNumberOfFloors();i++){
            floors.put(i,new Floor(i));
        }
        for(int i =0; i<optionsParser.getNumberOfElevators();i++){
            elevators.add(new Elevator(i,floors.get(0),this,optionsParser));
        }
    }

    public Floor nextFloor(Floor prevFloor, Direction direction){
        return floors.get(prevFloor.getNumber()+ direction.toIntDirection());
    }

    public LinkedList<Task> getTasks(){
        LinkedList<Task> tasks = new LinkedList<>();
        for(Elevator elevator: elevators){
            for(int i = 0;i < elevator.getTasks().size();i++){
                tasks.addAll(elevator.getTasks().get(i));
            }
        }
        return tasks;
    }

    public LinkedList<Elevator> getElevators() {
        return elevators;
    }

    public Map<Integer, Floor> getFloors() {
        return floors;
    }

    public ElevatorsSystemEngine getEngine() {
        return engine;
    }

    public ElevatorSystemGenerator getGenerator() {
        return generator;
    }

    public Floor getLastFloor(){
        return floors.get(optionsParser.getNumberOfFloors()-1);
    }
}
