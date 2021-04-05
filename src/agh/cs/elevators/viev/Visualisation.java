package agh.cs.elevators.viev;

import agh.cs.elevators.basics.Direction;
import agh.cs.elevators.basics.Elevator;
import agh.cs.elevators.basics.Task;
import agh.cs.elevators.engine.OptionsParser;
import agh.cs.elevators.system.ElevatorSystemGenerator;
import agh.cs.elevators.system.ElevatorsSystem;
import agh.cs.elevators.system.ElevatorsSystemEngine;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Visualisation {

    private final int rows;
    private final int columns;
    private final int squareSize;
    private final Timeline timeline;
    private final OptionsParser parameters;
    private final GridPane gridPane;
    private final BorderPane bPane;
    private final HBox statisticPanel;
    private final Scene scene;
    private final String path = "resources/agh/cs/elevators/";
    private final ElevatorsSystem system;
    private final ElevatorsSystemEngine engine;
    private final ElevatorSystemGenerator generator;
    private Direction newTaskDirection;
    private boolean showInfo;
    private boolean generateData;

    public Visualisation(OptionsParser parameters, ElevatorsSystem system) {

        this.parameters = parameters;
        this.system = system;
        this.engine = system.getEngine();
        this.generator = system.getGenerator();
        this.rows = parameters.getNumberOfFloors();
        this.columns = parameters.getNumberOfElevators();
        this.squareSize =40;
        this.gridPane = new GridPane();
        this.bPane = new BorderPane();
        this.showInfo = false;
        this.generateData = false;
        this.newTaskDirection = Direction.DOWN;
        this.statisticPanel = new HBox();
        this.timeline = new Timeline(new KeyFrame(Duration.millis(600), e -> step()));
        timeline.setCycleCount(Animation.INDEFINITE);

        drawBorderPane();
        this.scene = new Scene(bPane,bPane.getMaxWidth(), bPane.getMaxHeight());
        getKey();
    }

    private void step(){
        if(generateData){
            generator.addRandom();
        }
        engine.step();
        drawBorderPane();
    }


    private void drawBorderPane() {
        drawBackground();
        drawElevators();
        drawStatisticPanel();
        drawTasks();
        gridPane.scaleXProperty();
    }

    private void drawBackground() {
        Image backgroundImage = new Image(path +"bglight.png");
        gridPane.getChildren().clear();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns+3; j++) {
                Rectangle rectangle = new Rectangle(j * squareSize, i * squareSize, squareSize, squareSize);
                if(j>=columns){
                    rectangle.setFill(Color.rgb(142,140,132));
                }
                else {
                    rectangle.setFill(new ImagePattern(backgroundImage));
                    int finalI = rows - i - 1;
                    int finalJ = j;
                    rectangle.setOnMouseClicked((MouseEvent e) -> {
                        if (!showInfo) {
                            Task task = new Task(this.newTaskDirection, system.getFloors().get(finalI));
                            if (newTaskDirection == Direction.INPLACE) {
                                system.getElevators().get(finalJ).addTask(task);
                            } else {
                                engine.addPickUp(task);
                            }
                            drawTasks();
                        }
                    });
                }
                gridPane.add(rectangle, j * squareSize, i * squareSize);
            }
        }
    }

    private void drawElevators() {
        Rectangle rectangle;

        for (Elevator elevator : system.getElevators()) {

            int i = elevator.getId();
            int j = parameters.getNumberOfFloors() - elevator.getCurrentFloor().getNumber()-1;
            rectangle = new Rectangle(i * squareSize, j * squareSize, squareSize, squareSize);
            Image objectImage;
            if (elevator.getDirection().isDown()) {
                objectImage = new Image(path + "down.png");
            } else if (elevator.getDirection().isUp()) {
                objectImage = new Image(path + "up.png");
            } else {
                objectImage = new Image(path + "inplace.png");
            }
            rectangle.setFill(new ImagePattern(objectImage));
            rectangle.setOnMouseClicked((MouseEvent e) ->{
                if(showInfo) {
                    system.getElevators().get(i).printTasks();
                }
            });
            gridPane.add(rectangle, i * squareSize, j * squareSize);
        }
        bPane.setCenter(gridPane);
    }

    private void drawTasks(){
        Rectangle rectangle;

        for (Task task : system.getTasks()) {
            int i = rows - task.getDestination().getNumber() -1;
            int j = task.getElevator().getId();
            Image objectImage;
            if (task.getDirection().isInPlace()) {
                rectangle = new Rectangle(j * squareSize, i * squareSize, squareSize, squareSize);
                objectImage = new Image(path + "inplace.png");
                rectangle.setFill(new ImagePattern(objectImage));
                rectangle.setWidth(15);
                rectangle.setHeight(15);
                gridPane.add(rectangle, j * squareSize, i * squareSize);
            }
            else{
                if(task.getDirection().isUp()) {
                    rectangle = new Rectangle((columns+1) * squareSize, i * squareSize, squareSize, squareSize);
                    objectImage = new Image(path + "up.png");
                    rectangle.setFill(new ImagePattern(objectImage));
                    gridPane.add(rectangle, (columns+1) * squareSize, i * squareSize);
                }
                else {
                    objectImage = new Image(path + "down.png");
                    rectangle = new Rectangle((columns) * squareSize, i* squareSize, squareSize, squareSize);
                    rectangle.setFill(new ImagePattern(objectImage));
                    gridPane.add(rectangle, (columns) * squareSize, i * squareSize);
                }
            }
        }
        bPane.setCenter(gridPane);
    }



    private void drawStatisticPanel(){

        Button upButton = createButton("up.png");
        upButton.setOnAction(e -> {
            this.showInfo = false;
            this.newTaskDirection = Direction.UP;
        });
        Button downButton = createButton("down.png");
        downButton.setOnAction(e -> {
            this.showInfo = false;
            this.newTaskDirection = Direction.DOWN;
        });
        Button inPlaceButton = createButton("inplace.png");
        inPlaceButton.setOnAction(e -> {
            this.showInfo = false;
            this.newTaskDirection = Direction.INPLACE;
        });
        Button infoButton = createButton("info.jpg");
        infoButton.setOnAction(e -> {
            this.showInfo = true;
        });
        Button generatorButton = createButton("random.png");
        generatorButton.setOnAction(e -> {
            this.generateData = !generateData;
        });
        Button startPauseButton = createButton("start.png");
        startPauseButton.setOnAction(e -> {
            if(timeline.getStatus() == Animation.Status.RUNNING){
                timeline.pause();
            }
            else {
                timeline.play();
            }
        });


        statisticPanel.getChildren().clear();
        statisticPanel.setAlignment(Pos.CENTER);
        statisticPanel.setSpacing(5);
        statisticPanel.getChildren().addAll(downButton,upButton,inPlaceButton,infoButton,generatorButton,startPauseButton);
        statisticPanel.setMaxWidth(gridPane.getMaxWidth());
        statisticPanel.setBackground(new Background(new BackgroundFill(Color.rgb(142,140,132), CornerRadii.EMPTY, Insets.EMPTY)));
        bPane.setTop(statisticPanel);

    }

    private Button createButton(String imgPath){
        Button button = new Button();
        button.setAlignment(Pos.CENTER_RIGHT);
        Image image = new Image(path + imgPath);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setPreserveRatio(true);
        button.setGraphic(imageView);
        button.setPrefSize(22, 22);
        return button;
    }

    private void getKey() {
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.S) {
                step();
            }
        });
    }

    public Scene getScene() {
        return scene;
    }

}
