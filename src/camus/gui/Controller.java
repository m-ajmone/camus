package camus.gui;

import camus.core.GofBoard;
import camus.core.GcgBoard;
import camus.gui.FileHandler;
import gof.core.Board;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.sound.midi.InvalidMidiDataException;

import camus.music.Nota;
import camus.music.Sintetizzatore;
import camus.music.Spartito;
import camus.music.Strumento;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import junit.framework.TestFailure;

public class Controller implements Initializable {

	private final int    DEFAULT_SIZE = 15;
	private final double DEFAULT_PROB = 0.3;
	private final int DEFAULT_POSSIBLE_STATE = 6;
	private final int MIN_ITERATION = 2;

	@FXML
	private FlowPane baseGof;
	@FXML
	private FlowPane baseGcg;
	@FXML
	private FlowPane baseGof1;
	@FXML
	private FlowPane baseGcg1;
	@FXML
	private FlowPane baseGof2;
	@FXML
	private FlowPane baseGcg2;
	@FXML
	private FlowPane baseGof3;
	@FXML
	private FlowPane baseGcg3;
	@FXML
	private Label strumentoLabel;
	@FXML
	private Label strumento1Label;
	@FXML
	private Label strumento2Label;
	@FXML
	private Label strumento3Label;
	@FXML
	private Label timeLabel, iterLabel;
	@FXML
	private Slider countSlider;
	@FXML
	private HBox presetBox;
	@FXML
	private Button openButton, saveButton, openPresetBtn;
	@FXML
	private Button runButton, stopButton, playButton, stopMusicButton, pauseMusicButton, settingsButton, resetButton;
	@FXML
	private HBox rootBox;

	private GofBoard[] gofBoards;
	private GcgBoard[] gcgBoards;

	private GofBoard[] gofBoardsInitial;
	private GcgBoard[] gcgBoardsInitial;
	int num = 16;
	int bpm = 60;
	private int state = 0;
	
	private JavaFXDisplayDriver display;

	private Timeline loop = null;
	private Timeline loopMusic = null;
	private int time = 0;
	private int iterCount = 0;

	private int windowWidth = 750;
	private int cellSizePx = 5;

	private ArrayList<Strumento> orchestra = new ArrayList <Strumento>();
	private Spartito[] spartiti;
	HashMap<String, int[][]> mappaScale;
	HashMap<String, int[]> mappaRepeat;
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
		mappaScale = new HashMap<String, int[][]>();
		mappaRepeat = new HashMap<String, int[]>();
		defineOrchestra();
		strumentoLabel.setText(orchestra.get(0).getName());
		if(orchestra.size() > 1) strumento1Label.setText(orchestra.get(1).getName()); else strumento1Label.setText("[Null]");
		if(orchestra.size() > 2) strumento2Label.setText(orchestra.get(2).getName()); else strumento1Label.setText("[Null]");
		if(orchestra.size() > 3) strumento3Label.setText(orchestra.get(3).getName()); else strumento1Label.setText("[Null]");
		try {
			sint = new Sintetizzatore(orchestra);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for(int i = 0; i < num; i++){
			createBoardGof(i, DEFAULT_SIZE, DEFAULT_PROB);
			createBoardGcg(i, DEFAULT_SIZE, DEFAULT_POSSIBLE_STATE);
			spartiti[i] = new Spartito(orchestra.get(i));
		}
		gofBoardsInitial = new GofBoard[num];
		gcgBoardsInitial = new GcgBoard[num];
		for(int i = 0; i < num; i++){
			gofBoardsInitial[i] = new GofBoard(gofBoards[i]);
			gcgBoardsInitial[i] = new GcgBoard(gcgBoards[i]);
		}
		createDisplay();
		timeLabel.setText(new Integer(time).toString());
		iterLabel.setText(new Integer(iterCount).toString());
		state = 0;
		changeState(state);
	}
	
