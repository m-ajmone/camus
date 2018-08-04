package camus.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import camus.music.Spartito;
import camus.music.Strumento;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import gof.core.Board;
import gof.core.Cell;

public class FileHandler {

    public static Board openFromFile(int defaultSize) {
        File file = askForOpenFile();
        if (file == null) {
            return null;
        }
        
        return loadFromFile(file, defaultSize);
    }
    
    public static void openSettingsFromFile(Strumento[] strumenti, HashMap<String, int[][]> mappaScale, HashMap<String, int[]> mappaRepeat) {
        File file = askForOpenSettingsFile();
        
        loadFromSettingsFile(file, strumenti, mappaScale, mappaRepeat);
    }
    
    public static Board loadFromFile(File file, int defaultSize) {
        String input = "";
        int sz = defaultSize;
        try (Scanner s = new Scanner(file)) {
            while (s.hasNextLine()) {
                String line = s.nextLine().replaceAll("\\s+","");
                input += line;
                
                sz = line.length();
            }
        } catch (FileNotFoundException e) {
            // should never happen since we return on null file
            // so if we end up here it's something really bad 
            // and so we let it blow up to runtime
            throw new RuntimeException(e);
        }

        Cell[][] g = new Cell[sz][sz];

        int pos = 0;
        for (int i = 0; i < sz; i++) {
            for (int j = 0; j < sz; j++) {
                boolean state = (input.charAt(pos) =='1');
                g[i][j] = new Cell(state);
                pos++;
            }
        }

        return new Board(g);        
    }
    
