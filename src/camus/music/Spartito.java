package camus.music;

import java.util.ArrayList;

import camus.music.Nota;
import camus.music.Strumento;
import camus.core.*;

public class Spartito {
	private int reference = 0;
	private ArrayList<ArrayList<Nota>> spartito= new ArrayList<ArrayList<Nota>>();
	private Strumento strumento;
	private ArrayList<ArrayList<int[]>> flow;
	private int quartina = 64;

	public Spartito(Strumento s){
		this.strumento = s;
		this.quartina = s.getQuartina();
	}

	public void estrazione(GofBoard gofBoard, GcgBoard gcgBoard) {
		ArrayList<Nota> step = new ArrayList<Nota>();

		GofCell[][] gofGrid = gofBoard.getGrid();
		GcgCell[][] gcgGrid = gcgBoard.getGrid();

		boolean a, b, c, d, m, n, o, p;
		int width = gofGrid[0].length;
		String tm[] = new String[2];
		Nota newNota;
		int middle;
		for(int j=0; j < width; j++) {
			for(int i=0; i < width; i++) {
				if (gofGrid[i][j].getState()) {
					//reference = 40 + (int)(Math.random() * 17);
					//newNota = new Nota(reference, width - i, j+1);
					middle = reference + (width-i) + j+1;
					int ottava = strumento.getOttava();
					
					if(strumento.getScala().length == 1) {
						middle = middle % strumento.getScala()[0].length;
						int bottom;
						int upper;
						/*if(middle == 0)// || middle == 1)
							bottom = 0;
						else
							bottom = middle - 1;
						if(middle == strumento.getScala()[0].length - 1)// || middle == strumento.getScala().length - 2)
							upper = strumento.getScala()[0].length - 1;
						else
							upper = middle + 1;
						*/
						bottom = (middle + (width-i)) % strumento.getScala()[0].length;
						upper = (middle + j+1) % strumento.getScala()[0].length;
						newNota = new Nota(strumento, strumento.getScala()[0][middle] + ottava, strumento.getScala()[0][bottom] + ottava, strumento.getScala()[0][upper] + ottava);
					}else {
						middle = middle % strumento.getScala().length;
						newNota = new Nota(strumento, strumento.getScala()[middle][0] + ottava, strumento.getScala()[middle][1] + ottava, strumento.getScala()[middle][2] + ottava);
					}

					a = false;
					if (j != 0){
						if(gofGrid[i][j-1].getState()){
							a = true;
						}
					}

					b = false;
					if (j != width-1){
						if(gofGrid[i][j+1].getState()){
							b = true;
						}
					}

					c = false;
					if (i != width-1){
						if(gofGrid[i+1][j].getState()){
							c = true;
						}
					}

					d = false;
					if (i != 0){
						if(gofGrid[i-1][j].getState()){
							d = true;
						}
					}

					m = false;
					if (i != 0 && j != 0){
						if(gofGrid[i-1][j-1].getState()){
							m = true;
						}
					}

					n = false;
					if (i != width-1 && j != width-1){
						if(gofGrid[i+1][j+1].getState()){
							n = true;
						}
					}

					o = false;
					if (i != width-1 && j != 0){
						if(gofGrid[i+1][j-1].getState()){
							o = true;
						}
					}

					p = false;
					if (i != 0 && j != width - 1){
						if(gofGrid[i-1][j+1].getState()){
							p = true;
						}
					}

					tm = calculateTimeMorphology(a, b, c, d, m, n, o, p);
					newNota.setStatoGcg(gcgGrid[i][j].getState());
					newNota.setTimeMorfology(tm);
					step.add(newNota);

					//orchestra.add(new Strumento(gcgGrid[i][j].getState()));
				}
			}
		}
		spartito.add(step);
	}

