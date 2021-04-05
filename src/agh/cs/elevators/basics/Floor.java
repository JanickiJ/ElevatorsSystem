package agh.cs.elevators.basics;

import java.util.Objects;

public class Floor {
    private final int number;

    public Floor(int number){
        this.number = number;

    }

    public int getDistance(Floor other){
        return Math.abs(other.getNumber() - this.number);
    }

    public boolean isAbove(Floor other){
        return (this.getNumber() - other.getNumber()>0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Floor)) return false;
        Floor floor = (Floor) o;
        return number == floor.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    public int getNumber() {
        return number;
    }
}
