package agh.cs.elevators.engine;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class OptionsParser {

    private int numberOfElevators;
    private int numberOfFloors;

    public static OptionsParser loadPropFromFile(String path) throws FileNotFoundException, IllegalArgumentException {
        Gson gson = new Gson();
        OptionsParser parameters = gson.fromJson(new FileReader(path), OptionsParser.class);
        parameters.testData();
        return parameters;
    }

    public void testData() throws IllegalArgumentException {
        String incorrect = "Incorrect data: ";
        if (numberOfElevators <= 0) { throw new IllegalArgumentException(incorrect + "number of elevators"); }
        if (numberOfFloors <= 0 || numberOfFloors >16 ) { throw new IllegalArgumentException(incorrect + "number of floors"); }
    }


    public int getNumberOfElevators() {
        return numberOfElevators;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

}