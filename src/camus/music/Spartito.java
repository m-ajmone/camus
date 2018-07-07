package camus.music;

import java.util.ArrayList;

import camus.music.Nota;
import camus.music.Strumento;
import camus.core.*;

public class Spartito {
	static int reference = 0;
	static ArrayList<ArrayList<Nota>> spartito= new ArrayList<ArrayList<Nota>>();
	static ArrayList<Strumento> orchestra = new ArrayList <Strumento>();
	
	public void MusicController(){
		
	}
	
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
					newNota.setTimeMorfology(tm);
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
	
	public void printList() {
	    /*for(Nota elemento : spartito) {
	    	System.out.print("|" + elemento.getReference() + " " + elemento.getNote2() + " " + elemento.getNote3() + "| ");
	    }
	    System.out.println();
	    for(Strumento elemento : orchestra) {
	    	System.out.print(elemento.getStrumento() + " ");
	    }*/
		
		/*int length = spartito.size();
		for (int i = 0; i < length; i++){
			Nota nota = spartito.get(i);
			System.out.println(i + " ->  |" + nota.getReference() + " " + nota.getNote2() + " " + nota.getNote3() + "|  \ttimeMorfology: " + nota.getTimeMorfology()[0] + "; " + nota.getTimeMorfology()[1] + "  \tstrumento: " + orchestra.get(i).getStrumento());
		}*/
		
		int length = spartito.size();
		for (int i = 0; i < length; i++){
			int length2 = spartito.get(i).size();
			for (int j = 0; j < length2; j++){
				Nota nota = spartito.get(i).get(j);
				System.out.println(i + ": " + j + " ->  |" + nota.getReference() + " " + nota.getNote2() + " " + nota.getNote3() + "|  \ttimeMorfology: " + nota.getTimeMorfology()[0] + "; " + nota.getTimeMorfology()[1] + "  \tstrumento: " + nota.getStrumento());
			}
		}
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
			
}


