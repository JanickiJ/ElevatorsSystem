package agh.cs.elevators.basics;

public class Task {
    private Floor destination;
    private final Direction direction;
    private Elevator elevator;
    private boolean isCompleted;


    public Task(Direction direction, Floor destination) {
        this.direction = direction;
        isCompleted = false;
        this.destination = destination;
    }

    public Floor getDestination(){
        return destination;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
    public void complete(){
        this.isCompleted =true;
    }

    public Elevator getElevator() {
        return elevator;
    }

    public void setElevator(Elevator elevator) {
        this.elevator = elevator;
    }


    public void print(){
        System.out.println("("+destination.getNumber()+", "+direction+")");
    }
}