	/*public void estrazione(GofBoard[] gofBoards, GcgBoard[] gcgBoards, int num) {
		ArrayList<Nota> step = new ArrayList<Nota>();

		for(int k = 0; k < num; k++){
			GofCell[][] gofGrid = gofBoards[k].getGrid();
			GcgCell[][] gcgGrid = gcgBoards[k].getGrid();

			boolean a, b, c, d, m, n, o, p;
			int width = gofGrid[0].length;
			String tm[] = new String[2];
			Nota newNota;
			int middle;
			for(int i=0; i < width; i++) {
				for(int j=0; j < width; j++) {
					if (gofGrid[i][j].getState()) {
						//reference = 40 + (int)(Math.random() * 17);
						//newNota = new Nota(reference, width - i, j+1);
						middle = reference + (width-i) + j+1;
						middle = middle % eMinorPentatonic.length;
						int bottom;
						if(middle == 0)
							bottom = 0;
						else
							bottom = middle - 1;
						int upper;
						if(middle == eMinorPentatonic.length - 1)
							upper = eMinorPentatonic.length - 1;
						else
							upper = middle + 1;
						newNota = new Nota(eMinorPentatonic[middle], eMinorPentatonic[bottom], eMinorPentatonic[upper]);

						a = false;
						if (j != 0){
							if(gofGrid[i][j-1].getState()){
								a = true;
							}
						}

						b = false;
						if (j != width-1){
							if(gofGrid[i][j+1].getState()){
								b = true;
							}
						}

						c = false;
						if (i != width-1){
							if(gofGrid[i+1][j].getState()){
								c = true;
							}
						}

						d = false;
						if (i != 0){
							if(gofGrid[i-1][j].getState()){
								d = true;
							}
						}

						m = false;
						if (i != 0 && j != 0){
							if(gofGrid[i-1][j-1].getState()){
								m = true;
							}
						}

						n = false;
						if (i != width-1 && j != width-1){
							if(gofGrid[i+1][j+1].getState()){
								n = true;
							}
						}

						o = false;
						if (i != width-1 && j != 0){
							if(gofGrid[i+1][j-1].getState()){
								o = true;
							}
						}

						p = false;
						if (i != 0 && j != width - 1){
							if(gofGrid[i-1][j+1].getState()){
								p = true;
							}
						}

						tm = calculateTimeMorphology(a, b, c, d, m, n, o, p);
						newNota.setStrumento(orchestra.get(k));
						newNota.setLunghezzaGcg(gcgGrid[i][j].getState());
						newNota.setTimeMorfology(tm, quartina);
						step.add(newNota);

						//orchestra.add(new Strumento(gcgGrid[i][j].getState()));
					}
				}
			}
		}
		spartito.add(step);
	}*/

	public String[] calculateTimeMorphology(boolean a, boolean b, boolean c, boolean d, boolean m, boolean n, boolean o, boolean p) {

		String timeMorph[] = new String[2];

		/*int abcd = Integer.valueOf(String.valueOf(a) + String.valueOf(b) + String.valueOf(c) + String.valueOf(d));
		int dcba = Integer.valueOf(String.valueOf(d) + String.valueOf(c) + String.valueOf(b) + String.valueOf(a));
		int mnop = Integer.valueOf(String.valueOf(m) + String.valueOf(n) + String.valueOf(o) + String.valueOf(p));
		int ponm = Integer.valueOf(String.valueOf(p) + String.valueOf(o) + String.valueOf(n) + String.valueOf(m));
		 */
		String s1 = (a | d) ? "1" : "0";
		String s2 = (b | c) ? "1" : "0";
		String s3 = (c | b) ? "1" : "0";
		String s4 = (d | a) ? "1" : "0";

		String tgg = s1 + s2 + s3 + s4;

		s1 = (m | p) ? "1" : "0";
		s2 = (n | o) ? "1" : "0";
		s3 = (o | n) ? "1" : "0";
		s4 = (p | m) ? "1" : "0";

		String dur = s1 + s2 + s3 + s4;

		switch(tgg) {
		case "0000":
			//timeMorph[0] = "B[UM]";
			timeMorph[0] = "UMB";
			break;
		case "0001":
			//timeMorph[0] = "[UMB]";
			timeMorph[0] = "BUM";
			break;
		case "0010":
			timeMorph[0] = "BUM";
			break;
		case "0011":
			timeMorph[0] = "UMB";
			break;
		case "0101":
			timeMorph[0] = "BMU";
			break;
		case "0110":
			timeMorph[0] = "UBM";
			break;
		case "0111":
			timeMorph[0] = "MBU";
			break;
		case "1001":
			//timeMorph[0] = "U[MB]";
			timeMorph[0] = "MBU";
			break;
		case "1011":
			timeMorph[0] = "MUB";
			break;
		case "1111":
			//timeMorph[0] = "M[UB]";
			timeMorph[0] = "MUB";
			break;

		}

		switch(dur) {
		case "0000":
			timeMorph[1] = "B[UM]";
			break;
		case "0001":
			timeMorph[1] = "[UMB]";
			break;
		case "0010":
			timeMorph[1] = "BUM";
			break;
		case "0011":
			timeMorph[1] = "UMB";
			break;
		case "0101":
			timeMorph[1] = "BMU";
			break;
		case "0110":
			timeMorph[1] = "UBM";
			break;
		case "0111":
			timeMorph[1] = "MBU";
			break;
		case "1001":
			timeMorph[1] = "U[MB]";
			break;
		case "1011":
			timeMorph[1] = "MUB";
			break;
		case "1111":
			timeMorph[1] = "M[UB]";
			break;
		}

		return timeMorph;
	}

