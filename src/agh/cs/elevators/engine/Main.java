package agh.cs.elevators.engine;

import agh.cs.elevators.system.ElevatorsSystem;
import agh.cs.elevators.viev.Visualisation;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

import static agh.cs.elevators.engine.OptionsParser.loadPropFromFile;

public class Main extends Application {

    private OptionsParser parameters;

    @Override
    public void start(Stage primaryStage) {
        String parametersPath = "src\\agh\\cs\\elevators\\parameters.json";
        try {
            parameters = loadPropFromFile(parametersPath);
        } catch (IllegalArgumentException | FileNotFoundException ex) {
            System.out.println();
        }

        ElevatorsSystem system= new ElevatorsSystem(parameters);
        Visualisation visualisation = new Visualisation(parameters,system);
        primaryStage.setTitle("ELEVATORS SYSTEM");
        primaryStage.setScene(visualisation.getScene());
        primaryStage.show();

    }
}
