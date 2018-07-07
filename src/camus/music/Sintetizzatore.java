package camus.music;

import java.util.ArrayList;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

public class Sintetizzatore {
	private Synthesizer synthesizer;
    private final MidiChannel[] midiChannels;
    private final Instrument[] instruments;
    
    public Sintetizzatore() {
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
    
    public void play(Nota nota) throws InterruptedException{
    	int lower = 30;
    	int step = 4;
    	
    	synthesizer.getChannels()[0].programChange((nota.getStrumento() * 2)+80);
    	
    	midiChannels[0].noteOn(lower + (nota.getReference()*step), 6000);
    	midiChannels[0].noteOn(lower + (nota.getNote2()*step), 6000);
    	midiChannels[0].noteOn(lower + (nota.getNote3()*step), 6000);
    	Thread.sleep(400);
    	midiChannels[0].noteOff(lower + (nota.getReference()*step), 6000);
    	midiChannels[0].noteOff(lower + (nota.getNote2()*step), 6000);
    	midiChannels[0].noteOff(lower + (nota.getNote3()*step), 6000);
    }
    
    public void play(Spartito s) throws InterruptedException{
    	ArrayList<ArrayList<Nota>> spartito = s.getSpartito();
    	
    	int length = spartito.size();
		for (int i = 0; i < length; i++){
			int length2 = spartito.get(i).size();
			for (int j = 0; j < length2; j++){
				Nota nota = spartito.get(i).get(j);
				play(nota);
			}
			
		}
    }
}
