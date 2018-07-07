package camus.gui;

import camus.core.GofBoard;
import camus.core.GcgBoard;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import camus.music.Spartito;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller implements Initializable {
    
    private final int    DEFAULT_SIZE = 10;
    private final double DEFAULT_PROB = 0.3;
    private final int DEFAULT_POSSIBLE_STATE = 10;

    @FXML
    private FlowPane baseGof;
    @FXML
    private FlowPane baseGcg;
    @FXML
    private Label countLabel;
    @FXML
    private Slider countSlider;
    @FXML
    private HBox presetBox;
    @FXML
    private Button openButton, saveButton, openPresetBtn;
    @FXML
    private Button runButton, stopButton, randomizeButton, clearButton;
    @FXML
    private HBox rootBox;

    private GofBoard gofBoard;
    private GcgBoard gcgBoard;

    private JavaFXDisplayDriver display;

    private Timeline loop = null;
    
    private int windowWidth = 750;
    private int cellSizePx = 5;
    
    private Spartito spartito;
    //private PresetHandler presetHandler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       /* presetHandler = new PresetHandler();
        AnchorPane anchor = presetHandler.loadPresets(base);
        presetBox.getChildren().add(anchor);
		*/
        createBoardGof(DEFAULT_SIZE, DEFAULT_PROB);
        createBoardGcg(DEFAULT_SIZE, DEFAULT_POSSIBLE_STATE);
        createDisplay();
        spartito = new Spartito();
        
        attachResizeListener();
    }

    @FXML
    private void onRun(Event evt) {
        toggleButtons(false);

        loop = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            gofBoard.update();
            display.displayBoardGof(gofBoard);
            gcgBoard.update();
            display.displayBoardGcg(gcgBoard);
            
            spartito.estrazione(gofBoard, gcgBoard);
        }));
        
        loop.setCycleCount(100);
        loop.play();
    }

    @FXML
    private void onStop(Event evt) {
        toggleButtons(true);
        loop.stop();
        spartito.printList();
    }

    @FXML
    private void onClear(Event evt) {
        createBoardGof(DEFAULT_SIZE, 0);
        createBoardGcg(DEFAULT_SIZE, 10);
    }

    @FXML
    private void onRandomize(Event evt) {
        createBoardGof(DEFAULT_SIZE, (double) countSlider.getValue()/100);
        createBoardGcg(DEFAULT_SIZE, 10);
    }
    
    @FXML
    private void onPresetOpen(Event evt) {
       /* board = presetHandler.openCurrentPreset(DEFAULT_SIZE);
        createDisplay();*/
    }

    /**
     * TODO: check if valid file (correct number of cells for rectangle shaped board)
     */
    @FXML
    private void onOpen(Event evt) {
       /* Board newBoard = FileHandler.openFromFile(DEFAULT_SIZE);
        if (newBoard != null) {
            board = newBoard;
            createDisplay();
        }*/
    }

    @FXML
    private void onSave(Event evt) {
        //FileHandler.saveToFile(board);
    }


    @FXML
    private void onSlide(Event evt) {
       /* countSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                countLabel.setText(newValue.intValue()+"%");
                createBoard(DEFAULT_SIZE, (double) newValue.intValue()/100);
            }
        });*/
    }


    @FXML
    private void onAbout(Event evt) {
        // TEXT //
        Text text1 = new Text("Conway's Game of Life\n");
        text1.setFont(Font.font(30));
        Text text2 = new Text(
                "\nThe Game of Life, also known simply as Life, is a cellular automaton devised by the British mathematician John Horton Conway in 1970.\n"
                        + "The game is a zero-player game, meaning that its evolution is determined by its initial state, requiring no further input. One interacts with the Game of Life by creating an initial configuration and observing how it evolves or, for advanced players, by creating patterns with particular properties."
                );
        Text text3 = new Text("\n\nRules\n");
        text3.setFont(Font.font(20));
        Text text4 = new Text(
                "\nThe universe of the Game of Life is a two-dimensional orthogonal grid of square cells, each of which is in one of two possible states, alive or dead. Every cell interacts with its eight neighbours, which are the cells that are horizontally, vertically, or diagonally adjacent. At each step in time, the following transitions occur:\n"
                        +"\n1) Any live cell with fewer than two live neighbours dies, as if caused by under-population.\n"
                        +"2) Any live cell with two or three live neighbours lives on to the next generation.\n"
                        +"3) Any live cell with more than three live neighbours dies, as if by overcrowding.\n"
                        +"4) Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.\n\nMore on Wikipedia:\n"
                );

        Hyperlink link = new Hyperlink("http://en.wikipedia.org/wiki/Conway%27s_Game_of_Life <-------not working");
        TextFlow tf = new TextFlow(text1,text2,text3,text4,link);
        tf.setPadding(new Insets(10, 10, 10, 10));
        tf.setTextAlignment(TextAlignment.JUSTIFY);
        // END TEXT, START WINDOW //
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(new Stage());
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(tf);
        Scene dialogScene = new Scene(dialogVbox, 450, 500);
        dialog.setScene(dialogScene);
        dialog.show();
        // END WINDOW //
    }
    
    private void toggleButtons(boolean enable) {
        countSlider.setDisable(!enable);
        presetBox.setDisable(!enable);
        openButton.setDisable(!enable);
        openPresetBtn.setDisable(!enable);
        saveButton.setDisable(!enable);
        runButton.setDisable(!enable);
        clearButton.setDisable(!enable);
        randomizeButton.setDisable(!enable);

        stopButton.setDisable(enable);
    }

    private void createBoardGof(int size, double prob) {
        gofBoard = new GofBoard(size, size, prob);
        //createDisplay();
    }
    
    private void createBoardGcg(int size, int p) {
        gcgBoard = new GcgBoard(size, size, p);
        //createDisplay();
    }
    
    private void createDisplay() {
        display = new JavaFXDisplayDriver(gofBoard.getSize(), cellSizePx, gofBoard, gcgBoard);

        baseGof.getChildren().clear();
        baseGof.getChildren().add(new Group(display.getPaneGof()));
        baseGcg.getChildren().clear();
        baseGcg.getChildren().add(new Group(display.getPaneGcg()));   
    }
    
    private void attachResizeListener() {
        ChangeListener<Number> sizeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int newWidth = newValue.intValue();
                if (newWidth > 250 && Math.abs(newWidth - windowWidth) >= 50) {
                    windowWidth = newWidth;
                    cellSizePx = newWidth / (DEFAULT_SIZE + 80);
                    createDisplay();
                }
            }
        };
        rootBox.widthProperty().addListener(sizeListener);
    }
}