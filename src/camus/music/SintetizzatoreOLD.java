package camus.music;

import java.util.ArrayList;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

public class SintetizzatoreOLD {
	private Synthesizer synthesizer;
    private final MidiChannel[] midiChannels;
    private final Instrument[] instruments;
    private static int[] orchestra = {24, 24, 24, 24};
    
    public SintetizzatoreOLD() {
        try {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
        } catch (MidiUnavailableException ex) {
            ex.printStackTrace();
            System.exit(1);
        }   

        this.midiChannels = synthesizer.getChannels();

        Soundbank bank = synthesizer.getDefaultSoundbank();

        synthesizer.loadAllInstruments(bank);

        this.instruments = synthesizer.getAvailableInstruments();
        synthesizer.loadAllInstruments(synthesizer.getDefaultSoundbank());
        synthesizer.getChannels()[0].programChange(0);

        System.out.println("[STATE] MIDI channels: " + midiChannels.length);
        System.out.println("[STATE] Instruments: " + instruments.length);
    }
    
    public void play(Spartito s) throws InterruptedException{
    	ArrayList<ArrayList<Nota>> spartito = s.getSpartito();
    	
    	int length = spartito.size();
		for (int i = 0; i < length; i++){
			/*int length2 = spartito.get(i).size();
			for (int j = 0; j < length2; j++){
				Nota nota = spartito.get(i).get(j);
				play(nota);
			}*/
			play(spartito.get(i));
			
		}
    }
    
    public void play(ArrayList<Nota> step) throws InterruptedException{
    	int length2 = step.size();
    	for (int j = 0; j < length2; j++){
			Nota nota = step.get(j);
			play(nota);
			//startNote(nota);
		}
    	
		/*for (int j = 0; j < length2; j++){
			Nota nota = step.get(j);
			//play(nota);
			startNote(nota);
		}
		Thread.sleep(400);
		for (int j = 0; j < length2; j++){
			Nota nota = step.get(j);
			//play(nota);
			stopNote(nota);
		}*/
    }
    
    public void play(Nota nota) throws InterruptedException{
    	int lower = 0;
    	int step = 1;
    	
    	synthesizer.getChannels()[0].programChange(orchestra[nota.getStrumento()]);
    	
    	midiChannels[0].noteOn(lower + (nota.getM()*step), 6);
    	Thread.sleep(100);
    	midiChannels[0].noteOn(lower + (nota.getB()*step), 6);
    	Thread.sleep(100);
    	midiChannels[0].noteOn(lower + (nota.getU()*step), 6);
    	Thread.sleep(100);
    	midiChannels[0].noteOff(lower + (nota.getM()*step), 6);
    	Thread.sleep(100);
    	midiChannels[0].noteOff(lower + (nota.getB()*step), 6);
    	Thread.sleep(100);
    	midiChannels[0].noteOff(lower + (nota.getU()*step), 6);
    }
    
    public void startNote(Nota nota) throws InterruptedException{
    	int lower = 0;
    	int step = 1;
    	
    	synthesizer.getChannels()[0].programChange(orchestra[nota.getStrumento()]);
    	
    	midiChannels[0].noteOn(lower + (nota.getM()*step), 6000);
    	midiChannels[0].noteOn(lower + (nota.getB()*step), 6000);
    	midiChannels[0].noteOn(lower + (nota.getU()*step), 6000);
    }
    
    public void stopNote(Nota nota) throws InterruptedException{
    	int lower = 0;
    	int step = 1;
    	
    	synthesizer.getChannels()[0].programChange(orchestra[nota.getStrumento()]);
    	
    	midiChannels[0].noteOff(lower + (nota.getM()*step), 6000);
    	midiChannels[0].noteOff(lower + (nota.getB()*step), 6000);
    	midiChannels[0].noteOff(lower + (nota.getU()*step), 6000);
    }
    
    public void playFlow(Spartito spartito, int bpm) throws InterruptedException {
    	
    	int quartina = spartito.getQuartina();
    	
    	int spacing = 60000/bpm*4/quartina;
    	
    	ArrayList<ArrayList<int[]>> flow = spartito.getFlow();
    	
    	for(int i = 0; i<flow.size();i++) {
    		ArrayList<int[]> beat = flow.get(i);
    		if(beat != null) {
    			for(int j = 0; j< beat.size();j++) {
    				int array[] = beat.get(j);
    				if(array[1] != -1) {
    			    	synthesizer.getChannels()[0].programChange(orchestra[array[1]]);
    			    	midiChannels[0].noteOn(array[0], 6000);
    				}
    				else
    			    	midiChannels[0].noteOff(array[0], 6000);
    			}
    		}
    		Thread.sleep(spacing);
    		//Thread.sleep(10);
    	}
    }
}