	public int getSpartitoSize(){
		int sum = 0;
		for(int i = 0; i < spartito.size(); i++){
			sum += spartito.get(i).size();
		}
		return sum;
	}

	public void translate(){
		if(strumento.isSicronizzazione())
			translateSincrona();
		else
			translateAsincrona();
	}
	
	
	public void translateSincrona(){
		int flowSize = getSpartitoSize() * (quartina * 10);
		flow = new ArrayList<ArrayList<int[]>>();
		int index = strumento.getInizio();
		int fine;
		if(strumento.getContinuaPer() == 0)
			fine = flowSize - 1;
		else
			fine = Math.min(index + strumento.getContinuaPer(), flowSize - 1);
		
		for(int k = 0; k < flowSize; k++)
			flow.add(null);

		int array[];
		int repeat = 1;
		int max = 0;
		boolean inserisci;
		int pos;
		index = index + (int)(strumento.getGapQuartina());
		int length1 = spartito.size();
		for(int i = 0; i < length1 && index <= fine; i++){     //scorre le schermate del Game of Life di questo strumento
			int length2 = spartito.get(i).size();
			for(int j = 0; j < length2 && index <= fine; j++){		//scorre le Note ricavate dalle celle attive di tale schermata
				Nota nota = spartito.get(i).get(j);
				
				repeat = 1;
				if(strumento.getRepeat() != null)
					repeat = strumento.getRepeat()[ nota.getStatoGcg() % strumento.getRepeat().length ] * 4;
				
				for(int k = 0; k < repeat && index <= fine; k++){
					inserisci = true;
					if(k % 2 != 0)
						inserisci = false;
					
					if(inserisci){
						array = new int[2];
						array[0] = nota.getB();
						//array[1] = nota.getStrumento().getStrumentIndex();
						array[1] = strumento.getOrchestraIndex(); //nota.getStrumento().getOrchestraIndex();
						int startB = nota.getbStart();
						pos = index + startB;// + (int)strumento.getGapQuartina();
						if(flow.get(pos) == null)
							flow.set(pos, new ArrayList<int[]>());
						flow.get(pos).add(array);
		
						array = new int[2];
						array[0] = nota.getM();
						//array[1] = nota.getStrumento().getStrumentIndex();
						array[1] = strumento.getOrchestraIndex(); //nota.getStrumento().getOrchestraIndex();
						int startM = nota.getmStart();
						pos = index + startM;// + (int)strumento.getGapQuartina();
						if(flow.get(pos) == null)
							flow.set(pos, new ArrayList<int[]>());
						flow.get(pos).add(array);
		
						array = new int[2];
						array[0] = nota.getU();
						//array[1] = nota.getStrumento().getStrumentIndex();
						array[1] = strumento.getOrchestraIndex(); //nota.getStrumento().getOrchestraIndex();
						int startU = nota.getuStart();
						pos = index + startU;// + (int)strumento.getGapQuartina();
						if(flow.get(pos) == null)
							flow.set(pos, new ArrayList<int[]>());
						flow.get(pos).add(array);
		
						array = new int[2];
						array[0] = - nota.getB();
						//array[1] = -1;
						array[1] = strumento.getOrchestraIndex(); //nota.getStrumento().getOrchestraIndex();
						int endB = nota.getbEnd();
						pos = index + endB;// + (int)strumento.getGapQuartina();
						if(flow.get(pos) == null)
							flow.set(pos, new ArrayList<int[]>());
						flow.get(pos).add(array);
						max = Math.max(max, pos);
		
						array = new int[2];
						array[0] = - nota.getM();
						//array[1] = -1;
						array[1] = strumento.getOrchestraIndex(); //nota.getStrumento().getOrchestraIndex();
						int endM = nota.getmEnd();
						pos = index + endM;// + (int)strumento.getGapQuartina();
						if(flow.get(pos) == null)
							flow.set(pos, new ArrayList<int[]>());
						flow.get(pos).add(array);
						max = Math.max(max, pos);
		
						array = new int[2];
						array[0] = - nota.getU();
						//array[1] = -1;
						array[1] = strumento.getOrchestraIndex(); //nota.getStrumento().getOrchestraIndex();
						int endU = nota.getuEnd();
						pos = index + endU;// + (int)strumento.getGapQuartina();
						if(flow.get(pos) == null)
							flow.set(pos, new ArrayList<int[]>());
						flow.get(pos).add(array);
						max = Math.max(max, pos);
					}else{
						pos = index + nota.getbEnd();
						max = Math.max(max, pos);
						pos = index + nota.getmEnd();
						max = Math.max(max, pos);
						pos = index + nota.getuEnd();
						max = Math.max(max, pos);
					}
					//index = index + (int)(quartina);
					
					int resto = (max - (int)strumento.getGapQuartina()) % (quartina);
					if(resto == 0)
						index = max;
					else
						index = max + (quartina - resto);
				}
			}
		}
	}

