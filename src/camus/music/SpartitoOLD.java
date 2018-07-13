package camus.music;

import java.util.ArrayList;

import camus.music.Nota;
import camus.music.Strumento;
import camus.core.*;

public class Spartito {
	private int reference = 60;
	private ArrayList<ArrayList<Nota>> spartito= new ArrayList<ArrayList<Nota>>();
	private ArrayList<Strumento> orchestra = new ArrayList <Strumento>();
	private ArrayList<ArrayList<int[]>> flow;
	private int quartina = 64;
	
	public void estrazione(GofBoard gofBoard, GcgBoard gcgBoard) {
		ArrayList<Nota> step = new ArrayList<Nota>();

		GofCell[][] gofGrid = gofBoard.getGrid();
		GcgCell[][] gcgGrid = gcgBoard.getGrid();

		boolean a, b, c, d, m, n, o, p;
		int width = gofGrid[0].length;
		String tm[] = new String[2];
		Nota newNota;

		for(int i=0; i < width; i++) {
			for(int j=0; j < width; j++) {
				if (gofGrid[i][j].getState()) {
					reference = 40 + (int)(Math.random() * 17);
					newNota = new Nota(reference, width - i, j+1);

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
					newNota.setTimeMorfology(tm, quartina);
					newNota.setStrumento(gcgGrid[i][j].getState());
					step.add(newNota);

					//orchestra.add(new Strumento(gcgGrid[i][j].getState()));
				}
			}
		}
		spartito.add(step);
	}

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
			timeMorph[0] = "B[UM]";
			break;
		case "0001":
			timeMorph[0] = "[UMB]";
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
			timeMorph[0] = "U[MB]";
			break;
		case "1011":
			timeMorph[0] = "MUB";
			break;
		case "1111":
			timeMorph[0] = "M[UB]";
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
		int flowSize = getSpartitoSize() * (quartina * 2);
		flow = new ArrayList<ArrayList<int[]>>();

		for(int k = 0; k < flowSize; k++)
			flow.add(null);

		int array[];
		int index = 0; 
		int length1 = spartito.size();
		for(int i = 0; i < length1; i++){
			int length2 = spartito.get(i).size();
			for(int j = 0; j < length2; j++){
				Nota nota = spartito.get(i).get(j);

				array = new int[2];
				array[0] = nota.getB();
				array[1] = nota.getStrumento();
				int startB = nota.getbStart();
				if(flow.get(index + startB) == null)
					flow.set(index + startB, new ArrayList<int[]>());
				flow.get(index + startB).add(array);

				array = new int[2];
				array[0] = nota.getM();
				array[1] = nota.getStrumento();
				int startM = nota.getmStart();
				if(flow.get(index + startM) == null)
					flow.set(index + startM, new ArrayList<int[]>());
				flow.get(index + startM).add(array);

				array = new int[2];
				array[0] = nota.getU();
				array[1] = nota.getStrumento();
				int startU = nota.getuStart();
				if(flow.get(index + startU) == null)
					flow.set(index + startU, new ArrayList<int[]>());
				flow.get(index + startU).add(array);


				array = new int[2];
				array[0] = nota.getB();
				array[1] = -1;
				int endB = nota.getbEnd();
				if(flow.get(index + endB) == null)
					flow.set(index + endB, new ArrayList<int[]>());
				flow.get(index + endB).add(array);

				array = new int[2];
				array[0] = nota.getM();
				array[1] = -1;
				int endM = nota.getmEnd();
				if(flow.get(index + endM) == null)
					flow.set(index + endM, new ArrayList<int[]>());
				flow.get(index + endM).add(array);


				array = new int[2];
				array[0] = nota.getU();
				array[1] = -1;
				int endU = nota.getuEnd();
				if(flow.get(index + endU) == null)
					flow.set(index + endU, new ArrayList<int[]>());
				flow.get(index + endU).add(array);

				index = index + (quartina * 2);
			}
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

}