	public void defineOrchestra(){
		//Strumento(String name, int strumentIndex, double lunghezzaNota, int gapQuartina, boolean sincr
		
		
		int[][] eMinorPentatonic = {{40, 43, 45, 47, 50, 52, 55, 57, 59, 62, 64, 67, 69, 71, 74, 76}};//, 79, 81, 83, 86, 88, 91};
		mappaScale.put("eMinorPentatonic", eMinorPentatonic);
		//GO WELL TOGETHER
		int[][] cMajor = {{48, 50, 52, 53, 55, 57, 59, 60}};
		mappaScale.put("cMajor", cMajor);
		int[][] armonyC = { {36, 40, 43}, {38, 41, 45}, {40, 43, 47}, {41, 45, 48}, {43, 47, 50}, {45, 48, 52}, {47, 50, 53}};
		mappaScale.put("armonyC", armonyC);
		int[][] aMinorPentatonic = {{45, 48, 50, 52, 55, 57, 60, 62, 67, 69}};
		mappaScale.put("aMinorPentatonic", aMinorPentatonic);

		//GO WELL TOGETHER
		int[][] aMinScale = {{45, 47, 48, 50, 52, 53, 56}};
		mappaScale.put("aMinScale", aMinScale);
		int[][] aMinArmony = {{33, 36, 40}, {35, 38, 41}, {36, 40, 44}, {38, 41, 45}, {40, 44, 47}, {41, 45, 48}, {44, 47, 50}};
		mappaScale.put("aMinArmony", aMinArmony);

		//int[] pianoBase = {/*31, 33, 35,*/ 38, 40, 42, 43, 45, 47, 48, 50, 52, 54, 55};
		int[][] scalaViolino = {{50, 52, 55, 57, 59, 62, 64, 67, 69, 71, 74}};
		mappaScale.put("scalaViolino", scalaViolino);
		int[][] drums = {{60}};
		mappaScale.put("drums", drums);
		int[] repeatNull = null;
		mappaRepeat.put("[Null]", repeatNull);
		int[] repeat1 = {1};
		mappaRepeat.put("1", repeat1);
		int[] repeat12 = {1, 2};
		mappaRepeat.put("1, 2", repeat12);
		int[] repeat123 = {1, 2, 3};
		mappaRepeat.put("1, 2, 3", repeat123);

		Strumento s;   
		s = new Strumento("Piano 1", 1, 1, 0, true);
		s.setLunghezzaGcg(0);
		s.setQuartina(64);
		s.setOttava(12);
		s.setInizio(512);
		s.setRepeat(mappaRepeat.get("1, 2"), "1, 2");
		s.setScala(mappaScale.get("armonyC"), "armonyC");
		s.setForzaOn(40);
		orchestra.add(s);
		/*FLAMENCO*/
		s = new Strumento("Kalimba", 108, 1, 0, true);
		s.setScala(mappaScale.get("drums"), "drums");
		s.setLunghezzaGcg(0);
		s.setForzaOn(40);
		orchestra.add(s);

		s = new Strumento("Piano 1", 1, 1, 0, false);
		s.setScala(mappaScale.get("cMajor"), "cMajor");
		s.setForzaOn(200);
		s.setInizio(512);
		s.setContinuaPer(512);
		s.setPausa(512);
		s.setRipetiPer(10);
		s.setOttava(12);
		orchestra.add(s);

		s = new Strumento("Soprano Sax", 64, 1, 0, false);
		s.setScala(mappaScale.get("cMajor"), "cMajor");
		s.setInizio(1024);
		s.setContinuaPer(512);
		s.setPausa(512);
		s.setRipetiPer(10);
		s.setForzaOn(100);
		//s.setOttava(12);
		orchestra.add(s);
		
		for(int i = 0; i < 16; i++){
			s = new Strumento("[Null]", 0, 0, 0);
			//s.setContinuaPer(-1);
			s.setRipetiPer(0);
			orchestra.add(s);
		}
		/*CLASSICA*/
		s = new Strumento("pistola", 128, 1, 0, true);
		s.setScala(mappaScale.get("drums"), "drums");
		s.setForzaOn(100);
		orchestra.add(s);

		s = new Strumento("Piano 1", 1, 1, 0, true);
		s.setScala(mappaScale.get("armonyC"), "armonyC");
		s.setForzaOn(500);
		s.setOttava(12);
		orchestra.add(s);
		s = new Strumento("Violin", 1, 4, 0, false);    
		s.setScala(mappaScale.get("cMajor"), "cMajor");
		//s.setScala(aMinorPentatonic);
		s.setForzaOn(500);
		s.setOttava(12);
		orchestra.add(s);
		s = new Strumento("Piano 1", 1, 4, 0, false);
		s.setForzaOn(500);
		s.setOttava(3);
		orchestra.add(s);
		s = new Strumento("Violin", 40, 1, 0, true);
		s.setForzaOn(80);
		orchestra.add(s);


		s = new Strumento("Violin", 40, 1, 32, true);
		s.setLunghezzaGcg(0);
		s.setScala(mappaScale.get("scalaViolino"), "scalaViolino");
		orchestra.add(s);



		/*AFRICA*/
		s = new Strumento("Woodblock", 115, 1.8, 0, true);
		s.setScala(mappaScale.get("drums"), "drums");
		orchestra.add(s);
		s = new Strumento("Taiko", 116, 1, 0, false);
		s.setScala(mappaScale.get("eMinorPentatonic"), "eMinorPentatonic");
		orchestra.add(s);
		s = new Strumento("Nylon-str.Gt", 24, 1.8, 0, false);
		orchestra.add(s);

		/*ASIATICA*/
		s = new Strumento("Pan Flute", 75, 1.8, 0, true);
		s.setScala(mappaScale.get("cMajor"), "cMajor");
		s.setOttava(12);
		orchestra.add(s);
		s = new Strumento("Shamisen", 106, 1.8, 0, false);
		orchestra.add(s);

		/*BO*/
		s = new Strumento("Piano 1", 1, 1, 0, true);
		s.setScala(mappaScale.get("scalaViolino"), "scalaViolino");
		orchestra.add(s);
		s = new Strumento("Fingered Bs.", 33, 2, 0, true);
		//s.setOttava(-12);
		orchestra.add(s);
		s = new Strumento("Gt.Harmonics", 31, 0.7, 0, false);
		s.setOttava(-12);
		orchestra.add(s);
		s = new Strumento("Piano 1", 1, 1, 0, true);
		orchestra.add(s);
		s = new Strumento("Voice Oohs", 53, 4, 1, true);
		orchestra.add(s);

		s = new Strumento("Violin", 40, 4, 0, false);
		orchestra.add(s);
		s = new Strumento("Violin", 40, 4, 0, false);
		orchestra.add(s);
		s = new Strumento("Xylophone", 13, 1, 0, true);
		orchestra.add(s);
		s = new Strumento("Trumpet", 56, 2, 0);
		orchestra.add(s);

		for(int i = 0 ; i < orchestra.size(); i++){
			orchestra.get(i).setOrchestraIndex(i);
//			System.out.println("nomeStrumento: " + orchestra.get(i).getName() + " orchestraIndex: " + orchestra.get(i).getOrchestraIndex());
		}
	}
	