	public void translateAsincrona(){
		int flowSize = getSpartitoSize() * (quartina * 10);
		flow = new ArrayList<ArrayList<int[]>>();
		int index = strumento.getInizio();
		int fine;
		if(strumento.getContinuaPer() == 0)
			fine = flowSize - 1;
		else
			fine = Math.min(index + strumento.getContinuaPer(), flowSize - 1);
		
		for(int k = 0; k < flowSize; k++)
			flow.add(null);

		int array[];
		int max = 0;
		int pos;
		int length1 = spartito.size();
		for(int i = 0; i < length1 && index <= fine; i++){
			int length2 = spartito.get(i).size();
			for(int j = 0; j < length2 && index <= fine; j++){
				Nota nota = spartito.get(i).get(j);

				array = new int[2];
				array[0] = nota.getB();
				//array[1] = nota.getStrumento().getStrumentIndex();
				array[1] = strumento.getOrchestraIndex(); //nota.getStrumento().getOrchestraIndex();
				int startB = nota.getbStart();
				pos = index + startB;
				if(flow.get(pos) == null)
					flow.set(pos, new ArrayList<int[]>());
				flow.get(pos).add(array);

				array = new int[2];
				array[0] = nota.getM();
				//array[1] = nota.getStrumento().getStrumentIndex();
				array[1] = strumento.getOrchestraIndex(); //nota.getStrumento().getOrchestraIndex();
				int startM = nota.getmStart();
				pos = index + startM;
				if(flow.get(pos) == null)
					flow.set(pos, new ArrayList<int[]>());
				flow.get(pos).add(array);

				array = new int[2];
				array[0] = nota.getU();
				//array[1] = nota.getStrumento().getStrumentIndex();
				array[1] = strumento.getOrchestraIndex(); //nota.getStrumento().getOrchestraIndex();
				int startU = nota.getuStart();
				pos = index + startU;
				if(flow.get(pos) == null)
					flow.set(pos, new ArrayList<int[]>());
				flow.get(pos).add(array);


				array = new int[2];
				array[0] = - nota.getB();
				//array[1] = -1;
				array[1] = strumento.getOrchestraIndex(); //nota.getStrumento().getOrchestraIndex();
				int endB = nota.getbEnd();
				pos = index + endB;
				if(flow.get(pos) == null)
					flow.set(pos, new ArrayList<int[]>());
				flow.get(pos).add(array);
				max = Math.max(max, pos);

				array = new int[2];
				array[0] = - nota.getM();
				//array[1] = -1;
				array[1] = strumento.getOrchestraIndex(); //nota.getStrumento().getOrchestraIndex();
				int endM = nota.getmEnd();
				pos = index + endM;
				if(flow.get(pos) == null)
					flow.set(pos, new ArrayList<int[]>());
				flow.get(pos).add(array);
				max = Math.max(max, pos);

				array = new int[2];
				array[0] = - nota.getU();
				//array[1] = -1;
				array[1] = strumento.getOrchestraIndex(); //nota.getStrumento().getOrchestraIndex();
				int endU = nota.getuEnd();
				pos = index + endU;
				if(flow.get(pos) == null)
					flow.set(pos, new ArrayList<int[]>());
				flow.get(pos).add(array);
				max = Math.max(max, pos);

				index = max;
				int resto = index % (quartina);     //se la chiusura di una nota si avvicina alla quartina
				//la nota successiva comincia sulla quartina per rispettare il tempo
				if(resto >= (quartina) - 8)
					index = max + (quartina - resto);
			}
		}
	}

