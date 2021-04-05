package agh.cs.elevators.basics;

import java.util.Random;

public enum Direction {
    DOWN,
    UP,
    INPLACE;

    public boolean isUp(){
        return this == UP;
    }
    public boolean isDown(){
        return this == DOWN;
    }
    public boolean isInPlace(){
        return this == INPLACE;
    }

    public Direction opposite(){
        return switch (this){
            case UP -> DOWN;
            case DOWN -> UP;
            case INPLACE -> INPLACE;
        };
    }

    public Direction randomDirection(){
        Random random = new Random();
        return switch (random.nextInt(2)) {
            case 0 -> UP;
            case 1 -> DOWN;
            default -> throw new IllegalStateException("Unexpected value: " + random.nextInt(2));
        };
    }

    public int toIntDirection(){
        return switch (this){
            case UP -> 1;
            case INPLACE -> 0;
            case DOWN -> -1;
        };
    }
}
