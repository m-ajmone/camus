package camus.gui;

import camus.core.GofBoard;
import camus.core.GcgBoard;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import camus.music.Nota;
import camus.music.Sintetizzatore;
import camus.music.Spartito;
import camus.music.Strumento;
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
    
    private final int    DEFAULT_SIZE = 15;
    private final double DEFAULT_PROB = 0.3;
    private final int DEFAULT_POSSIBLE_STATE = 6;

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

    private GofBoard[] gofBoards;
    private GcgBoard[] gcgBoards;
    int num = 2;
    
    private JavaFXDisplayDriver display;

    private Timeline loop = null;
    
    private int windowWidth = 750;
    private int cellSizePx = 5;
    
    private ArrayList<Strumento> orchestra = new ArrayList <Strumento>();
    private Spartito[] spartiti;
    private Sintetizzatore sint;
    
    //private PresetHandler presetHandler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       /* presetHandler = new PresetHandler();
        AnchorPane anchor = presetHandler.loadPresets(base);
        presetBox.getChildren().add(anchor);
		*/
    	gofBoards = new GofBoard[num];
    	gcgBoards = new GcgBoard[num];
    	spartiti = new Spartito[num];
    	
    	for(int i = 0; i < num; i++){
    		createBoardGof(i, DEFAULT_SIZE, DEFAULT_PROB);
        	createBoardGcg(i, DEFAULT_SIZE, DEFAULT_POSSIBLE_STATE);
        	spartiti[i] = new Spartito();
        	spartiti[i].defineOrchestra();
        }
        createDisplay();
        //spartito = new Spartito();
        //spartito.defineOrchestra();
        sint = new Sintetizzatore();
    }

    @FXML
    private void onRun(Event evt) {
        toggleButtons(false);

        loop = new Timeline(new KeyFrame(Duration.millis(100), e -> {
        	for(int i = 0; i < num; i++){
	            gofBoards[i].update();
	            display.displayBoardGof(gofBoards[i]);
	            gcgBoards[i].update();
	            display.displayBoardGcg(gcgBoards[i]);
	            
	            spartiti[i].estrazione(gofBoards[i], gcgBoards[i], i);
        	}
        	
        	try {
				Thread.sleep(50);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }));
        
        for(int j=0; j<num; j++){
        	gofBoards[j].update();
            display.displayBoardGof(gofBoards[j]);
            gcgBoards[j].update();
            display.displayBoardGcg(gcgBoards[j]);
        }
        loop.setCycleCount(100);
        loop.play();
    }

    @FXML
    private void onStop(Event evt) throws InterruptedException {
        toggleButtons(true);
        loop.stop();
        //spartiti[0].printList();
        
        //sint.play(spartito);
        //sint.play(spartito.getSpartito().get(0).get(0));
    }

    @FXML
    private void onClear(Event evt) {
        createBoardGof(0, DEFAULT_SIZE, 0);
        createBoardGcg(0, DEFAULT_SIZE, 10);
    }

    @FXML
    private void onRandomize(Event evt) throws InterruptedException {
        /*createBoardGof(DEFAULT_SIZE, (double) countSlider.getValue()/100);
        createBoardGcg(DEFAULT_SIZE, 10);*/
    	for(int i = 0; i < num ; i++){
	    	spartiti[i].translate(i * 256);
	    	spartiti[i].defineBeat();
    	}
    	spartiti[0].printFlow(1000);
    	
    	sint.playFlow(spartiti, 60);
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
    	gofBoards[0].update();
        display.displayBoardGof(gofBoards[0]);
        gcgBoards[0].update();
        display.displayBoardGcg(gcgBoards[0]);
        
        spartiti[0].estrazione(gofBoards[0], gcgBoards[0], 0);
        int size = spartiti[0].getSpartito().size();
        try {
			sint.play(spartiti[0].getSpartito().get(size - 1));
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

    private void createBoardGof(int i, int size, double prob) {
        gofBoards[i] = new GofBoard(size, size, prob);
        //createDisplay();
    }
    
    private void createBoardGcg(int i, int size, int p) {
        gcgBoards[i] = new GcgBoard(size, size, p);
        //createDisplay();
    }
    
    private void createDisplay() {
        display = new JavaFXDisplayDriver(gofBoards[0].getSize(), cellSizePx, gofBoards[0], gcgBoards[0]);

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