    public static void loadFromSettingsFile(File file, Strumento[] strumenti, HashMap<String, int[][]> mappaScale, HashMap<String, int[]> mappaRepeat) {
        String line;
        String value;
        int i = 0;
        try (Scanner s = new Scanner(file)) {
            while (s.hasNextLine()) {
            	line = "";
                line = s.nextLine();
                
                value = "";
                value = line.substring(0, line.indexOf(';') + 1);
                value = value.replaceAll(";", "").trim();
                strumenti[i].setName(value);
                line = line.substring(line.indexOf(';') + 1);
                
                value = "";
                value = line.substring(0, line.indexOf(';') + 1);
                value = value.replaceAll(";", "").trim();
                strumenti[i].setStrumentIndex(new Integer(value).intValue());
                line = line.substring(line.indexOf(';') + 1);
                
                value = "";
                value = line.substring(0, line.indexOf(';') + 1);
                value = value.replaceAll(";", "").trim();
                strumenti[i].setSicronizzazione(new Boolean(value).booleanValue());
                line = line.substring(line.indexOf(';') + 1);
                
                value = "";
                value = line.substring(0, line.indexOf(';') + 1);
                value = value.replaceAll(";", "").trim();
                strumenti[i].setScala(mappaScale.get(value), value);
                line = line.substring(line.indexOf(';') + 1);
                
                value = "";
                value = line.substring(0, line.indexOf(';') + 1);
                value = value.replaceAll(";", "").trim();
                strumenti[i].setOttava(new Integer(value).intValue());
                line = line.substring(line.indexOf(';') + 1);
                
                value = "";
                value = line.substring(0, line.indexOf(';') + 1);
                value = value.replaceAll(";", "").trim();
                strumenti[i].setForzaOn(new Integer(value).intValue());
                line = line.substring(line.indexOf(';') + 1);
                
                value = "";
                value = line.substring(0, line.indexOf(';') + 1);
                value = value.replaceAll(";", "").trim();
                strumenti[i].setInizio(new Integer(value).intValue());
                line = line.substring(line.indexOf(';') + 1);
                
                value = "";
                value = line.substring(0, line.indexOf(';') + 1);
                value = value.replaceAll(";", "").trim();
                strumenti[i].setContinuaPer(new Integer(value).intValue());
                line = line.substring(line.indexOf(';') + 1);
                
                value = "";
                value = line.substring(0, line.indexOf(';') + 1);
                value = value.replaceAll(";", "").trim();
                strumenti[i].setPausa(new Integer(value).intValue());
                line = line.substring(line.indexOf(';') + 1);
                
                value = "";
                value = line.substring(0, line.indexOf(';') + 1);
                value = value.replaceAll(";", "").trim();
                strumenti[i].setRipetiPer(new Integer(value).intValue());
                line = line.substring(line.indexOf(';') + 1);
                
                value = "";
                value = line.substring(0, line.indexOf(';') + 1);
                value = value.replaceAll(";", "").trim();
                strumenti[i].setQuartina(new Integer(value).intValue());
                line = line.substring(line.indexOf(';') + 1);
                
                value = "";
                value = line.substring(0, line.indexOf(';') + 1);
                value = value.replaceAll(";", "").trim();
                strumenti[i].setLunghezzaNota(new Double(value).doubleValue());
                line = line.substring(line.indexOf(';') + 1);
                
                value = "";
                value = line.substring(0, line.indexOf(';') + 1);
                value = value.replaceAll(";", "").trim();
                strumenti[i].setLunghezzaGcg(new Double(value).doubleValue());
                line = line.substring(line.indexOf(';') + 1);
                
                value = "";
                value = line.substring(0, line.indexOf(';') + 1);
                value = value.replaceAll(";", "").trim();
                strumenti[i].setDelay(new Double(value).doubleValue());
                line = line.substring(line.indexOf(';') + 1);
                
                value = "";
                value = line.substring(0, line.indexOf(';') + 1);
                value = value.replaceAll(";", "").trim();
                strumenti[i].setGapQuartina(new Integer(value).intValue());
                line = line.substring(line.indexOf(';') + 1);
                
                value = "";
                value = line.substring(0, line.indexOf(';') + 1);
                value = value.replaceAll(";", "").trim();
                strumenti[i].setRepeat(mappaRepeat.get(value), value);
                line = line.substring(line.indexOf(';') + 1);
                
                i++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }  
    }
    
    public static void saveToFile(Board board) {
        File file = askForSaveFile();
        if (file == null) {
            return;
        }
        
        String output = ""; // string of numbers from board
        Cell[][] g = board.getGrid();
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[0].length; j++) {
                output+= g[i][j].getState() ? 1 : 0;
            }
            if (i != g.length-1){
                output+="\n";
            }
        }

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(output);
            fileWriter.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    public static void saveSettingsToFile(Strumento[] strumenti) {
        File file = askForSaveSettingsFile();
        if (file == null) {
            return;
        }
        
        String output = "";
        Strumento s;
        for(int i = 0; i < strumenti.length; i++){
        	s = strumenti[i];
        	output = output + s.getName().trim() + ";";
        	output = output + s.getStrumentIndex() + ";";
        	output = output + new Boolean(s.isSicronizzazione()).toString().trim() + ";";
        	output = output + s.getNomeScala().trim() + ";";
        	output = output + s.getOttava() + ";";
        	output = output + s.getForzaOn() + ";";
        	output = output + s.getInizio() + ";";
        	output = output + s.getContinuaPer() + ";";
        	output = output + s.getPausa() + ";";
        	output = output + s.getRipetiPer() + ";";
        	output = output + s.getQuartina() + ";";
        	output = output + s.getLunghezzaNota() + ";";
        	output = output + s.getLunghezzaGcg() + ";";
        	output = output + s.getDelay() + ";";
        	output = output + s.getGapQuartina() + ";";
        	output = output + s.getNomeRepeat().trim() + ";\n";
        }

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(output);
            fileWriter.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    private static File askForSaveFile() {
        return getFileChooser().showSaveDialog(new Stage());
    }
    
    private static File askForSaveSettingsFile() {
        return getFileChooserSettings().showSaveDialog(new Stage());
    }
    
    private static File askForOpenFile() {
        return getFileChooser().showOpenDialog(new Stage());
    }
    
    private static File askForOpenSettingsFile() {
        return getFileChooserSettings().showOpenDialog(new Stage());
    }
    
    private static FileChooser getFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Game of Life Board File");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("GOFB files (*.gofb)", "*.gofb"));
        
        return fileChooser;
    }
    
    private static FileChooser getFileChooserSettings() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("File Settaggio Strumenti");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Instruments Settings files (*.iset)", "*.iset"));
        
        return fileChooser;
    }
}