	@FXML
	private void onReset(Event evt) {
		state = 0;
		changeState(state);
		iterCount = 0;
		iterLabel.setText(new Integer(iterCount).toString());
		
		for(int i = 0; i < num; i++){
			gofBoards[i] = new GofBoard(gofBoardsInitial[i]);
			gcgBoards[i] = new GcgBoard(gcgBoardsInitial[i]);
			spartiti[i] = new Spartito(orchestra.get(i));
		}
		display.displayBoardGof(gofBoards[0]);
		if(gofBoards.length > 1) display.displayBoardGof1(gofBoards[1]);
		if(gofBoards.length > 2) display.displayBoardGof2(gofBoards[2]);
		if(gofBoards.length > 3) display.displayBoardGof3(gofBoards[3]);
		display.displayBoardGcg(gcgBoards[0]);
		if(gcgBoards.length > 1) display.displayBoardGcg1(gcgBoards[1]);
		if(gcgBoards.length > 2) display.displayBoardGcg2(gcgBoards[2]);
		if(gcgBoards.length > 3) display.displayBoardGcg3(gcgBoards[3]);
	}
	
	@FXML
	private void onRun(Event evt) {
		//toggleButtons(false);
		if(state == 0 || state == 2)
			state = 1;
		if(state == 4)
			state = 5;
		changeState(state);

		loop = new Timeline(new KeyFrame(Duration.millis(300), e -> {
			for(int i = 0; i < num; i++){
				gofBoards[i].update();
				gcgBoards[i].update();
				spartiti[i].estrazione(gofBoards[i], gcgBoards[i]);
			}
			iterCount++;
			iterLabel.setText(new Integer(iterCount).toString());
			display.displayBoardGof(gofBoards[0]);
			if(gofBoards.length > 1) display.displayBoardGof1(gofBoards[1]);
			if(gofBoards.length > 2) display.displayBoardGof2(gofBoards[2]);
			if(gofBoards.length > 3) display.displayBoardGof3(gofBoards[3]);
			display.displayBoardGcg(gcgBoards[0]);
			if(gcgBoards.length > 1) display.displayBoardGcg1(gcgBoards[1]);
			if(gcgBoards.length > 2) display.displayBoardGcg2(gcgBoards[2]);
			if(gcgBoards.length > 3) display.displayBoardGcg3(gcgBoards[3]);
		}));
		
		//gofBoardsInitial = new GofBoard[1];
		//gcgBoardsInitial = new GcgBoard[1];
		if(iterCount == 0){
			for(int i = 0; i < MIN_ITERATION; i++){
				for(int j=0; j<num; j++){
					gofBoards[j].update();
					//display.displayBoardGof(gofBoards[j]);
					gcgBoards[j].update();
					//display.displayBoardGcg(gcgBoards[j]);
				}
				iterCount++;
				iterLabel.setText(new Integer(iterCount).toString());
			}
		}
		
		loop.setCycleCount(100);
		loop.play();
	}

	@FXML
	private void onStop(Event evt) throws InterruptedException {
		//toggleButtons(true);
		if(state == 1)
			state = 2;
		if(state == 5)
			state = 4;
		changeState(state);
		
		loop.stop();
		//gofBoards[0] = new GofBoard(gofBoardsInitial[0]);

		for(int i = 0; i < num ; i++){
			spartiti[i].translate();
		}
		time = 0;
		timeLabel.setText(new Integer(time).toString());
		//spartiti[0].printFlow(1000);
		//spartiti[1].printFlow(1000);

		//spartiti[0].printList();

		//sint.play(spartito);
		//sint.play(spartito.getSpartito().get(0).get(0));
	}

