package camus.gui;

import camus.core.GofBoard;
import camus.core.GcgBoard;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import camus.music.Nota;
import camus.music.Sintetizzatore;
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
    private final int DEFAULT_POSSIBLE_STATE = 4;

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
    private Sintetizzatore sint;
    
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
        sint = new Sintetizzatore();
        
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
            int size = spartito.getSpartito().size();
            try {
				//sint.play(spartito.getSpartito().get(size - 1));
				Thread.sleep(50);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }));
        
        loop.setCycleCount(100);
        loop.play();
    }

    @FXML
    private void onStop(Event evt) throws InterruptedException {
        toggleButtons(true);
        loop.stop();
        spartito.printList();
        
        //sint.play(spartito);
        //sint.play(spartito.getSpartito().get(0).get(0));
    }

    @FXML
    private void onClear(Event evt) {
        createBoardGof(DEFAULT_SIZE, 0);
        createBoardGcg(DEFAULT_SIZE, 10);
    }

    @FXML
    private void onRandomize(Event evt) throws InterruptedException {
        /*createBoardGof(DEFAULT_SIZE, (double) countSlider.getValue()/100);
        createBoardGcg(DEFAULT_SIZE, 10);*/
    	spartito.translate();
    	sint.playFlow(spartito, 90);
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
    	gofBoard.update();
        display.displayBoardGof(gofBoard);
        gcgBoard.update();
        display.displayBoardGcg(gcgBoard);
        
        spartito.estrazione(gofBoard, gcgBoard);
        int size = spartito.getSpartito().size();
        try {
			sint.play(spartito.getSpartito().get(size - 1));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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