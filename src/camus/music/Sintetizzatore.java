package camus.music;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

import camus.gui.Controller;

public class Sintetizzatore {
	private Synthesizer synthesizer;
	private final MidiChannel[] midiChannels;
	private final Instrument[] instruments;
	HashMap<String, Integer> mappaStrumenti;
	
	public Sintetizzatore(ArrayList<Strumento> orchestra) throws InvalidMidiDataException, Exception {
		try {
			synthesizer = MidiSystem.getSynthesizer();
			synthesizer.open();
		} catch (MidiUnavailableException ex) {
			ex.printStackTrace();
			System.exit(1);
		}   

		this.midiChannels = synthesizer.getChannels();

		File f = new File(".\\src\\fluid.sf2");
		Soundbank bank = MidiSystem.getSoundbank(f);

//		Soundbank bank = synthesizer.getDefaultSoundbank();

		synthesizer.loadAllInstruments(bank);

		this.instruments = synthesizer.getLoadedInstruments();
		synthesizer.loadAllInstruments(synthesizer.getDefaultSoundbank());

		orchestra.sort(null);
		for(int i=0; i < orchestra.size() && i < synthesizer.getChannels().length; i++){
			midiChannels[i].programChange(orchestra.get(i).getStrumentIndex());
		}
		//midiChannels[15].programChange(4); //canale 15 predefinito per il beat
		
		creaMappaStrumenti();
		System.out.println("[STATE] MIDI channels: " + midiChannels.length);
		System.out.println("[STATE] Instruments: " + instruments.length);
	}
	
	public void changeChannel(int i, int strumentIndex){
		midiChannels[i].programChange(strumentIndex);
	}
	
	public void playFlow(Spartito spartito, int bpm) throws InterruptedException {

		int quartina = spartito.getStrumento().getQuartina();

		//int spacing = 60000/bpm*4/quartina;
		int spacing = 60000/bpm/quartina;

		ArrayList<ArrayList<int[]>> flow = spartito.getFlow();

		for(int i = 0; i<flow.size();i++) {
			ArrayList<int[]> beat = flow.get(i);
			if(beat != null) {
				for(int j = 0; j< beat.size();j++) {
					int array[] = beat.get(j);
					//if(array[1] != -1) {
					if(array[0] >= 0){
						//synthesizer.getChannels()[0].programChange(array[1]);//(orchestra[array[1]]);
						midiChannels[array[1]].noteOn(array[0], 6000);
					}
					else
						midiChannels[array[1]].noteOff(- array[0], 6000);
				}
			}
			Thread.sleep(spacing);
			//Thread.sleep(10);
		}
	}

	public void playFlow(Spartito[] spartiti, int bpm) throws InterruptedException {
		System.out.println("playFlow start...");
		//ArrayList<ArrayList<int[]>> flow = mergeSpartiti(spartiti);
		ArrayList<ArrayList<int[]>> flow = spartiti[0].getFlow();

		int quartina = spartiti[0].getStrumento().getQuartina();
		//int spacing = 60000/bpm*4/quartina;
		int spacing = 60000/bpm/quartina;

		for(int i = 0; i<flow.size();i++) {

			if(i % quartina == 0)
				System.out.print("\n" + i + "->Q:\t");
			else
				System.out.print("\n" + i + "->\t");
			for(int k = 0; k < spartiti.length; k ++){
				ArrayList<int[]> beat = spartiti[k].getFlow().get(i);
				if(beat != null) {
					for(int j = 0; j< beat.size();j++) {
						int array[] = beat.get(j);
						//if(array[1] != -1) {
						if(array[0] >= 0){
							//synthesizer.getChannels()[0].programChange(array[1]);//(orchestra[array[1]]);
							midiChannels[array[1]].noteOn(array[0], spartiti[k].getStrumento().getForzaOn());
							System.out.print("Channel[" + array[1] + "](" + spartiti[k].getStrumento().getName() + ").noteON:" + array[0] + ";  ");
						}
						else{
							midiChannels[array[1]].noteOff(- array[0], spartiti[k].getStrumento().getForzaOn());
							System.out.print("Channel[" + array[1] + "](" + spartiti[k].getStrumento().getName() + ").noteOFF:" + (-array[0]) + ";  ");
						}
					}
				}else
					System.out.print("X");
			}
			Thread.sleep(spacing);
			//Thread.sleep(10);
		}
		System.out.println("playFlow end...");
	}