	@FXML
	private void onPauseMusic(Event evt) {
		/*createBoardGof(0, DEFAULT_SIZE, 0);
        createBoardGcg(0, DEFAULT_SIZE, 10);
		toggleButtonsMusic(true);*/
		state = 4;
		changeState(state);
		loopMusic.stop();
		sint.stopMusic();
	}
	
	@FXML
	private void onStopMusic(Event evt) {
		/*createBoardGof(0, DEFAULT_SIZE, 0);
        createBoardGcg(0, DEFAULT_SIZE, 10);
		toggleButtonsMusic(true);*/
		state = 4;
		changeState(state);
		loopMusic.stop();
		sint.stopMusic();
		time = 0;
		timeLabel.setText(new Integer(time).toString());
	}

	@FXML
	private void onPlay(Event evt) throws InterruptedException {
		/*createBoardGof(DEFAULT_SIZE, (double) countSlider.getValue()/100);
        createBoardGcg(DEFAULT_SIZE, 10);*/

		//sint.playFlow(spartiti, 60);
		//toggleButtonsMusic(false);
		state = 3;
		changeState(state);
		int quartina = spartiti[0].getStrumento().getQuartina();
		int spacing = 60000/bpm/quartina;

		loopMusic = new Timeline(new KeyFrame(Duration.millis(spacing), e -> {
			try {
				sint.playFlowOnce(spartiti, bpm, time);
				time++;
				timeLabel.setText(new Integer(time).toString());
			} catch (Exception e1) {

				e1.printStackTrace();
			}
		}));

		loopMusic.setCycleCount(spartiti[0].getFlow().size());
		loopMusic.play();
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

		gofBoards[0].update();
		display.displayBoardGof(gofBoards[0]);
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
	private void onSettings(Event evt) {
		double width = 50.0;
		
		Strumento[] strumentiTemp = new Strumento[num];
		for(int i = 0; i < num; i++){
			strumentiTemp[i] = new Strumento(spartiti[i].getStrumento());
		}
		
		// TEXT //
		Text text1 = new Text("Settaggio strumenti\n");
		text1.setFont(Font.font(30));
		Text text2 = new Text(
				"\nScegli i parametri e premi OK.");
		TextFlow tf = new TextFlow(text1,text2);
		tf.setPadding(new Insets(10, 10, 10, 10));
		tf.setTextAlignment(TextAlignment.JUSTIFY);
		// END TEXT, START WINDOW //
		final Stage dialog = new Stage();
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(new Stage());
		VBox dialogVbox = new VBox(20);
		dialogVbox.getChildren().add(tf);
		
		HBox bpmHBox = new HBox();
		Label bpmLabel = new Label("BPM:");
		TextField textBpm = new TextField(new Integer(bpm).toString());
		textBpm.setId("textBpm");
		textBpm.setMaxWidth(width);
		bpmHBox.getChildren().add(bpmLabel);
		bpmHBox.getChildren().add(textBpm);
		Button salvaSet = new Button("Salva parametri su file");
		salvaSet.setTranslateX(20);
		Button apriSet = new Button("Apri parametri da file");
		apriSet.setTranslateX(20);
		bpmHBox.getChildren().add(salvaSet);
		bpmHBox.getChildren().add(apriSet);
		bpmHBox.setSpacing(10);
		
		//Defining the Submit button
		Button submit = new Button("OK");
		submit.setTranslateX(50);
		bpmHBox.getChildren().add(submit);
		Label debug = new Label("debug OK");
		debug.setTranslateX(50);
		//bpmHBox.getChildren().add(debug);
		
		dialogVbox.getChildren().add(bpmHBox);
		
		ScrollPane sp = new ScrollPane();
		ObservableList<String> strumenti = 
				FXCollections.observableArrayList(
						sint.listOfInstruments().keySet()
						);
		ObservableList<String> veroFalso = 
				FXCollections.observableArrayList(
							"Sì",
							"No"
						);
		ObservableList<String> listaScale = 
				FXCollections.observableArrayList(
							mappaScale.keySet()
						);
		ObservableList<String> listaRepeat = 
				FXCollections.observableArrayList(
							mappaRepeat.keySet()
						);
		
		Strumento s;
		for(int i = 0; i < num; i++){
			
			s = strumentiTemp[i];
			
			HBox hb = new HBox();
			HBox hb1 = new HBox();
			
			VBox indexVBox = new VBox();
			Label indexLabel = new Label("Strumento " + (i+1));
			indexLabel.setFont(Font.font(22));
			ComboBox comboStrum = new ComboBox(strumenti);
			comboStrum.setId("comboStrum" + i);
			comboStrum.setValue(s.getName());
			indexVBox.getChildren().add(indexLabel);
			indexVBox.getChildren().add(comboStrum);
			
			VBox sincrVBox = new VBox();
			Label sincrLabel = new Label("Sincroniz.");
			ComboBox comboSincr = new ComboBox(veroFalso);
			comboSincr.setId("comboSincr" + i);
			if(s.isSicronizzazione())
				comboSincr.setValue("Sì");
			else
				comboSincr.setValue("No");
			sincrVBox.getChildren().add(sincrLabel);
			sincrVBox.getChildren().add(comboSincr);
			
			VBox scaleVBox = new VBox();
			Label scaleLabel = new Label("Scala");
			ComboBox comboScale = new ComboBox(listaScale);
			comboScale.setId("comboScale" + i);
			comboScale.setValue(s.getNomeScala());
			scaleVBox.getChildren().add(scaleLabel);
			scaleVBox.getChildren().add(comboScale);
			
			VBox ottavaVBox = new VBox();
			Label ottavaLabel = new Label("Ottava");
			TextField textOttava = new TextField(new Integer(s.getOttava()).toString());
			textOttava.setId("textOttava" + i);
			textOttava.setMaxWidth(width);
			ottavaVBox.getChildren().add(ottavaLabel);
			ottavaVBox.getChildren().add(textOttava);
			
			VBox forzaVBox = new VBox();
			Label forzaLabel = new Label("Forza");
			TextField textForza = new TextField(new Integer(s.getForzaOn()).toString());
			textForza.setId("textForza" + i);
			textForza.setMaxWidth(width);
			forzaVBox.getChildren().add(forzaLabel);
			forzaVBox.getChildren().add(textForza);
			
			VBox inizioVBox = new VBox();
			Label inizioLabel = new Label("Inizio");
			TextField textInizio = new TextField(new Integer(s.getInizio()).toString());
			textInizio.setId("textInizio" + i);
			textInizio.setMaxWidth(width + 15);
			inizioVBox.getChildren().add(inizioLabel);
			inizioVBox.getChildren().add(textInizio);
			
			VBox continuaVBox = new VBox();
			Label continuaLabel = new Label("Continua per");
			TextField textContinua = new TextField(new Integer(s.getContinuaPer()).toString());
			textContinua.setId("textContinua" + i);
			textContinua.setMaxWidth(width + 15);
			continuaVBox.getChildren().add(continuaLabel);
			continuaVBox.getChildren().add(textContinua);
			
			VBox pausaVBox = new VBox();
			Label pausaLabel = new Label("Pausa");
			TextField textPausa = new TextField(new Integer(s.getPausa()).toString());
			textPausa.setId("textPausa" + i);
			textPausa.setMaxWidth(width + 15);
			pausaVBox.getChildren().add(pausaLabel);
			pausaVBox.getChildren().add(textPausa);
			
			VBox ripetiPerVBox = new VBox();
			Label ripetiPerLabel = new Label("Ripeti per");
			TextField textRipetiPer = new TextField(new Integer(s.getRipetiPer()).toString());
			textRipetiPer.setId("textRipetiPer" + i);
			textRipetiPer.setMaxWidth(width);
			ripetiPerVBox.getChildren().add(ripetiPerLabel);
			ripetiPerVBox.getChildren().add(textRipetiPer);
			
			VBox quartinaVBox = new VBox();
			Label quartinaLabel = new Label("Quartina");
			TextField textQuartina = new TextField(new Integer(s.getQuartina()).toString());
			textQuartina.setId("textQuartina" + i);
			textQuartina.setMaxWidth(width);
			quartinaVBox.getChildren().add(quartinaLabel);
			quartinaVBox.getChildren().add(textQuartina);

			VBox lungNotaVBox = new VBox();
			Label lungNotaLabel = new Label("Lunghezza\nnota statica");
			TextField textLungNota = new TextField(new Double(s.getLunghezzaNota()).toString());
			textLungNota.setId("textLungNota" + i);
			textLungNota.setMaxWidth(width);
			lungNotaVBox.getChildren().add(lungNotaLabel);
			lungNotaVBox.getChildren().add(textLungNota);
			
			VBox lungGcgVBox = new VBox();
			Label lungGcgLabel = new Label("Lunghezza\nnota dinamica");
			TextField textLungGcg = new TextField(new Double(s.getLunghezzaGcg()).toString());
			textLungGcg.setId("textLungGcg" + i);
			textLungGcg.setMaxWidth(width);
			lungGcgVBox.getChildren().add(lungGcgLabel);
			lungGcgVBox.getChildren().add(textLungGcg);
			
			VBox delayVBox = new VBox();
			Label delayLabel = new Label("Delay");
			TextField textDelay = new TextField(new Double(s.getDelay()).toString());
			textDelay.setId("textDelay" + i);
			textDelay.setMaxWidth(width);
			delayVBox.getChildren().add(delayLabel);
			delayVBox.getChildren().add(textDelay);
			
			VBox gapQuartinaVBox = new VBox();
			Label gapQuartinaLabel = new Label("Gap quartina");
			TextField textGapQuartina = new TextField(new Integer(s.getGapQuartina()).toString());
			textGapQuartina.setId("textGapQuartina" + i);
			textGapQuartina.setMaxWidth(width);
			gapQuartinaVBox.getChildren().add(gapQuartinaLabel);
			gapQuartinaVBox.getChildren().add(textGapQuartina);
			
			VBox repeatVBox = new VBox();
			Label repeatLabel = new Label("Repeat");
			ComboBox comboRepeat = new ComboBox(listaRepeat);
			comboRepeat.setId("comboRepeat" + i);
			comboRepeat.setValue(s.getNomeRepeat());
			repeatVBox.getChildren().add(repeatLabel);
			repeatVBox.getChildren().add(comboRepeat);
			
			hb.getChildren().addAll(indexVBox, sincrVBox, scaleVBox, ottavaVBox, forzaVBox, inizioVBox, continuaVBox, pausaVBox, ripetiPerVBox, quartinaVBox);
			hb.setSpacing(10);
			
			hb1.getChildren().addAll(lungNotaVBox, lungGcgVBox, delayVBox, gapQuartinaVBox, repeatVBox);
			hb1.setTranslateX(135);
			hb1.setTranslateY(-27.5);
			hb1.setSpacing(10);
			
			dialogVbox.getChildren().add(hb);
			dialogVbox.getChildren().add(hb1);
		}
		dialogVbox.setTranslateX(10);
		
		//Setting an action for the Submit button
		submit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
		    public void handle(ActionEvent e) {
				try {
					boolean change = false;
					String name;
					Integer index;
					int[][] scala;
					int[] repeat;
					
					TextField textBpm = (TextField) dialogVbox.lookup("#textBpm");
					if(textBpm != null){
						name = textBpm.getText();
						if(name != null && !name.equals("")){
							Integer bmp = new Integer(name);
							if(bmp != null)
								bpm = bmp.intValue();
						}
					}
					System.out.println("MAPPA TO STRING:");
					System.out.println(sint.listOfInstruments().toString());
					for(int i = 0; i < num; i++){
						ComboBox comboStrum = (ComboBox) dialogVbox.lookup("#comboStrum" + i);
						if(comboStrum != null){
							name = comboStrum.getValue().toString();
							index = sint.listOfInstruments().get(name);
							if(index != null){
								System.out.println(i + "  name: " + name + " index: " + index.intValue());
								spartiti[i].getStrumento().setName(name);
								spartiti[i].getStrumento().setStrumentIndex(index.intValue());
															
								orchestra.get(i).setName(name);
								orchestra.get(i).setStrumentIndex(index.intValue());
								if(i == 0)
									strumentoLabel.setText(name);
								if(i == 1)
									strumento1Label.setText(name);
								if(i == 2)
									strumento2Label.setText(name);
								if(i == 3)
									strumento3Label.setText(name);
								
								sint.changeChannel(i, index.intValue());
								change = true;
							}else
								System.out.println(i + "  name: " + name + " index: NULL");
						}
						
						ComboBox comboSincr = (ComboBox) dialogVbox.lookup("#comboSincr" + i);
						if(comboSincr != null){
							name = comboSincr.getValue().toString();
							if(name != null && !name.equals(""))
								if(name.equalsIgnoreCase("No"))
									spartiti[i].getStrumento().setSicronizzazione(false);
								else
									spartiti[i].getStrumento().setSicronizzazione(true);
						}
						
						ComboBox comboScale = (ComboBox) dialogVbox.lookup("#comboScale" + i);
						if(comboScale != null){
							name = comboScale.getValue().toString();
							scala = mappaScale.get(name);
							if(scala != null)
								spartiti[i].getStrumento().setScala(scala, name);
						}
						
						TextField textOttava = (TextField) dialogVbox.lookup("#textOttava" + i);
						if(textOttava != null){
							name = textOttava.getText();
							if(name != null && !name.equals("")){
								Integer ottava = new Integer(name);
								if(ottava != null)
									spartiti[i].getStrumento().setOttava(ottava.intValue());
							}
						}
						
						TextField textForza = (TextField) dialogVbox.lookup("#textForza" + i);
						if(textForza != null){
							name = textForza.getText();
							if(name != null && !name.equals("")){
								Integer forza = new Integer(name);
								if(forza != null)
									spartiti[i].getStrumento().setForzaOn(forza.intValue());
							}
						}
						
						TextField textInizio = (TextField) dialogVbox.lookup("#textInizio" + i);
						if(textInizio != null){
							name = textInizio.getText();
							if(name != null && !name.equals("")){
								Integer inizio = new Integer(name);
								if(inizio != null)
									spartiti[i].getStrumento().setInizio(inizio.intValue());
							}
						}

						TextField textContinua = (TextField) dialogVbox.lookup("#textContinua" + i);
						if(textContinua != null){
							name = textContinua.getText();
							if(name != null && !name.equals("")){
								Integer continua = new Integer(name);
								if(continua != null)
									spartiti[i].getStrumento().setContinuaPer(continua.intValue());
							}
						}
						
						TextField textPausa = (TextField) dialogVbox.lookup("#textPausa" + i);
						if(textPausa != null){
							name = textPausa.getText();
							if(name != null && !name.equals("")){
								Integer pausa = new Integer(name);
								if(pausa != null)
									spartiti[i].getStrumento().setPausa(pausa.intValue());
							}
						}
						
						TextField textRipetiPer = (TextField) dialogVbox.lookup("#textRipetiPer" + i);
						if(textRipetiPer != null){
							name = textRipetiPer.getText();
							if(name != null && !name.equals("")){
								Integer ripetiPer = new Integer(name);
								if(ripetiPer != null)
									spartiti[i].getStrumento().setRipetiPer(ripetiPer.intValue());
							}
						}
						
						TextField textQuartina = (TextField) dialogVbox.lookup("#textQuartina" + i);
						if(textQuartina != null){
							name = textQuartina.getText();
							if(name != null && !name.equals("")){
								Integer quartina = new Integer(name);
								if(quartina != null)
									spartiti[i].getStrumento().setQuartina(quartina.intValue());
							}
						}
						
						TextField textLungNota = (TextField) dialogVbox.lookup("#textLungNota" + i);
						if(textLungNota != null){
							name = textLungNota.getText();
							if(name != null && !name.equals("")){
								Double lungNota = new Double(name);
								if(lungNota != null)
									spartiti[i].getStrumento().setLunghezzaNota(lungNota.doubleValue());
							}
						}
						
						TextField textLungGcg = (TextField) dialogVbox.lookup("#textLungGcg" + i);
						if(textLungGcg != null){
							name = textLungGcg.getText();
							if(name != null && !name.equals("")){
								Double lungGcg = new Double(name);
								if(lungGcg != null)
									spartiti[i].getStrumento().setLunghezzaGcg(lungGcg.doubleValue());
							}
						}
						
						TextField textDelay = (TextField) dialogVbox.lookup("#textDelay" + i);
						if(textDelay != null){
							name = textDelay.getText();
							if(name != null && !name.equals("")){
								Double delay = new Double(name);
								if(delay != null)
									spartiti[i].getStrumento().setDelay(delay.doubleValue());
							}
						}
						
						TextField textGapQuartina = (TextField) dialogVbox.lookup("#textGapQuartina" + i);
						if(textGapQuartina != null){
							name = textGapQuartina.getText();
							if(name != null && !name.equals("")){
								Integer gapQuartina = new Integer(name);
								if(gapQuartina != null)
									spartiti[i].getStrumento().setGapQuartina(gapQuartina.intValue());
							}
						}
						
						ComboBox comboRepeat = (ComboBox) dialogVbox.lookup("#comboRepeat" + i);
						if(comboRepeat != null){
							name = comboRepeat.getValue().toString();
							repeat = mappaRepeat.get(name);
							spartiti[i].getStrumento().setRepeat(repeat, name);
						}
					}
//					if(change)
//						sint = new Sintetizzatore(orchestra);
				}catch (Exception e1) {
					e1.printStackTrace();
					debug.setText(e1.getMessage());
				}finally{
					dialog.close();
				}
		     }
		 });
		 
