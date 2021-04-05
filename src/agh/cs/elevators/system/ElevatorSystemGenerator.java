package agh.cs.elevators.system;

import agh.cs.elevators.basics.Direction;
import agh.cs.elevators.basics.Elevator;
import agh.cs.elevators.basics.Task;
import agh.cs.elevators.engine.OptionsParser;

import java.util.LinkedList;
import java.util.Random;

public class ElevatorSystemGenerator {
    private final ElevatorsSystemEngine engine;
    private final ElevatorsSystem system;
    private final OptionsParser optionsParser;
    private final LinkedList<Task> tasks;

    public ElevatorSystemGenerator(ElevatorsSystem system, OptionsParser optionsParser) {
        this.engine = system.getEngine();
        this.optionsParser = optionsParser;
        this.tasks = new LinkedList<>();
        this.system = system;
    }

    public void addRandom(){
        addRandomPickUp();
        addRandomTask();
    }

    public void addRandomPickUp(){
        Random rand = new Random();
        Direction x = Direction.UP.randomDirection();
        int y = rand.nextInt(optionsParser.getNumberOfFloors());
        Task task = new Task(x,system.getFloors().get(y));
        tasks.add(task);
        engine.addPickUp(task);
    }

    public void addRandomTask(){
        Random rand = new Random();
        int x, min, max;
        Direction direction;
        Elevator elevator;
        LinkedList<Task> clone = (LinkedList<Task>) tasks.clone();

        for(Task task : clone){
            if(task.isCompleted()){
                direction = task.getDirection();
                elevator = task.getElevator();
                if(direction.isUp()){
                    min = elevator.getCurrentFloor().getNumber();
                    max = optionsParser.getNumberOfFloors() -1;
                }
                else{
                    min = 0;
                    max = elevator.getCurrentFloor().getNumber();
                }
                x = rand.nextInt((max - min) + 1) + min;

                Task newTask = new Task(Direction.INPLACE, system.getFloors().get(x));
                elevator.addTask(newTask);
                tasks.remove(task);
            }
        }
    }
}