	public void playFlowOnce(Spartito[] spartiti, int bpm, int time) throws InterruptedException {
		int quartina = spartiti[0].getStrumento().getQuartina();
    	int spacing = 60000/bpm/quartina;
    	
		if(time % quartina == 0)
			System.out.print("\n" + time + "->Q:\t");
		else
			System.out.print("\n" + time + "->\t");
		for(int k = 0; k < spartiti.length; k ++){
			ArrayList<int[]> beat = spartiti[k].getFlow().get(time);
			if(beat != null) {
				for(int j = 0; j< beat.size();j++) {
					int array[] = beat.get(j);
					if(array[0] >= 0){
						midiChannels[array[1]].noteOn(array[0], spartiti[k].getStrumento().getForzaOn());
						System.out.print("Channel[" + array[1] + "](" + spartiti[k].getStrumento().getName() + ").noteON:" + array[0] + ";  ");
					}
					else{
						midiChannels[array[1]]. noteOff(- array[0], spartiti[k].getStrumento().getForzaOn());
						System.out.print("Channel[" + array[1] + "](" + spartiti[k].getStrumento().getName() + ").noteOFF:" + (-array[0]) + ";  ");
					}
				}
			}else
				System.out.print("X");
		}
	}

	public ArrayList<ArrayList<int[]>> mergeSpartiti(Spartito[] spartiti){
		System.out.println("merge start...");
		ArrayList<ArrayList<int[]>> masterFlow = spartiti[0].getFlow();

		for(int k = 0; k < spartiti.length; k++){
			System.out.println("spartito " + k);
			for(int i = 0; i < spartiti[k].getFlow().size(); i++) {
				System.out.println("\t flow.get " + i);
				ArrayList<int[]> beat = spartiti[k].getFlow().get(i);
				if(beat != null) {
					for(int j = 0; j< beat.size();j++) {
						System.out.println("\t\t .get " + j);
						int array[] = beat.get(j);
						masterFlow.get(i).add(array);
					}
				}
				else
					System.out.println("\t\t null");
			}
		}

		System.out.println("merge end...");
		return masterFlow;
	}
	
	public void stopMusic(){
		for (int i = 0; i < midiChannels.length; i++)
			midiChannels[i].allNotesOff();
	}
	
	private void creaMappaStrumenti(){
		mappaStrumenti = new HashMap<String, Integer>();
		Instrument[] inst = synthesizer.getAvailableInstruments();
		String key;
		for(int i = 0; i < inst.length && i < 128; i++){
			key = inst[i].getName().trim();
			mappaStrumenti.put(key, new Integer(i));
			System.out.println("MAPPA  name: " + key + " index: " + mappaStrumenti.get(inst[i].getName()));
		}
	}
	
	public HashMap<String, Integer> listOfInstruments(){
		return mappaStrumenti;
	}
	
	/* public void play(Spartito s) throws InterruptedException{
    	ArrayList<ArrayList<Nota>> spartito = s.getSpartito();

    	int length = spartito.size();
		for (int i = 0; i < length; i++){
			int length2 = spartito.get(i).size();
			for (int j = 0; j < length2; j++){
				Nota nota = spartito.get(i).get(j);
				play(nota);
			}
			play(spartito.get(i));

		}
    }*/

	/*public void play(ArrayList<Nota> step) throws InterruptedException{
    	int length2 = step.size();
    	for (int j = 0; j < length2; j++){
			Nota nota = step.get(j);
			play(nota);
			//startNote(nota);
		}

		for (int j = 0; j < length2; j++){
			Nota nota = step.get(j);
			//play(nota);
			startNote(nota);
		}
		Thread.sleep(400);
		for (int j = 0; j < length2; j++){
			Nota nota = step.get(j);
			//play(nota);
			stopNote(nota);
		}
    }*/

	/*public void play(Nota nota) throws InterruptedException{
    	int lower = 0;
    	int step = 1;

    	synthesizer.getChannels()[0].programChange(orchestra[nota.getStrumento().getStrumentIndex()]);

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
    }*/

	/* public void startNote(Nota nota) throws InterruptedException{
    	int lower = 0;
    	int step = 1;

    	synthesizer.getChannels()[0].programChange(orchestra[nota.getStrumento().getStrumentIndex()]);

    	midiChannels[0].noteOn(lower + (nota.getM()*step), 6000);
    	midiChannels[0].noteOn(lower + (nota.getB()*step), 6000);
    	midiChannels[0].noteOn(lower + (nota.getU()*step), 6000);
    }

    public void stopNote(Nota nota) throws InterruptedException{
    	int lower = 0;
    	int step = 1;

    	synthesizer.getChannels()[0].programChange(orchestra[nota.getStrumento().getStrumentIndex()]);

    	midiChannels[0].noteOff(lower + (nota.getM()*step), 6000);
    	midiChannels[0].noteOff(lower + (nota.getB()*step), 6000);
    	midiChannels[0].noteOff(lower + (nota.getU()*step), 6000);
    }*/

}
