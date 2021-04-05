package agh.cs.elevators.basics;

import agh.cs.elevators.engine.OptionsParser;
import agh.cs.elevators.system.ElevatorsSystem;
import java.util.HashMap;
import java.util.LinkedList;

public class Elevator {
    private final int id;
    private Floor currentFloor;
    private Floor destinationFloor;
    private Direction direction;
    private final HashMap<Integer, LinkedList<Task>> tasks;
    private final ElevatorsSystem system;

    public Elevator(int id, Floor startFloor, ElevatorsSystem system, OptionsParser optionsParser) {
        this.id = id;
        this.currentFloor = startFloor;
        this.tasks = new HashMap<>();
        this.system = system;
        this.direction = Direction.INPLACE;
        for (int i = 0; i < optionsParser.getNumberOfFloors(); i++) {
            tasks.put(i, new LinkedList<>());
        }
    }

    public int countFs(Task newTask) {
        int Fs;

        if (direction.isInPlace()){
            Fs = currentFloor.getDistance(newTask.getDestination())+1;
        }
        else if (direction.isUp() == newTask.getDestination().isAbove(currentFloor)) {
            if (newTask.getDirection() == direction) {
                Fs = currentFloor.getDistance(newTask.getDestination());
            } else {
                if (direction.isUp()) {
                    Fs = currentFloor.getDistance(newTask.getDestination()) + 2 * newTask.getDestination().getDistance(system.getLastFloor());
                } else {
                    Fs = currentFloor.getDistance(newTask.getDestination()) + 2 * (newTask.getDestination().getNumber());
                }
            }
        } else{
            if(direction.isUp()){
                Fs = currentFloor.getDistance(newTask.getDestination()) + 2 * newTask.getDestination().getDistance(system.getLastFloor());
            }
            else{
                Fs = currentFloor.getDistance(newTask.getDestination()) + 2 * (newTask.getDestination().getNumber());
            }
        }
        return Fs;
    }

    public void move() {
        removeTask();
        if(chooseTask()==null){
            direction = Direction.INPLACE;
            return;
        }
        destinationFloor = chooseTask().getFirst().getDestination();
        currentFloor = system.nextFloor(currentFloor,direction);
    }

    private LinkedList<Task> chooseTask() {
        LinkedList<Task> newTask;
        LinkedList<Task> downTask = null;
        LinkedList<Task> upTask = null;

        for (int i = tasks.size()-1; i > currentFloor.getNumber(); i--) {

            if (!tasks.get(i).isEmpty()) {
                upTask = tasks.get(i);
            }
        }
        for (int i = 0; i < currentFloor.getNumber(); i++) {
            if (!tasks.get(i).isEmpty()) {
                downTask = tasks.get(i);
            }
        }

        if(upTask == null && downTask == null){
            direction = Direction.INPLACE;
        }

        if (direction.isUp()) {
            if(upTask == null){
                direction = Direction.DOWN;
                newTask = downTask;
            }
            else{
                newTask = upTask;
            }
        }
        else {
            if(downTask == null){
                direction = Direction.UP;
                newTask = upTask;
            }
            else{
                newTask = downTask;
                direction = Direction.DOWN;
            }
        }
        return newTask;

    }


    public Direction getDirection() {
        return direction;
    }

    public void removeTask(){
        for(Task task :tasks.get(currentFloor.getNumber())){
            task.complete();
        }
        tasks.get(currentFloor.getNumber()).clear();
    }

    public void addTask(Task newTask){
        newTask.setElevator(this);
        tasks.get(newTask.getDestination().getNumber()).add(newTask);
        if(chooseTask()==null){
            direction = Direction.INPLACE;
        }
    }

    public int getId() {
        return id;
    }

    public Floor getCurrentFloor() {
        return currentFloor;
    }

    public HashMap<Integer, LinkedList<Task>> getTasks() {
        return tasks;
    }

    public void printTasks(){
        for(int i =0; i<tasks.size();i++){
            for(Task task : tasks.get(i)){
                task.print();
            }
        }
        System.out.println("-------");
    }
}