		//Setting an action for the Clear button
		/*clear.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		    public void handle(ActionEvent e) {
		        name.clear();
		        lastName.clear();
		        comment.clear();
		        label.setText(null);
		    }
		});*/
		
		salvaSet.setOnAction(new EventHandler<ActionEvent>() {

			@Override
		    public void handle(ActionEvent e) {
				try {
					FileHandler.saveSettingsToFile(strumentiTemp);
				}catch (Exception e1) {
					e1.printStackTrace();
					debug.setText(e1.getMessage());
				}
			}
		});
		apriSet.setOnAction(new EventHandler<ActionEvent>() {

			@Override
		    public void handle(ActionEvent e) {
				try {
					FileHandler.openSettingsFromFile(strumentiTemp, mappaScale, mappaRepeat);
					for(int i = 0; i < num; i++){
						ComboBox comboStrum = (ComboBox) dialogVbox.lookup("#comboStrum" + i);
						comboStrum.setValue(strumentiTemp[i].getName());
						
						ComboBox comboSincr = (ComboBox) dialogVbox.lookup("#comboSincr" + i);
						if(strumentiTemp[i].isSicronizzazione())
							comboSincr.setValue("Sì");
						else
							comboSincr.setValue("No");
						
						ComboBox comboScale = (ComboBox) dialogVbox.lookup("#comboScale" + i);
						comboScale.setValue(strumentiTemp[i].getNomeScala());
						
						TextField textOttava = (TextField) dialogVbox.lookup("#textOttava" + i);
						textOttava.setText(new Integer(strumentiTemp[i].getOttava()).toString());
						
						TextField textForza = (TextField) dialogVbox.lookup("#textForza" + i);
						textForza.setText(new Integer(strumentiTemp[i].getForzaOn()).toString());
						
						TextField textInizio = (TextField) dialogVbox.lookup("#textInizio" + i);
						textInizio.setText(new Integer(strumentiTemp[i].getInizio()).toString());

						TextField textContinua = (TextField) dialogVbox.lookup("#textContinua" + i);
						textContinua.setText(new Integer(strumentiTemp[i].getContinuaPer()).toString());
						
						TextField textPausa = (TextField) dialogVbox.lookup("#textPausa" + i);
						textPausa.setText(new Integer(strumentiTemp[i].getPausa()).toString());
						
						TextField textRipetiPer = (TextField) dialogVbox.lookup("#textRipetiPer" + i);
						textRipetiPer.setText(new Integer(strumentiTemp[i].getRipetiPer()).toString());
						
						TextField textQuartina = (TextField) dialogVbox.lookup("#textQuartina" + i);
						textQuartina.setText(new Integer(strumentiTemp[i].getQuartina()).toString());
						
						TextField textLungNota = (TextField) dialogVbox.lookup("#textLungNota" + i);
						textLungNota.setText(new Double(strumentiTemp[i].getLunghezzaNota()).toString());
						
						TextField textLungGcg = (TextField) dialogVbox.lookup("#textLungGcg" + i);
						textLungGcg.setText(new Double(strumentiTemp[i].getLunghezzaGcg()).toString());
						
						TextField textDelay = (TextField) dialogVbox.lookup("#textDelay" + i);
						textDelay.setText(new Double(strumentiTemp[i].getDelay()).toString());
						
						TextField textGapQuartina = (TextField) dialogVbox.lookup("#textGapQuartina" + i);
						textGapQuartina.setText(new Integer(strumentiTemp[i].getGapQuartina()).toString());
						
						ComboBox comboRepeat = (ComboBox) dialogVbox.lookup("#comboRepeat" + i);
						comboRepeat.setValue(strumentiTemp[i].getNomeRepeat());
					}
				}catch (Exception e1) {
					e1.printStackTrace();
					debug.setText(e1.getMessage());
				}
			}
		});
		sp.setContent(dialogVbox);
		Scene dialogScene = new Scene(sp, 900, 650);
		dialog.setScene(dialogScene);
		dialog.show();
		// END WINDOW //
	}
	
	private void changeState(int state){
		switch(state){
			case 0:
				setAllDisabled(false);
				stopButton.setDisable(true);
				resetButton.setDisable(true);
				playButton.setDisable(true);
				pauseMusicButton.setDisable(true);
				stopMusicButton.setDisable(true);
				break;
			case 1: case 5:
				setAllDisabled(true);
				stopButton.setDisable(false);
				break;
			case 2:
				setAllDisabled(false);
				stopButton.setDisable(true);
				pauseMusicButton.setDisable(true);
				stopMusicButton.setDisable(true);
				break;
			case 3:
				setAllDisabled(true);
				pauseMusicButton.setDisable(false);
				stopMusicButton.setDisable(false);
				break;
			case 4:
				setAllDisabled(false);
				stopButton.setDisable(true);
				pauseMusicButton.setDisable(true);
				break;
			default:
				setAllDisabled(false);
				break;
		}
	}
	
	private void setAllDisabled(boolean bool) {
//		presetBox.setDisable(bool);
//		openButton.setDisable(bool);
//		openPresetBtn.setDisable(bool);
//		saveButton.setDisable(bool);
		runButton.setDisable(bool);
		playButton.setDisable(bool);
		stopMusicButton.setDisable(bool);
		pauseMusicButton.setDisable(bool);
		stopButton.setDisable(bool);
		resetButton.setDisable(bool);
		settingsButton.setDisable(bool);
	}

	private void toggleButtonsMusic(boolean enable) {
		presetBox.setDisable(!enable);
		openButton.setDisable(!enable);
		openPresetBtn.setDisable(!enable);
		saveButton.setDisable(!enable);
		runButton.setDisable(!enable);
		playButton.setDisable(!enable);
		stopButton.setDisable(!enable);

		stopMusicButton.setDisable(enable);
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
		display = new JavaFXDisplayDriver(gofBoards[0].getSize(), cellSizePx, gofBoards[0], gcgBoards[0], gofBoards[1], gcgBoards[1], gofBoards[2], gcgBoards[2], gofBoards[3], gcgBoards[3]);

		baseGof.getChildren().clear();
		baseGof.getChildren().add(new Group(display.getPaneGof()));
		baseGcg.getChildren().clear();
		baseGcg.getChildren().add(new Group(display.getPaneGcg()));
		baseGof1.getChildren().clear();
		baseGof1.getChildren().add(new Group(display.getPaneGof1()));
		baseGcg1.getChildren().clear();
		baseGcg1.getChildren().add(new Group(display.getPaneGcg1()));
		baseGof2.getChildren().clear();
		baseGof2.getChildren().add(new Group(display.getPaneGof2()));
		baseGcg2.getChildren().clear();
		baseGcg2.getChildren().add(new Group(display.getPaneGcg2()));
		baseGof3.getChildren().clear();
		baseGof3.getChildren().add(new Group(display.getPaneGof3()));
		baseGcg3.getChildren().clear();
		baseGcg3.getChildren().add(new Group(display.getPaneGcg3()));
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