	public void aggiustaBase(Spartito melodia) {
		ArrayList<ArrayList<int[]>> flowMelodia = melodia.getFlow();
		
		
		ArrayList<int[]> beatMelodia;
		ArrayList<int[]> beatBase;
		int arrayMelodia[];
		int arrayBase[];
		for(int i = 0; i<flowMelodia.size() && i < flow.size(); i += quartina) {
			beatMelodia = flowMelodia.get(i);
			if(beatMelodia != null) {
				arrayMelodia = beatMelodia.get(0);
				for(int z = 1; z < beatMelodia.size(); z++){
					if(arrayMelodia[0] > 0)
						break;
					arrayMelodia = beatMelodia.get(z);
				}
				
				if(arrayMelodia[0] > 0){
					int notaMelodia = arrayMelodia[0];
					int[] accordo = new int[3];
					if(notaMelodia > 60)
						accordo[0] = notaMelodia - 24;
					else
						accordo[0] = notaMelodia - 12;
					
					accordo[1] = accordo[0] + 2;
					accordo[2] = accordo[1] + 2;
					
					if(flow.get(i) != null){
						beatBase = flow.get(i);
						int k = 0;
						for(int j = 0; j < beatBase.size(); j++){
							arrayBase = beatBase.get(j);
							if(arrayBase[0] > 0){
								arrayBase[0] = accordo[k];
								if(k == accordo.length - 1)
									k = 0;
								else
									k++;
								System.out.println("SET aggiusta;  i:" + i + " Nota melodia:" + notaMelodia);
								flow.get(i).set(j, arrayBase);
							}
						}
					}
				}
			}
		}
	}
	
	public void defineBeat(){
		int[] startBeat = new int[2];
		startBeat[0] = 40;
		startBeat[1] = 15;   //canale 15 predefinito per il beat
		int[] endBeat = new int[2];
		endBeat[0] = -40;
		endBeat[1] = 15;
		int j;
		for(int i=0; i<flow.size() - 100; i+=quartina){
			if(flow.get(i) == null)
				flow.set(i, new ArrayList<int[]>());
			flow.get(i).add(startBeat);
			j = i + 5;
			if(flow.get(j) == null)
				flow.set(j, new ArrayList<int[]>());
			flow.get(j).add(endBeat);
		}
	}

	public void printList() {		
		int length = spartito.size();
		for (int i = 0; i < length; i++){
			int length2 = spartito.get(i).size();
			for (int j = 0; j < length2; j++){
				Nota nota = spartito.get(i).get(j);
				System.out.println(i + ": " + j + " ->  |" + nota.getM() + " " + nota.getB() + " " + nota.getU() + "|  \ttimeMorfology: " + nota.getTimeMorfology()[0] + "; " + nota.getTimeMorfology()[1] + "  \tstrumento: " + nota.getStrumento());
				System.out.println("\t M:" + nota.getmStart() + " : " + nota.getmEnd() + "\t B:" + nota.getbStart() + " : " + nota.getbEnd() + "\t U:" + nota.getuStart() + " : " + nota.getuEnd());
			}
		}
	}

	public void printFlow(int stop){
		if (flow.size() < stop)
			stop = flow.size();
		for(int i = 0; i<stop;i++) {
			ArrayList<int[]> beat = flow.get(i);

			if(i % quartina == 0)
				System.out.print(i +" Q -> ");
			else
				System.out.print(i +" -> ");
			if(beat != null) {
				for(int j = 0; j< beat.size();j++) {
					int array[] = beat.get(j);

					System.out.print(j + ": [" + array[0] + ", " + array[1] + "] ");
				}
				System.out.println();
			}else
				System.out.println("X");
		}
	}

	public ArrayList<ArrayList<Nota>> getSpartito() {
		return spartito;
	}

	public void setSpartito(ArrayList<ArrayList<Nota>> spartito) {
		this.spartito = spartito;
	}

	public static void print2DB(boolean mat[][])
	{
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[i].length; j++) {
				System.out.print(mat[i][j] + " ");
			}
			System.out.print("\n");
		}
	}

	public static void print2DI(int mat[][])
	{
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[i].length; j++) {
				System.out.print(mat[i][j] + " ");
			}
			System.out.print("\n");
		}
	}

	public ArrayList<ArrayList<int[]>> getFlow() {
		return flow;
	}

	public void setFlow(ArrayList<ArrayList<int[]>> flow) {
		this.flow = flow;
	}

	public int getQuartina() {
		return quartina;
	}

	public void setQuartina(int quartina) {
		this.quartina = quartina;
	}

	public Strumento getStrumento() {
		return strumento;
	}

	public void setStrumento(Strumento strumento) {
		this.strumento = strumento;
	}


}


